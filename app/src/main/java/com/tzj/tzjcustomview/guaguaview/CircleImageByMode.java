package com.tzj.tzjcustomview.guaguaview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
public class CircleImageByMode extends View {

    //图片
    private Bitmap srcBitmap;

    //图片下方的圆角
    private Bitmap roundBitmap;

    private Canvas mCanvas;

    private Paint paint;

    public CircleImageByMode(Context context, AttributeSet attrs) {
        super(context, attrs);

        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic4);
        roundBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(roundBitmap);

        paint=new Paint();
        paint.setAntiAlias(true);

        mCanvas.drawCircle(200,200,100,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawBitmap(srcBitmap,0,0,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(roundBitmap,0,0,null);
    }
}
