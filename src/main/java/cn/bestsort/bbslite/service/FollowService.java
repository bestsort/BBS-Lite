package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.FollowEnum;
import cn.bestsort.bbslite.mapper.FollowMapper;
import cn.bestsort.bbslite.pojo.model.Follow;
import cn.bestsort.bbslite.pojo.model.FollowExample;
import cn.bestsort.bbslite.pojo.vo.FollowVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName FollowService
 * @Description
 * @Author bestsort
 * @Date 19-9-28 下午7:19
 * @Version 1.0
 */

@Service
public class FollowService {
    @Autowired
    private FollowMapper followMapper;
    @Resource
    private CountService countService;

    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdate(FollowVo followCreateDTO) {
        Follow follow = new Follow();
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
        } else {
            follow = follows.get(0);
            val = flipStatus(follow);
            followMapper.updateByPrimaryKeySelective(follow);
        }
        /*
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
                }else */
        if (follow.getType().intValue() == FollowEnum.QUESTION.getCode()) {
            countService.updateQuestionFollowCount(follow.getFollowTo(), val);
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
