/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-21 下午8:10.
 */

package com.uama.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by GuJiaJia on 2016/12/28.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class StringUtils {

    // 防止为空。
    public static String parseNullToZero(String str) {
        if (TextUtils.isEmpty(str)) {
            return "0";
        }
        return str;
    }

    // 防止为空。
    public static String parseOrderToZero(String str) {
        if (TextUtils.isEmpty(str)) {
            return "0.00";
        }
        return str;
    }

    /**
     * 字符串是否为空
     */
    public static boolean isNull(String str) {
        if (str == null || str.equals("null") || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    //设置字符串,null的时候设置""
    public static String newString(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static String newString(Integer number) {
        if (number == null) {
            return "0";
        }
        return String.valueOf(number);
    }

    public static String newString(Double number) {
        if (number == null) {
            return "0.0";
        }
        return String.valueOf(number);
    }

    public static String doubleToString(Double dou) {
        if(dou == null){
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(dou);
    }

    public static String doubleStringToString(String dou) {
        return doubleToString(stringToDouble(dou));
    }

    /**
     * 对象转double
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
            Log.i("UamaUtils","StringUtils toDouble:"+e.getMessage());
        }
        return 0;
    }

    public static String doubleToIntegerString(Double dou) {
        DecimalFormat df = new DecimalFormat("######0");
        return df.format(dou);
    }

    /**
     * 对象转float
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static float toFloat(String obj) {
        try {
            return Float.parseFloat(obj);
        } catch (Exception e) {
            Log.i("UamaUtils","StringUtils toFloat:"+e.getMessage());
        }
        return 0f;
    }

    /**
     * 将数字转换成字符串
     * 如果数字大于999，返回’999+‘
     * 否则返回数字本身
     */
    public static String parse999(int number) {
        if (number > 999) {
            return "999+";
        } else {
            return String.valueOf(number);
        }
    }


    /**
     * 得到中间省略的电话号码
     */
    public static String getUserInvisPhone(String telNumber) {
        return !TextUtils.isEmpty(telNumber) && telNumber.length() == 11 ? telNumber.substring(0, 3)
                + "****"
                + telNumber.substring(7, telNumber.length()) : "";
    }

    public static String getMoneyString(String money){
        if(money == null || money.equals("")){
            return "";
        }
        if(toInt(money) == 0){
            return "";
        }else{
            return "¥"+toInt(money);
        }
    }

    public static String getMoneyString(Double money){
        if(money == null || money == 0){
            return "";
        }
        return "¥"+money;
    }

    /**
     * 字符串转整数
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            Log.i("UamaUtils","StringUtils toInt:"+e.getMessage());
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    public static Double stringToDouble(String string) {
        if (string == null) {
            string = "0.00";
        }
        return Double.valueOf(string).doubleValue();
    }

}
