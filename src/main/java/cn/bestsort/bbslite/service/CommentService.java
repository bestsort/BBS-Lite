package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.SortBy;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.CommentMapper;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.CommentExample;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.vo.CommentVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName CommentService
 * @Description 评论处理Service层.
 * @Author bestsort
 * @Date 19-9-13 下午4:57
 * @Version 1.0
 * TODO BUG:当comment输入内容长度 >1000 的时候会报错.
 */
@CacheConfig(cacheNames = {"commentCache"})
@Service
public class CommentService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;

    @CachePut(keyGenerator = "myKeyGenerator")
    public void insert(Comment comment) {
        if (comment.getCommentTo() == null) {
            throw new CustomizeException(CustomizeErrorCodeEnum.TARGET_PAI_NOT_FOUND);
        }
        if(comment.getPid() == 0L){
            if (questionMapper.selectByPrimaryKey(comment.getCommentTo()) == null){
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            commentMapper.insertSelective(comment);
        }else if (comment.getPid() == 1L){
            //回复评论
            Comment dbComment =commentMapper.selectByPrimaryKey(comment.getCommentTo());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }
        else {
            throw new CustomizeException(CustomizeErrorCodeEnum.USER_ERROR);
        }
        questionExtMapper.incQuestionComment(comment.getCommentTo(),1L);
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public PageInfo<CommentVo> listByQuestionId(Long questionId, Integer page, Integer size) {
        Long creator = questionMapper.selectByPrimaryKey(questionId).getCreator();

        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andCommentToEqualTo(questionId);
        commentExample.setOrderByClause(SortBy.DEFAULT_ORDER);
        //PageHelper.startPage(page,size);
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.isEmpty()) {
            return new PageInfo<>();
        }

        //将comments 先按照评论分级排序再按照时间排序
        comments.sort(new CommentComparator());

        //将 comment 转为 commentDTO
        HashMap<Long, CommentVo> dtoHashMap = new HashMap<>(10);
        List<CommentVo> commentVos = new ArrayList<>(10);
        for(Comment comment:comments){
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            User user = userMapper.selectByPrimaryKey(comment.getCommentBy());
            user.setPassword(null);
            user.setToken(null);
            commentVo.setUser(user);
            commentVo.setIsAuthor(creator.equals(commentVo.getUser().getId()));
            if(comment.getPid() == 0) {
                commentVo.setSon(new ArrayList<>());
                // 如果为父评论则将其加入到 commentDTOList 并在 map 中标记
                dtoHashMap.put(comment.getId(), commentVo);
                commentVos.add(commentVo);
            }
            else{
                // 否则就将评论装入到对应的父评论下
                dtoHashMap.get(comment.getCommentTo())
                        .getSon()
                        .add(commentVo);
            }
        }
        return new PageInfo<>(commentVos);
    }
    private static class CommentComparator implements Comparator<Comment>{
        @Override
        public int compare(Comment o1, Comment o2) {
            long res;
            if(!o1.getPid().equals(o2.getPid())){
                //level 不相等则按照level排序
                res = o1.getPid()-o2.getPid();
            }
            else{
                //否则按照时间先后排序
                res = o2.getGmtCreate() - o1.getGmtCreate();
            }
            return (int)res;
        }
    }
}
