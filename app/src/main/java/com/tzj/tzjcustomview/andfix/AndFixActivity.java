package com.tzj.tzjcustomview.andfix;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tzj.tzjcustomview.App;
import com.tzj.tzjcustomview.R;

import java.io.IOException;

/**
 * <p>
 * Description：AndFix热补丁修复测试
 * </p>
 *
 * @author tangzhijie
 */
public class AndFixActivity extends AppCompatActivity {

    private Button btnLoadPatch;
    private TextView tv;
    private Button btnSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andfix);

        btnSet = (Button) findViewById(R.id.btnSet);
        tv = (TextView) findViewById(R.id.tv);
        btnLoadPatch = (Button) findViewById(R.id.btnLoadPatch);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set();
            }
        });
        btnLoadPatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载补丁，一般从服务器下载，这里直接放在sd卡下
                String patchPath = Environment.getExternalStorageDirectory()
                        .getPath() + "/test.apatch";
                try {
                    App.getApplication().mPatchManager.addPatch(patchPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void set() {
        tv.setText("使用热补丁修复了");
    }
}
