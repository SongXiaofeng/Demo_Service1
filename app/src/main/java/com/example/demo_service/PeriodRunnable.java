package com.example.demo_service;


/**
*
*    新建�?个线程执行周期发送消息更新主线程
*/

import android.os.Handler;
import android.os.Message;

public class PeriodRunnable implements Runnable {
    private long delaytime;
    private Handler myHandler;
    private int what;
    private LoggerUtils logger;
    private int i=0;

    private PeriodRunnable(long delaytime,LoggerUtils logger) {
        this.delaytime = delaytime;
        this.logger=logger;
        
    }

    private PeriodRunnable(long delaytime, Handler handler,int what) {
        this.delaytime = delaytime;
        this.myHandler = handler;
        this.what=what;
    }
    
    private PeriodRunnable(long delaytime, Handler handler,LoggerUtils logger) {
        this.delaytime = delaytime;
        this.myHandler = handler;
        this.logger=logger;
    }
    private PeriodRunnable(long delaytime, Handler handler,LoggerUtils logger,int what) {
        this.delaytime = delaytime;
        this.myHandler = handler;
        this.logger=logger;
        this.what=what;
    }


    public static PeriodRunnable getInstance(long delaytime, Handler handler,int what){
        return new PeriodRunnable(delaytime,handler,what);
    }
    
    public static PeriodRunnable getInstance(long delaytime, Handler handler,LoggerUtils loggerint,int what){
        return new PeriodRunnable(delaytime,handler,loggerint,what);
    }

    public static PeriodRunnable getInstance(long delaytime,LoggerUtils logger){
        return new PeriodRunnable(delaytime,logger);
    }
    
    public static PeriodRunnable getInstance(long delaytime,Handler handler,LoggerUtils logger){
        return new PeriodRunnable(delaytime,logger);
    }

    @Override
    public void run() {
        while (true) {
            try {
            	i++;
                if (myHandler != null) {
                    Message message = new Message();
                    message.what = what;
                    message.obj=i;
                    myHandler.sendMessage(message);// 
                }
                logger.v("ExampleStartService","---->i="+i);
                Thread.sleep(delaytime);// 线程暂停XX秒，单位毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

