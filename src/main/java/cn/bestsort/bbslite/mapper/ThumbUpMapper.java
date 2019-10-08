package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.pojo.model.ThumbUp;
import cn.bestsort.bbslite.pojo.model.ThumbUpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThumbUpMapper {
    long countByExample(ThumbUpExample example);

    int deleteByExample(ThumbUpExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ThumbUp record);

    int insertSelective(ThumbUp record);

    List<ThumbUp> selectByExample(ThumbUpExample example);

    ThumbUp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ThumbUp record, @Param("example") ThumbUpExample example);

    int updateByExample(@Param("record") ThumbUp record, @Param("example") ThumbUpExample example);

    int updateByPrimaryKeySelective(ThumbUp record);

    int updateByPrimaryKey(ThumbUp record);
}