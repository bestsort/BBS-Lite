package cn.bestsort.bbslite.dao.mapper;

import cn.bestsort.bbslite.bean.model.Follow;
import cn.bestsort.bbslite.bean.model.FollowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface FollowMapper {
    long countByExample(FollowExample example);

    int deleteByExample(FollowExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Follow record);

    int insertSelective(Follow record);

    List<Follow> selectByExampleWithRowbounds(FollowExample example, RowBounds rowBounds);

    List<Follow> selectByExample(FollowExample example);

    Follow selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Follow record, @Param("example") FollowExample example);

    int updateByExample(@Param("record") Follow record, @Param("example") FollowExample example);

    int updateByPrimaryKeySelective(Follow record);

    int updateByPrimaryKey(Follow record);
}