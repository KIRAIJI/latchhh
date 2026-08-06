package com.theartofdev.edmodo.cropper;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import androidx.exifinterface.media.ExifInterface;
import com.luck.picture.lib.config.PictureMimeType;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/* JADX INFO: loaded from: classes.dex */
final class BitmapUtils {
    private static int mMaxTextureSize;
    static Pair<String, WeakReference<Bitmap>> mStateBitmap;
    static final Rect EMPTY_RECT = new Rect();
    static final RectF EMPTY_RECT_F = new RectF();
    static final RectF RECT = new RectF();
    static final float[] POINTS = new float[6];
    static final float[] POINTS2 = new float[6];

    BitmapUtils() {
    }

    static RotateBitmapResult rotateBitmapByExif(Bitmap bitmap, Context context, Uri uri) {
        ExifInterface exifInterface = null;
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream != null) {
                ExifInterface exifInterface2 = new ExifInterface(inputStreamOpenInputStream);
                try {
                    inputStreamOpenInputStream.close();
                } catch (Exception unused) {
                }
                exifInterface = exifInterface2;
            }
        } catch (Exception unused2) {
        }
        return exifInterface != null ? rotateBitmapByExif(bitmap, exifInterface) : new RotateBitmapResult(bitmap, 0);
    }

    static RotateBitmapResult rotateBitmapByExif(Bitmap bitmap, ExifInterface exifInterface) {
        int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        return new RotateBitmapResult(bitmap, attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? 0 : 270 : 90 : 180);
    }

    static BitmapSampled decodeSampledBitmap(Context context, Uri uri, int i, int i2) throws Throwable {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            BitmapFactory.Options optionsDecodeImageForOption = decodeImageForOption(contentResolver, uri);
            if (optionsDecodeImageForOption.outWidth == -1 && optionsDecodeImageForOption.outHeight == -1) {
                throw new RuntimeException("File is not a picture");
            }
            optionsDecodeImageForOption.inSampleSize = Math.max(calculateInSampleSizeByReqestedSize(optionsDecodeImageForOption.outWidth, optionsDecodeImageForOption.outHeight, i, i2), calculateInSampleSizeByMaxTextureSize(optionsDecodeImageForOption.outWidth, optionsDecodeImageForOption.outHeight));
            return new BitmapSampled(decodeImage(contentResolver, uri, optionsDecodeImageForOption), optionsDecodeImageForOption.inSampleSize);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load sampled bitmap: " + uri + "\r\n" + e.getMessage(), e);
        }
    }

    static BitmapSampled cropBitmapObjectHandleOOM(Bitmap bitmap, float[] fArr, int i, boolean z, int i2, int i3, boolean z2, boolean z3) {
        int i4 = 1;
        do {
            try {
                return new BitmapSampled(cropBitmapObjectWithScale(bitmap, fArr, i, z, i2, i3, 1.0f / i4, z2, z3), i4);
            } catch (OutOfMemoryError e) {
                i4 *= 2;
            }
        } while (i4 <= 8);
        throw e;
    }

    private static Bitmap cropBitmapObjectWithScale(Bitmap bitmap, float[] fArr, int i, boolean z, int i2, int i3, float f, boolean z2, boolean z3) {
        float f2 = f;
        Rect rectFromPoints = getRectFromPoints(fArr, bitmap.getWidth(), bitmap.getHeight(), z, i2, i3);
        Matrix matrix = new Matrix();
        matrix.setRotate(i, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        float f3 = z2 ? -f2 : f2;
        if (z3) {
            f2 = -f2;
        }
        matrix.postScale(f3, f2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, rectFromPoints.left, rectFromPoints.top, rectFromPoints.width(), rectFromPoints.height(), matrix, true);
        if (bitmapCreateBitmap == bitmap) {
            bitmapCreateBitmap = bitmap.copy(bitmap.getConfig(), false);
        }
        return i % 90 != 0 ? cropForRotatedImage(bitmapCreateBitmap, fArr, rectFromPoints, i, z, i2, i3) : bitmapCreateBitmap;
    }

    static BitmapSampled cropBitmap(Context context, Uri uri, float[] fArr, int i, int i2, int i3, boolean z, int i4, int i5, int i6, int i7, boolean z2, boolean z3) {
        int i8 = 1;
        do {
            try {
                return cropBitmap(context, uri, fArr, i, i2, i3, z, i4, i5, i6, i7, z2, z3, i8);
            } catch (OutOfMemoryError e) {
                i8 *= 2;
            }
        } while (i8 <= 16);
        throw new RuntimeException("Failed to handle OOM by sampling (" + i8 + "): " + uri + "\r\n" + e.getMessage(), e);
    }

    static float getRectLeft(float[] fArr) {
        return Math.min(Math.min(Math.min(fArr[0], fArr[2]), fArr[4]), fArr[6]);
    }

    static float getRectTop(float[] fArr) {
        return Math.min(Math.min(Math.min(fArr[1], fArr[3]), fArr[5]), fArr[7]);
    }

    static float getRectRight(float[] fArr) {
        return Math.max(Math.max(Math.max(fArr[0], fArr[2]), fArr[4]), fArr[6]);
    }

    static float getRectBottom(float[] fArr) {
        return Math.max(Math.max(Math.max(fArr[1], fArr[3]), fArr[5]), fArr[7]);
    }

    static float getRectWidth(float[] fArr) {
        return getRectRight(fArr) - getRectLeft(fArr);
    }

    static float getRectHeight(float[] fArr) {
        return getRectBottom(fArr) - getRectTop(fArr);
    }

    static float getRectCenterX(float[] fArr) {
        return (getRectRight(fArr) + getRectLeft(fArr)) / 2.0f;
    }

    static float getRectCenterY(float[] fArr) {
        return (getRectBottom(fArr) + getRectTop(fArr)) / 2.0f;
    }

    static Rect getRectFromPoints(float[] fArr, int i, int i2, boolean z, int i3, int i4) {
        Rect rect = new Rect(Math.round(Math.max(0.0f, getRectLeft(fArr))), Math.round(Math.max(0.0f, getRectTop(fArr))), Math.round(Math.min(i, getRectRight(fArr))), Math.round(Math.min(i2, getRectBottom(fArr))));
        if (z) {
            fixRectForAspectRatio(rect, i3, i4);
        }
        return rect;
    }

    private static void fixRectForAspectRatio(Rect rect, int i, int i2) {
        if (i != i2 || rect.width() == rect.height()) {
            return;
        }
        if (rect.height() > rect.width()) {
            rect.bottom -= rect.height() - rect.width();
        } else {
            rect.right -= rect.width() - rect.height();
        }
    }

    static Uri writeTempStateStoreBitmap(Context context, Bitmap bitmap, Uri uri) {
        boolean z = true;
        try {
            if (uri == null) {
                uri = Uri.fromFile(File.createTempFile("aic_state_store_temp", PictureMimeType.JPG, context.getCacheDir()));
            } else if (new File(uri.getPath()).exists()) {
                z = false;
            }
            if (z) {
                writeBitmapToUri(context, bitmap, uri, Bitmap.CompressFormat.JPEG, 95);
            }
            return uri;
        } catch (Exception e) {
            Log.w("AIC", "Failed to write bitmap to temp file for image-cropper save instance state", e);
            return null;
        }
    }

    static void writeBitmapToUri(Context context, Bitmap bitmap, Uri uri, Bitmap.CompressFormat compressFormat, int i) throws FileNotFoundException {
        OutputStream outputStreamOpenOutputStream = null;
        try {
            outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(compressFormat, i, outputStreamOpenOutputStream);
        } finally {
            closeSafe(outputStreamOpenOutputStream);
        }
    }

    static Bitmap resizeBitmap(Bitmap bitmap, int i, int i2, CropImageView.RequestSizeOptions requestSizeOptions) {
        if (i > 0 && i2 > 0) {
            try {
                if (requestSizeOptions == CropImageView.RequestSizeOptions.RESIZE_FIT || requestSizeOptions == CropImageView.RequestSizeOptions.RESIZE_INSIDE || requestSizeOptions == CropImageView.RequestSizeOptions.RESIZE_EXACT) {
                    Bitmap bitmapCreateScaledBitmap = null;
                    if (requestSizeOptions == CropImageView.RequestSizeOptions.RESIZE_EXACT) {
                        bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i2, false);
                    } else {
                        float width = bitmap.getWidth();
                        float height = bitmap.getHeight();
                        float fMax = Math.max(width / i, height / i2);
                        if (fMax > 1.0f || requestSizeOptions == CropImageView.RequestSizeOptions.RESIZE_FIT) {
                            bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (width / fMax), (int) (height / fMax), false);
                        }
                    }
                    if (bitmapCreateScaledBitmap != null) {
                        if (bitmapCreateScaledBitmap != bitmap) {
                            bitmap.recycle();
                        }
                        return bitmapCreateScaledBitmap;
                    }
                }
            } catch (Exception e) {
                Log.w("AIC", "Failed to resize cropped image, return bitmap before resize", e);
            }
        }
        return bitmap;
    }

    private static BitmapSampled cropBitmap(Context context, Uri uri, float[] fArr, int i, int i2, int i3, boolean z, int i4, int i5, int i6, int i7, boolean z2, boolean z3, int i8) throws Throwable {
        Rect rectFromPoints = getRectFromPoints(fArr, i2, i3, z, i4, i5);
        int iWidth = i6 > 0 ? i6 : rectFromPoints.width();
        int iHeight = i7 > 0 ? i7 : rectFromPoints.height();
        Bitmap bitmap = null;
        int i9 = 1;
        try {
            BitmapSampled bitmapSampledDecodeSampledBitmapRegion = decodeSampledBitmapRegion(context, uri, rectFromPoints, iWidth, iHeight, i8);
            bitmap = bitmapSampledDecodeSampledBitmapRegion.bitmap;
            i9 = bitmapSampledDecodeSampledBitmapRegion.sampleSize;
        } catch (Exception unused) {
        }
        if (bitmap != null) {
            try {
                Bitmap bitmapRotateAndFlipBitmapInt = rotateAndFlipBitmapInt(bitmap, i, z2, z3);
                try {
                    if (i % 90 != 0) {
                        bitmapRotateAndFlipBitmapInt = cropForRotatedImage(bitmapRotateAndFlipBitmapInt, fArr, rectFromPoints, i, z, i4, i5);
                    }
                    return new BitmapSampled(bitmapRotateAndFlipBitmapInt, i9);
                } catch (OutOfMemoryError e) {
                    e = e;
                    bitmap = bitmapRotateAndFlipBitmapInt;
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    throw e;
                }
            } catch (OutOfMemoryError e2) {
                e = e2;
            }
        } else {
            return cropBitmap(context, uri, fArr, i, z, i4, i5, i8, rectFromPoints, iWidth, iHeight, z2, z3);
        }
    }

    private static BitmapSampled cropBitmap(Context context, Uri uri, float[] fArr, int i, boolean z, int i2, int i3, int i4, Rect rect, int i5, int i6, boolean z2, boolean z3) {
        Bitmap bitmapCropBitmapObjectWithScale = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int iCalculateInSampleSizeByReqestedSize = calculateInSampleSizeByReqestedSize(rect.width(), rect.height(), i5, i6) * i4;
            options.inSampleSize = iCalculateInSampleSizeByReqestedSize;
            Bitmap bitmapDecodeImage = decodeImage(context.getContentResolver(), uri, options);
            if (bitmapDecodeImage != null) {
                try {
                    int length = fArr.length;
                    float[] fArr2 = new float[length];
                    System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                    for (int i7 = 0; i7 < length; i7++) {
                        fArr2[i7] = fArr2[i7] / options.inSampleSize;
                    }
                    bitmapCropBitmapObjectWithScale = cropBitmapObjectWithScale(bitmapDecodeImage, fArr2, i, z, i2, i3, 1.0f, z2, z3);
                    if (bitmapCropBitmapObjectWithScale != bitmapDecodeImage) {
                        bitmapDecodeImage.recycle();
                    }
                } catch (Throwable th) {
                    if (bitmapDecodeImage != null) {
                        bitmapDecodeImage.recycle();
                    }
                    throw th;
                }
            }
            return new BitmapSampled(bitmapCropBitmapObjectWithScale, iCalculateInSampleSizeByReqestedSize);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load sampled bitmap: " + uri + "\r\n" + e.getMessage(), e);
        } catch (OutOfMemoryError e2) {
            if (0 != 0) {
                bitmapCropBitmapObjectWithScale.recycle();
            }
            throw e2;
        }
    }

    private static BitmapFactory.Options decodeImageForOption(ContentResolver contentResolver, Uri uri) throws Throwable {
        InputStream inputStreamOpenInputStream;
        try {
            inputStreamOpenInputStream = contentResolver.openInputStream(uri);
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStreamOpenInputStream, EMPTY_RECT, options);
                options.inJustDecodeBounds = false;
                closeSafe(inputStreamOpenInputStream);
                return options;
            } catch (Throwable th) {
                th = th;
                closeSafe(inputStreamOpenInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStreamOpenInputStream = null;
        }
    }

    private static Bitmap decodeImage(ContentResolver contentResolver, Uri uri, BitmapFactory.Options options) throws FileNotFoundException {
        do {
            InputStream inputStreamOpenInputStream = null;
            try {
                try {
                    inputStreamOpenInputStream = contentResolver.openInputStream(uri);
                    return BitmapFactory.decodeStream(inputStreamOpenInputStream, EMPTY_RECT, options);
                } catch (OutOfMemoryError unused) {
                    options.inSampleSize *= 2;
                    closeSafe(inputStreamOpenInputStream);
                }
            } finally {
                closeSafe(inputStreamOpenInputStream);
            }
        } while (options.inSampleSize <= 512);
        throw new RuntimeException("Failed to decode image: " + uri);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.theartofdev.edmodo.cropper.BitmapUtils.BitmapSampled decodeSampledBitmapRegion(android.content.Context r4, android.net.Uri r5, android.graphics.Rect r6, int r7, int r8, int r9) throws java.lang.Throwable {
        /*
            r0 = 0
            android.graphics.BitmapFactory$Options r1 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L60
            r1.<init>()     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L60
            int r2 = r6.width()     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L60
            int r3 = r6.height()     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L60
            int r7 = calculateInSampleSizeByReqestedSize(r2, r3, r7, r8)     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L60
            int r9 = r9 * r7
            r1.inSampleSize = r9     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L60
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L60
            java.io.InputStream r4 = r4.openInputStream(r5)     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L60
            r7 = 0
            android.graphics.BitmapRegionDecoder r7 = android.graphics.BitmapRegionDecoder.newInstance(r4, r7)     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L59
        L22:
            com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled r8 = new com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38 java.lang.OutOfMemoryError -> L3a
            android.graphics.Bitmap r9 = r7.decodeRegion(r6, r1)     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38 java.lang.OutOfMemoryError -> L3a
            int r2 = r1.inSampleSize     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38 java.lang.OutOfMemoryError -> L3a
            r8.<init>(r9, r2)     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38 java.lang.OutOfMemoryError -> L3a
            closeSafe(r4)
            if (r7 == 0) goto L35
            r7.recycle()
        L35:
            return r8
        L36:
            r5 = move-exception
            goto L57
        L38:
            r6 = move-exception
            goto L5b
        L3a:
            int r8 = r1.inSampleSize     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38
            int r8 = r8 * 2
            r1.inSampleSize = r8     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38
            int r8 = r1.inSampleSize     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38
            r9 = 512(0x200, float:7.175E-43)
            if (r8 <= r9) goto L22
            closeSafe(r4)
            if (r7 == 0) goto L4e
            r7.recycle()
        L4e:
            com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled r4 = new com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled
            r5 = 1
            r4.<init>(r0, r5)
            return r4
        L55:
            r5 = move-exception
            r7 = r0
        L57:
            r0 = r4
            goto L86
        L59:
            r6 = move-exception
            r7 = r0
        L5b:
            r0 = r4
            goto L62
        L5d:
            r5 = move-exception
            r7 = r0
            goto L86
        L60:
            r6 = move-exception
            r7 = r0
        L62:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L85
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L85
            r8.<init>()     // Catch: java.lang.Throwable -> L85
            java.lang.String r9 = "Failed to load sampled bitmap: "
            r8.append(r9)     // Catch: java.lang.Throwable -> L85
            r8.append(r5)     // Catch: java.lang.Throwable -> L85
            java.lang.String r5 = "\r\n"
            r8.append(r5)     // Catch: java.lang.Throwable -> L85
            java.lang.String r5 = r6.getMessage()     // Catch: java.lang.Throwable -> L85
            r8.append(r5)     // Catch: java.lang.Throwable -> L85
            java.lang.String r5 = r8.toString()     // Catch: java.lang.Throwable -> L85
            r4.<init>(r5, r6)     // Catch: java.lang.Throwable -> L85
            throw r4     // Catch: java.lang.Throwable -> L85
        L85:
            r5 = move-exception
        L86:
            closeSafe(r0)
            if (r7 == 0) goto L8e
            r7.recycle()
        L8e:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.theartofdev.edmodo.cropper.BitmapUtils.decodeSampledBitmapRegion(android.content.Context, android.net.Uri, android.graphics.Rect, int, int, int):com.theartofdev.edmodo.cropper.BitmapUtils$BitmapSampled");
    }

    private static Bitmap cropForRotatedImage(Bitmap bitmap, float[] fArr, Rect rect, int i, boolean z, int i2, int i3) {
        int iAbs;
        int iAbs2;
        int iAbs3;
        if (i % 90 == 0) {
            return bitmap;
        }
        double radians = Math.toRadians(i);
        int i4 = (i < 90 || (i > 180 && i < 270)) ? rect.left : rect.right;
        int iAbs4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= fArr.length) {
                iAbs = 0;
                iAbs2 = 0;
                iAbs3 = 0;
                break;
            }
            if (fArr[i5] >= i4 - 1 && fArr[i5] <= i4 + 1) {
                int i6 = i5 + 1;
                iAbs4 = (int) Math.abs(Math.sin(radians) * ((double) (rect.bottom - fArr[i6])));
                iAbs2 = (int) Math.abs(Math.cos(radians) * ((double) (fArr[i6] - rect.top)));
                iAbs3 = (int) Math.abs(((double) (fArr[i6] - rect.top)) / Math.sin(radians));
                iAbs = (int) Math.abs(((double) (rect.bottom - fArr[i6])) / Math.cos(radians));
                break;
            }
            i5 += 2;
        }
        rect.set(iAbs4, iAbs2, iAbs3 + iAbs4, iAbs + iAbs2);
        if (z) {
            fixRectForAspectRatio(rect, i2, i3);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
        if (bitmap != bitmapCreateBitmap) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    private static int calculateInSampleSizeByReqestedSize(int i, int i2, int i3, int i4) {
        int i5 = 1;
        if (i2 > i4 || i > i3) {
            while ((i2 / 2) / i5 > i4 && (i / 2) / i5 > i3) {
                i5 *= 2;
            }
        }
        return i5;
    }

    private static int calculateInSampleSizeByMaxTextureSize(int i, int i2) {
        if (mMaxTextureSize == 0) {
            mMaxTextureSize = getMaxTextureSize();
        }
        int i3 = 1;
        if (mMaxTextureSize > 0) {
            while (true) {
                int i4 = i2 / i3;
                int i5 = mMaxTextureSize;
                if (i4 <= i5 && i / i3 <= i5) {
                    break;
                }
                i3 *= 2;
            }
        }
        return i3;
    }

    private static Bitmap rotateAndFlipBitmapInt(Bitmap bitmap, int i, boolean z, boolean z2) {
        if (i <= 0 && !z && !z2) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(i);
        matrix.postScale(z ? -1.0f : 1.0f, z2 ? -1.0f : 1.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (bitmapCreateBitmap != bitmap) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    private static int getMaxTextureSize() {
        try {
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            egl10.eglInitialize(eGLDisplayEglGetDisplay, new int[2]);
            int[] iArr = new int[1];
            egl10.eglGetConfigs(eGLDisplayEglGetDisplay, null, 0, iArr);
            EGLConfig[] eGLConfigArr = new EGLConfig[iArr[0]];
            egl10.eglGetConfigs(eGLDisplayEglGetDisplay, eGLConfigArr, iArr[0], iArr);
            int[] iArr2 = new int[1];
            int i = 0;
            for (int i2 = 0; i2 < iArr[0]; i2++) {
                egl10.eglGetConfigAttrib(eGLDisplayEglGetDisplay, eGLConfigArr[i2], 12332, iArr2);
                if (i < iArr2[0]) {
                    i = iArr2[0];
                }
            }
            egl10.eglTerminate(eGLDisplayEglGetDisplay);
            return Math.max(i, 2048);
        } catch (Exception unused) {
            return 2048;
        }
    }

    private static void closeSafe(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    static final class BitmapSampled {
        public final Bitmap bitmap;
        final int sampleSize;

        BitmapSampled(Bitmap bitmap, int i) {
            this.bitmap = bitmap;
            this.sampleSize = i;
        }
    }

    static final class RotateBitmapResult {
        public final Bitmap bitmap;
        final int degrees;

        RotateBitmapResult(Bitmap bitmap, int i) {
            this.bitmap = bitmap;
            this.degrees = i;
        }
    }
}
