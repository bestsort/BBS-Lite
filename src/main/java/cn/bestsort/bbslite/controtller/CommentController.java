package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.cache.aop.annotation.Cache;
import cn.bestsort.bbslite.aop.annotation.NeedLogin;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.PeopleCenterEnum;
import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.CommentKid;
import cn.bestsort.bbslite.pojo.model.CommentParent;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.serviceimpl.ArticleServiceImpl;
import cn.bestsort.bbslite.service.serviceimpl.CommentServiceImpl;
import cn.bestsort.bbslite.service.serviceimpl.UserServiceImpl;
import cn.bestsort.bbslite.vo.CommentVo;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 评论控制器,用于处理评论(以及对文章的回复)提交
 * @author bestsort
 * @date 19-9-13 下午3:51
 * @version 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class  CommentController {
    private final CommentServiceImpl commentService;
    private final UserServiceImpl userService;
    private final ArticleServiceImpl articleService;
    @Cache
    @ResponseBody
    @GetMapping("/loadComment")
    public ResultDto get(@RequestParam(name = "id") Long articleId,
                         HttpSession session){
        try {
            User user = (User)session.getAttribute("user");
            Long userId = user==null?null:user.getId();
            PageInfo<CommentVo> comments = commentService.listByArticleId(articleId,userId,1,5);
            Long creator = articleService.getArticleDetail(articleId).getCreator();
            List<CommentVo> list = comments.getList();
            return ResultDto.okOf()
                    .addMsg("comments",list)
                    .addMsg("creator",creator);
        }catch (Exception e){
            log.error("Cause by LoadComment:\n{},\n{}",e.getMessage(),e.getCause());
            return ResultDto.errorOf(CustomizeErrorCodeEnum.SYS_ERROR);
        }
    }

    @NeedLogin
    @ResponseBody
    @PostMapping("/commitComment")
    public ResultDto commitComment(@RequestParam(name = "articleId") Long articleId,
                                   @RequestParam(name = "pid",required = false) Long pid,
                                   @RequestParam(name = "content") String content,
                                   @RequestParam(name = "sendToUser",required = false) Long userId,
                                   HttpSession session){
        User user = (User)session.getAttribute("user");
        int maxSize = 255;
        if (content.length() > maxSize){
            ResultDto.errorOf(403
                    ,"你的字数是" + content.length() + ",超过最大限制（255）了哟");
        }
        boolean isParent = (pid==null);
        Comment comment = isParent?
                new CommentParent():
                new CommentKid();

        comment.setContent(content);
        comment.setCommentById(user.getId());
        try {
            commentService.insert(comment,isParent?articleId:pid,isParent,userId);
            return ResultDto.okOf();
        }catch (Exception e){
            log.error("Cause by CommitComment:\n{},\n{}",e.getMessage(),e.getCause());
            return ResultDto.errorOf(CustomizeErrorCodeEnum.SYS_ERROR);
        }
    }
    @ResponseBody
    @GetMapping("/listCommentCenter")
    public ResultDto listCommentCenter(@RequestParam(name = "userId") Long id,
                                       @RequestParam(name = "pageSize",defaultValue = "10") Integer size,
                                       @RequestParam(name = "pageNo",defaultValue = "1")Integer page){
        PageInfo pageInfo = commentService.listByUserId(id, page, size);
        return ResultDto.okOf()
                .addMsg("page",pageInfo)
                .addMsg("func", PeopleCenterEnum.COMMENT);
    }
}