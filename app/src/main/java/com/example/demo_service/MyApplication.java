package com.example.demo_service;

import android.app.Application;

/**
 * Created by leon on 2016/7/25.
 */
public class MyApplication extends Application {

    private  Boolean isExampleStartService=false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setExampleStartServiceFlag(Boolean bl) {
        this.isExampleStartService = bl;
    }

    public Boolean getExampleStartServiceFlag() {
        return isExampleStartService;
    }
}
