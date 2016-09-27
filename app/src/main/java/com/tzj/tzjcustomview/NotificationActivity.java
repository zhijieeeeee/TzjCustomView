package com.tzj.tzjcustomview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_basic;
    private Button btn_remote;
    private Button btn_hang;

    private int progress;
    boolean isDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btn_basic = (Button) findViewById(R.id.btn_basic);
        btn_remote = (Button) findViewById(R.id.btn_remote);
        btn_hang = (Button) findViewById(R.id.btn_hang);

        btn_basic.setOnClickListener(this);
        btn_remote.setOnClickListener(this);
        btn_hang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_basic:
                basicNotification();
                break;
            case R.id.btn_remote:
                remoteNotification();
                break;
            case R.id.btn_hang:
                break;
        }
    }

    /**
     * 普通notification
     */
    private void basicNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //点击通知的事件
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setTicker("sss")// 设置了这个才有悬挂效果
                .setContentTitle("I am ContentTitle")
                .setContentText("I am ContentText")
                .setContentIntent(pendingIntent)//点击通知的事件
                .setAutoCancel(true)//点击后消失
                .setOngoing(true)//设置为ture，表示它为一个正在进行的通知。(无法侧滑删除掉)
                .setProgress(100, progress, false);//设置进度条

        final Notification notify2 = builder.build();
        notify2.flags |= Notification.FLAG_AUTO_CANCEL;
        notify2.defaults = Notification.DEFAULT_SOUND;

        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notify2);

        //更新进度条
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isDone) {
                    if (progress >= 100) {
                        builder.setContentText("下载完成")
                                .setProgress(0, 0, false);//移除进度条
                        notificationManager.notify(0, builder.build());
                        notificationManager.cancel(0);//取消掉通知
                        isDone = true;
                        return;
                    }
                    progress++;
                    builder.setProgress(100, progress, false);
                    notificationManager.notify(0, builder.build());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    /**
     * 自定义布局notification
     */
    private void remoteNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remote_notification);
        remoteViews.setTextViewText(R.id.tv_name, "七里香");
        remoteViews.setTextViewText(R.id.tv_author, "周杰伦");

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOngoing(true)//设置为ture，表示它为一个正在进行的通知。(无法侧滑删除掉)
                .setContent(remoteViews)//小视图时候显示的布局
                .setCustomBigContentView(remoteViews);//大视图时候显示的模式

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
