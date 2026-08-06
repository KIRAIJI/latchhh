package com.lazyee.nfc.util;

import android.graphics.BitmapFactory;
import android.util.Log;
import com.google.android.material.timepicker.TimeModel;
import com.lazyee.nfc.manager.NfcManager;
import java.io.FileInputStream;
import java.io.IOException;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class ImageUtils {
    public static byte byte_change(byte b) {
        byte b2 = 0;
        for (byte b3 = 0; b3 < 8; b3 = (byte) (b3 + 1)) {
            b2 = (byte) (((byte) (b2 << 1)) | ((b >>> b3) & 1));
        }
        return b2;
    }

    public static byte[] ReadBmp8File(String str) {
        int width = NfcManager.getDeviceInfo().getWidth();
        int height = NfcManager.getDeviceInfo().getHeight();
        int i = width / 8;
        if (i % 4 != 0) {
            i = ((i / 4) + 1) * 4;
        }
        int i2 = (i * height) + 62 + ((8 - (height % 8)) * i);
        byte[] bArr = new byte[i2];
        try {
            byte[] bArr2 = new byte[i2];
            FileInputStream fileInputStream = new FileInputStream(str);
            int i3 = 0;
            fileInputStream.read(bArr2, 0, i2);
            fileInputStream.close();
            LogUtil.d(FMUtil.byteToHex(bArr2));
            for (int i4 = height - 1; i4 >= 0; i4--) {
                System.arraycopy(bArr2, (i4 * i) + 62, bArr, (i3 * i) + 62, i);
                i3++;
            }
            return bArr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] ReadBmp24File(String str, boolean z) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i = 0;
        options.inJustDecodeBounds = false;
        options.inBitmap = null;
        options.inMutable = true;
        byte[] bArrFuckFloydSteinberg = BMPConverterUtil.fuckFloydSteinberg(NfcManager.getDeviceInfo().getBitmap(), NfcManager.getDeviceInfo().getDeviceType(), z);
        int width = NfcManager.getDeviceInfo().getWidth();
        int height = NfcManager.getDeviceInfo().getHeight();
        int i2 = width % 4;
        int i3 = (width * height * 3) + 54;
        byte[] bArr = new byte[(i2 != 0 ? i2 * height : 0) + i3];
        System.arraycopy(bArrFuckFloydSteinberg, 0, bArr, 54, bArrFuckFloydSteinberg.length);
        byte[] bArr2 = new byte[i3];
        for (int i4 = height - 1; i4 >= 0; i4--) {
            int i5 = width * 3;
            try {
                System.arraycopy(bArr, (i4 * i5) + 54 + (i4 * i2), bArr2, (i * i5) + 54, i5);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return bArr2;
    }

    public static void VerticalScanning(byte[] bArr, byte[] bArr2, int i, int i2, int i3) {
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            for (int i6 = 0; i6 < i2; i6++) {
                int i7 = (i3 * 8 * i6) + 62;
                bArr[(i4 * i2) + i6] = (byte) (((short) (bArr2[i7 + i5] & ByteCompanionObject.MIN_VALUE)) + ((short) ((bArr2[(i7 + i3) + i5] & ByteCompanionObject.MIN_VALUE) >>> 1)) + ((short) ((bArr2[((i3 * 2) + i7) + i5] & ByteCompanionObject.MIN_VALUE) >>> 2)) + ((short) ((bArr2[((i3 * 3) + i7) + i5] & ByteCompanionObject.MIN_VALUE) >>> 3)) + ((short) ((bArr2[((i3 * 4) + i7) + i5] & ByteCompanionObject.MIN_VALUE) >>> 4)) + ((short) ((bArr2[((i3 * 5) + i7) + i5] & ByteCompanionObject.MIN_VALUE) >>> 5)) + ((short) ((bArr2[((i3 * 6) + i7) + i5] & ByteCompanionObject.MIN_VALUE) >>> 6)) + ((short) ((bArr2[(i7 + (i3 * 7)) + i5] & ByteCompanionObject.MIN_VALUE) >>> 7)));
            }
            int i8 = i4 + 1;
            for (int i9 = 0; i9 < i2; i9++) {
                int i10 = (i3 * 8 * i9) + 62;
                bArr[(i8 * i2) + i9] = (byte) (((short) ((bArr2[i10 + i5] & 64) << 1)) + ((short) (bArr2[i10 + i3 + i5] & 64)) + ((short) ((bArr2[((i3 * 2) + i10) + i5] & 64) >>> 1)) + ((short) ((bArr2[((i3 * 3) + i10) + i5] & 64) >>> 2)) + ((short) ((bArr2[((i3 * 4) + i10) + i5] & 64) >>> 3)) + ((short) ((bArr2[((i3 * 5) + i10) + i5] & 64) >>> 4)) + ((short) ((bArr2[((i3 * 6) + i10) + i5] & 64) >>> 5)) + ((short) ((bArr2[(i10 + (i3 * 7)) + i5] & 64) >>> 6)));
            }
            int i11 = i8 + 1;
            for (int i12 = 0; i12 < i2; i12++) {
                int i13 = (i3 * 8 * i12) + 62;
                bArr[(i11 * i2) + i12] = (byte) (((short) ((bArr2[i13 + i5] & 32) << 2)) + ((short) ((bArr2[(i13 + i3) + i5] & 32) << 1)) + ((short) (bArr2[(i3 * 2) + i13 + i5] & 32)) + ((short) ((bArr2[((i3 * 3) + i13) + i5] & 32) >>> 1)) + ((short) ((bArr2[((i3 * 4) + i13) + i5] & 32) >>> 2)) + ((short) ((bArr2[((i3 * 5) + i13) + i5] & 32) >>> 3)) + ((short) ((bArr2[((i3 * 6) + i13) + i5] & 32) >>> 4)) + ((short) ((bArr2[(i13 + (i3 * 7)) + i5] & 32) >>> 5)));
            }
            int i14 = i11 + 1;
            for (int i15 = 0; i15 < i2; i15++) {
                int i16 = (i3 * 8 * i15) + 62;
                bArr[(i14 * i2) + i15] = (byte) (((short) ((bArr2[i16 + i5] & 16) << 3)) + ((short) ((bArr2[(i16 + i3) + i5] & 16) << 2)) + ((short) ((bArr2[((i3 * 2) + i16) + i5] & 16) << 1)) + ((short) (bArr2[(i3 * 3) + i16 + i5] & 16)) + ((short) ((bArr2[((i3 * 4) + i16) + i5] & 16) >>> 1)) + ((short) ((bArr2[((i3 * 5) + i16) + i5] & 16) >>> 2)) + ((short) ((bArr2[((i3 * 6) + i16) + i5] & 16) >>> 3)) + ((short) ((bArr2[(i16 + (i3 * 7)) + i5] & 16) >>> 4)));
            }
            int i17 = i14 + 1;
            for (int i18 = 0; i18 < i2; i18++) {
                int i19 = (i3 * 8 * i18) + 62;
                bArr[(i17 * i2) + i18] = (byte) (((short) ((bArr2[i19 + i5] & 8) << 4)) + ((short) ((bArr2[(i19 + i3) + i5] & 8) << 3)) + ((short) ((bArr2[((i3 * 2) + i19) + i5] & 8) << 2)) + ((short) ((bArr2[((i3 * 3) + i19) + i5] & 8) << 1)) + ((short) (bArr2[(i3 * 4) + i19 + i5] & 8)) + ((short) ((bArr2[((i3 * 5) + i19) + i5] & 8) >>> 1)) + ((short) ((bArr2[((i3 * 6) + i19) + i5] & 8) >>> 2)) + ((short) ((bArr2[(i19 + (i3 * 7)) + i5] & 8) >>> 3)));
            }
            int i20 = i17 + 1;
            for (int i21 = 0; i21 < i2; i21++) {
                int i22 = (i3 * 8 * i21) + 62;
                bArr[(i20 * i2) + i21] = (byte) (((short) ((bArr2[i22 + i5] & 4) << 5)) + ((short) ((bArr2[(i22 + i3) + i5] & 4) << 4)) + ((short) ((bArr2[((i3 * 2) + i22) + i5] & 4) << 3)) + ((short) ((bArr2[((i3 * 3) + i22) + i5] & 4) << 2)) + ((short) ((bArr2[((i3 * 4) + i22) + i5] & 4) << 1)) + ((short) (bArr2[(i3 * 5) + i22 + i5] & 4)) + ((short) ((bArr2[((i3 * 6) + i22) + i5] & 4) >>> 1)) + ((short) ((bArr2[(i22 + (i3 * 7)) + i5] & 4) >>> 2)));
            }
            int i23 = i20 + 1;
            for (int i24 = 0; i24 < i2; i24++) {
                int i25 = (i3 * 8 * i24) + 62;
                bArr[(i23 * i2) + i24] = (byte) (((short) ((bArr2[i25 + i5] & 2) << 6)) + ((short) ((bArr2[(i25 + i3) + i5] & 2) << 5)) + ((short) ((bArr2[((i3 * 2) + i25) + i5] & 2) << 4)) + ((short) ((bArr2[((i3 * 3) + i25) + i5] & 2) << 3)) + ((short) ((bArr2[((i3 * 4) + i25) + i5] & 2) << 2)) + ((short) ((bArr2[((i3 * 5) + i25) + i5] & 2) << 1)) + ((short) (bArr2[(i3 * 6) + i25 + i5] & 2)) + ((short) ((bArr2[(i25 + (i3 * 7)) + i5] & 2) >>> 1)));
            }
            int i26 = i23 + 1;
            for (int i27 = 0; i27 < i2; i27++) {
                int i28 = (i3 * 8 * i27) + 62;
                bArr[(i26 * i2) + i27] = (byte) (((short) ((bArr2[i28 + i5] & 1) << 7)) + ((short) ((bArr2[(i28 + i3) + i5] & 1) << 6)) + ((short) ((bArr2[((i3 * 2) + i28) + i5] & 1) << 5)) + ((short) ((bArr2[((i3 * 3) + i28) + i5] & 1) << 4)) + ((short) ((bArr2[((i3 * 4) + i28) + i5] & 1) << 3)) + ((short) ((bArr2[((i3 * 5) + i28) + i5] & 1) << 2)) + ((short) ((bArr2[((i3 * 6) + i28) + i5] & 1) << 1)) + ((short) (bArr2[i28 + (i3 * 7) + i5] & 1)));
            }
            i4 = i26 + 1;
        }
    }

    public static byte[] Color24VerticalScanning(byte[] bArr) {
        int width = NfcManager.getDeviceInfo().getWidth();
        int height = NfcManager.getDeviceInfo().getHeight();
        int i = height % 8;
        int i2 = i != 0 ? 8 - i : 0;
        int length = bArr.length + (i2 * width);
        byte[] bArr2 = new byte[length];
        int i3 = 0;
        for (int i4 = 0; i4 < width; i4++) {
            for (int i5 = 0; i5 < height; i5++) {
                int i6 = i3 + 1;
                bArr2[i3] = bArr[(i5 * width) + i4];
                if (i5 == height - 1) {
                    for (int i7 = 0; i7 < i2; i7++) {
                        bArr2[i6 + i7] = 0;
                    }
                    i6 += i2;
                }
                i3 = i6;
            }
        }
        byte[] bArr3 = new byte[8];
        int i8 = (height + i2) / 8;
        byte[] bArr4 = new byte[width * i8];
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        for (int i12 = 0; i12 < length; i12++) {
            int i13 = i9 + 1;
            bArr3[i9] = bArr2[i12];
            if (i13 == 8) {
                bArr4[i10 + i11] = (byte) ((bArr3[0] << 7) | bArr3[7] | (bArr3[6] << 1) | (bArr3[5] << 2) | (bArr3[4] << 3) | (bArr3[3] << 4) | (bArr3[2] << 5) | (bArr3[1] << 6));
                i10++;
                if (i10 == i8) {
                    i11 += i8;
                    i9 = 0;
                    i10 = 0;
                } else {
                    i9 = 0;
                }
            } else {
                i9 = i13;
            }
        }
        return bArr4;
    }

    public static byte[] color24horizontalScanning(byte[] bArr) {
        int width = NfcManager.getDeviceInfo().getWidth();
        int height = NfcManager.getDeviceInfo().getHeight();
        int i = width % 8;
        int i2 = i != 0 ? 8 - i : 0;
        int length = bArr.length + (i2 * height);
        byte[] bArr2 = new byte[length];
        int i3 = 0;
        for (int i4 = 0; i4 < height; i4++) {
            int i5 = width - 1;
            for (int i6 = i5; i6 >= 0; i6--) {
                int i7 = i3 + 1;
                bArr2[i3] = bArr[(i4 * width) + i6];
                if (i6 == i5) {
                    for (int i8 = 0; i8 < i2; i8++) {
                        bArr2[i7 + i8] = 0;
                    }
                    i7 += i2;
                }
                i3 = i7;
            }
        }
        byte[] bArr3 = new byte[8];
        int i9 = (width + i2) / 8;
        byte[] bArr4 = new byte[height * i9];
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        for (int i13 = 0; i13 < length; i13++) {
            int i14 = i10 + 1;
            bArr3[i10] = bArr2[i13];
            if (i14 == 8) {
                bArr4[i11 + i12] = (byte) ((bArr3[0] << 7) | bArr3[7] | (bArr3[6] << 1) | (bArr3[5] << 2) | (bArr3[4] << 3) | (bArr3[3] << 4) | (bArr3[2] << 5) | (bArr3[1] << 6));
                i11++;
                if (i11 == i9) {
                    i12 += i9;
                    i10 = 0;
                    i11 = 0;
                } else {
                    i10 = 0;
                }
            } else {
                i10 = i14;
            }
        }
        return bArr4;
    }

    public static byte[] Color4VerticalScanning(byte[] bArr) {
        int width = NfcManager.getDeviceInfo().getWidth();
        int height = NfcManager.getDeviceInfo().getHeight();
        int i = height % 8;
        int i2 = i != 0 ? 8 - i : 0;
        int length = bArr.length + (i2 * width);
        byte[] bArr2 = new byte[length];
        int i3 = 0;
        for (int i4 = 0; i4 < width; i4++) {
            for (int i5 = 0; i5 < height; i5++) {
                int i6 = i3 + 1;
                bArr2[i3] = bArr[(i5 * width) + i4];
                if (i5 == height - 1) {
                    for (int i7 = 0; i7 < i2; i7++) {
                        bArr2[i6 + i7] = 0;
                    }
                    i6 += i2;
                }
                i3 = i6;
            }
        }
        byte[] bArr3 = new byte[4];
        int i8 = (height + i2) / 8;
        byte[] bArr4 = new byte[width * i8 * 2];
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        for (int i12 = 0; i12 < length; i12++) {
            int i13 = i9 + 1;
            bArr3[i9] = bArr2[i12];
            if (i13 == 4) {
                bArr4[i10 + i11] = (byte) (bArr3[3] | (bArr3[2] << 2) | (bArr3[1] << 4) | (bArr3[0] << 6));
                i10++;
                if (i10 == i8) {
                    i11 += i8;
                    i9 = 0;
                    i10 = 0;
                } else {
                    i9 = 0;
                }
            } else {
                i9 = i13;
            }
        }
        return bArr4;
    }

    public static byte[] color4horizontalScanning(byte[] bArr) {
        int width = NfcManager.getDeviceInfo().getWidth();
        int height = NfcManager.getDeviceInfo().getHeight();
        int i = width % 8;
        int i2 = i != 0 ? 8 - i : 0;
        int length = bArr.length + (i2 * height);
        byte[] bArr2 = new byte[length];
        int i3 = 0;
        for (int i4 = 0; i4 < height; i4++) {
            int i5 = width - 1;
            for (int i6 = i5; i6 >= 0; i6--) {
                int i7 = i3 + 1;
                bArr2[i3] = bArr[(i4 * width) + i6];
                if (i6 == i5) {
                    for (int i8 = 0; i8 < i2; i8++) {
                        bArr2[i7 + i8] = 0;
                    }
                    i7 += i2;
                }
                i3 = i7;
            }
        }
        byte[] bArr3 = new byte[4];
        int i9 = (width + i2) / 8;
        byte[] bArr4 = new byte[height * i9 * 2];
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        for (int i13 = 0; i13 < length; i13++) {
            int i14 = i10 + 1;
            bArr3[i10] = bArr2[i13];
            if (i14 == 4) {
                bArr4[i11 + i12] = (byte) (bArr3[3] | (bArr3[2] << 2) | (bArr3[1] << 4) | (bArr3[0] << 6));
                i11++;
                if (i11 == i9) {
                    i12 += i9;
                    i10 = 0;
                    i11 = 0;
                } else {
                    i10 = 0;
                }
            } else {
                i10 = i14;
            }
        }
        return bArr4;
    }

    public static byte[] HorizontalScanning(byte[] bArr, int i, int i2, int i3) {
        byte[] bArr2 = new byte[bArr.length];
        Log.d("转换算法", "水平扫描的");
        Log.d("原来的图片宽度", String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(i)));
        Log.d("原来的图片高度", String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(i2)));
        Log.d("实际图片真实有效数据宽度", String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(i3)));
        for (int i4 = 0; i4 < i2; i4++) {
            System.arraycopy(bArr, (i4 * i3) + 62, bArr2, i4 * i, i);
        }
        return bArr2;
    }

    public static byte[] reverseArray(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr2[i3] = byte_change(bArr[((i2 - i3) - 1) + i]);
        }
        return bArr2;
    }

    public static void getColorDataBmp24(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int width = NfcManager.getDeviceInfo().getWidth() * NfcManager.getDeviceInfo().getHeight() * 3;
        int i = 0;
        for (int i2 = 0; i2 < width; i2 += 3) {
            int i3 = i2 + 54;
            if (bArr[i3] == 0 && bArr[i3 + 1] == 0 && bArr[i3 + 2] == 0) {
                bArr2[i] = (byte) (NfcManager.getDeviceInfo().getBlack() > 1 ? 1 : 0);
                bArr3[i] = (byte) (NfcManager.getDeviceInfo().getBlack() % 2 == 1 ? 1 : 0);
            } else if (bArr[i3] == -1 && bArr[i3 + 1] == -1 && bArr[i3 + 2] == -1) {
                bArr2[i] = (byte) (NfcManager.getDeviceInfo().getWhite() > 1 ? 1 : 0);
                bArr3[i] = (byte) (NfcManager.getDeviceInfo().getWhite() % 2 == 1 ? 1 : 0);
            }
            if (NfcManager.getDeviceInfo().getDeviceType() == 1) {
                if (bArr[i3] == 0 && bArr[i3 + 1] == 0 && bArr[i3 + 2] == -1) {
                    bArr2[i] = (byte) (NfcManager.getDeviceInfo().getRed() > 1 ? 1 : 0);
                    bArr3[i] = (byte) (NfcManager.getDeviceInfo().getRed() % 2 != 1 ? 0 : 1);
                }
            } else if (NfcManager.getDeviceInfo().getDeviceType() == 2 && bArr[i3] == 0 && bArr[i3 + 1] == -1 && bArr[i3 + 2] == -1) {
                bArr2[i] = (byte) (NfcManager.getDeviceInfo().getYellow() > 1 ? 1 : 0);
                bArr3[i] = (byte) (NfcManager.getDeviceInfo().getYellow() % 2 != 1 ? 0 : 1);
            }
            i++;
        }
    }

    public static void getColorDataBmp4(byte[] bArr, byte[] bArr2) {
        int i;
        int width = NfcManager.getDeviceInfo().getWidth() * NfcManager.getDeviceInfo().getHeight() * 3;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = width / 2;
            if (i2 >= i) {
                break;
            }
            int i4 = 54 + i2;
            if (bArr[i4] == 0 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 0) {
                bArr2[i3] = (byte) NfcManager.getDeviceInfo().getBlack();
            } else if (bArr[i4] == -1 && bArr[i4 + 1] == -1 && bArr[i4 + 2] == -1) {
                bArr2[i3] = (byte) NfcManager.getDeviceInfo().getWhite();
            }
            if (bArr[i4] == 0 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == -1) {
                bArr2[i3] = (byte) NfcManager.getDeviceInfo().getRed();
            }
            if (bArr[i4] == 0 && bArr[i4 + 1] == -1 && bArr[i4 + 2] == -1) {
                bArr2[i3] = (byte) NfcManager.getDeviceInfo().getYellow();
            }
            i3++;
            i2 += 3;
        }
        int i5 = 54 + i;
        for (int i6 = 0; i6 < i; i6 += 3) {
            int i7 = i5 + i6;
            if (bArr[i7] == 0 && bArr[i7 + 1] == 0 && bArr[i7 + 2] == 0) {
                bArr2[i3] = (byte) NfcManager.getDeviceInfo().getBlack();
            } else if (bArr[i7] == -1 && bArr[i7 + 1] == -1 && bArr[i7 + 2] == -1) {
                bArr2[i3] = (byte) NfcManager.getDeviceInfo().getWhite();
            }
            if (bArr[i7] == 0 && bArr[i7 + 1] == 0 && bArr[i7 + 2] == -1) {
                bArr2[i3] = (byte) NfcManager.getDeviceInfo().getRed();
            }
            if (bArr[i7] == 0 && bArr[i7 + 1] == -1 && bArr[i7 + 2] == -1) {
                bArr2[i3] = (byte) NfcManager.getDeviceInfo().getYellow();
            }
            i3++;
        }
    }
}
