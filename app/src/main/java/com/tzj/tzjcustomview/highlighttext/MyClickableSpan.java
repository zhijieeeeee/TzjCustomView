package com.tzj.tzjcustomview.highlighttext;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public abstract class MyClickableSpan extends ClickableSpan {

    private Context context;
    private int color;

    public MyClickableSpan(Context context) {
        this.context = context;
    }

    public MyClickableSpan(Context context, @ColorRes int color) {
        this.color = color;
        this.context = context;
    }

    public abstract void onClick(View widget);

    @Override
    public void updateDrawState(TextPaint ds) {
        //去掉下划线
        ds.setUnderlineText(false);
        if (color != 0) {
            ds.setColor(ContextCompat.getColor(context, color));
        }
    }
}
