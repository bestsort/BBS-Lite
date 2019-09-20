package cn.bestsort.bbslite.dto;

import cn.bestsort.bbslite.model.User;
import lombok.Data;

/**
 * @ClassName CommentDTO
 * @Description 评论数据传输类
 * @Author bestsort
 * @Date 19-9-16 下午8:08
 * @Version 1.0
 */

@Data
public class CommentDTO {
    private Long id;
    private Long pid;
    private Byte type;
    private Long commentator;
    private Long gmtCreate;
    private Long likeCount;
    private String content;
    private User user;
}
