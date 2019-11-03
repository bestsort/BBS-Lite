package cn.bestsort.bbslite.mapper;

import java.util.List;

import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.CommentKid;
import cn.bestsort.bbslite.pojo.model.CommentParent;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    List<CommentParent> listComment(long questionId);
    boolean insertCommentParent(CommentParent parent);
    boolean insertCommentKid(CommentKid kid);
    CommentParent getCommentParent(Long id);
    CommentKid getCommentKid(Long id);

    long incCommentLike(long id, long val);

    List<Comment> listCommentByUserId(Long id);
}