/**
 * Project Name:my_project
 * File Name:BitmapUtils.java
 * Package Name:com.lvman.utils
 * Date:2014年10月10日下午4:18:23
 * Copyright (c) 2014.
 *
 */

package com.uama.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:BitmapUtils <br/>
 * Function: bitmap工具类 Reason: TODO ADD REASON. <br/>
 * Date: 2014年10月10日 下午4:18:23 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class BitmapUtils {
	
	/**
	 * 获取图片文件的信息，是否旋转了90度，如果是则反转
	 * 
	 * @param bitmap 需要旋转的图片
	 * @param path 图片的路径
	 */
	public static Bitmap reviewPicRotate(Bitmap bitmap, String path) {
		int degree = readPictureDegree(path);
		if (degree != 0) {
			Matrix m = new Matrix();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			m.setRotate(degree); // 旋转angle度
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
		}
		return bitmap;
	}
	
	/**
	 * 解码图像，减少资源消耗
	 * 
	 * @param f
	 * @return
	 */
	public static Bitmap decodeFile(File f) {
		try {
			// 解码图像大小
			BitmapFactory.Options o = new BitmapFactory.Options();
			// If set to true, the decoder will return null (no bitmap), but the
			// out... fields will still be set, allowing the caller to query the
			// bitmap without having to allocate the memory for its pixels.
			// 意思就是说如果该值设为true那么将不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			
			// 找到正确的刻度值，它应该是2的幂。
			// final int REQUIRED_SIZE = 70;
			final int REQUIRED_SIZE = 300;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	
	/**
	 * 解码图像，减少内存消耗
	 * 
	 * @param f
	 * @param requiredSize 需要的尺寸,当宽或者高的一半=requiredSize，不再解码
	 * @return
	 */
	public static Bitmap decodeFile(File f, int requiredSize) {
		try {
			// 解码图像大小
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	
	public static Bitmap compressImage(Bitmap image) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	
	public static Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
		
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}
	
	/**
	 * 保存图片为JPEG
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static String saveJPGE_After(Bitmap bitmap, String path) {
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * 读取图片的旋转角度，三星等手机会自动旋转90°，这个时候需要根据exif来进行旋转操作先
	 * 
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	/**
	 * 更改角度
	 * 
	 * @param src 来源的bitmap
	 * @param angle 旋转的角度
	 * @return
	 */
	public static Bitmap changeDegree(Bitmap src, int angle) {
		if (src == null) {
			return null;
		}
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		Bitmap newb = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
		// 回收原图
		// src.recycle();
		return newb;
	}
	
	/**
	 * 以CenterCrop方式resize图片
	 * 
	 * @param src 原始图片
	 * @param destWidth 目标图片宽度
	 * @param destHeight 目标图片高度
	 * @param angle 旋转角度，根据图片的exif来进行处理
	 * @return
	 */
	public static Bitmap resizeBitmapByCenterCrop(Bitmap src, int destWidth, int destHeight, boolean bool, int angle) {
		if (src == null || destWidth == 0 || destHeight == 0) {
			return null;
		}
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 图片宽度
		int w = src.getWidth();
		// 图片高度
		int h = src.getHeight();
		// Imageview宽度
		int x = destWidth;
		// Imageview高度
		int y = destHeight;
		// 高宽比之差
		int temp = (y / x) - (h / w);
		/**
		 * 判断高宽比例，如果目标高宽比例大于原图，则原图高度不变，宽度为(w1 = (h * x) / y)拉伸 画布宽高(w1,h),在原图的((w - w1) / 2, 0)位置进行切割
		 */
		
		if (temp > 0) {
			// 计算画布宽度
			int w1 = (h * x) / y;
			// 创建一个指定高宽的图片
			Bitmap newb = Bitmap.createBitmap(src, (w - w1) / 2, 0, w1, h, matrix, true);
			// 原图回收
			if (bool && src != null && !src.isRecycled()) {
				src.recycle();
			}
			return newb;
		} else {
			/**
			 * 如果目标高宽比小于原图，则原图宽度不变，高度为(h1 = (y * w) / x), 画布宽高(w, h1), 原图切割点(0, (h - h1) / 2)
			 */
			
			// 计算画布高度
			int h1 = (y * w) / x;
			// 创建一个指定高宽的图片
			// 创建新的图片
			Bitmap newb = Bitmap.createBitmap(src, 0, (h - h1) / 2, w, h1, matrix, true);
			// 原图回收
			if (bool && src != null && !src.isRecycled()) {
				src.recycle();
			}
			return newb;
		}
	}
	
	public static Bitmap compressionPicture(Bitmap bitmap, int maxWidth, int maxHeight) {
		byte[] data = ImageUtils.Bitmap2Bytes(bitmap);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		
		opts.inSampleSize = computeSampleSize(opts, -1, maxWidth * maxHeight);
		opts.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
	}
	
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}
	
	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
				Math.floor(h / minSideLength));
		
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
	
	public static Bitmap getBitmap(String filePath, int width, int height) {
		
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, options);
			// int p = c.getResources().getDimensionPixelSize(R.dimen.parking_width);
			options.inSampleSize = calculateInSampleSize(options, width, height);
			System.out.println(options.inSampleSize);
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(filePath, options);
			
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError error) {
		}
		return null;
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		if (inSampleSize < 0) {
			inSampleSize = 1;
		}
		return inSampleSize;
	}
	
	public static int max = 0;
	public static boolean act_bool = true;
	public static List<Bitmap> bmp = new ArrayList<Bitmap>();
	
	// 图片sd地址 上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
	public static List<String> drr = new ArrayList<String>();
	
	public static Bitmap revitionImageSize(String path, Bitmap bitmap, Context context) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		while (true) {
			if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				options.inPurgeable = true;
				options.inInputShareable = true;  
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}

	/**
	 * 获取 Bitmap 大小
     */
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
	}

	/**
	 * 转换图片成圆形
	 *
	 * @param bitmap 传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width,
				height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	public static String compress(Context context, String uri) {
		String path = "";
		if (TextUtils.isEmpty(uri)) {
			return "";
		}
		FileOutputStream fos = null;
		try {
			int degree = readPictureDegree(uri);
			Bitmap save = rotaingImageView(degree, getBitmap(uri, 480, 480));
			String dir = getDiskCacheDir(context, "Camare");
			File tmpFile = new File(dir);
			if (!tmpFile.exists()) {
				tmpFile.mkdirs();
			}
			path = String.format("%s%s.jpg", dir, System.currentTimeMillis());
			fos = new FileOutputStream(path);
			save.compress(Bitmap.CompressFormat.JPEG, 40, fos);// 把数据写入文件

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流 并且回收图片
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	public static String getDiskCacheDir(Context context, String uniqueName) {
		final String cachePath =
				Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
						!isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() :
						context.getCacheDir().getPath();

		return cachePath + File.separator + uniqueName + File.separator;
	}

	public static boolean hasFroyo() {
		// Can use static final constants like FROYO, declared in later versions
		// of the OS since they are inlined at compile time. This is guaranteed
		// behavior.
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}



	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static boolean isExternalStorageRemovable() {
		return !hasGingerbread() || Environment.isExternalStorageRemovable();
	}


	@TargetApi(Build.VERSION_CODES.FROYO)
	public static File getExternalCacheDir(Context context) {
		if (hasFroyo()) {
			return context.getExternalCacheDir();
		}
		final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
	}

	/**
	 * 图片旋转
	 *
	 * @param angle
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
}
