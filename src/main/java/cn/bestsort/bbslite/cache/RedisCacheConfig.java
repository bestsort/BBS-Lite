package cn.bestsort.bbslite.cache;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * TODO
 *
 * @author bestsort
 * @version 1.0
 * @date 12/20/19 2:50 PM
 */
public class RedisCacheConfig implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"cn.bestsort.bbslite.cache.aop.aspect.DefaultCacheAspect"};
    }
}