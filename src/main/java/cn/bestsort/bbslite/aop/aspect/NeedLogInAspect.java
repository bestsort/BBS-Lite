package cn.bestsort.bbslite.aop.aspect;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.pojo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.session.StandardSessionFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author bestsort
 * @date 2019/11/28 下午8:46
 * 校验用户是否登录
 */
@Slf4j
@Aspect
@Component
public class NeedLogInAspect {
    @Pointcut("@annotation(cn.bestsort.bbslite.aop.annotation.NeedLogin)")
    public void needLogIn(){}

    @Around("needLogIn()")
    public Object interceptor(ProceedingJoinPoint point) throws Throwable {
        //获取请求
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        User user = (User)(request.getSession().getAttribute("user"));
        if (user != null){
            return point.proceed();
        }else {
            return ResultDto.errorOf(new CustomizeException(CustomizeErrorCodeEnum.NO_LOGIN));
        }
    }
}
