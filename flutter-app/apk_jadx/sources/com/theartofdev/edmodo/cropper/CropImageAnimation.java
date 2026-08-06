package com.theartofdev.edmodo.cropper;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
final class CropImageAnimation extends Animation implements Animation.AnimationListener {
    private final CropOverlayView mCropOverlayView;
    private final ImageView mImageView;
    private final float[] mStartBoundPoints = new float[8];
    private final float[] mEndBoundPoints = new float[8];
    private final RectF mStartCropWindowRect = new RectF();
    private final RectF mEndCropWindowRect = new RectF();
    private final float[] mStartImageMatrix = new float[9];
    private final float[] mEndImageMatrix = new float[9];
    private final RectF mAnimRect = new RectF();
    private final float[] mAnimPoints = new float[8];
    private final float[] mAnimMatrix = new float[9];

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
    }

    public CropImageAnimation(ImageView imageView, CropOverlayView cropOverlayView) {
        this.mImageView = imageView;
        this.mCropOverlayView = cropOverlayView;
        setDuration(300L);
        setFillAfter(true);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(this);
    }

    public void setStartState(float[] fArr, Matrix matrix) {
        reset();
        System.arraycopy(fArr, 0, this.mStartBoundPoints, 0, 8);
        this.mStartCropWindowRect.set(this.mCropOverlayView.getCropWindowRect());
        matrix.getValues(this.mStartImageMatrix);
    }

    public void setEndState(float[] fArr, Matrix matrix) {
        System.arraycopy(fArr, 0, this.mEndBoundPoints, 0, 8);
        this.mEndCropWindowRect.set(this.mCropOverlayView.getCropWindowRect());
        matrix.getValues(this.mEndImageMatrix);
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        float[] fArr;
        this.mAnimRect.left = this.mStartCropWindowRect.left + ((this.mEndCropWindowRect.left - this.mStartCropWindowRect.left) * f);
        this.mAnimRect.top = this.mStartCropWindowRect.top + ((this.mEndCropWindowRect.top - this.mStartCropWindowRect.top) * f);
        this.mAnimRect.right = this.mStartCropWindowRect.right + ((this.mEndCropWindowRect.right - this.mStartCropWindowRect.right) * f);
        this.mAnimRect.bottom = this.mStartCropWindowRect.bottom + ((this.mEndCropWindowRect.bottom - this.mStartCropWindowRect.bottom) * f);
        this.mCropOverlayView.setCropWindowRect(this.mAnimRect);
        int i = 0;
        int i2 = 0;
        while (true) {
            fArr = this.mAnimPoints;
            if (i2 >= fArr.length) {
                break;
            }
            float[] fArr2 = this.mStartBoundPoints;
            fArr[i2] = fArr2[i2] + ((this.mEndBoundPoints[i2] - fArr2[i2]) * f);
            i2++;
        }
        this.mCropOverlayView.setBounds(fArr, this.mImageView.getWidth(), this.mImageView.getHeight());
        while (true) {
            float[] fArr3 = this.mAnimMatrix;
            if (i < fArr3.length) {
                float[] fArr4 = this.mStartImageMatrix;
                fArr3[i] = fArr4[i] + ((this.mEndImageMatrix[i] - fArr4[i]) * f);
                i++;
            } else {
                Matrix imageMatrix = this.mImageView.getImageMatrix();
                imageMatrix.setValues(this.mAnimMatrix);
                this.mImageView.setImageMatrix(imageMatrix);
                this.mImageView.invalidate();
                this.mCropOverlayView.invalidate();
                return;
            }
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        this.mImageView.clearAnimation();
    }
}
