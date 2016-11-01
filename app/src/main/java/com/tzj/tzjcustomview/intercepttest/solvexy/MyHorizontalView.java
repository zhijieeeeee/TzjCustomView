package com.tzj.tzjcustomview.intercepttest.solvexy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * <p>
 * Description：解决横竖滑动布局的滑动冲突
 * </p>
 *
 * @author tangzhijie
 */
public class MyHorizontalView extends ViewGroup {

    public MyHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取判定为滑动的最小距离
        ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childLeft = 0;
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            child.layout(childLeft, 0, childLeft + child.getMeasuredWidth(), child.getMeasuredHeight());
            childLeft += child.getMeasuredWidth();
        }
    }

    private int lastInterceptX;
    private int lastInterceptY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("MyLog", "MyHorizontalView:onInterceptTouchEvent");
        boolean isIntercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isIntercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int disX = x - lastInterceptX;
                int disY = y - lastInterceptY;
                if (Math.abs(disX) > Math.abs(disY)) {
                    isIntercepted = true;
                } else {
                    isIntercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isIntercepted = false;
                break;
        }
        lastInterceptX = x;
        lastInterceptY = y;

        lastX = x;
        return isIntercepted;
    }

    private int lastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int disX = x - lastX;
                scrollBy(-disX, 0);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastX = x;
        return true;
    }
}
