package com.wzq.mvvmsmart.net.net_utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wzq.mvvmsmart.net.net_utils.gsontypeadapter.DoubleTypeAdapter;
import com.wzq.mvvmsmart.net.net_utils.gsontypeadapter.FloatTypeAdapter;
import com.wzq.mvvmsmart.net.net_utils.gsontypeadapter.IntegerTypeAdapter;
import com.wzq.mvvmsmart.net.net_utils.gsontypeadapter.LongTypeAdapter;
import com.wzq.mvvmsmart.net.net_utils.gsontypeadapter.StringTypeAdapter;
import java.util.List;
import java.util.Map;

/**
 * created 王志强 2020.04.30
 */
public class GsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                    .registerTypeAdapter(int.class, new IntegerTypeAdapter())
                    .registerTypeAdapter(String.class, new StringTypeAdapter())
                    .registerTypeAdapter(Double.class, new DoubleTypeAdapter())
                    .registerTypeAdapter(double.class, new DoubleTypeAdapter())
                    .registerTypeAdapter(Long.class, new LongTypeAdapter())
                    .registerTypeAdapter(long.class, new LongTypeAdapter())
                    .registerTypeAdapter(Float.class, new FloatTypeAdapter())
                    .registerTypeAdapter(float.class, new FloatTypeAdapter())
                    .create();
        }
    }

    public static Gson getGson() {
        return gson;
    }

    private GsonUtil() {
    }


    /**
     * 转成json
     *
     * @param object
     */
    public static String bean2String(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gson2Bean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gson2List(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gson2ListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gson2Maps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
