package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.mapper.ThumbUpMapper;
import cn.bestsort.bbslite.pojo.model.ThumbUp;
import cn.bestsort.bbslite.pojo.model.ThumbUpExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

/**
 * @ClassName ThumbUpService
 * @Description
 * @Author bestsort
 * @Date 19-9-28 下午6:57
 * @Version 1.0
 */

@CacheConfig(cacheNames = {"like"})
@Service
public class ThumbUpService {
    @Autowired
    ThumbUpMapper thumbUpMapper;
    public Boolean getQuestionThumbUpByUser(Long userId,Long questionId){
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria()
                .andThumbUpByEqualTo(userId)
                .andThumbUpToEqualTo(questionId)
                .andTypeEqualTo(FunctionItem.QUESTION.getCode().byteValue())
                .andStatusEqualTo((byte) 1);
        List<ThumbUp> thumbUps = thumbUpMapper.selectByExample(example);

        return !thumbUps.isEmpty();
    }

    public Boolean setThumbUpCount(Long questionId, Long useId,FunctionItem item) {
        if(item == FunctionItem.COMMENT){

        }
        return null;
    }
}
