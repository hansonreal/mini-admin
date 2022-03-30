package com.github.mini.common.result;

import com.github.mini.common.code.ResultCode;
import com.github.mini.common.enums.CommonCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @className: BaseResult
 * @description: 基础返回结果
 * @author: Hanson
 * @version: V1.0
 * @create: 2020-01-28 14:42
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
public class BaseResult implements IBaseResponse {
    /**
     * 是否成功
     */
    private boolean flag = SUCCESS;
    /**
     * 返回码
     */
    private String code = SUCCESS_CODE;
    /**
     * 返回信息
     */
    private String message;

    public BaseResult(ResultCode code) {
        this.flag = code.isSuccess();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public static BaseResult ok() {
        return new BaseResult(CommonCodeEnum.SUCCESS);
    }

    public static BaseResult fail() {
        return new BaseResult(CommonCodeEnum.FAIL);
    }
}
