package com.shangxian.pinkink.util;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: InkScreenImageDitherUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u0012"}, d2 = {"Lcom/shangxian/pinkink/util/PixelColor;", "", "red", "", "green", "blue", "alpha", "(IIII)V", "getAlpha", "()I", "setAlpha", "(I)V", "getBlue", "setBlue", "getGreen", "setGreen", "getRed", "setRed", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PixelColor {
    private int alpha;
    private int blue;
    private int green;
    private int red;

    public PixelColor(int i, int i2, int i3, int i4) {
        this.red = i;
        this.green = i2;
        this.blue = i3;
        this.alpha = i4;
    }

    public /* synthetic */ PixelColor(int i, int i2, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, (i5 & 8) != 0 ? 255 : i4);
    }

    public final int getAlpha() {
        return this.alpha;
    }

    public final int getBlue() {
        return this.blue;
    }

    public final int getGreen() {
        return this.green;
    }

    public final int getRed() {
        return this.red;
    }

    public final void setAlpha(int i) {
        this.alpha = i;
    }

    public final void setBlue(int i) {
        this.blue = i;
    }

    public final void setGreen(int i) {
        this.green = i;
    }

    public final void setRed(int i) {
        this.red = i;
    }
}
