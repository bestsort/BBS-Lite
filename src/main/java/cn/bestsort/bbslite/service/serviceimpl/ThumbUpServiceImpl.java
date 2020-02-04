package cn.bestsort.bbslite.service.serviceimpl;

import cn.bestsort.bbslite.cache.aop.annotation.Cache;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.mapper.ext.ThumbUpExtMapper;
import cn.bestsort.bbslite.mapper.ThumbUpMapper;
import cn.bestsort.bbslite.pojo.model.ThumbUp;
import cn.bestsort.bbslite.pojo.model.ThumbUpExample;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 * @author bestsort
 * @date 19-9-28 下午6:57
 * @version 1.0
 */

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class ThumbUpServiceImpl {
    private final ThumbUpMapper thumbUpMapper;
    private final ThumbUpExtMapper thumbUpExtMapper;
    public Boolean getArticleThumbUpByUser(Long userId,Long articleId){
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria()
                .andThumbUpByEqualTo(userId)
                .andThumbUpToEqualTo(articleId)
                .andTypeEqualTo(FunctionItem.getCode(FunctionItem.ARTICLE))
                .andStatusEqualTo((byte) 1);
        List<ThumbUp> thumbUps = thumbUpMapper.selectByExample(example);

        return !thumbUps.isEmpty();
    }

    public Boolean setThumbUpCount(Long articleId, Long useId,FunctionItem item,Boolean isActive) {
        isActive = !isActive;
        ThumbUp thumbUp = new ThumbUp();
        thumbUp.setThumbUpBy(useId);
        thumbUp.setThumbUpTo(articleId);
        thumbUp.setStatus((byte)(isActive?1:0));
        thumbUp.setGmtModified(System.currentTimeMillis());
        thumbUp.setType(FunctionItem.getCode(item));
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria().andThumbUpByEqualTo(useId)
                .andThumbUpToEqualTo(articleId)
                .andTypeEqualTo(FunctionItem.getCode(item));
        if(thumbUpMapper.countByExample(example) != 0) {
            thumbUpExtMapper.setThumbUpCount(thumbUp);
        }
        else{
            thumbUp.setGmtCreate(thumbUp.getGmtModified());
            thumbUpMapper.insertSelective(thumbUp);
        }
        return isActive;
    }

    @Cache
    public Boolean getStatusByUser(Long articleId, Long id) {
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria()
                .andThumbUpByEqualTo(id)
                .andThumbUpToEqualTo(articleId)
                .andStatusEqualTo((byte)1);
        return thumbUpMapper.countByExample(example)!=0;
    }
}
