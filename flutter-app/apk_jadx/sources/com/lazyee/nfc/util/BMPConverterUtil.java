package com.lazyee.nfc.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.lang.reflect.Array;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class BMPConverterUtil {
    public static final int PALETTE_BW = 0;
    public static final int PALETTE_BWR = 1;
    public static final int PALETTE_BWY = 2;

    private static byte plus_truncate_uchar(byte b, int i) {
        int i2 = (b & 255) + i;
        if (i2 < 0) {
            return (byte) 0;
        }
        if (i2 > 255) {
            return (byte) -1;
        }
        return (byte) (b + i);
    }

    public static RGBTriple[] getPalette(int i) {
        return i == 1 ? new RGBTriple[]{new RGBTriple(0, 0, 0), new RGBTriple(255, 255, 255), new RGBTriple(255, 0, 0)} : i == 2 ? new RGBTriple[]{new RGBTriple(0, 0, 0), new RGBTriple(255, 255, 255), new RGBTriple(255, 255, 0)} : i == 3 ? new RGBTriple[]{new RGBTriple(0, 0, 0), new RGBTriple(255, 255, 255), new RGBTriple(255, 0, 0), new RGBTriple(255, 255, 0)} : new RGBTriple[]{new RGBTriple(0, 0, 0), new RGBTriple(255, 255, 255)};
    }

    public static byte[][] fuckFloydSteinbergDither(RGBTriple[][] rGBTripleArr, RGBTriple[] rGBTripleArr2) {
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte.class, rGBTripleArr.length, rGBTripleArr[0].length);
        if (rGBTripleArr2.length <= 2) {
            int length = rGBTripleArr2.length;
        }
        for (int i = 0; i < rGBTripleArr.length; i++) {
            try {
                for (int i2 = 0; i2 < rGBTripleArr[i].length; i2++) {
                    bArr[i][i2] = (byte) rGBTripleArr[i][i2].getColorIndex();
                }
            } catch (Exception unused) {
            }
        }
        return bArr;
    }

    public static byte[][] floydSteinbergDither(RGBTriple[][] rGBTripleArr, RGBTriple[] rGBTripleArr2, boolean z) {
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte.class, rGBTripleArr.length, rGBTripleArr[0].length);
        int length = rGBTripleArr2.length > 2 ? 3 : rGBTripleArr2.length;
        for (int i = 0; i < rGBTripleArr.length; i++) {
            try {
                for (int i2 = 0; i2 < rGBTripleArr[i].length; i2++) {
                    RGBTriple rGBTriple = rGBTripleArr[i][i2];
                    byte bFindNearestColor = findNearestColor(rGBTriple, rGBTripleArr2);
                    bArr[i][i2] = bFindNearestColor;
                    if (!z) {
                        for (int i3 = 0; i3 < length; i3++) {
                            int i4 = (rGBTriple.channels[i3] & UByte.MAX_VALUE) - (rGBTripleArr2[bFindNearestColor].channels[i3] & UByte.MAX_VALUE);
                            int i5 = i2 + 1;
                            if (i5 < rGBTripleArr[0].length) {
                                rGBTripleArr[i][i5].channels[i3] = plus_truncate_uchar(rGBTripleArr[i][i5].channels[i3], (i4 * 7) >> 4);
                            }
                            int i6 = i + 1;
                            if (i6 < rGBTripleArr.length) {
                                int i7 = i2 - 1;
                                if (i7 > 0) {
                                    rGBTripleArr[i6][i7].channels[i3] = plus_truncate_uchar(rGBTripleArr[i6][i7].channels[i3], (i4 * 3) >> 4);
                                }
                                rGBTripleArr[i6][i2].channels[i3] = plus_truncate_uchar(rGBTripleArr[i6][i2].channels[i3], (i4 * 5) >> 4);
                                if (i5 < rGBTripleArr[0].length) {
                                    rGBTripleArr[i6][i5].channels[i3] = plus_truncate_uchar(rGBTripleArr[i6][i5].channels[i3], i4 >> 4);
                                }
                            }
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return bArr;
    }

    public static byte[] fuckFloydSteinberg(Bitmap bitmap, int i, boolean z) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        RGBTriple[][] rGBTripleArr = (RGBTriple[][]) Array.newInstance((Class<?>) RGBTriple.class, width, height);
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
                rGBTripleArr[i2][i3] = new RGBTriple(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
            }
        }
        int i4 = width % 4;
        int i5 = (width * 3) + i4;
        byte[][] bArrFuckFloydSteinbergDither = fuckFloydSteinbergDither(rGBTripleArr, getPalette(i));
        byte[] bArr = new byte[(width * height * 3) + (i4 * height)];
        int i6 = height - 1;
        int i7 = 0;
        while (i7 < height) {
            int i8 = 0;
            int i9 = 0;
            while (i8 < width) {
                if (bArrFuckFloydSteinbergDither[i8][i7] == 0) {
                    int i10 = (i6 * i5) + i9;
                    bArr[i10] = 0;
                    bArr[i10 + 1] = 0;
                    bArr[i10 + 2] = 0;
                } else if (bArrFuckFloydSteinbergDither[i8][i7] == 1) {
                    int i11 = (i6 * i5) + i9;
                    bArr[i11] = -1;
                    bArr[i11 + 1] = -1;
                    bArr[i11 + 2] = -1;
                } else if (bArrFuckFloydSteinbergDither[i8][i7] == 2) {
                    int i12 = (i6 * i5) + i9;
                    bArr[i12] = 0;
                    bArr[i12 + 1] = 0;
                    bArr[i12 + 2] = -1;
                } else if (bArrFuckFloydSteinbergDither[i8][i7] == 3) {
                    int i13 = (i6 * i5) + i9;
                    bArr[i13] = 0;
                    bArr[i13 + 1] = -1;
                    bArr[i13 + 2] = -1;
                }
                i8++;
                i9 += 3;
            }
            i7++;
            i6--;
        }
        return bArr;
    }

    public static byte[] floydSteinbergTransformer(Bitmap bitmap, int i, boolean z, boolean z2, boolean z3, char c) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        RGBTriple[][] rGBTripleArr = (RGBTriple[][]) Array.newInstance((Class<?>) RGBTriple.class, width, height);
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
                rGBTripleArr[i2][i3] = new RGBTriple(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
            }
        }
        int i4 = width % 4;
        int i5 = (width * 3) + i4;
        byte[][] bArrFloydSteinbergDither = floydSteinbergDither(rGBTripleArr, getPalette(i), z);
        if (z2) {
            byte[] bArr = new byte[height];
            for (int i6 = 0; i6 < width / 2; i6++) {
                System.arraycopy(bArrFloydSteinbergDither[i6], 0, bArr, 0, height);
                int i7 = (width - i6) - 1;
                System.arraycopy(bArrFloydSteinbergDither[i7], 0, bArrFloydSteinbergDither[i6], 0, height);
                System.arraycopy(bArr, 0, bArrFloydSteinbergDither[i7], 0, height);
            }
        } else if (z3) {
            for (int i8 = 0; i8 < width; i8++) {
                for (int i9 = 0; i9 < height / 2; i9++) {
                    byte b = bArrFloydSteinbergDither[i8][i9];
                    int i10 = (height - i9) - 1;
                    bArrFloydSteinbergDither[i8][i9] = bArrFloydSteinbergDither[i8][i10];
                    bArrFloydSteinbergDither[i8][i10] = b;
                }
            }
        }
        byte[] bArr2 = new byte[(width * height * 3) + (i4 * height)];
        if (c == 0) {
            int i11 = height - 1;
            int i12 = 0;
            while (i12 < height) {
                int i13 = 0;
                int i14 = 0;
                while (i13 < width) {
                    if (bArrFloydSteinbergDither[i13][i12] == 0) {
                        int i15 = (i11 * i5) + i14;
                        bArr2[i15] = 0;
                        bArr2[i15 + 1] = 0;
                        bArr2[i15 + 2] = 0;
                    } else if (bArrFloydSteinbergDither[i13][i12] == 1) {
                        int i16 = (i11 * i5) + i14;
                        bArr2[i16] = -1;
                        bArr2[i16 + 1] = -1;
                        bArr2[i16 + 2] = -1;
                    } else if (bArrFloydSteinbergDither[i13][i12] == 2 && i == 1) {
                        int i17 = (i11 * i5) + i14;
                        bArr2[i17] = 0;
                        bArr2[i17 + 1] = 0;
                        bArr2[i17 + 2] = -1;
                    } else if (bArrFloydSteinbergDither[i13][i12] == 2 && i == 2) {
                        int i18 = (i11 * i5) + i14;
                        bArr2[i18] = 0;
                        bArr2[i18 + 1] = -1;
                        bArr2[i18 + 2] = -1;
                    } else if (i == 3) {
                        if (bArrFloydSteinbergDither[i13][i12] == 2) {
                            int i19 = (i11 * i5) + i14;
                            bArr2[i19] = 0;
                            bArr2[i19 + 1] = 0;
                            bArr2[i19 + 2] = -1;
                        } else if (bArrFloydSteinbergDither[i13][i12] == 3) {
                            int i20 = (i11 * i5) + i14;
                            bArr2[i20] = 0;
                            bArr2[i20 + 1] = -1;
                            bArr2[i20 + 2] = -1;
                        }
                    }
                    i13++;
                    i14 += 3;
                }
                i12++;
                i11--;
            }
        } else {
            int i21 = height - 1;
            int i22 = i21;
            while (i21 >= 0) {
                int i23 = width - 1;
                int i24 = 0;
                while (i23 >= 0) {
                    if (bArrFloydSteinbergDither[i23][i21] == 0) {
                        int i25 = (i22 * i5) + i24;
                        bArr2[i25] = 0;
                        bArr2[i25 + 1] = 0;
                        bArr2[i25 + 2] = 0;
                    } else if (bArrFloydSteinbergDither[i23][i21] == 1) {
                        int i26 = (i22 * i5) + i24;
                        bArr2[i26] = -1;
                        bArr2[i26 + 1] = -1;
                        bArr2[i26 + 2] = -1;
                    } else if (bArrFloydSteinbergDither[i23][i21] == 2 && i == 1) {
                        int i27 = (i22 * i5) + i24;
                        bArr2[i27] = 0;
                        bArr2[i27 + 1] = 0;
                        bArr2[i27 + 2] = -1;
                    } else if (bArrFloydSteinbergDither[i23][i21] == 2 && i == 2) {
                        int i28 = (i22 * i5) + i24;
                        bArr2[i28] = 0;
                        bArr2[i28 + 1] = -1;
                        bArr2[i28 + 2] = -1;
                    } else if (i == 3) {
                        if (bArrFloydSteinbergDither[i23][i21] == 2) {
                            int i29 = (i22 * i5) + i24;
                            bArr2[i29] = 0;
                            bArr2[i29 + 1] = 0;
                            bArr2[i29 + 2] = -1;
                        } else if (bArrFloydSteinbergDither[i23][i21] == 3) {
                            int i30 = (i22 * i5) + i24;
                            bArr2[i30] = 0;
                            bArr2[i30 + 1] = -1;
                            bArr2[i30 + 2] = -1;
                        }
                    }
                    i23--;
                    i24 += 3;
                }
                i21--;
                i22--;
            }
        }
        return bArr2;
    }

    private static byte findNearestColor(RGBTriple rGBTriple, RGBTriple[] rGBTripleArr) {
        int i = 195076;
        byte b = 0;
        for (byte b2 = 0; b2 < rGBTripleArr.length; b2 = (byte) (b2 + 1)) {
            int i2 = (rGBTriple.channels[0] & UByte.MAX_VALUE) - (rGBTripleArr[b2].channels[0] & UByte.MAX_VALUE);
            int i3 = (rGBTriple.channels[1] & UByte.MAX_VALUE) - (rGBTripleArr[b2].channels[1] & UByte.MAX_VALUE);
            int i4 = (rGBTriple.channels[2] & UByte.MAX_VALUE) - (rGBTripleArr[b2].channels[2] & UByte.MAX_VALUE);
            int i5 = (i2 * i2) + (i3 * i3) + (i4 * i4);
            if (i5 < i) {
                b = b2;
                i = i5;
            }
        }
        return b;
    }

    public static class RGBTriple {
        public final byte[] channels;
        private int colorValue;

        public RGBTriple(int i, int i2, int i3) {
            this.colorValue = Color.argb(255, i, i2, i3);
            this.channels = new byte[]{(byte) i, (byte) i2, (byte) i3};
        }

        boolean isBlack() {
            return this.colorValue == -16777216;
        }

        boolean isYellow() {
            return this.colorValue == -256;
        }

        boolean isWhite() {
            return this.colorValue == -1;
        }

        boolean isRed() {
            return this.colorValue == -65536;
        }

        public int getColorIndex() {
            if (isBlack()) {
                return 0;
            }
            if (isWhite()) {
                return 1;
            }
            if (isRed()) {
                return 2;
            }
            return isYellow() ? 3 : 0;
        }
    }
}
