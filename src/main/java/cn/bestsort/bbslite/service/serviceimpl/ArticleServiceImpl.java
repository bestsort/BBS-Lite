package cn.bestsort.bbslite.service.serviceimpl;

import cn.bestsort.bbslite.cache.aop.annotation.Cache;
import cn.bestsort.bbslite.cache.aop.annotation.IncCache;
import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.SortBy;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.ext.ArticleExtMapper;
import cn.bestsort.bbslite.mapper.ArticleMapper;
import cn.bestsort.bbslite.mapper.ext.TopicExtMapper;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.ArticleExample;
import cn.bestsort.bbslite.service.ArticleInterface;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 * @author bestsort
 * @date 19-10-3 下午12:33
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleServiceImpl implements ArticleInterface {
    public static int SEARCH = 1;
    public static int TOPIC = 2;
    public static int USER = 3;
    public static int ALL = 4;
    private final ArticleMapper articleMapper;
    private final ArticleExtMapper articleExtMapper;
    private final TopicExtMapper topicExtMapper;

    @Cache
    public synchronized PageInfo<Article> findArticleListByCategory(int page,int size,int category){
        ArticleExample example = new ArticleExample();
        example.setOrderByClause(SortBy.DEFAULT_ORDER);

        PageHelper.startPage(page,size);
        List<Article> articles = articleMapper.selectByExample(example);
        return new PageInfo<>(articles);
    }

    public void incArticleLike(Long articleId, Long val){
        articleExtMapper.incArticleLike(articleId,1L);
    }

    @Override
    public synchronized Long createOrUpdate(Article article) {
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

    @Override
    public PageInfo<Article> getPageBySearch(ArticleQueryDto queryDto) {
        List<Article> articles;
        PageHelper.startPage(queryDto.getPageNo(),queryDto.getPageSize());
        articles = articleExtMapper.listBySearch(queryDto);
        return new PageInfo<>(articles);
    }
    @Cache
    @Override
    public Article getArticleDetail(Long id){
        return articleMapper.selectByPrimaryKey(id);
    }

    public Long incArticleFollow(Long id) {
        return getFollow(id) + 1L;
    }

    @Cache(key = "article:view_count:")
    public Long getArticleView(Long id){
        return articleMapper.selectByPrimaryKey(id).getViewCount();
    }

    @IncCache
    public Long incArticleView(Long id) {
        return getArticleView(id);
    }

    public Long decArticleView(Long id) {
        return getArticleView(id) - 1L;
    }


    public Long incFollow(Long id){return getFollow(id);}
    public Long getFollow(Long id){
        return articleMapper.selectByPrimaryKey(id).getFollowCount();
    }


    public Long incView(Long id){return getArticleView(id);}




    public Long getLike(Long id){
        return articleMapper.selectByPrimaryKey(id).getLikeCount();
    }
    public Long getComment(Long id){
        return articleMapper.selectByPrimaryKey(id).getCommentCount();
    }
}
