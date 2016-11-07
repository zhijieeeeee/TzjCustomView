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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        tv= (TextView) findViewById(R.id.tv);

        moveClockView= (ClockView) findViewById(R.id.clockView);
        moveClockView.setOnCurrentTimeListener(new ClockView.OnCurrentTimeListener() {
            @Override
            public void currentTime(String time) {
                tv.setText(time);
            }
        });
    }
}
