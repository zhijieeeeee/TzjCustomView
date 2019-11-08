package com.tzj.tzjcustomview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressLint("AppCompatCustomView")
public class LongTouchBtn extends ImageView {

    private ScheduledExecutorService scheduledExecutorService;

    private LongTouchListener mListener;

    private int mtime;

    public LongTouchBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onLongTouch();
                        }
                    }
                }, 0, mtime, TimeUnit.SECONDS);
                return false;
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (scheduledExecutorService != null) {
                        scheduledExecutorService.shutdown();
                        scheduledExecutorService = null;
                    }
                }
                return false;
            }
        });
    }

    public void setOnLongTouchListener(LongTouchListener listener, int time) {
        mListener = listener;
        mtime = time;
    }

    public interface LongTouchListener {
        void onLongTouch();
    }
}
