package com.github.ma.common.exception;


import com.github.ma.common.code.ResultCode;

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
