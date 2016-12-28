package com.uama.utils;

import android.os.Handler;

/**
 * Created by GuJiaJia on 2016/7/9.
 * 延时action
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class DelayUtils {
    public static void doAfterDelay(int delay, final DelayDoListener delayDoListener){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                delayDoListener.doAction();
            }
        },delay);
    }

    public interface DelayDoListener{
        void doAction();
    }
}
