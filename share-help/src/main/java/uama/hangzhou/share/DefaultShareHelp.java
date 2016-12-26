package uama.hangzhou.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 分享默认帮助类，实现目前已知的默认分享
 * Created by jams_zhen on 2016/12/23.
 */

public class DefaultShareHelp implements InterShare,UMShareListener {

    private Context mContext;
    private Bitmap jiepingMap;
    private ShareAction shareAction;
    private ShareContent shareContent;
    private String msg;

    public DefaultShareHelp(Context context){
        this.mContext=context;
    }
    //仅设置截屏分享,多数情况
    public void setDefaultJPMap(Bitmap jpMap,String msg){
        this.jiepingMap=jpMap;
        this.msg=msg;
    }

    /**
     * 设置纯文本的分享
     * @param shareContent qq分享需要使用shareContent处理，包含(包含链接，标题，分享内容)
     * @param msg 纯文本分享内容，微信分享&短信文案
     */
    public void setDefaultShareContentWithMsg(ShareContent shareContent,String msg,int iconRes){
        this.shareContent=shareContent;
        this.msg=msg;
        this.jiepingMap= BitmapFactory.decodeResource(mContext.getResources(), iconRes);
    }

    @Override
    public void share(int type) {
        try {
            UMImage image = new UMImage(mContext, jiepingMap);
            shareAction = new ShareAction((Activity) mContext).setCallback(this);
            switch (type) {
                case ShareView.QQ_SHARE:
                    shareAction.setPlatform(SHARE_MEDIA.QQ);
                    if (shareContent != null) {
                        shareAction.setShareContent(shareContent).withMedia(image).share();
                    } else {
                        shareAction.withMedia(image).share();
                    }
                    break;
                case ShareView.WEIXIN_SHARE:
                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
                    if (shareContent != null) {
                        shareAction.withText(msg).share();
                    } else {
                        shareAction.withMedia(image).share();
                    }
                    break;
                case ShareView.MESSAGE_SHARE:
                    shareAction.setPlatform(SHARE_MEDIA.SMS)
                            .withText(msg)
                            .share();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String a = e.getMessage();
        }
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
