package com.zkp.bettas;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas
 * @time: 2018/8/15 10:26
 * @description:
 */
public class BettasApplication extends Application {

    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;

    private static OkHttpClient mOkHttpClient;
    private static Context context;
    private static BettasApplication instance;

    public BettasApplication() {
    }

    public static OkHttpClient genericClient() {

        if (mOkHttpClient != null)
            return mOkHttpClient;

        return mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }

    public static Context getContext() {
        return context;
    }

    public static BettasApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        Fresco.initialize(this);
    }
}
