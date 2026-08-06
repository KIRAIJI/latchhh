package com.shangxian.pinkink.constants;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;

/* JADX INFO: compiled from: InkScreenSize.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/shangxian/pinkink/constants/InkScreenSize;", "", "width", "", "height", "(Ljava/lang/String;III)V", "getHeight", "()I", "getWidth", "MODEL1", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public enum InkScreenSize {
    MODEL1(240, TypedValues.CycleType.TYPE_PATH_ROTATE);

    private final int height;
    private final int width;

    InkScreenSize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }
}
