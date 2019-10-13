package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.mapper.FollowMapper;
import cn.bestsort.bbslite.pojo.model.Follow;
import cn.bestsort.bbslite.pojo.model.FollowExample;
import cn.bestsort.bbslite.pojo.model.ThumbUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Integer getFollowById(){
        return null;
    }
    @Transactional(rollbackFor = Exception.class)
    public Integer insertOrUpdate(Follow follow) {
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
            return followMapper.insertSelective(follow);
        } else {
            follow = follows.get(0);
            val = flipStatus(follow);
            return followMapper.updateByPrimaryKeySelective(follow);
        }
    }
    public  Boolean isFollowed(Long by, Long to, FunctionItem item){
        FollowExample example = new FollowExample();
        example.createCriteria()
                .andFollowByEqualTo(by)
                .andFollowToEqualTo(to)
                .andTypeEqualTo(item.getCode().byteValue())
                .andStatusEqualTo((byte) 1);
        List<Follow> follows = followMapper.selectByExample(example);
        return !follows.isEmpty();
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
