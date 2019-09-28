package cn.bestsort.bbslite.dao.dto;

import cn.bestsort.bbslite.bean.model.User;
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
    private Long id;
    private String title;
    private String description;
    private String tag;
    private String topic;
    private String category;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
