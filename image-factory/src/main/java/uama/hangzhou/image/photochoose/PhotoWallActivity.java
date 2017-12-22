/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-27 下午12:44.
 */

package uama.hangzhou.image.photochoose;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uama.hangzhou.image.R;
import uama.hangzhou.image.constant.Constants;
import uama.hangzhou.image.util.CacheFileUtils;
import uama.hangzhou.image.util.DeviceUtils;
import uama.hangzhou.image.util.PhotoToastUtil;
import uama.hangzhou.image.util.SDCardImageLoader;

/**
 * 选择图片页面
 * Created by GuJiaJia
 */
public class PhotoWallActivity extends FragmentActivity implements View.OnClickListener {

    public static final String PhotoChooseParams = "PhotoChooseParams";

    private RelativeLayout title;
    private RecyclerView mPhotoWall;
    private TextView albumTitleBarSkip;
    private TextView tvNumber;

    private RecyclerImageAdapter adapter;
    public boolean firstIsCamera;//默认第一张图显示拍照
    private String mNewImageFilePath;
    private List<String> selectedImageList;
    private PhotoChooseParams chooseParams;

    private List<String> phoneImageList;//手机里面的图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uimage_photo_wall);
        chooseParams = (PhotoChooseParams) getIntent().getSerializableExtra(PhotoChooseParams);
        if (chooseParams == null) {
            PhotoToastUtil.showErrorDialog(PhotoWallActivity.this, R.string.uimage_no_param);
            finish();
        }
        phoneImageList = new ArrayList<>();
        initView();
    }

    private void initView() {
        title = (RelativeLayout) findViewById(R.id.ll_comm_topbar);
        albumTitleBarSkip = (TextView) findViewById(R.id.album_title_bar_skip);
        mPhotoWall = (RecyclerView) findViewById(R.id.photo_wall_grid);
        mPhotoWall.setLayoutManager(new GridLayoutManager(this,3));
        mPhotoWall.addItemDecoration(new SpaceItemDecoration((int)DeviceUtils.convertDpToPixel(this,2)));
        TextView tvConfirm = (TextView) findViewById(R.id.tv_photo_wall_confirm);
        tvNumber = (TextView) findViewById(R.id.tv_photo_wall_num);
        findViewById(R.id.album_title_bar_cancel).setOnClickListener(this);
        albumTitleBarSkip.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        initData();
    }

    private void initData() {
        if (chooseParams.getTitleColor() != 0) {
            //设置标题颜色
            title.setBackgroundColor(chooseParams.getTitleColor());
        }
        selectedImageList = chooseParams.getSelectedImages();//已经选择的图片地址
        firstIsCamera = chooseParams.isFirstIsCamera();
        albumTitleBarSkip.setVisibility(chooseParams.isShowSkipText() ? View.VISIBLE : View.INVISIBLE);
        if (firstIsCamera) {
            adapter = new RecyclerImageAdapter(this, phoneImageList, selectedImageList,
                    chooseParams.getMaxCounts(), chooseParams.getCheckBoxBg(), chooseParams.getDefaultCameraBg(), chooseParams.getDefaultCameraSrc());//第一张拍照
        } else {
            adapter = new RecyclerImageAdapter(this, phoneImageList, selectedImageList,
                    chooseParams.getMaxCounts());
        }
        mPhotoWall.setAdapter(adapter);
        if (firstIsCamera) {
            setChooseCounts(selectedImageList.size() - 1);
        } else {
            setChooseCounts(selectedImageList.size());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }).start();
    }


    //获取手机里面的数据
    private void getData() {
        phoneImageList.addAll(SDCardImageLoader.getImagePathsByContentProvider(this));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }

    //显示选择进度
    public void setChooseCounts(int chooseCounts) {
        if (chooseCounts > 0) {
            tvNumber.setVisibility(View.VISIBLE);
            tvNumber.setText(String.valueOf(chooseCounts));
        } else {
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
                if (firstIsCamera) {
                    selectedImageList.remove(0);
                }
                Intent intent = new Intent();
                intent.putStringArrayListExtra("paths", (ArrayList<String>) selectedImageList);
                setResult(Constants.PhotoChooseResultCode, intent);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.album_title_bar_cancel) {//取消
            finish();
        } else if (id == R.id.album_title_bar_skip) {//跳过
            setResult(Constants.PhotoChooseSkip);//跳过
            finish();
        } else if (id == R.id.tv_photo_wall_confirm) {//确认
            List<String> paths = adapter.getSelectList();
            if (paths == null || paths.size() == (firstIsCamera ? 1 : 0)) {
                PhotoToastUtil.showErrorDialog(PhotoWallActivity.this, R.string.uimage_no_choose_image);
                return;
            }
            if (firstIsCamera) {
                paths.remove(0);
            }
            Intent intent = new Intent();
            intent.putStringArrayListExtra("paths", (ArrayList<String>) paths);
            setResult(Constants.PhotoChooseResultCode, intent);
            finish();
        }
    }
}
