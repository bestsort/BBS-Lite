package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.TopicMapper;
import cn.bestsort.bbslite.pojo.model.Topic;
import cn.bestsort.bbslite.pojo.model.TopicExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TopicServices
 * @Description TODO topic
 * @Author bestsort
 * @Date 2019/10/4 下午2:32
 * @Version 1.0
 */
@Service
@CacheConfig(cacheNames = "topicCache")
public class TopicService {
    @Autowired
    private TopicMapper topicMapper;
    public static int FOLLOW = 0;
    public static int ARTICLE = 1;


    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<Topic> getAll(){
        return topicMapper.selectByExample(new TopicExample());
    }
    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<Topic> getAllWithoutDefault(){
        List<Topic>topics = topicMapper.selectByExample(new TopicExample());
        topics.remove(0);
        return topics;
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public Topic getById(long id){
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
            topic2Db.setQuestionCount(topic2Db.getQuestionCount()+val);
        }
        topicMapper.updateByExampleSelective(topic2Db,example);
    }
}
