package com.eshel.ourvisa;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


@SuppressLint("Registered")
public class BaseApplication extends Application{

    private static BaseApplication app;
    private Handler mHandler;

    public BaseApplication() {
        app = this;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        app = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static BaseApplication getContext() {
        return app;
    }

    public Handler getHandler() {
        return mHandler;
    }
}
