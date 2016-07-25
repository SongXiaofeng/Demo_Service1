package com.example.demo_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;

/**
 * 1. When the service started first time,
 * it will run onCreate()-->onStarment()-->
 * you can get parameter from intent.
 * <p/>
 * 2. when you stop application manually it will restart
 * onCreate()-->onStarment()-->
 * intent=null ,this time.
 * <p/>
 * 3.when restart the application
 * it will run onStarment()-->
 */
public class ExampleStartService extends Service {
    private final String TAG = "ExampleStartService";
    private LoggerUtils logger;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    int a = (Integer) msg.obj;
                    if (a == 100) {
                        //stopSelf();
                        //logger.deleteSDcardExpiredLog();
                    }
                    break;
            }
        }

        ;
    };
    private Thread myThread;
    private SharedPreferences mySharedPre;
    private SharedPreferences.Editor editor;
    private MyApplication myapp;

    @Override
    public void onCreate() {
        super.onCreate();
       // mySharedPre=getSharedPreferences("serverDestoryFlag", Context.MODE_PRIVATE);
       // editor=mySharedPre.edit();
        logger = LoggerUtils.getInstance(getApplicationContext());
        myapp=(MyApplication)getApplication();
       // editor.putBoolean("ExampleStartServiceIsDestory",false);
      //  editor.apply();
        logger.v(TAG, "onCreate()");
        logger.deleteLatestLog();
        if (null == myThread) {
            myThread = new Thread(PeriodRunnable.getInstance(3000, mHandler, logger,1,myapp));
            myThread.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            logger.v(TAG, "onStartCommand()-->"
                    + "intent.getStringExtra=" + intent.getStringExtra("StartServicenName")
                    + "startId=" + startId);
        } else {
            logger.v(TAG, "onStartCommand()"
                    + "-->intent=null" + "startId=" + startId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        logger.v(TAG, "onDestroy()"+"--ExampleStartServiceFlag="+new MyApplication().getExampleStartServiceFlag());
      //  editor.putBoolean("ExampleStartServiceIsDestory",true);
      //  editor.apply();
        // myThread.destroy();
        myapp.setExampleStartServiceFlag(true);
        logger.v(TAG, "ExampleStartServiceFlag="+myapp.getExampleStartServiceFlag());
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
