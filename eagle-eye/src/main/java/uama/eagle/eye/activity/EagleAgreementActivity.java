/**
 * Project Name:my_project
 * File Name:ProtocolActivity.java
 * Package Name:com.lvman.activity.regist
 * Date:2014年11月3日上午10:56:39
 * Copyright (c) 2014.
 */

package uama.eagle.eye.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import great.sun.com.eagle_eye.R;
import uama.eagle.eye.listen.MyOnClickListener;
import uama.eagle.eye.util.PreferenceUtils;
import uama.eagle.eye.util.StyleUtil;
import uama.eagle.eye.util.Tool;
import uama.eagle.eye.widget.MyWebviewClient;

import static java.security.AccessController.getContext;


public class EagleAgreementActivity extends Activity {
    TextView content;

    WebView webView;
    TextView button;
    ProgressBar loadingImageView;
    private String eagle_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exp_qrcode_protocol);
        StyleUtil.titleBackKey(this,"鹰眼协议");
        eagle_url = getIntent().getStringExtra("EAGLE_EYE_PEOTOCOL");
        if(TextUtils.isEmpty(eagle_url)){

        }else {
            initMiddle();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initMiddle() {
        webView = (WebView) findViewById(R.id.protocol);
        loadingImageView = (ProgressBar) findViewById(R.id.loadingImageView);
        // 设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        webView.setWebViewClient(new MyWebviewClient(this));
        webView.setWebChromeClient(new MyWebChromeClient());
            webView.loadUrl(eagle_url);
        button = (TextView) findViewById(R.id.agree);
        button.setOnClickListener(new MyOnClickListener() {

            @Override
            public void onClick(View v) {
                if (Tool.isFastDoubleClick())
                    return;
                PreferenceUtils.setPrefBoolean(EagleAgreementActivity.this,"Eagle_Agreement_Show",false);
//                startActivity(new Intent(EagleAgreementActivity.this,EagleEyeActivity.class));
                finish();
            }
        });
    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (loadingImageView != null) {
                if (newProgress == 100) {
                    loadingImageView.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            button.setVisibility(View.VISIBLE);
                        }
                    }, 200);
                } else {
                    if (loadingImageView.getVisibility() == View.GONE)
                        loadingImageView.setVisibility(View.VISIBLE);
                    loadingImageView.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }
    }

}