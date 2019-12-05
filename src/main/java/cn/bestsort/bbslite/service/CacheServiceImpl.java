package cn.bestsort.bbslite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;

/**
 * Cache data
 * @author bestsort
 * @date 2019/12/4 下午7:03
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CacheServiceImpl implements CacheService {
    private final JedisPool jedisPool;
    @Override
    public Jedis getResource() throws Exception {
        return jedisPool.getResource();
    }

    @Override
    public void set(String key,String val,long time,String strategy) {
        Jedis jedis = null;
        try{
            jedis = getResource();
            jedis.set(key,val,strategy,"ex",time);
        }catch (Exception e){
            log.error("Redis set error:{}-{},value:{}",
                    e.getMessage(), key, val);
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public String get(String key) {
        String result = null;
        Jedis jedis = null;
        try{
            jedis = getResource();
            result = jedis.get(key);
        }catch (Exception e){
            log.error("Redis set error:{}-{},value:{}",e.getMessage(),key,result);
        }finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public boolean containKey(String key) {
        Jedis jedis = null;
        try{
            jedis = getResource();
            return jedis.exists(key);
        }catch (Exception e){
            log.error("redis server error:{}",e.getMessage());
            return false;
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public void returnResource(Jedis jedis) {
        if (jedis != null){
            jedis.close();
        }
    }

    @Override
    public String getKeyForAop(JoinPoint joinPoint, HttpServletRequest request) {
        Object[] objects = joinPoint.getArgs();
        return objects[0].toString();
    }
}
