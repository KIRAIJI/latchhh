package com.lazyee.nfc.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import androidx.core.view.MotionEventCompat;
import androidx.exifinterface.media.ExifInterface;
import com.lazyee.nfc.bean.DeviceInfo;
import com.lazyee.nfc.constant.Constants;
import com.lazyee.nfc.manager.NfcManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class BmpUtils {
    private static byte[] addBMPImageHeader(int i) {
        return new byte[]{66, 77, (byte) (i >> 0), (byte) (i >> 8), (byte) (i >> 16), (byte) (i >> 24), 0, 0, 0, 0, 62, 0, 0, 0};
    }

    public static boolean GetBmpFormat(String str, DeviceInfo deviceInfo) {
        try {
            byte[] bArr = new byte[64];
            FileInputStream fileInputStream = new FileInputStream(str);
            fileInputStream.read(bArr, 0, 64);
            fileInputStream.close();
            int i = (bArr[21] << 36) + (bArr[20] << 22) + (bArr[19] << 8) + (bArr[18] & UByte.MAX_VALUE);
            int i2 = (bArr[25] << 36) + (bArr[24] << 22) + (bArr[23] << 8) + (bArr[22] & UByte.MAX_VALUE);
            if (i == deviceInfo.getWidth() && i2 == deviceInfo.getHeight() && bArr[0] == 66 && bArr[1] == 77 && bArr[14] == 40 && bArr[15] == 0 && bArr[16] == 0 && bArr[17] == 0) {
                int i3 = -1;
                if (bArr[28] == 24) {
                    i3 = 3;
                } else if (bArr[28] == 1) {
                    i3 = 2;
                }
                if (deviceInfo.getColorCount() == i3) {
                    return true;
                }
                if (NfcManager.getDeviceInfo().getColorDesc() != null && 4 == deviceInfo.getColorCount() && 14 == deviceInfo.getColorDesc().length()) {
                    if ("4_color Screen".contentEquals(deviceInfo.getColorDesc())) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bitmap toGrayscale(Bitmap bitmap) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return bitmapCreateBitmap;
    }

    public static Bitmap convertGreyImg(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int i3 = (width * i) + i2;
                int i4 = iArr[i3];
                int i5 = (int) ((((double) ((16711680 & i4) >> 16)) * 0.3d) + (((double) ((65280 & i4) >> 8)) * 0.59d) + (((double) (i4 & 255)) * 0.11d));
                iArr[i3] = i5 | (i5 << 16) | (-16777216) | (i5 << 8);
            }
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmapCreateBitmap;
    }

    public static Bitmap convertVerMirro(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f, -1.0f);
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        canvas.drawBitmap(bitmapCreateBitmap2, new Rect(0, 0, bitmapCreateBitmap2.getWidth(), bitmapCreateBitmap2.getHeight()), new Rect(0, 0, width, height), (Paint) null);
        return bitmapCreateBitmap;
    }

    public static Bitmap convertHorMirro(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Matrix matrix = new Matrix();
        matrix.postScale(-1.0f, 1.0f);
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        canvas.drawBitmap(bitmapCreateBitmap2, new Rect(0, 0, bitmapCreateBitmap2.getWidth(), bitmapCreateBitmap2.getHeight()), new Rect(0, 0, width, height), (Paint) null);
        return bitmapCreateBitmap;
    }

    public static Bitmap convertToBlackWhite(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int i3 = (width * i) + i2;
                int i4 = iArr[i3];
                int i5 = (int) ((((double) ((16711680 & i4) >> 16)) * 0.3d) + (((double) ((65280 & i4) >> 8)) * 0.59d) + (((double) (i4 & 255)) * 0.11d));
                iArr[i3] = i5 | (i5 << 16) | (-16777216) | (i5 << 8);
            }
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmapCreateBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmapCreateBitmap;
    }

    public static Bitmap zeroAndOne(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int i = width * height;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = iArr[i2];
            int iRed = Color.red(i3);
            int iGreen = Color.green(i3);
            int iBlue = Color.blue(i3);
            int iAlpha = Color.alpha(i3);
            int i4 = (int) ((((double) iRed) * 0.3d) + (((double) iGreen) * 0.59d) + (((double) iBlue) * 0.11d));
            int i5 = 255;
            if (i4 > 255) {
                i4 = 255;
            }
            if (i4 < 0) {
                i4 = 0;
            }
            if (i4 == 0) {
                i5 = i4;
            }
            iArr2[i2] = Color.argb(iAlpha, i5, i5, i5);
        }
        bitmapCreateBitmap.setPixels(iArr2, 0, width, 0, 0, width, height);
        return bitmapCreateBitmap;
    }

    public static Bitmap invertBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            int i2 = i * width;
            for (int i3 = 0; i3 < width; i3++) {
                int i4 = iArr[i2];
                iArr[i2] = ((255 - (i4 & 255)) & 255) | ((((i4 >> 24) & 255) & 255) << 24) | (((255 - ((i4 >> 16) & 255)) & 255) << 16) | (((255 - ((i4 >> 8) & 255)) & 255) << 8);
                i2++;
            }
        }
        bitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap getViewBitmap(View view) {
        view.clearFocus();
        view.setPressed(false);
        boolean zWillNotCacheDrawing = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);
        int drawingCacheBackgroundColor = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);
        if (drawingCacheBackgroundColor != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null) {
            Log.e("BtPrinter", "failed getViewBitmap(" + view + ")", new RuntimeException());
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawingCache);
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(zWillNotCacheDrawing);
        view.setDrawingCacheBackgroundColor(drawingCacheBackgroundColor);
        return bitmapCreateBitmap;
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(i / width, i2 / height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        Log.d("kkk", ExifInterface.GPS_MEASUREMENT_3D);
        return bitmapCreateBitmap;
    }

    public static Bitmap zoomBitmapEx(Bitmap bitmap, int i, int i2) {
        return ThumbnailUtils.extractThumbnail(bitmap, i, i2);
    }

    public static int getTextWidth(Paint paint, String str) {
        if (str == null || str.length() <= 0) {
            return 0;
        }
        int length = str.length();
        paint.getTextWidths(str, new float[length]);
        int iCeil = 0;
        for (int i = 0; i < length; i++) {
            iCeil += (int) Math.ceil(r2[i]);
        }
        return iCeil;
    }

    public static String GetSdDirPath() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/data";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdir();
        }
        if (file.exists()) {
            String str2 = str + "/com.fm";
            File file2 = new File(str2);
            file2.mkdir();
            if (file2.exists()) {
                return str2;
            }
        }
        return null;
    }

    public static String getImagePath() {
        return GetSdDirPath() + Constants.IMAGE_NAME;
    }

    public static String getImagePath(String str) {
        return GetSdDirPath() + str;
    }

    public static Drawable zoomDrawable(Drawable drawable, int i, int i2) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapDrawableToBitmap = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        matrix.postScale(i / intrinsicWidth, i2 / intrinsicHeight);
        return new BitmapDrawable((Resources) null, Bitmap.createBitmap(bitmapDrawableToBitmap, 0, 0, intrinsicWidth, intrinsicHeight, matrix, true));
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static boolean saveBmpEx(Bitmap bitmap) {
        if (bitmap == null) {
            return false;
        }
        bitmap.getWidth();
        bitmap.getHeight();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        bitmap.getPixels(new int[width * height], 0, width, 0, 0, width, height);
        return true;
    }

    public static void saveBmp(Bitmap bitmap) {
        saveBmp(bitmap, getImagePath());
    }

    public static void saveBmp(Bitmap bitmap, String str) {
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width * 3;
        int i2 = ((width % 4) + i) * height;
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            writeWord(fileOutputStream, 19778);
            writeDword(fileOutputStream, i2 + 54);
            writeWord(fileOutputStream, 0);
            writeWord(fileOutputStream, 0);
            writeDword(fileOutputStream, 54L);
            writeDword(fileOutputStream, 40L);
            writeLong(fileOutputStream, width);
            writeLong(fileOutputStream, height);
            writeWord(fileOutputStream, 1);
            writeWord(fileOutputStream, 24);
            writeDword(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            writeLong(fileOutputStream, 0L);
            writeLong(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            byte[] bArr = new byte[i2];
            int i3 = i + (width % 4);
            int i4 = height - 1;
            int i5 = 0;
            while (i5 < height) {
                int i6 = 0;
                int i7 = 0;
                while (i6 < width) {
                    int pixel = bitmap.getPixel(i6, i5);
                    int i8 = (i4 * i3) + i7;
                    bArr[i8] = (byte) Color.blue(pixel);
                    bArr[i8 + 1] = (byte) Color.green(pixel);
                    bArr[i8 + 2] = (byte) Color.red(pixel);
                    i6++;
                    i7 += 3;
                }
                i5++;
                i4--;
            }
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static String binarization(int i) {
        int i2;
        int i3;
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i4 = 0;
        options.inJustDecodeBounds = false;
        options.inBitmap = null;
        options.inMutable = true;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(getImagePath(), options);
        int width = bitmapDecodeFile.getWidth();
        int height = bitmapDecodeFile.getHeight();
        int i5 = width * 3;
        int i6 = ((width % 4) + i5) * height;
        int[] iArr = new int[width * height];
        int i7 = 0;
        int i8 = 0;
        while (true) {
            i2 = 100;
            if (i7 >= height) {
                break;
            }
            int iRed = 0;
            for (int i9 = 0; i9 < width; i9++) {
                int pixel = bitmapDecodeFile.getPixel(i9, i7);
                iRed += ((((Color.red(pixel) * 30) + (Color.green(pixel) * 59)) + (Color.blue(pixel) * 11)) + 50) / 100;
                if (i7 == 0) {
                    iArr[i8] = iRed;
                } else {
                    iArr[i8] = iArr[i8 - width] + iRed;
                }
                i8++;
            }
            i7++;
        }
        byte[] bArr = new byte[i6];
        int i10 = 0;
        int i11 = 0;
        while (i10 < height) {
            int i12 = i10 - i;
            int i13 = i10 + i;
            if (i12 < 0) {
                i12 = i4;
            }
            if (i13 >= height) {
                i13 = height - 1;
            }
            int i14 = i12 * width;
            int i15 = i13 * width;
            int i16 = (i13 - i12) * i2;
            int i17 = i4;
            while (i17 < width) {
                int i18 = i17 - i;
                int i19 = i17 + i;
                if (i18 < 0) {
                    i18 = i4;
                }
                if (i19 >= width) {
                    i19 = width - 1;
                }
                int i20 = (i19 - i18) * i16;
                int pixel2 = bitmapDecodeFile.getPixel(i17, i10);
                int iBlue = Color.blue(pixel2);
                int iGreen = Color.green(pixel2);
                int iRed2 = Color.red(pixel2);
                int i21 = ((((iRed2 * 30) + (iGreen * 59)) + (iBlue * 11)) + 50) / 100;
                int i22 = ((iArr[i15 + i19] - iArr[i14 + i19]) - iArr[i15 + i18]) - iArr[i14 + i18];
                if (NfcManager.getDeviceInfo().getColorCount() == 2) {
                    if (i21 * i20 < i22 * 95) {
                        bArr[i11 + 0] = 0;
                        bArr[i11 + 1] = 0;
                        bArr[i11 + 2] = 0;
                    } else {
                        bArr[i11 + 0] = -1;
                        bArr[i11 + 1] = -1;
                        bArr[i11 + 2] = -1;
                    }
                    i3 = 100;
                } else {
                    i3 = 100;
                    if (iRed2 > 100 && i21 < 120) {
                        bArr[i11 + 0] = 0;
                        bArr[i11 + 1] = 0;
                        bArr[i11 + 2] = -1;
                    } else if (i21 * i20 < i22 * 95) {
                        bArr[i11 + 0] = 0;
                        bArr[i11 + 1] = 0;
                        bArr[i11 + 2] = 0;
                    } else {
                        bArr[i11 + 0] = -1;
                        bArr[i11 + 1] = -1;
                        bArr[i11 + 2] = -1;
                    }
                }
                i11 += 3;
                i17++;
                i2 = i3;
                i4 = 0;
            }
            i10++;
            i4 = 0;
        }
        try {
            File file = new File(getImagePath("binarization.bmp"));
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(getImagePath("binarization.bmp"));
            writeWord(fileOutputStream, 19778);
            writeDword(fileOutputStream, i6 + 54);
            writeWord(fileOutputStream, 0);
            writeWord(fileOutputStream, 0);
            writeDword(fileOutputStream, 54L);
            long width2 = bitmapDecodeFile.getWidth();
            long height2 = bitmapDecodeFile.getHeight();
            writeDword(fileOutputStream, 40L);
            writeLong(fileOutputStream, width2);
            writeLong(fileOutputStream, height2);
            writeWord(fileOutputStream, 1);
            writeWord(fileOutputStream, 24);
            writeDword(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            writeLong(fileOutputStream, 0L);
            writeLong(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            byte[] bArr2 = new byte[i6];
            int i23 = i5 + (width % 4);
            int i24 = height - 1;
            int i25 = 0;
            int i26 = 0;
            while (i25 < height) {
                int i27 = i26;
                int i28 = 0;
                int i29 = 0;
                while (i28 < width) {
                    int i30 = (i24 * i23) + i29;
                    bArr2[i30] = bArr[i27];
                    bArr2[i30 + 1] = bArr[i27 + 1];
                    bArr2[i30 + 2] = bArr[i27 + 2];
                    i27 += 3;
                    i28++;
                    i29 += 3;
                }
                i25++;
                i24--;
                i26 = i27;
            }
            fileOutputStream.write(bArr2);
            fileOutputStream.flush();
            fileOutputStream.close();
            if (NfcManager.getDeviceInfo().getColorCount() == 2) {
                Convert24bmpToBlackWithebmp(getImagePath("binarization.bmp"), getImagePath(Constants.IMAGE_BLACK_WHITE_NAME), true);
                return getImagePath(Constants.IMAGE_BLACK_WHITE_NAME);
            }
            return getImagePath("binarization.bmp");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveBmp(Bitmap bitmap, String str, byte[] bArr) {
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = ((width * 3) + (width % 4)) * height;
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            writeWord(fileOutputStream, 19778);
            writeDword(fileOutputStream, i + 54);
            writeWord(fileOutputStream, 0);
            writeWord(fileOutputStream, 0);
            writeDword(fileOutputStream, 54L);
            writeDword(fileOutputStream, 40L);
            writeLong(fileOutputStream, width);
            writeLong(fileOutputStream, height);
            writeWord(fileOutputStream, 1);
            writeWord(fileOutputStream, 24);
            writeDword(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            writeLong(fileOutputStream, 0L);
            writeLong(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            writeDword(fileOutputStream, 0L);
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void saveBlackWitheBmp(String str, byte[] bArr) {
        int length = bArr.length + 62;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 62, bArr.length);
        int width = NfcManager.getDeviceInfo().getWidth();
        int height = NfcManager.getDeviceInfo().getHeight();
        bArr2[0] = 66;
        bArr2[1] = 77;
        bArr2[2] = (byte) (length & 255);
        bArr2[3] = (byte) ((length & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr2[4] = (byte) ((length & 16711680) >> 16);
        bArr2[5] = (byte) ((length & (-16777216)) >> 24);
        bArr2[10] = 62;
        bArr2[14] = 40;
        bArr2[18] = (byte) (width & 255);
        bArr2[19] = (byte) ((width & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr2[20] = (byte) ((width & 16711680) >> 16);
        bArr2[21] = (byte) ((width & (-16777216)) >> 24);
        bArr2[22] = (byte) (height & 255);
        bArr2[23] = (byte) ((height & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr2[24] = (byte) ((height & 16711680) >> 16);
        bArr2[25] = (byte) ((height & (-16777216)) >> 24);
        bArr2[26] = 1;
        bArr2[28] = 1;
        bArr2[34] = -32;
        bArr2[35] = 11;
        bArr2[58] = -1;
        bArr2[59] = -1;
        bArr2[60] = -1;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            fileOutputStream.write(bArr2);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeWord(FileOutputStream fileOutputStream, int i) throws IOException {
        fileOutputStream.write(new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255)});
    }

    public static void writeDword(FileOutputStream fileOutputStream, long j) throws IOException {
        fileOutputStream.write(new byte[]{(byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255)});
    }

    public static void writeLong(FileOutputStream fileOutputStream, long j) throws IOException {
        fileOutputStream.write(new byte[]{(byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255)});
    }

    public static byte[] ReadBmp24FileOrg(String str, int i, int i2) {
        int i3 = i % 4;
        int i4 = (i * i2 * 3) + 54 + (i3 != 0 ? i3 * i2 : 0);
        byte[] bArr = new byte[i4];
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            fileInputStream.read(bArr, 0, i4);
            fileInputStream.close();
            if (bArr[0] == 66 && bArr[1] == 77 && bArr[14] == 40 && bArr[15] == 0 && bArr[16] == 0 && bArr[17] == 0) {
                fileInputStream.close();
                return bArr;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] ReadBmp24File(String str, int i, int i2) {
        int i3 = i % 4;
        int i4 = 0;
        int i5 = (i * i2 * 3) + 54;
        int i6 = (i3 != 0 ? i3 * i2 : 0) + i5;
        byte[] bArr = new byte[i6];
        byte[] bArr2 = new byte[i5];
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            fileInputStream.read(bArr, 0, i6);
            fileInputStream.close();
            if (bArr[0] == 66 && bArr[1] == 77 && bArr[14] == 40 && bArr[15] == 0 && bArr[16] == 0 && bArr[17] == 0) {
                for (int i7 = i2 - 1; i7 >= 0; i7--) {
                    int i8 = i * 3;
                    System.arraycopy(bArr, (i7 * i8) + 54 + (i7 * i3), bArr2, (i4 * i8) + 54, i8);
                    i4++;
                }
                fileInputStream.close();
                return bArr2;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean Convert24bmpToBlackWithebmp(String str, String str2, boolean z) {
        int width = NfcManager.getDeviceInfo().getWidth();
        int height = NfcManager.getDeviceInfo().getHeight();
        byte[] bArrReadBmp24FileOrg = ReadBmp24FileOrg(str, width, height);
        if (bArrReadBmp24FileOrg == null) {
            return false;
        }
        int iCeil = (int) Math.ceil(((double) width) / 8.0d);
        int i = iCeil % 4;
        int i2 = i != 0 ? (iCeil - i) + 4 + 0 : iCeil;
        int i3 = (i2 * height) + 62;
        byte[] bArr = new byte[i3];
        getColorDataBmp24(bArrReadBmp24FileOrg, width, height, bArr, i2, iCeil, z);
        bArr[0] = 66;
        bArr[1] = 77;
        bArr[2] = (byte) (i3 & 255);
        bArr[3] = (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr[4] = (byte) ((i3 & 16711680) >> 16);
        bArr[5] = (byte) ((i3 & (-16777216)) >> 24);
        bArr[6] = 0;
        bArr[7] = 0;
        bArr[8] = 0;
        bArr[9] = 0;
        bArr[10] = 62;
        bArr[11] = 0;
        bArr[12] = 0;
        bArr[13] = 0;
        bArr[14] = 40;
        bArr[15] = 0;
        bArr[16] = 0;
        bArr[17] = 0;
        bArr[18] = (byte) (width & 255);
        bArr[19] = (byte) ((width & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr[20] = (byte) ((width & 16711680) >> 16);
        bArr[21] = (byte) ((width & (-16777216)) >> 24);
        bArr[22] = (byte) (height & 255);
        bArr[23] = (byte) ((65280 & height) >> 8);
        bArr[24] = (byte) ((height & 16711680) >> 16);
        bArr[25] = (byte) ((height & (-16777216)) >> 24);
        bArr[26] = 1;
        bArr[27] = 0;
        bArr[28] = 1;
        bArr[29] = 0;
        bArr[30] = 0;
        bArr[31] = 0;
        bArr[32] = 0;
        bArr[33] = 0;
        bArr[34] = -32;
        bArr[35] = 11;
        bArr[36] = 0;
        bArr[37] = 0;
        bArr[38] = 0;
        bArr[39] = 0;
        bArr[40] = 0;
        bArr[41] = 0;
        bArr[42] = 0;
        bArr[43] = 0;
        bArr[44] = 0;
        bArr[45] = 0;
        bArr[46] = 0;
        bArr[47] = 0;
        bArr[48] = 0;
        bArr[49] = 0;
        bArr[50] = 0;
        bArr[51] = 0;
        bArr[52] = 0;
        bArr[53] = 0;
        bArr[54] = 0;
        bArr[55] = 0;
        bArr[56] = 0;
        bArr[57] = 0;
        bArr[58] = -1;
        bArr[59] = -1;
        bArr[60] = -1;
        bArr[61] = 0;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            new File(str).delete();
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void ArrayFillZero(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = 0;
        }
    }

    private static byte GetByteValue(byte[] bArr) {
        return (byte) ((bArr[0] << 7) | bArr[7] | (bArr[6] << 1) | (bArr[5] << 2) | (bArr[4] << 3) | (bArr[3] << 4) | (bArr[2] << 5) | (bArr[1] << 6));
    }

    public static void getColorDataBmp24(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4, boolean z) {
        int i5;
        int i6;
        byte[] bArr3 = new byte[8];
        int i7 = i % 4;
        int i8 = (i * i2 * 3) + (i7 != 0 ? i7 * i2 : 0);
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        while (i9 < i8) {
            if (z) {
                int i14 = i9 + 54;
                i5 = i9;
                if (((int) ((((double) (bArr[i14] & UByte.MAX_VALUE)) * 0.3d) + (((double) (bArr[i14 + 1] & UByte.MAX_VALUE)) * 0.59d) + (((double) (bArr[i14 + 2] & UByte.MAX_VALUE)) * 0.11d))) < 180) {
                    i6 = i10 + 1;
                    bArr3[i10] = (byte) (NfcManager.getDeviceInfo().getWhite() > 0 ? 1 : 0);
                } else {
                    i6 = i10 + 1;
                    bArr3[i10] = (byte) (NfcManager.getDeviceInfo().getBlack() > 0 ? 1 : 0);
                }
            } else {
                i5 = i9;
                int i15 = i5 + 54;
                if (bArr[i15] == -1 && bArr[i15 + 1] == -1 && bArr[i15 + 2] == -1) {
                    i6 = i10 + 1;
                    bArr3[i10] = (byte) (NfcManager.getDeviceInfo().getBlack() > 0 ? 1 : 0);
                } else {
                    i6 = i10 + 1;
                    bArr3[i10] = (byte) (NfcManager.getDeviceInfo().getWhite() > 0 ? 1 : 0);
                }
            }
            i10 = i6;
            i9 = i5 + 3;
            i11++;
            if (i11 == i) {
                i9 += i7;
                bArr2[i12 + 62 + i13] = GetByteValue(bArr3);
                i13 += i3;
                ArrayFillZero(bArr3);
                i10 = 0;
                i11 = 0;
            } else if (i10 == 8) {
                bArr2[i12 + 62 + i13] = GetByteValue(bArr3);
                i12++;
                if (i12 == i4) {
                    i13 += i3;
                    i10 = 0;
                } else {
                    i10 = 0;
                }
            }
            i12 = 0;
        }
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(i / width, i2 / height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    private Bitmap scaleBitmap(Bitmap bitmap, float f) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(f, f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        if (bitmapCreateBitmap.equals(bitmap)) {
            return bitmapCreateBitmap;
        }
        bitmap.recycle();
        return bitmapCreateBitmap;
    }

    private Bitmap cropBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < height) {
            height = width;
        }
        int i = height / 2;
        return Bitmap.createBitmap(bitmap, width / 3, 0, i, (int) (((double) i) / 1.2d), (Matrix) null, false);
    }

    public static Bitmap DrawRectOnBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStrokeWidth(1.0f);
        paint.setColor(-16777216);
        canvas.drawRect(new Rect(1, 1, width - 1, height - 1), paint);
        canvas.save();
        canvas.restore();
        return bitmap;
    }

    private Bitmap rotateBitmap(Bitmap bitmap, float f) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        if (bitmapCreateBitmap.equals(bitmap)) {
            return bitmapCreateBitmap;
        }
        bitmap.recycle();
        return bitmapCreateBitmap;
    }

    private Bitmap skewBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postSkew(-0.6f, -0.3f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        if (bitmapCreateBitmap.equals(bitmap)) {
            return bitmapCreateBitmap;
        }
        bitmap.recycle();
        return bitmapCreateBitmap;
    }

    public static Bitmap convertToSketch(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width * height;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                int i4 = (i2 * width) + i3;
                iArr[i4] = ((Color.red(iArr[i4]) + Color.green(iArr[i4])) + Color.blue(iArr[i4])) / 3;
                iArr2[i4] = 255 - iArr[i4];
            }
        }
        gaussGray(iArr2, 5.0d, 5.0d, width, height);
        for (int i5 = 0; i5 < height; i5++) {
            for (int i6 = 0; i6 < width; i6++) {
                int i7 = (i5 * width) + i6;
                int iMin = Math.min((iArr[i7] << 8) / (256 - iArr2[i7]), 255);
                iArr[i7] = Color.rgb(iMin, iMin, iMin);
            }
        }
        bitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static int gaussGray(int[] iArr, double d, double d2, int i, int i2) {
        int i3;
        int i4;
        double[] dArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int[] iArr5;
        double[] dArr2;
        double[] dArr3;
        double[] dArr4;
        int i5;
        double dAbs;
        int i6 = i;
        int iMax = Math.max(i, i2);
        double[] dArr5 = new double[iMax];
        double[] dArr6 = new double[iMax];
        double[] dArr7 = new double[5];
        double[] dArr8 = new double[5];
        double[] dArr9 = new double[5];
        double[] dArr10 = new double[5];
        double[] dArr11 = new double[5];
        double[] dArr12 = new double[5];
        int[] iArr6 = new int[iMax];
        int[] iArr7 = new int[iMax];
        int[] iArr8 = new int[4];
        int[] iArr9 = new int[4];
        if (d2 > 0.0d) {
            dAbs = Math.abs(d2) + 1.0d;
            int[] iArr10 = iArr7;
            iArr2 = iArr9;
            iArr3 = iArr8;
            int i7 = 4;
            iArr5 = iArr6;
            dArr2 = dArr11;
            dArr3 = dArr9;
            dArr4 = dArr10;
            findConstants(dArr7, dArr8, dArr9, dArr10, dArr11, dArr12, Math.sqrt((-(dAbs * dAbs)) / (Math.log(0.00392156862745098d) * 2.0d)));
            int i8 = 0;
            while (i8 < i6) {
                for (int i9 = 0; i9 < iMax; i9++) {
                    dArr5[i9] = 0.0d;
                    dArr6[i9] = 0.0d;
                }
                int i10 = i2;
                int i11 = 1;
                for (int i12 = 0; i12 < i10; i12++) {
                    iArr5[i12] = iArr[(i12 * i6) + i8];
                }
                int i13 = i10 - 1;
                iArr3[0] = iArr5[0];
                iArr2[0] = iArr5[i13];
                int i14 = i13;
                int i15 = 0;
                int i16 = 0;
                int i17 = 0;
                while (i15 < i10) {
                    int i18 = i15 < i7 ? i15 : i7;
                    int i19 = 0;
                    while (i19 <= i18) {
                        dArr5[i16] = dArr5[i16] + ((dArr7[i19] * ((double) iArr5[i17 - i19])) - (dArr3[i19] * dArr5[i16 - i19]));
                        dArr6[i13] = dArr6[i13] + ((dArr8[i19] * ((double) iArr5[i14 + i19])) - (dArr4[i19] * dArr6[i13 + i19]));
                        i19++;
                        i18 = i18;
                        i14 = i14;
                        i15 = i15;
                        iMax = iMax;
                    }
                    int i20 = iMax;
                    int i21 = i14;
                    int i22 = i15;
                    while (i19 <= 4) {
                        double[] dArr13 = dArr12;
                        dArr5[i16] = dArr5[i16] + ((dArr7[i19] - dArr2[i19]) * ((double) iArr3[0]));
                        dArr6[i13] = dArr6[i13] + ((dArr8[i19] - dArr13[i19]) * ((double) iArr2[0]));
                        i19++;
                        dArr12 = dArr13;
                        i8 = i8;
                    }
                    i17++;
                    i14 = i21 - 1;
                    i16++;
                    i13--;
                    i15 = i22 + 1;
                    i10 = i2;
                    iMax = i20;
                    i7 = 4;
                    i11 = 1;
                }
                int i23 = iMax;
                double[] dArr14 = dArr12;
                int i24 = i8;
                int i25 = i10;
                int[] iArr11 = iArr10;
                transferGaussPixels(dArr5, dArr6, iArr11, i11, i25);
                for (int i26 = 0; i26 < i25; i26++) {
                    iArr[(i26 * i6) + i24] = iArr11[i26];
                }
                i8 = i24 + 1;
                iArr10 = iArr11;
                dArr12 = dArr14;
                iMax = i23;
                i7 = 4;
            }
            i3 = i2;
            i4 = iMax;
            dArr = dArr12;
            iArr4 = iArr10;
            i5 = 1;
        } else {
            i3 = i2;
            i4 = iMax;
            dArr = dArr12;
            iArr2 = iArr9;
            iArr3 = iArr8;
            iArr4 = iArr7;
            iArr5 = iArr6;
            dArr2 = dArr11;
            dArr3 = dArr9;
            dArr4 = dArr10;
            i5 = 1;
            dAbs = d2;
        }
        if (d > 0.0d) {
            double dAbs2 = Math.abs(d) + 1.0d;
            if (dAbs2 != dAbs) {
                findConstants(dArr7, dArr8, dArr3, dArr4, dArr2, dArr, Math.sqrt((-(dAbs2 * dAbs2)) / (Math.log(0.00392156862745098d) * 2.0d)));
            }
            int i27 = 0;
            while (i27 < i3) {
                int i28 = i4;
                for (int i29 = 0; i29 < i28; i29++) {
                    dArr5[i29] = 0.0d;
                    dArr6[i29] = 0.0d;
                }
                for (int i30 = 0; i30 < i6; i30++) {
                    iArr5[i30] = iArr[(i27 * i6) + i30];
                }
                int i31 = i6 - 1;
                iArr3[0] = iArr5[0];
                iArr2[0] = iArr5[i31];
                int i32 = i31;
                int i33 = i32;
                int i34 = 0;
                int i35 = 0;
                int i36 = 0;
                while (i34 < i6) {
                    int i37 = i34 < 4 ? i34 : 4;
                    int i38 = 0;
                    while (i38 <= i37) {
                        dArr5[i35] = dArr5[i35] + ((dArr7[i38] * ((double) iArr5[i36 - i38])) - (dArr3[i38] * dArr5[i35 - i38]));
                        dArr6[i32] = dArr6[i32] + ((dArr8[i38] * ((double) iArr5[i33 + i38])) - (dArr4[i38] * dArr6[i32 + i38]));
                        i38++;
                        i27 = i27;
                        i28 = i28;
                    }
                    int i39 = i27;
                    int i40 = i28;
                    while (i38 <= 4) {
                        dArr5[i35] = dArr5[i35] + ((dArr7[i38] - dArr2[i38]) * ((double) iArr3[0]));
                        dArr6[i32] = dArr6[i32] + ((dArr8[i38] - dArr[i38]) * ((double) iArr2[0]));
                        i38++;
                        dArr7 = dArr7;
                    }
                    i36++;
                    i33--;
                    i35++;
                    i32--;
                    i34++;
                    i6 = i;
                    i27 = i39;
                    i28 = i40;
                    i5 = 1;
                }
                int i41 = i5;
                int i42 = i27;
                i4 = i28;
                double[] dArr15 = dArr7;
                transferGaussPixels(dArr5, dArr6, iArr4, i41, i6);
                for (int i43 = 0; i43 < i6; i43++) {
                    iArr[(i42 * i6) + i43] = iArr4[i43];
                }
                i27 = i42 + 1;
                i5 = i41;
                dArr7 = dArr15;
                i3 = i2;
            }
        }
        return 0;
    }

    private static void transferGaussPixels(double[] dArr, double[] dArr2, int[] iArr, int i, int i2) {
        int i3 = i * i2;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i4 < i3) {
            int i8 = i5 + 1;
            double d = dArr[i5];
            int i9 = i6 + 1;
            double d2 = d + dArr2[i6];
            if (d2 > 255.0d) {
                d2 = 255.0d;
            } else if (d2 < 0.0d) {
                d2 = 0.0d;
            }
            iArr[i7] = (int) d2;
            i4++;
            i7++;
            i6 = i9;
            i5 = i8;
        }
    }

    private static void findConstants(double[] dArr, double[] dArr2, double[] dArr3, double[] dArr4, double[] dArr5, double[] dArr6, double d) {
        double dSqrt = Math.sqrt(6.283186d) * d;
        double d2 = (-1.783d) / d;
        double d3 = (-1.723d) / d;
        double d4 = 0.6318d / d;
        double d5 = 1.997d / d;
        double d6 = 1.6803d / dSqrt;
        double d7 = 3.735d / dSqrt;
        double d8 = (-0.6803d) / dSqrt;
        double d9 = (-0.2598d) / dSqrt;
        double d10 = d6 + d8;
        dArr[0] = d10;
        dArr[1] = (Math.exp(d3) * ((Math.sin(d5) * d9) - ((d8 + (d6 * 2.0d)) * Math.cos(d5)))) + (Math.exp(d2) * ((Math.sin(d4) * d7) - (((d8 * 2.0d) + d6) * Math.cos(d4))));
        double d11 = d2 + d3;
        double dExp = Math.exp(d11) * 2.0d * ((((d10 * Math.cos(d5)) * Math.cos(d4)) - ((Math.cos(d5) * d7) * Math.sin(d4))) - ((Math.cos(d4) * d9) * Math.sin(d5)));
        double d12 = d2 * 2.0d;
        double d13 = d3 * 2.0d;
        dArr[2] = dExp + (Math.exp(d12) * d8) + (Math.exp(d13) * d6);
        double d14 = d3 + d12;
        double dExp2 = Math.exp(d14) * ((d9 * Math.sin(d5)) - (d8 * Math.cos(d5)));
        double d15 = d2 + d13;
        dArr[3] = dExp2 + (Math.exp(d15) * ((d7 * Math.sin(d4)) - (d6 * Math.cos(d4))));
        double d16 = 0.0d;
        dArr[4] = 0.0d;
        dArr3[0] = 0.0d;
        dArr3[1] = ((Math.exp(d3) * (-2.0d)) * Math.cos(d5)) - ((Math.exp(d2) * 2.0d) * Math.cos(d4));
        dArr3[2] = (Math.cos(d5) * 4.0d * Math.cos(d4) * Math.exp(d11)) + Math.exp(d13) + Math.exp(d12);
        dArr3[3] = ((Math.cos(d4) * (-2.0d)) * Math.exp(d15)) - ((Math.cos(d5) * 2.0d) * Math.exp(d14));
        dArr3[4] = Math.exp(d12 + d13);
        for (int i = 0; i <= 4; i++) {
            dArr4[i] = dArr3[i];
        }
        dArr2[0] = 0.0d;
        for (int i2 = 1; i2 <= 4; i2++) {
            dArr2[i2] = dArr[i2] - (dArr3[i2] * dArr[0]);
        }
        double d17 = 0.0d;
        double d18 = 0.0d;
        for (int i3 = 0; i3 <= 4; i3++) {
            d16 += dArr[i3];
            d18 += dArr2[i3];
            d17 += dArr3[i3];
        }
        double d19 = d17 + 1.0d;
        double d20 = d16 / d19;
        double d21 = d18 / d19;
        for (int i4 = 0; i4 <= 4; i4++) {
            dArr5[i4] = dArr3[i4] * d20;
            dArr6[i4] = dArr4[i4] * d21;
        }
    }

    public static Bitmap sharpenImageAmeliorate(Bitmap bitmap) {
        int[] iArr = {-1, -1, -1, -1, 9, -1, -1, -1, -1};
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int[] iArr2 = new int[width * height];
        bitmap.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i = height - 1;
        int i2 = 1;
        int i3 = 1;
        while (i3 < i) {
            int i4 = width - 1;
            int i5 = i2;
            while (i5 < i4) {
                int i6 = 0;
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                int i10 = -1;
                while (i10 <= i2) {
                    int i11 = -1;
                    while (i11 <= i2) {
                        int i12 = iArr2[((i3 + i11) * width) + i5 + i10];
                        int iRed = Color.red(i12);
                        int iGreen = Color.green(i12);
                        int iBlue = Color.blue(i12);
                        i6 += (int) (iRed * iArr[i9] * 0.3f);
                        i7 += (int) (iArr[i9] * iGreen * 0.3f);
                        i8 += (int) (iArr[i9] * iBlue * 0.3f);
                        i9++;
                        i11++;
                        i2 = 1;
                    }
                    i10++;
                    i2 = 1;
                }
                iArr2[(i3 * width) + i5] = Color.argb(255, Math.min(255, Math.max(0, i6)), Math.min(255, Math.max(0, i7)), Math.min(255, Math.max(0, i8)));
                i5++;
                i2 = 1;
            }
            i3++;
            i2 = 1;
        }
        bitmapCreateBitmap.setPixels(iArr2, 0, width, 0, 0, width, height);
        return bitmapCreateBitmap;
    }

    public static void formatBMPBlackWhite(Bitmap bitmap) {
        if (bitmap != null) {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(getImagePath(Constants.IMAGE_BLACK_WHITE_NAME));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] iArr = new int[width * height];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            byte[] bArrAddBMP_RGB_888 = addBMP_RGB_888(iArr, width, height);
            byte[] bArrAddBMPImageHeader = addBMPImageHeader(bArrAddBMP_RGB_888.length + 62);
            byte[] bArrAddBMPImageInfosHeader = addBMPImageInfosHeader(width, height, bArrAddBMP_RGB_888.length);
            byte[] bArrAddBMPImageColorTable = addBMPImageColorTable();
            byte[] bArr = new byte[bArrAddBMP_RGB_888.length + 62];
            System.arraycopy(bArrAddBMPImageHeader, 0, bArr, 0, bArrAddBMPImageHeader.length);
            System.arraycopy(bArrAddBMPImageInfosHeader, 0, bArr, 14, bArrAddBMPImageInfosHeader.length);
            System.arraycopy(bArrAddBMPImageColorTable, 0, bArr, 54, bArrAddBMPImageColorTable.length);
            System.arraycopy(bArrAddBMP_RGB_888, 0, bArr, 62, bArrAddBMP_RGB_888.length);
            try {
                fileOutputStream.write(bArr);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    private static byte[] addBMPImageInfosHeader(int i, int i2, int i3) {
        Log.i("_DETEST_", "size=" + i3);
        return new byte[]{40, 0, 0, 0, (byte) (i >> 0), (byte) (i >> 8), (byte) (i >> 16), (byte) (i >> 24), (byte) (i2 >> 0), (byte) (i2 >> 8), (byte) (i2 >> 16), (byte) (i2 >> 24), 1, 0, 1, 0, 0, 0, 0, 0, (byte) (i3 >> 0), (byte) (i3 >> 8), (byte) (i3 >> 16), (byte) (i3 >> 24), -61, 14, 0, 0, -61, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    private static byte[] addBMPImageColorTable() {
        return new byte[]{-1, -1, -1, 0, 0, 0, 0, 0};
    }

    private static byte[] addBMP_RGB_888(int[] iArr, int i, int i2) {
        int i3;
        int i4 = i * i2;
        int i5 = 3;
        byte[] bArr = new byte[3];
        if (i4 % 8 != 0) {
            i3 = (i4 / 8) + 1;
        } else {
            i3 = i4 / 8;
        }
        int i6 = i3 % 4;
        if (i6 != 0) {
            i3 += i6;
        }
        byte[] bArr2 = new byte[i3];
        int i7 = i4 - 1;
        int i8 = 1;
        int i9 = 0;
        while (i7 >= i) {
            int i10 = i7 - i;
            int i11 = i10 + 1;
            while (i11 <= i7) {
                bArr[0] = (byte) (iArr[i11] >> 0);
                bArr[1] = (byte) (iArr[i11] >> 8);
                bArr[2] = (byte) (iArr[i11] >> 16);
                String str = "";
                int i12 = 0;
                while (i12 < i5) {
                    String hexString = Integer.toHexString(bArr[i12] & UByte.MAX_VALUE);
                    if (hexString.length() == 1) {
                        hexString = "0" + hexString;
                    }
                    str = str + hexString;
                    i12++;
                    i5 = 3;
                }
                if (i8 > 8) {
                    i9++;
                    i8 = 1;
                }
                if (!str.equals("ffffff")) {
                    bArr2[i9] = (byte) (bArr2[i9] | (1 << (8 - i8)));
                }
                i8++;
                i11++;
                i5 = 3;
            }
            i7 = i10;
        }
        return bArr2;
    }

    public static void adaptiveThreshold(Bitmap bitmap, double d) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width / 8;
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
            }
        }
    }
}
