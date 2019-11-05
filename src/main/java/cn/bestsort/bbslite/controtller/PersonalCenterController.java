package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.CommentService;
import cn.bestsort.bbslite.service.ArticleService;
import cn.bestsort.bbslite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

@Controller
public class PersonalCenterController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @RequestMapping("/center")
    public String profile(@RequestParam(name = "id",required = false) Long id,
                             HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null){
            throw new CustomizeException(CustomizeErrorCodeEnum.NO_WAY);
        }
        return "redirect:/people?user=" + user.getId();
    }
    @GetMapping("/people")
    public String people(){
        return "people";
    }
}
