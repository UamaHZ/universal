package com.hangzhou.uama;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uama.hangzhou.image.zoom.ZoomDrawView;

public class Main2Activity extends AppCompatActivity {

    private ZoomDrawView zoomDrawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        zoomDrawView = (ZoomDrawView) findViewById(R.id.img_layout);
        zoomDrawView.setImageURI(Uri.parse("http://121.40.102.80:7080//img/201707/nei/nei150097441790228.jpg"));
    }
}
