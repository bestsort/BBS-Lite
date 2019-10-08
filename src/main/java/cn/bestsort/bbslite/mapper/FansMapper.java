package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.pojo.model.Fans;
import cn.bestsort.bbslite.pojo.model.FansExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FansMapper {
    long countByExample(FansExample example);

    int deleteByExample(FansExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Fans record);

    int insertSelective(Fans record);

    List<Fans> selectByExample(FansExample example);

    Fans selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Fans record, @Param("example") FansExample example);

    int updateByExample(@Param("record") Fans record, @Param("example") FansExample example);

    int updateByPrimaryKeySelective(Fans record);

    int updateByPrimaryKey(Fans record);
}