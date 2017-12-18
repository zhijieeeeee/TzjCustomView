package com.tzj.tzjcustomview.verificationview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tzj.tzjcustomview.R;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class VerificationActivity extends AppCompatActivity {

    VerificationView vv;
    LinearLayout ll_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        ll_container= (LinearLayout) findViewById(R.id.ll_container);
        vv= (VerificationView) findViewById(R.id.vv);
        Button btnReset= (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.reset();
                Log.i("MyLog","验证码是="+vv.getText());
            }
        });
        Button btn_get= (Button) findViewById(R.id.btn_get);
        final ImageView iv= (ImageView) findViewById(R.id.iv);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_container.buildDrawingCache();
                Bitmap bitmap=ll_container.getDrawingCache();
                iv.setImageBitmap(bitmap);
            }
        });
    }
}
