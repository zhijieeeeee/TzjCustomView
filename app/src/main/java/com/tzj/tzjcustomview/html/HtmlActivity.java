package com.tzj.tzjcustomview.html;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tzj.tzjcustomview.R;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class HtmlActivity extends AppCompatActivity {

    private EditText et;
    private Button btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);

        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);

        String html = "<b>哈哈哈哈</b><br/>" +
                "<img src=\"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4131634322,487666839&fm=27&gp=0.jpg\" /><br>" +
                "少数解耦爱江山哦i";

        //设置超链接可以打开网页//click must
        et.setMovementMethod(LinkMovementMethod.getInstance());
        et.setText(Html.fromHtml(html, new URLImageGetter(et, this), new URLTagHandler(this)));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start = et.getSelectionStart();
                int end = et.getSelectionEnd();
//                CharSequence selectText = et.getText().subSequence(start, end);
                Toast.makeText(HtmlActivity.this, Html.toHtml(et.getText()), Toast.LENGTH_SHORT).show();
                Log.i("MyLog",Html.toHtml(et.getText()));
                Log.i("MyLog","start="+start);
                Log.i("MyLog","end="+end);

                String sss=Html.toHtml(et.getText());
                et.setText(Html.fromHtml(sss, new URLImageGetter(et, HtmlActivity.this), new URLTagHandler(HtmlActivity.this)));
            }
        });
    }
}
