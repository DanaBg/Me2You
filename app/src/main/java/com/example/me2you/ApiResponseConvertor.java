package com.example.me2you;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ApiResponseConvertor extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == JSONObject.class && annotations.length == 0) {
            return new Converter<ResponseBody, List<String>>() {
                @Override
                public List<String> convert(ResponseBody value) throws IOException {
                    try {
                        // Convert the response into a List of Strings
                        // Here's an example implementation that assumes the response is a JSON array of strings
                        Log.d("check", "convert: " + value.string());
//                        String jsonString = value.string();
//                        JSONArray jsonArray = new JSONArray(jsonString);
//                        List<String> stringList = new ArrayList<>();
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            String string = jsonArray.getString(i);
//                            stringList.add(string);
//                        }
                        return null;
                    } catch (Error error) {
                        Log.d("error in convert function", "convert: ");
                    }
                    ;
                    return null;
                }

                ;
            };
        } else {
            Log.d("noticee", "responseBodyConverter: " + type);
            return null;
        }
    }
}
