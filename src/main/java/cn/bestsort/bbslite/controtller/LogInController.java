package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author bestsort
 */
@Controller
public class LogInController {
    @Autowired
    UserService userService;
    @ResponseBody
    @PostMapping("/login")
    public ResultDto loginByAccount(@RequestParam("account") String account,
                                    @RequestParam("password") String password,
                                    @RequestParam("type") String type,
                                    HttpServletResponse response,
                                    HttpSession session){

        User user = userService.loginByAccount(account,password,type);
        if (user == null){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }
        session.setAttribute("user",user);
        response.addCookie(new Cookie("token",user.getToken()));
        return new ResultDto().okOf();
    }
}
