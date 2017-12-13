package uama.hangzhou.image.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by GuJiaJia on 2017/11/16.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class PhotoToastUtil {
    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showErrorDialog(Context context, int maxNum) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "最多可以选择" + maxNum + "张照片", Toast.LENGTH_SHORT);
        } else {
            mToast.setText("最多可以选择" + maxNum + "张照片");
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showErrorDialog(Context context, String tip) {
        if (mToast == null) {
            mToast = Toast.makeText(context, tip, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(tip);
        }
        mToast.show();
    }
}
