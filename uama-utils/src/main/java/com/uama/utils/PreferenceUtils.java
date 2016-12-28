package com.uama.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by liwei on 2016/12/27 16:26
 * Description: SharedPreferences 工具类
 */

public class PreferenceUtils {
    private SharedPreferences sp;

    public static PreferenceUtils getDefault(Context context) {
        return new PreferenceUtils(context);
    }

    public static PreferenceUtils get(Context context, String name) {
        return new PreferenceUtils(context, name);
    }

    private PreferenceUtils(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private PreferenceUtils(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public String get(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void put(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public boolean get(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void put(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public int get(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public void put(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public float get(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public void put(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public long get(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public void put(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public Set<String> get(String key, Set<String> defValue) {
        return sp.getStringSet(key, defValue);
    }

    public void put(String key, Set<String> value) {
        sp.edit().putStringSet(key, value).apply();
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public boolean contains(String key) {
        return sp.contains(key);
    }

    public void remove(String key) {
        sp.edit().remove(key).apply();
    }

    public void clear() {
        sp.edit().clear().apply();
    }
}
