package com.tzj.tzjcustomview.guaguaview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.tzj.tzjcustomview.R;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class CircleImageByShader extends View {

    //图片
    private Bitmap srcBitmap;

    private Paint paint;

    public CircleImageByShader(Context context, AttributeSet attrs) {
        super(context, attrs);

        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic4);

        BitmapShader bitmapShader = new BitmapShader(srcBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        paint = new Paint();
        paint.setShader(bitmapShader);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆角矩形
        canvas.drawCircle(400, 400, 200, paint);
    }
}
