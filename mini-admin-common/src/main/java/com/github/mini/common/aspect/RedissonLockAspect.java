package com.github.mini.common.aspect;

import com.github.mini.common.ann.RedissonLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Order(1)
@Component
public class RedissonLockAspect {

    @Resource
    private Redisson redisson;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) throws Throwable {
        Object obj;
        //方法内的所有参数
        Object[] params = joinPoint.getArgs();
        int lockIndex = redissonLock.lockIndex();
        //取得方法名
        String key = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //-1代表锁整个方法，而非具体锁哪条数据
        if (lockIndex != -1) {
            key += params[lockIndex];
        }
        //多久会自动释放，默认10秒
        int leaseTime = redissonLock.leaseTime();
        int waitTime = 5;
        RLock rLock = redisson.getLock(key);
        boolean res = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        if (res) {
            log.info("取到锁");
            obj = joinPoint.proceed();
            rLock.unlock();
            log.info("释放锁");
        } else {
            log.warn("----------没有获得锁----------");
            throw new RuntimeException("没有获得锁");
        }
        return obj;
    }
}
