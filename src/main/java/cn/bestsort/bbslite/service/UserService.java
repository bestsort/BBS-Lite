package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.mapper.UserBufferMapper;
import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.pojo.model.UserBuffer;
import cn.bestsort.bbslite.pojo.model.UserBufferExample;
import cn.bestsort.bbslite.pojo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${bbs.user.default.avatar-url}")
    String defaultAvatarUrl;
    @Value("${bbs.user.default.bio}")
    String defaultBio;
    @Value("${bbs.user.default.nickname}")
    String defaultNickname;


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserBufferMapper userBufferMapper;

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

    public User createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());


        List<User> dbUser = userMapper.selectByExample(userExample);

        if (dbUser.isEmpty()){
            user.setBio(defaultBio);
            user.setAvatarUrl(defaultAvatarUrl);
            user.setName(defaultNickname);
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
        userExample.clear();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        return userMapper.selectByExample(userExample).get(0);
    }
    public void signUpUser(UserBuffer userBuffer){
        userBufferMapper.insertSelective(userBuffer);
    }
    public boolean hasCreateUser(UserBuffer userCreateVo){
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(userCreateVo.getAccountId());
        return userMapper.selectByExample(example) != null;
    }
    public User activateUser(String token, String account){
        UserBufferExample example = new UserBufferExample();
        example.createCriteria().andAccountIdEqualTo(account)
                .andTokenEqualTo(token);
        UserBuffer buffer = userBufferMapper.selectByExample(example).get(0);
        User user = new User();
        user.setPassword(buffer.getPassword());
        user.setAccountId(buffer.getAccountId());
        user.setToken(buffer.getToken());
        user.setEmail(buffer.getEmail());
        return createOrUpdate(user);
    }
    public int clearUnActivateUser(){
        return userBufferMapper.deleteByExample(new UserBufferExample());
    }
    public User getSimpleInfoById(Long id) {
        User user = getById(id);
        if(user != null){
            user.setPassword(null);
            user.setToken(null);
        }
        return user;
    }
}
