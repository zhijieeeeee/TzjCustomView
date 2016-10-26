package com.tzj.tzjcustomview.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tzj.tzjcustomview.Book;
import com.tzj.tzjcustomview.IMyAidlInterface;
import com.tzj.tzjcustomview.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class TestService extends Service {

    /**
     * 书列表
     */
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    /**
     * 注册的监听列表
     * RemoteCallbackList是专门用来删除跨进程listener的接口
     */
    private RemoteCallbackList<IOnNewBookArrivedListener> listenerList
            = new RemoteCallbackList<>();


    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

        @Override
        public void add(int a, int b) throws RemoteException {
            Log.i("MyLog", "Service所在进程" + Process.myPid());
            Log.i("MyLog", "总和是" + (a + b));
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.i("MyLog", book.getId());
            Log.i("MyLog", book.getName());
            bookList.add(book);

            //有新书添加了，通知所有的监听接口
            int listenerCount = listenerList.beginBroadcast();
            for (int i = 0; i < listenerCount; i++) {
                IOnNewBookArrivedListener listenerItem = listenerList.getBroadcastItem(i);
                if (listenerItem != null) {
                    listenerItem.onNewBookArrived(book);
                }
            }
            listenerList.finishBroadcast();
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listenerList.register(listener);
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listenerList.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //权限验证
        int check = checkCallingOrSelfPermission("com.tzj.tzjcustomview.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }
}
