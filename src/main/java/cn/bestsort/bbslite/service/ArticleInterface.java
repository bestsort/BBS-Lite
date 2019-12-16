package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.pojo.model.Article;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * 文章相关接口
 * @author bestsort
 * @version 1.0
 * @date 12/10/19 7:57 AM
 */
public interface ArticleInterface extends BbsInterface {
    /**
     * 创建/更新文章
     *
     * @param article 文章实体
     * @return 文章id
     */
    Long createOrUpdate(Article article);

    /**
     * 获取文章分页后的列表
     *
     * @param queryDto 文章筛选条件
     * @return list , 文章列表+分页信息
     */
    PageInfo<Article> getPageBySearch(ArticleQueryDto queryDto);

    /**
     * 获取文章详情
     * @param id 文章id
     * @return 文章详情
     */
    Article getArticleDetail(Long id);
}