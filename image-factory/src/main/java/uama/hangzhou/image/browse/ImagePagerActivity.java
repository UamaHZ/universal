/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-27 下午3:48.
 */

package uama.hangzhou.image.browse;

/*
 *
 * Created by GuJiaJia on 2015/5/9.
 * E-mail: 965939858@qq.com
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

import uama.hangzhou.image.R;
import uama.hangzhou.image.zoom.ImageViewPager;

/**
 * 图片查看器
 */
public class ImagePagerActivity extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String FROM_PHOTO_WALL = "FROM_PHOTO_WALL";

    public static final String CAN_SAVE = "CAN_SAVE";
    private ImageViewPager mPager;
    private TextView indicator;
    private boolean from_photo_wall;
    private boolean canSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(ImagePagerActivity.this);
        setContentView(R.layout.uimage_image_detail_pager);
        int pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        List<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        from_photo_wall = getIntent().getBooleanExtra(FROM_PHOTO_WALL, false);
        canSave = getIntent().getBooleanExtra(CAN_SAVE,true);
        mPager = (ImageViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);

        CharSequence text = getString(R.string.uimage_viewpager_indicator, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.uimage_viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        List<String> fileList;

        ImagePagerAdapter(FragmentManager fm, List<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            if (from_photo_wall) {
                url = fileList.get(position);
            }
            return ImageDetailFragment.newInstance(url,canSave);
        }

    }
}