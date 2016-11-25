package com.tzj.tzjcustomview.statistics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.tzj.tzjcustomview.R;

import java.util.Random;

/**
 * <p>
 * Description：饼状统计图View
 * </p>
 *
 * @author tangzhijie
 */
public class PieView extends View {

    /**
     * 使用wrap_content时默认的尺寸
     */
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    /**
     * 折线长度
     */
    private static final int SlASH_LINE_OFFSET = 60;

    /**
     * 横线长度
     */
    private static final int HOR_LINE_LENGTH = 180;

    /**
     * 横线上文字的横向偏移量
     */
    private static final int X_OFFSET = 20;

    /**
     * 横线上文字的纵向偏移量
     */
    private static final int Y_OFFSET = 10;

    /**
     * 中心坐标
     */
    private int centerX;
    private int centerY;

    /**
     * 半径
     */
    private float radius;

    /**
     * 弧形外接矩形
     */
    private RectF rectF;

    /**
     * 中间文本的大小
     */
    private Rect centerTextBound = new Rect();

    /**
     * 数据文本的大小
     */
    private Rect dataTextBound = new Rect();

    /**
     * 扇形画笔
     */
    private Paint mArcPaint;

    /**
     * 文本画笔
     */
    private Paint textPaint;

    private Paint linePaint;

    /**
     * 数据源数字数组
     */
    private int[] numbers;

    /**
     * 数据源名称数组
     */
    private String[] names;

    /**
     * 数据源总和
     */
    private int sum;

    /**
     * 颜色数组
     */
    private int[] colors;

    private Random random = new Random();

    //自定义属性 Start

    /**
     * 中间字体大小
     */
    private float centerTextSize = 20;

    /**
     * 数据字体大小
     */
    private float dataTextSize = 10;

    /**
     * 中间字体颜色
     */
    private int centerTextColor = Color.BLACK;

    /**
     * 数据字体颜色
     */
    private int dataTextColor = Color.BLACK;

    /**
     * 圆圈的宽度
     */
    private float circleWidth = 50;

    //自定义属性 End

    public PieView(Context context) {
        super(context);
        init();
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PieView);
        centerTextSize = typedArray.getDimension(R.styleable.PieView_centerTextSize, centerTextSize);
        dataTextSize = typedArray.getDimension(R.styleable.PieView_dataTextSize, dataTextSize);
        circleWidth = typedArray.getDimension(R.styleable.PieView_circleWidth, circleWidth);
        centerTextColor = typedArray.getColor(R.styleable.PieView_centerTextColor, centerTextColor);
        dataTextColor = typedArray.getColor(R.styleable.PieView_dataTextColor, dataTextColor);
        typedArray.recycle();
        init();
    }

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setStrokeWidth(circleWidth);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setTextSize(centerTextSize);
        textPaint.setAntiAlias(true);
        textPaint.setColor(centerTextColor);

        linePaint = new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setTextSize(dataTextSize);
        linePaint.setAntiAlias(true);
        linePaint.setColor(dataTextColor);
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
            setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        } else if (measureWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_WIDTH, measureHeightSize);
        } else if (measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measureWidthSize, DEFAULT_HEIGHT);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        //设置半径为宽高最小值的1/4
        radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2 * 0.5f;
        //设置扇形外接矩形
        rectF = new RectF(centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateAndDraw(canvas);
    }

    /**
     * 计算比例并且绘制扇形和数据
     */
    private void calculateAndDraw(Canvas canvas) {
        if (numbers == null || numbers.length == 0) {
            return;
        }
        //扇形开始度数
        int startAngle = 0;
        for (int i = 0; i < numbers.length; i++) {
            //所占百分比
            float percent = numbers[i] / (float) sum;
            //所占度数
            float angle = (float) Math.ceil(percent * 360);
            //绘制第i段扇形
            drawArc(canvas, startAngle, angle, colors[i]);
            startAngle += angle;

            //绘制数据
            if (numbers[i] <= 0) {
                continue;
            }
            //当前扇形的中心度数
            float arcCenterDegree = 90 + startAngle - angle / 2;
            drawLineAndData(canvas, arcCenterDegree, i);
        }
        //绘制中心数字总和
        canvas.drawText(sum + "", centerX - centerTextBound.width() / 2, centerY + centerTextBound.height() / 2, textPaint);
    }

    /**
     * 计算和绘制数据折线
     *
     * @param canvas 画布
     * @param degree 当前扇形中心度数
     * @param i      当前下标
     */
    private void drawLineAndData(Canvas canvas, float degree, int i) {
        //扇形弧线中心点距离圆心的坐标
        float x;
        float y;
        //折线开始坐标(扇形弧线中心点相对于view的坐标)
        float startX;
        float startY;
        //折线结束坐标
        float endX;
        float endY;
        //横线结束坐标
        float horEndX;
        float horEndY;
        //数字开始坐标
        float numberStartX;
        float numberStartY;
        //文本开始坐标
        float textStartX;
        float textStartY;

        //sin 一二正，三四负
        x = (float) (Math.sin(Math.toRadians(degree)) * radius);
        //cos 一四正，二三负
        y = (float) (Math.cos(Math.toRadians(degree)) * radius);

        startX = centerX + x;
        startY = centerY - y;

        if (degree > 90 && degree < 180) {//2象限
            endX = startX + SlASH_LINE_OFFSET;
            endY = startY + SlASH_LINE_OFFSET;
            //绘制折线
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            //绘制横线
            canvas.drawLine(endX, endY, endX + HOR_LINE_LENGTH, endY, linePaint);
            //绘制数字
            canvas.drawText(numbers[i] + "", endX + X_OFFSET, endY - Y_OFFSET, linePaint);
            //绘制名称
            canvas.drawText(names[i] + "", endX + X_OFFSET, endY + dataTextBound.height() / 2, linePaint);
        } else if (degree == 180) {
            startX = centerX;
            startY = centerY + radius;
            endX = startX + SlASH_LINE_OFFSET;
            endY = startY + SlASH_LINE_OFFSET;
            //绘制折线
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            //绘制横线
            canvas.drawLine(endX, endY, endX + HOR_LINE_LENGTH, endY, linePaint);
            //绘制数字
            canvas.drawText(numbers[i] + "", endX + X_OFFSET, endY - Y_OFFSET, linePaint);
            //绘制名称
            canvas.drawText(names[i] + "", endX + X_OFFSET, endY + dataTextBound.height() / 2, linePaint);
        } else if (degree > 180 && degree < 270) {//3象限
            endX = startX - SlASH_LINE_OFFSET;
            endY = startY + SlASH_LINE_OFFSET;
            //绘制折线
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            //绘制横线
            canvas.drawLine(endX, endY, endX - HOR_LINE_LENGTH, endY, linePaint);
            //绘制数字
            canvas.drawText(numbers[i] + "", endX - HOR_LINE_LENGTH + X_OFFSET, endY - Y_OFFSET, linePaint);
            //绘制名称
            canvas.drawText(names[i] + "", endX - HOR_LINE_LENGTH + X_OFFSET, endY + dataTextBound.height() / 2, linePaint);
        } else if (degree == 270) {
            startX = centerX - radius;
            startY = centerY;
            endX = startX - SlASH_LINE_OFFSET;
            endY = startY - SlASH_LINE_OFFSET;
            //绘制折线
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            //绘制横线
            canvas.drawLine(endX, endY, endX - HOR_LINE_LENGTH, endY, linePaint);
            //绘制数字
            canvas.drawText(numbers[i] + "", endX - HOR_LINE_LENGTH + X_OFFSET, endY - Y_OFFSET, linePaint);
            //绘制名称
            canvas.drawText(names[i] + "", endX - HOR_LINE_LENGTH + X_OFFSET, endY + dataTextBound.height() / 2, linePaint);
        } else if (degree > 270 && degree < 360) {//4象限
            endX = startX - SlASH_LINE_OFFSET;
            endY = startY - SlASH_LINE_OFFSET;
            //绘制折线
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            //绘制横线
            canvas.drawLine(endX, endY, endX - HOR_LINE_LENGTH, endY, linePaint);
            //绘制数字
            canvas.drawText(numbers[i] + "", endX - HOR_LINE_LENGTH + X_OFFSET, endY - Y_OFFSET, linePaint);
            //绘制名称
            canvas.drawText(names[i] + "", endX - HOR_LINE_LENGTH + X_OFFSET, endY + dataTextBound.height() / 2, linePaint);
        } else if (degree == 360) {
            startX = centerX;
            startY = centerY - radius;
            endX = startX - SlASH_LINE_OFFSET;
            endY = startY - SlASH_LINE_OFFSET;
            //绘制折线
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            //绘制横线
            canvas.drawLine(endX, endY, endX - HOR_LINE_LENGTH, endY, linePaint);
            //绘制数字
            canvas.drawText(numbers[i] + "", endX - HOR_LINE_LENGTH + X_OFFSET, endY - Y_OFFSET, linePaint);
            //绘制名称
            canvas.drawText(names[i] + "", endX - HOR_LINE_LENGTH + X_OFFSET, endY + dataTextBound.height() / 2, linePaint);
        } else if (degree > 360) {//1象限
            endX = startX + SlASH_LINE_OFFSET;
            endY = startY - SlASH_LINE_OFFSET;
            //绘制折线
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            //绘制横线
            canvas.drawLine(endX, endY, endX + HOR_LINE_LENGTH, endY, linePaint);
            //绘制数字
            canvas.drawText(numbers[i] + "", endX + X_OFFSET, endY - Y_OFFSET, linePaint);
            //绘制名称
            canvas.drawText(names[i] + "", endX + X_OFFSET, endY + dataTextBound.height() / 2, linePaint);
        }
    }

    /**
     * 绘制扇形
     *
     * @param canvas     画布
     * @param startAngle 开始度数
     * @param angle      扇形的度数
     * @param color      颜色
     */
    private void drawArc(Canvas canvas, float startAngle, float angle, int color) {
        mArcPaint.setColor(color);
        //-0.5和+0.5是为了让每个扇形之间没有间隙
        canvas.drawArc(rectF, startAngle - 0.5f, angle + 0.5f, false, mArcPaint);
    }

    /**
     * 生成随机颜色
     */
    private int randomColor() {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return Color.rgb(red, green, blue);
    }

    /**
     * 设置数据
     *
     * @param numbers 数字数组
     * @param names   名称数组
     */
    public void setData(@NonNull int[] numbers, @NonNull String[] names) {
        if (numbers == null || numbers.length == 0 || names == null || names.length == 0) {
            return;
        }
        if (numbers.length != names.length) {
            //名称个数与数字个数不相等
            return;
        }

        this.numbers = numbers;
        this.names = names;
        colors = new int[numbers.length];
        for (int i = 0; i < this.numbers.length; i++) {
            sum += numbers[i];
            colors[i] = randomColor();
        }
        textPaint.getTextBounds(sum + "", 0, (sum + "").length(), centerTextBound);
        textPaint.getTextBounds(names[0] + "", 0, (names[0] + "").length(), dataTextBound);
        invalidate();
    }
}
