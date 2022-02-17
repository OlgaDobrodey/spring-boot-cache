package com.itrex.cache.annotation;


import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Cacheable(cacheNames = "accountCache", keyGenerator = "keyGenerator")
public @interface SlowService {
}
