package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.CommentMapper;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.pojo.dto.CommentDto;
import cn.bestsort.bbslite.pojo.model.*;
import org.springframework.beans.BeanUtils;
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
@CacheConfig(cacheNames = {"comment"})
@Service
public class CommentService {

    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @CachePut(keyGenerator = "keyGenerator")
    public void insert(Comment comment) {
        if (comment.getPid() == null || comment.getPid()==0) {
            throw new CustomizeException(CustomizeErrorCodeEnum.TARGET_PAI_NOT_FOUND);
        }
        if(comment.getLevel() <= 1){
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getQuestionId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1L);
            questionExtMapper.incCommentCount(question);

        }else{
            //回复评论
            Comment dbComment =commentMapper.selectByPrimaryKey(comment.getPid());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }
    }

    @Cacheable(keyGenerator = "keyGenerator")
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
        HashMap<Long, CommentDto> dtoHashMap = new HashMap<>();
        List<CommentDto> commentDTOList = new ArrayList<>();
        for(Comment comment:comments){
            CommentDto commentDTO = new CommentDto();
            BeanUtils.copyProperties(comment,commentDTO);
            User user = new User();
            user.setId(comment.getCommentator());
            user.setAvatarUrl(comment.getUserAvatarUrl());
            commentDTO.setUser(user);
            if(comment.getLevel() <= 1) {
                // 如果为父评论则将其加入到 commentDTOList 并在 map 中标记
                dtoHashMap.put(comment.getId(), commentDTO);
                commentDTOList.add(commentDTO);
            }
            else{
                // 否则就将评论装入到对应的父评论下
                dtoHashMap.get(comment.getPid())
                        .getSecondaryComment()
                        .add(commentDTO);
            }
        }
        return commentDTOList;
    }
    private static class CommentComparator implements Comparator<Comment>{
        @Override
        public int compare(Comment o1, Comment o2) {
            long res = 0;
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
