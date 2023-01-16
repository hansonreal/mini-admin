package com.github.mini.gateway.security.ext;


import com.github.mini.common.ann.Anonymous;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Slf4j
@Component
public class SecurityExtUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private int count = 0;

    private Set<String> anonymousUrls = Collections.emptySet();


    /**
     * 获取匿名访问的URL
     */
    @PostConstruct
    public Set<String> init() {
        count++;
        log.info("当前执行第:{}次",count);
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();


        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            Anonymous anonymous = handlerMethod.getMethodAnnotation(Anonymous.class);
            if (anonymous != null) {
                RequestMappingInfo requestMappingInfo = infoEntry.getKey();
                PathPatternsRequestCondition pathPatternsCondition = requestMappingInfo.getPathPatternsCondition();
                if (pathPatternsCondition != null) {//spring boot 2.6.4 该值不会为空, 2.4.2版本该值为空
                    Set<PathPattern> patterns = pathPatternsCondition.getPatterns();
                    patterns.forEach(pathPattern -> anonymousUrls.add(pathPattern.getPatternString()));
                }
                PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                if (null != patternsCondition) {////spring boot 2.6.4 版本该值为空, 2.4.2该值不会为空
                    anonymousUrls.addAll(patternsCondition.getDirectPaths());
                }

            }
        }
        log.info("可以匿名访问的URL:{}", anonymousUrls);
        return anonymousUrls;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
