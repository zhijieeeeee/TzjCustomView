package com.tzj.tzjcustomview.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.tzj.tzjcustomview.Point;

/**
 * <p> FileName： MoveCircleView</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-10 14:20
 */
public class MoveCircleView extends View {

    Path circlePath = new Path();

    Paint mPaint;

    RectF mRectf;

    private int currentAngle = 0;

    private boolean isDrawCircle = true;

    private Path rightLinePath = new Path();
    private Path leftLinePath = new Path();

    private int margin = 20;

    private int radio;

    private Point startPoint = new Point();

    private float currentStart;

    private Point centerPoint = new Point();

    private float currentCenter;

    private Point endPoint = new Point();

    private float gen3 = (float) Math.sqrt(3);

    private float k1, k2;
    private float b1, b2;

    private static final int CIRCLE_SPEED=5;
    private static final int GOGO_SPEED=4;

    public MoveCircleView(Context context) {
        super(context);
    }

    public MoveCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(200, 200);

        radio = getMeasuredWidth() / 2 - margin;

        startPoint.x = gen3 / 2 * radio + getMeasuredWidth() / 2-10;
        startPoint.y = getMeasuredHeight() / 2 - radio / 2;
        rightLinePath.moveTo(startPoint.x, startPoint.y);

        currentStart = startPoint.x;

        centerPoint.x = startPoint.x - radio;
        centerPoint.y = startPoint.y + radio;
        leftLinePath.moveTo(centerPoint.x, centerPoint.y);

        currentCenter = centerPoint.x;

        endPoint.x = centerPoint.x - radio / 2;
        endPoint.y = centerPoint.y - radio / 2;

        k1 = (centerPoint.y - startPoint.y) / (centerPoint.x - startPoint.x);
        b1 = startPoint.y - k1 * startPoint.x;

        k2 = (centerPoint.y - endPoint.y) / (centerPoint.x - endPoint.x);
        b2 = endPoint.y - k2 * endPoint.x;

        mRectf = new RectF(margin, margin, getMeasuredWidth() - margin, getMeasuredWidth() - margin);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isDrawCircle) {
            circlePath.arcTo(mRectf, 0, currentAngle);
        } else {
            rightLinePath.lineTo(currentStart, getY1(currentStart));
            leftLinePath.lineTo(currentCenter, getY2(currentCenter));
        }
        canvas.drawPath(circlePath, mPaint);
        canvas.drawPath(rightLinePath, mPaint);
        canvas.drawPath(leftLinePath, mPaint);
        if (isDrawCircle) {
            currentAngle += CIRCLE_SPEED;
            circlePath.reset();
            if (currentAngle > 360) {
                currentAngle = 360;
                circlePath.arcTo(mRectf, 3, currentAngle);
                canvas.drawPath(circlePath, mPaint);
                isDrawCircle = false;
            }
        } else {
            if (currentStart >= centerPoint.x) {
                currentStart -= GOGO_SPEED;
            } else {
                if (currentCenter >= endPoint.x) {
                    currentCenter -= GOGO_SPEED;
                }
            }
        }
        invalidate();
    }

    private float getY1(float x) {
        return x * k1 + b1;
    }

    private float getY2(float x) {
        return x * k2 + b2;
    }
}
