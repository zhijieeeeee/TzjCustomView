package com.tzj.tzjcustomview.scrolltest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * <p>
 * Description：平移view的各种方法
 * </p>
 *
 * @author tangzhijie
 */
public class MyDragView extends ImageView {

    private int lastX;
    private int lastY;

    private Scroller mScroller;

    public MyDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int disX = x - lastX;
                int disY = y - lastY;
                //平移方法一:layout
//                layout(getLeft() + disX, getTop() + disY, getRight() + disX, getBottom() + disY);

                //平移方法二:offsetLeftAndRight,offsetTopAndBottom
//                offsetLeftAndRight(disX);
//                offsetTopAndBottom(disY);

                //平移方法三:LayoutParams
//                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                marginLayoutParams.leftMargin = getLeft() + disX;
//                marginLayoutParams.topMargin = getTop() + disY;
//                setLayoutParams(marginLayoutParams);

                //平移方法四:scrollBy与scrollTo，在ACTION_UP中结合Scroller实现平滑的移动回到原点
                ((View) getParent()).scrollBy(-disX, -disY);


                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                View parentView = (View) getParent();
                mScroller.startScroll(
                        parentView.getScrollX(),
                        parentView.getScrollY(),
                        -parentView.getScrollX(),
                        -parentView.getScrollY());
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通过重绘来不断调用computeScroll
            invalidate();
        }
    }
}
