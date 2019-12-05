package cn.bestsort.bbslite.interceptor;

import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.pojo.model.UserExample;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * 拦截器,验证用户在进入某一页面是否已经登录
 * @author bestsort
 * @date 19-8-31 下午7:53
 * @version 1.0
 */
@Slf4j
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length != 0) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        user = userService.getByToken(token);
                        if (user != null) {
                            //写入Session便于持久化登录
                            request.getSession().setAttribute("user", user);
                        }
                        break;
                    }
                }
            }
        }
        log.info("\naccount:{} \nname: {} \nlogin from: {} \nhas been view :{} \nby: {}  \nReferer from {}",
                user==null?"Customer":user.getAccountId(),
                user==null?"Customer":user.getName(),IpUtil.getIpAddr(request),
                request.getRequestURI()+(request.getQueryString()==null?"":("?"+request.getQueryString())),
                request.getHeader("User-Agent"),
                request.getHeader("Referer"));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) { }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) { }
}
