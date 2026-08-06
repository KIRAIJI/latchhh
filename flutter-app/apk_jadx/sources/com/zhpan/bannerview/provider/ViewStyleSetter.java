package com.zhpan.bannerview.provider;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ViewStyleSetter {
    public static void applyRoundCorner(View view, float f) {
        if (view == null) {
            return;
        }
        view.setClipToOutline(true);
        view.setOutlineProvider(new RoundViewOutlineProvider(f));
    }
}
