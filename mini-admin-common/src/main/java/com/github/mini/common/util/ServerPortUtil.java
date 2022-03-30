package com.github.mini.common.util;

import java.util.Random;

/**
 * @className: ServerPortUtil
 * @description: 端口工具类
 * @author: HanSon.Q
 * @version: V1.0
 */
public class ServerPortUtil {
    /**
     * 获取可用端口，范围2000-65535
     *
     * @return 可用端口
     */
    public static int getAvailablePort() {
        int max = 65535;
        int min = 2000;
        Random random = new Random();
        int port = random.nextInt(max) % (max - min + 1) + min;
        boolean using = NetUtil.isLocalePortUsing(port);
        if (using) {
            return getAvailablePort();
        } else {
            return port;
        }
    }
}
