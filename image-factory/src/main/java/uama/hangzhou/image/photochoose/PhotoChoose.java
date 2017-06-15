package uama.hangzhou.image.photochoose;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uama.hangzhou.image.R;
import uama.hangzhou.image.constant.Constants;
import uama.hangzhou.image.util.CacheFileUtils;
import uama.hangzhou.image.widget.MyGridView;

/**
 * Created by gujiajia on 2016/9/29.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 * 图片添加器，正常情况下
 */

public class PhotoChoose {
    private MyGridView myGridView;
    private PublishImageGridVIewAdapter imageGridVIewAdapter;
    private ArrayList<String> mImageList;//保存选择的图片非常重要
    private int maxCounts;//最大选择图片数量
    private Activity activity;
    public String mNewImageFilePath;
    private int color;

    public PhotoChoose(Activity activity, MyGridView myGridView, int maxCounts) {
        this.myGridView = myGridView;
        this.activity = activity;
        this.maxCounts = maxCounts;
        init();
    }
    public PhotoChoose(Activity activity, MyGridView myGridView, int maxCounts, @ColorRes int color) {
        this.myGridView = myGridView;
        this.activity = activity;
        this.maxCounts = maxCounts;
        this.color = color;
        init();
    }
    @PermissionYes(300)
    private void getCamera(List<String> grantedPermissions) {
        goToTakePhoto();
    }

    @PermissionNo(300)
    private void noCamera(List<String> grantedPermissions) {
    }

    @PermissionYes(301)
    private void getExternal(List<String> grantedPermissions) {
        goToChooseImage();
    }

    @PermissionNo(301)
    private void noExternal(List<String> grantedPermissions) {
    }

    private void init() {
        mImageList = new ArrayList<>();
        imageGridVIewAdapter = new PublishImageGridVIewAdapter(activity, mImageList, maxCounts, new PublishImageGridVIewAdapter.ShowChooseMenu() {
            @Override
            public void show() {
                showPopupWindow();
            }
        });
        myGridView.setAdapter(imageGridVIewAdapter);
    }

    //弹出拍照、相册选择
    public void showPopupWindow() {
        if (activity == null) {
            return;
        }
        String strArray[] = {activity.getString(R.string.uimage_choose_photo), activity.getString(R.string.uimage_take_camera)};
        MessageDialog.showBottomMenu(activity, strArray, new MessageDialog.MenuDialogOnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                switch (index) {
                    case 1:
                        AndPermission.with(activity)
                                .requestCode(301)
                                .callback(PhotoChoose.this)
                                .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                .start();
                        break;
                    case 2:
                        AndPermission.with(activity)
                                .requestCode(300)
                                .callback(PhotoChoose.this)
                                .permission(Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE)
                                .start();
                        break;
                }
            }
        });
    }

    //拍照
    public void goToTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mNewImageFilePath = CacheFileUtils.getUpLoadPhotosPath();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, new File(mNewImageFilePath).getAbsolutePath());
        Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//        Uri uri = Uri.fromFile(new File(mNewImageFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        activity.startActivityForResult(intent, Constants.TAKE_PHOTO);
    }

    //选择照片
    private void goToChooseImage() {
        Intent intent = new Intent(activity, PhotoWallActivity.class);
        intent.putExtra(PhotoWallActivity.SelectedCounts, mImageList);
        intent.putExtra(PhotoWallActivity.MaxCounts, maxCounts);
        intent.putExtra(PhotoWallActivity.PHOTO_WALL_COLOR,color);
        activity.startActivityForResult(intent, Constants.SELECT_IMAGE);
    }

    public void setImageList(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SELECT_IMAGE) {
            if (resultCode == 1991) {
                if (data == null) {
                    return;
                }
                PublishImageGridVIewAdapter adapter = (PublishImageGridVIewAdapter) myGridView.getAdapter();
                mImageList.clear();
                mImageList.addAll(data.getStringArrayListExtra("paths"));
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == Constants.TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                File imageFile = new File(mNewImageFilePath);
                Uri uri = Uri.fromFile(imageFile);
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                PublishImageGridVIewAdapter adapter = (PublishImageGridVIewAdapter) myGridView.getAdapter();
                mImageList.add(CacheFileUtils.getRealFilePath(activity, uri));
                adapter.notifyDataSetChanged();
            }
        }
    }

    //获取选中的图片列表
    public ArrayList<String> getChosenImageList() {
        return mImageList;
    }
}
