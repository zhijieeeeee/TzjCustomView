package com.tzj.tzjcustomview.studyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class ProgressView extends View {

    private Paint circlePaint;
    private Paint textPaint;
    private Paint arcPaint;

    //弧线的外接矩形
    private RectF arcRectF;

    private String text = "Android";
    //获取字的宽高
    private Rect textBound = new Rect();

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);

        arcPaint = new Paint();
        arcPaint.setColor(Color.RED);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(20);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(80);
        //获取字的宽高
        textPaint.getTextBounds(text, 0, text.length(), textBound);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        arcRectF = new RectF(getMeasuredWidth() * 0.1f,
                getMeasuredWidth() * 0.1f,
                getMeasuredWidth() * 0.9f,
                getMeasuredWidth() * 0.9f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆形
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                getMeasuredWidth() / 4, circlePaint);
        //绘制弧形
        canvas.drawArc(arcRectF, 0, 270, false, arcPaint);
        //绘制文字
        canvas.drawText(text, 0, text.length(),
                getMeasuredWidth() / 2 - textBound.width() / 2,
                getMeasuredHeight() / 2 + textBound.height() / 2,
                textPaint);
    }
}
