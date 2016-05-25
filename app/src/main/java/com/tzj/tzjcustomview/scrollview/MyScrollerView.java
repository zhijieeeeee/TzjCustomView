package com.tzj.tzjcustomview.scrollview;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * <p> ProjectName： MyLearn</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-01-21 10:07
 */
public class MyScrollerView extends ViewGroup {

    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 左边界
     */
    private int leftBorder;

    /**
     * 右边界
     */
    private int rightBorder;

    /**
     * 当前点击的x坐标
     */
    private float pressX;

    /**
     * 移动后的X
     */
    private float moveX;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float lastMoveX;

    public MyScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            //测量每个子控件的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            //根据测量后的宽高为每个子控件布局
            childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
        }
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(getChildCount() - 1).getRight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressX = ev.getRawX();
                lastMoveX = pressX;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getRawX();
                float dis = Math.abs(moveX - pressX);
                lastMoveX = moveX;
                if (dis > mTouchSlop) {// 当手指拖动值大于mTouchSlop值时，认为应该进行滚动，将事件在这里拦截掉，阻止事件传递到子控件当中
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                int scrollX = (int) (lastMoveX - moveX);
                //完全靠左，getScrollX为0，左滑，getScrollX增加
                if (getScrollX() + scrollX < leftBorder) {//左侧边界检测
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrollX > rightBorder) { //右侧边界检测
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrollX, 0);
                lastMoveX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
