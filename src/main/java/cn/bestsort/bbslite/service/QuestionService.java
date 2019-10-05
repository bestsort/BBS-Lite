package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.QuestionExample;
import cn.bestsort.bbslite.pojo.model.Topic;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName QuestionService
 * @Description TODO
 * @Author bestsort
 * @Date 19-10-3 下午12:33
 * @Version 1.0
 */
@CacheConfig(cacheNames = "question")
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Resource
    private CountService countService;
    private static String DEAFULT_ORDER = "gmt_create desc";


    @Cacheable(keyGenerator = "myKeyGenerator")
    public Question getByQuestionId(long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
        }
        return  question;
    }
    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<Question> listByRowBounds(QuestionExample questionExample,Integer offset, Integer size){
        questionExample.setOrderByClause(DEAFULT_ORDER);
        return questionMapper.selectByExampleWithRowbounds(
                questionExample, new RowBounds(offset,size));
    }

    public List<Question> listBySearch(String search){
        return questionExtMapper.listBySearch(search,DEAFULT_ORDER);
    }


    public List<Question> listByExample(QuestionExample example){return questionMapper.selectByExample(example);}


    @CachePut(keyGenerator = "myKeyGenerator")
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
    public Long countAll(){
        return questionMapper.countByExample(new QuestionExample());
    }

    public Long countByExample(QuestionExample questionExample){
        return questionMapper.countByExample(questionExample);
    }
}
