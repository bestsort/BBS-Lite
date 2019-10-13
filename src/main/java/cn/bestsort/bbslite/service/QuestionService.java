package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.dto.QuestionQueryDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.QuestionExample;
import cn.bestsort.bbslite.enums.SortBy;
import cn.bestsort.bbslite.pojo.model.Topic;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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


    public PageInfo<Question> findQuestionListByCategory(int page,int size,int category){
        QuestionExample example = new QuestionExample();
        example.setOrderByClause(SortBy.DEAFULT_ORDER);

        PageHelper.startPage(page,size);
        List<Question> questions = questionMapper.selectByExample(example);
        return new PageInfo<>(questions);
    }

    public Long createOrUpdate(Question question) {
        question.setGmtModified(System.currentTimeMillis());
        Long result = null;
        if(question.getId() == null){
            question.setGmtCreate(question.getGmtModified());

            result = questionExtMapper.insertQuestionExt(question);
        }else {
            int updated = questionMapper.updateByPrimaryKeySelective(question);
            if(updated != 1) {
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }

            //topicExtMapper.incQuestion(topic);
        }
        return result;
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

    public PageInfo<Question> getPageBySearch(QuestionQueryDto queryDto) {
        List<Question> questions;
        PageHelper.startPage(queryDto.getPageNo(),queryDto.getPageSize());
        questions = questionExtMapper.listBySearch(queryDto);
        return new PageInfo<>(questions);
    }

    public Question getQuestionDetail(Long id) {
        return questionMapper.selectByPrimaryKey(id);
    }
}
