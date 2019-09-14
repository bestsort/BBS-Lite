package cn.bestsort.bbslite.dto;

import lombok.Data;

/**
 * @ClassName CommentDTO
 * @Description Comment DTO
 * @Author bestsort
 * @Date 19-9-13 下午4:00
 * @Version 1.0
 */

@Data
public class CommentDTO {
    private Long pid;
    private Byte type;
    private Integer commentator;
    private String content;
}
