package com.zhpan.bannerview.provider;

import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

/* JADX INFO: loaded from: classes.dex */
public class RoundViewOutlineProvider extends ViewOutlineProvider {
    private final float mRadius;

    public RoundViewOutlineProvider(float f) {
        this.mRadius = f;
    }

    @Override // android.view.ViewOutlineProvider
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(new Rect(0, 0, view.getWidth(), view.getHeight()), this.mRadius);
    }
}
