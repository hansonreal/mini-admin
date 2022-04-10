package com.github.mini.gateway.web;

import com.github.mini.common.result.JwtResult;
import com.github.mini.gateway.security.service.AuthService;
import com.github.mini.gateway.web.cmd.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    /**
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public JwtResult login(@RequestBody @Validated LoginRequest loginRequest) {
        log.info("认证");
        @NotBlank(message = "登录名不能为空") String username = loginRequest.getUsername();
        @NotBlank(message = "密码不能为空") String password = loginRequest.getPassword();
        String auth = authService.auth(username, password);


        return JwtResult.ok(jwt);
    }


}
