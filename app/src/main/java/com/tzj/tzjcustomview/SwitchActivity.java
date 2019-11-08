package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tzj.tzjcustomview.switchview.SwitchView;

/**
 * Description：I won't write any description!
 * Author ：Spider-Man.
 * Date：2019/1/2
 */
public class SwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        final SwitchView switchView = (SwitchView) findViewById(R.id.switchView);
        switchView.setStatus(true);
        switchView.setOnSwitchListener(new SwitchView.OnSwitchListener() {
            @Override
            public void onSwitch() {
                switchView.changeStatus();
            }
        });
    }
}
