package com.github.mini.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @className: Base64Util
 * @description: Base64工具类
 * @author: HanSon.Q
 * @version: V1.0
 * @date: 2020/12/2
 */
public class Base64Util {
    public static final Base64.Encoder encoder = Base64.getEncoder();
    public static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * base64加密
     *
     * @param encodeText 明文
     * @return 加密之后的结果
     */
    public static byte[] encoder(byte[] encodeText) {
        return encoder.encode(encodeText);
    }

    /**
     * base64加密
     *
     * @param data 明文
     * @return 加密之后的结果
     */
    public static byte[] encoder(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        return encoder.encode(bytes);
    }

    /**
     * base64加密
     *
     * @param decodeText 密文
     */
    public static byte[] decoder(byte[] decodeText) {
        return decoder.decode(decodeText);
    }

    /**
     * base64加密
     *
     * @param data 密文
     */
    public static byte[] decoder(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        return decoder.decode(bytes);
    }




}
