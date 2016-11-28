package com.tzj.tzjcustomview.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.don.pieviewlibrary.PieView;
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
    private int[] data = new int[]{1,2,3,4,5};
    private String[] name = new String[]{"监管查询1","监管查询2","监管查询3","监管查询4","监管查询5"};

    private PieView pieView2;
    private int[] data2 = new int[]{10,20,30};
    private String[] name2 = new String[]{"北京","上海","深圳"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        pieView = (PieView) findViewById(R.id.pieView);
        pieView.setData(data, name);

        pieView2 = (PieView) findViewById(R.id.pieView2);
        pieView2.setData(data2, name2);
    }
}
