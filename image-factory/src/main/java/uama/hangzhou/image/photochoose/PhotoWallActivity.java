/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-27 下午12:44.
 */

package uama.hangzhou.image.photochoose;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import uama.hangzhou.image.R;
import uama.hangzhou.image.constant.Constants;
import uama.hangzhou.image.util.CacheFileUtils;
import uama.hangzhou.image.util.PhotoToastUtil;


/**
 * 选择图片页面
 * Created by GuJiaJia
 */
public class PhotoWallActivity extends FragmentActivity {
    private ImageView cancel;
    private TextView albumTitleBarSkip;
    private TextView tvConfirm;
    private TextView tvNumber;
    private PhotoWallAdapter adapter;
    private int maxNum;//最大选择图片数量
    public static String MaxCounts = "MaxCounts";//最大选择图片数量
    public static String SelectedImages = "SelectCounts";//已选择图片地址
    public static String PHOTO_WALL_COLOR = "PHOTO_WALL_COLOR";//标题背景色
    public static String CHECK_BOX_BG = "CHECK_BOX_BG";//选择按钮样式
    public static String CAMERA_BG = "CAMERA_BG";//第一张拍照背景图
    public static String CAMERA_SRC = "CAMERA_SRC";//第一张图片资源图
    public static String FirstCAMERA = "FirstCAMERA";//默认第一张图显示拍照
    public boolean FirstCamera;//默认第一张图显示拍照
    private String mNewImageFilePath;
    private ArrayList<String> selectedImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uimage_photo_wall);
        tvConfirm = (TextView) findViewById(R.id.tv_photo_wall_confirm);
        tvNumber = (TextView) findViewById(R.id.tv_photo_wall_num);
        int titleColor = getIntent().getIntExtra(PHOTO_WALL_COLOR, 0);//必须为资源
        RelativeLayout title = (RelativeLayout) findViewById(R.id.ll_comm_topbar);
        int checkBox_bg = getIntent().getIntExtra(CHECK_BOX_BG, 0);
        int cameraBg = getIntent().getIntExtra(CAMERA_BG, 0);
        int cameraSrc = getIntent().getIntExtra(CAMERA_SRC, 0);
        FirstCamera = getIntent().getBooleanExtra(FirstCAMERA, true);
        if (titleColor != 0) {
            title.setBackgroundColor(titleColor);
        }
        cancel = (ImageView) findViewById(R.id.album_title_bar_cancel);
        albumTitleBarSkip = (TextView) findViewById(R.id.album_title_bar_skip);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        albumTitleBarSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(1999);//跳过
                finish();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> paths = adapter.getSelectList();
                if (FirstCamera) {
                    paths.remove(0);
                }
                if(paths == null || paths.size()==0){
                    PhotoToastUtil.showErrorDialog(PhotoWallActivity.this, "您还没有选择图片");
                    return;
                }
                Intent intent = new Intent();
                intent.putStringArrayListExtra("paths", paths);
                setResult(1991, intent);
                finish();
            }
        });
        maxNum = getIntent().getIntExtra(MaxCounts, 9);
        selectedImageList = getIntent().getStringArrayListExtra(SelectedImages);//已经选择的图片地址
        if (selectedImageList == null) {
            selectedImageList = new ArrayList<>();
        }
        GridView mPhotoWall = (GridView) findViewById(R.id.photo_wall_grid);
        if (FirstCamera) {
            adapter = new PhotoWallAdapter(this, getImagePathsByContentProvider(), selectedImageList,
                    maxNum, checkBox_bg, cameraBg, cameraSrc);//第一张拍照
        } else {
            adapter = new PhotoWallAdapter(this, getImagePathsByContentProvider(), selectedImageList,
                    maxNum);
        }
        mPhotoWall.setAdapter(adapter);
        if (FirstCamera) {
            setChooseCounts(selectedImageList.size() - 1);
        } else {
            setChooseCounts(selectedImageList.size());
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 使用ContentProvider读取SD卡所有图片。
     */
    private ArrayList<String> getImagePathsByContentProvider() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpg和png的图片
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        ArrayList<String> cursorList = null;
        if (cursor != null) {
            //从最新的图片开始读取.
            //当cursor中没有数据时，cursor.moveToLast()将返回false
            if (cursor.moveToLast()) {
                cursorList = new ArrayList<>();

                while (true) {
                    // 获取图片的路径
                    String path = cursor.getString(0);
                    if (fileIsExists(path)) {
                        cursorList.add(path);
                    }
                    if (!cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }
        return cursorList;
    }

    //获取文件大小
    public long getFileSizes(File f) throws Exception {

        long s = 0;
        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件夹不存在");
        }

        return s;
    }

    //判断图片是否有效
    public boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {//路径不存在
                return false;
            }
            if (getFileSizes(f) <= 0) {//图片大小为0
                return false;
            }
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            if (w <= 0 || h <= 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    //显示选择进度
    public void setChooseCounts(int chooseCounts) {
        if(chooseCounts>0){
            tvNumber.setVisibility(View.VISIBLE);
            tvNumber.setText(String.valueOf(chooseCounts));
        }else {
            tvNumber.setVisibility(View.GONE);
        }
    }


    //拍照
    public void goToTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mNewImageFilePath = CacheFileUtils.getUpLoadPhotosPath();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, new File(mNewImageFilePath).getAbsolutePath());
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        startActivityForResult(intent, Constants.PhotoWallActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PhotoWallActivity) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImageList.add(mNewImageFilePath);
                if (FirstCamera) {
                    selectedImageList.remove(0);
                }
                Intent intent = new Intent();
                intent.putStringArrayListExtra("paths", selectedImageList);
                setResult(1991, intent);
                finish();
            }
        }
    }
}
