package cn.bestsort.bbslite.aop.aspect;

import cn.bestsort.bbslite.aop.annotation.Cache;
import cn.bestsort.bbslite.service.CacheService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * TODO
 * @author bestsort
 * @date 2019/12/4 下午7:25
 * @version 1.0
 */
@Component
@Aspect
@Slf4j
public class CacheAspect {
    @Autowired
    private CacheService cacheService;
    @Pointcut("@annotation(cn.bestsort.bbslite.aop.annotation.Cache)")
    public void annotationAspect() {
    }

    @Around("annotationAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        //获取注解自身
        Cache cacheAop = ((MethodSignature) joinPoint
                .getSignature())
                .getMethod()
                .getAnnotation(Cache.class);

        //存储接口返回值
        Object object = new Object();
        String strategy = cacheAop.strategy();
        long min = cacheAop.min();
        long max = cacheAop.max()<min?min+3:cacheAop.max();
        long time = min + (int)(Math.random() * (max-min+1));
        switch (cacheAop.type()){
            case "D": time *= 24L;
            case "H": time *= 60L;
            case "M": time *= 60L;
            default: break;
        }
        String key = cacheService.getKeyForAop(joinPoint,request);
        if (cacheService.containKey(key)){
            String obj = cacheService.get(key);
            String fail = "fail";
            if(fail.endsWith(obj)){
                try {
                    joinPoint.proceed();
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            }else {
                log.info("cache hit !");
                return JSON.parseObject(obj);
            }
        }else {
            try {
                object = joinPoint.proceed();
                String save2Cache = JSON.toJSONString(object);
                cacheService.set(key,save2Cache,time,strategy);
            }catch (Throwable throwable){
                throwable.printStackTrace();
            }
        }
        return object;
    }
}
