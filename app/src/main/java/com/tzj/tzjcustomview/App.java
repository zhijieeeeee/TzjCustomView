package com.tzj.tzjcustomview;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class App extends Application {

    private static App mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        mApplication = this;

        CrashHandler.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决方法数越界,defaultConfig，dependencies，Application配置
        MultiDex.install(this);
    }

    public static App getApplication() {
        return mApplication;
    }
}
