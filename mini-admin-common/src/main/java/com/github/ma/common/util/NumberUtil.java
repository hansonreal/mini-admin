package com.github.ma.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className: NumberUtil
 * @description: 数字工具类
 * @author: Hanson
 * @version: V1.0
 * @create: 2020-02-06 15:36
 **/
public class NumberUtil {

    public static boolean isInt(Double num) {
        return num.intValue() == num;
    }

    /**
     * 判断字符串是否是数值格式
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        if (str == null || str.trim().equals("")) {
            return false;
        }
        return str.matches("^\\d+$");
    }

    /**
     * 将一个小数精确到指定位数
     *
     * @param num
     * @param scale
     * @return
     */
    public static double scale(double num, int scale) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * @param len 长度
     * @return 生成指定位数的随机数字
     */
    public static String generateCode(int len) {
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0, len);
    }

    /**
     * 校验金额是否符合规则
     *
     * @param pattern   表达式
     * @param payAmount 支付金额
     * @return true | false
     */
    public static boolean checkMoney(String pattern, String payAmount) {
        Pattern moneyPattern = Pattern.compile(pattern);
        Matcher match = moneyPattern.matcher(payAmount);
        return !match.matches();
    }
}
