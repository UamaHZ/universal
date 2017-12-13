package uama.hangzhou.image.photochoose;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uama.hangzhou.image.R;
import uama.hangzhou.image.constant.Constants;
import uama.hangzhou.image.util.CacheFileUtils;
import uama.hangzhou.image.util.ImageSword;


/**
 * Created by gujiajia on 2016/9/29.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 * 4 张占位图的选择器
 */

public class FourPicturesChoose {
    private ImageView imageView1, imageView2, imageView3, imageView4;
    private SparseArray<ImageView> imageViewList;
    private Activity activity;
    private ArrayList<String> imageList;
    private String mNewImageFilePath;
    private int color;
    private int checkBg;
    private int firstDefaultBg = -1, secondDefaultBg = -1;//第一张和第二张的默认背景
    private boolean firstIsCamera = true;
    private int cameraBg;//第一张拍照背景图
    private int cameraSrc;//第一张图片资源图

    public FourPicturesChoose(Activity activity, SparseArray<ImageView> nImageViewList) {
        this.activity = activity;
        this.imageViewList = nImageViewList;
        if (imageViewList != null && imageViewList.size() > 0) {
            init();
        }
    }

    public FourPicturesChoose(Activity activity, SparseArray<ImageView> nImageViewList,boolean firstIsCamera) {
        this.activity = activity;
        this.imageViewList = nImageViewList;
        this.firstIsCamera = firstIsCamera;
        if (imageViewList != null && imageViewList.size() > 0) {
            init();
        }
    }
    private void init() {
        imageList = new ArrayList<>();
        switch (imageViewList.size()) {
            case 1:
                imageView1 = imageViewList.get(0);
                break;
            case 2:
                imageView1 = imageViewList.get(0);
                imageView2 = imageViewList.get(1);
                break;
            case 3:
                imageView1 = imageViewList.get(0);
                imageView2 = imageViewList.get(1);
                imageView3 = imageViewList.get(2);
                break;
            case 4:
                imageView1 = imageViewList.get(0);
                imageView2 = imageViewList.get(1);
                imageView3 = imageViewList.get(2);
                imageView4 = imageViewList.get(3);
                break;
            default:
                break;
        }
        if (imageView1 != null) {
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(0);
                }
            });
        }
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(1);
                }
            });
        }

        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(2);
                }
            });
        }

        if (imageView4 != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(3);
                }
            });
        }

    }

    //弹出拍照、相册选择
    private void showPopupWindow(final int position) {
        final String strArray[];
        if (position < imageList.size()) {
            strArray = new String[]{activity.getString(R.string.uimage_delete)};
        } else {
            if(firstIsCamera){
                AndPermission.with(activity)
                        .requestCode(304)
                        .callback(FourPicturesChoose.this)
                        .permission(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .start();
                return;
            }
            strArray = new String[]{activity.getString(R.string.uimage_choose_photo), activity.getString(R.string.uimage_take_camera)};
        }
        MessageDialog.showBottomMenu(activity, strArray, new MessageDialog.MenuDialogOnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                if (position < imageList.size()) {
                    if (index == 1) {
                        imageList.remove(position);
                        upDateImageGroup();
                    }
                    return;
                }
                switch (index) {
                    case 1:
                        AndPermission.with(activity)
                                .requestCode(303)
                                .callback(FourPicturesChoose.this)
                                .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                .start();
                        break;
                    case 2:
                        AndPermission.with(activity)
                                .requestCode(302)
                                .callback(FourPicturesChoose.this)
                                .permission(Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE)
                                .start();
                        break;
                }
            }
        });
    }


    //刷新选中的图片
    private void upDateImageGroup() {
        try {
            setDefaultBg();
            for (int i = 0; i < imageList.size(); i++) {
                imageViewList.get(i).setImageBitmap(ImageSword.getImage(imageList.get(i)));
            }
        } catch (Exception e) {
        }
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
        activity.startActivityForResult(intent, Constants.FOUR_TAKE_PHOTO);
    }

    //选择照片
    private void goToChooseImage() {
        Intent intent = new Intent(activity, PhotoWallActivity.class);
        intent.putExtra(PhotoWallActivity.SelectedImages, imageList);
        intent.putExtra(PhotoWallActivity.MaxCounts, imageViewList.size());
        intent.putExtra(PhotoWallActivity.PHOTO_WALL_COLOR, color);
        intent.putExtra(PhotoWallActivity.CHECK_BOX_BG, checkBg);
        intent.putExtra(PhotoWallActivity.FirstCAMERA, firstIsCamera);
        intent.putExtra(PhotoWallActivity.CAMERA_BG, cameraBg);
        intent.putExtra(PhotoWallActivity.CAMERA_SRC, cameraSrc);
        activity.startActivityForResult(intent, Constants.FOUR_SELECT_IMAGE);
    }

    public void setImageList(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Constants.FOUR_TAKE_PHOTO) {
                if (resultCode == Activity.RESULT_OK) {
                    File imageFile = new File(mNewImageFilePath);
                    Uri uri = Uri.fromFile(imageFile);
                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    imageList.add(CacheFileUtils.getRealFilePath(activity, uri));
                    upDateImageGroup();
                }
            } else if (requestCode == Constants.FOUR_SELECT_IMAGE) {
                if (resultCode == 1991) {
                    if (data == null) {
                        return;
                    }
                    imageList.clear();
                    imageList.addAll(data.getStringArrayListExtra("paths"));
                    upDateImageGroup();
                }
            }
        } catch (Exception e) {
        }
    }

    //设置默认图片
    private void setDefaultBg() {
        try {
            for (int i = 0; i < imageViewList.size(); i++) {
                if (imageViewList.get(i) != null) {
                    if (i == 0) {
                        imageViewList.get(i).setImageResource(firstDefaultBg == -1 ? R.mipmap.uimage_camera_default : firstDefaultBg);
                    } else if (i == 1) {
                        imageViewList.get(i).setImageResource(secondDefaultBg == -1 ? R.mipmap.uimage_camera_default_ext : secondDefaultBg);
                    } else {
                        imageViewList.get(i).setImageResource(R.mipmap.uimage_camera_default_ext);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    //获取选中的图片list
    public ArrayList<String> getChosenImageList() {
        return imageList;
    }

    //设置title颜色
    public void setTitleColor(int color) {
        this.color = color;
    }

    //设置checkBox的背景
    public void setCheckBackground(int checkBg) {
        this.checkBg = checkBg;
    }

    //设置第一张默认图
    public void setFirstSelectBg(int bg) {
        firstDefaultBg = bg;
        setDefaultBg();
    }

    //设置第二张默认图
    public void setSecondSelectBg(int bg) {
        secondDefaultBg = bg;
        setDefaultBg();
    }

    //设置第一张图片背景图
    public void setCameraBg(int cameraBg){this.cameraBg = cameraBg;}

    //设置第一张图片资源
    public void setCameraSrc(int cameraSrc){this.cameraSrc = cameraSrc;}

    @PermissionYes(302)
    private void getCamera(List<String> grantedPermissions) {
        goToTakePhoto();
    }

    @PermissionNo(302)
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

    @PermissionYes(303)
    private void getExternal(List<String> grantedPermissions) {
        goToChooseImage();
    }

    @PermissionNo(303)
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

    @PermissionYes(304)
    private void getExternalAndCamera(List<String> grantedPermissions) {
        goToChooseImage();
    }

    @PermissionNo(304)
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
