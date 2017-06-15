package uama.hangzhou.image.photochoose;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
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
    private ImageView imgViewList[] = new ImageView[4];
    private Activity activity;
    public ArrayList<String> imageList;
    public String mNewImageFilePath;
    private int color;

    public FourPicturesChoose(Activity activity, ImageView imageView1, ImageView imageView2, ImageView imageView3, ImageView imageView4) {
        this.activity = activity;
        this.imageView1 = imageView1;
        this.imageView2 = imageView2;
        this.imageView3 = imageView3;
        this.imageView4 = imageView4;
        init();
    }
    public FourPicturesChoose(Activity activity, ImageView imageView1, ImageView imageView2, ImageView imageView3, ImageView imageView4,int color) {
        this.activity = activity;
        this.imageView1 = imageView1;
        this.imageView2 = imageView2;
        this.imageView3 = imageView3;
        this.imageView4 = imageView4;
        this.color = color;
        init();
    }
    @PermissionYes(302)
    private void getCamera(List<String> grantedPermissions) {
        goToTakePhoto();
    }

    @PermissionNo(302)
    private void noCamera(List<String> grantedPermissions) {
    }

    @PermissionYes(303)
    private void getExternal(List<String> grantedPermissions) {
        goToChooseImage();
    }

    @PermissionNo(303)
    private void noExternal(List<String> grantedPermissions) {
    }

    private void init() {
        imageList = new ArrayList<>();
        imgViewList[0] = imageView1;
        imgViewList[1] = imageView2;
        imgViewList[2] = imageView3;
        imgViewList[3] = imageView4;
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(0);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(1);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(2);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(3);
            }
        });
    }

    //弹出拍照、相册选择
    public void showPopupWindow(final int position) {
        final String strArray[];
        if (position < imageList.size()) {
            strArray = new String[]{activity.getString(R.string.uimage_delete)};
        } else {
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
        imgViewList[0].setImageResource(R.mipmap.uimage_camera_default);
        imgViewList[1].setImageResource(R.mipmap.uimage_camera_default_ext);
        imgViewList[2].setImageResource(R.mipmap.uimage_camera_default_ext);
        imgViewList[3].setImageResource(R.mipmap.uimage_camera_default_ext);
        for (int i = 0; i < imageList.size(); i++) {
            imgViewList[i].setImageBitmap(ImageSword.getImage(imageList.get(i)));
        }
    }

    //拍照
    public void goToTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mNewImageFilePath = CacheFileUtils.getUpLoadPhotosPath();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, new File(mNewImageFilePath).getAbsolutePath());
        Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        activity.startActivityForResult(intent, Constants.TAKE_PHOTO);
    }

    //选择照片
    private void goToChooseImage() {
        Intent intent = new Intent(activity, PhotoWallActivity.class);
        intent.putExtra(PhotoWallActivity.SelectedCounts, imageList);
        intent.putExtra(PhotoWallActivity.MaxCounts, 4);
        intent.putExtra(PhotoWallActivity.PHOTO_WALL_COLOR,color);
        activity.startActivityForResult(intent, Constants.SELECT_IMAGE);
    }

    public void setImageList(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                File imageFile = new File(mNewImageFilePath);
                Uri uri = Uri.fromFile(imageFile);
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                imageList.add(CacheFileUtils.getRealFilePath(activity, uri));
                upDateImageGroup();
            }
        } else if (requestCode == Constants.SELECT_IMAGE) {
            if (resultCode == 1991) {
                if (data == null) {
                    return;
                }
                imageList.clear();
                imageList.addAll(data.getStringArrayListExtra("paths"));
                upDateImageGroup();
            }
        }
    }

    //获取选中的图片list
    public ArrayList<String> getChosenImageList() {
        return imageList;
    }
}
