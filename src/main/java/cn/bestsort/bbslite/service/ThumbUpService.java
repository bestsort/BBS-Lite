package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.mapper.ThumbUpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ClassName ThumbUpService
 * @Description
 * @Author bestsort
 * @Date 19-9-28 下午6:57
 * @Version 1.0
 */

@CacheConfig(cacheNames = {"like"})
@Service
public class ThumbUpService {
    @Autowired
    ThumbUpMapper thumbUpMapper;

}
