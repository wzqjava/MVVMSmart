package com.wzq.mvvmsmart.net.net_utils.gsontypeadapter;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * created 王志强 2020.04.30
 */
public class NumberUtils {
    public static boolean isIntOrLong(String str){
        return TextUtils.isDigitsOnly(str);
    }

    public static boolean isFloatOrDouble(String str){
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
