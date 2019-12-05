package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.mapper.TopicMapper;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.ArticleService;
import cn.bestsort.bbslite.service.TopicService;
import cn.bestsort.bbslite.vo.PublishVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 提问控制器,用于修改文章/发布文章
 * @author bestsort
 * @date 19-8-26 下午7:53
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublishController {
    private final ArticleService articleService;
    private final TopicService topicService;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @ResponseBody
    @GetMapping("/getPublishInfo")
    public ResultDto getPublishInfo(@RequestParam(name = "id",required = false) Long id,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        ResultDto resultDto;
        if(user == null){
            resultDto = new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        else if (id != null && !articleService.getArticleDetail(id).getCreator().equals(user.getId())) {
            resultDto = new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_WAY);
        } else {
            resultDto = new ResultDto().okOf();
            PublishVo publishVo = new PublishVo();
            publishVo.setTopics(topicService.getAll());
            if (id != null) {
                publishVo.setArticle(articleService.getArticleDetail(id));
            }
            resultDto.addMsg("publishInfo", publishVo);
        }
        return resultDto;
    }
    /**
     * TODO 部分字段为空时须进行前端校验
     **/
    @ResponseBody
    @PostMapping("/publish")
    public ResultDto doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam("topic") Byte topic,
            HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }

        Article article = new Article();
        article.setTitle(title);
        article.setTag(tag);
        article.setDescription(description);
        article.setCreator(user.getId());
        article.setUserAvatarUrl(user.getAvatarUrl());
        article.setUserName(user.getName());
        article.setTopic(topic);
        if (id != null) {
            article.setId(id);
        }
        article.setTopic(topic);
        Long newId = articleService.createOrUpdate(article);
        if(newId == null){
            newId = id;
        }
        return new ResultDto().okOf().addMsg("id",newId);
    }
}
