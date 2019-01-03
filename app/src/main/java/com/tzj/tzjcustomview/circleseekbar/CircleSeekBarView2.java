package com.tzj.tzjcustomview.circleseekbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tzj.tzjcustomview.R;

/**
 * Description：I won't write any description!
 * Author ：Spider-Man.
 * Date：2019/1/2
 */
public class CircleSeekBarView2 extends View {

    private int centerX;
    private int centerY;
    private float radius;
    private RectF rectF;
    private Paint mArcPaint;
    private float circleWidth = 4;
    //最小角度
    public static int MIN_DEGREE = 180;
    //最大角度
    public static int MAX_DEGREE = 210;
    //默认角度
    public static int DEFAULT_THUMB_DEGREE = 180;
    private int thumbDegree = DEFAULT_THUMB_DEGREE;
    //滑块旋转角度
    private int thumbRotateDegree = thumbDegree - MIN_DEGREE;
    private Bitmap thumbBitmap;
    private int thumbHeight, thumbWidth;
    private Paint thumbPaint;

    private OnSeekListener onSeekListener;

    public void setOnSeekListener(OnSeekListener onSeekListener) {
        this.onSeekListener = onSeekListener;
    }

    public CircleSeekBarView2(Context context) {
        super(context);
        init();
    }

    public CircleSeekBarView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleSeekBarView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setThumbDegree(int thumbDegree) {
        this.thumbDegree = thumbDegree;
        thumbRotateDegree = thumbDegree - MIN_DEGREE;
    }

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setStrokeWidth(circleWidth);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setPathEffect(new DashPathEffect(new float[]{8, 8}, 0));
        mArcPaint.setColor(Color.WHITE);

        thumbPaint = new Paint();
        thumbPaint.setAntiAlias(true);
        thumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arr);
        thumbHeight = thumbBitmap.getHeight();
        thumbWidth = thumbBitmap.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (measureWidthMode == MeasureSpec.AT_MOST
                && measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);
        } else if (measureWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, measureHeightSize);
        } else if (measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measureWidthSize, 200);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        //设置半径为宽高最小值的1/4
        radius = getMeasuredHeight() / 2 - 70;
        //设置扇形外接矩形
        rectF = new RectF(centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF, MIN_DEGREE, MAX_DEGREE - MIN_DEGREE, false, mArcPaint);
        //计算滑块x,y坐标
        float thumbPosition[] = calculatePosition(thumbDegree);
        canvas.drawBitmap(rotateBitmap(thumbBitmap, thumbRotateDegree), thumbPosition[0] - thumbWidth / 2, thumbPosition[1] - thumbHeight / 2, thumbPaint);
    }


    /**
     * 计算弧度对应坐标
     *
     * @param degree 弧度
     */
    private float[] calculatePosition(float degree) {
        float realDegree = degree + 90;
        //由于Math.sin(double a)中参数a不是度数而是弧度，所以需要将度数转化为弧度
        //而Math.toRadians(degree)的作用就是将度数转化为弧度
        //sin 一二正，三四负 sin（180-a）=sin(a)
        //扇形弧线中心点距离圆心的x坐标
        float x = (float) (Math.sin(Math.toRadians(realDegree)) * radius);
        //cos 一四正，二三负
        //扇形弧线中心点距离圆心的y坐标
        float y = (float) (Math.cos(Math.toRadians(realDegree)) * radius);

        float startX = centerX + x;
        float startY = centerY - y;

        float[] position = new float[2];
        position[0] = startX;
        position[1] = startY;
        return position;
    }

    private boolean isPointOnThumb(float eventX, float eventY) {
        boolean result = false;
        double distance = Math.sqrt(Math.pow(eventX - centerX, 2)
                + Math.pow(eventY - centerY, 2));
        if (distance < getMeasuredWidth() && distance > (getMeasuredWidth() / 2 - thumbWidth)) {
            result = true;
        }
        return result;
    }

    private void seekTo(float eventX, float eventY, boolean isUp) {
        if (isPointOnThumb(eventX, eventY) && !isUp) {
            //计算点击坐标对应的弧度
            double radian = Math.atan2(eventY - centerY, eventX - centerX);
            if (radian < 0) {
                radian = radian + 2 * Math.PI;
            }
            int targetDegree = (int) Math.round(Math.toDegrees(radian));
            if (targetDegree >= MIN_DEGREE && targetDegree <= MAX_DEGREE) {//滑动范围内
                thumbRotateDegree += (targetDegree - thumbDegree);
                thumbDegree = targetDegree;
                Log.i("MyLog", "thumbDegree=" + thumbDegree);
                Log.i("MyLog", "thumbRotateDegree=" + thumbRotateDegree);
                invalidate();
                if(onSeekListener!=null){
                    onSeekListener.onSeek(thumbRotateDegree);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                seekTo(eventX, eventY, false);
                break;
            case MotionEvent.ACTION_MOVE:
                seekTo(eventX, eventY, false);
                break;
            case MotionEvent.ACTION_UP:
                seekTo(eventX, eventY, true);
                break;
        }
        return true;
    }

    public  interface  OnSeekListener{
        void onSeek(int thumbRotateDegree);
    }

}
