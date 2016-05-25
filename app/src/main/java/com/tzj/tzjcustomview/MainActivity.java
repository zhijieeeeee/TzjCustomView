package com.tzj.tzjcustomview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzj.tzjcustomview.exchangeview.DepthPageTransformer;
import com.tzj.tzjcustomview.exchangeview.TestFragment;
import com.tzj.tzjcustomview.exchangeview.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_xiu;
    private Button btn_gao;
    private Button btn_guayigua;
    private Button btn_vp;
    private Button btn_swipecard;
    private Button btn_study;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_xiu = (Button) findViewById(R.id.btn_xiu);
        btn_guayigua = (Button) findViewById(R.id.btn_guayigua);
        btn_gao = (Button) findViewById(R.id.btn_gao);
        btn_vp = (Button) findViewById(R.id.btn_vp);
        btn_swipecard= (Button) findViewById(R.id.btn_swipecard);
        btn_study= (Button) findViewById(R.id.btn_study);
        btn_xiu.setOnClickListener(this);
        btn_gao.setOnClickListener(this);
        btn_guayigua.setOnClickListener(this);
        btn_vp.setOnClickListener(this);
        btn_swipecard.setOnClickListener(this);
        btn_study.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xiu:
                startActivity(new Intent(MainActivity.this, XiuActivity.class));
                break;
            case R.id.btn_gao:
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
                break;
            case R.id.btn_guayigua:
                startActivity(new Intent(MainActivity.this, GuaActivity.class));
                break;
            case R.id.btn_vp:
                startActivity(new Intent(MainActivity.this, ExchangeActivity.class));
                break;
            case R.id.btn_swipecard:
                startActivity(new Intent(MainActivity.this, SwipeCardActivity.class));
                break;
            case R.id.btn_study:
                startActivity(new Intent(MainActivity.this, StudyActivity.class));
                break;
        }
    }
}
