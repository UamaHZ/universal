package uama.hangzhou.image.photochoose;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import uama.hangzhou.image.R;

/**
 * Created by GuJiaJia on 2017/12/22.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class ImageRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private OnRecycleItemClickListener myItemClickListener;
    ImageView imageView;
    CheckBox checkBox;
    public ImageRecyclerHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.photo_wall_item_photo);
        checkBox = (CheckBox) itemView.findViewById(R.id.photo_wall_item_cb);
    }


    @Override
    public void onClick(View v) {
        if(myItemClickListener != null){
            myItemClickListener.itemClick();
        }
    }
}
