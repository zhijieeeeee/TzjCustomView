package com.tzj.tzjcustomview.dragview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * <p>
 * Description：侧滑菜单
 * </p>
 *
 * @author tangzhijie
 */
public class MenuLayout extends FrameLayout {

    private ViewDragHelper viewDragHelper;

    private View contentView;
    private View menuView;

    private int menuWidth;

    public MenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this, callBack);
    }

    //获取拖拽的子View
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        contentView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        menuWidth = menuView.getMeasuredWidth();
    }

    //接管touch事件
    public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback callBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //捕获内容布局的事件
            return child == contentView;
        }

        /**
         * 处理水平方向上的拖动
         * @param  child 被拖动到view
         * @param  left 移动到达的x轴的距离
         * @param  dx 建议的移动的x距离
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.i("MyLog", "left:" + left);
            //判断滑动边界
            if (left < 0) {
                return 0;
            }
            if (left > menuWidth) {
                return menuWidth;
            }
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //手指抬起时，没有滑到菜单的1/2，关闭菜单
            if (contentView.getLeft() < (menuWidth / 2)) {
                viewDragHelper.smoothSlideViewTo(contentView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(MenuLayout.this);
            } else {
                viewDragHelper.smoothSlideViewTo(contentView, menuWidth, 0);
                ViewCompat.postInvalidateOnAnimation(MenuLayout.this);
            }
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
