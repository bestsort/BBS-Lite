package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.CommentMapper;
import cn.bestsort.bbslite.pojo.dto.CommentDto;
import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.CommentExample;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private UserService userServicel;
    @Resource
    private  QuestionService questionService;
    @Resource
    private CountService countService;
    @Autowired
    private CommentMapper commentMapper;

    @CachePut(keyGenerator = "myKeyGenerator")
    public void insert(Comment comment) {
        if (comment.getPid() == null || comment.getPid()==0) {
            throw new CustomizeException(CustomizeErrorCodeEnum.TARGET_PAI_NOT_FOUND);
        }
        if(comment.getLevel() <= 1){
            //回复问题
            Question question = questionService.getByQuestionId(comment.getQuestionId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            countService.updateQuestionCommentCount(question.getId(),1L);
        }else{
            //回复评论
            Comment dbComment =commentMapper.selectByPrimaryKey(comment.getPid());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<CommentDto> listByQuestionId(Long questionId) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andQuestionIdEqualTo(questionId);
        commentExample.setOrderByClause("gmt_create desc");

        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }

        //将comments 先按照评论分级排序再按照时间排序
        comments.sort(new CommentComparator());


        //将 comment 转为 commentDTO
        HashMap<Long, CommentDto> dtoHashMap = new HashMap<>(10);
        List<CommentDto> commentDtos = new ArrayList<>(10);
        for(Comment comment:comments){
            CommentDto commentDTO = new CommentDto();
            BeanUtils.copyProperties(comment,commentDTO);
            User user = userServicel.getById(comment.getCommentator());
            commentDTO.setUser(user);
            if(comment.getLevel() <= 1) {
                // 如果为父评论则将其加入到 commentDTOList 并在 map 中标记
                dtoHashMap.put(comment.getId(), commentDTO);
                commentDtos.add(commentDTO);
            }
            else{
                // 否则就将评论装入到对应的父评论下
                dtoHashMap.get(comment.getPid())
                        .getSecondaryComment()
                        .add(commentDTO);
            }
        }
        return commentDtos;
    }
    private static class CommentComparator implements Comparator<Comment>{
        @Override
        public int compare(Comment o1, Comment o2) {
            long res;
            if(!o1.getLevel().equals(o2.getLevel())){
                //level 不相等则按照level排序
                res = o2.getLevel()-o1.getLevel();
            }
            else{
                //否则按照时间先后排序
                res = o1.getGmtCreate()-o2.getGmtCreate();
            }
            return (int)res;
        }
    }
}
