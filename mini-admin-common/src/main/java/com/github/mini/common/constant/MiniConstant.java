package com.github.mini.common.constant;

import java.time.Duration;

public interface MiniConstant {
    /**
     * 分割符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");


    String JWT_PAYLOAD_USER_KEY = "userInfo";

    // 算法名称
    public static final String ALGORITHM_NAME = "RSA";

    // 算法名称
    public static final String MD5_RSA = "MD5withRSA";


    // 默认密码
    public static final String DEFAULT_PWD = "admin";

    //access_token 名称
    public static final String ACCESS_TOKEN_NAME = "iToken";

    //token失效时间
    public static final long TOKEN_TIME = 60 * 60 * 2; //单位：s,  两小时


    public static final String CACHE_KEY_IMG_CAPTCHA_CODE = "img_captcha_code_%s";

    public static Duration CAPTCHA_CODE_CACHE_TIME = Duration.ofSeconds(60);

    static String getCacheKeyImgCaptchaCode(String imgToken) {
        return String.format(CACHE_KEY_IMG_CAPTCHA_CODE, imgToken);
    }
}
