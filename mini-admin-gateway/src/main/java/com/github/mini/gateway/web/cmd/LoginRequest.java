package com.github.mini.gateway.web.cmd;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class LoginRequest {

    private String username;

    private String password;

    private String captchaKey;

    private String captchaCode;
}
