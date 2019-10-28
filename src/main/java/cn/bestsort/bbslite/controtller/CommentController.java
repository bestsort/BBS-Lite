package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.CommentKid;
import cn.bestsort.bbslite.pojo.model.CommentParent;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.CommentService;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.vo.CommentVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName CommentController
 * @Description 评论控制器,用于处理评论(以及对问题的回复)提交
 * @Author bestsort
 * @Date 19-9-13 下午3:51
 * @Version 1.0
 */

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @ResponseBody
    @GetMapping("/loadComment")
    public ResultDto get(@RequestParam(name = "id") Long questionId){
        try {
            PageInfo<CommentVo> comments = commentService.listByQuestionId(questionId,1,5);
            List<CommentVo> list = comments.getList();
            return new ResultDto().okOf()
                    .addMsg("comments",list);
        }catch (Exception e){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.SYS_ERROR);
        }
    }


    @ResponseBody
    @PostMapping("/commitComment")
    public ResultDto commitComment(@RequestParam(name = "questionId") Long questionId,
                                   @RequestParam(name = "pid",required = false) Long pid,
                                   @RequestParam(name = "content") String content,
                                   HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        boolean isParent = (pid==null);
        Comment comment = isParent?
                new CommentParent():
                new CommentKid();

        comment.setContent(content);
        comment.setCommentById(user.getId());
        try {
            commentService.insert(comment,isParent?questionId:pid,isParent);
            return new ResultDto().okOf();
        }catch (Exception e){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.SYS_ERROR);
        }
    }
}