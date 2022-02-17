package com.itrex.cache.config;

import com.itrex.cache.resolver.OurCacheResolver;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Configuration
public class CacheConfig {

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "_"
                        + StringUtils.arrayToDelimitedString(params, "_");
            }

        };
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public EhCacheCacheManager alternateCacheManager() {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        net.sf.ehcache.CacheManager eh = ehCacheManagerFactoryBean().getObject();
        ehCacheCacheManager.setCacheManager(eh);
        return ehCacheCacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }

    @Bean
    CacheResolver cacheResolver(){
        return new OurCacheResolver(cacheManager(),alternateCacheManager());
    }
}
