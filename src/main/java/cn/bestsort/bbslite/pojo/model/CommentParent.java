package cn.bestsort.bbslite.pojo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author bestsort
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentParent extends Comment{
    private Long articleId;
    private Long likeCount;
    private List<CommentKid> kids;
}
