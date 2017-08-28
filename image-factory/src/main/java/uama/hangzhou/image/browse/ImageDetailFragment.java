/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-27 下午3:48.
 */

package uama.hangzhou.image.browse;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import uama.hangzhou.image.R;
import uama.hangzhou.image.util.DeviceUtils;
import uama.hangzhou.image.zoom.OnPhotoTapListener;
import uama.hangzhou.image.zoom.OnViewTapListener;
import uama.hangzhou.image.zoom.ZoomDrawView;


/**
 * 单张图片显示的Fragment
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ZoomDrawView photoDraweeView;
    private ProgressBar progressBar;
    private TextView tvLoadFail;
    private ImageRequest request;
    private boolean loadSuccess;
    private PipelineDraweeControllerBuilder controllerBuilder;
    private Toast mToast;
    private static String FilePackagePath = Environment.getExternalStorageDirectory() + File.separator + "uama" + File.separator + "download";
    private boolean canSave;

    public static ImageDetailFragment newInstance(String imageUrl, boolean canSave) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        args.putBoolean("canSave", canSave);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        canSave = getArguments() == null || getArguments().getBoolean("canSave");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.uimage_image_detail_fragment, container, false);

        if (mImageUrl == null)
            return v;

        photoDraweeView = (ZoomDrawView) v.findViewById(R.id.photo_detail_view);
        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        tvLoadFail = (TextView) v.findViewById(R.id.tv_load_fail);
        progressBar.setVisibility(View.VISIBLE);
        request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(mImageUrl))
                .setResizeOptions(new ResizeOptions(DeviceUtils.getDisplayWidth(getActivity()), DeviceUtils.getDisplayHeight(getActivity())))
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(true)
                .build();
        controllerBuilder = Fresco.newDraweeControllerBuilder();
        controllerBuilder.setOldController(photoDraweeView.getController());
        controllerBuilder.setAutoPlayAnimations(true);
        controllerBuilder.setTapToRetryEnabled(true);
        controllerBuilder.setControllerListener(new BaseControllerListener<ImageInfo>() {

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                progressBar.setVisibility(View.GONE);
                tvLoadFail.setVisibility(View.GONE);
                if (imageInfo == null || photoDraweeView == null) {
                    return;
                }
                loadSuccess = true;
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                progressBar.setVisibility(View.GONE);
                loadSuccess = false;
                tvLoadFail.setVisibility(View.VISIBLE);
            }
        });
        controllerBuilder.setImageRequest(request);
        photoDraweeView.setController(controllerBuilder.build());
        photoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (!loadSuccess) {
                    controllerBuilder.setImageRequest(request);
                    photoDraweeView.setController(controllerBuilder.build());
                    progressBar.setVisibility(View.VISIBLE);
                    return;
                }
                try {
                    getActivity().finish();
                } catch (NullPointerException e) {
                }
            }
        });
        photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (!loadSuccess) {
                    controllerBuilder.setImageRequest(request);
                    photoDraweeView.setController(controllerBuilder.build());
                    progressBar.setVisibility(View.VISIBLE);
                    return;
                }
                try {
                    getActivity().finish();
                } catch (NullPointerException e) {
                }
            }
        });

        photoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (canSave) {
                    TipMessageDialog.showSaveMessage(getContext(), new TipMessageDialog.OnItemClickListener() {
                        @Override
                        public void onItemClick() {
                            savePicture(mImageUrl);
                        }
                    });
                }
                return true;
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void savePicture(final String urlPath) {
        File destDir = new File(FilePackagePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        new Thread() {
            public void run() {
                try {
                    String filepath = FilePackagePath + File.separator + getLocalTimeName();
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6 * 1000);  // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
                    Message msg = new Message();
                    if (conn.getResponseCode() != 200) {
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                    InputStream inSream = conn.getInputStream();
                    //把图片保存到项目的根目录

                    readAsFile(inSream, new File(filepath), handler);
                    Intent mediaScanIntent = new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File file = new File(filepath + getLocalTimeName());
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    getActivity().sendBroadcast(mediaScanIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    public static void readAsFile(InputStream inSream, File file, Handler handler) throws Exception {
        FileOutputStream outStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        Message msg = new Message();
        msg.what = 1;
        Bundle bundle = new Bundle();
        bundle.putString("tip", file.getAbsolutePath());
        msg.setData(bundle);
        handler.sendMessage(msg);
        outStream.close();
        inSream.close();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    showTips("下载失败");
                    break;
                case 1:
                    if (msg.getData() != null && msg.getData().getString("tip") != null) {
                        showTips("图片成功保存到" + msg.getData().getString("tip"));
                    } else {
                        showTips("保存成功！");
                    }
                    break;
            }
        }
    };

    public void showTips(String msg) {
        if (mToast == null) {
            mToast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        } else {
            mToast.cancel();
            mToast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    //获取保存图片的时间命名
    public static String getLocalTimeName() {
        String dataStr = "";
        String str = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);
        dataStr = "Uama_" + str.substring(0, 4) + "_" + str.substring(4, 6) + "_" + str.substring(6, 8) + "_" + str.substring(8, 14) + "_" + UUID.randomUUID() + ".jpg";
        return dataStr;
    }
}
