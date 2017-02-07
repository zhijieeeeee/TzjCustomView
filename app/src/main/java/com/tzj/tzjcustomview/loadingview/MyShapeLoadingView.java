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

    private AnimatorSet upAnimatorSet;
    private AnimatorSet downAnimatorSet;

    ObjectAnimator transAnim;
    ObjectAnimator shadowScaleAnim;
    ObjectAnimator rotateAnim;

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
        transAnim = ObjectAnimator.ofFloat(shapeView, "translationY", distance, 0);
        shadowScaleAnim = ObjectAnimator.ofFloat(iv_shadow, "scaleX", 0.2f, 1);
        rotateAnim = null;
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
        upAnimatorSet = new AnimatorSet();
        upAnimatorSet.setDuration(ANIM_DURATION);

        upAnimatorSet.playTogether(shadowScaleAnim, transAnim, rotateAnim);
        upAnimatorSet.addListener(new Animator.AnimatorListener() {
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
        upAnimatorSet.start();
    }

    /**
     * 下降
     */
    private void down() {
        transAnim = ObjectAnimator.ofFloat(shapeView, "translationY", 0, distance);
        shadowScaleAnim = ObjectAnimator.ofFloat(iv_shadow, "scaleX", 1, 0.2f);
        transAnim.setDuration(ANIM_DURATION);
        shadowScaleAnim.setDuration(ANIM_DURATION);
        transAnim.setInterpolator(new AccelerateInterpolator(1.2f));
        shadowScaleAnim.setInterpolator(new AccelerateInterpolator(1.2f));
        downAnimatorSet = new AnimatorSet();
        downAnimatorSet.setDuration(ANIM_DURATION);
        downAnimatorSet.playTogether(shadowScaleAnim, transAnim);
        downAnimatorSet.addListener(new Animator.AnimatorListener() {
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
        downAnimatorSet.start();
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //当包含此view的Activity或者当前view被remove时，view的onDetachedFromWindow会被调用
    //与此方法对应的是onAttachedToWindow
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //停止动画，防止内存泄漏
        upAnimatorSet.cancel();
        downAnimatorSet.cancel();
        transAnim.cancel();
        shadowScaleAnim.cancel();
        rotateAnim.cancel();
    }

}
