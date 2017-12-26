package uama.hangzhou.image.listener;

import java.util.List;

/**
 * Created by GuJiaJia on 2017/12/26.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 * 拦截已选择点击事件（拦截删除）
 */

public interface SelectedViewClickListener {
    void click(int position, List<String> selectedList);
}
