package com.codepath.apps.mytwitterclient.lib;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kshantaraman on 12/13/15.
 */
public class JsonHelper {
    public static Long findLongOrZero(String field, JSONObject obj) throws JSONException {
        return obj.has(field) ? Long.parseLong(obj.getString(field)) : 0;
    }

    public static int findIntOrZero(String field, JSONObject obj) throws JSONException {
        return obj.has(field) ? Integer.parseInt(obj.getString(field)) : 0;
    }

    public static String findOrBlank(String field, JSONObject obj) throws JSONException {
        return obj.has(field) ? obj.getString(field) : "";
    }
}
