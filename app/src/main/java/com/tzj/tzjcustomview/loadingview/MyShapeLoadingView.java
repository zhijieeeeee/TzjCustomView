package com.tzj.tzjcustomview.loadingview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.tzj.tzjcustomview.R;

/**
 * <p> ProjectName： MyLearn</p>
 * <p>
 * Description：形状与阴影结合的控件（高仿58加载动画）
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-01-26 15:02
 */
public class MyShapeLoadingView extends FrameLayout {

    /**
     * 阴影
     */
    private ImageView iv_shadow;

    /**
     * 形状
     */
    private ShapeView shapeView;

    /**
     * 一次升或降的动画执行时间
     */
    private int ANIM_DURATION = 500;

    /**
     * 球移动的距离
     */
    private int distance;

    public MyShapeLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.shape_loading, null);
        addView(v);

        iv_shadow = (ImageView) findViewById(R.id.iv_shadow);
        shapeView = (ShapeView) findViewById(R.id.shapeView);

        distance = dip2px(getContext(), 55);
        down();
    }

    /**
     * 上升
     */
    private void up() {
        ObjectAnimator transAnim = ObjectAnimator.ofFloat(shapeView, "translationY", distance, 0);
        ObjectAnimator shadowScaleAnim = ObjectAnimator.ofFloat(iv_shadow, "scaleX", 0.2f, 1);
        ObjectAnimator rotateAnim = null;
        //判断形状
        switch (shapeView.getShape()) {
            case Circle:
                rotateAnim = ObjectAnimator.ofFloat(shapeView, "rotation", 0, 180);
                break;
            case Rectangle:
                rotateAnim = ObjectAnimator.ofFloat(shapeView, "rotation", 0, 180);
                break;
            case Triangle:
                rotateAnim = ObjectAnimator.ofFloat(shapeView, "rotation", 0, -240);
                break;
        }
        transAnim.setDuration(ANIM_DURATION);
        rotateAnim.setDuration(ANIM_DURATION);
        shadowScaleAnim.setDuration(ANIM_DURATION);
        transAnim.setInterpolator(new DecelerateInterpolator(1.2f));
        rotateAnim.setInterpolator(new DecelerateInterpolator(1.2f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIM_DURATION);

        animatorSet.playTogether(shadowScaleAnim, transAnim, rotateAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                down();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    /**
     * 下降
     */
    private void down() {
        ObjectAnimator transAnim = ObjectAnimator.ofFloat(shapeView, "translationY", 0, distance);
        ObjectAnimator shadowScaleAnim = ObjectAnimator.ofFloat(iv_shadow, "scaleX", 1, 0.2f);
        transAnim.setDuration(ANIM_DURATION);
        shadowScaleAnim.setDuration(ANIM_DURATION);
        transAnim.setInterpolator(new AccelerateInterpolator(1.2f));
        shadowScaleAnim.setInterpolator(new AccelerateInterpolator(1.2f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIM_DURATION);
        animatorSet.playTogether(shadowScaleAnim, transAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                shapeView.changeShape();
                up();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
