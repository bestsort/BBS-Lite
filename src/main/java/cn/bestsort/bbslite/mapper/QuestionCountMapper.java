package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.pojo.model.QuestionCount;
import cn.bestsort.bbslite.pojo.model.QuestionCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QuestionCountMapper {
    long countByExample(QuestionCountExample example);

    int deleteByExample(QuestionCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(QuestionCount record);

    int insertSelective(QuestionCount record);

    List<QuestionCount> selectByExampleWithRowbounds(QuestionCountExample example, RowBounds rowBounds);

    List<QuestionCount> selectByExample(QuestionCountExample example);

    QuestionCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QuestionCount record, @Param("example") QuestionCountExample example);

    int updateByExample(@Param("record") QuestionCount record, @Param("example") QuestionCountExample example);

    int updateByPrimaryKeySelective(QuestionCount record);

    int updateByPrimaryKey(QuestionCount record);
}