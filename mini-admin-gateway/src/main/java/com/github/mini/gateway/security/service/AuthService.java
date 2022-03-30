package com.github.mini.gateway.security.service;

import com.github.mini.common.exception.MiniException;
import com.github.mini.system.domain.security.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    public String auth(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new MiniException("用户名/密码错误！");
        } catch (AuthenticationException e) {
            log.error("AuthenticationException:", e);
            throw new MiniException("认证服务出现异常， 请重试或联系系统管理员！");
        }
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();


        return "";
    }


}
