package com.tzj.tzjcustomview.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p> ProjectName： MyLearn</p>
 * <p>
 * Description：改变形状的控件
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-01-26 16:32
 */
public class ShapeView extends View {

    private Paint paint;

    /**
     * 矩形与View的边界
     */
    private final int RECTANGLE_OFFSET = 4;

    /**
     * 当前形状
     */
    private Shape shape = Shape.Circle;

    /**
     * 圆的颜色
     */
    private int circleColor;

    /**
     * 矩形颜色
     */
    private int rectangleColor;

    /**
     * 三角形颜色
     */
    private int triangleColor;

    public enum Shape {
        Circle, Rectangle, Triangle
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        circleColor = Color.RED;
        rectangleColor = Color.GREEN;
        triangleColor = Color.BLUE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (shape) {
            case Circle://圆
                paint.setColor(circleColor);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
                shape = Shape.Rectangle;
                break;
            case Rectangle://矩形
                paint.setColor(rectangleColor);
                Path path1 = new Path();
                path1.moveTo(RECTANGLE_OFFSET, RECTANGLE_OFFSET);
                path1.lineTo(getWidth() - RECTANGLE_OFFSET, RECTANGLE_OFFSET);
                path1.lineTo(getWidth() - RECTANGLE_OFFSET, getHeight() - RECTANGLE_OFFSET);
                path1.lineTo(RECTANGLE_OFFSET, getHeight() - RECTANGLE_OFFSET);
                path1.lineTo(RECTANGLE_OFFSET, RECTANGLE_OFFSET);
                path1.close();
                canvas.drawPath(path1, paint);
                shape = Shape.Triangle;
                break;
            case Triangle://三角形
                paint.setColor(triangleColor);
                Path path = new Path();
                float gen3 = (float) Math.sqrt(3);
                int borderLength = getWidth() / 2;
                int offset = getHeight() / 2 - borderLength;
                path.moveTo(getWidth() / 2, getHeight() / 2);//重心
                path.lineTo(getWidth() / 2, offset);//上
                path.lineTo((getWidth() / 2 - (gen3 / 2 * borderLength)), getHeight() / 2 + borderLength / 2);
                path.lineTo((getWidth() / 2 + (gen3 / 2 * borderLength)), getHeight() / 2 + borderLength / 2);
                path.lineTo(getWidth() / 2, offset);
                path.close();
                canvas.drawPath(path, paint);
                shape = Shape.Circle;
                break;
        }
    }

    /**
     * 改变形状
     */
    public void changeShape() {
        invalidate();
    }

    /**
     * 获得当前形状
     *
     * @return
     */
    public Shape getShape() {
        return shape;
    }
}
