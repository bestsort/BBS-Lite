package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.mapper.FollowExtMapper;
import cn.bestsort.bbslite.mapper.FollowMapper;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.Follow;
import cn.bestsort.bbslite.pojo.model.FollowExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName FollowService
 * @Description
 * @Author bestsort
 * @Date 19-9-28 下午7:19
 * @Version 1.0
 */

@Service
public class FollowService {
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private FollowExtMapper followExtMapper;

    /**
     * TODO bug: 打开两个相同页面并且分别点赞会被计数两次
     * TODO 将 isActive 改为由后端校验
     * @param articleId
     * @param userId
     * @param item
     * @param isActive
     * @return
     */
    public Boolean setFollowCount(Long articleId,Long userId,FunctionItem item,Boolean isActive) {
        Follow follow = new Follow();
        isActive = !isActive;
        follow.setFollowBy(userId);
        follow.setStatus((byte)(isActive?1:0));
        follow.setFollowTo(articleId);
        follow.setType(FunctionItem.getCode(FunctionItem.ARTICLE));
        FollowExample followExample = new FollowExample();
        followExample.createCriteria()
                .andFollowByEqualTo(userId)
                .andFollowToEqualTo(articleId)
                .andTypeEqualTo(follow.getType());
        follow.setGmtModified(System.currentTimeMillis());

        if(item == FunctionItem.ARTICLE) {
            if (followMapper.countByExample(followExample) == 0) {
                follow.setGmtCreate(follow.getGmtModified());
                followMapper.insertSelective(follow);
            } else {
                followExtMapper.setFollowCount(follow);
            }
            return isActive;
        }
        return !isActive;
    }
    public  Boolean getStatusByUser(Long to, Long by, FunctionItem item){
        FollowExample example = new FollowExample();
        example.createCriteria()
                .andFollowByEqualTo(by)
                .andFollowToEqualTo(to)
                .andTypeEqualTo(FunctionItem.getCode(item))
                .andStatusEqualTo((byte) 1);
        return followMapper.countByExample(example) != 0;
    }

    public List<Article> getListByUser(Long id, FunctionItem article,Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return followExtMapper.listFollowArticleByUser(id, FunctionItem.getCode(article));
    }
}
