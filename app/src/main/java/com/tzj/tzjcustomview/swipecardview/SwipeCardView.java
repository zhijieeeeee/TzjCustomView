package com.tzj.tzjcustomview.swipecardview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;

import java.util.List;

/**
 * <p> FileName： SwipeCardView</p>
 * <p>
 * Description：子定义切换卡片view
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class SwipeCardView extends RelativeLayout implements View.OnTouchListener {

    /**
     * 当前最顶层的view
     */
    private View currentView;

    /**
     * 作为卡片的试图列表
     */
    private List<View> viewList;

    /**
     * 手指点击的x坐标
     */
    private float pressX;

    /**
     * 最大可移动的距离
     */
    private float maxDistance;
    private float allDis;

    /**
     * 当前角度
     */
    private float degree;

    /**
     * 当前偏移量,往左是1>0，往右是0>1
     */
    private float position;

    /**
     * 最大可滑的角度
     */
    private static final float MAX_DEGREE = 60;

    public SwipeCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置卡片列表项列表
     *
     * @param viewList 试图列表
     */
    public void setViewList(List<View> viewList) {
        this.viewList = viewList;
        if (viewList == null || viewList.size() == 0) {
            return;
        }

        setOnTouchListener(this);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);

        //添加view到RelativeLayout中,倒序放
        for (int i = viewList.size() - 1; i >= 0; i--) {
            addView(viewList.get(i), rlp);
        }

        //设置旋转角度,倒序设置
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).setRotation(getChildCount() - 1 - i);
        }

        //获得relativeLayout最顶层的子view
        currentView = getChildAt(getChildCount() - 1);

        //获得子视图的宽度
        ViewTreeObserver vto = currentView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                currentView.getViewTreeObserver().removeOnPreDrawListener(this);
                maxDistance = currentView.getMeasuredWidth() / 2;
                allDis = maxDistance;
                return true;
            }
        });
    }

    /**
     * 滑完的偏移量速度
     */
    private float step;

    @Override
    public boolean onTouch(final View view, MotionEvent motionEvent) {
        if (getChildCount() <= 1) {
            return true;
        }
        currentView = getChildAt(getChildCount() - 1);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                allDis = maxDistance;
                pressX = motionEvent.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                float curX = motionEvent.getRawX();
                float distanceX = curX - pressX;
                //往左是减，往右加
                allDis += distanceX;
                //往左是1>0，往右是0>1
                position = allDis / maxDistance;
                if (distanceX > 0) {//往右
                    if (allDis >= maxDistance) {
                        allDis = maxDistance;
                    }
                    if (position >= 1) {
                        position = 1;
                    }
                } else {//往左
                    if (allDis <= 0) {
                        allDis = 0;
                    }
                    if (position <= 0) {
                        position = 0;
                    }
                }

                //根据偏移量计算当前滑动的角度
                degree = (1 - position) * MAX_DEGREE;
                //设置透明度和旋转角度
                ViewHelper.setPivotX(currentView, 0);
                ViewHelper.setPivotY(currentView, currentView.getMeasuredHeight());
                ViewHelper.setRotation(currentView, -degree);
                ViewHelper.setAlpha(currentView, position);

                pressX = curX;

                break;
            case MotionEvent.ACTION_UP:
                if (degree >= (MAX_DEGREE / 2)) {//滑到了可自动滑出的边界，自动左滑
                    isLeftOut = true;
                    currentView.postDelayed(leftOutRunnable, 0);
                } else {//自动右滑回来
                    isRightBack = true;
                    //右滑回来需要的角度
                    float disDegree = degree;
                    //右滑回来需要的偏移量
                    float disPosition = 1 - position;
                    //透明度回到1的步长
                    step = disPosition / disDegree;
                    currentView.postDelayed(rightBackRunnable, 0);
                }
                break;

        }
        return true;
    }

    //是否执行自动左滑
    private boolean isLeftOut;
    /**
     * 自动左滑
     */
    private Runnable leftOutRunnable = new Runnable() {
        @Override
        public void run() {
            if (degree <= MAX_DEGREE && isLeftOut) {
                position -= 0.05;
                degree += 1;
                if (degree >= MAX_DEGREE) {//完毕
                    isLeftOut = false;
                    degree = MAX_DEGREE;
                    bottom();
                    return;
                }
                ViewHelper.setPivotX(currentView, 0);
                ViewHelper.setPivotY(currentView, currentView.getMeasuredHeight());
                ViewHelper.setRotation(currentView, -degree);
                ViewHelper.setAlpha(currentView, position);
                currentView.postDelayed(leftOutRunnable, 1);
            }
        }
    };

    //是否执行自动右滑
    private boolean isRightBack;
    /**
     * 自动右滑回来
     */
    private Runnable rightBackRunnable = new Runnable() {
        @Override
        public void run() {
            if (degree >= 0 && isRightBack) {
                position += step;
                degree -= 1;
                if (degree <= 0) {
                    position = 1;
                    isRightBack = false;
                    degree = 0;
                }
                ViewHelper.setPivotX(currentView, 0);
                ViewHelper.setPivotY(currentView, currentView.getMeasuredHeight());
                ViewHelper.setRotation(currentView, -degree);
                ViewHelper.setAlpha(currentView, position);
                currentView.postDelayed(rightBackRunnable, 1);
            }
        }
    };

    /**
     * 把当前滑掉的重置到底部
     */
    private void bottom() {
        removeView(currentView);
        //重置滑掉的view的透明度和旋转角度
        ViewHelper.setAlpha(currentView, 1);
        ViewHelper.setPivotX(currentView, 0);
        ViewHelper.setPivotY(currentView, currentView.getMeasuredHeight());
        ViewHelper.setRotation(currentView, 0);
        //添加到底部
        addView(currentView, 0);
        //重置旋转角度
        for (int i = getChildCount() - 1; i >= 0; i--) {
            ViewHelper.setPivotX(currentView, currentView.getMeasuredWidth() / 2);
            ViewHelper.setPivotY(currentView, currentView.getMeasuredHeight() / 2);
            getChildAt(i).setRotation(getChildCount() - 1 - i);
        }
    }

    /**
     * 删除项
     *
     * @param v 被删除的视图
     */
    public void deleteItem(View v) {
        viewList.remove(v);
        removeView(v);
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).setRotation(getChildCount() - 1 - i);
        }
    }
}
