package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.CommentMapper;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.pojo.model.*;
import cn.bestsort.bbslite.vo.CommentVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public void insert(Comment comment,Long id,boolean isParent) {
        if(isParent){
            CommentParent commentParent = (CommentParent) comment;
            commentParent.setQuestionId(id);
            if (questionMapper.selectByPrimaryKey(id) == null){
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            commentParent.setGmtCreate(System.currentTimeMillis());
            commentParent.setGmtModified(commentParent.getGmtCreate());
            commentMapper.insertCommentParent(commentParent);
            questionExtMapper.incQuestionComment(id,1L);
        }else{
            //回复评论
            CommentParent parent = commentMapper.getCommentParent(id);
            if(parent == null)
            {
                throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
            }
            CommentKid kid = (CommentKid)comment;
            kid.setPid(id);
            commentMapper.insertCommentKid(kid);
            questionExtMapper.incQuestionComment(parent.getQuestionId(),1L);
        }
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public PageInfo<CommentVo> listByQuestionId(Long questionId, Integer page, Integer size) {
        Long creator = questionMapper.selectByPrimaryKey(questionId).getCreator();

        //PageHelper.startPage(page,size);
        List<CommentParent> comments = commentMapper.listComment(questionId);
        if (comments.isEmpty()) {
            return new PageInfo<>();
        }

        //将 comment 转为 commentVO
        HashMap<Long, CommentVo> isFather = new HashMap<>(10);
        List<CommentVo> commentVos = new ArrayList<>(10);
        Map<Long,User> users = getUserMap(comments);
        /*
              如果为父评论则将其加入到 commentVOList 并在 map 中标记
              否则就将评论装入到对应的父评论下
         */
        for(CommentParent parent:comments){
            CommentVo commentVo = cloneParent2Vo(parent,users,creator);
            commentVos.add(commentVo);
            isFather.put(parent.getId(), commentVo);
            for(CommentKid kid:parent.getKids()) {
                isFather.get(kid.getPid()).getSon().add(cloneKid2Vo(kid,users,creator));
            }
        }
        return new PageInfo<>(commentVos);
    }
    private CommentVo cloneParent2Vo(CommentParent parent,Map<Long,User>userMap,Long creator){
        CommentVo commentVo = new CommentVo();
        commentVo.setIsAuthor(parent.getCommentById().equals(creator));
        commentVo.setContent(parent.getContent());
        commentVo.setCommentByUser(userMap.get(parent.getCommentById()));
        commentVo.setGmtCreate(parent.getGmtCreate());
        commentVo.setLikeCount(parent.getLikeCount());
        commentVo.setSon(new LinkedList<>());
        commentVo.setId(parent.getId());
        return commentVo;
    }
    private CommentVo cloneKid2Vo(CommentKid kid,Map<Long,User>userMap,Long creator){
        CommentVo commentVo = new CommentVo();
        commentVo.setIsAuthor(kid.getCommentById().equals(creator));
        commentVo.setContent(kid.getContent());
        commentVo.setCommentByUser(userMap.get(kid.getCommentById()));
        commentVo.setGmtCreate(kid.getGmtCreate());
        return commentVo;
    }
    private Map<Long, User> getUserMap(List<CommentParent> comments){
        UserExample example = new UserExample();
        Set<Long> userIds = new HashSet<>();
        for (CommentParent parent:comments){
            for (CommentKid kid :parent.getKids()){
                userIds.add(kid.getCommentById());
            }
            userIds.add(parent.getCommentById());
        }
        example.createCriteria().andIdIn(new LinkedList<>(userIds));
        List<User>users = userMapper.selectByExample(example);
        //TODO 敏感数据置空
        return users.stream().collect(Collectors.toMap(User::getId, user->user));
    }

    public void incCommentLike(long commentId, long val) {
        commentMapper.incCommentLike(commentId,val);
    }
}
