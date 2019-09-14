package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CommentTypeEnum;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.CommentMapper;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.model.Comment;
import cn.bestsort.bbslite.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CommentService
 * @Description 评论处理Service层
 * @Author bestsort
 * @Date 19-9-13 下午4:57
 * @Version 1.0
 */

@Service
public class CommentService {

    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
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
}
