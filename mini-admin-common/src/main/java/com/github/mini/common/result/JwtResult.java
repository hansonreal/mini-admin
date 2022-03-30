package com.github.mini.common.result;

import com.github.mini.common.code.ResultCode;
import com.github.mini.common.enums.CommonCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @className: JwtResult
 * @description: JWT结果类
 * @author: Hanson
 * @version: V1.0
 * @create: 2020-01-28 15:29
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
public class JwtResult extends BaseResult {
    private Object jwt;

    public JwtResult(ResultCode codeEnum) {
        super(codeEnum);
    }

    public JwtResult(ResultCode codeEnum, String jwt) {
        super(codeEnum);
        this.jwt = jwt;
    }

    public static JwtResult ok(String jwt) {
        return new JwtResult(CommonCodeEnum.SUCCESS, jwt);
    }
}
