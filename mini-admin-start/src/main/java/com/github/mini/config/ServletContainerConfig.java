package com.github.mini.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: ServletContainerConfig
 * @description:
 * @author: hanson
 * @since: V1.0
 * @date: 2021/9/27
 */
@Configuration
public class ServletContainerConfig {
    /**
     * 定制Undertow服务器
     * @return
     */
    @Bean
    @ConditionalOnClass(UndertowServletWebServerFactory.class)
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
        });
    }
}
