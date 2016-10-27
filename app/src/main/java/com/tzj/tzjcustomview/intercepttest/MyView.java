package com.tzj.tzjcustomview.intercepttest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class MyView extends View {

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("MyLog", "MyView:dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("MyLog", "MyView:onTouchEvent");

        float x = event.getX();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("MyLog", "MyView:down:" + x);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MyLog", "MyView:move:" + x);
                break;
            case MotionEvent.ACTION_UP:
                Log.i("MyLog", "MyView:up:" + x);
                break;
        }


        return true;
    }
}
