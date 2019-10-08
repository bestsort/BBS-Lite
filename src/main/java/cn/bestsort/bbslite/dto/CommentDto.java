package cn.bestsort.bbslite.dto;

import cn.bestsort.bbslite.pojo.model.User;
import lombok.Data;

import java.util.LinkedList;

/**
 * @ClassName CommentDto
 * @Description 评论数据传输类
 * @Author bestsort
 * @Date 19-9-16 下午8:08
 * @Version 1.0
 */

@Data
public class CommentDto {
    private Long id;
    private Long pid;
    private Byte level;
    private Long commentator;
    private Long gmtCreate;
    private Long likeCount;
    private String content;
    private User user;
    private LinkedList<CommentDto> secondaryComment;
}
