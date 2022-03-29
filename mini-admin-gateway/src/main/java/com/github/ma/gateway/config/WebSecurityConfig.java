package com.github.ma.gateway.config;

import com.github.ma.common.properties.MiniAdminProperties;
import com.github.ma.common.properties.RsaKeyProperties;
import com.github.ma.gateway.security.authentication.MiniAuthenticationEntryPoint;
import com.github.ma.gateway.security.filter.JwtAuthenticationFilter;
import com.github.ma.gateway.security.handler.MiniAccessDeniedHandler;
import com.github.ma.gateway.security.service.UserDetailsServiceImpl;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启@PreAuthorize @PostAuthorize 等前置后置安全校验注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserDetailsServiceImpl userDetailsService;
    private final MiniAdminProperties adminProperties;
    private final RsaKeyProperties rsaKeyProperties;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,
                             MiniAdminProperties adminProperties,
                             RsaKeyProperties rsaKeyProperties) {
        this.userDetailsService = userDetailsService;
        this.adminProperties = adminProperties;
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 使用BCrypt强哈希函数 实现PasswordEncoder
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }




    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()  // 由于使用的是JWT，我们这里不需要csrf,跨站请求伪造
                .cors()
                .and().exceptionHandling().authenticationEntryPoint(new MiniAuthenticationEntryPoint())// 认证失败处理方式
                .accessDeniedHandler(new MiniAccessDeniedHandler())//无权限操作异常处理
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 基于token，所以不需要session
                .and().authorizeRequests().anyRequest().authenticated()// 除上面外的所有请求全部需要鉴权认证
                .and().addFilterBefore(new JwtAuthenticationFilter(rsaKeyProperties), UsernamePasswordAuthenticationFilter.class) // 添加JWT 认证过滤器filter
                .headers().cacheControl();// 禁用缓存
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //ignore文件 ： 无需进入spring security 框架
        // 1.允许对于网站静态资源的无授权访问
        // 2.对于获取token的rest api要允许匿名访问
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.jpeg",
                "/**/*.svg",
                "/**/*.ico",
                "/**/*.webp",
                "/*.txt",
                "/**/*.xls",
                "/**/*.mp4"   //支持mp4格式的文件匿名访问
        ).antMatchers(
                "/api/anon/**" //匿名访问接口
        );
    }

}
