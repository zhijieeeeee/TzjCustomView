package com.tzj.tzjcustomview.remoteviews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.tzj.tzjcustomview.R;

/**
 * <p>
 * Description：RemoteViews只支持某些View，不支持自定义控件
 * </p>
 *
 * @author tangzhijie
 */
public class MyAppWidgetProvider extends AppWidgetProvider {

    private static final String CLICK_ACTION = "clock";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(CLICK_ACTION)) {//单击事件
            Toast.makeText(context, "点我干撒", Toast.LENGTH_SHORT).show();

            //更新RemoteViews
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            remoteViews.setImageViewResource(R.id.iv,R.drawable.classification_one);

            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(new ComponentName(context,MyAppWidgetProvider.class),
                    remoteViews);
        }
    }

    //小部件添加或者更新时调用
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }

    }

    /**
     * 窗口小部件更新
     *
     * @param context
     * @param appWidgeManger
     * @param appWidgetId
     */
    private void onWidgetUpdate(Context context,
                                AppWidgetManager appWidgeManger, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        //设置点击事件
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.btn, pendingIntent);
        //更新小部件
        appWidgeManger.updateAppWidget(appWidgetId, remoteViews);
    }
}
