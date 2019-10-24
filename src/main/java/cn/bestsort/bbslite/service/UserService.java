package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.pojo.model.UserExample;
import cn.bestsort.bbslite.util.MurmursHash;
import cn.bestsort.bbslite.vo.UserCreateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
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

    public User createOrUpdate(User user) {
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
        userExample.clear();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        return userMapper.selectByExample(userExample).get(0);
    }

    public boolean hasCreateUser(UserCreateVo userCreateVo){
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(userCreateVo.getAccountId());
        return userMapper.selectByExample(example) != null;
    }
    public User activateUser(String token, String account) throws Exception{
        UserExample example = new UserExample();
        example.createCriteria().andTokenEqualTo(token).andAccountIdEqualTo(account);
        User user = userMapper.selectByExample(example).get(0);
        assert user.getToken().equals(MurmursHash.hashUnsigned(token));
        user.setAuthenticated((byte)1);
        userMapper.updateByPrimaryKey(user);
        return user;
    }
    public int clearUnActivateUser(){
        UserExample example = new UserExample();
        example.createCriteria().andAuthenticatedEqualTo((byte)0);
        return userMapper.deleteByExample(example);
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
