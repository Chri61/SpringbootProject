package com.alone.sell.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Chri61
 * @Date 2022/1/10 0010
 */
public class JsonUtil {

    public static String objToJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
