package uama.hangzhou.image.photochoose;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by GuJiaJia on 2017/12/22.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;
        outRect.bottom = space;
    }
}
