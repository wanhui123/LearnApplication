package com.wh.learnapplication.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wh.learnapplication.R;
import com.wh.learnapplication.widgit.StepProgressView;

public class StepprogressviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepprogressview);
        final StepProgressView stepProgressView = findViewById(R.id.ts_step_progress_view);

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                stepProgressView.setMaxProgress(100);
                stepProgressView.setCurrentProgress(90);
            }
        });
        valueAnimator.start();


    }
}
