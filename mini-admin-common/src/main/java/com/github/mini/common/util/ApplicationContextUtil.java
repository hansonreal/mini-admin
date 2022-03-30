package com.github.mini.common.util;

import com.github.mini.common.ann.Anonymous;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextUtil.applicationContext == null) {
            ApplicationContextUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        Assert.isNull(applicationContext, "Spring 容器未初始化");
        return applicationContext;
    }


    /**
     * 通过name获取 Bean.
     */
    public static Object getBean(String name) {
        Assert.isNull(applicationContext, "Spring 容器未初始化");
        if (!getApplicationContext().containsBean(name)) {
            return null;
        }

        return getApplicationContext().getBean(name);

    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        if (!getApplicationContext().containsBean(name)) {
            return null;
        }
        return getApplicationContext().getBean(name, clazz);
    }


    /**
     * 获取匿名访问的URL
     *
     * @return
     */
    @PostConstruct
    public Set<String> getAnonymousUrls() {
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            Anonymous anonymous = handlerMethod.getMethodAnnotation(Anonymous.class);
            if (anonymous != null) {
                if (infoEntry.getKey().getPatternsCondition() != null) {
                    anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                }
            }
        }
        log.info("可以匿名访问的URL:{}", anonymousUrls);
        return anonymousUrls;
    }


}
