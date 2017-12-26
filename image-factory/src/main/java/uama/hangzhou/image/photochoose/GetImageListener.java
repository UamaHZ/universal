package uama.hangzhou.image.photochoose;

import java.util.List;

/**
 * Created by GuJiaJia on 2017/12/25.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public interface GetImageListener {
    void getComplete(List<String> imageList,boolean hasNext);
}
