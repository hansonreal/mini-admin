package com.github.mini.gateway.web;

import com.github.mini.common.ann.Anonymous;
import com.github.mini.common.properties.RsaKeyProperties;
import com.github.mini.common.result.DataResult;
import com.github.mini.common.result.JwtResult;
import com.github.mini.common.util.RsaUtil;
import com.github.mini.gateway.security.service.AuthService;
import com.github.mini.gateway.web.cmd.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@RestController
@RequestMapping
public class IndexController {


    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @Anonymous
    @GetMapping({"/", "/index"})
    public String init() {
        return "Welcome to use Mini Admin Sys!";
    }


    /**
     * 获取公钥
     *
     * @return
     */
    @GetMapping("/rsakey")
    public DataResult getRsaPublicKey() throws Exception {
        PublicKey publicKey = rsaKeyProperties.getPublicKey();
        String publicKeyStr = RsaUtil.getPublicKeyStr(publicKey);
        return DataResult.ok(publicKeyStr);
    }


    @GetMapping("/hi")
    public String hello() {
        return "Welcome to use Mini Admin Sys!";
    }


    @PostMapping("/login")
    public JwtResult login(LoginRequest request) {
        return JwtResult.ok("");
    }
}
