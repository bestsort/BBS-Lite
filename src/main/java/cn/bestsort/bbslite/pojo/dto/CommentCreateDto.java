package cn.bestsort.bbslite.pojo.dto;

import lombok.Data;

/**
 * @ClassName CommentCreateDto
 * @Description Comment DTO
 * @Author bestsort
 * @Date 19-9-13 下午4:00
 * @Version 1.0
 */

@Data
public class CommentCreateDto {
    private Long pid;
    private Byte level;
    private Long commentator;
    private String content;
    private Long questionId;
}
