package com.tzj.tzjcustomview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class MyService extends Service {

    @Override
    public void onCreate() {
        Toast.makeText(this,"启动了",Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
