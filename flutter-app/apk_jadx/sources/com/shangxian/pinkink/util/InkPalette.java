package com.shangxian.pinkink.util;

import kotlin.Metadata;

/* JADX INFO: compiled from: InkScreenImageDitherUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\n\u0010\u0007R\u0019\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\f\u0010\u0007¨\u0006\r"}, d2 = {"Lcom/shangxian/pinkink/util/InkPalette;", "", "()V", "BW", "", "Lcom/shangxian/pinkink/util/PixelColor;", "getBW", "()[Lcom/shangxian/pinkink/util/PixelColor;", "[Lcom/shangxian/pinkink/util/PixelColor;", "BWR", "getBWR", "BWRY", "getBWRY", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class InkPalette {
    public static final InkPalette INSTANCE = new InkPalette();
    private static final PixelColor[] BWR = {new PixelColor(0, 0, 0, 255), new PixelColor(255, 255, 255, 255), new PixelColor(255, 0, 0, 255)};
    private static final PixelColor[] BWRY = {new PixelColor(0, 0, 0, 255), new PixelColor(255, 255, 255, 255), new PixelColor(255, 0, 0, 255), new PixelColor(255, 255, 0, 255)};
    private static final PixelColor[] BW = {new PixelColor(0, 0, 0, 255), new PixelColor(255, 255, 255, 255)};

    private InkPalette() {
    }

    public final PixelColor[] getBWR() {
        return BWR;
    }

    public final PixelColor[] getBWRY() {
        return BWRY;
    }

    public final PixelColor[] getBW() {
        return BW;
    }
}
