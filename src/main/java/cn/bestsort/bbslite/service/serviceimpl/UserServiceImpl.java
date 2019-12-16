package cn.bestsort.bbslite.service.serviceimpl;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.OAuth2UserMapper;
import cn.bestsort.bbslite.mapper.UserBufferMapper;
import cn.bestsort.bbslite.mapper.ext.UserExtMapper;
import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.pojo.model.*;
import cn.bestsort.bbslite.util.CopyAuth2User;
import cn.bestsort.bbslite.util.MurmursHash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * User 的更新/新增
 * @author bestsort
 * @date 19-9-1 上午6:48
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl {
    @Value("${bbs.user.default.avatar-url:}")
    String defaultAvatarUrl;
    @Value("${bbs.user.default.bio:这人太懒了,没有留下简介}")
    String defaultBio;
    @Value("${bbs.user.default.nickname:无名氏}")
    String defaultNickname;
    private final OAuth2UserMapper oAuth2UserMapper;
    private final UserExtMapper userExtMapper;
    private final UserMapper userMapper;
    private final UserBufferMapper userBufferMapper;

    public User getById(long id){
        return userMapper.selectByPrimaryKey(id);
    }

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
            if(user.getBio() == null) {
                user.setBio(defaultBio);
            }
            if (user.getAvatarUrl() == null) {
                user.setAvatarUrl(defaultAvatarUrl);
            }
            if (user.getName() == null) {
                user.setName(defaultNickname);
            }
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
        UserBufferExample bufferExample = new UserBufferExample();
        bufferExample.createCriteria().andAccountIdEqualTo(userCreateVo.getAccountId());
        return !(userMapper.selectByExample(example).isEmpty()
                &&userBufferMapper.selectByExample(bufferExample).isEmpty());
    }
    public User loginByAccount(String account,String password,String type){
        User user = null;
        UserExample example = new UserExample();
        if("email".equals(type)){
            example.createCriteria().andEmailEqualTo(account)
                    .andPasswordEqualTo(MurmursHash.hashUnsignedWithSalt(password));
        }
        else if ("account".equals(type)) {
            example.createCriteria().andAccountIdEqualTo(account)
                    .andPasswordEqualTo(MurmursHash.hashUnsignedWithSalt(password));
        }else {
            throw new CustomizeException(CustomizeErrorCodeEnum.NO_WAY);
        }
        List<User> users = userMapper.selectByExample(example);
        if (!users.isEmpty()) {
            user = userMapper.selectByExample(example).get(0);
        }
        return user;
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
        user = createOrUpdate(user);
        log.info("User activation succeeded,The account information is: {}",user);
        return user;
    }
    public int clearUnActivateUser(){
        return userBufferMapper.deleteByExample(new UserBufferExample());
    }
    public User getSimpleInfoById(Long id) {
        return userExtMapper.selectSimpleInfoById(id);
    }
    public User createOrUpdateAuth(OAuth2User auth2User) {
        auth2User.setGmtCreated(System.currentTimeMillis());
        auth2User.setGmtModified(auth2User.getGmtCreated());
        CopyAuth2User copyAuth2User = new CopyAuth2User();
        User user = copyAuth2User.copy2User(auth2User);
        userExtMapper.insertUserExt(user);
        user.setToken(UUID.randomUUID().toString());
        auth2User.setUserId(user.getId());
        oAuth2UserMapper.insertSelective(auth2User);
        return user;
    }

    public User queryOauthUser(OAuth2User oauth2User) {
        OAuth2UserExample example = new OAuth2UserExample();
        example.createCriteria()
                .andUuidEqualTo(oauth2User.getUuid())
                .andSourceEqualTo(oauth2User.getSource());
        List<OAuth2User> list = oAuth2UserMapper.selectByExample(example);
        OAuth2User user = null;
        if (!list.isEmpty()) {
            user = list.get(0);
        }
        if (user != null){
            return userMapper.selectByPrimaryKey(user.getUserId());
        }
        return null;
    }
}
