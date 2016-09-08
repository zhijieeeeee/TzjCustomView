package com.tzj.tzjcustomview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class TabVpDetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout toolbar_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_vp_detail);

        toolbar_layout= (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar_layout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        toolbar_layout.setExpandedTitleColor(Color.parseColor("#ffffff"));
    }
}
