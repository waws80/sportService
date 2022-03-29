package edu.wj.sport.service.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private static final String dateFormat = "yyy-MM-dd";

    public static Gson getGson(){
        return new GsonBuilder().setDateFormat(dateFormat)
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }
}
