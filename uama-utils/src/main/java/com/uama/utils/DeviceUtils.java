package com.uama.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

/**
 * Created by GuJiaJia on 2016/12/28.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class DeviceUtils {

    //获取手机屏幕宽度
    public static int getDisplayWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    //获取手机屏幕高度
    public static int getDisplayHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    // 获取设备型号
    public static String getModel() {
        return Build.MODEL;
    }

    // 获取完整设备型号
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    // 获得系统版本号
    public static String getSystemCode() {
        return Build.VERSION.RELEASE;
    }

    /* 获取当前系统的android版本号 */
    public static int getSystem() {
        return Build.VERSION.SDK_INT;
    }

    //获取APP版本号
    public static String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Obtain app version error!";
        }
    }

    //获取手机设备号
    public static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return "Obtain serverId error!";
        }
    }

    //获取设备名称
    public static String getMobileName() {
        return android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
    }
}
