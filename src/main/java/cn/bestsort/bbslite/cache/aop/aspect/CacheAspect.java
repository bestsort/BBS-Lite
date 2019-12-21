package cn.bestsort.bbslite.cache.aop.aspect;

import cn.bestsort.bbslite.cache.service.KeyGenerator;
import cn.bestsort.bbslite.cache.aop.annotation.Cache;
import cn.bestsort.bbslite.util.SpringContextUtil;

/**
 * TODO
 *
 * @author bestsort
 * @version 1.0
 * @date 12/21/19 9:25 AM
 */
public interface CacheAspect {

    default Cache init(){

        return null;
    };
    /**
     * 获取 key 生成器
     * @param clazz key生成工具类
     * @return key generator class
     */
    default KeyGenerator getKeyGenerator(String clazz){
        return (KeyGenerator) SpringContextUtil.getBean(clazz);
    }

    /**
     * 默认时间计算
     * @param cacheAop Cache 相关注解
     * @return times
     */
    default long getTime(Cache cacheAop){
        long min = cacheAop.min();
        long max = cacheAop.max()<min?min+3:cacheAop.max();
        long time = min + (int)(Math.random() * (max-min+1));
        switch (cacheAop.timeType()){
            case DAY: time *= 24L;
            case HOUR: time *= 60L;
            case MINUTES: time *= 60L;
            default: break;
        }
        return time;
    }

    /**
     * 获取缓存过期策略
     * @param cacheAop Cache相关注解
     * @return 策略
     */
    default String getStrategy(Cache cacheAop){
        return cacheAop.strategy();
    }
}
