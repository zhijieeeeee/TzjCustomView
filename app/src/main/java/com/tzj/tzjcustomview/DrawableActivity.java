package com.tzj.tzjcustomview;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class DrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        TextView tvTransition = (TextView) findViewById(R.id.tvTransition);
        TransitionDrawable transitionDrawable = (TransitionDrawable) tvTransition.getBackground();
        transitionDrawable.startTransition(3000);
    }
}
