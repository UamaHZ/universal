package com.hangzhou.uama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import uama.hangzhou.image.photochoose.FourPicturesChoose;
import uama.hangzhou.image.photochoose.PhotoChoose;
import uama.hangzhou.image.widget.MyGridView;


public class MainActivity extends AppCompatActivity {
    private SimpleDraweeView zoomDrawView;
    private MyGridView myGridView;
    private FourPicturesChoose fourPicturesChoose;
    ImageView image1, image2, image3, image4;
    //    private ShareView shareView;
    private PhotoChoose photoChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(MainActivity.this);
        setContentView(R.layout.activity_main);
        init();
//        shareView = (ShareView) findViewById(R.id.share_view);
//        shareView.setDefaultJPMap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), "ssss");
//        findViewById(R.id.show_share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareView.setVisibility(View.VISIBLE);
//            }
//        });
//        myGridView = (MyGridView) findViewById(R.id.grid_view_publish_photo);
        photoChoose = new PhotoChoose(this, myGridView, 9);
//        zoomDrawView = (SimpleDraweeView) findViewById(R.id.image);
//        zoomDrawView.setImageURI(Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/03087bf40ad162d9ec74553b14dfa9ec8a13cd7a.jpg"));
//        zoomDrawView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<String> imageList = new ArrayList<String>();
//                imageList.add("http://g.hiphotos.baidu.com/image/pic/item/03087bf40ad162d9ec74553b14dfa9ec8a13cd7a.jpg");
//                Intent intent = new Intent(MainActivity.this, ImagePagerActivity.class);
//                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 1);
//                intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (ArrayList<String>) imageList);
//                startActivity(intent);
//            }
//        });
    }

    private void init() {
        image1 = (ImageView) findViewById(R.id.photo_choose_img1);
        image2 = (ImageView) findViewById(R.id.photo_choose_img2);
        image3 = (ImageView) findViewById(R.id.photo_choose_img3);
        image4 = (ImageView) findViewById(R.id.photo_choose_img4);
        fourPicturesChoose = new FourPicturesChoose(this, image1, image2, image3, image4, ContextCompat.getColor(this, R.color.green_light),
                100);
        myGridView = (MyGridView) findViewById(R.id.grid_view_publish_photo);
        photoChoose = new PhotoChoose(this, myGridView, 6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fourPicturesChoose.setImageList(requestCode, resultCode, data);
    }
}
