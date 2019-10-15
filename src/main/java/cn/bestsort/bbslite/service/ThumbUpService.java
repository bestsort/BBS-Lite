package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.mapper.ThumbUpExtMapper;
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
    @Autowired
    ThumbUpExtMapper thumbUpExtMapper;
    public Boolean getQuestionThumbUpByUser(Long userId,Long questionId){
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria()
                .andThumbUpByEqualTo(userId)
                .andThumbUpToEqualTo(questionId)
                .andTypeEqualTo(FunctionItem.getCode(FunctionItem.QUESTION))
                .andStatusEqualTo((byte) 1);
        List<ThumbUp> thumbUps = thumbUpMapper.selectByExample(example);

        return !thumbUps.isEmpty();
    }

    public Boolean setThumbUpCount(Long questionId, Long useId,FunctionItem item,Boolean isActive) {
        isActive = !isActive;
        ThumbUp thumbUp = new ThumbUp();
        thumbUp.setThumbUpBy(useId);
        thumbUp.setThumbUpTo(questionId);
        thumbUp.setStatus((byte)(isActive?1:0));
        thumbUp.setGmtModified(System.currentTimeMillis());
        thumbUp.setType(FunctionItem.getCode(item));
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria().andThumbUpByEqualTo(useId)
                .andThumbUpToEqualTo(questionId)
                .andTypeEqualTo(FunctionItem.getCode(item));
        if(item == FunctionItem.QUESTION){
            if(thumbUpMapper.countByExample(example) != 0) {
                thumbUpExtMapper.setThumbUpCount(thumbUp);
            }
            else{
                thumbUp.setGmtCreate(thumbUp.getGmtModified());
                thumbUpMapper.insertSelective(thumbUp);
            }
            return isActive;
        }
        return !isActive;
    }

    public Boolean getStatusByUser(Long questionId, Long id) {
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria()
                .andThumbUpByEqualTo(id)
                .andThumbUpToEqualTo(questionId)
                .andStatusEqualTo((byte)1);
        return thumbUpMapper.countByExample(example)!=0;
    }
}
