package com.tzj.tzjcustomview.html;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class URLTagHandler implements Html.TagHandler {

    private Context mContext;
    int startTag;
    int endTag;

    String fundId = "";

    public URLTagHandler(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        // 处理标签<img>
        if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
            // 获取长度
            int len = output.length();
            // 获取图片地址
            ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
            String imgURL = images[0].getSource();
            // 使图片可点击并监听点击事件
            output.setSpan(new ClickableImage(mContext, imgURL), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (tag.toLowerCase(Locale.getDefault()).equals("span")) {
            //opening是否是标签的开始，tag标签的名字，output输出的文字，xmlReader用来获取自定义属性。
            if (opening) {
                startTag = output.length();
                fundId = startGame(tag, output, xmlReader);
                Log.i("MyLog", "fundId: " + fundId);
            } else {
                endTag = output.length();
                final String finalFundId = fundId;
                Log.i("MyLog", "finalFundId: " + finalFundId);
                if (!TextUtils.isEmpty(finalFundId)) {
                    output.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            Toast.makeText(mContext, finalFundId, Toast.LENGTH_SHORT).show();
                        }
                    }, startTag, endTag, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        Log.i("MyLog", tag);

    }

    private class ClickableImage extends ClickableSpan {
        private String url;
        private Context context;

        public ClickableImage(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            // 进行图片点击之后的处理
            Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
        }
    }


    public String startGame(String tag, Editable output, XMLReader xmlReader) {
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[]) dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer) lengthField.get(atts);
            String myAttributeA = null;
            String myAttributeB = null;
            for (int i = 0; i < len; i++) {
                if ("class".equals(data[i * 5 + 1])) {
                    myAttributeA = data[i * 5 + 4];
                    Log.i("MyLog", "class: " + myAttributeA);
                }
                if ("fund-id".equals(data[i * 5 + 1])) {
                    myAttributeB = data[i * 5 + 4];
                    Log.i("MyLog", "fund-id: " + myAttributeB);
                }
            }
            return myAttributeB;
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }

}
