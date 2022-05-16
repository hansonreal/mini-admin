package com.github.mini.common.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {
    /**
     * 要锁哪个参数
     */
    int lockIndex() default -1;

    /**
     * 锁多久后自动释放（单位：秒）
     * 租约时间
     */
    int leaseTime() default 10;
}
