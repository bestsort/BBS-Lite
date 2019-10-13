package cn.bestsort.bbslite.vo;

import lombok.Data;

/**
 * @ClassName QuestionDetailOptionVo
 * @Description TODO
 * @Author bestsort
 * @Date 2019/10/13 上午9:23
 * @Version 1.0
 */

@Data
public class QuestionDetailOptionVo {
    private Boolean isThumbUpQuestion;
    private Boolean isFollowQuestion;
    private Boolean isFollowUser;
    private Boolean isCreator;
}
