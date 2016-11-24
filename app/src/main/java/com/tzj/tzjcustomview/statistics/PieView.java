package com.tzj.tzjcustomview.statistics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
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
     * 中心坐标
     */
    private int centerX;
    private int centerY;

    /**
     * 弧形外接矩形
     */
    private RectF rectF;

    /**
     * 中间文本的大小
     */
    private Rect textBound = new Rect();

    /**
     * 扇形画笔
     */
    private Paint mArcPaint;

    /**
     * 文本画笔
     */
    private Paint textPaint;

    /**
     * 数据源数组
     */
    private int[] numbers;

    /**
     * 数据源总和
     */
    private int sum;

    /**
     * 颜色数组
     */
    private int[] colors;

    private Random random = new Random();

    /**
     * 中间字体大小
     */
    private float textSize = 20;

    /**
     * 中间字体颜色
     */
    private int textColor = Color.BLACK;

    /**
     * 圆圈的宽度
     */
    private float circleWidth = 50;

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
        textSize = typedArray.getDimension(R.styleable.PieView_textSize, textSize);
        circleWidth = typedArray.getDimension(R.styleable.PieView_circleWidth, circleWidth);
        textColor = typedArray.getColor(R.styleable.PieView_textColor, textColor);
        typedArray.recycle();
        init();
    }

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setStrokeWidth(circleWidth);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
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
        float radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2 * 0.5f;
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
     * 计算比例并且绘制扇形和数字
     */
    private void calculateAndDraw(Canvas canvas) {
        if (numbers == null || numbers.length == 0) {
            return;
        }
        //开始度数
        int startAngle = 0;
        for (int i = 0; i < numbers.length; i++) {
            //所占百分比
            float percent = numbers[i] / (float) sum;
            //所占度数
            float angle = (float) Math.ceil(percent * 360);
            //绘制第i段扇形
            drawArc(canvas, startAngle, angle, colors[i]);
            Log.i("MyLog", "第" + i + "项：" + "startAngle=" + startAngle + ",angle=" + angle);
            startAngle += angle;
        }
        //绘制中心数字总和
        canvas.drawText(sum + "", centerX - textBound.width() / 2, centerY + textBound.height() / 2, textPaint);
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
     */
    public void setData(int[] numbers) {
        this.numbers = numbers;
        colors = new int[numbers.length];
        for (int i = 0; i < this.numbers.length; i++) {
            sum += numbers[i];
            colors[i] = randomColor();
        }
        textPaint.getTextBounds(sum + "", 0, (sum + "").length(), textBound);
        invalidate();
    }
}
