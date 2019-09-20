package cn.bestsort.bbslite.dto;

import lombok.Data;

/**
 * @ClassName CommentCreateDTO
 * @Description Comment DTO
 * @Author bestsort
 * @Date 19-9-13 下午4:00
 * @Version 1.0
 */

@Data
public class CommentCreateDTO {
    private Long pid;
    private Byte level;
    private Integer commentator;
    private String content;
    private Long questionId;
}
