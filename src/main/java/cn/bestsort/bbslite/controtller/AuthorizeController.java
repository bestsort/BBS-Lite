package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.pojo.model.OAuth2User;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.UserService;
import cn.hutool.json.JSONUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 获取 Gitub 用户登录信息并插入/更新其数据库内内容
 * @author bestsort
 * @date 19-8-22 下午7:36
 * @version 1.0
 */

@Slf4j
@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizeController {
    private final UserService userService;
    private final AuthRequestFactory factory;
    @GetMapping
    public List<String> list() {
        return factory.oauthList();
    }

    @GetMapping("/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }
    @RequestMapping("/{type}/callback")
    public String login(@PathVariable String type,
                        AuthCallback callback,
                        HttpSession session,
                        HttpServletResponse servletResponse) {
        AuthRequest authRequest = factory.get(type);
        AuthResponse response = authRequest.login(callback);
        log.info("【response】= {}", JSONUtil.toJsonStr(response));
        OAuth2User auth2User = new OAuth2User();
        BeanUtils.copyProperties(response.getData(),auth2User);
        User user = userService.queryOauthUser(auth2User);
        if(auth2User.getUuid() != null){
            if (user == null) {
                user = userService.createOrUpdateAuth(auth2User);
            }
            servletResponse.addCookie(new Cookie("token",user.getToken()));
            session.setAttribute("user",user);
        }else {
            log.error("callback post github error,{}",auth2User);
        }
        return "redirect:/";
    }
}