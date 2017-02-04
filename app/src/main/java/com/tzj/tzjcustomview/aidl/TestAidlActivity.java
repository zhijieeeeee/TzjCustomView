package com.tzj.tzjcustomview.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tzj.tzjcustomview.Book;
import com.tzj.tzjcustomview.IMyAidlInterface;
import com.tzj.tzjcustomview.IOnNewBookArrivedListener;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class TestAidlActivity extends AppCompatActivity {

    private IMyAidlInterface iMyAidlInterface;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {

                //注册新书新增的监听
                iMyAidlInterface.registerListener(iOnNewBookArrivedListener);

                iMyAidlInterface.add(1, 2);

                Book book = new Book();
                book.setId("12346");
                book.setName("乔布斯传");
                iMyAidlInterface.addBook(book);

                Book book2 = new Book();
                book2.setId("4567");
                book2.setName("馬克扎克伯格");
                iMyAidlInterface.addBook(book2);

                Log.i("MyLog", "书的数量=" + iMyAidlInterface.getBookList().size());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //监听
    private IOnNewBookArrivedListener iOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            Log.i("MyLog", "有书新增了");
            Log.i("MyLog", "有书新增了=" + book.getId());
            Log.i("MyLog", "有书新增了=" + book.getName());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, TestService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        Log.i("MyLog", "Activity所在进程" + Process.myPid());
    }

    @Override
    protected void onDestroy() {
        //取消注册监听
        if (iMyAidlInterface != null && iMyAidlInterface.asBinder().isBinderAlive()) {
            try {
                iMyAidlInterface.unRegisterListener(iOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
        iOnNewBookArrivedListener=null;
        super.onDestroy();
    }


}
