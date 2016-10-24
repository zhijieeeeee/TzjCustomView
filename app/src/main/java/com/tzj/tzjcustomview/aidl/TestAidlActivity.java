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
                iMyAidlInterface.add(1, 2);

                Book book=new Book();
                book.setId("12346");
                book.setName("乔布斯传");
                iMyAidlInterface.addBook(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, TestService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        Log.i("MyLog","Activity所在进程"+ Process.myPid());
    }
}
