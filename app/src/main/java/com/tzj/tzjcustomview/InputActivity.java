package com.tzj.tzjcustomview;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */

public class InputActivity extends AppCompatActivity {

    private LinearLayout ll;
    private EditText et;
    private ListView lv;
    private List<String> list = new ArrayList<>();
    private InputAdapter adatper;
    private LinearLayout ll_et;

    private TextView tv;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        tv = (TextView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(et.getText());
            }
        });

        ll_et = (LinearLayout) findViewById(R.id.ll_et);

        ll = (LinearLayout) findViewById(R.id.ll);

        et = (EditText) findViewById(R.id.et);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    resetSv();
                }
            }
        });

        lv = (ListView) findViewById(R.id.lv);

        list.add("我是评论A");
        list.add("我是评论B");
        list.add("我是评论C");
        list.add("我是评论D");
        list.add("我是评论E");
        list.add("我是评论F");
        list.add("我是评论G");
        list.add("我是评论H");
        list.add("我是评论I");
        list.add("我是评论J");
        list.add("我是评论K");
        list.add("我是评论L");
        list.add("我是评论M");
        list.add("我是评论N");
        adatper = new InputAdapter(this, list);
        lv.setAdapter(adatper);
        adatper.setOnCommentClick(new InputAdapter.OnCommentListener() {
            @Override
            public void onComment(final View view, int position) {
                et.requestFocus();
                FunctionUtil.showSoftKeyboard(et, InputActivity.this);

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int[] location = new int[2];
                        //一个控件在其整个屏幕上的坐标位置（左上角）
                        view.getLocationOnScreen(location);
                        //点击的item的底部Y坐标（左上角Y+控件高度）
                        Log.i("MyLog", "ScreenUtil.getScreenHeight=" + ScreenUtil.getScreenHeight(InputActivity.this));
                        Log.i("MyLog", "view.getHeight()=" + view.getHeight());
                        Log.i("MyLog", "location[1]=" + location[1]);
                        int viewY = ScreenUtil.getScreenHeight(InputActivity.this) - (location[1] + view.getHeight());
                        Log.i("MyLog", "view距离底部=" + viewY);


                        int[] location2 = new int[2];
                        //获取评论框的y坐标
                        ll_et.getLocationOnScreen(location2);
                        Log.i("MyLog", "ll_etY坐标=" + location2[1]);
                        int etBottom = ScreenUtil.getScreenHeight(InputActivity.this) - location2[1];
                        Log.i("MyLog", "ll_et距离底部=" + etBottom);


                        int dis = viewY - etBottom;

                        //滑动的距离=item底部坐标-软键盘高度-输入框高度
//                        int dis = viewY - (840+ll_et.getMeasuredHeight());
                        Log.i("MyLog", "dis=" + dis);
//                        Log.i("MyLog", "ll_et.getMeasuredHeight()=" + ll_et.getMeasuredHeight());
                        lv.smoothScrollBy(-dis, 300);
                    }
                }, 200);


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
                int screenHeight = ScreenUtil.getScreenHeight(InputActivity.this);
                int heightDifference = screenHeight - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, heightDifference);//设置sv的marginBottom的值为软键盘占有的高度即可
                ll.requestLayout();

                //
            }
        });
    }
}
