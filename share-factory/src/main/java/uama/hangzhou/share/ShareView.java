package uama.hangzhou.share;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.ShareContent;

/**
 * user by guozhen
 * use for share
 * link 997558595@qq.com
 */
public class ShareView extends RelativeLayout implements View.OnClickListener {
    private RelativeLayout my_share;
    private View view_back;

    private View share_linear;

    private ImageView img_weixin;
    private ImageView img_qq;
    private ImageView img_msg;

    private TextView tx_weixin;
    private TextView tx_qq;
    private TextView tx_msg;

    //图片资源
    private int weixinShareImgRes;//微信icon
    private int qqShareImgRes;//qqicon
    private int msgShareImgRes;//短信icon

    private String weixinShareTx;//微信文案
    private String qqShareTx;//qq文案
    private String msgShareTx;//短信文案

    private boolean isMisMsg=false;//是否隐藏短信，默认显示
    private int txColor;//文字颜色


    public static final int QQ_SHARE = 1;
    public static final int WEIXIN_SHARE = 2;
    public static final int MESSAGE_SHARE = 3;

    private static final int DEFLUT_ANIM_TIME = 300;
    private InterShare interShare;//此处开放自定义分享，特殊需求实现
    private DefaultShareHelp defaultShareHelp;

    public ShareView(Context context) {
        super(context);
        init();
    }

    public ShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttr(context,attrs);
        init();
    }

    public ShareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 设置显示文案:默认显示 微信好友，QQ好友，短信
     * @param wxTx 替换微信文案
     * @param qqTx 替换qq文案
     * @param msgTx 替换短信文案
     */
    public void setShareViewTx(String wxTx,String qqTx,String msgTx){
        this.weixinShareTx=wxTx;
        this.qqShareTx=qqTx;
        this.msgShareTx=msgTx;
    }

    /**
     * 设置显示的分享icon
     * @param wxRes
     * @param qqRes
     * @param msgRes
     */
    public void setShareViewRes(int wxRes,int qqRes,int msgRes){
        this.weixinShareImgRes=wxRes;
        this.qqShareImgRes=qqRes;
        this.msgShareImgRes=msgRes;
    }

    /**
     * 设置是否隐藏短信
     * @param misMsg
     */
    public void setMisMsg(boolean misMsg){
        this.isMisMsg=misMsg;
    }

    /**
     * 设置显示的文字颜色
     * @param txColor
     */
    public void setTxColor(int txColor){
        this.txColor=txColor;
    }

    /**
     * 设置控件属性
     *
     * @param attrs
     */
    private void setAttr(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShareView);
        try {
            if (typedArray != null) {
                //此处设置图片资源
                weixinShareImgRes = typedArray.getResourceId(R.styleable.ShareView_weixin_icon,0);
                qqShareImgRes = typedArray.getResourceId(R.styleable.ShareView_qq_icon, 0);
                msgShareImgRes = typedArray.getResourceId(R.styleable.ShareView_msg_icon,0);

                weixinShareTx= typedArray.getString(R.styleable.ShareView_weixin_tx);
                qqShareTx= typedArray.getString(R.styleable.ShareView_qq_tx);
                msgShareTx= typedArray.getString(R.styleable.ShareView_msg_tx);

                txColor=typedArray.getColor(R.styleable.ShareView_share_tx_color, 0);

                isMisMsg=typedArray.getBoolean(R.styleable.ShareView_need_mis_msg,false);
            }
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * 实现此接口标识，你需要自定义qq,微信，短信分享
     *
     * @param interShare
     */
    public void setInterShare(InterShare interShare) {
        this.interShare = interShare;
    }

    /**
     * 初始化点击事件
     */
    private void init() {

        defaultShareHelp = new DefaultShareHelp(getContext());
        View view = View.inflate(getContext(), R.layout.share_activity, this);
        my_share = (RelativeLayout) view.findViewById(R.id.my_share);
        view_back=view.findViewById(R.id.share_linear);
        share_linear=view.findViewById(R.id.share_linear);

        View share_msg=view.findViewById(R.id.share_msg);

        img_weixin=(ImageView)view.findViewById(R.id.img_weixin);
        img_qq=(ImageView)view.findViewById(R.id.img_qq);
        img_msg=(ImageView)view.findViewById(R.id.img_msg);

        tx_weixin=(TextView) view.findViewById(R.id.tx_weixin);
        tx_qq=(TextView)view.findViewById(R.id.tx_qq);
        tx_msg=(TextView)view.findViewById(R.id.tx_msg);

        my_share.setOnClickListener(this);
        view.findViewById(R.id.share_qq).setOnClickListener(this);
        view.findViewById(R.id.share_msg).setOnClickListener(this);
        view.findViewById(R.id.share_weixin).setOnClickListener(this);

        //设置资源
        if(weixinShareImgRes!=0){
            img_weixin.setImageResource(weixinShareImgRes);
        }
        if(qqShareImgRes!=0){
            img_qq.setImageResource(qqShareImgRes);
        }
        if(msgShareImgRes!=0){
            img_msg.setImageResource(msgShareImgRes);
        }

        if(!TextUtils.isEmpty(weixinShareTx)){
            tx_weixin.setText(weixinShareTx);
        }
        if(!TextUtils.isEmpty(qqShareTx)){
            tx_qq.setText(qqShareTx);
        }
        if(!TextUtils.isEmpty(msgShareTx)){
            tx_msg.setText(msgShareTx);
        }

        if(isMisMsg)share_msg.setVisibility(GONE);
        else share_msg.setVisibility(VISIBLE);

        if(txColor!=0){
            tx_weixin.setTextColor(txColor);
            tx_qq.setTextColor(txColor);
            tx_msg.setTextColor(txColor);
        }

    }

    /**
     * 截屏分享
     *
     * @param jpMap 截屏bitmap
     * @param msg   分享短信文案
     */
    public void setDefaultJPMap(Bitmap jpMap, String msg) {
        defaultShareHelp.setDefaultJPMap(jpMap, msg);
    }

    /**
     * 设置纯文本的分享
     *
     * @param shareContent qq分享需要使用shareContent处理，包含(包含链接，标题，分享内容)
     * @param msg          纯文本分享内容，微信分享&短信文案
     */
    public void setDefaultShareContentWithMsg(ShareContent shareContent, String msg, int iconRes) {
        defaultShareHelp.setDefaultShareContentWithMsg(shareContent, msg, iconRes);
    }

    @Override
    public void onClick(View v) {
        onDismiss(DEFLUT_ANIM_TIME);
//        setVisibility(View.GONE);
        //于此，将分享功能分离出来，由帮助类实现；且满足自定义接口，实现定制化分享功能
        if (v.getId() == R.id.share_weixin) {
            if(interShare!=null) interShare.share(WEIXIN_SHARE);
            else defaultShareHelp.share(WEIXIN_SHARE);//微信分享
        } else if (v.getId() == R.id.share_qq) {
            if(interShare!=null) interShare.share(QQ_SHARE);
            else defaultShareHelp.share(QQ_SHARE);//qq分享
        } else if (v.getId() == R.id.share_msg) {
            if(interShare!=null) interShare.share(MESSAGE_SHARE);
            else defaultShareHelp.share(MESSAGE_SHARE);//短信
        }
    }


    /**
     * 重写，实现渐显动画
     *
     * @param visibility
     */
    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() == GONE && visibility == VISIBLE)
            showAnim(this, DEFLUT_ANIM_TIME);
        super.setVisibility(visibility);
    }

    private void showAnim(View v, long time) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(time);
        v.startAnimation(anim);
    }

    /**
     * 自定义控件隐藏动画
     */
    private void onDismiss(long time) {
        //透明度渐变有问题，后期优化
        Animation anim = new AlphaAnimation(1.0f, 0.1f);
        anim.setDuration(time);
        view_back.setAnimation(anim);
        anim.start();
        Animation transAnim=new TranslateAnimation(0,0,0,share_linear.getHeight());
        transAnim.setDuration(time);
        share_linear.startAnimation(transAnim);
        transAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
