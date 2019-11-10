package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.ArticleExample;

import java.util.List;

/**
 * @ClassName ArticleExtMapper
 * @Description 对于 article 表相关内容的扩展封装,实现了一些将要用到而 Myatis 不能自动生成的功能:
 *      incView : 按照权值增加浏览数(权值为 record.viewCount 默认为1)
 *      incCommentCount : 按照权值增加评论数,增加 record.viewCount 默认为1)
 * @Author bestsort
 * @Date 19-9-13 下午1:39
 * @Version 1.0
 */

public interface ArticleExtMapper {
    List<Article> listBySearch(ArticleQueryDto articleQueryDto);
    void insertArticleExt(Article article);
    Boolean incArticleLike(Long id,Long val);
    Boolean incArticleFollow(Long id, Long val);
    Boolean incArticleComment(Long id,Long val);
    Boolean incArticleView(Long id,Long val);
}
