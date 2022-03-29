package com.github.ma.common.constant;

public interface MiniAdminConstant {
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
}
