package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.ItemTypeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.QuestionExample;
import cn.bestsort.bbslite.pojo.model.Topic;
import cn.bestsort.bbslite.pojo.vo.QuestionInfoVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName QuestionService
 * @Description
 * @Author bestsort
 * @Date 19-10-3 下午12:33
 * @Version 1.0
 */
@CacheConfig(cacheNames = "questionCache")
@Service
public class QuestionService {
    public static int SEARCH = 1;
    public static int TOPIC = 2;
    public static int USER = 3;
    public static int ALL = 4;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Resource
    private TopicService topicService;
    @Resource
    private CountService countService;
    @Resource
    private UserService userService;

    private static String DEAFULT_ORDER = "gmt_create desc";


    @Cacheable(keyGenerator = "myKeyGenerator")
    public QuestionInfoVo getVoByQuestionId(long id) {
        QuestionInfoVo result = new QuestionInfoVo();
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
        }

        result.setQuestion(question);
        result.setQuestionCount(countService.getQuestionCountById(question.getId()));
        result.setUser(userService.getById(question.getCreator()));

        return  result;
    }
    public Question getByQuestionId(long id){
        return questionMapper.selectByPrimaryKey(id);
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<Question> listByRowBounds(Object key,long page,long size,long type){
        QuestionExample example = new QuestionExample();
        long offset;
        long totalCount;
        List<Question> questions;
        if(type == SEARCH){
            questions = questionExtMapper.listBySearch(key.toString(),DEAFULT_ORDER);
            totalCount = questions.size();
            page = Math.min(totalCount /size + (totalCount %size==0? 0 : 1),page);
            page = Math.max(page,1);
        }else{
            if(type == TOPIC) {
                example.createCriteria().andTopicEqualTo(key.toString());
            }
            else if (type == USER){
                example.createCriteria().andCreatorEqualTo((long)key);
            }
            totalCount = questionMapper.countByExample(example);
            //限制访问合法
            page = Math.min(totalCount /size + (totalCount %size==0? 0L : 1L),page);
            page = Math.max(page,1);
            offset = size * (page - 1);
            example.setOrderByClause(DEAFULT_ORDER);
            questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds((int) offset, (int) size));
        }
        return questions;
    }

    public List<Question> listBySearch(String search){
        return questionExtMapper.listBySearch(search,DEAFULT_ORDER);
    }


    @CacheEvict
    public void createOrUpdate(Question question) {
        question.setGmtModified(System.currentTimeMillis());
        if(questionMapper.selectByPrimaryKey(question.getId()) == null){
            question.setGmtCreate(question.getGmtModified());
            questionMapper.insertSelective(question);
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andGmtCreateEqualTo(question.getGmtCreate())
                    .andCreatorEqualTo(question.getCreator())
                    .andTitleEqualTo(question.getTitle())
                    .andTagEqualTo(question.getTag());

            Question question1 = questionMapper.selectByExample(example).get(0);
            countService.insertQuestionCount(question1.getId());
        }else {
            int updated = questionMapper.updateByPrimaryKeySelective(question);
            if(updated != 1) {
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            Topic topic = new Topic();
            topic.setName(question.getTopic());
            //topicExtMapper.incQuestion(topic);
        }
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public long countByType(int type,Object key){
        QuestionExample example = new QuestionExample();
        if(type == TOPIC) {
            example.createCriteria().andTopicEqualTo(key.toString());
        }
        else if (type == USER){
            example.createCriteria().andCreatorEqualTo((long)key);
        }
        return questionMapper.countByExample(example);
    }
}
