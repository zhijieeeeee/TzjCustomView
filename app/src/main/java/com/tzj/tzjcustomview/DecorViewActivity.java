package com.tzj.tzjcustomview;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 学习DecorView，并且解决透明状态栏下，标题被软键盘顶上去的问题
 */

public class DecorViewActivity extends AppCompatActivity {

    //不设置透明状态栏且不设置adjustPan或adjustResize，Scrollview中editText会自动被软键盘弹起
    //不设置透明状态栏但设置adjustPan，ScrollView和标题栏都会被顶上去
    //不设置透明状态栏但设置adjustResize，Scrollview中editText会自动被软键盘弹起
    // (和不设置adjustPan或adjustResize一样 )


    //设置透明状态栏并且不设置adjustPan或adjustResize,没有任何效果
    //设置透明状态栏和adjustPan,ScrollView和标题栏都会被顶上去
    //设置透明状态栏和adjustResize,没有任何效果

    private LinearLayout sv;
    private EditText et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decorview);
        //一、DecorView为整个Window界面的最顶层View。
        //二、DecorView只有一个子元素为LinearLayout。
        // 代表整个Window界面，包含通知栏，标题栏，内容显示栏三块区域


        //DecorView中装载了一个LinearLayout，用来放置TitleView（通常是ActionBar）
        //和ContentView（是一个FrameLayout，我们setContentView就是放到这里面）

//        View decorView = getWindow().getDecorView();
        //SYSTEM_UI_FLAG_FULLSCREEN表示全屏的意思，也就是会将状态栏隐藏(注意不包括ActionBar)
//        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //setSystemUiVisibility()方法来设置系统UI元素的可见性
//        decorView.setSystemUiVisibility(option);
        //隐藏ActionBar
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        //Api19 4.4以上使用半透明状态栏   方法一
        //这种方法，状态栏底部会有一层透明的灰层（半透明）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        //Api21 以上使用透明状态栏   方法二
        //这种方法，状态栏底部完全透明
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        //解决透明状态栏下输入框被软键盘遮住
        sv = (LinearLayout) findViewById(R.id.sv);
        et = (EditText) findViewById(R.id.et);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    resetSv();
                }
            }
        });
    }

    /**
     * 根据软键盘的高度设置ScrollView的marginBottom
     */
    private void resetSv() {
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //getWindowVisibleDisplayFrame(rect)可以获取到程序显示的区域，包括标题栏
                // 但不包括状态栏,获取后的区域坐标会保存在rect(Rect类型)中
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = ScreenUtil.getScreenHeight(DecorViewActivity.this);
                int heightDifference = screenHeight - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                Log.i("MyLog", "heightDifference=" + heightDifference);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) sv.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, heightDifference);//设置sv的marginBottom的值为软键盘占有的高度即可
                sv.requestLayout();
            }
        });
    }


}
