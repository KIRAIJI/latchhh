package com.shangxian.pinkink.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InkScreenImageDitherUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0002J\u001e\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ)\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00102\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000bH\u0002J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\u0006\u0010\b\u001a\u00020\u0004H\u0002J \u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J#\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u00072\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0010H\u0002¢\u0006\u0002\u0010\u001cJ&\u0010\u001d\u001a\u00020\u001e2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\u0006\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u0007H\u0002J.\u0010 \u001a\u00020\u001e2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\u0006\u0010\u001f\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J&\u0010\"\u001a\u00020\u001e2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\u0006\u0010\u001f\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u000bH\u0002¨\u0006$"}, d2 = {"Lcom/shangxian/pinkink/util/InkScreenImageDitherUtils;", "", "()V", "createDitherBitmap", "Landroid/graphics/Bitmap;", "pixels", "", "Lcom/shangxian/pinkink/util/PixelColor;", "bitmap", "dithering", "threshold", "", "filter", "Lcom/shangxian/pinkink/util/InkFilterMode;", "ditheringCanvasByPalette", "palette", "", "(Landroid/graphics/Bitmap;[Lcom/shangxian/pinkink/util/PixelColor;Lcom/shangxian/pinkink/util/InkFilterMode;)Landroid/graphics/Bitmap;", "getAvailableColorValue", "colorVal", "getBitmapPixels", "", "getColorErr", "color1", "color2", "rate", "getNearColorV2", TypedValues.Custom.S_COLOR, "(Lcom/shangxian/pinkink/util/PixelColor;[Lcom/shangxian/pinkink/util/PixelColor;)Lcom/shangxian/pinkink/util/PixelColor;", "updatePixel", "", "index", "updatePixelErr", NotificationCompat.CATEGORY_ERROR, "updatePixelsRedValue", "errRedValue", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class InkScreenImageDitherUtils {
    public static final InkScreenImageDitherUtils INSTANCE = new InkScreenImageDitherUtils();

    /* JADX INFO: compiled from: InkScreenImageDitherUtils.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[InkFilterMode.values().length];
            iArr[InkFilterMode.BINARY.ordinal()] = 1;
            iArr[InkFilterMode.BAYER.ordinal()] = 2;
            iArr[InkFilterMode.FLOYD_STEINBERG.ordinal()] = 3;
            iArr[InkFilterMode.ATKINSON.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final int getAvailableColorValue(int colorVal) {
        if (colorVal < 0) {
            return 0;
        }
        if (colorVal > 255) {
            return 255;
        }
        return colorVal;
    }

    private InkScreenImageDitherUtils() {
    }

    private final PixelColor getColorErr(PixelColor color1, PixelColor color2, int rate) {
        int red = color1.getRed();
        int green = color1.getGreen();
        int blue = color1.getBlue();
        float f = rate;
        return new PixelColor((int) Math.floor((red - color2.getRed()) / f), (int) Math.floor((green - color2.getGreen()) / f), (int) Math.floor((blue - color2.getBlue()) / f), 0, 8, null);
    }

    private final PixelColor getNearColorV2(PixelColor color, PixelColor[] palette) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int length = palette.length;
        int i = 0;
        int i2 = 195076;
        int i3 = 0;
        while (i < length) {
            int i4 = i + 1;
            int red2 = (red & 255) - (palette[i].getRed() & 255);
            int green2 = (green & 255) - (palette[i].getGreen() & 255);
            int blue2 = (blue & 255) - (palette[i].getBlue() & 255);
            int i5 = (red2 * red2) + (green2 * green2) + (blue2 * blue2);
            if (i5 < i2) {
                i3 = i;
                i = i4;
                i2 = i5;
            } else {
                i = i4;
            }
        }
        return palette[i3];
    }

    private final void updatePixel(List<PixelColor> pixels, int index, PixelColor color) {
        if (index >= pixels.size()) {
            return;
        }
        pixels.set(index, color);
    }

    private final void updatePixelErr(List<PixelColor> pixels, int index, PixelColor err, int rate) {
        if (index >= pixels.size()) {
            return;
        }
        PixelColor pixelColor = pixels.get(index);
        int red = pixelColor.getRed();
        int green = pixelColor.getGreen();
        int blue = pixelColor.getBlue();
        pixels.set(index, new PixelColor(getAvailableColorValue(red + (err.getRed() * rate)), getAvailableColorValue(green + (err.getGreen() * rate)), getAvailableColorValue(blue + (err.getBlue() * rate)), 0, 8, null));
    }

    private final List<PixelColor> getBitmapPixels(Bitmap bitmap) {
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < width; i++) {
            int i2 = iArr[i];
            arrayList.add(new PixelColor(Color.red(i2), Color.green(i2), Color.blue(i2), 0, 8, null));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x016e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.graphics.Bitmap dithering(android.graphics.Bitmap r27, int r28, com.shangxian.pinkink.util.InkFilterMode r29) {
        /*
            Method dump skipped, instruction units count: 450
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.util.InkScreenImageDitherUtils.dithering(android.graphics.Bitmap, int, com.shangxian.pinkink.util.InkFilterMode):android.graphics.Bitmap");
    }

    private final void updatePixelsRedValue(List<PixelColor> pixels, int index, int errRedValue) {
        if (index >= pixels.size()) {
            return;
        }
        PixelColor pixelColor = pixels.get(index);
        pixels.set(index, new PixelColor(pixelColor.getRed() + errRedValue, pixelColor.getGreen(), pixelColor.getBlue(), 0, 8, null));
    }

    public final Bitmap ditheringCanvasByPalette(Bitmap bitmap, PixelColor[] palette, InkFilterMode filter) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(palette, "palette");
        Intrinsics.checkNotNullParameter(filter, "filter");
        int width = bitmap.getWidth();
        List<PixelColor> bitmapPixels = getBitmapPixels(bitmap);
        int i = 0;
        for (Object obj : bitmapPixels) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            PixelColor pixelColor = (PixelColor) obj;
            InkScreenImageDitherUtils inkScreenImageDitherUtils = INSTANCE;
            PixelColor nearColorV2 = inkScreenImageDitherUtils.getNearColorV2(pixelColor, palette);
            int i3 = WhenMappings.$EnumSwitchMapping$0[filter.ordinal()];
            if (i3 == 1) {
                inkScreenImageDitherUtils.updatePixel(bitmapPixels, i, nearColorV2);
            } else if (i3 == 3) {
                PixelColor colorErr = inkScreenImageDitherUtils.getColorErr(pixelColor, nearColorV2, 16);
                inkScreenImageDitherUtils.updatePixel(bitmapPixels, i, nearColorV2);
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i2, colorErr, 7);
                int i4 = i + width;
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i4 - 1, colorErr, 3);
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i4, colorErr, 5);
            } else if (i3 == 4) {
                PixelColor colorErr2 = inkScreenImageDitherUtils.getColorErr(pixelColor, nearColorV2, 8);
                inkScreenImageDitherUtils.updatePixel(bitmapPixels, i, nearColorV2);
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i2, colorErr2, 1);
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i + 2, colorErr2, 1);
                int i5 = i + width;
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i5 - 1, colorErr2, 1);
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i5, colorErr2, 1);
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i5 + 1, colorErr2, 1);
                inkScreenImageDitherUtils.updatePixelErr(bitmapPixels, i + (width * 2), colorErr2, 1);
            }
            i = i2;
        }
        return createDitherBitmap(bitmapPixels, bitmap);
    }

    private final Bitmap createDitherBitmap(List<PixelColor> pixels, Bitmap bitmap) {
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        int[] iArr = new int[pixels.size()];
        int i = 0;
        for (Object obj : pixels) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            PixelColor pixelColor = (PixelColor) obj;
            iArr[i] = Color.rgb(pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue());
            i = i2;
        }
        mutableBitmap.setPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        Intrinsics.checkNotNullExpressionValue(mutableBitmap, "mutableBitmap");
        return mutableBitmap;
    }
}
