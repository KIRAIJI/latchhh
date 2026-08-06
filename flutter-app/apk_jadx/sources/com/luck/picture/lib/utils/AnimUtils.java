package com.luck.picture.lib.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.Key;

/* JADX INFO: loaded from: classes.dex */
public class AnimUtils {
    public static final int DURATION = 250;

    public static void rotateArrow(ImageView imageView, boolean z) {
        float f = 0.0f;
        float f2 = 180.0f;
        if (!z) {
            f2 = 0.0f;
            f = 180.0f;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ROTATION, f, f2);
        objectAnimatorOfFloat.setDuration(250L);
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        objectAnimatorOfFloat.start();
    }

    public static void selectZoom(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.05f, 1.0f), ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.05f, 1.0f));
        animatorSet.setDuration(250L);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }
}
