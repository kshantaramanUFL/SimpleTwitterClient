package com.codepath.apps.mytwitterclient.lib;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kshantaraman on 12/13/15.
 */
public class LogHelper {
    public static String logJsonFailure(JSONObject errorResponse) {

        StringBuffer sb = new StringBuffer();

        if (errorResponse == null) {
            sb.append("Null response returned from Twitter API.");
            Log.d("failure", sb.toString());
        } else {
            try {
                sb.append("Error returned from Twitter API.");
                JSONArray errors = errorResponse.getJSONArray("errors");
                for (int i = 0; i < errors.length(); i++) {
                    sb.append(errors.getJSONObject(i).getString("message")).append(", ");
                }
            } catch (JSONException e) {
                sb.append(e.toString());
            }
            Log.d("failure", errorResponse.toString());
        }
        return sb.toString();
    }
}
