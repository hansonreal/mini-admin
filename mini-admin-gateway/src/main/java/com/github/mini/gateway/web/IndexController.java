package com.github.mini.gateway.web;

import com.github.mini.common.ann.Anonymous;
import com.github.mini.common.result.JwtResult;
import com.github.mini.gateway.security.service.AuthService;
import com.github.mini.gateway.web.cmd.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class IndexController {

    @Autowired
    private AuthService authService;

    @Anonymous
    @GetMapping({"/", "/index"})
    public String init() {
        return "Welcome to use Mini Admin Sys!";
    }


    @PostMapping("/login")
    public JwtResult login(LoginRequest request) {
        return JwtResult.ok("");
    }
}
