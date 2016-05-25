package com.tzj.tzjcustomview.studyview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p> FileName： MeasureTestView</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class MeasureTestView extends View {

    public MeasureTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    /**
     * 测量宽度，如果使用了wrap_content，就使用默认的
     *
     * @param widthMeasureSpec
     */
    private int measureWidth(int widthMeasureSpec) {
        int result;
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureSize = MeasureSpec.getSize(widthMeasureSpec);
        if (measureMode == MeasureSpec.EXACTLY) {//使用的是固定值或者match_parent
            result = measureSize;
        } else {
            result = 200;
            if (measureMode == MeasureSpec.AT_MOST) {//使用wrap_content
                result = Math.min(result, measureSize);
            }
        }
        return result;
    }

    /**
     * 测量高度，如果使用了wrap_content，就使用默认的
     *
     * @param heightMeasureSpec
     */
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int measureMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureSize = MeasureSpec.getSize(heightMeasureSpec);
        if (measureMode == MeasureSpec.EXACTLY) {//使用的是固定值或者match_parent
            result = measureSize;
        } else {
            result = 200;
            if (measureMode == MeasureSpec.AT_MOST) {//使用wrap_content
                //使用默认和测量出来的两者的最小值
                result = Math.min(result, measureSize);
            }
        }
        return result;
    }
}
