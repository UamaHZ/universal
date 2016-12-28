package com.uama.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 
 * author Cosmo Jiang
 * email ruchao.jiang@uama.com.cn
 * date 2014年9月10日 上午11:34:32
 * desc 动画辅助类
 *
 */
public class AnimUtils {

	public static void startAlhpaAnim(View v, AnimationListener listener) {
		Animation anim = new  AlphaAnimation(0.3f,1.0f);
	      anim.setDuration(2000);
	      anim.setFillAfter(true);
	      anim.setInterpolator(new LinearInterpolator());
	      anim.setAnimationListener(listener);
	      v.startAnimation(anim);
	}
	
	public static void startAlhpaAnim(View v,long time,AnimationListener listener) {
		Animation anim = new AlphaAnimation(0.3f,1.0f);
	      anim.setDuration(time);
	      anim.setFillAfter(true);
	      anim.setInterpolator(new LinearInterpolator());
	      anim.setAnimationListener(listener);
	      v.startAnimation(anim);
	}
	
	
	public static void endAlhpaAnim(View v,long time,AnimationListener listener) {
		  Animation anim = new  AlphaAnimation(1.0f,0.0f);
	      anim.setDuration(time);
	      anim.setFillAfter(true);
	      anim.setInterpolator(new LinearInterpolator());
	      anim.setAnimationListener(listener);
	      v.startAnimation(anim);

	}
	
	public static void startAlhpaAnim(View v) {
		Animation anim = new  AlphaAnimation(0.0f,1.0f);
	      anim.setDuration(2000);
	      anim.setFillAfter(true);
	      anim.setInterpolator(new LinearInterpolator());
	      v.startAnimation(anim);
	}
	
	
	public static void startAlhpaAnim(View v,long time) {
		Animation anim = new  AlphaAnimation(0.0f,1.0f);
	      anim.setDuration(time);
	      anim.setFillAfter(true);
	      anim.setInterpolator(new LinearInterpolator());
	      v.startAnimation(anim);
	}
	
	public static void progress(View v) {
		RotateAnimation anim = new RotateAnimation(0f, 361f, 1, 0.5f, 1, 0.5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(-1);
		anim.setRepeatMode(RotateAnimation.RESTART);
		anim.setDuration(1400);
		v.startAnimation(anim);

	}
	
	public static void progressConverse(View v) {
		RotateAnimation anim = new RotateAnimation(0f, -361f, 1, 0.5f, 1, 0.5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(-1);
		anim.setRepeatMode(RotateAnimation.RESTART);
		anim.setDuration(1400);
		v.startAnimation(anim);

	}
	
	
	public static void scanMask(View v) {
		TranslateAnimation mAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
		mAnimation.setDuration(6000);
		mAnimation.setRepeatCount(-1);
		mAnimation.setRepeatMode(Animation.RESTART);
		mAnimation.setInterpolator(new LinearInterpolator());
		v.startAnimation(mAnimation);

	}
	
	
	public static void scanMaskVertical(View v) {
		TranslateAnimation mAnimation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.9f
				, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f);
		mAnimation.setDuration(6000);
		mAnimation.setRepeatCount(-1);
		mAnimation.setRepeatMode(Animation.RESTART);
		mAnimation.setInterpolator(new LinearInterpolator());
		v.startAnimation(mAnimation);
		/*TranslateAnimation mAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0.9f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f);
		mAnimation.setDuration(6000);
		mAnimation.setRepeatCount(-1);
		mAnimation.setRepeatMode(Animation.RESTART);
		mAnimation.setInterpolator(new LinearInterpolator());
		v.startAnimation(mAnimation);*/

	}
	
	public static void rotateStart(View v) {
		Animation anim = new RotateAnimation(0f, -180f,
				Animation.RELATIVE_TO_SELF, 0.5F,
				Animation.RELATIVE_TO_SELF, 0.5F);
		anim.setDuration(200);
		anim.setFillAfter(true);
		anim.setFillEnabled(true);
		v.startAnimation(anim);

	}
	
	public static void rotateEnd(View v) {
		Animation anim = new RotateAnimation(-180f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5F,
				Animation.RELATIVE_TO_SELF, 0.5F);
		anim.setDuration(200);
		anim.setFillAfter(true);
		anim.setFillEnabled(true);
		v.startAnimation(anim);

	}
	
	public static void rotateProgress(View v) {
		Animation anim = new RotateAnimation(0f, -180f,
				Animation.RELATIVE_TO_SELF, 0.5F,
				Animation.RELATIVE_TO_SELF, 0.5F);
		anim.setDuration(800);
		anim.setFillAfter(true);
		anim.setFillEnabled(true);
		v.startAnimation(anim);

	}



	public static void startAnimations(Context context, View view,int animId, int duration){
		Animation animation= AnimationUtils.loadAnimation(context,animId);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setFillEnabled(true);
		view.startAnimation(animation);
	}

	public static void startAnimation(Context context,View view,int animId,int duration){
		Animation animation= AnimationUtils.loadAnimation(context,animId);
		animation.setDuration(duration);
		view.startAnimation(animation);
	}

	public static void stratAlphaAppearAnim(Context context,View view) {
		Animation animation= AnimationUtils.loadAnimation(context, R.anim.anim_appear);
		animation.setDuration(200);
		view.startAnimation(animation);
	}

	public static void stratAlphaDisappearAnim(Context context,View view) {
		Animation animation= AnimationUtils.loadAnimation(context, R.anim.anim_disappear);
		animation.setDuration(200);
		view.startAnimation(animation);
	}

	public static void startAnimation(Context context, View view, int animId){
		Animation animation= AnimationUtils.loadAnimation(context,animId);
		view.startAnimation(animation);
	}
}