package com.example.demo_service;

import android.app.Service;
import android.content.Intent;
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
                        stopSelf();
                        //logger.deleteSDcardExpiredLog();
                    }
                    break;
            }
        }

        ;
    };
    private Thread myThread;

    @Override
    public void onCreate() {
        super.onCreate();
        logger = LoggerUtils.getInstance(getApplicationContext());
        logger.v(TAG, "onCreate()");
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

        if (null == myThread) {
            myThread = new Thread(PeriodRunnable.getInstance(3000, mHandler, logger, 1));
            myThread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        logger.v(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
