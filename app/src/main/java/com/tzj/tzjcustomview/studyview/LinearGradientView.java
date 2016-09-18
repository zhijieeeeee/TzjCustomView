package com.tzj.tzjcustomview.studyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class LinearGradientView extends View {

    private LinearGradient linearGradient;
    private int viewWidth;
    private int viewHeight;
    private Paint paint;
    private Matrix matrix;

    private int tranX;

    public LinearGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LinearGradientView(Context context) {
        this(context, null);
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        //设置颜色渐变
        linearGradient = new LinearGradient(
                0, 0, viewWidth, 0, new int[]{Color.BLACK, 0xffffff, Color.BLACK},
                null, Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setShader(linearGradient);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, viewWidth, viewHeight, paint);

        tranX += viewWidth / 5;
        if (tranX > viewWidth * 2) {
            tranX = -viewWidth;
        }
        //矩阵平移
        matrix.setTranslate(tranX, 0);
        linearGradient.setLocalMatrix(matrix);
        //每隔100ms重绘
        postInvalidateDelayed(100);
    }
}
