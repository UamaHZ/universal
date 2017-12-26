package uama.hangzhou.image.util;

/*
 *
 * Created by gujiajia on 2016/6/25.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import uama.hangzhou.image.photochoose.GetImageListener;

public class SDCardImageLoader {
    private static final int PAGE_SIZE = 250;

    /**
     * 使用ContentProvider读取SD卡所有图片。
     */
    public static void getImagePathsByContentProvider(Context context, int pageIndex, final GetImageListener getImageListener) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC limit " + PAGE_SIZE + " offset " + pageIndex * PAGE_SIZE;
        ContentResolver mContentResolver = context.getContentResolver();

        // 只查询jpg和png的图片
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                sortOrder);
        if (cursor == null) {
            return;
        }
        ArrayList<String> cursorList;

        cursorList = new ArrayList<>();

        while (cursor.moveToNext()) {
            // 获取图片的路径
            String path = cursor.getString(0);
            cursorList.add(path);
        }
        cursor.close();
        Log.i("ailee", "cursorList.size=" + cursorList.size() + ",  pageIndex=" + pageIndex);
        if (getImageListener != null) {
            getImageListener.getComplete(cursorList, cursorList.size() >= PAGE_SIZE);
        }
    }

    public static boolean checkIsBitmap(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {//路径不存在
                return false;
            }
            if (getFileSizes(f) <= 0) {//图片大小为0
                return false;
            }
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            if (w <= 0 || h <= 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //获取文件大小
    public static long getFileSizes(File f) throws Exception {

        long s = 0;
        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件夹不存在");
        }

        return s;
    }

}
