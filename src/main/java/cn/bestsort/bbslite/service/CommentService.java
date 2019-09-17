package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.dto.CommentDTO;
import cn.bestsort.bbslite.enums.CommentTypeEnum;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName CommentService
 * @Description 评论处理Service层.
 * @Author bestsort
 * @Date 19-9-13 下午4:57
 * @Version 1.0
 * @Notice 待修复: 当comment输入内容长度 >1000 的时候会报错.
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
    @Transactional
    public void insert(Comment comment) {
        if (comment.getPid() == null || comment.getPid()==0) {
            throw new CustomizeException(CustomizeErrorCodeEnum.TARGET_PAI_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new  CustomizeException((CustomizeErrorCodeEnum.TYPE_PARAM_WRONG));
        }

        if(comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            //回复评论
            Comment dbComment =commentMapper.selectByPrimaryKey(comment.getPid());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getPid());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }

    public List<CommentDTO> listByQuestionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andPidEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }

        //获取去重后的评论人id
        Set<Long>commentators = comments.stream().map(Comment::getCommentator)
                .collect(Collectors.toSet());

        List<Long>userIds = new ArrayList();
        userIds.addAll(commentators);

        //获取去重后的评论人信息并将其映射为Map, Key为id, Value为User信息
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(User::getId, user->user));

        //将 comment 转为 commentDTO
        List<CommentDTO> commentDTOList = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOList;
    }
}
