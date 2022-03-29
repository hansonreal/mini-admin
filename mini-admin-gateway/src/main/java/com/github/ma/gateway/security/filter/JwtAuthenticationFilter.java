package com.github.ma.gateway.security.filter;

import com.github.mini.system.domain.security.JwtUserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security框架中验证组件的前置过滤器；
 * 用于验证token有效期，并放置ContextAuthentication信息,为后续spring security框架验证提供数据；
 * 避免使用@Component等bean自动装配注解：@Component会将filter被spring实例化为web容器的全局filter，导致重复过滤。
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        JwtUserDetails jwtUserDetails = commonFilter(request);
        if (jwtUserDetails == null) {
            filterChain.doFilter(request, response);
            return;
        }
    }

    private JwtUserDetails commonFilter(HttpServletRequest request) {
        return null;
    }


}
