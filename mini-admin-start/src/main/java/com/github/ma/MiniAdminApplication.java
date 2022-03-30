package com.github.ma;

import com.github.ma.common.properties.MiniAdminProperties;
import com.github.ma.common.properties.RsaKeyProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Mini启动入口
 */
@Slf4j
@SpringBootApplication
@MapperScan
@EnableConfigurationProperties({MiniAdminProperties.class, RsaKeyProperties.class})
public class MiniAdminApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MiniAdminApplication.class, args);
        Environment env = context.getEnvironment();
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            log.info("\n----------------------------------------------------------------------------------------------------\n\t" +
                    "Application Mini Admin is running! Access URLs:\n\t" +
                    "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                    "External:\thttp://" + ip + ":" + port + path + "/\n" +
                    "----------------------------------------------------------------------------------------------------");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


}
