package com.tzj.tzjcustomview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tzj.tzjcustomview.swipecardview.SwipeCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> FileName： SwipeCardActivity</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-15 10:56
 */
public class SwipeCardActivity extends Activity {

    private SwipeCardView swipecard;
    private List<View> veiwList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipecard);
        swipecard = (SwipeCardView) findViewById(R.id.swipecard);
        for (int i = 0; i < 5; i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.item_card, null);
            TextView tv = (TextView) v.findViewById(R.id.tv);
            Button btn = (Button) v.findViewById(R.id.btn);
            btn.setTag(v);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swipecard.deleteItem((View) view.getTag());
                }
            });
            tv.setText("我是第" + i + "个");
            veiwList.add(v);
        }
        swipecard.setViewList(veiwList);
    }
}
