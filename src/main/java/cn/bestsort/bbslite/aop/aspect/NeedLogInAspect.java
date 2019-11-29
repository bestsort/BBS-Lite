package cn.bestsort.bbslite.aop.aspect;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.session.StandardSessionFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author bestsort
 * @date 2019/11/28 下午8:46
 * @description 校验用户是否登录
 */
@Slf4j
@Aspect
@Component
public class NeedLogInAspect {
    @Pointcut("@annotation(NeedLogin)")
    public void needLogIn(){}

    @Around("needLogIn()")
    public Object interceptor(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取被拦截的方法
        Method method = signature.getMethod();
        //获取被拦截的方法名
        String methodName = method.getName();
        Object[] arg = point.getArgs();
        for (Object o : arg) {
            if (o instanceof StandardSessionFacade && ((StandardSessionFacade)o).getAttribute("user") != null)  {
                return point.proceed();
            }
        }
        return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
    }
}
