package com.hangzhou.uama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

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
        fourPicturesChoose = new FourPicturesChoose(this,viewSparseArray);
//        fourPicturesChoose.setTitleColor(ContextCompat.getColor(this, R.color.green_light));
        fourPicturesChoose.setCheckBackground(R.drawable.ols_comment_checkbox);
        fourPicturesChoose.setFirstSelectBg(R.mipmap.ols_comment_checkbox_normal);

        myGridView = (MyGridView) findViewById(R.id.grid_view_publish_photo);
        photoChoose = new PhotoChoose(this, myGridView, 4,3,true);
        photoChoose.setCheckBackground(R.drawable.uimage_selector_checkbox);
//        photoChoose.setTitleColor(ContextCompat.getColor(this, R.color.green_light));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
