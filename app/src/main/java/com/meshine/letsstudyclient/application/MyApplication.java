package com.meshine.letsstudyclient.application;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Meshine on 16/4/25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
