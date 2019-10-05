package cn.bestsort.bbslite.pojo.vo;

import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.QuestionCount;
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
    QuestionCount questionCount;
    User user;
    Question question;
}
