package com.tzj.tzjcustomview;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alipay.euler.andfix.patch.PatchManager;
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


    //阿里巴巴AndFix热补丁修复-----------------------
    public PatchManager mPatchManager;

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

        //阿里巴巴AndFix热补丁修复-----------------------
        // 初始化patch管理类
        mPatchManager = new PatchManager(this);
        // 初始化patch版本
        mPatchManager.init("1.0");
        // 加载已经添加到PatchManager中的patch
        mPatchManager.loadPatch();


        //如果有新的补丁需要修复，下载完成后，进行以下操作
        //添加patch，只需指定patch的路径即可，补丁会立即生效
        //mPatchManager.addPatch(path);

        //当apk版本升级，需要把之前patch文件的删除，需要以下操作
        //删除所有已加载的patch文件
        //mPatchManager.removeAllPatch();
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
