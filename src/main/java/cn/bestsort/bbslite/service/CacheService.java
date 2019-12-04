package cn.bestsort.bbslite.service;

import org.aspectj.lang.JoinPoint;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bestsort
 */
public interface CacheService {
    /**
     * 获取jedis实例
     * @return Jedis链接
     * @throws Exception 连接redis失败
     */
    Jedis getResource() throws Exception;

    /**
     *
     * redis 条目设置
     * @param key key
     * @param val val
     * @param time 具体时间(秒)
     * @param strategy 过期策略 (nx/xx)
     */
    void set(String key,String val,long time,String strategy);
    /**
     * get value by key
     * @param key key
     * @return value
     */
    String get(String key);
    /**
     * is exist key
     * @param key key
     * @return is exist
     */
    boolean containKey(String key);
    /**
     * give back jedis instance
     * @param jedis jedis instance
     */
    void returnResource(Jedis jedis);
    /**
     * generate key
     * @param joinPoint aop join point
     * @param request http request
     * @return key
     */
    String getKeyForAop(JoinPoint joinPoint, HttpServletRequest request);
}
