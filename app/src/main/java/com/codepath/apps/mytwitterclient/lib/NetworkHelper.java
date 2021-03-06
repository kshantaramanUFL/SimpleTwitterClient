package com.codepath.apps.mytwitterclient.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kshantaraman on 12/13/15.
 */
public class NetworkHelper {
    public static Boolean isUp(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static Boolean isDown(Context context) {
        return ! isUp(context);
    }
}