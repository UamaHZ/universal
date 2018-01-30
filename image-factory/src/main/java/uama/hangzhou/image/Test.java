package uama.hangzhou.image;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GuJiaJia on 2018/1/30.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class Test {
    public static void main(String[] arg) {
        String url = "http://c.hiphotos.baidu.com/image/pic/item/91529822720e0cf3457ad8150146f21fbf09aa4b.jpg";
        String urls = "http://121.40.102.80:7080//img/201801/nei/nei151721708493445.jpg";
        System.out.print("是否是Url:" + isHttpUrl(urls));
    }

    public static boolean isHttpUrl(String urls) {
        if (!TextUtils.isEmpty(urls) && urls.length() > 5) {
            if (urls.substring(0, 3).toLowerCase().equals("ftp") || urls.substring(0, 5).toLowerCase().equals("http:")) {
                return true;
            }
        }
        return false;

    }
}
