package uama.eagle.eye.util;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import great.sun.com.eagle_eye.R;
import uama.eagle.eye.listen.MyOnClickListener;

/**
 * Created by GuJiaJia on 2017/2/21.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class StyleUtil {
    /**
     * 只有左边返回键
     *
     * @param activity
     * @param title 标题
     */
    public static void titleBackKey(final Activity activity, String title) {
        ((TextView) activity.findViewById(R.id.title_text)).setText(title);
        ((ImageView) activity.findViewById(R.id.iv_back)).setVisibility(View.VISIBLE);
        ((ImageView) activity.findViewById(R.id.iv_back)).setBackgroundResource(R.mipmap.header_back);
        LinearLayout lvBack = (LinearLayout) activity.findViewById(R.id.ler_back);
        lvBack.setVisibility(View.VISIBLE);
        lvBack.setOnClickListener(new MyOnClickListener() {

            @Override
            public void onClick(View v) {
                if (Tool.isFastDoubleClick())
                    return;
                activity.finish();
            }
        });
    }
}
