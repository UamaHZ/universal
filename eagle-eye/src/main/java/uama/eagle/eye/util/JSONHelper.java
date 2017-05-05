/*
 * 杭州绿漫科技有限公司
 * Copyright (c) 16-6-21 下午8:47.
 */

package uama.eagle.eye.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON数据帮助类
 */
public class JSONHelper {
	/**
	 * 从JSON对象中取得字符串
	 * 
	 * @param o
	 * @param name
	 * @return
	 */
	public static String getString(JSONObject o, String name) {
		String ret = "";
		try {
			if ((o != null) && (!o.isNull(name))) {
				ret = o.getString(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/*
	 * 从JSON对象中取得bool
	 * 
	 * @param o
	 * 
	 * @param name
	 * 
	 * @return
	 */
	public static boolean getBool(JSONObject o, String name) {
		boolean ret = false;
		try {
			if ((o != null) && (!o.isNull(name))) {
				ret = o.getBoolean(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 从JSON对象中取得集合
	 * 
	 * @return
	 */
	public static List<String> getList(JSONArray arr) {
		List<String> list = new ArrayList<String>();
		try {
			
			for (int i = 0; i < arr.length(); i++) {
				list.add(arr.getString(i));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 从JSON对象中取得集合
	 * 
	 * @param o
	 * @return
	 */
	public static List<Object> getArrayList(JSONObject o, String arrName) {
		List<Object> list = new ArrayList<Object>();
		try {
			JSONArray arr = o.getJSONArray(arrName);
			for (int i = 0; i < arr.length(); i++) {
				list.add(arr.getJSONObject(i));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 从JSON对象中取得字符串数组
	 */
	public static String[] getStringArray(JSONArray arr) {
		int length = arr.length();
		String[] strs = new String[length];
		try {
			
			for (int i = 0; i < length; i++) {
				strs[i] = arr.getString(i);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	/**
	 * 从JSON对象中取得整型
	 * 
	 * @param o
	 * @param name
	 * @return
	 */
	public static int getInt(JSONObject o, String name) {
		int ret = -1;
		try {
			if ((o != null) && (!o.isNull(name))) {
				ret = o.getInt(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 从JSON对象中取得long型
	 * 
	 * @param o
	 * @param name
	 * @return
	 */
	public static long getLong(JSONObject o, String name) {
		long ret = -1;
		try {
			if ((o != null) && (!o.isNull(name))) {
				ret = o.getLong(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
