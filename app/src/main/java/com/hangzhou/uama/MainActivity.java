package com.hangzhou.uama;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import uama.hangzhou.image.album.Matisse;
import uama.hangzhou.image.album.MimeType;
import uama.hangzhou.image.album.engine.impl.GlideEngine;
import uama.hangzhou.image.album.internal.entity.CaptureStrategy;
import uama.hangzhou.image.constant.Constants;
import uama.hangzhou.image.photochoose.FourPicturesChoose;
import uama.hangzhou.image.photochoose.PhotoChoose;
import uama.hangzhou.image.widget.MyGridView;


public class MainActivity extends AppCompatActivity {
    private SimpleDraweeView zoomDrawView;
    private MyGridView myGridView;
    private FourPicturesChoose fourPicturesChoose;
    ImageView image1, image2, image3, image4;
    private PhotoChoose photoChoose;
    private TextView tv;
    private SparseArray<ImageView> viewSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(MainActivity.this);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tv = (TextView) findViewById(R.id.show_share);
        viewSparseArray = new SparseArray<>();
        image1 = (ImageView) findViewById(R.id.photo_choose_img1);
        image2 = (ImageView) findViewById(R.id.photo_choose_img2);
        image3 = (ImageView) findViewById(R.id.photo_choose_img3);
        image4 = (ImageView) findViewById(R.id.photo_choose_img4);
        viewSparseArray.put(0,image1);
        viewSparseArray.put(1,image2);
        viewSparseArray.put(2,image3);
        viewSparseArray.put(3,image4);
        fourPicturesChoose = new FourPicturesChoose(this,viewSparseArray);

        myGridView = (MyGridView) findViewById(R.id.grid_view_publish_photo);
        photoChoose = new PhotoChoose(this, myGridView, 4,3,true);
        photoChoose.setCanSkip(true);
        photoChoose.setRequestCode(1002,1100);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Matisse.from(MainActivity.this)
//                        .choose(MimeType.ofImage())
//                        .theme(R.style.Matisse_Dracula)
//                        .countable(false)
//                        .maxSelectable(9)
//                        .originalEnable(true)
//                        .maxOriginalSize(10)
//                        .imageEngine(new PicassoEngine())
//                        .forResult(100);
                Matisse.from(MainActivity.this)
                        .choose(MimeType.of(MimeType.JPEG,MimeType.PNG))
                        .countable(true)
                        .capture(true)
                        .captureStrategy(
                                new CaptureStrategy(true, BuildConfig.APPLICATION_ID+".provider"))
                        .theme(R.style.Matisse_Mine)
                        .maxSelectable(9)
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
//                        .setSkip(true)
                        .imageEngine(new GlideEngine())
                        .forResult(100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Constants.PhotoChooseSkip){
            Log.i("ailee","调到下一页");
        }
        fourPicturesChoose.setImageList(requestCode, resultCode, data);
        photoChoose.setImageList(requestCode,resultCode,data);
    }
}
