package cn.bestsort.bbslite.service.cacheimpl;

import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.service.ArticleInterface;
import com.github.pagehelper.PageInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * TODO
 * @author bestsort
 * @version 1.0
 * @date 12/10/19 8:04 AM
 */

public class ArticleCache implements ArticleInterface {
    @Override
    public Long createOrUpdate(Article article) {
        return null;
    }

    @Override
    public PageInfo<Article> getPageBySearch(ArticleQueryDto queryDto) {
        return null;
    }

    @Override
    public Article getArticleDetail(Long id) {
        return null;
    }
}
