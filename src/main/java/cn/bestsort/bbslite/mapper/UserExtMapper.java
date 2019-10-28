package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.pojo.model.User;


public interface UserExtMapper {
    int updateFollowCount(User record);
    User selectSimpleInfoById(Long id);
}
