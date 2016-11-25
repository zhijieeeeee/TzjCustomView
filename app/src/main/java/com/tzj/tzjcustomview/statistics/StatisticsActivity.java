package com.tzj.tzjcustomview.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tzj.tzjcustomview.R;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class StatisticsActivity extends AppCompatActivity {

    private PieView pieView;
    private int[] data = new int[]{10, 20,10};
    private String[] name = new String[]{"监管查询", "执法巡查", "执法巡查"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        pieView = (PieView) findViewById(R.id.pieView);
        pieView.setData(data, name);
    }
}
