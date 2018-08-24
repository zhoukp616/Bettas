package com.zkp.bettas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.utils
 * @time: 2018/8/15 10:20
 * @description:
 */
public class NetUtil {

    public static boolean isConnected(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }
}
