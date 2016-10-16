package com.example.demo_service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;


//https://developer.android.com/guide/components/services.html

/*1.服务可以同时以这两种方式运行，也就是说，它既可以是启动服务（以无限期运行），也允许绑定。
问题只是在于您是否实现了一组回调方法：onStartCommand()（允许组件启动服务）和 onBind()（允许绑定服务）。
无论应用是处于启动状态还是绑定状态，抑或处于启动并且绑定状态，任何应用组件均可像使用活动那样通过调用 Intent 来使用服务（即使此服务来自另一应用）。
不过，您可以通过清单文件将服务声明为私有服务，并阻止其他应用访问。
 使用清单文件声明服务部分将对此做更详尽的阐述。
注意：服务在其托管进程的主线程中运行，它既不创建自己的线程，也不在单独的进程中运行（除非另行指定）。 这意味着，如果服务将执行任何 CPU 密集型工作或阻止性操作（例如 MP3 播放或联网），则应在服务内创建新线程来完成这项工作。通过使用单独的线程，可以降低发生“应用无响应”(ANR) 错误的风险，而应用的主线程仍可继续专注于运行用户与 Activity 之间的交互*/


/*3.如果组件通过调用 startService() 启动服务（这会导致对 onStartCommand() 的调用），则服务将一直运行，
 直到服务使用 stopSelf() 自行停止运行，或由其他组件通过调用 stopService() 停止它为止。
如果组件是通过调用 bindService() 来创建服务（且未调用 onStartCommand()），则服务只会在该组件与其绑定时运行。
一旦该服务与所有客户端之间的绑定全部取消，系统便会销毁它。*/

/*4.仅当内存过低且必须回收系统资源以供具有用户焦点的 Activity 使用时，Android 系统才会强制停止服务。
如果将服务绑定到具有用户焦点的 Activity，则它不太可能会终止；
如果将服务声明为在前台运行（稍后讨论），则它几乎永远不会终止。
或者，如果服务已启动并要长时间运行，则系统会随着时间的推移降低服务在后台任务列表中的位置，而服务也将随之变得非常容易被终止；
如果服务是启动服务，则您必须将其设计为能够妥善处理系统对它的重启。
如果系统终止服务，那么一旦资源变得再次可用，系统便会重启服务（不过这还取决于从 onStartCommand() 返回的值，本文稍后会对此加以讨论）。
如需了解有关系统会在何时销毁服务的详细信息，请参阅进程和线程文档。*/

//log_path /storage/sdcard0/DCIM/log
public class MainActivity extends Activity implements View.OnClickListener {
    private final String exampleStartService = "ExampleStartService";
    private final String exampleBindService = "ExampleBindService";
    private LoggerUtils logger;
    private Button bt_start_service;
    private Button bt_bind_service;
    private Button bt_stop_start_service;
    private Button bt_ubind_service;
    private Button bt_begin_anotherActivity;
    private ExampleBindService service;
    private final String TAG = "ExampleBinderService";

    private ServiceConnection conn = new ServiceConnection() {
        public boolean isBound;

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBound = true;
            ExampleBindService.MyBinder myBinder = (ExampleBindService.MyBinder)binder;
            service = myBinder.getService();
            Log.i(TAG, "MainActivity onServiceConnected");
            int num = service.getRandomNumber();
            Log.i(TAG, "MainActivity 中调用 ExampleBindService的getRandomNumber方法, 结果: " + num);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Log.i(TAG, "ActivityA onServiceDisconnected");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logger = LoggerUtils.getInstance(getApplicationContext());
        logger.deleteLatestLog();

        bt_start_service=(Button)findViewById(R.id.start_service);
        bt_bind_service=(Button)findViewById(R.id.bind_service);
        bt_stop_start_service=(Button)findViewById(R.id.stop_start_service);
        bt_ubind_service=(Button)findViewById(R.id.stop_bind_service);
        bt_begin_anotherActivity=(Button)findViewById(R.id.begin_anotherActivity);

        bt_start_service.setOnClickListener(this);
        bt_bind_service.setOnClickListener(this);
        bt_stop_start_service.setOnClickListener(this);
        bt_ubind_service.setOnClickListener(this);
        bt_begin_anotherActivity.setOnClickListener(this);

      //  exampleStartService();
      //  exampleBindService();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {

                        if (ServiceUtils.isServiceRunning(getApplicationContext(), ExampleStartService.class.getName())) {
                            logger.v(ExampleStartService.class.getName() + " is running");
                        } else {
                            logger.v(ExampleStartService.class.getName() + " is not running");
                        }
                        if (ServiceUtils.isServiceRunning(getApplicationContext(), ExampleBindService.class.getName())) {
                            logger.v(ExampleBindService.class.getName() + " is running");
                        } else {
                            logger.v(ExampleBindService.class.getName() + " is not running");
                        }
                        Thread.sleep(8000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void exampleBindService() {
        Intent intent = new Intent();
        intent.putExtra("BindServicenName", exampleBindService);
        intent.setClass(this, ExampleBindService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    private void exampleStartService() {
        Intent intent = new Intent();
        intent.putExtra("StartServicenName", exampleStartService);
        intent.setClass(this, ExampleStartService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

          /*  bt_start_service=(Button)findViewById(R.id.start_service);
            bt_bind_service=(Button)findViewById(R.id.bind_service);
            bt_stop_start_service=(Button)findViewById(R.id.stop_start_service);
            bt_ubind_service=(Button)findViewById(R.id.stop_bind_service);
            bt_begin_anotherActivity=(Button)findViewById(R.id.begin_anotherActivity);*/
            case R.id.start_service:
                Toast.makeText(this,"Start Service",Toast.LENGTH_LONG).show();
                exampleStartService();
                break;
            case R.id.bind_service:
                exampleBindService();
                break;
            case R.id.stop_start_service:
                stopService(new Intent(this,ExampleStartService.class));
                break;
            case R.id.stop_bind_service:
                unbindService(conn);
                break;
            case R.id.begin_anotherActivity:
                startActivity(new Intent(this,BtestActivity.class));
                break;


        }
    }
}
