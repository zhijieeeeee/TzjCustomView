package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.tzj.tzjcustomview.rvgalleryview.RvAdapter;
import com.tzj.tzjcustomview.rvgalleryview.RvAdapterWithScale;
import com.tzj.tzjcustomview.rvgalleryview.library.CardScaleHelper;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class RvGalleryWithScaleActivity extends SwipeBackActivity {

    private RecyclerView rv;
    private RvAdapterWithScale adapter;
    private List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_gallery);
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < 10; i++) {
            list.add(R.drawable.pic4);
            list.add(R.drawable.pic5);
            list.add(R.drawable.pic6);
        }
        adapter = new RvAdapterWithScale(list);
        rv.setAdapter(adapter);

        // mRecyclerView绑定scale效果
        CardScaleHelper mCardScaleHelper = new CardScaleHelper();
//        mCardScaleHelper.setCurrentItemPos(2);
        mCardScaleHelper.attachToRecyclerView(rv);
    }
}
