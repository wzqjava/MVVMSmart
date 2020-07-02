package com.wzq.mvvmsmart.net.net_utils.gsontypeadapter;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * created 王志强 2020.04.30
 */
public class StringTypeAdapter extends TypeAdapter<String> {


    @Override
    public void write(JsonWriter out, String value) throws IOException {
        try {
            if (value == null){
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
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                Log.e("TypeAdapter", "null is not a string");
                return "";
            }
        } catch (Exception e) {
            Log.e("TypeAdapter", "Not a String", e);
        }
        return "";
    }
}
