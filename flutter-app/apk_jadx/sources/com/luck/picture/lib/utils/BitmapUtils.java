package com.luck.picture.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import com.luck.picture.lib.basic.PictureContentResolver;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class BitmapUtils {
    private static final int ARGB_8888_MEMORY_BYTE = 4;
    private static final int MAX_BITMAP_SIZE = 104857600;

    /* JADX WARN: Removed duplicated region for block: B:60:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void rotateImage(android.content.Context r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            r0 = 0
            int r1 = readPictureDegree(r6, r7)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            if (r1 <= 0) goto L87
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            r2.<init>()     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            r3 = 1
            r2.inJustDecodeBounds = r3     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            boolean r3 = com.luck.picture.lib.config.PictureMimeType.isContent(r7)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            if (r3 == 0) goto L21
            android.net.Uri r3 = android.net.Uri.parse(r7)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            java.io.InputStream r3 = com.luck.picture.lib.basic.PictureContentResolver.openInputStream(r6, r3)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            android.graphics.BitmapFactory.decodeStream(r3, r0, r2)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            goto L25
        L21:
            android.graphics.BitmapFactory.decodeFile(r7, r2)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            r3 = r0
        L25:
            int r4 = r2.outWidth     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            int r5 = r2.outHeight     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            int r4 = computeSize(r4, r5)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            r2.inSampleSize = r4     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            r4 = 0
            r2.inJustDecodeBounds = r4     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            boolean r4 = com.luck.picture.lib.config.PictureMimeType.isContent(r7)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            if (r4 == 0) goto L45
            android.net.Uri r4 = android.net.Uri.parse(r7)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            java.io.InputStream r3 = com.luck.picture.lib.basic.PictureContentResolver.openInputStream(r6, r4)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r3, r0, r2)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
            goto L49
        L45:
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeFile(r7, r2)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L82
        L49:
            if (r2 == 0) goto L79
            android.graphics.Bitmap r1 = rotatingImage(r2, r1)     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L75
            boolean r2 = com.luck.picture.lib.config.PictureMimeType.isContent(r7)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            if (r2 == 0) goto L60
            android.net.Uri r7 = android.net.Uri.parse(r7)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.io.OutputStream r6 = com.luck.picture.lib.basic.PictureContentResolver.openOutputStream(r6, r7)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.io.FileOutputStream r6 = (java.io.FileOutputStream) r6     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            goto L65
        L60:
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
        L65:
            r0 = r6
            saveBitmapFile(r1, r0)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r6 = r0
            goto L7b
        L6b:
            r6 = move-exception
            r7 = r0
            goto L80
        L6e:
            r6 = move-exception
            r7 = r0
            goto L85
        L71:
            r6 = move-exception
            r7 = r0
            r1 = r2
            goto L80
        L75:
            r6 = move-exception
            r7 = r0
            r1 = r2
            goto L85
        L79:
            r6 = r0
            r1 = r2
        L7b:
            r0 = r3
            goto L89
        L7d:
            r6 = move-exception
            r7 = r0
            r1 = r7
        L80:
            r0 = r3
            goto Lb6
        L82:
            r6 = move-exception
            r7 = r0
            r1 = r7
        L85:
            r0 = r3
            goto La2
        L87:
            r6 = r0
            r1 = r6
        L89:
            com.luck.picture.lib.utils.PictureFileUtils.close(r0)
            com.luck.picture.lib.utils.PictureFileUtils.close(r6)
            if (r1 == 0) goto Lb4
            boolean r6 = r1.isRecycled()
            if (r6 != 0) goto Lb4
        L97:
            r1.recycle()
            goto Lb4
        L9b:
            r6 = move-exception
            r7 = r0
            r1 = r7
            goto Lb6
        L9f:
            r6 = move-exception
            r7 = r0
            r1 = r7
        La2:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> Lb5
            com.luck.picture.lib.utils.PictureFileUtils.close(r0)
            com.luck.picture.lib.utils.PictureFileUtils.close(r7)
            if (r1 == 0) goto Lb4
            boolean r6 = r1.isRecycled()
            if (r6 != 0) goto Lb4
            goto L97
        Lb4:
            return
        Lb5:
            r6 = move-exception
        Lb6:
            com.luck.picture.lib.utils.PictureFileUtils.close(r0)
            com.luck.picture.lib.utils.PictureFileUtils.close(r7)
            if (r1 == 0) goto Lc7
            boolean r7 = r1.isRecycled()
            if (r7 != 0) goto Lc7
            r1.recycle()
        Lc7:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.utils.BitmapUtils.rotateImage(android.content.Context, java.lang.String):void");
    }

    public static Bitmap rotatingImage(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static void saveBitmapFile(Bitmap bitmap, FileOutputStream fileOutputStream) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
            PictureFileUtils.close(fileOutputStream);
            PictureFileUtils.close(byteArrayOutputStream);
        } catch (Exception e2) {
            e = e2;
            byteArrayOutputStream2 = byteArrayOutputStream;
            e.printStackTrace();
            PictureFileUtils.close(fileOutputStream);
            PictureFileUtils.close(byteArrayOutputStream2);
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream2 = byteArrayOutputStream;
            PictureFileUtils.close(fileOutputStream);
            PictureFileUtils.close(byteArrayOutputStream2);
            throw th;
        }
    }

    public static int readPictureDegree(Context context, String str) {
        ExifInterface exifInterface;
        int i;
        InputStream inputStreamOpenInputStream = null;
        try {
            if (PictureMimeType.isContent(str)) {
                inputStreamOpenInputStream = PictureContentResolver.openInputStream(context, Uri.parse(str));
                exifInterface = new ExifInterface(inputStreamOpenInputStream);
            } else {
                exifInterface = new ExifInterface(str);
            }
            int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                i = 180;
            } else if (attributeInt == 6) {
                i = 90;
            } else {
                if (attributeInt != 8) {
                    return 0;
                }
                i = 270;
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            PictureFileUtils.close(inputStreamOpenInputStream);
        }
    }

    public static int[] getMaxImageSize(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return new int[]{-1, -1};
        }
        int iComputeSize = computeSize(i, i2);
        long totalMemory = getTotalMemory();
        int i3 = -1;
        boolean z = false;
        int i4 = -1;
        while (!z) {
            i3 = i / iComputeSize;
            i4 = i2 / iComputeSize;
            if (i3 * i4 * 4 > totalMemory) {
                iComputeSize *= 2;
            } else {
                z = true;
            }
        }
        return new int[]{i3, i4};
    }

    public static long getTotalMemory() {
        long j = Runtime.getRuntime().totalMemory();
        if (j > 104857600) {
            return 104857600L;
        }
        return j;
    }

    public static int computeSize(int i, int i2) {
        if (i % 2 == 1) {
            i++;
        }
        if (i2 % 2 == 1) {
            i2++;
        }
        int iMax = Math.max(i, i2);
        float fMin = Math.min(i, i2) / iMax;
        if (fMin > 1.0f || fMin <= 0.5625d) {
            double d = fMin;
            if (d <= 0.5625d && d > 0.5d) {
                int i3 = iMax / 1280;
                if (i3 == 0) {
                    return 1;
                }
                return i3;
            }
            return (int) Math.ceil(((double) iMax) / (1280.0d / d));
        }
        if (iMax < 1664) {
            return 1;
        }
        if (iMax < 4990) {
            return 2;
        }
        if (iMax <= 4990 || iMax >= 10240) {
            return iMax / 1280;
        }
        return 4;
    }
}
