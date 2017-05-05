package uama.eagle.eye.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebviewClient extends WebViewClient {
	Context context;
	
	public MyWebviewClient(Context context) {
		this.context = context;
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		addImageClickListner(view);
	}
	
	// 注入js函数监听
	private void addImageClickListner(WebView view) {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
		view.loadUrl("javascript:(function(){" + "var imgs = document.getElementsByTagName(\"img\"); "
				+ "var imgURLs=new Array(imgs.length);" + "for(var i=0;i<imgs.length;i++)  " + "{"
				+ "	   imgURLs[i] = imgs[i].src;" + "    imgs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.openImage(imgURLs,imgURLs.indexOf(this.src));  " + "    }  " + "}"
				+ "})()");
	}
	
}
