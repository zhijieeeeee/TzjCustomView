package com.tzj.tzjcustomview.clockview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class MoveClockView extends View {

    /**
     * 使用wrap_content时默认的尺寸
     */
    private final static int DEFAULT_SIZE = 400;

    /**
     * 圆心
     */
    private int centerX;
    private int centerY;

    /**
     * 圆半径
     */
    private int radius;

    /**
     * 圆的画笔
     */
    private Paint circlePaint;

    /**
     * 刻度线画笔
     */
    private Paint markPaint;

    /**
     * 刻度线宽度
     */
    private final static int MARK_WIDTH = 8;

    /**
     * 刻度线长度
     */
    private final static int MARK_LENGTH = 20;

    /**
     * 刻度线与圆的距离
     */
    private final static int MARK_GAP = 12;

    /**
     * 时针画笔
     */
    private Paint hourPaint;

    /**
     * 分针画笔
     */
    private Paint minutePaint;

    /**
     * 秒针画笔
     */
    private Paint secondPaint;

    /**
     * 时针长度
     */
    private int hourLineLength;

    /**
     * 分针长度
     */
    private int minuteLineLength;

    /**
     * 秒针长度
     */
    private int secondLineLength;

    public MoveClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MoveClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveClockView(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        reMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        centerX = width / 2 + getPaddingLeft();
        centerY = height / 2 + getPaddingTop();
        radius = Math.min(width, height) / 2;

        hourLineLength = radius / 2;
        minuteLineLength = radius * 3 / 4;
        secondLineLength = radius * 3 / 4;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
        //绘制刻度线
        for (int i = 0; i < 12; i++) {
            if (i % 3 == 0) {//一刻钟
                markPaint.setColor(Color.parseColor("#B5B5B5"));
            } else {
                markPaint.setColor(Color.parseColor("#EBEBEB"));
            }
            canvas.drawLine(
                    centerX,
                    centerY - radius + MARK_GAP,
                    centerX,
                    centerY - radius + MARK_GAP + MARK_LENGTH,
                    markPaint);
            canvas.rotate(30, centerX, centerY);
        }
        canvas.save();

        Calendar calendar = Calendar.getInstance();
        int hour24 = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        Log.i("MyLog", "hour24=" + hour24);
        Log.i("MyLog", "minute=" + minute);
        Log.i("MyLog", "second=" + second);

        //时针
        Bitmap hourBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas hourCanvas = new Canvas(hourBitmap);

        //分针
        Bitmap minuteBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas minuteCanvas = new Canvas(minuteBitmap);

        //秒针
        Bitmap secondBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas secondCanvas = new Canvas(secondBitmap);

        //(方案一)每过一小时(3600秒)时针添加30度，所以每秒时针添加（1/120）度
        //(方案二)每过一小时(60分钟)时针添加30度，所以每分钟时针添加（1/2）度
        hourCanvas.rotate(hour24 * 30 + (minute * 0.5f), getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        hourCanvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                getMeasuredWidth() / 2, getMeasuredHeight() / 2 - hourLineLength, hourPaint);

        //每过一分钟（60秒）分针添加6度，所以每秒分针添加（1/10）度；当minute加1时，正好second是0
        minuteCanvas.rotate(minute * 6 + (second * 0.1f), getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        minuteCanvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                getMeasuredWidth() / 2, getMeasuredHeight() / 2 - minuteLineLength, minutePaint);

        //每过一秒旋转6度
        secondCanvas.rotate(second * 6, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        secondCanvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                getMeasuredWidth() / 2, getMeasuredHeight() / 2 - secondLineLength, secondPaint);


        canvas.drawBitmap(hourBitmap, 0, 0, null);
        canvas.drawBitmap(minuteBitmap, 0, 0, null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);

        //每隔1s重新绘制
        postInvalidateDelayed(1000);
    }

    /**
     * 初始化
     */
    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.WHITE);

        markPaint = new Paint();
        circlePaint.setAntiAlias(true);
        markPaint.setStyle(Paint.Style.FILL);
        markPaint.setStrokeCap(Paint.Cap.ROUND);
        markPaint.setStrokeWidth(MARK_WIDTH);

        hourPaint = new Paint();
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(Color.BLACK);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPaint.setStrokeWidth(10);

        minutePaint = new Paint();
        minutePaint.setAntiAlias(true);
        minutePaint.setColor(Color.BLACK);
        minutePaint.setStyle(Paint.Style.FILL);
        minutePaint.setStrokeCap(Paint.Cap.ROUND);
        minutePaint.setStrokeWidth(6);

        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setColor(Color.RED);
        secondPaint.setStyle(Paint.Style.FILL);
        secondPaint.setStrokeCap(Paint.Cap.ROUND);
        secondPaint.setStrokeWidth(4);

    }

    /**
     * 判断是否有wrap_content
     */
    private void reMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (measureWidthMode == MeasureSpec.AT_MOST
                && measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_SIZE, DEFAULT_SIZE);
        } else if (measureWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_SIZE, measureHeight);
        } else if (measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measureWidth, DEFAULT_SIZE);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        scheduledThreadPool.shutdown();
    }
}
