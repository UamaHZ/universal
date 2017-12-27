package uama.hangzhou.image.photochoose;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uama.hangzhou.image.R;
import uama.hangzhou.image.album.Matisse;
import uama.hangzhou.image.album.MimeType;
import uama.hangzhou.image.album.engine.impl.GlideEngine;
import uama.hangzhou.image.album.internal.entity.CaptureStrategy;
import uama.hangzhou.image.constant.Constants;
import uama.hangzhou.image.listener.SelectedViewClickListener;
import uama.hangzhou.image.util.CacheFileUtils;
import uama.hangzhou.image.widget.MyGridView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by gujiajia on 2016/9/29.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 * 图片添加器，正常情况下
 */

public class PhotoChoose {
    private MyGridView myGridView;
    private ArrayList<String> mImageList;//保存选择的图片非常重要
    private int maxCounts;//最大选择图片数量
    private int columnCount = 4;//一行显示item的数量
    private Activity activity;
    private String mNewImageFilePath;
    private boolean firstIsCamera = true;
    private int divide = -1;//间距
    private PublishImageGridVIewAdapter imageGridVIewAdapter;
    private boolean canSkip = true;//是否有跳过

    /**
     * @param maxCounts   最大选择图片数量
     * @param columnCount 一行显示数量
     */
    public PhotoChoose(Activity activity, MyGridView myGridView, int maxCounts, int columnCount) {
        this.myGridView = myGridView;
        this.activity = activity;
        this.maxCounts = maxCounts;
        this.columnCount = columnCount;
        myGridView.setNumColumns(columnCount);
        init();
    }

    /**
     * @param maxCounts     最大选择图片数量
     * @param columnCount   一行显示数量
     * @param firstIsCamera 是否带拍照的选择图片
     */
    public PhotoChoose(Activity activity, MyGridView myGridView, int maxCounts, int columnCount, boolean firstIsCamera) {
        this.myGridView = myGridView;
        this.activity = activity;
        this.maxCounts = maxCounts;
        this.columnCount = columnCount;
        this.firstIsCamera = firstIsCamera;
        myGridView.setNumColumns(columnCount);
        init();
    }

    /**
     * @param maxCounts     最大选择图片数量
     * @param columnCount   一行显示数量
     * @param divide        间距
     * @param firstIsCamera 是否带拍照的选择图片
     *                      一行显示的数量的间距必须对应上使用
     */
    public PhotoChoose(Activity activity, MyGridView myGridView, int maxCounts, int columnCount, int divide, boolean firstIsCamera) {
        this.myGridView = myGridView;
        this.activity = activity;
        this.maxCounts = maxCounts;
        this.columnCount = columnCount;
        this.firstIsCamera = firstIsCamera;
        this.divide = divide;
        myGridView.setNumColumns(columnCount);
        init();
    }



    private void init() {
        mImageList = new ArrayList<>();
        imageGridVIewAdapter = new PublishImageGridVIewAdapter(activity, mImageList,
                maxCounts, columnCount, divide, new PublishImageGridVIewAdapter.ShowChooseMenu() {
            @Override
            public void show() {
                if (firstIsCamera) {
                    AndPermission.with(activity)
                            .requestCode(302)
                            .callback(PhotoChoose.this)
                            .permission(Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)
                            .start();
                } else {
                    showPopupWindow();
                }
            }
        });
        myGridView.setAdapter(imageGridVIewAdapter);
    }

    //弹出拍照、相册选择
    private void showPopupWindow() {
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
    private void goToTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mNewImageFilePath = CacheFileUtils.getUpLoadPhotosPath();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, new File(mNewImageFilePath).getAbsolutePath());
        Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        activity.startActivityForResult(intent, Constants.ONLY_TAKE_PHOTO);
    }

    //选择照片
    private void goToChooseImage() {
        Matisse.from(activity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(true)
                .capture(firstIsCamera)
                .captureStrategy(
                        new CaptureStrategy(true, activity.getString(R.string.aplicationId)+".provider"))
                .theme(R.style.Matisse_Uama)
                .maxSelectable(maxCounts - mImageList.size())
                .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.uimage_grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .setSkip(canSkip)
                .imageEngine(new GlideEngine())
                .forResult(Constants.ONLY_SELECT_IMAGE);
    }

    public void setImageList(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Constants.ONLY_SELECT_IMAGE) {
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    mImageList.addAll(Matisse.obtainPathResult(data));
                    imageGridVIewAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == Constants.ONLY_TAKE_PHOTO) {
                if (resultCode == RESULT_OK) {
                    File imageFile = new File(mNewImageFilePath);
                    Uri uri = Uri.fromFile(imageFile);
                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    mImageList.add(CacheFileUtils.getRealFilePath(activity, uri));
                    imageGridVIewAdapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCanSkip(boolean canSkip){
        this.canSkip = canSkip;
    }
    //设置已选择图片的点击事件
    public void setSelectedImageClickListener(SelectedViewClickListener selectedImageClickListener) {
        imageGridVIewAdapter.setClickListener(selectedImageClickListener);
    }

    //获取选中的图片列表
    public ArrayList<String> getChosenImageList() {
        return mImageList;
    }

    //设置选中的图片列表
    public void setChooseImageList(ArrayList<String> list) {
        if (list == null) {
            return;
        }
        mImageList.clear();
        mImageList.addAll(list);
        ((PublishImageGridVIewAdapter) myGridView.getAdapter()).notifyDataSetChanged();
    }


    @PermissionYes(300)
    private void getCamera(List<String> grantedPermissions) {
        goToTakePhoto();
    }

    @PermissionNo(300)
    private void noCamera(List<String> grantedPermissions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setMessage("无法使用此功能，请检查是否已经打开摄像头或文件读取权限。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false);
        builder.show();
    }

    @PermissionYes(301)
    private void getExternal(List<String> grantedPermissions) {
        goToChooseImage();
    }

    @PermissionNo(301)
    private void noExternal(List<String> grantedPermissions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setMessage("无法使用此功能，请检查是否已经打开摄像头或文件读取权限。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false);
        builder.show();
    }

    @PermissionYes(302)
    private void getExternalAndCamera(List<String> grantedPermissions) {
        goToChooseImage();
    }

    @PermissionNo(302)
    private void noExternalAndCamera(List<String> grantedPermissions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setMessage("无法使用此功能，请检查是否已经打开摄像头或文件读取权限。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false);
        builder.show();
    }
}
