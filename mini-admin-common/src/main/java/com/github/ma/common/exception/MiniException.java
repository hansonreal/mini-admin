package com.github.ma.common.exception;

import com.github.ma.common.code.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @className: SuperEdgeRestException
 * @description: 自定义异常
 * @author: Hanson
 * @version: V1.0
 * @create: 2020-01-28 14:35
 **/
@Getter
@Setter
public class MiniException extends RuntimeException {
    private String msg;
    private String code = "999";

    public MiniException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MiniException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MiniException(String msg, String code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MiniException(String msg, String code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public MiniException(ResultCode codeEnum) {
        super(codeEnum.getMessage());
        this.msg = codeEnum.getMessage();
        this.code = codeEnum.getCode();
    }

    public MiniException(ResultCode codeEnum, Throwable e) {
        super(codeEnum.getMessage(), e);
        this.msg = codeEnum.getMessage();
        this.code = codeEnum.getCode();
    }
}
