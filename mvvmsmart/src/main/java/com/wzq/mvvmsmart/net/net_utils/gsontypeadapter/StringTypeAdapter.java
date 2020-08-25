package com.wzq.mvvmsmart.net.net_utils.gsontypeadapter;

import android.util.Log;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * author : wzq
 * date   : 2019/12/26 19:14
 */
public class StringTypeAdapter extends TypeAdapter<String> {

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        try {
            if (value == null) {
                value = "";
            }
            out.value(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String read(JsonReader in) throws IOException {
        try {
            String value;
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                Log.e("TypeAdapter", "null is not a number");
                return "";
            }
            if (in.peek() == JsonToken.BOOLEAN) {
                boolean b = in.nextBoolean();
                Log.e("TypeAdapter", b + " is not a number");
                return "";
            }
            if (in.peek() == JsonToken.STRING) {
                String str = in.nextString();
                return str;
            } else {
                value = in.nextString();
                return value;
            }
        } catch (Exception e) {
            Log.e("TypeAdapter", "Not a number", e);
        }
        return "";
    }
}
