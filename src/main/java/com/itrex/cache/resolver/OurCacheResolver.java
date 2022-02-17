package com.itrex.cache.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.ArrayList;
import java.util.Collection;

public class OurCacheResolver implements CacheResolver {

    private final CacheManager cacheManager;
    private final CacheManager alternateCacheManager;

    @Autowired
    public OurCacheResolver(CacheManager cacheManager, CacheManager alternateCacheManager) {
        this.cacheManager = cacheManager;
        this.alternateCacheManager = alternateCacheManager;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
       Collection<Cache> caches = new ArrayList<>();

       if(context.getTarget().getClass().getName().contains("Bank")){
           caches.add(alternateCacheManager.getCache("bank"));
       }
       else
       {
           caches.add(cacheManager.getCache("accountCache"));
       }

        return caches;
    }
}
