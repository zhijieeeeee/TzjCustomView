package com.tzj.tzjcustomview;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Description：捕获Crash
 * </p>
 *
 * @author tangzhijie
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler sInstance;
    private Context context;
    //系统默认的UncaughtExceptionHandler
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    //异常日志存放路径
    private static final String PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/CrashLog/";
    //异常日志文件名
    private static final String FILENAME = "crash.txt";

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        sInstance = new CrashHandler();
        return sInstance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        this.context = context.getApplicationContext();
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当程序有未被捕获的异常，系统会自动回调这个方法
     *
     * @param t 异常所在线程
     * @param e 异常
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        dumpToSdCard(e);

        e.printStackTrace();
        //如果系统有默认的异常处理器，则交给系统，否则自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 导出异常信息到sd卡文件
     *
     * @param e
     */
    private void dumpToSdCard(Throwable e) {
        //sd卡不存在
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        //创建日志所在文件夹
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //日志文件
        File file = new File(PATH + FILENAME);

        //写入文件
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            printExceptionInfo(pw, e);
            pw.close();
        } catch (Exception e1) {
            Log.e("MyLog", "print info fail");
        }

    }

    /**
     * 打印异常信息
     */
    private void printExceptionInfo(PrintWriter pw, Throwable e) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                PackageManager.GET_ACTIVITIES);

        //导出时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        pw.println(date);

        pw.print("App版本:");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        pw.print("Android版本:");
        pw.print(Build.VERSION.RELEASE);

        pw.print("手机型号:");
        pw.println(Build.MANUFACTURER + " " + Build.MODEL);

        pw.print("CPU:");
        pw.println(Build.CPU_ABI);

        //打印异常信息
        e.printStackTrace(pw);
    }
}
