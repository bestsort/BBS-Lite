package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.model.Question;

/**
 * @ClassName QuestionExtMapper
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-13 下午1:39
 * @Version 1.0
 */

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}
