package com.example.demo_service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by 木日山 on 2016/10/17.
 */
public class ExampleIntentService extends IntentService {

    private final String TAG = "ExampleIntentService";
    //private final LoggerUtils logger;


    public ExampleIntentService() {

        super("ExampleIntentService");

      // logger = LoggerUtils.getInstance(getApplicationContext());
       Log.d(TAG, "ExampleIntentService()");

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent()");
        //Intent是从Activity发过来的，携带识别参数，根据参数不同执行不同的任务
        String action = intent.getExtras().getString("param");
        if (action.equals("oper1")) {
           // System.out.println("Operation1");
            Log.d(TAG, "-e " + "Operation1");
            Toast.makeText(ExampleIntentService.this, "-e " + "Operation1", Toast.LENGTH_LONG)
                    .show();
        } else if (action.equals("oper2")) {
            Log.d(TAG, "-e " + "Operation2");
            Toast.makeText(ExampleIntentService.this, "-e " + "Operation2", Toast.LENGTH_LONG)
                    .show();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
