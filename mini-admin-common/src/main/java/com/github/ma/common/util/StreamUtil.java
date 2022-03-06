package com.github.ma.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @className: StreamUtil
 * @description: 常用流操作方法
 * @author: Hanson
 * @version: V1.0
 * @create: 2020-02-04 03:11
 **/
@Slf4j
public class StreamUtil {
    /**
     * 流转字符串
     *
     * @param in 字节输入流
     * @return 字符串
     */
    public static String getString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("get string failure", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
