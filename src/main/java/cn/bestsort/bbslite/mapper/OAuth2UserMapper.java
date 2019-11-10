package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.pojo.model.OAuth2User;
import cn.bestsort.bbslite.pojo.model.OAuth2UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OAuth2UserMapper {
    long countByExample(OAuth2UserExample example);

    int deleteByExample(OAuth2UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OAuth2User record);

    int insertSelective(OAuth2User record);

    List<OAuth2User> selectByExample(OAuth2UserExample example);

    OAuth2User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OAuth2User record, @Param("example") OAuth2UserExample example);

    int updateByExample(@Param("record") OAuth2User record, @Param("example") OAuth2UserExample example);

    int updateByPrimaryKeySelective(OAuth2User record);

    int updateByPrimaryKey(OAuth2User record);
}