package uama.hangzhou.image.photochoose;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import uama.hangzhou.image.R;
import uama.hangzhou.image.browse.ImagePagerActivity;
import uama.hangzhou.image.util.PhotoToastUtil;
import uama.hangzhou.image.util.SDCardImageLoader;
import uama.hangzhou.image.util.SDImageLoader;

/**
 * Created by GuJiaJia on 2017/12/22.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class RecyclerImageAdapter extends RecyclerView.Adapter<ImageRecyclerHolder>{
    private Context context;
    private List<String> imagePathList = null;
    private int maxNum;
    private List<String> selectedImageList;
    private int customCheckBoxBg;
    private int cameraBg;
    private int cameraSrc;
    private boolean firstIsCamera;
    private SDImageLoader sdImageLoader;

    RecyclerImageAdapter(Context context, List<String> imagePathList, List<String> selectedImageList,
                     int maxNum) {
        this.context = context;
        this.imagePathList = imagePathList;
        this.selectedImageList = selectedImageList;
        this.maxNum = maxNum;
        sdImageLoader = new SDImageLoader();
    }

    RecyclerImageAdapter(Context context, List<String> imagePathList, List<String> selectedImageList,
                     int maxNum, int customCheckBoxBg) {
        this.context = context;
        this.imagePathList = imagePathList;
        this.selectedImageList = selectedImageList;
        this.maxNum = maxNum;
        this.customCheckBoxBg = customCheckBoxBg;
        sdImageLoader = new SDImageLoader();
    }

    RecyclerImageAdapter(Context context, List<String> imagePathList, List<String> selectedImageList,
                     int maxNum, int customCheckBoxBg, int cameraBg, int cameraSrc) {
        this.context = context;
        this.imagePathList = imagePathList;
        this.selectedImageList = selectedImageList;
        this.maxNum = maxNum;
        this.customCheckBoxBg = customCheckBoxBg;
        this.cameraBg = cameraBg;
        this.cameraSrc = cameraSrc;
        firstIsCamera = true;
        selectedImageList.add(0, "camera");
        sdImageLoader = new SDImageLoader();
    }

    @Override
    public ImageRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.uimage_photo_wall_item, null);
        return new ImageRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageRecyclerHolder holder,final int position) {
        final String filePath = imagePathList.get(position);
        try {
            if(customCheckBoxBg>0){
                holder.checkBox.setBackgroundResource(customCheckBoxBg);
            }
        }catch (Exception e){
        }
        boolean bSelected = isSelected(filePath);
        if (bSelected) {
            holder.imageView.setColorFilter(context.getResources().getColor(R.color.uimage_image_checked_bg));
        } else {
            holder.imageView.setColorFilter(null);
        }

        holder.checkBox.setChecked(bSelected);

        if (!TextUtils.isEmpty(filePath)) {
            if (firstIsCamera && position == 0) {
                holder.imageView.setTag(filePath);
                holder.imageView.setBackgroundResource(cameraBg <= 0 ? R.mipmap.camera_bg_blur : cameraBg);
                holder.imageView.setImageResource(cameraSrc <= 0 ? R.mipmap.camera_icon_88black : cameraSrc);
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER);
                holder.checkBox.setVisibility(View.GONE);
            } else {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.imageView.setTag(filePath);
                sdImageLoader.loadImage(filePath, holder.imageView);
            }
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected(filePath)) {
                    if (selectedImageList.size() > (firstIsCamera ? maxNum : maxNum - 1)) {
                        PhotoToastUtil.showErrorMax(context, maxNum);
                        holder.checkBox.setChecked(false);
                        return;
                    } else {
                        if(SDCardImageLoader.checkIsBitmap(filePath)){
                            selectedImageList.add(filePath);
                            holder.checkBox.setChecked(true);
                            holder.imageView.setColorFilter(context.getResources().getColor(R.color.uimage_image_checked_bg));
                        }else {
                            holder.checkBox.setChecked(false);
                            PhotoToastUtil.showErrorDialog(context, context.getString(R.string.uimage_image_is_unvalid));
                        }
                    }
                } else {
                    selectedImageList.remove(filePath);
                    holder.imageView.setColorFilter(null);
                    holder.checkBox.setChecked(false);
                }

                ((PhotoWallActivity) context).setChooseCounts(firstIsCamera ? selectedImageList.size() - 1 : selectedImageList.size());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstIsCamera && position == 0) {
                    if (selectedImageList.size() > maxNum) {
                        PhotoToastUtil.showErrorDialog(context, maxNum);
                        return;
                    }
                    //调起拍照
                    ((PhotoWallActivity) context).goToTakePhoto();
                    return;
                }
                List<String> imageList = new ArrayList<>();
                imageList.add("file://" + filePath);
                Intent intent = new Intent(context, ImagePagerActivity.class);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                intent.putExtra(ImagePagerActivity.CAN_SAVE, false);
                intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (ArrayList<String>) imageList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagePathList == null ? 0 : imagePathList.size();
    }

    public List<String> getSelectList() {
        return selectedImageList;
    }

    private boolean isSelected(String path) {
        int selectCounts = selectedImageList.size();
        for (int i = 0; i < selectCounts; i++) {
            if (path.equals(selectedImageList.get(i))) {
                return true;
            }
        }
        return false;
    }
}
