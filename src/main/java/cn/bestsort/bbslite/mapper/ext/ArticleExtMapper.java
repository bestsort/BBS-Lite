package cn.bestsort.bbslite.mapper.ext;

import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.pojo.model.Article;

import java.util.List;

/**
 * 对于 article 表相关内容的扩展封装,实现了一些将要用到而 Myatis 不能自动生成的功能:
 *      incView : 按照权值增加浏览数(权值为 record.viewCount 默认为1)
 *      incCommentCount : 按照权值增加评论数,增加 record.viewCount 默认为1)
 * @author bestsort
 * @date 19-9-13 下午1:39
 * @version 1.0
 */

public interface ArticleExtMapper {
    List<Article> listBySearch(ArticleQueryDto articleQueryDto);
    void insertArticleExt(Article article);
    Long getArticleView(Long id);
    Boolean incArticleFollow(Long val, Long id);
    Boolean incArticleComment(Long val,Long id);
    Boolean incArticleView(Long val,Long id);
    Boolean incArticleLike(Long val,Long id);
}
