/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-21 下午8:24.
 */

package uama.eagle.eye.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceUtils {
	
	// 保存的token的key
	public static final String TOKEN = "token";
	public static final String DEVICE_MANAGER = "device_manager";
	// 教程视频背景音乐的音量
	private final static String WORK_ROOM = "work_room";
	
	public static String getPrefString(Context context, String key, final String defaultValue) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		return settings.getString(key, defaultValue);
	}
	
	public static void setPrefString(Context context, final String key, final String value) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings.edit().putString(key, value).commit();
	}
	
	public static boolean getPrefBoolean(Context context, final String key, final boolean defaultValue) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		return settings.getBoolean(key, defaultValue);
	}
	
	public static boolean hasKey(Context context, final String key) {
		return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
	}
	
	public static void setPrefBoolean(Context context, final String key, final boolean value) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings.edit().putBoolean(key, value).commit();
	}
	
	public static void setPrefInt(Context context, final String key, final int value) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings.edit().putInt(key, value).commit();
	}
	
	public static int getPrefInt(Context context, final String key, final int defaultValue) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		return settings.getInt(key, defaultValue);
	}
	
	public static void setPrefFloat(Context context, final String key, final float value) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings.edit().putFloat(key, value).commit();
	}
	
	public static float getPrefFloat(Context context, final String key, final float defaultValue) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		return settings.getFloat(key, defaultValue);
	}
	
	public static void setSettingLong(Context context, final String key, final long value) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings.edit().putLong(key, value).commit();
	}
	
	public static long getPrefLong(Context context, final String key, final long defaultValue) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		return settings.getLong(key, defaultValue);
	}
	
	public static void clearPreference(Context context, final SharedPreferences p) {
		final Editor editor = p.edit();
		editor.clear();
		editor.commit();
	}
	
	public static void removePreference(Context context, String key) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = settings.edit();
		editor.remove(key);
		editor.commit();
	}
	
	public static boolean putUpdateUrl(Context context, String data) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DEVICE_MANAGER, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("UpdateUrl", data);
		return editor.commit();
	}
	
	public static boolean putWorkRoom(Context context, int work_room) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(WORK_ROOM, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("work_room", work_room);
		return editor.commit();
	}
	
	public static int getWorkRoom(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(WORK_ROOM, 0);
		return sharedPreferences.getInt("work_room", 0);
	}
	
}