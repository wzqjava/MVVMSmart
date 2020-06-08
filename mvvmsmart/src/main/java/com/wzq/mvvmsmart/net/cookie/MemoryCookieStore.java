package com.wzq.mvvmsmart.net.cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * created 王志强 2020.04.30
 */
public class MemoryCookieStore implements CookieStore {
    private final HashMap<String, List<Cookie>> allCookies = new HashMap<>();

    @Override
    public void add(HttpUrl url, List<Cookie> cookies) {
        List<Cookie> oldCookies = allCookies.get(url.host());

        if (oldCookies != null) {
            Iterator<Cookie> itNew = cookies.iterator();
            Iterator<Cookie> itOld = oldCookies.iterator();
            while (itNew.hasNext()) {
                String va = itNew.next().name();
                while (va != null && itOld.hasNext()) {
                    String v = itOld.next().name();
                    if (v != null && va.equals(v)) {
                        itOld.remove();
                    }
                }
            }
            oldCookies.addAll(cookies);
        } else {
            allCookies.put(url.host(), cookies);
        }


    }

    @Override
    public List<Cookie> get(HttpUrl uri) {
        List<Cookie> newCookies = new ArrayList<>();
        if (allCookies.get(uri.host()) != null) {
            for (Cookie cookie : allCookies.get(uri.host())) {//深拷贝 防止多线程并发时报数组越界和空指针问题
                Cookie parse = Cookie.parse(uri, cookie.toString());
                newCookies.add(parse);
            }
        } else {
            allCookies.put(uri.host(), newCookies);
        }
        /*List<Cookie> cookies = allCookies.get(uri.host());
        if (cookies == null)
        {
            cookies = new ArrayList<>();
            allCookies.put(uri.host(), cookies);
        }*/
        return newCookies;

    }

    @Override
    public boolean removeAll() {
        allCookies.clear();
        return true;
    }

    @Override
    public List<Cookie> getCookies() {
        List<Cookie> cookies = new ArrayList<>();
        Set<String> httpUrls = allCookies.keySet();
        for (String url : httpUrls) {
            cookies.addAll(allCookies.get(url));
        }
        return cookies;
    }


    @Override
    public boolean remove(HttpUrl uri, Cookie cookie) {
        List<Cookie> cookies = allCookies.get(uri.host());
        if (cookie != null) {
            return cookies.remove(cookie);
        }
        return false;
    }


}
