/**
 *
 */
package com.wzq.mvvmsmart.net.cookie;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.wzq.mvvmsmart.net.net_utils.MetaDataUtil;

/**
 * @author   created 王志强 2020.04.30
 */
public class CookieUtils {
    private final String KCookieDel = "deleteMe";
    public static final String KJsessionidCookie = "JSESSIONID_COOKIE";
    private Context mContext;
    private String mCookies = "";
    private static CookieUtils mCookieUtils = null;

    public CookieUtils(Context context) {
        this.mContext = context;
    }

    public static synchronized CookieUtils getInstance(Context context) {
        if (mCookieUtils == null) {
            mCookieUtils = new CookieUtils(context);
        }
        return mCookieUtils;
    }

    /**
     * 保存cookie
     * * @author
     */
    public void saveCookie(String cookie) {
        if (TextUtils.isEmpty(cookie) || KCookieDel.equals(cookie)) {
            return;
        }
        String cookieStr = KJsessionidCookie + "=" + cookie;
        if (cookieStr.equals(mCookies)) {
            return;
        }
        mCookies = cookieStr;
    }

    /**
     * 同步一下webview cookie
     *
     * @author
     */
    public void synCookies(String url) {
        if (TextUtils.isEmpty(mCookies) || KCookieDel.equals(mCookies)) {
            return;
        }
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
//        cookieManager.removeAllCookie();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cookieManager.setCookie(MetaDataUtil.getBaseUrl(), mCookies);
        CookieSyncManager.getInstance().sync();
//        if (Build.VERSION.SDK_INT < 21) {
//            CookieSyncManager.getInstance().sync();
//        } else {
//            CookieManager.getInstance().flush();
//        }
    }

    public String getCookie() {
        return mCookies;
    }
}
