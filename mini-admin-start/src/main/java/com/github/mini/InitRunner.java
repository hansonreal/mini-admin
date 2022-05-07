package com.github.mini;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 类似开机自启，可以用来加载缓存数据，读取配置文件
 * 避免在启动类中写过多函数
 */
@Component
public class InitRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
    }
}
