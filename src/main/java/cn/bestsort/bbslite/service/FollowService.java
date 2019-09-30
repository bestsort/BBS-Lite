package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.bean.model.*;
import cn.bestsort.bbslite.dao.create.FollowCreateDTO;
import cn.bestsort.bbslite.dao.mapper.FollowMapper;
import cn.bestsort.bbslite.dao.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.dao.mapper.TopicExtMapper;
import cn.bestsort.bbslite.dao.mapper.UserExtMapper;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FollowEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName FollowService
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-28 下午7:19
 * @Version 1.0
 */

@Service
public class FollowService {
    @Autowired
    FollowMapper followMapper;
    @Autowired
    TopicExtMapper topicExtMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserExtMapper userExtMapper;
    Follow follow;
    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdate(FollowCreateDTO followCreateDTO) {
        follow = new Follow();
        BeanUtils.copyProperties(followCreateDTO, follow);
        follow.setType(
                Objects.requireNonNull(FollowEnum.getKeyByValue(
                        followCreateDTO.getType()))
                        .byteValue());

        FollowExample followExample = new FollowExample();
        followExample.createCriteria()
                .andFollowByEqualTo(follow.getFollowBy())
                .andFollowToEqualTo(follow.getFollowTo())
                .andTypeEqualTo(follow.getType());

        List<Follow> follows = followMapper.selectByExample(followExample);

        follow.setGmtModified(System.currentTimeMillis());
        long val = 1;
        if (follows.isEmpty()) {
            follow.setGmtCreate(follow.getGmtModified());
            followMapper.insertSelective(follow);
        }
        else{
            follow = follows.get(0);
            val = flipStatus(follow);
            followMapper.updateByPrimaryKeySelective(follow);
        }

        if(follow.getType().intValue() == FollowEnum.USER.getCode()){
            User user = new User();
            user.setId(follow.getFollowTo());
            user.setFollowCount(val);
            userExtMapper.updateFollowCount(user);
        }else if(follow.getType().intValue() == FollowEnum.TOPIC.getCode()){
            Topic topic = new Topic();
            topic.setId(follow.getFollowTo());
            topic.setFollowCount(val);
            topicExtMapper.updateFollowCount(topic);

        }else if(follow.getType().intValue() == FollowEnum.QUESTION.getCode()){
            Question question = new Question();
            question.setFollowCount(val);
            question.setId(follow.getFollowTo());
            questionExtMapper.updateFollowCount(question);
        }
    }
    private long flipStatus(Follow follow){
        if (follow.getStatus() != null && follow.getStatus() > 0){
            follow.setStatus((byte)0);
            return -1;
        }else{
            follow.setStatus((byte)1);
            return 1;
        }
    }
}
