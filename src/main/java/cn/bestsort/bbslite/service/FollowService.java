package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.bean.model.Follow;
import cn.bestsort.bbslite.bean.model.FollowExample;
import cn.bestsort.bbslite.bean.model.Topic;
import cn.bestsort.bbslite.bean.model.TopicExample;
import cn.bestsort.bbslite.dao.mapper.FollowMapper;
import cn.bestsort.bbslite.dao.mapper.TopicExtMapper;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdate(Follow follow){
        FollowExample followExample = new FollowExample();
        followExample.createCriteria()
                .andFollowByEqualTo(follow.getFollowBy())
                .andFollowToEqualTo(follow.getFollowTo());

        List<Follow> follows = followMapper.selectByExample(followExample);
        Topic topic = new Topic();
        topic.setId(follow.getFollowTo());
        if(follows.isEmpty()) {
            follow.setGmtCreate(System.currentTimeMillis());
            follow.setGmtModified(follow.getGmtCreate());
            topic.setFollow(1L);
            topicExtMapper.updateFollow(topic);
            followMapper.insertSelective(follow);
        }else {
            follow = follows.get(0);
            if (follow.getStatus() > 0){
                follow.setStatus((byte)0);
                topic.setFollow(-1L);
            }else{
                follow.setStatus((byte)1);
                topic.setFollow(1L);
            }
            follow.setGmtModified(System.currentTimeMillis());

            followMapper.updateByPrimaryKeySelective(follow);
            topicExtMapper.updateFollow(topic);
        }
    }
}
