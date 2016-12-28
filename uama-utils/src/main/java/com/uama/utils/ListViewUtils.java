package com.uama.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by liwei on 2016/12/27 17:28
 * Description: ListView 相关工具类
 */

public class ListViewUtils {
    public static void setHeight(ListView listView) {
        /*获取adapter*/
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        /* listAdapter.getCount()返回数据项的数目*/
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        /*
         * listView.getDividerHeight()获取子项间分隔符占用的高度
         * params.height最后得到整个ListView完整显示需要的高度
         * */
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))+DensityUtils.dp2px(listView.getContext(),17);
        listView.setLayoutParams(params);
    }
}
