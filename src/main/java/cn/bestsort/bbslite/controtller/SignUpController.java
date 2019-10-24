package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.MailService;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.util.MurmursHash;
import cn.bestsort.bbslite.vo.UserCreateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @ClassName SignUpController
 * @Description TODO 用户注册 密码为 murmur(用户密码+账号)
 * @Author bestsort
 * @Date 19-9-23 下午8:13
 * @Version 1.0
 */
@Slf4j
@Controller
public class SignUpController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mail;
    @RequestMapping("/sign_up")
    public String signUp(){
        return "sign_up";
    }

    @ResponseBody
    @PostMapping("/sign_up")
    public ResultDto post(@RequestBody UserCreateVo userCreateDTO){

        if (!userService.hasCreateUser(userCreateDTO)){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.USER_EXITED);
        }

        User user = new User();
        String token = UUID.randomUUID().toString();
        BeanUtils.copyProperties(userCreateDTO,user);
        user.setGmtModified(System.currentTimeMillis());
        user.setGmtCreate(user.getGmtModified());
        user.setPassword(MurmursHash.hashUnsigned(user.getPassword()+user.getAccountId()));
        user.setToken(token);
        user = userService.createOrUpdate(user);
        userCreateDTO.setToken(user.getToken());
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