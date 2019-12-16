package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.serviceimpl.ArticleServiceImpl;
import cn.bestsort.bbslite.service.serviceimpl.CommentServiceImpl;
import cn.bestsort.bbslite.service.serviceimpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 个人资料控制器
 * @author bestsort
 * @date 19-8-30 下午6:21
 * @version 1.0
 */

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonalCenterController {
    private final UserServiceImpl userService;
    private final ArticleServiceImpl articleService;
    private final CommentServiceImpl commentService;
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
