package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.pojo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserService
 * @Description User 的更新/新增
 * @Author bestsort
 * @Date 19-9-1 上午6:48
 * @Version 1.0
 */
@Service
@CacheConfig(cacheNames = "userCache")
public class UserService {
    @Autowired
    private UserMapper userMapper;


    @Cacheable(keyGenerator = "myKeyGenerator")
    public User getById(long id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public User getByToken(String token){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        return users.isEmpty() ? null : users.get(0);
    }

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());


        List<User> dbUser = userMapper.selectByExample(userExample);

        if (dbUser.isEmpty()){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertSelective(user);
        }else {
            User save2Db;
            save2Db = dbUser.get(0);
            save2Db.setAvatarUrl(user.getAvatarUrl());
            save2Db.setName(user.getName());
            save2Db.setToken(user.getToken());
            save2Db.setGmtModified(System.currentTimeMillis());
            userMapper.updateByPrimaryKeySelective(save2Db);
        }
    }
}
