package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.manager.MailService;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.pojo.model.UserBuffer;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.util.MurmursHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private MailService mail;

    @ResponseBody
    @GetMapping("/getUserInfo")
    public ResultDto getUserInfo(@RequestParam(name = "id")Long id,
                                 HttpSession session){
        User user = userService.getSimpleInfoById(id);
        boolean isOwn = false;
        User sessonUser = (User)session.getAttribute("user");
        if (sessonUser != null && sessonUser.getId().equals(id)){
            isOwn = true;
        }
        if (user == null){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.USER_NOT_FOUND);
        }
        return new ResultDto().okOf()
                .addMsg("user",user)
                .addMsg("isOwn",isOwn);
    }

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
    @RequestMapping("/logout")
    public String logout(HttpSession session,
                         HttpServletResponse response){
        session.removeAttribute("user");
        //删除 Cookie
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/sign-up")
    public ResultDto post(@RequestParam("accountId") String accountId,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email){
        UserBuffer user = new UserBuffer();
        user.setAccountId(accountId);
        user.setEmail(email);
        user.setPassword(password);
        if (userService.hasCreateUser(user)){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.USER_EXITED);
        }
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setPassword(MurmursHash.hashUnsignedWithSalt(user.getPassword()));
        user.setToken(token);
        userService.signUpUser(user);
        mail.sendMail(user.getToken(),user.getAccountId(),user.getEmail());
        return new ResultDto().okOf();
    }

    @GetMapping("/activate")
    public String activate(){
        return "activate";
    }
    @ResponseBody
    @PostMapping("/activate")
    public ResultDto activeAccount(@RequestParam("token") String token,
                                   @RequestParam("account")String account,
                                   HttpSession session,
                                   HttpServletResponse response){
        try {
            User user = userService.activateUser(token,account);
            session.setAttribute("user",user);
            response.addCookie(new Cookie("token",token));
        }catch (Exception ignore){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.USER_ERROR);
        }
        return new ResultDto().okOf();
    }
}
