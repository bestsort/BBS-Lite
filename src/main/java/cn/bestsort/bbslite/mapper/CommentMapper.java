package cn.bestsort.bbslite.mapper;

import java.util.List;

import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.CommentKid;
import cn.bestsort.bbslite.pojo.model.CommentParent;
import cn.bestsort.bbslite.vo.CommentCenterVo;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    List<CommentParent> listComment(long articleId);
    boolean insertCommentParent(CommentParent parent);
    boolean insertCommentKid(CommentKid kid);
    CommentParent getCommentParentById(Long id);
    CommentKid getCommentKidById(Long id);
    long incCommentLike(long id, long val);
    List<CommentCenterVo> listCommentParentByUserId(Long id);
}