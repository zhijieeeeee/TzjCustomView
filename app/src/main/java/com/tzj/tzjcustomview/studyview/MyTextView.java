package com.tzj.tzjcustomview.studyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * <p> FileName： MyTextView</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class MyTextView extends TextView {

    private int viewWidth;
    private LinearGradient linearGradient;
    private Paint mPaint;
    private Matrix matrix;
    private int mTranslate;

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (viewWidth == 0) {
            viewWidth = getMeasuredWidth();
            if (viewWidth > 0) {
                mPaint = getPaint();
                //设置颜色渐变
                linearGradient = new LinearGradient(
                        0, 0, viewWidth, 0, new int[]{Color.BLACK, 0xffffff, Color.BLACK},
                        null, Shader.TileMode.CLAMP);
                mPaint.setShader(linearGradient);
                matrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix != null) {
            mTranslate += viewWidth / 5;
            if (mTranslate > 2 * viewWidth) {
                mTranslate = -viewWidth;
            }
            //矩阵平移
            matrix.setTranslate(mTranslate, 0);
            linearGradient.setLocalMatrix(matrix);
            //没个100ms重绘
            postInvalidateDelayed(100);
        }
    }
}
