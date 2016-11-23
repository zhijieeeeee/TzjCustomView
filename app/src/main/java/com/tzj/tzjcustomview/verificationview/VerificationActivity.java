package com.tzj.tzjcustomview.verificationview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        vv= (VerificationView) findViewById(R.id.vv);
        Button btnReset= (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.reset();
                Log.i("MyLog","验证码是="+vv.getText());
            }
        });
    }
}
