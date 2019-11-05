package cn.bestsort.bbslite.vo;

import lombok.Data;

/**
 * @ClassName ArticleDetailOptionVo
 * @Description TODO
 * @Author bestsort
 * @Date 2019/10/13 上午9:23
 * @Version 1.0
 */

@Data
public class ArticleDetailOptionVo {
    private Boolean isThumbUpArticle;
    private Boolean isFollowArticle;
    private Boolean isFollowUser;
    private Boolean isCreator;
}
