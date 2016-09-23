package com.tzj.tzjcustomview.guaguaview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.tzj.tzjcustomview.R;

/**
 * <p>
 * Description：通过BitmapShader绘制圆角图片
 * </p>
 *
 * @author tangzhijie
 */
public class CornerImageByShader extends View {

    //图片
    private Bitmap srcBitmap;

    private Paint paint;

    //用于缩放图片的
    private Matrix matrix;

    //圆角半径
    private int radius = 50;
    //圆角外接矩形
    private RectF rectF;

    public CornerImageByShader(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        matrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    private void setShader() {
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic5);

        BitmapShader bitmapShader = new BitmapShader(srcBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //计算缩放比例
        float scale = Math.max(
                getMeasuredWidth() * 1.0f / srcBitmap.getWidth(),
                getMeasuredHeight() * 1.0f / srcBitmap.getHeight());

        matrix.setScale(scale, scale);

        bitmapShader.setLocalMatrix(matrix);

        paint.setShader(bitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setShader();
        //绘制圆角矩形
        canvas.drawRoundRect(rectF, radius, radius, paint);
    }
}
