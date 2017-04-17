package com.tzj.tzjcustomview.scrolltextview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tzj.tzjcustomview.R;
import com.tzj.tzjcustomview.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/17.
 */

public class ScrollTextViewActivity extends AppCompatActivity {

    private LinearLayout ll_container;
    private String[] array = new String[]{"某某人购买了金鹰核心1", "某某人购买了金鹰核心2", "某某人购买了金鹰核心3", "某某人购买了金鹰核心4"};
    float allDis;
    ScheduledExecutorService scheduledThreadPool;

    private List<View> viewList = new ArrayList<>();

    private int currentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_textview);

        ll_container = (LinearLayout) findViewById(R.id.ll_container);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll_container.getLayoutParams();
        lp.height = 4 * lp.height;
        ll_container.requestLayout();

        for (int i = 0; i < array.length; i++) {
            TextView view = (TextView) LayoutInflater.from(this).inflate(R.layout.item_scroll_textview, ll_container, false);
            view.setText(array[i]);
            viewList.add(view);
            ll_container.addView(view);
        }


        scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allDis += ScreenUtil.dip2px(ScrollTextViewActivity.this, 50);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ll_container, "translationY", -allDis);
                        objectAnimator.setDuration(500);
                        objectAnimator.start();
                        objectAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                TextView view = (TextView) LayoutInflater.from(ScrollTextViewActivity.this).inflate(R.layout.item_scroll_textview, ll_container, false);
                                view.setText(array[currentIndex % viewList.size()]);
                                ll_container.addView(view);
                                currentIndex++;
                            }
                        });
                    }
                });

            }
        }, 2, 2, TimeUnit.SECONDS);


        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduledThreadPool.shutdown();
                Log.i("MyLog", "ll_container.getChildCount()=" + ll_container.getChildCount());
            }
        });
    }
}
