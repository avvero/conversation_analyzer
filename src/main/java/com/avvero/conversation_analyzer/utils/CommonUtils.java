package com.avvero.conversation_analyzer.utils;

import com.google.gson.Gson;

/**
 * @author Avvero
 */
public class CommonUtils {

    public static final Gson GSON = new Gson();

    public static String dataToJson(Object o) {
        return GSON.toJson(o);
    }

}
