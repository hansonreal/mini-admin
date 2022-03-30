package com.github.mini.common.result;

import com.github.mini.common.code.ResultCode;
import com.github.mini.common.enums.CommonCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @className: PictureResult
 * @description: 上传图片返回结果
 * @author: Hanson
 * @version: V1.0
 * @create: 2020-02-06 15:30
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
public class PictureResult extends BaseResult {

    /**
     * 回显图片使用的url
     */
    private String url;

    public PictureResult(ResultCode codeEnum) {
        super(codeEnum);
    }

    public PictureResult(ResultCode codeEnum, String url) {
        super(codeEnum);
        this.url = url;
    }

    public static PictureResult ok(String url) {
        return new PictureResult(CommonCodeEnum.SUCCESS, url);
    }
}
