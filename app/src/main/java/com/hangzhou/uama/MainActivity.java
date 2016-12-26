package com.hangzhou.uama;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import uama.hangzhou.image.photochoose.ImagePagerActivity;
import uama.hangzhou.image.zoom.ZoomDrawView;


public class MainActivity extends AppCompatActivity {
    private SimpleDraweeView zoomDrawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(MainActivity.this);
        setContentView(R.layout.activity_main);
        zoomDrawView = (SimpleDraweeView) findViewById(R.id.image);
        zoomDrawView.setImageURI(Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/03087bf40ad162d9ec74553b14dfa9ec8a13cd7a.jpg"));
        zoomDrawView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> imageList = new ArrayList<String>();
                imageList.add("http://g.hiphotos.baidu.com/image/pic/item/03087bf40ad162d9ec74553b14dfa9ec8a13cd7a.jpg");
                Intent intent = new Intent(MainActivity.this, ImagePagerActivity.class);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 1);
                intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (ArrayList<String>) imageList);
                startActivity(intent);
            }
        });
    }
}
