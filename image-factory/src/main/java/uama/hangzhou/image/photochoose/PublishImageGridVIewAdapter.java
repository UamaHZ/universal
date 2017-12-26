/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-25 下午3:22.
 */

package uama.hangzhou.image.photochoose;

/*
 * Created by gujiajia on 2016/6/25.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import uama.hangzhou.image.R;
import uama.hangzhou.image.listener.SelectedViewClickListener;
import uama.hangzhou.image.util.DeviceUtils;
import uama.hangzhou.image.util.SDImageLoader;

public class PublishImageGridVIewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mImageList;
    private int maxCounts;
    private int height;
    private ShowChooseMenu showChooseMenu;
    private SDImageLoader sdImageLoader;
    private SelectedViewClickListener selectedViewClickListener;//已选择的view的点击事件

    PublishImageGridVIewAdapter(Context context, List<String> mImageList, int maxCounts, int columnCount, int divide,ShowChooseMenu showChooseMenu) {
        mContext = context;
        this.mImageList = mImageList;
        this.maxCounts = maxCounts;
        this.showChooseMenu = showChooseMenu;
        if(divide<0){
            this.height = (int) (DeviceUtils.getDisplayWidth(context) - DeviceUtils.convertDpToPixel(context, (columnCount + 1) * 15)) / columnCount;
        }else {
            this.height = divide;
        }
        if (this.mImageList == null) {
            this.mImageList = new ArrayList<>();
        }
        sdImageLoader = new SDImageLoader();
    }

    private void deleteItem(int position) {
        mImageList.remove(position);
        notifyDataSetChanged();
    }

    public void setClickListener(SelectedViewClickListener selectedViewClickListener){
        this.selectedViewClickListener = selectedViewClickListener;
    }
    @Override
    public int getCount() {
        if (mImageList.size() < maxCounts - 1) {
            return mImageList.size() + 1;
        } else {
            return maxCounts;
        }
    }

    @Override
    public Object getItem(int position) {
        return mImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.uimage_publish_gridview_item, parent, false);
            viewHolder.ivPic = (ImageView) convertView.findViewById(R.id.iv_grid_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivPic.setTag(intToObject(position));
        setRelativeLayoutWH(viewHolder.ivPic, height, height);
        viewHolder.ivPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (position < mImageList.size()) {
            String path = mImageList.get(position);
            viewHolder.ivPic.setTag(path);
            viewHolder.ivPic.setVisibility(View.VISIBLE);
            sdImageLoader.loadImage(path,viewHolder.ivPic);
        } else {
            if (position == maxCounts && !mImageList.isEmpty()) {
                viewHolder.ivPic.setVisibility(View.GONE);
            } else {
                viewHolder.ivPic.setVisibility(View.VISIBLE);
                viewHolder.ivPic.setImageResource(R.mipmap.morephoto_icon_add);
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageList.size() != maxCounts && getCount() - 1 == position) {
                    DeviceUtils.closeKeyBoard((Activity) mContext);
                    showChooseMenu.show();
                } else {
                    if(selectedViewClickListener != null){
                        selectedViewClickListener.click(position,mImageList);
                        return;
                    }
                    String strArray[] = {mContext.getString(R.string.uimage_delete)};
                    MessageDialog.showBottomMenu(mContext, strArray, new MessageDialog.MenuDialogOnItemClickListener() {
                        @Override
                        public void onItemClick(int index) {
                            switch (index) {
                                case 1:
                                    deleteItem(position);
                                    break;
                            }
                        }
                    });
                }
            }
        });
        return convertView;
    }

    private void setRelativeLayoutWH(View view, int width, int height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    class ViewHolder {
        ImageView ivPic;
    }

    public interface ShowChooseMenu {
        void show();
    }

    private Integer intToObject(int i) {
        try {
            return i;
        } catch (Exception e) {
            return 0;
        }
    }

    //加载本地图片
    public static void loadFileUrl(SimpleDraweeView sdv, String path) {
        sdv.setImageURI(Uri.parse("file://" + path));
    }
}
