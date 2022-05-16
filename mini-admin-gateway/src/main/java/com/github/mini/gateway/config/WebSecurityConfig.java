package com.github.mini.gateway.config;

import com.github.mini.common.properties.RsaKeyProperties;
import com.github.mini.gateway.security.authentication.MiniAuthenticationEntryPoint;
import com.github.mini.gateway.security.ext.SecurityExtUtil;
import com.github.mini.gateway.security.filter.JwtAuthenticationFilter;
import com.github.mini.gateway.security.handler.MiniAccessDeniedHandler;
import com.github.mini.gateway.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启@PreAuthorize @PostAuthorize 等前置后置安全校验注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @Autowired
    private SecurityExtUtil securityExt;

    /**
     * 使用BCrypt强哈希函数 实现PasswordEncoder
     **/
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() {
        return new UserDetailsServiceImpl();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoderBean());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()  // 由于使用的是JWT，我们这里不需要csrf（跨站请求伪造）
                .cors()// 跨越配置支持，此处代码会自动加载一个bean名称为 corsFilter的Filter
                .and().exceptionHandling().authenticationEntryPoint(new MiniAuthenticationEntryPoint())// 认证失败处理方式
                .accessDeniedHandler(new MiniAccessDeniedHandler())//无权限操作异常处理
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 基于token，所以不需要session
                .and().authorizeRequests().antMatchers(securityExt.getAnonymousUrls().toArray(new String[0])).permitAll()//可以匿名被访问的URL
                .and().authorizeRequests().anyRequest().authenticated()// 除上面外的所有请求全部需要鉴权认证
                .and().addFilterBefore(new JwtAuthenticationFilter(rsaKeyProperties), UsernamePasswordAuthenticationFilter.class) // 添加JWT 认证过滤器filter
                .headers().cacheControl();// 禁用缓存
    }

}
