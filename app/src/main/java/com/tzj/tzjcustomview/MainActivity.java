package com.tzj.tzjcustomview;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tzj.tzjcustomview.annotation.AnnotationUse;
import com.tzj.tzjcustomview.annotation.OnValueAnnotation;
import com.tzj.tzjcustomview.annotation.TestAnnotation;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

@OnValueAnnotation("class")
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
            "自动校正并缩放的recyclerView",
            "InterceptTest",
            "平移各种方法测试与Scroller",
            "绘制时钟",
            "材料设计的一些属性",
            "Notification测试", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //是否允许滑动返回
        setSwipeBackEnable(false);

        ListView lv = (ListView) findViewById(R.id.lv);
        //给ListView设置无数据时显示的布局
        lv.setEmptyView(findViewById(R.id.iv_empty));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, XiuActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //加上5.0以上的翻页效果
                            startActivity(intent, ActivityOptions
                                    .makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        } else {
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, OtherActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intent1, ActivityOptions
                                    .makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        } else {
                            startActivity(intent1);
                        }
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
                    case 10:
                        startActivity(new Intent(MainActivity.this, InterceptTestActivity.class));
                        break;
                    case 11:
                        startActivity(new Intent(MainActivity.this, ScrollTestActivity.class));
                        break;
                    case 12:
                        startActivity(new Intent(MainActivity.this, ClockActivity.class));
                        break;
                    case 13:
                        startActivity(new Intent(MainActivity.this, MaterialActivity.class));
                        break;
                    case 14:
                        startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                        break;
                }
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
//                Log.i("MyLog", "firstVisibleItem=" + firstVisibleItem);
//                Log.i("MyLog", "visibleItemCount=" + visibleItemCount);
//                Log.i("MyLog", "totalItemCount=" + totalItemCount);
            }
        });

        //获得注解
        AnnotationUse.use(this);
    }

    @TestAnnotation(
            name = "tangzhijie",
            age = 100
    )
    @OnValueAnnotation("only one value")
    public void annotationMethod() {

    }

    @TestAnnotation(age = 30)
    public boolean annotationMethod2() {
        return false;
    }
}
