package com.tzj.tzjcustomview.switchview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.ObjectAnimator;
import com.tzj.tzjcustomview.R;

/**
 * Description：I won't write any description!
 * Author ：Spider-Man.
 * Date：2019/1/2
 */
public class SwitchView extends LinearLayout {

    private ImageView iv_bg;
    private ImageView iv_thumb;

    //状态 true 开；false 关
    private boolean status = false;

    private ObjectAnimator objectAnimator;

    //点击监听
    private OnSwitchListener onSwitchListener;

    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        this.onSwitchListener = onSwitchListener;
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_switch, this, true);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        iv_thumb = (ImageView) findViewById(R.id.iv_thumb);
        iv_thumb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSwitchListener != null) {
                    onSwitchListener.onSwitch();
                }
            }
        });
    }

    /**
     * 动画改变状态
     */
    public void changeStatus() {
        int distance = iv_bg.getWidth() - iv_thumb.getWidth();
        if (status) {
            objectAnimator = ObjectAnimator.ofFloat(iv_thumb, "translationX", distance, 0);
            iv_bg.setImageResource(R.drawable.bg_off);
        } else {
            objectAnimator = ObjectAnimator.ofFloat(iv_thumb, "translationX", 0, distance);
            iv_bg.setImageResource(R.drawable.bg_on);
        }
        objectAnimator.setDuration(200);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        status = !status;
    }

    /**
     * 设置初始状态
     */
    public void setStatus(boolean status) {
        this.status = status;
        if (status) {
            iv_bg.setImageResource(R.drawable.bg_on);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv_thumb.setTranslationX(iv_bg.getWidth() - iv_thumb.getWidth());
                }
            }, 100);
        } else {
            iv_bg.setImageResource(R.drawable.bg_off);
        }
    }

    /**
     * 获取状态
     */
    public boolean getStatus() {
        return status;
    }

    public interface OnSwitchListener {
        void onSwitch();
    }
}
