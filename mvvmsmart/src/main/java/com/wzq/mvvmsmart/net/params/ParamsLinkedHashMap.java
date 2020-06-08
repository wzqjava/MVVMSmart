package com.wzq.mvvmsmart.net.params;

import java.util.Iterator;
import java.util.Map;

/**
 * created 王志强 2020.04.30
 * 参数封装,许多公司的参数要放到HashMap里, 这里统一封装;
 */
public class ParamsLinkedHashMap extends BaseParamsLinkedHashMap {

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
