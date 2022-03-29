package com.github.ma.gateway.security.filter;

import com.github.ma.common.constant.MiniAdminConstant;
import com.github.ma.common.properties.RsaKeyProperties;
import com.github.ma.common.util.ApplicationContextUtil;
import com.github.ma.common.util.JwtUtil;
import com.github.ma.common.util.RedisUtil;
import com.github.mini.system.domain.security.JwtUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
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

        //将信息放置到Spring-security context中
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

    private JwtUserDetails commonFilter(HttpServletRequest request) {

        String authToken = request.getHeader(MiniAdminConstant.ACCESS_TOKEN_NAME);
        if (!StringUtils.hasLength(authToken)) {
            authToken = request.getParameter(MiniAdminConstant.ACCESS_TOKEN_NAME);
        }
        if (!StringUtils.hasLength(authToken)) {
            return null; //放行,并交给UsernamePasswordAuthenticationFilter进行验证,返回公共错误信息.
        }

        RsaKeyProperties rsaKeyProperties = ApplicationContextUtil.getBean(RsaKeyProperties.class);
        assert rsaKeyProperties != null;
        JwtUtil.JWTPayload jwtPayload = JwtUtil.getInfoFromToken(authToken, rsaKeyProperties.getPublicKey());
        //token字符串解析失败
        if (jwtPayload == null || !StringUtils.hasLength(jwtPayload.getCacheKey())) {
            return null;
        }
        //根据用户名查找数据库
        JwtUserDetails jwtUserDetails = RedisUtil.getObject(jwtPayload.getCacheKey(), JwtUserDetails.class);
        if (jwtUserDetails == null) {
            RedisUtil.del(jwtPayload.getCacheKey());
            return null; //数据库查询失败，删除redis
        }
        //续签时间
        RedisUtil.expire(jwtPayload.getCacheKey(), MiniAdminConstant.TOKEN_TIME);
        return jwtUserDetails;
    }


}
