package com.github.mini.common.enums;

import com.github.mini.common.code.ResultCode;
import lombok.ToString;

/**
 * @className: CommonCodeEnum
 * @description: 通用返回码
 * @author: Hanson
 * @create: 2020-01-28 14:08
 * 10000-- 通用错误代码
 */
@ToString
public enum CommonCodeEnum implements ResultCode {
    SUCCESS(true, "10000", "操作成功！"),
    FAIL(false, "10001", "操作失败！"),
    INVALID_PARAM(false, "10002", "非法参数！"),
    UNAUTHENTICATED(false, "10003", "此操作需要登陆系统！"),
    UNAUTHORISE(false, "10004", "权限不足，无权操作！"),
    SERVER_ERROR(false, "99999", "抱歉，系统繁忙，请稍后重试！"),
    ;
    boolean success;
    String code;
    String message;

    private CommonCodeEnum(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return success;
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
