package com.wzq.sample.http2.manager;

import android.content.Context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;

/**
 * created 王志强 2020.04.30
 */
public class HttpResponseManager {

    private Map<Context, Map<Integer, DisposableObserver>> map = new HashMap<>();


    public void addRequest(Context context, int what, DisposableObserver disposableObserver) {
        if (map == null) return;
        if (map.containsKey(context)) {
            Map<Integer, DisposableObserver> observerMap = map.get(context);
            if (observerMap != null && !observerMap.containsKey(what)) {
                observerMap.put(what, disposableObserver);
            } else {
                observerMap = new HashMap<>();
                observerMap.put(what, disposableObserver);
            }
            map.put(context, observerMap);
        } else {
            Map<Context, Map<Integer, DisposableObserver>> map = new HashMap<>();
            Map<Integer, DisposableObserver> observerMap = map.get(context);
            if (observerMap != null && !observerMap.containsKey(what)) {
                observerMap.put(what, disposableObserver);
            } else {
                observerMap = new HashMap<>();
                observerMap.put(what, disposableObserver);
            }
            map.put(context, observerMap);
        }
    }

    public void remveRequest(Context context, int what) {
        if (map == null) return;
        if (map.containsKey(context)) {
            Map<Integer, DisposableObserver> observerMap = map.get(context);
            if (observerMap.containsKey(what)) {
                DisposableObserver disposableObserver = observerMap.get(what);
                if (!disposableObserver.isDisposed()) {
                    disposableObserver.dispose();
                }
                observerMap.remove(what);
                if (observerMap.size() == 0) {
                    map.remove(context);
                } else {
                    map.put(context, observerMap);
                }
                if (observerMap.size() == 0) {
                    map.remove(context);
                }
            }
        }
    }

    public void remveRequest(Context context) {
        if (map == null) return;
        if (map.containsKey(context)) {
            Map<Integer, DisposableObserver> observerMap = map.get(context);
            Iterator<Map.Entry<Integer, DisposableObserver>> iterator = observerMap.entrySet().iterator();
            while (iterator.hasNext()) {
                DisposableObserver disposableObserver = iterator.next().getValue();
                if (!disposableObserver.isDisposed()) {
                    disposableObserver.dispose();
                }
                iterator.remove();
                if (observerMap.size() == 0) {
                    map.remove(context);
                } else {
                    map.put(context, observerMap);
                }
            }
            if (observerMap.size() == 0) {
                map.remove(context);
            }
        }
    }


    public static HttpResponseManager getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        static HttpResponseManager instance = new HttpResponseManager();
    }
}
