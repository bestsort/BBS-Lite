package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-1 上午6:48
 * @Version 1.0
 */

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccount(user.getAccountId());
        if (dbUser == null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setGmtModified(System.currentTimeMillis());

            userMapper.update(dbUser);
        }
    }
}
