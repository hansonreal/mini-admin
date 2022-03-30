package com.github.mini.common.enums;

import com.github.mini.common.code.ResultCode;
import lombok.ToString;

/**
 * @className: CommonCodeEnum
 * @description: 通用返回码
 * @author: hanson
 * @date: 2020-01-28 14:08
 * 10000-- 通用错误代码
 */
@ToString
public enum CommonCodeEnum implements ResultCode {
    SUCCESS("000", "操作成功！"),
    FAIL("1001", "操作失败！"),

    UNAUTHORIZED("998", "请提供必要信息进行认证"),
    UNKNOWN("998", "当前异常未配置"),
    SERVER_ERROR("999", "抱歉，系统繁忙，请稍后重试！"),
    ;
    String code;
    String message;

    private CommonCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public static CommonCodeEnum getEnumByCode(String serviceCode) {
        for (CommonCodeEnum commonCodeEnum : CommonCodeEnum.values()) {
            if (commonCodeEnum.code.equalsIgnoreCase(serviceCode)) {
                return commonCodeEnum;
            }
        }
        return UNKNOWN;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
