package com.tzj.tzjcustomview.circleseekbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.tzj.tzjcustomview.R;

/**
 * Description：I won't write any description!
 * Author ：Spider-Man.
 * Date：2019/1/2
 */
public class ChairView extends LinearLayout {

    private FrameLayout fl;
    private ImageView iv1;
    private ImageView iv2;
    private CircleSeekBarView circle_seekbar;
    private SeekBar seekbar;

    public ChairView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.chair_view, this, true);

        fl = (FrameLayout) findViewById(R.id.fl);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        circle_seekbar = (CircleSeekBarView) findViewById(R.id.circle_seekbar);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        circle_seekbar.setThumbDegree(260);
        circle_seekbar.setOnSeekListener(new CircleSeekBarView.OnSeekListener() {
            @Override
            public void onSeek(int thumbRotateDegree) {
                iv2.setPivotX(iv2.getWidth() / 2);
                iv2.setPivotY(iv2.getHeight());
                iv2.setRotation(thumbRotateDegree);
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
