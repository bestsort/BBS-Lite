package cn.bestsort.bbslite.vo;

import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.User;
import lombok.Data;

/**
 * @ClassName QuestionInfoVo
 * @Description
 * @Author bestsort
 * @Date 2019/10/4 下午2:09
 * @Version 1.0
 */

@Data
public class QuestionInfoVo {
    Question question;
    User user;
}
