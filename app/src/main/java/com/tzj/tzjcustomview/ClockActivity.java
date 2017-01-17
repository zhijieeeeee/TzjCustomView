package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.don.clockviewlibrary.ClockView;

/**
 * <p>
 * Description：钟表绘制
 * </p>
 *
 * @author tangzhijie
 */
public class ClockActivity extends AppCompatActivity {

    private ClockView moveClockView;
    private TextView tv;

    //这里是用来测试内存泄漏的
    private static Leak mLeak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        tv = (TextView) findViewById(R.id.tv);

        moveClockView = (ClockView) findViewById(R.id.clockView);
        moveClockView.setOnCurrentTimeListener(new ClockView.OnCurrentTimeListener() {
            @Override
            public void currentTime(String time) {
                tv.setText(time);
            }
        });

        //这里是用来测试内存泄漏的
        mLeak = new Leak();
    }

    //这里是用来测试内存泄漏的
    //非静态内部类会引用外部类对象，这里Leak引用了外部类ClockActivity
    //而mLeak是静态的，ClockActivity被引用无法回收，就发生了内存泄漏
    class Leak {

    }
}
