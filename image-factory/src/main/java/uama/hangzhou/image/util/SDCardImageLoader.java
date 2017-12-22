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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uama.hangzhou.image.R;

public class SDCardImageLoader {
    private static SDCardImageLoader mInstance;
    //缓存
    private LruCache<String, Bitmap> imageCache;
    // 固定2个线程来执行任务
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private Handler handler = new Handler();

    private int screenW, screenH;
    public static final int PAGE_SIZE = 200;

    public static SDCardImageLoader getInstance(Context context) {
        synchronized (SDCardImageLoader.class) {
            if (mInstance == null) {
                mInstance = new SDCardImageLoader(DeviceUtils.getDisplayWidth(context), DeviceUtils.getDisplayHeight(context));
            }
        }
        return mInstance;
    }

    private SDCardImageLoader(int screenW, int screenH) {
        this.screenW = screenW;
        this.screenH = screenH;

        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;

        // 设置图片缓存大小为程序最大可用内存的1/4
        imageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public Bitmap loadDrawable(final int smallRate, final String filePath,
                               final ImageCallback callback) {
        // 如果缓存过就从缓存中取出数据
        if (imageCache.get(filePath) != null) {
            return imageCache.get(filePath);
        }

        // 如果缓存没有则读取SD卡
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(filePath, opt);

                    // 获取到这个图片的原始宽度和高度
                    int picWidth = opt.outWidth;
                    int picHeight = opt.outHeight;

                    //读取图片失败时直接返回
                    if (picWidth == 0 || picHeight == 0) {
                        return;
                    }

                    //初始压缩比例
                    opt.inSampleSize = smallRate;
                    // 根据屏的大小和图片大小计算出缩放比例
                    if (picWidth > picHeight) {
                        if (picWidth > screenW)
                            opt.inSampleSize *= picWidth / screenW;
                    } else {
                        if (picHeight > screenH)
                            opt.inSampleSize *= picHeight / screenH;
                    }

                    //这次再真正地生成一个有像素的，经过缩放了的bitmap
                    opt.inJustDecodeBounds = false;
                    Bitmap bmp = BitmapFactory.decodeFile(filePath, opt);

                    int angle = readPictureDegree(filePath);

                    if (angle != 0) {
                        Matrix m = new Matrix();
                        int width1 = bmp.getWidth();
                        int height1 = bmp.getHeight();
                        m.setRotate(angle); // 旋转angle度

                        Bitmap tempBmp = bmp;
                        bmp = Bitmap.createBitmap(bmp, 0, 0, width1, height1, m, true);
                        tempBmp.recycle();
                    }

                    //存入map
                    imageCache.put(filePath, bmp);
                    final Bitmap finalBitmap = bmp;
                    handler.post(new Runnable() {
                        public void run() {
                            callback.imageLoaded(finalBitmap);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return null;
    }

    /**
     * 异步读取SD卡图片，并按指定的比例进行压缩（最大不超过屏幕像素数）
     *
     * @param smallRate 压缩比例，不压缩时输入1，此时将按屏幕像素数进行输出
     * @param filePath  图片在SD卡的全路径
     * @param imageView 组件
     */
    public void loadImage(int smallRate, final String filePath, final ImageView imageView) {
        Bitmap bmp = loadDrawable(smallRate, filePath, new ImageCallback() {
            @Override
            public void imageLoaded(Bitmap bmp) {
                if (imageView.getTag().equals(filePath)) {
                    if (bmp != null) {
                        imageView.setImageBitmap(bmp);
                    } else {
                        imageView.setImageResource(R.mipmap.uimage_empty_photo);
                    }
                }
            }
        });

        if (bmp != null) {
            if (imageView.getTag().equals(filePath)) {
                imageView.setImageBitmap(bmp);
            }
        } else {
            imageView.setImageResource(R.mipmap.uimage_empty_photo);
        }

    }

    public static int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
        }
        return degree;
    }

    // 对外界开放的回调接口
    public interface ImageCallback {
        // 注意 此方法是用来设置目标对象的图像资源
        public void imageLoaded(Bitmap imageDrawable);
    }

    /**
     * 使用ContentProvider读取SD卡所有图片。
     */
    public static ArrayList<String> getImagePathsByContentProvider(Context context) {
        int pageIndex = 0;
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;
//        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC limit " + PAGE_SIZE + " offset " + pageIndex * PAGE_SIZE;
        ContentResolver mContentResolver = context.getContentResolver();

        // 只查询jpg和png的图片
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);
        if (cursor == null) {
            return null;
        }
        ArrayList<String> cursorList = null;

        //从最新的图片开始读取.
        //当cursor中没有数据时，cursor.moveToLast()将返回false
        if (cursor.moveToLast()) {
            cursorList = new ArrayList<>();

            while (true) {
                // 获取图片的路径
                String path = cursor.getString(0);
                if (fileIsExists(path)) {
                    cursorList.add(path);
                }
                if (!cursor.moveToPrevious()) {
                    break;
                }
            }
        }
        cursor.close();
        return cursorList;
    }

    //判断图片是否有效
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {//路径不存在
                return false;
            }
            if (getFileSizes(f) <= 0) {//图片大小为0
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static boolean checkIsBitmap(String path) {
        try {
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
