package com.github.mini.gateway.config;

import com.github.mini.common.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {


    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
