package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.cache.aop.annotation.Cache;
import cn.bestsort.bbslite.cache.aop.annotation.IncCache;
import cn.bestsort.bbslite.cache.enums.Time;
import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.enums.PeopleCenterEnum;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.serviceimpl.ArticleServiceImpl;
import cn.bestsort.bbslite.service.serviceimpl.FollowServiceImpl;
import cn.bestsort.bbslite.service.serviceimpl.ThumbUpServiceImpl;
import cn.bestsort.bbslite.service.serviceimpl.UserServiceImpl;
import cn.bestsort.bbslite.vo.ArticleDetailOptionVo;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 文章控制器,用于查询文章列表
 * @author bestsort
 * @date 19-8-31 下午8:35
 * @version 1.0
 */

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {
    private final ArticleServiceImpl articleService;
    private final UserServiceImpl userService;
    private final ThumbUpServiceImpl thumbUpService;
    private final FollowServiceImpl followService;
    @GetMapping("/article/{id}")
    public String article(@PathVariable("id") Long id){
        return "article";
    }

    /**
     * 获取文章列表(可根据搜索内容/话题/分类筛选结果)
     * @param queryDto 封装查询类
     * @return 根据条件筛选并进行分页后的文章列表
     */

    @ResponseBody
    @GetMapping("/loadArticleList")
    public ResultDto getArticleList(ArticleQueryDto queryDto){
        PageInfo<Article> pageInfo = articleService.getPageBySearch(queryDto);
        return ResultDto.okOf()
                .addMsg("page",pageInfo)
                .addMsg("func", PeopleCenterEnum.ARTICLE);
    }

    /**
     * 加载文章详情
     * @param id 文章id
     * @return 文章详情
     */


    @ResponseBody
    @GetMapping("/loadArticleDetail")
    public ResultDto getArticleDetail(@RequestParam(name = "id") Long id){
        Article article = articleService.getArticleDetail(id);
        article.setViewCount(articleService.incArticleView(id));
        User user = userService.getSimpleInfoById(article.getCreator());
        return ResultDto.okOf()
                .addMsg("article",article)
                .addMsg("user",user);
    }
    @Cache
    @ResponseBody
    @GetMapping("/loadArticleOption")
    public Object getArticleOption(@RequestParam(name = "articleId") Long articleId,
                                   @RequestParam(name = "userId") Long userId,
                                   HttpSession session){
        ArticleDetailOptionVo articleDetailOptionVo = new ArticleDetailOptionVo();
        User user = (User)session.getAttribute("user");
        if(user != null && user.getId().equals(userId)){
            articleDetailOptionVo.setIsCreator(false);
        }

        if(user != null) {
            articleDetailOptionVo.setIsThumbUpArticle(
                    thumbUpService.getStatusByUser(articleId, user.getId())
            );
            articleDetailOptionVo.setIsFollowArticle(
                    followService.getStatusByUser(articleId,user.getId(), FunctionItem.ARTICLE)
            );
        }else {
            articleDetailOptionVo.setIsThumbUpArticle(false);
            articleDetailOptionVo.setIsFollowArticle(false);
        }
        return ResultDto.okOf().addMsg("options",articleDetailOptionVo);
    }
}
