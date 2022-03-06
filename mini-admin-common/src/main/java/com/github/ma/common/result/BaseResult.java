package com.github.ma.common.result;

import com.github.ma.common.code.ResultCode;
import com.github.ma.common.enums.CommonCodeEnum;
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
     * 返回码
     */
    private String code = SUCCESS_CODE;
    /**
     * 返回信息
     */
    private String message = SUCCESS_MSG;

    public BaseResult(ResultCode code) {
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
