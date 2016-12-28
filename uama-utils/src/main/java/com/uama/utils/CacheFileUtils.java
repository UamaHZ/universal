package com.uama.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by gujiajia on 2016/6/27.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class CacheFileUtils {
    /**
     * 获取照片文件路径
     * @return
     */
    public static String getUpLoadPhotosPath() {
        StringBuffer fileSB = new StringBuffer();
        String key = String.format("%s.jpg", CreatKeyUtil.generateSequenceNo());
        fileSB.append(getImagePath()).append(File.separator).append(key);
        return fileSB.toString();
    }

    public static String getImagePath() {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), FileUtils.IMAGE_FILE_PATH);
        if (!file.mkdirs()) {
            Log.e("App", "Directory not created");
        }
        return file.getPath();
    }
}