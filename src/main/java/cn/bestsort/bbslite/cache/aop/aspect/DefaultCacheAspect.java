package cn.bestsort.bbslite.cache.aop.aspect;

import cn.bestsort.bbslite.cache.aop.annotation.Cache;
import cn.bestsort.bbslite.cache.service.CacheService;
import cn.bestsort.bbslite.util.SpelUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
/**
 * 简单的缓存实现
 * @author bestsort
 * @date 2019/12/4 下午7:25
 * @version 1.0
 */
@Aspect
@Slf4j
public class DefaultCacheAspect implements CacheAspect{
    @Autowired
    private CacheService cacheService;
    @Pointcut("@annotation(cn.bestsort.bbslite.cache.aop.annotation.IncCache)")
    private void doAround(){}

    @Around("doAround()")
    public Object doCache(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        // 通过AnnotatedElementUtils将@IncCache等相关注解合并为@Cache
        Method method= ((MethodSignature) joinPoint.getSignature()).getMethod();

        // 获取合并参数后的注解
        Cache cacheAop = AnnotatedElementUtils.getMergedAnnotation(method,Cache.class);
        assert cacheAop != null;

        String key;
        Object result = null;

        // 如果key不为默认值则以解析SpEL表达式后的 key 为准

        if (!"".equals(cacheAop.key())){
            key = SpelUtil.parse(cacheAop.key(), method,joinPoint.getArgs());
        }
        // 否则根据定义的 key generator 生成 key
        else {
            key = getKeyGenerator(cacheAop.keyGenerator()).generate(
                    joinPoint.getTarget(),
                    ((MethodSignature) joinPoint.getSignature()).getMethod(),
                    joinPoint.getArgs());
        }

        if (cacheService.containKey(key)){
            String obj = cacheService.get(key);
            String fail = "fail";
            if(fail.endsWith(obj)){
                try {
                    result = joinPoint.proceed();
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            }else {
                log.info("cache hit : {} --> {}",request.getRequestURI(),request.getQueryString());
                Signature signature =  joinPoint.getSignature();
                Class returnType = ((MethodSignature) signature).getReturnType();
                result = JSON.parseObject(obj,returnType);
            }
        }else {
            try {
                result = joinPoint.proceed();
                String save2Cache = JSON.toJSONString(result);
                cacheService.set(key,save2Cache,getTime(cacheAop),getStrategy(cacheAop));
            }catch (Throwable throwable){
                throwable.printStackTrace();
            }
        }
        return result;
    }
}
