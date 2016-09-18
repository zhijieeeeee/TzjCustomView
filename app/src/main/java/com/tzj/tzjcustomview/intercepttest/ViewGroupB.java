package com.tzj.tzjcustomview.intercepttest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class ViewGroupB extends ViewGroup {

    public ViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        View child = getChildAt(0);
        child.layout(200, 200, getMeasuredWidth() - 200, getMeasuredHeight() - 200);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("MyLog", "ViewGroupB:onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("MyLog", "ViewGroupB:dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("MyLog", "ViewGroupB:onTouchEvent");

        float x = event.getX();
        Log.i("MyLog", "ViewGroupB:onTouchEvent:" + x);
        return super.onTouchEvent(event);
    }
}
