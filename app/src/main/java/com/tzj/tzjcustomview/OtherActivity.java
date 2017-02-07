package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;

import com.tzj.tzjcustomview.loadingview.MyShapeLoadingView;
import com.tzj.tzjcustomview.triangleview.TriangleWithCircleView;

/**
 * <p> FileName： OtherActivity</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-02-18 13:47
 */
public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        setContentView(R.layout.activity_other);

//        ((ViewStub) findViewById(R.id.vs_empty)).setVisibility(View.VISIBLE);
//        ((ViewStub) findViewById(R.id.vs_empty)).inflate();

    }

}
