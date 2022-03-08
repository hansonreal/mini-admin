package com.github.ma.common.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(prefix = "mini.admin")
public class MiniAdminProperties {

    /**
     * 是否允许跨域请求 [生产环境建议关闭， 若api与前端项目没有在同一个域名下时，应开启此配置或在nginx统一配置允许跨域]
     **/
    private boolean allowCrossOrigin = true;


    // 线程池配置
    private Pool pool = new Pool();

    // 关闭应用配置
    private Shutdown shutdown = new Shutdown();


    /**
     * 线程池配置
     */
    @Data
    public static class Pool {
        /**
         * 线程池创建时的初始化线程数，默认为20
         */
        private int coreSize = 20;
        /**
         * 线程池的最大线程数，默认为30
         */
        private int maxPoolSize = 30;
        /**
         * 缓冲执行任务的队列大小，默认值为15
         */
        private int queueCapacity = 15;
        /**
         * 线程终止前允许保持空闲的时间 默认为60秒
         */
        private Duration keepAlive = Duration.ofSeconds(60);
        /**
         * 是否允许核心线程超时,默认为true
         */
        private boolean allowCoreThreadTimeout = true;
        /**
         * 线程名称前缀，设置好了之后可以方便在日志中查看处理任务所在的线程池
         */
        private String threadNamePrefix = "M-A-T";
    }


    /**
     * 关闭应用配置
     */
    @Data
    public static class Shutdown {
        /**
         * 是否等待剩余任务完成之后才关闭应用
         */
        private boolean awaitTermination = true;
        /**
         * 等待剩余任务完成的最大时间
         */
        private Duration awaitTerminationPeriod = Duration.ofSeconds(90);

    }
}
