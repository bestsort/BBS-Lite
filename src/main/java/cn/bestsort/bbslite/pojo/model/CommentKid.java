package cn.bestsort.bbslite.pojo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentKid extends Comment {
    private Long pid;
    private Long commentToUserId;
}
