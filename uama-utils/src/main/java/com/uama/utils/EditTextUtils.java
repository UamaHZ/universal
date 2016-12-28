package com.uama.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

/**
 * Created by liwei on 2016/12/28 10:02
 * Description: EditText 工具类
 */

public class EditTextUtils {

    /**
     * 限制 EditText 的最大输入长度
     * 中英文均为一个字符
     * @param editText 要限制的 EditText
     * @param length 最大长度
     */
    public static void lengthFilter(EditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /**
     * 限制 EditText 的最大输入长度
     * 中英文均为一个字符，超过最大长度回调
     * @param editText 要限制的 EditText
     * @param length 最大长度
     * @param listener 回调
     */
    public static void lengthFilter(EditText editText, int length, LMLengthFilter.Listener listener) {
        editText.setFilters(new InputFilter[]{new LMLengthFilter(length, listener)});
    }

    /**
     * 自定义长度过滤器
     * 代码从 InputFilter.LengthFilter 搬过来，在超过最大长度时进行回调
     */
    public static class LMLengthFilter implements InputFilter {
        private final int mMax;
        private final Listener mListener;

        public interface Listener {
            void onLengthExceeded();
        }

        public LMLengthFilter(int max, Listener listener) {
            mMax = max;
            mListener = listener;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            int keep = mMax - (dest.length() - (dend - dstart));
            if (keep <= 0) { // 输入之前已经达到最大长度
                if (mListener != null) {
                    mListener.onLengthExceeded();
                }
                return "";
            } else if (keep >= end - start) { // 输入之前没有达到最大长度，加上新输入的文字也没有达到最大长度
                return null; // keep original
            } else { // 输入之前没有达到最大长度，加上新输入的问题超过最大长度
                if (mListener != null) {
                    mListener.onLengthExceeded();
                }
                keep += start;
                // 下面这个判断我也不太清楚什么意思
                // high surrogate 高位代理，跟编码有关
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }
    }


}
