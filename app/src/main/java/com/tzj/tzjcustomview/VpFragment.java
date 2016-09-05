package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class VpFragment extends Fragment {

    public static VpFragment getInstance() {
        VpFragment vpFragment = new VpFragment();
        return vpFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vp, container, false);
        return v;
    }
}
