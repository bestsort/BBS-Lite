package cn.bestsort.bbslite.util.interceptor;

import cn.bestsort.bbslite.dao.mapper.UserMapper;
import cn.bestsort.bbslite.bean.model.User;
import cn.bestsort.bbslite.bean.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName SessionInterceptor
 * @Description 拦截器,验证用户在进入某一页面是否已经登录
 * @Author bestsort
 * @Date 19-8-31 下午7:53
 * @Version 1.0
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> user = userMapper.selectByExample(userExample);
                     if (!user.isEmpty()) {
                         //点赞允许匿名用户,所以需要将user信息写入servlet中
                         request.setAttribute("user", user.get(0));
                         //写入Session便于持久化登录
                         request.getSession().setAttribute("user", user.get(0));
                     }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) { }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) { }
}
