package com.wzq.sample.http2.oldhelper;

import java.util.Iterator;
import java.util.Map;

/**
 * created 王志强 2020.04.30
 * Get参数
 */
public class GetParams extends BaseParams {
    /**
     * 添加参数
     *
     * @param key
     * @param object
     */
    public void addParams(String key, Object object) {
        this.put(key, object);
    }

    @Override
    public String toString() {
        int i = 0;
        String s = "";
        Iterator iter = entrySet().iterator();
        while (iter.hasNext()) {
            if (i != 0) {
                s = s + "&";
            }
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            s = s + key + "=" + val;
            i++;
        }

        return s;
    }
}
