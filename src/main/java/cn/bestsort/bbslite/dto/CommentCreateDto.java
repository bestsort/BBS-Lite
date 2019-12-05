package cn.bestsort.bbslite.dto;

import lombok.Data;

/**
 * Comment DTO
 * @author bestsort
 * @date 19-9-13 下午4:00
 * @version 1.0
 */

@Data
public class CommentCreateDto {
    private Long pid;
    private Byte level;
    private Long commentator;
    private String content;
    private Long articleId;
    private String userAvatarUrl;
}
