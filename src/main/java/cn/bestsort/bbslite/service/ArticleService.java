package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.SortBy;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.ArticleExtMapper;
import cn.bestsort.bbslite.mapper.ArticleMapper;
import cn.bestsort.bbslite.mapper.TopicExtMapper;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.ArticleExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleService {
    public static int SEARCH = 1;
    public static int TOPIC = 2;
    public static int USER = 3;
    public static int ALL = 4;
    private final ArticleMapper articleMapper;
    private final ArticleExtMapper articleExtMapper;
    private final TopicExtMapper topicExtMapper;

    public PageInfo<Article> findArticleListByCategory(int page,int size,int category){
        ArticleExample example = new ArticleExample();
        example.setOrderByClause(SortBy.DEFAULT_ORDER);

        PageHelper.startPage(page,size);
        List<Article> articles = articleMapper.selectByExample(example);
        return new PageInfo<>(articles);
    }

    public void incArticleLike(Long articleId, Long val){
        articleExtMapper.incArticleLike(articleId, val);
    }

    public Long createOrUpdate(Article article) {
        article.setGmtModified(System.currentTimeMillis());
        Long result = null;
        if(article.getId() == null){
            article.setGmtCreate(article.getGmtModified());
            topicExtMapper.updateCountWithVal(article.getTopic(),1L);
            articleExtMapper.insertArticleExt(article);
            result = article.getId();
        }else {
            int updated = articleMapper.updateByPrimaryKeySelective(article);
            if(updated != 1) {
                throw new CustomizeException(CustomizeErrorCodeEnum.ARTICLE_NOT_FOUND);
            }
        }
        return result;
    }
    @Cacheable(keyGenerator = "myKeyGenerator")
    public PageInfo<Article> getPageBySearch(ArticleQueryDto queryDto) {
        List<Article> articles;
        PageHelper.startPage(queryDto.getPageNo(),queryDto.getPageSize());
        articles = articleExtMapper.listBySearch(queryDto);
        return new PageInfo<>(articles);
    }
    @Cacheable(keyGenerator = "myKeyGenerator")
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
