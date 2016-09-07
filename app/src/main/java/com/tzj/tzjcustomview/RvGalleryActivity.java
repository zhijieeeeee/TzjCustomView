package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.tzj.tzjcustomview.rvgalleryview.RvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class RvGalleryActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RvAdapter adapter;
    private List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_gallery);
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        //support v4 24.2.0中增加一个非常重要的类SnapHelper，
        // 他的作用是让RecyclerView滑动视图后使停止位置正好停在某页的正中间
        new LinearSnapHelper().attachToRecyclerView(rv);

        for (int i = 0; i < 10; i++) {
            list.add(R.drawable.pic4);
            list.add(R.drawable.pic5);
            list.add(R.drawable.pic6);
        }
        adapter = new RvAdapter(list);
        rv.setAdapter(adapter);
    }
}
