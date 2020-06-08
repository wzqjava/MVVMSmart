package com.wzq.mvvmsmart.net.net_utils;

import com.tencent.mmkv.MMKV;
import com.wzq.mvvmsmart.utils.KLog;

/**
 * created 王志强 2020.04.30
 */
public class MmkvUtils {
    private static final String TAG = MmkvUtils.class.getSimpleName();

    public static void putIntValue(String name, int value) {
        MMKV.defaultMMKV()
                .encode(name, value);
    }

    public static int getIntValue(String name) {
        return MMKV.defaultMMKV()
                .decodeInt(name);
    }

    public static void putStringValue(String name, String value) {
        KLog.INSTANCE.e("MMkV===="+name+":"+value);
        MMKV.defaultMMKV().encode(name, value);
    }


    public static String getStringValue(String name) {
        return MMKV.defaultMMKV().decodeString(name, "");
    }

    public static void putBooleanValue(String name, boolean value) {
        MMKV.defaultMMKV().encode(name, value);
    }

    public static boolean getBooleanValue(String name) {
        return MMKV.defaultMMKV().decodeBool(name, false);
    }

    /**
     * 存储数据
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        MMKV kv = MMKV.defaultMMKV();
        boolean result = false;
        if (value instanceof Integer) {
            result = kv.encode(key, (Integer) value);
        } else if (value instanceof Long) {
            result = kv.encode(key, (Long) value);
        } else if (value instanceof Float) {
            result = kv.encode(key, (Float) value);
        } else if (value instanceof Double) {
            result = kv.encode(key, (Double) value);
        } else if (value instanceof Boolean) {
            result = kv.encode(key, (Boolean) value);
        } else if (value instanceof String) {
            result = kv.encode(key, (String) value);
        } else if (value instanceof byte[]) {
            result = kv.encode(key, (byte[]) value);
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @param defValue
     * @param <T>
     * @return
     */
    public static <T> T get(String key, T defValue) {
        MMKV kv = MMKV.defaultMMKV();
        Object result = null;
        if (defValue instanceof Integer) {
            result = kv.decodeInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            result = kv.decodeLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            result = kv.decodeFloat(key, (Float) defValue);
        } else if (defValue instanceof Double) {
            result = kv.decodeDouble(key, (Double) defValue);
        } else if (defValue instanceof Boolean) {
            result = kv.decodeBool(key, (Boolean) defValue);
        } else if (defValue instanceof String) {
            result = kv.decodeString(key, (String) defValue);
        } else if (defValue instanceof byte[]) {
            result = kv.decodeBytes(key);
        }
        return (T) result;
    }
}
