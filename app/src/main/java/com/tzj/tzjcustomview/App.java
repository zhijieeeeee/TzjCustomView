package com.tzj.tzjcustomview;

import android.app.Application;

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
        mApplication = this;

        CrashHandler.getInstance().init(this);
    }

    public static App getApplication() {
        return mApplication;
    }
}
