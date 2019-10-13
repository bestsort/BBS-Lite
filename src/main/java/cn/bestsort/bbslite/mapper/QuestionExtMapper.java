package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.dto.QuestionQueryDto;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.QuestionExample;

import java.util.List;

/**
 * @ClassName QuestionExtMapper
 * @Description 对于 question 表相关内容的扩展封装,实现了一些将要用到而 Myatis 不能自动生成的功能:
 *      incView : 按照权值增加浏览数(权值为 record.viewCount 默认为1)
 *      incCommentCount : 按照权值增加评论数,增加 record.viewCount 默认为1)
 * @Author bestsort
 * @Date 19-9-13 下午1:39
 * @Version 1.0
 */

public interface QuestionExtMapper {
    List<Question> listBySearch(QuestionQueryDto questionQueryDto);
    Long insertQuestionExt(Question question);
}
