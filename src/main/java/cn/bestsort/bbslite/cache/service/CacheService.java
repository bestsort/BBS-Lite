package cn.bestsort.bbslite.cache.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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

    @Async
    void set(String key,String val,long time,String strategy);

    /**
     * redis  key 自增
     * @param key key
     * @param step 步长
     */
    @Async
    void inc(String key,Long step);
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
}
