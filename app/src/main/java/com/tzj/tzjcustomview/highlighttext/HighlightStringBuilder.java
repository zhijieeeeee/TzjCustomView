package com.tzj.tzjcustomview.highlighttext;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class HighlightStringBuilder {

    private SpannableStringBuilder mSpannableStringBuilder;

    private String content;
    private TextView textView;

    public HighlightStringBuilder setContent(String content) {
        this.content = content;
        mSpannableStringBuilder = new SpannableStringBuilder(content);
        return this;
    }

    public HighlightStringBuilder setTextView(TextView textView) {
        this.textView = textView;
        this.textView.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }

    public HighlightStringBuilder setHighlightContent(String highlightContent, MyClickableSpan clickableSpan) {
        int startIndex = content.indexOf(highlightContent);
        int endIndex = startIndex + highlightContent.length();
        mSpannableStringBuilder.setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public void create() {
        textView.setText(mSpannableStringBuilder);
    }

}
