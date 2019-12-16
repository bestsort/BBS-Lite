package cn.bestsort.bbslite.service.serviceimpl;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.TopicMapper;
import cn.bestsort.bbslite.pojo.model.Topic;
import cn.bestsort.bbslite.pojo.model.TopicExample;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO topic
 * @author bestsort
 * @date 2019/10/4 下午2:32
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TopicServiceImpl {
    private final TopicMapper topicMapper;
    public static int FOLLOW = 0;
    public static int ARTICLE = 1;


    public List<Topic> getAll(){
        return topicMapper.selectByExample(new TopicExample());
    }
    public List<Topic> getAllWithoutDefault(){
        List<Topic>topics = topicMapper.selectByExample(new TopicExample());
        topics.remove(0);
        return topics;
    }

    public Topic getById(Byte id){
        return topicMapper.selectByPrimaryKey(id);
    }

    public void updateTopic(int val,String topicName,int colType){
        TopicExample example = new TopicExample();
        example.createCriteria().andNameEqualTo(topicName);
        List<Topic> topic = topicMapper.selectByExample(example);
        if (topic.isEmpty()) {
            throw new CustomizeException(CustomizeErrorCodeEnum.USER_ERROR);
        }
        Topic topic2Db = topic.get(0);

        if(FOLLOW == colType){
            topic2Db.setFollowCount(topic2Db.getFollowCount()+val);

        }else {
            topic2Db.setArticleCount(topic2Db.getArticleCount()+val);
        }
        topicMapper.updateByExampleSelective(topic2Db,example);
    }
}
