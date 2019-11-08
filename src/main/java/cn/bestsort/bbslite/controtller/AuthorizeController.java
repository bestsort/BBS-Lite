package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.AccessTokenDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.manager.GithubProvider;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.vo.GithubUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @ClassName
 * @Description 获取 Gitub 用户登录信息并插入/更新其数据库内内容
 * @Author bestsort
 * @Date 19-8-22 下午7:36
 * @Version 1.0
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${bbs.github.id:}")
    private String clientId;
    @Value("${bbs.github.secret:}")
    private String secret;
    @Value("${bbs.url:}" + "/callback")
    private String redirectUri;
    @Autowired
    private UserService userService;

    @GetMapping("github-login")
    public String githubLogin(){
        if (secret.length() + clientId.length() == 0){
            throw new CustomizeException(CustomizeErrorCodeEnum.URL_NOT_FOUND);
        }

        String url = "https://github.com/login/oauth/authorize?"+
                "client_id=" + clientId + "&" +
                "redirect_uri=" + redirectUri +
                "&scope=user&state=1&&allow_signup=true";
        return "redirect:" + url;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpSession session){
        AccessTokenDto accessTokenDTO = new AccessTokenDto();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserVo githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            BeanUtils.copyProperties(githubUser,user);
            user.setToken(token);
            user.setName(githubUser.getLogin());
            user.setAccountId(githubUser.getId());
            userService.createOrUpdate(user);
            user = userService.getByToken(token);
            session.setAttribute("user",user);
        }else {
            log.error("callback post github error,{}",githubUser);
        }
        return "redirect:/";
    }
}
