package cn.bestsort.bbslite.vo;

import lombok.Data;

@Data
public class CommentCenterVo {
    private Long commentToId;
    private String commentToTitle;
    private Long commentTime;
    private String commentContent;
}
