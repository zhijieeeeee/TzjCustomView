package com.tzj.tzjcustomview;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * <p>
 * Description：IntentService测试
 * </p>
 *
 * @author tangzhijie
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        //这个name，debug使用
        super("MyLog");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //在这里面执行耗时操作,有多个任务时顺序执行，全部执行完会销毁service
        String action = intent.getStringExtra("task_action");
        Log.i("MyLog", action);
        SystemClock.sleep(3000);
    }

    @Override
    public void onDestroy() {
        Log.i("MyLog", "MyIntentService destroy");
        super.onDestroy();
    }
}
