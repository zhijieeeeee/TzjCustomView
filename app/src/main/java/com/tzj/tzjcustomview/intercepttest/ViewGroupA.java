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
public class ViewGroupA extends ViewGroup {


    public ViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        View child = getChildAt(0);
//        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        View child = getChildAt(0);
        child.layout(100, 100, getMeasuredWidth() - 100, getMeasuredHeight() - 100);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("MyLog", "ViewGroupA:onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("MyLog", "ViewGroupA:dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    //如果onInterceptTouchEvent返回true,拦截掉事件，不再往下传递，同时把事件交给onTouchEvent处理
    //如果onTouchEvent返回false，会接受down之后，把事件再往上层的onTouchEvent传递
    //如果onTouchEvent返回true,会接受down到up的一系列事件，不往上层的onTouchEvent传递

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("MyLog", "ViewGroupA:onTouchEvent");

        float x = event.getX();
        Log.i("MyLog", "ViewGroupA:onTouchEvent:" + x);
        return super.onTouchEvent(event);
    }
}
