package com.tzj.tzjcustomview.html;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tzj.tzjcustomview.R;

import jp.wasabeef.richeditor.RichEditor;

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
    private WebView wb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);

        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);
        wb = (WebView) findViewById(R.id.wb);

        String html = "<b>哈哈哈哈</b><br/>" +
                "<img src=\"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4131634322,487666839&fm=27&gp=0.jpg\" alt=\"11111\"/><br>" +
                "<span class=\"fund_url\" fund-id =\"1916\">1916</span><fund>77777</fund>少数解耦爱江山哦i" +
                "<span class=\"fund_url\" fund-id =\"210000\">210000</span>" +
                "<span>没有class的</span>"
                +"<video src=\\\"http://o7iblkvul.bkt.clouddn.com/test.mp3\\\" type=\\\"video/x-ms-asf-plugin\\\" width=\\\"550\\\" height=\\\"400\\\" autostart=\\\"false\\\" loop=\\\"true\\\" />";

        //TextView加载Html代码
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
                Log.i("MyLog", Html.toHtml(et.getText()));
                Log.i("MyLog", "start=" + start);
                Log.i("MyLog", "end=" + end);

                String sss = Html.toHtml(et.getText());
                et.setText(Html.fromHtml(sss, new URLImageGetter(et, HtmlActivity.this), new URLTagHandler(HtmlActivity.this)));
            }
        });


        //使用WebView加载html源码，并且注入js和css

        wb.getSettings().setJavaScriptEnabled(true);
        wb.addJavascriptInterface(new JsCallJavaObj() {
            @Override
            @JavascriptInterface
            public void toFundDetail(String fundId) {
                if (!TextUtils.isEmpty(fundId)) {
                    Toast.makeText(HtmlActivity.this, fundId, Toast.LENGTH_SHORT).show();
                }
            }
        }, "jsCallJavaObj");
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageClickListner();
            }
        });
        //拼接css
        String CSS_STYPE = "<head><style>img{max-width:100%  !important;}  " +
                ".fund_url{color:#3d9dff}</style></head>";
        String h = CSS_STYPE + html;
        wb.loadData(CSS_STYPE + h, "text/html; charset=UTF-8", null);//这种写法可以正确解码

    }

    //注入js，并用js调用原生方法
    private void addImageClickListner() {
        wb.loadUrl("javascript:function img(){" +
                "var href=document.getElementsByTagName(\"img\");" +
                "\t\t for(var i=0;i<href.length;i++){\n" +
                "\t\t \t   var a=href[i];\n" +
                "\t\t \t   a.onclick=function(){\n" +
                "\t\t \t        window.jsCallJavaObj.toFundDetail(this.alt)" +
                "\t\t \t   };\n" +
                "\t\t }" +
                "}");
        wb.loadUrl("javascript:function span(){" +
                "var ttt=document.getElementsByTagName(\"span\");" +
                "\t\t for(var i=0;i<ttt.length;i++){\n" +
                "\t\t \t   var t=ttt[i];\n" +
                "\t\t \t   t.onclick=function(){\n" +
                "\t\t \t        window.jsCallJavaObj.toFundDetail(this.getAttribute('fund-id'))" +
                "\t\t \t   };\n" +
                "\t\t }" +
                "}");
        //执行js函数
        wb.loadUrl("javascript:img()");
        wb.loadUrl("javascript:span()");
    }


    /**
     * Js調用Java接口
     */
    private interface JsCallJavaObj {
        void toFundDetail(String fundId);
    }
}
