package com.github.mini.common.result;

import com.github.mini.common.code.ResultCode;
import com.github.mini.common.enums.CommonCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @className: LoginResult
 * @description: 登录返回结果类
 * @author: Hanson
 * @version: V1.0
 * @create: 2020-01-28 15:27
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
public class LoginResult extends BaseResult {
    /**
     * 返回数据
     */
    private Object token;


    public LoginResult(ResultCode result) {
        super(result);
    }

    public LoginResult(ResultCode result, Object token) {
        super(result);
        this.token = token;
    }

    public static LoginResult ok(Object token) {
        return new LoginResult(CommonCodeEnum.SUCCESS, token);
    }

}
