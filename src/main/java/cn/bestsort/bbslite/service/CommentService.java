package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.*;
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
    @Autowired
    private ThumbUpMapper thumbUpMapper;
    public List<Comment> listByUserId(Long id){
        return commentMapper.listCommentByUserId(id);
    }
    @CachePut(keyGenerator = "myKeyGenerator")
    public void insert(Comment comment,Long pid,boolean isParent,Long userId) {
        if(isParent){
            CommentParent commentParent = (CommentParent) comment;
            commentParent.setQuestionId(pid);
            if (questionMapper.selectByPrimaryKey(pid) == null){
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            commentParent.setGmtCreate(System.currentTimeMillis());
            commentParent.setGmtModified(commentParent.getGmtCreate());
            commentMapper.insertCommentParent(commentParent);
            questionExtMapper.incQuestionComment(pid,1L);
        }else{
            if (userMapper.selectByPrimaryKey(userId) == null){
                throw new CustomizeException(CustomizeErrorCodeEnum.URL_NOT_FOUND);
            }
            //回复评论
            CommentParent parent = commentMapper.getCommentParent(pid);
            if(parent == null)
            {
                throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
            }
            CommentKid kid = (CommentKid)comment;
            kid.setGmtCreate(System.currentTimeMillis());
            kid.setGmtModified(kid.getGmtCreate());
            kid.setPid(pid);
            kid.setCommentToUserId(userId);
            commentMapper.insertCommentKid(kid);
            questionExtMapper.incQuestionComment(parent.getQuestionId(),1L);
        }
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public PageInfo<CommentVo> listByQuestionId(Long questionId,Long userId, Integer page, Integer size) {
        Long creator = questionMapper.selectByPrimaryKey(questionId).getCreator();

        //PageHelper.startPage(page,size);
        List<CommentParent> comments = commentMapper.listComment(questionId);
        if (comments.isEmpty()) {
            return new PageInfo<>();
        }

        //将 comment 转为 commentVO
        HashMap<Long, CommentVo> isFather = new HashMap<>(10);
        List<CommentVo> commentVos = new ArrayList<>(10);
        Map<Long,User> users = getUserMapByComments(comments);
        /*
              如果为父评论则将其加入到 commentVOList 并在 map 中标记
              否则就将评论装入到对应的父评论下
         */
        Map<Long, Boolean> isThumb =new HashMap<>();
        if(userId != null) {
            isThumb = getThumbUpMap(comments, userId);
        }
        for(CommentParent parent:comments){
            CommentVo commentVo = cloneParent2Vo(parent,users,creator,isThumb,userId);
            commentVos.add(commentVo);
            isFather.put(parent.getId(), commentVo);
            for(CommentKid kid:parent.getKids()) {
                isFather.get(kid.getPid()).getSon().add(cloneKid2Vo(kid,users,creator));
            }
        }
        return new PageInfo<>(commentVos);
    }

    /**
     * 将 CommentParent 转换为 CommentVo,并且校验该用户是否为问题的作者
     * @param parent
     * @param userMap
     * @param creator
     * @param isThumb
     * @param userId
     * @return 转换后的 CommentVo
     */
    private CommentVo cloneParent2Vo(CommentParent parent,Map<Long,User>userMap,Long creator,Map<Long,Boolean> isThumb,Long userId){
        CommentVo commentVo = new CommentVo();
        commentVo.setIsActive(isThumb.get(parent.getId())!=null);
        commentVo.setContent(parent.getContent());
        commentVo.setCommentByUser(userMap.get(parent.getCommentById()));
        commentVo.setGmtCreate(parent.getGmtCreate());
        commentVo.setLikeCount(parent.getLikeCount());
        commentVo.setSon(new LinkedList<>());
        commentVo.setId(parent.getId());
        return commentVo;
    }

    /**
     * 将 CommentKid 转换为 CommentVo,并且校验该用户是否为问题的作者
     * @param kid
     * @param userMap
     * @param creator
     * @return 转换后的 CommentVo
     */
    private CommentVo cloneKid2Vo(CommentKid kid,Map<Long,User>userMap,Long creator){
        CommentVo commentVo = new CommentVo();
        commentVo.setContent(kid.getContent());
        commentVo.setCommentByUser(userMap.get(kid.getCommentById()));
        commentVo.setGmtCreate(kid.getGmtCreate());
        commentVo.setCommentToUser(userMap.get(kid.getCommentToUserId()));
        return commentVo;
    }

    /**
     * 将评论列表中的 user 进行去重处理
     * @param comments
     * @return Map: userId <--> userInfo
     */
    private Map<Long, User> getUserMapByComments(List<CommentParent> comments){
        UserExample example = new UserExample();
        Set<Long> userIds = new HashSet<>();
        for (CommentParent parent:comments){
            for (CommentKid kid :parent.getKids()){
                userIds.add(kid.getCommentById());
                userIds.add(kid.getCommentToUserId());
            }
            userIds.add(parent.getCommentById());
        }
        example.createCriteria().andIdIn(new LinkedList<>(userIds));
        List<User>users = userMapper.selectByExample(example);
        //TODO 敏感数据置空
        return users.stream().collect(Collectors.toMap(User::getId, user->user));
    }

    /**
     * 判断当前用户是否对某条评论进行点赞
     * @param comments
     * @param userId
     * @return Map: commentId <--> isThumbUp
     */
    private Map<Long,Boolean> getThumbUpMap(List<CommentParent> comments,Long userId){

        Set<Long> ids = comments.stream().map(CommentParent::getId).collect(Collectors.toSet());
        ThumbUpExample thumbUpExample = new ThumbUpExample();
        thumbUpExample.createCriteria().andThumbUpByEqualTo(userId)
                .andTypeEqualTo(FunctionItem.getCode(FunctionItem.COMMENT))
                .andStatusEqualTo((byte) 1)
                .andThumbUpToIn(new LinkedList<>(ids));
        return thumbUpMapper.selectByExample(thumbUpExample).stream().collect(Collectors.toMap(ThumbUp::getThumbUpTo,thumb->true));
    }

    public void incCommentLike(long commentId, long val) {
        commentMapper.incCommentLike(commentId,val);
    }
}
