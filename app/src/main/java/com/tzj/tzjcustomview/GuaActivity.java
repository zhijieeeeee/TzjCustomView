package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tzj.tzjcustomview.guaguaview.GuaGuaView;

/**
 * <p> FileName： GuaActivity</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-02-18 13:46
 */
public class GuaActivity extends AppCompatActivity {

    private Button btn_clear;

    private GuaGuaView guaguaview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guaguaka);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        guaguaview = (GuaGuaView) findViewById(R.id.guaguaview);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guaguaview.clear();
            }
        });
    }
}
