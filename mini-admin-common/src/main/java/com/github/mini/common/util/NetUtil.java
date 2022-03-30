package com.github.mini.common.util;

import java.net.InetAddress;
import java.net.Socket;

/**
 * @className: NetUtil
 * @description: 网络工具类, 判断端口是否可用
 * @author: HanSon.Q
 * @version: V1.0
 */
public class NetUtil {
    /***
     * 本地端口是否可用
     * @param port 端口
     * true:端口使用中 |  false:端口未使用
     */
    public static boolean isLocalePortUsing(int port) {
        boolean flag = true;
        try {
            flag = isPortUsing("127.0.0.1", port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /***
     * 指定主机的端口是否可用
     * @param port 端口
     * true:端口使用中 |  false:端口未使用
     */
    public static boolean isPortUsing(String host, int port) {
        boolean flag = false;
        try {
            InetAddress theAddress = InetAddress.getByName(host);
            Socket socket = new Socket(theAddress, port);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
