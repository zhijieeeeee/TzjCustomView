package com.tzj.tzjcustomview.highlighttext;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description：高亮工具类
 * </p>
 *
 * @author tangzhijie
 */
public class HighlightStringBuilder {

    private SpannableStringBuilder mSpannableStringBuilder;

    private List<String> highLightList = new ArrayList<>();
    private List<CharacterStyle> spanList = new ArrayList<>();

    private Context context;
    private String content;
    private TextView textView;

    public HighlightStringBuilder(Context context) {
        this.context = context;
    }

    /**
     * 设置文本内容
     *
     * @param content 文本内容
     */
    public HighlightStringBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 设置TextView控件
     *
     * @param textView 目标TextView
     */
    public HighlightStringBuilder setTextView(TextView textView) {
        this.textView = textView;
        return this;
    }

    /**
     * 设置高亮内容与点击事件
     *
     * @param highlightContent 需要高亮的文本
     * @param clickableSpan    高亮文本对应的点击事件，可为null
     */
    public HighlightStringBuilder setHighlightContent(String highlightContent, MyClickableSpan clickableSpan) {
        if (TextUtils.isEmpty(highlightContent)) {
            return this;
        }
        highLightList.add(highlightContent);
        if (clickableSpan != null) {
            spanList.add(clickableSpan);
        } else {
            spanList.add(new MyClickableSpan(context) {
                @Override
                public void onClick(View widget) {

                }
            });
        }
        return this;
    }

    /**
     * 设置高亮内容(不需点击事件)
     *
     * @param highlightContent 需要高亮的文本
     * @param color            高亮颜色
     */
    public HighlightStringBuilder setHighlightContent(String highlightContent, @ColorRes int color) {
        if (TextUtils.isEmpty(highlightContent)) {
            return this;
        }
        CharacterStyle characterStyle;
        try {
            characterStyle = new ForegroundColorSpan(ContextCompat.getColor(context, color));
            spanList.add(characterStyle);
            highLightList.add(highlightContent);
        } catch (Exception e) {
            //颜色值不存在
        }
        return this;
    }

    /**
     * 一定要在最后一步调用
     */
    public void create() {
        if (textView == null) {
            throw new IllegalArgumentException("请调用setTextView设置目标TextView");
        }
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("原始文本不能为空");
        }
        if (mSpannableStringBuilder != null) {
            return;
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        mSpannableStringBuilder = new SpannableStringBuilder(content);
        for (int i = 0; i < highLightList.size(); i++) {
            String highLightContent = highLightList.get(i);
            if (!content.contains(highLightContent)) {
                continue;
            }
            int startIndex = content.indexOf(highLightContent);
            int endIndex = startIndex + highLightContent.length();
            mSpannableStringBuilder.setSpan(spanList.get(i), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(mSpannableStringBuilder);
    }

}
