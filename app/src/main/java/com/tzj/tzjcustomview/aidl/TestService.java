package com.tzj.tzjcustomview.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tzj.tzjcustomview.Book;
import com.tzj.tzjcustomview.IMyAidlInterface;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class TestService extends Service {

    IMyAidlInterface.Stub mBinder=new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void add(int a, int b) throws RemoteException {
            Log.i("MyLog","Service所在进程"+ Process.myPid());
            Log.i("MyLog","总和是"+(a+b));
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.i("MyLog",book.getId());
            Log.i("MyLog",book.getName());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
