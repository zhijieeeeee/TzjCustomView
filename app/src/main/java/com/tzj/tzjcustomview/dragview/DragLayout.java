package com.tzj.tzjcustomview.dragview;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class DragLayout extends LinearLayout {

    private View dragView;

    private ViewDragHelper dragHelper;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //创建ViewDragHelper的实例
        //ViewDragHelper.create(ViewGroup forParent, float sensitivity, ViewDragHelper.Callback cb);
        //参数1： 一个ViewGroup， 也就是ViewDragHelper将要用来拖拽谁下面的子view
        //参数2：灵敏度，一般设置为1.0f就行
        //参数3：一个回调，用来处理拖动到位置
        dragHelper = ViewDragHelper.create(this, callback);
    }

    //自定义callback
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        /**
         * 尝试捕获子view，一定要返回true
         * @param  child 尝试捕获的view
         * @param  pointerId 指示器id？
         * 这里可以决定哪个子view可以拖动
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
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

            //左侧边界检查
            if (left < getPaddingLeft()) {
                return getPaddingLeft();
            }
            //右侧边界检查
            if (left > getWidth() - child.getWidth()) {
                return getWidth() - child.getWidth();
            }
            return left;
        }

        /**
         *  处理竖直方向上的拖动
         * @param  child 被拖动到view
         * @param  top 移动到达的y轴的距离
         * @param  dy 建议的移动的y距离
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.i("MyLog", "top:" + top);

            //上边界检查
            if (top < getPaddingTop()) {
                return getPaddingTop();
            }
            //下边界检查
            if (top > getHeight() - child.getHeight()) {
                return getHeight() - child.getHeight();
            }
            return top;
        }

        /**
         * 当拖拽到状态改变时回调
         * @params 新的状态
         */
        @Override
        public void onViewDragStateChanged(int state) {
            switch (state) {
                case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动
                    break;
                case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者 正在进行fling/snap
                    break;
                case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
                    break;
            }
            super.onViewDragStateChanged(state);
        }

        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            invalidate();
        }


    };

    //接管touch事件
    public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    //获取拖拽的子View
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = getChildAt(0);
    }
}
