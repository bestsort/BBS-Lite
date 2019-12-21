package cn.bestsort.bbslite.cache.service;

import cn.bestsort.bbslite.cache.CacheModel;
import cn.bestsort.bbslite.cache.aop.annotation.Cache;

/**
 * TODO
 *
 * @author bestsort
 * @version 1.0
 * @date 12/21/19 3:16 PM
 */
public interface CacheType {
    /**
     * 返回拆箱后的 Cache对象
     * @return
     */
    CacheModel getCacheModel();
}
