package cn.bestsort.bbslite.vo;

import cn.bestsort.bbslite.pojo.model.User;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CommentDto
 * @Description 评论数据传输类
 * @Author bestsort
 * @Date 19-9-16 下午8:08
 * @Version 1.0
 */

@Data
public class CommentVo {
    private Long id;
    private Long pid;
    private Long commentTo;
    private Long commentBy;
    private Boolean isAuthor;
    private Long gmtCreate;
    private Long likeCount;
    private String content;
    private User user;
    private List<CommentVo> son;
}
