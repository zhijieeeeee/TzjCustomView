package com.tzj.tzjcustomview;

import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class MaterialActivity extends AppCompatActivity {

    private View v1;
    private View v2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider1 = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
                }
            };
            ViewOutlineProvider viewOutlineProvider2 = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            };

            v1.setOutlineProvider(viewOutlineProvider1);
            v2.setOutlineProvider(viewOutlineProvider2);
        }

    }
}
