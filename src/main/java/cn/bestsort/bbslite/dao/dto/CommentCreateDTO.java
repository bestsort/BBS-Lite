package cn.bestsort.bbslite.dao.dto;

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
    private Long commentator;
    private String content;
    private Long questionId;
}
