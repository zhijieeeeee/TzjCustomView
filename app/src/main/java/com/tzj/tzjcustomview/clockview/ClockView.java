package com.tzj.tzjcustomview.clockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>
 * Description：自定义时钟，绘制
 * </p>
 *
 * @author tangzhijie
 */
public class ClockView extends View {

    //圆画笔
    private Paint circlePaint;
    //刻度线与刻度值画笔
    private Paint degreePaint;
    //小时指针画笔
    private Paint needleHourPaint;
    //分钟指针画笔
    private Paint needleMinutePaint;

    //刻度指针长度
    private final static int quarterLineLength = 60;
    //普通指针长度
    private final static int normalLineLength = 30;

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.BLACK);

        degreePaint = new Paint();
        degreePaint.setAntiAlias(true);

        needleHourPaint = new Paint();
        needleHourPaint.setAntiAlias(true);
        needleHourPaint.setStrokeWidth(8);

        needleMinutePaint = new Paint();
        needleMinutePaint.setAntiAlias(true);
        needleMinutePaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制圆
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, circlePaint);
        //绘制刻度值与刻度线
        for (int i = 0; i < 24; i++) {
            String degree = String.valueOf(i);
            if (i == 0 || i == 6 || i == 12 || i == 18) {//整点刻度线
                degreePaint.setColor(Color.BLACK);
                degreePaint.setStrokeWidth(5);
                degreePaint.setTextSize(50);
                canvas.drawLine(
                        getMeasuredWidth() / 2,
                        getHeight() / 2 - getMeasuredWidth() / 2,
                        getMeasuredWidth() / 2,
                        getHeight() / 2 - getMeasuredWidth() / 2 + quarterLineLength,
                        degreePaint);

                canvas.drawText(
                        degree,
                        getMeasuredWidth() / 2 - degreePaint.measureText(degree) / 2,
                        getHeight() / 2 - getMeasuredWidth() / 2 + quarterLineLength + 50,
                        degreePaint);
            } else {
                degreePaint.setColor(Color.GRAY);
                degreePaint.setStrokeWidth(3);
                degreePaint.setTextSize(40);
                canvas.drawLine(
                        getMeasuredWidth() / 2,
                        getHeight() / 2 - getMeasuredWidth() / 2,
                        getMeasuredWidth() / 2,
                        getHeight() / 2 - getMeasuredWidth() / 2 + normalLineLength,
                        degreePaint);

                canvas.drawText(
                        degree,
                        getMeasuredWidth() / 2 - degreePaint.measureText(degree) / 2,
                        getHeight() / 2 - getMeasuredWidth() / 2 + normalLineLength + 50,
                        degreePaint);
            }
            //每次画布旋转15度
            canvas.rotate(15, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        }
        //将之前所有绘制的保存起来，让后续的绘制像在一个新的图层上绘制
        canvas.save();

        //将画布平移到中心点
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        //绘制小时与分钟指针
        canvas.drawLine(0, 0, 100, 100, needleHourPaint);
        canvas.drawLine(0, 0, 100, 200, needleMinutePaint);

        //（注意：画布会重置，回到上一个save时状态）
        //用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
        //把自调用最后一个save函数之后应用的所有效果从画布上移除。
        canvas.restore();
    }
}
