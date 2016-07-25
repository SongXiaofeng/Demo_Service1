package com.example.demo_service;


/**
*
*/

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

public class PeriodRunnable implements Runnable {
    private static Boolean isExampleStartService=false;
    private static SharedPreferences mySharedPre;
    private MyApplication myappliacation;
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

    private PeriodRunnable(long delaytime,LoggerUtils logger,MyApplication myapp) {
        this.delaytime = delaytime;
        this.myappliacation=myapp;
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
       // isExampleStartService=MyApplication.isExampleStartService;
        return new PeriodRunnable(delaytime,handler,loggerint,what);
    }

    public static PeriodRunnable getInstance(long delaytime,LoggerUtils logger){
        return new PeriodRunnable(delaytime,logger);
    }
    
    public static PeriodRunnable getInstance(long delaytime,Handler handler,LoggerUtils logger){
        return new PeriodRunnable(delaytime,logger);
    }

    public static Runnable getInstance(long delaytime,Handler handler,LoggerUtils logger, int what,MyApplication myapp) {
       // mySharedPre=context.getSharedPreferences("serverDestoryFlag",Context.MODE_PRIVATE);
        return new PeriodRunnable(delaytime,logger,myapp);
    }

/*
     作者：田元
    链接：https://www.zhihu.com/question/39274138/answer/89418429
    来源：知乎著作权归作者所有，转载请联系作者获得授权。
     2、一个线程sleep的小坑，我相信很多朋友都写过这样的代码：
    public void run() {
        try{
            Thread.sleep(1000);
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
    但是设想一下，如果我们的线程在执行sleep之前就被interrupt了呢，别以为不可能，
    ThreadPoolExecutor框架就是通过对所有的Thread进行interrupt来取消所有线程，这是我们上述代码就会抛出异常。所以良好的实践应该是：
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try{
                Thread.sleep(1000);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
*/
  //  editor.putBoolean("ExampleStartServiceIsDestory",true);
    @Override
    public void run() {
       // Boolean isExampleStartService=false;
      //  if(!mySharedPre.getBoolean("ExampleStartServiceIsDestory",false)) {
            while (!Thread.currentThread().isInterrupted()) {
                if(!myappliacation.getExampleStartServiceFlag()) {
                try {
                    i++;
                    if (myHandler != null) {
                        Message message = new Message();
                        message.what = what;
                        message.obj = i;
                        myHandler.sendMessage(message);//
                    }
                    logger.v("ExampleStartService", "---->i="+i+"--->ExampleStartServiceFlag="+myappliacation.getExampleStartServiceFlag());
                    Thread.sleep(delaytime);// 线程暂停XX秒，单位毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

