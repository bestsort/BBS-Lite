package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.pojo.dto.QuestionDto;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.QuestionExample;
import cn.bestsort.bbslite.pojo.model.Topic;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
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
    QuestionMapper questionMapper;
    @Autowired

    QuestionExtMapper questionExtMapper;
    @Resource
    UserService userService;

    private static String DEAFULT_ORDER = "gmt_create desc";
    @Cacheable(keyGenerator = "myKeyGenerator")
    public QuestionDto getByQuestionId(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDTO = new QuestionDto();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(userService.getById(question.getCreator()));
        return  questionDTO;
    }


    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<Question> listByRowBounds(Integer offset, Integer size){
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause(DEAFULT_ORDER);
        return questionMapper.selectByExampleWithRowbounds(
                questionExample, new RowBounds(offset,size));
    }

    public List<Question> listBySearch(String search){
        return questionExtMapper.listBySearch(search,DEAFULT_ORDER);
    }
    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<Question> listByUserId(Long userId,Integer offset, Integer size){
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        questionExample.setOrderByClause(DEAFULT_ORDER);
        return questionMapper.selectByExampleWithRowbounds(
                questionExample, new RowBounds(offset,size));
    }

    @CachePut(keyGenerator = "myKeyGenerator")
    public void createOrUpdate(Question question) {
        question.setGmtModified(System.currentTimeMillis());
        if(questionMapper.selectByPrimaryKey(question.getId()) == null){
            question.setGmtCreate(question.getGmtModified());
            questionMapper.insertSelective(question);
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
    public Long countByUserId(Long userId){
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        return questionMapper.countByExample(questionExample);
    }
}
