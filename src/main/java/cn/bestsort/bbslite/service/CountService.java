package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.mapper.QuestionCountExtMapper;
import cn.bestsort.bbslite.mapper.QuestionCountMapper;
import cn.bestsort.bbslite.pojo.model.QuestionCount;
import cn.bestsort.bbslite.pojo.model.QuestionCountExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ClassName CountSerVice
 * @Description TODO
 * @Author bestsort
 * @Date 2019/10/4 上午11:31
 * @Version 1.0
 */
@Service
@CacheConfig(cacheNames = "count")
public class CountService {
    @Autowired
    private QuestionCountExtMapper questionCountExtMapper;
    @Autowired
    private QuestionCountMapper questionCountMapper;

    @Cacheable(keyGenerator = "myKeyGenerator")
    public QuestionCount getQuestionCountById(long questionId){
        QuestionCountExample example = new QuestionCountExample();
        example.createCriteria()
                .andQuestionIdEqualTo(questionId);
        return questionCountMapper.selectByExample(example).get(0);
    }

    public void insertQuestionCount(long questionId){
        QuestionCount questionCount = new QuestionCount();
        questionCount.setGmtCreate(System.currentTimeMillis());
        questionCount.setGmtModified(questionCount.getGmtCreate());
        questionCount.setQuestionId(questionId);
        questionCountMapper.insertSelective(questionCount);
    }

    public void incQuestionView(long questionId){
        QuestionCount questionCount = new QuestionCount();
        questionCount.setView(1L);
        questionCount.setQuestionId(questionId);
        questionCountExtMapper.incViewCount(questionCount);
    }
    public void  updateQuestionCommentCount(long questionId,long val){
        QuestionCount questionCount = new QuestionCount();
        questionCount.setComment(val);
        questionCount.setQuestionId(questionId);
        questionCountExtMapper.updateCommentCount(questionCount);
    }
    public void updateQuestionFollowCount(long questionId,long val){
        QuestionCount questionCount = new QuestionCount();
        questionCount.setFollow(val);
        questionCount.setQuestionId(questionId);
        questionCountExtMapper.updateFollowCount(questionCount);
    }
}
