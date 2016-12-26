/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-27 上午10:26.
 */

package uama.hangzhou.image.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

/**
 * Created by gujiajia on 2016/6/27.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class CacheFileUtils {
    public static final String IMAGE_FILE_PATH = "image";
    public static final String IMG_TYPE_PNG = "png";
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
                Environment.DIRECTORY_PICTURES), IMAGE_FILE_PATH);
        if (!file.mkdirs()) {
            Log.e("App", "Directory not created");
        }
        return file.getPath();
    }
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
