package com.uama.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GuJiaJia on 2016/12/28.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class DateUtils {
    /**
     * 获取当前时间
     * 传时间类型(例：yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentTime(String dateType) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateType);
        return formatter.format(new Date());
    }
}
