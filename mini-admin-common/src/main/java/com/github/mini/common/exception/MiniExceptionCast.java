package com.github.mini.common.exception;


import com.github.mini.common.code.ResultCode;

/**
 * @className: SuperEdgeExceptionCast
 * @description: 异常转换
 * @author: hanson
 * @date: 2020-01-28 14:59
 * @since : V1.0
 **/
public class MiniExceptionCast {
    public MiniException cast(ResultCode code) {
        return new MiniException(code);
    }
}
