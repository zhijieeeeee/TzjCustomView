package com.tzj.tzjcustomview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class AnimationMenuActivity extends AppCompatActivity {

    private ImageView iv;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;

    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_menu);

        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);

        ObjectAnimator iv2Anim = ObjectAnimator.ofFloat(iv2, "translationX", 200);
        ObjectAnimator iv3Anim = ObjectAnimator.ofFloat(iv3, "translationX", -200);
        ObjectAnimator iv4Anim = ObjectAnimator.ofFloat(iv4, "translationY", 200);
        ObjectAnimator iv5Anim = ObjectAnimator.ofFloat(iv5, "translationY", -200);
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.playTogether(iv2Anim, iv3Anim, iv4Anim, iv5Anim);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet.start();
            }
        });
    }
}
