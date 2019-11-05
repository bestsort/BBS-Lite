package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.SortBy;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.ArticleExtMapper;
import cn.bestsort.bbslite.mapper.ArticleMapper;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.ArticleExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ArticleService
 * @Description
 * @Author bestsort
 * @Date 19-10-3 下午12:33
 * @Version 1.0
 */
@CacheConfig(cacheNames = "articleCache")
@Service
public class ArticleService {
    public static int SEARCH = 1;
    public static int TOPIC = 2;
    public static int USER = 3;
    public static int ALL = 4;

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleExtMapper articleExtMapper;

    public PageInfo<Article> findArticleListByCategory(int page,int size,int category){
        ArticleExample example = new ArticleExample();
        example.setOrderByClause(SortBy.DEFAULT_ORDER);

        PageHelper.startPage(page,size);
        List<Article> articles = articleMapper.selectByExample(example);
        return new PageInfo<>(articles);
    }

    public Boolean incArticleLike(Long articleId,Long val){
        return articleExtMapper.incArticleLike(articleId,val);
    }

    public Long createOrUpdate(Article article) {
        article.setGmtModified(System.currentTimeMillis());
        Long result = null;
        if(article.getId() == null){
            article.setGmtCreate(article.getGmtModified());

            result = articleExtMapper.insertArticleExt(article);
        }else {
            int updated = articleMapper.updateByPrimaryKeySelective(article);
            if(updated != 1) {
                throw new CustomizeException(CustomizeErrorCodeEnum.ARTICLE_NOT_FOUND);
            }

            //topicExtMapper.incArticle(topic);
        }
        return result;
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    public long countByType(int type,Object key){
        ArticleExample example = new ArticleExample();
        if(type == TOPIC) {
            example.createCriteria().andTopicEqualTo(key.toString());
        }
        else if (type == USER){
            example.createCriteria().andCreatorEqualTo((long)key);
        }
        return articleMapper.countByExample(example);
    }

    public PageInfo<Article> getPageBySearch(ArticleQueryDto queryDto) {
        List<Article> articles;
        PageHelper.startPage(queryDto.getPageNo(),queryDto.getPageSize());
        articles = articleExtMapper.listBySearch(queryDto);
        return new PageInfo<>(articles);
    }

    public Article getArticleDetail(Long id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    public void incArticleFollow(Long id, long val) {
        articleExtMapper.incArticleFollow(id,val);
    }

    public void incArticleView(Long id, Long val) {
        articleExtMapper.incArticleView(id,val);
    }
}
