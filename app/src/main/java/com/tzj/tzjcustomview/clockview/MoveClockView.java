package com.tzj.tzjcustomview.clockview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private Canvas hourCanvas;
    private Canvas minuteCanvas;
    private Canvas secondCanvas;

    private Bitmap hourBitmap;
    private Bitmap minuteBitmap;
    private Bitmap secondBitmap;

    private Paint hourPaint;
    private Paint minutePaint;
    private Paint secondPaint;

    private int hourLineLength;
    private int minuteLineLength;
    private int secondLineLength;

    private Calendar calendar;

    private ScheduledExecutorService scheduledThreadPool
            = Executors.newScheduledThreadPool(1);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            calendar = Calendar.getInstance();
            int hour24 = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            //时针
            hourBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            hourCanvas = new Canvas(hourBitmap);
            hourCanvas.rotate(hour24 * 30, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
            hourCanvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                    getMeasuredWidth() / 2, getMeasuredHeight() / 2 - hourLineLength, hourPaint);

            //分针
            minuteBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            minuteCanvas = new Canvas(minuteBitmap);
            minuteCanvas.rotate(minute * 6, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
            minuteCanvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                    getMeasuredWidth() / 2, getMeasuredHeight() / 2 - minuteLineLength, minutePaint);

            //秒针
            secondBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            secondCanvas = new Canvas(secondBitmap);
            secondCanvas.rotate(second * 6, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
            secondCanvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                    getMeasuredWidth() / 2, getMeasuredHeight() / 2 - secondLineLength, secondPaint);
            invalidate();
        }
    };

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

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, 1, TimeUnit.SECONDS);

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
        canvas.drawBitmap(hourBitmap, 0, 0, null);
        canvas.drawBitmap(minuteBitmap, 0, 0, null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
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

}
