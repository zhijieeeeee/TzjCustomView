package com.tzj.tzjcustomview.guaguaview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tzj.tzjcustomview.R;

/**
 * <p> ProjectName： TzjCustomView</p>
 * <p>
 * Description：自定义刮刮卡
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-02-15 13:22
 */
public class GuaGuaView extends View {

    /**
     * 内存中的canvas
     */
    private Canvas mCanvas;

    /**
     * 内存中canvas上面的bitmap
     */
    private Bitmap mBitmap;

    /**
     * 绘制路径的画笔
     */
    private Paint mPaint;

    /**
     * 手指滑的路径
     */
    private Path mPath;

    /**
     * 画笔的宽度
     */
    private static final int PAINT_WIDTH = 40;

    /**
     * 背景图
     */
    private Bitmap mBgBitmap;

    /**
     * 刮奖层上的文字
     */
    private String tipText = "刮一刮";
    /**
     * 刮奖层上的文字画笔
     */
    private Paint textPaint;
    /**
     * 用于计算刮奖层上的文字的大小
     */
    private Rect mTextBound = new Rect();

    public GuaGuaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuaGuaView(Context context) {
        super(context);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPath = new Path();
        //初始化手指滑动画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 圆角
        mPaint.setStrokeWidth(PAINT_WIDTH);// 设置画笔宽度
        //初始化刮奖层上的文字画笔
        textPaint = new Paint();
        textPaint.setStrokeWidth(2);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(100);
        textPaint.setAntiAlias(true);
        //计算文字的大小，用于设置文字的绘制位置
        textPaint.getTextBounds(tipText, 0, tipText.length(), mTextBound);
        //初始化背景图
        mBgBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.bg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //初始化内存画布
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        //绘制刮奖层
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        //绘制文字
        mCanvas.drawText(tipText, width / 2 - mTextBound.width() / 2, height / 2 - mTextBound.height() / 2, textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景图
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        //设置模式，交集部分会消失
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        //在内存中的bitmap上画path，path与刮奖层的交集会消失
        mCanvas.drawPath(mPath, mPaint);
        //把bitmap显示出来，覆盖在背景图上
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 上一次手指在屏幕的位置
     */
    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int disX = Math.abs((int) event.getX() - mLastX);
                int disY = Math.abs((int) event.getY() - mLastY);
                if (disX > 3 || disY > 3) {
                    mPath.lineTo((int) event.getX(), (int) event.getY());
                }
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 重置
     */
    public void clear() {
        mPath.reset();
        mCanvas.drawPaint(mPaint);
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        //绘制文字
        mCanvas.drawText("刮一刮", getMeasuredWidth() / 2 - 150, getMeasuredHeight() / 2, textPaint);
        invalidate();
    }

    /**
     * 回收bitmap
     */
    public void recycleBitmap() {
        mBitmap.recycle();
        mBgBitmap.recycle();
    }
}
