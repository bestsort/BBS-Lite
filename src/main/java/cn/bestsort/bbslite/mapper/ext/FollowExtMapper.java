package cn.bestsort.bbslite.mapper.ext;

import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.Follow;

import java.util.List;

public interface FollowExtMapper {
    Boolean setFollowCount(Follow follow);
    List<Article> listFollowArticleByUser(Long userId,Byte type);

}
