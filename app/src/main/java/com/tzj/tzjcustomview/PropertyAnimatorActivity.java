package com.tzj.tzjcustomview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * <p>
 * Description：属性动画
 * </p>
 *
 * @author tangzhijie
 */
public class PropertyAnimatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_color;
    private Button btn_alpha;
    private Button btn_rotate;
    private Button btn_scale;
    private Button btn_translate;
    private Button btn_wrapper;
    private Button btn_value_animator;
    private Button btn_animatorset;
    private Button btn_xml;

    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_porperty_animation);

        btn_color= (Button) findViewById(R.id.btn_color);
        btn_alpha = (Button) findViewById(R.id.btn_alpha);
        btn_rotate = (Button) findViewById(R.id.btn_rotate);
        btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_translate = (Button) findViewById(R.id.btn_translate);
        btn_wrapper = (Button) findViewById(R.id.btn_wrapper);
        btn_value_animator = (Button) findViewById(R.id.btn_value_animator);
        btn_animatorset = (Button) findViewById(R.id.btn_animatorset);
        btn_xml = (Button) findViewById(R.id.btn_xml);

        btn_color.setOnClickListener(this);
        btn_alpha.setOnClickListener(this);
        btn_rotate.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        btn_translate.setOnClickListener(this);
        btn_wrapper.setOnClickListener(this);
        btn_value_animator.setOnClickListener(this);
        btn_animatorset.setOnClickListener(this);
        btn_xml.setOnClickListener(this);

        //布局动画
        ll = (LinearLayout) findViewById(R.id.ll);
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        LayoutAnimationController layoutAnimationController =
                new LayoutAnimationController(scaleAnimation, 0.5f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        ll.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alpha:



                //如果有无线循环的属性动画，要在activity销毁的时候停止，否则会发生内存泄漏，
                //因为动画持有View,View又持有Activity
                //animator.cancel()



                ObjectAnimator alphaObj = ObjectAnimator.ofFloat(
                        v,
                        "alpha",
                        1, 0, 1);//透明度从1-0-1
                alphaObj.setDuration(2000);
                //实现所有的方法
                alphaObj.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.i("MyLog", "onAnimationStart");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.i("MyLog", "onAnimationEnd");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.i("MyLog", "onAnimationCancel");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        Log.i("MyLog", "onAnimationRepeat");
                    }
                });
                //选择要实现的方法
                alphaObj.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });
                alphaObj.start();
                break;
            case R.id.btn_rotate:
                ObjectAnimator rotateObj = ObjectAnimator.ofFloat(
                        v,
                        "rotationY",//rotation,rotationX,rotationY
                        180);
                rotateObj.setDuration(2000);
                rotateObj.start();
                break;
            case R.id.btn_scale:
//                ObjectAnimator scaleObj = ObjectAnimator.ofFloat(
//                        v,
//                        "scaleX",//scaleX,scaleY
//                        2.0f);
//                scaleObj.setDuration(2000);
//                scaleObj.start();


                //使用PropertyValuesHolder 同时缩放x和y
                PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("scaleX", 0.5f);
                PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("scaleY", 1.5f);

                ObjectAnimator p = ObjectAnimator.ofPropertyValuesHolder(v, p1, p2);
                p.setDuration(2000).start();

                break;
            case R.id.btn_translate:
                ObjectAnimator translateObj = ObjectAnimator.ofFloat(
                        v,
                        "translationX",//translationX,translationY
                        0, 300);
                translateObj.setDuration(2000);
                //设置差值器
                translateObj.setInterpolator(new BounceInterpolator());
                translateObj.start();
                break;
            case R.id.btn_wrapper:
                WrapperView wrapperView = new WrapperView(v);
                ObjectAnimator.ofInt(wrapperView, "height", 0, 500)
                        .setDuration(2000).start();
                break;
            case R.id.btn_value_animator:
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
                valueAnimator.setDuration(2000).start();
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Float value = (Float) animation.getAnimatedValue();
                        btn_value_animator.setText(value + "");
                    }
                });

                break;
            case R.id.btn_animatorset:
                ObjectAnimator o1 = ObjectAnimator.ofFloat(v, "translationX", 0, 200f);
                ObjectAnimator o2 = ObjectAnimator.ofFloat(v, "alpha", 1f, 0, 1f);
                ObjectAnimator o3 = ObjectAnimator.ofFloat(v, "rotation", 0, 360);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(3000);
                animatorSet.playSequentially(o1, o2, o3);
                animatorSet.start();
                break;
            case R.id.btn_xml:
                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animator_scalex);
                animator.setTarget(v);
                animator.start();
                break;
            case R.id.btn_color:
                ObjectAnimator colorAnim=ObjectAnimator.ofInt(v,"backgroundColor",0xffff8080,0xff8080ff);
                colorAnim.setDuration(3000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.start();
                break;
        }
    }

    //包装height的get和set方法，就可以通过属性动画改变
    private static class WrapperView {
        private View mView;

        public WrapperView(View mView) {
            this.mView = mView;
        }

        public int getHeight() {
            return mView.getLayoutParams().height;
        }

        public void setHeight(int height) {
            mView.getLayoutParams().height = height;
            mView.requestLayout();
        }
    }
}
