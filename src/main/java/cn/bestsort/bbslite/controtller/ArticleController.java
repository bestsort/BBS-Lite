package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.enums.PeopleCenterEnum;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.ArticleService;
import cn.bestsort.bbslite.service.FollowService;
import cn.bestsort.bbslite.service.ThumbUpService;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.vo.ArticleDetailOptionVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName ArticleController
 * @Description 文章控制器,用于查询文章列表
 * @Author bestsort
 * @Date 19-8-31 下午8:35
 * @Version 1.0
 */
@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ThumbUpService thumbUpService;
    @Autowired
    private FollowService followService;
    @GetMapping("/article/{id}")
    public String article(@PathVariable("id") Long id){
        return "article";
    }

    /**
     * @Description 获取文章列表(可根据搜索内容/话题/分类筛选结果)
     * @param sort 排序关键字
     * @param search 搜索关键字(根据正则搜索标题)
     * @param topic 话题
     * @param tag 标签(根据正则搜索标签)
     * @param size 页面大小(一页所容纳的文章数)
     * @param page 第几页
     * @param categoryVal 话题分类
     * @return 根据条件筛选并进行分页后的文章列表
     */
    @ResponseBody
    @GetMapping("/loadArticleList")
    public ResultDto getArticleList(@RequestParam(name = "pageSize",defaultValue = "10") Integer size,
                                     @RequestParam(name = "pageNo",defaultValue = "1") Integer page,
                                     @RequestParam(name = "sortby",required = false) String sort,
                                     @RequestParam(name = "search",required = false) String search,
                                     @RequestParam(name = "topic",required = false) Integer topic,
                                     @RequestParam(name = "tag",required = false) String tag,
                                     @RequestParam(name = "userId",required = false) Long userId,
                                     @RequestParam(name = "category",required = false) Integer categoryVal){
        ArticleQueryDto queryDto = ArticleQueryDto.builder()
                .search(search)
                .sortBy(sort)
                .category(categoryVal)
                .pageNo(page)
                .tag(tag)
                .pageSize(size)
                .topic(topic)
                .userId(userId).build();
        PageInfo<Article> pageInfo = articleService.getPageBySearch(queryDto);
        return new ResultDto().okOf()
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
        articleService.incArticleView(id,1L);
        User user = userService.getSimpleInfoById(article.getCreator());
        return new ResultDto().okOf()
                .addMsg("article",article)
                .addMsg("user",user);
    }

    @ResponseBody
    @GetMapping("/loadArticleOption")
    public ResultDto getArticleOption(@RequestParam(name = "articleId") Long articleId,
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
        return new ResultDto().okOf().addMsg("options",articleDetailOptionVo);
    }
}
