package com.hangzhou.uama;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by jams_zhen on 2016/12/26.
 */

public class SeApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        UMShareAPI.get(this);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wxbc08ff581e7503d0", "a0af1bd4ad13f380339edbff7c35c125");
        PlatformConfig.setQQZone("1105872880", "AEmqTlYqUq5o1t6A");

    }
}
