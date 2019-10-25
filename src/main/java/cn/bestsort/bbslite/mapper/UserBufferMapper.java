package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.pojo.model.UserBuffer;
import cn.bestsort.bbslite.pojo.model.UserBufferExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBufferMapper {
    long countByExample(UserBufferExample example);

    int deleteByExample(UserBufferExample example);

    int insert(UserBuffer record);

    int insertSelective(UserBuffer record);

    List<UserBuffer> selectByExample(UserBufferExample example);

    int updateByExampleSelective(@Param("record") UserBuffer record, @Param("example") UserBufferExample example);

    int updateByExample(@Param("record") UserBuffer record, @Param("example") UserBufferExample example);
}