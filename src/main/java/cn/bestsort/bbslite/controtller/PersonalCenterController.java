package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.CommentService;
import cn.bestsort.bbslite.service.QuestionService;
import cn.bestsort.bbslite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProfileController
 * @Description 个人资料控制器
 * @Author bestsort
 * @Date 19-8-30 下午6:21
 * @Version 1.0
 */

@RestController
public class PersonalCenterController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/persional-center")
    public ResultDto profile(@RequestParam(name = "action",required = false) FunctionItem item,
                             @RequestParam(name = "id",required = false) Long id,
                             @RequestParam(name="page",defaultValue = "1") Integer page,
                             @RequestParam(name="size",defaultValue = "5") Integer size,
                             HttpSession session){
/*        User user = (User)session.getAttribute("user");
        if(user == null){
            throw new CustomizeException(CustomizeErrorCodeEnum.NO_WAY);
        }*/
        switch (item){
            case QUESTION: {
                return new ResultDto().okOf()
                        .addMsg("question", questionService.listQuestionByUserId(id));
            }
            case COMMENT: {
                return new ResultDto().okOf()
                        .addMsg("comment", commentService.listByUserId(id));
            }
            default: return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_WAY);
        }
    }
}
