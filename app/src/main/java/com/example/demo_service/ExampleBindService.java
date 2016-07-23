package com.example.demo_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by leon on 2016/7/21.
 */
public class ExampleBindService extends Service  {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
