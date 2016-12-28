package com.uama.utils;

/**
 * Created by lvman on 2016/4/25.
 */
public class ClickTools {

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > 500)
            lastClickTime = time;
        return timeD <= 500;
    }

    private static long lastClickTime = 0;
}
