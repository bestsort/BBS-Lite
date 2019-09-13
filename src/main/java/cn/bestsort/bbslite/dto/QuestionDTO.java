package cn.bestsort.bbslite.dto;

import cn.bestsort.bbslite.model.User;
import lombok.Data;

/**
 * @ClassName QuestionDTO
 * @Description 问题详情
 * @Author bestsort
 * @Date 19-8-28 下午6:28
 * @Version 1.0
 */

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
