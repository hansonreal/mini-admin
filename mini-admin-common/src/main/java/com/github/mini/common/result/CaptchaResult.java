package com.github.mini.common.result;

import com.github.mini.common.code.ResultCode;
import com.github.mini.common.enums.CommonCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 验证码返回结果
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class CaptchaResult extends BaseResult {
    /**
     * 图形验证码数据
     */
    private String imgBase64Data;
    /**
     * 验证码的KEY
     */
    private String captchaToken;

    /**
     * 验证码有效期，默认60秒
     */
    private String expireTime;

    public CaptchaResult(ResultCode codeEnum, String imgBase64Data, String captchaToken, String expireTime) {
        super(codeEnum);
        this.imgBase64Data = imgBase64Data;
        this.captchaToken = captchaToken;
        this.expireTime = expireTime;
    }

    /**
     * 默认60秒的有效期
     *
     * @param imgBase64Data 验证码图形数据
     * @param captchaToken  验证KRY，存在Redis中的，验证时需要带上
     * @return 结果
     */
    public static CaptchaResult ok(String imgBase64Data, String captchaToken) {
        return new CaptchaResult(CommonCodeEnum.SUCCESS, imgBase64Data, captchaToken, "60");
    }

    /**
     * 自定义有效期
     *
     * @param imgBase64Data 验证码图形数据
     * @param captchaToken  验证KRY，存在Redis中的，验证时需要带上
     * @param expireTime    验证有效期
     * @return 结果
     */
    public static CaptchaResult ok(String imgBase64Data, String captchaToken, String expireTime) {
        return new CaptchaResult(CommonCodeEnum.SUCCESS, imgBase64Data, captchaToken, expireTime);
    }

}
