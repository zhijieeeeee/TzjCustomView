package com.tzj.tzjcustomview.intercepttest.solvexy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class MyHorizontalView222 extends HorizontalScrollView {

    public MyHorizontalView222(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取判定为滑动的最小距离
        ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private int lastInterceptX;
    private int lastInterceptY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
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
                    isIntercepted = false;
                } else {
                    isIntercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isIntercepted = false;
                break;
        }
        lastInterceptX = x;
        lastInterceptY = y;

        return isIntercepted;
    }

}
