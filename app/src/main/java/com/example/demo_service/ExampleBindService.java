package com.example.demo_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Created by leon on 2016/7/21.
 */
public class ExampleBindService extends Service  {

    private LoggerUtils logger;
    private MyApplication myapp;
    private final String TAG = "ExampleBinderService";
    private final Random generator = new Random();

    public class MyBinder extends Binder {

        public ExampleBindService getService(){
            return ExampleBindService.this;
        }

    }

    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        // editor=mySharedPre.edit();
        logger = LoggerUtils.getInstance(getApplicationContext());
        myapp=(MyApplication)getApplication();
        // editor.putBoolean("ExampleStartServiceIsDestory",false);
        //  editor.apply();
        logger.v(TAG, "onCreate()");
        logger.deleteLatestLog();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.v(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (null != intent) {
            logger.v(TAG, "onBind()-->"
                    + "intent.getStringExtra=" + intent.getStringExtra("BindServicenName")
                   );
        }
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        logger.v(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        logger.v(TAG, "onDestroy()");
        super.onDestroy();
    }
    //getRandomNumber是Service暴露出去供client调用的公共方法
    public int getRandomNumber(){
        return generator.nextInt();
    }

}
