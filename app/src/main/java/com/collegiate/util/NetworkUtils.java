package com.collegiate.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gauravarora on 09/07/15.
 */
public class NetworkUtils {

    public static boolean checkInternetConnectivity() {
        Context context = AppUtils.getApplicationContext();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] allNetworks = manager.getAllNetworkInfo();
        for (NetworkInfo networkInfo : allNetworks) {
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

}
