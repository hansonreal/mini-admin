package com.github.mini.gateway.web.cmd;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@Accessors(chain = true)
public class LoginRequest {

    @NotBlank(message = "登录名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

    private String captchaKey;

    private String captchaCode;
}
