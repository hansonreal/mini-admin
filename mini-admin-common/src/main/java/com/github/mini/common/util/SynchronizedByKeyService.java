package com.github.mini.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * KEY锁
 */
@Slf4j
@Component
public class SynchronizedByKeyService {

    private Map<String, ReentrantLock> mutexCache = new ConcurrentHashMap<>();

    /**
     * 执行方法
     *
     * @param key      锁KEY
     * @param runnable 执行方法
     */
    public void exec(String key, Runnable runnable) {
        ReentrantLock mute4Key = null;
        ReentrantLock mutexInCache;
        do {
            if (mute4Key != null) {
                mute4Key.unlock();
            }
            mute4Key = mutexCache.computeIfAbsent(key, k -> new ReentrantLock());
            mute4Key.lock();
            mutexInCache = mutexCache.get(key);
        } while (mutexInCache == null || mute4Key != mutexInCache);

        try {
            runnable.run();
        } finally {
            if (mute4Key.getQueueLength() == 0) {
                mutexCache.remove(key);
            }
            mute4Key.unlock();
        }
    }


}
