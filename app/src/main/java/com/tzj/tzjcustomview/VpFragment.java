package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class VpFragment extends Fragment {

    private RecyclerView rv;
    private List<Integer> list = new ArrayList<>();

    public static VpFragment getInstance() {
        VpFragment vpFragment = new VpFragment();
        return vpFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vp, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        //support v4 24.2.0中增加一个非常重要的类SnapHelper，
        // 他的作用是让RecyclerView滑动视图后使停止位置正好停在某页的正中间
//        new LinearSnapHelper().attachToRecyclerView(rv);

        for (int i = 0; i < 15; i++) {
            list.add(R.drawable.pic4);
            list.add(R.drawable.pic5);
            list.add(R.drawable.pic6);
        }
        RvAdapter adapter = new RvAdapter(getActivity(), list);
        rv.setAdapter(adapter);
        return v;
    }



}
