package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.enums.PeopleCenterEnum;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.ArticleService;
import cn.bestsort.bbslite.service.FollowService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName FollowController
 * @Description 获取关于用户/文章/话题的收藏请求
 * @Author bestsort
 * @Date 19-9-29 下午4:43
 * @Version 1.0
 */


@Controller
public class FollowController {
    @Autowired
    FollowService followService;
    @Autowired
    ArticleService articleService;
    @ResponseBody
    @PostMapping("/followArticle")
    public ResultDto follow(@RequestParam("id") Long id,
                            @RequestParam("isActive") Boolean isActive,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        boolean active = followService.setFollowCount(id, user.getId(), FunctionItem.ARTICLE, isActive);
        articleService.incArticleFollow(id,isActive?-1L:1L);
        return new ResultDto().okOf()
                .addMsg("isActive", active);
    }
    @ResponseBody
    @GetMapping("/listFollowArticle")
    public ResultDto getFollowList(@RequestParam(name = "userId") Long id,
                                   @RequestParam(name = "pageSize",defaultValue = "10") Integer size,
                                   @RequestParam(name = "pageNo",defaultValue = "1") Integer page){
        PageInfo<Article> articles = new PageInfo<>(
            followService.getListByUser(id,FunctionItem.ARTICLE,page,size)
        );

        return new ResultDto().okOf().addMsg("page",articles)
                .addMsg("func", PeopleCenterEnum.FOLLOW_ARTICLE);
    }
}
