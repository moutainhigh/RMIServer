package com.hgsoft.utils;

import java.text.DecimalFormat;

/**
 * 数字工具类
 * Created by wiki on 2017/8/28.
 */
public class NumberUtil {
    private NumberUtil(){}

    /**
     *  精确2位小数
     * @param num 类型必须是数字类型 int Double Decimal...之类的
     * @return 返回一个数字字符串
     */
    public static String get2Decimal(Object num){
        return new DecimalFormat("#0.00").format(num);
    }
}
