package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Window;
import android.widget.Toast;

/**
 * <p> FileName： XiuActivity</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-02-18 13:43
 */
public class XiuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());

        setContentView(R.layout.activity_xiu);


//        String tzj=getIntent().getStringExtra("tzj");
//        Toast.makeText(this,tzj,Toast.LENGTH_SHORT).show();

    }
}
