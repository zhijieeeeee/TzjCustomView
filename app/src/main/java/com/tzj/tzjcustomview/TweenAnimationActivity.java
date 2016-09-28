package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

/**
 * <p>
 * Description：补间动画
 * </p>
 *
 * @author tangzhijie
 */
public class TweenAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_alpha;
    private Button btn_rotate;
    private Button btn_scale;
    private Button btn_translate;
    private Button btn_animationset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_tween_animation);

        btn_alpha = (Button) findViewById(R.id.btn_alpha);
        btn_rotate = (Button) findViewById(R.id.btn_rotate);
        btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_translate = (Button) findViewById(R.id.btn_translate);
        btn_animationset = (Button) findViewById(R.id.btn_animationset);

        btn_alpha.setOnClickListener(this);
        btn_rotate.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        btn_translate.setOnClickListener(this);
        btn_animationset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alpha:
                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
                alphaAnimation.setDuration(2000);
                v.startAnimation(alphaAnimation);
                break;
            case R.id.btn_rotate:
//                RotateAnimation rotateAnimation = new RotateAnimation(
//                        0, 360, 100,100);
                //旋转中心为自身中心点
                RotateAnimation rotateAnimation = new RotateAnimation(
                        0, 360,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setDuration(1000);
                rotateAnimation.setRepeatCount(Animation.INFINITE);
                v.startAnimation(rotateAnimation);
                break;
            case R.id.btn_scale:
                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        0, 2f,
                        0, 2f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(2000);
                v.startAnimation(scaleAnimation);
                break;
            case R.id.btn_translate:
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        0, 200,
                        0, 200);
                translateAnimation.setDuration(2000);
                translateAnimation.setInterpolator(new BounceInterpolator());
                v.startAnimation(translateAnimation);
                break;
            case R.id.btn_animationset:
                animationSet(v);
                break;

        }
    }

    private void animationSet(View view) {
        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                0, 200,
                0, 200);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(2000);

        view.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i("MyLog", "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i("MyLog", "onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i("MyLog", "onAnimationRepeat");
            }
        });
    }
}
