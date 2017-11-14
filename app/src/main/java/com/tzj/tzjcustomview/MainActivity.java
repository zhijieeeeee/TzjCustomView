package com.tzj.tzjcustomview;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tzj.tzjcustomview.aidl.TestAidlActivity;
import com.tzj.tzjcustomview.andfix.AndFixActivity;
import com.tzj.tzjcustomview.annotation.AnnotationUse;
import com.tzj.tzjcustomview.annotation.OnValueAnnotation;
import com.tzj.tzjcustomview.annotation.TestAnnotation;
import com.tzj.tzjcustomview.databinding.DataBindingTestActivity;
import com.tzj.tzjcustomview.highlighttext.HighlightStringBuilder;
import com.tzj.tzjcustomview.highlighttext.MyClickableSpan;
import com.tzj.tzjcustomview.html.HtmlActivity;
import com.tzj.tzjcustomview.intercepttest.solvexy.SolveXYActivity;
import com.tzj.tzjcustomview.lottie.LottieActivity;
import com.tzj.tzjcustomview.proxy.Operate;
import com.tzj.tzjcustomview.proxy.OperateImpl;
import com.tzj.tzjcustomview.proxy.TestInvocationHandler;
import com.tzj.tzjcustomview.puzzle.PuzzleActivity;
import com.tzj.tzjcustomview.scrolltextview.ScrollTextViewActivity;
import com.tzj.tzjcustomview.statistics.StatisticsActivity;
import com.tzj.tzjcustomview.verificationview.VerificationActivity;

import java.lang.reflect.Proxy;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

@OnValueAnnotation("class")
public class MainActivity extends AppCompatActivity {

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
            "Notification测试",
            "补间动画相关测试",
            "属性动画相关测试",
            "扩散菜单",
            "拼图",
            "测试AIDL",
            "滑动冲突解决测试(横纵交替)",
            "DataBinding",
            "验证码",
            "统计",
            "各种Drawable",
            "AndFix热补丁修复",
            "Lottie",
            "DecorView和透明状态栏",
            "聊天输入框",
            "上滚TextView",
            "修改WheelView",
            "MoocView",
            "HTML.fromHtml",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //是否允许滑动返回
//        setSwipeBackEnable(false);

        //获取外部浏览器打开app时传入的参数Start
        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = intent.getData();

            if (uri != null) {
                String dataString = intent.getDataString();
                String scheme = uri.getScheme();
                String host = uri.getHost();
                String query = uri.getQuery();
                Toast.makeText(MainActivity.this, "外部浏览器打开时传进来的参数=" + query, Toast.LENGTH_LONG).show();
            }
        }
        //获取外部浏览器打开app时传入的参数End


        ListView lv = (ListView) findViewById(R.id.lv);
        //给ListView设置无数据时显示的布局
//        lv.setEmptyView(findViewById(R.id.iv_empty));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:

                        //隐式调用
                        Intent intent = new Intent();
                        //只要匹配action中的一个就行了
                        intent.setAction("xiu2");
                        //系统会默认添加default的category，所以在manifest中必须也添加default的category
                        //下面这个可设置可不设置，因为已经匹配了default
                        intent.addCategory("xiucategory");
                        //data如果没有指定URI，系统会默认content或file
//                        intent.setDataAndType(Uri.parse("file://abc"),"image/*");
                        intent.setDataAndType(Uri.parse("http://abc"), "video/*");

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
                    case 15:
                        startActivity(new Intent(MainActivity.this, TweenAnimationActivity.class));
                        break;
                    case 16:
                        startActivity(new Intent(MainActivity.this, PropertyAnimatorActivity.class));
                        break;
                    case 17:
                        startActivity(new Intent(MainActivity.this, AnimationMenuActivity.class));
                        break;
                    case 18:
                        startActivity(new Intent(MainActivity.this, PuzzleActivity.class));
                        break;
                    case 19:
                        startActivity(new Intent(MainActivity.this, TestAidlActivity.class));
                        break;
                    case 20:
                        startActivity(new Intent(MainActivity.this, SolveXYActivity.class));
                        break;
                    case 21:
                        startActivity(new Intent(MainActivity.this, DataBindingTestActivity.class));
                        break;
                    case 22:
                        startActivity(new Intent(MainActivity.this, VerificationActivity.class));
                        break;
                    case 23:
                        startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                        break;
                    case 24:
                        startActivity(new Intent(MainActivity.this, DrawableActivity.class));
                        break;
                    case 25:
                        startActivity(new Intent(MainActivity.this, AndFixActivity.class));
                        break;
                    case 26:
                        startActivity(new Intent(MainActivity.this, LottieActivity.class));
                        break;
                    case 27:
                        startActivity(new Intent(MainActivity.this, DecorViewActivity.class));
                        break;
                    case 28:
                        startActivity(new Intent(MainActivity.this, InputActivity.class));
                        break;
                    case 29:
                        startActivity(new Intent(MainActivity.this, ScrollTextViewActivity.class));
                        break;
                    case 30:
                        startActivity(new Intent(MainActivity.this, WheelViewActivity.class));
                        break;
                    case 31:
                        startActivity(new Intent(MainActivity.this, MoocActivity.class));
                        break;
                    case 32:
                        startActivity(new Intent(MainActivity.this, HtmlActivity.class));
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

        startService(new Intent(this, MyService.class));

        //IntentService,顺序执行耗时操作
        Intent service = new Intent(this, MyIntentService.class);
        service.putExtra("task_action", "T1");
        startService(service);
        service.putExtra("task_action", "T2");
        startService(service);
        service.putExtra("task_action", "T3");
        startService(service);

        //动态代理
        TestInvocationHandler testInvocationHandler = new TestInvocationHandler(new OperateImpl());
        Operate operate = (Operate) (Proxy.newProxyInstance(
                Operate.class.getClassLoader(),
                new Class[]{Operate.class},
                testInvocationHandler));
        operate.op1();
        operate.op2();
        operate.op3();

//        throw new RuntimeException("捕获测试异常2222222222222222222222");

        TextView tv = (TextView) findViewById(R.id.tv);


        new HighlightStringBuilder()
                .setContent("我在测试，<点我>少数哦爱好左上角愛哦<也可以点我>,<我没有颜色>-------吞吞吐吐")
                .setTextView(tv)
                .setHighlightContent("<点我>", new MyClickableSpan(R.color.colorAccent) {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(MainActivity.this, "点我", Toast.LENGTH_SHORT).show();
                    }
                })
                .setHighlightContent("<也可以点我>", new MyClickableSpan(R.color.green) {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(MainActivity.this, "也可以点我", Toast.LENGTH_SHORT).show();
                    }
                })
                .setHighlightContent("<我没有颜色>", new MyClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(MainActivity.this, "我没有颜色", Toast.LENGTH_SHORT).show();
                    }
                })
                .setHighlightContent("吞吞吐吐", R.color.green)
                .setHighlightContent("sssss", null)
                .create();

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
