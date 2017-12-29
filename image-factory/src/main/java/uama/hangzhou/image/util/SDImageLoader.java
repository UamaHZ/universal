package uama.hangzhou.image.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uama.hangzhou.image.R;

import static uama.hangzhou.image.util.ImageCalculateUtil.readPictureDegree;

/**
 * Created by GuJiaJia on 2017/12/25.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class SDImageLoader {
    //缓存
    private LruCache<String, Bitmap> imageCache;
    // 固定4个线程来执行任务
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private Handler handler = new Handler();

    public SDImageLoader() {
        // 获取应用程序最大可用内存
        int cacheSize = (int) Runtime.getRuntime().maxMemory();

        // 设置图片缓存大小为程序最大可用内存
        imageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }


    /**
     * 异步读取SD卡图片，并按指定的比例进行压缩（最大不超过屏幕像素数）
     *
     * @param filePath  图片在SD卡的全路径
     * @param imageView 组件
     */
    public void loadImage(final String filePath, final ImageView imageView) {
        samplingRateCompress(filePath, new ImageCallback() {
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

        imageView.setImageResource(R.mipmap.uimage_empty_photo);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void samplingRateCompress(final String filePath, final ImageCallback callback) {

        // 如果缓存过就从缓存中取出数据
        if (imageCache.get(filePath) != null) {
            handler.post(new Runnable() {
                public void run() {
                    callback.imageLoaded(imageCache.get(filePath));
                }
            });
        }
        executorService.submit(new Runnable() {
            public void run() {
                Bitmap bitmap = ImageCalculateUtil.getSmallBitmap(filePath, 480, 480);
                int angle = readPictureDegree(filePath);
                if (angle != 0) {
                    Matrix m = new Matrix();
                    int width1 = bitmap.getWidth();
                    int height1 = bitmap.getHeight();
                    m.setRotate(angle); // 旋转angle度
                    Bitmap tempBmp = bitmap;
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width1, height1, m, true);
                    tempBmp.recycle();
                }
                imageCache.put(filePath, bitmap);
                final Bitmap finalBitmap = bitmap;
                handler.post(new Runnable() {
                    public void run() {
                        callback.imageLoaded(finalBitmap);
                    }
                });
            }
        });
    }


    // 对外界开放的回调接口
    public interface ImageCallback {
        // 注意 此方法是用来设置目标对象的图像资源
        public void imageLoaded(Bitmap imageDrawable);
    }
}
