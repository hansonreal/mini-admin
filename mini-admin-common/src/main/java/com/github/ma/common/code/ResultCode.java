package com.github.ma.common.code;

/**
 * @className: ResultCode
 * @description: 结果码接口类
 * @author: Hanson
 * @date: 2020-01-28 14:08
 **/
public interface ResultCode {

    /**
     * @return 操作代码
     */
    String getCode();

    /**
     * @return 提示信息
     */
    String getMessage();


}
