package com.tzj.tzjcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class MainActivity extends SwipeBackActivity {

    private String[] item = {
            "咻一咻",
            "高仿",
            "刮刮卡",
            "卡片切换(Vp+Transformers)",
            "SwipeCard",
            "学习Measure",
            "TabLayout+Viewpager",
            "ViewDragHelper",
            "自动校正的recyclerView",
            "自动校正并缩放的recyclerView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //是否允许滑动返回
        setSwipeBackEnable(false);

        ListView lv = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, XiuActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, OtherActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, GuaActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ExchangeActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, SwipeCardActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, StudyActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, TabVpActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this, ViewDragHelperActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(MainActivity.this, RvGalleryActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(MainActivity.this, RvGalleryWithScaleActivity.class));
                        break;

                }
            }
        });
    }
}
