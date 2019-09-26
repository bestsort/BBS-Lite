package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.dto.CommentDTO;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.CommentMapper;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
            question.setCommentCount(1);
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

    public List<CommentDTO> listByQuestionId(Long questionId) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andQuestionIdEqualTo(questionId);
        commentExample.setOrderByClause("gmt_create desc");

        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        //获取去重后的评论人id

        List<Long> userIds = comments.stream()
                .map(Comment::getCommentator)
                .distinct()
                .collect(Collectors.toList());

        //获取去重后的评论人信息并将其映射为Map, Key为id, Value为User信息
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(User::getId, user->user));


        //将comments 先按照评论分级排序再按照时间排序
        comments.sort(new CommentComparator());


        //将 comment 转为 commentDTO
        HashMap<Long,CommentDTO> dtoHashMap = new HashMap<>();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment:comments){
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
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
