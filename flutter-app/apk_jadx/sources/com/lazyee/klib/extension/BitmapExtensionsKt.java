package com.lazyee.klib.extension;

import android.graphics.Bitmap;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BitmapExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a:\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u001c\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a0\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u001c\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a0\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u001e\u0010\r\u001a\u00020\u000e*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u000f\u001a\u00020\u0006¨\u0006\u0010"}, d2 = {"save", "", "Landroid/graphics/Bitmap;", "file", "Ljava/io/File;", "targetWidth", "", "targetHeight", "format", "Landroid/graphics/Bitmap$CompressFormat;", "quality", "saveJPEGFile", "savePNGFile", "toBase64String", "", "flags", "library_release"}, k = 2, mv = {1, 4, 2})
public final class BitmapExtensionsKt {
    public static /* synthetic */ void savePNGFile$default(Bitmap bitmap, File file, int i, int i2, int i3, int i4, Object obj) throws IOException {
        if ((i4 & 2) != 0) {
            i = -1;
        }
        if ((i4 & 4) != 0) {
            i2 = -1;
        }
        if ((i4 & 8) != 0) {
            i3 = 100;
        }
        savePNGFile(bitmap, file, i, i2, i3);
    }

    public static final void savePNGFile(Bitmap savePNGFile, File file, int i, int i2, int i3) throws IOException {
        Intrinsics.checkNotNullParameter(savePNGFile, "$this$savePNGFile");
        Intrinsics.checkNotNullParameter(file, "file");
        save(savePNGFile, file, i, i2, Bitmap.CompressFormat.PNG, i3);
    }

    public static /* synthetic */ void savePNGFile$default(Bitmap bitmap, File file, int i, int i2, Object obj) throws IOException {
        if ((i2 & 2) != 0) {
            i = 100;
        }
        savePNGFile(bitmap, file, i);
    }

    public static final void savePNGFile(Bitmap savePNGFile, File file, int i) throws IOException {
        Intrinsics.checkNotNullParameter(savePNGFile, "$this$savePNGFile");
        Intrinsics.checkNotNullParameter(file, "file");
        save(savePNGFile, file, -1, -1, Bitmap.CompressFormat.PNG, i);
    }

    public static /* synthetic */ void saveJPEGFile$default(Bitmap bitmap, File file, int i, int i2, int i3, int i4, Object obj) throws IOException {
        if ((i4 & 2) != 0) {
            i = -1;
        }
        if ((i4 & 4) != 0) {
            i2 = -1;
        }
        if ((i4 & 8) != 0) {
            i3 = 100;
        }
        saveJPEGFile(bitmap, file, i, i2, i3);
    }

    public static final void saveJPEGFile(Bitmap saveJPEGFile, File file, int i, int i2, int i3) throws IOException {
        Intrinsics.checkNotNullParameter(saveJPEGFile, "$this$saveJPEGFile");
        Intrinsics.checkNotNullParameter(file, "file");
        save(saveJPEGFile, file, i, i2, Bitmap.CompressFormat.JPEG, i3);
    }

    public static /* synthetic */ void saveJPEGFile$default(Bitmap bitmap, File file, int i, int i2, Object obj) throws IOException {
        if ((i2 & 2) != 0) {
            i = 100;
        }
        saveJPEGFile(bitmap, file, i);
    }

    public static final void saveJPEGFile(Bitmap saveJPEGFile, File file, int i) throws IOException {
        Intrinsics.checkNotNullParameter(saveJPEGFile, "$this$saveJPEGFile");
        Intrinsics.checkNotNullParameter(file, "file");
        save(saveJPEGFile, file, -1, -1, Bitmap.CompressFormat.JPEG, i);
    }

    public static /* synthetic */ void save$default(Bitmap bitmap, File file, int i, int i2, Bitmap.CompressFormat compressFormat, int i3, int i4, Object obj) throws IOException {
        int i5 = (i4 & 2) != 0 ? -1 : i;
        int i6 = (i4 & 4) != 0 ? -1 : i2;
        if ((i4 & 8) != 0) {
            compressFormat = Bitmap.CompressFormat.PNG;
        }
        Bitmap.CompressFormat compressFormat2 = compressFormat;
        if ((i4 & 16) != 0) {
            i3 = 100;
        }
        save(bitmap, file, i5, i6, compressFormat2, i3);
    }

    public static final void save(Bitmap save, File file, int i, int i2, Bitmap.CompressFormat format, int i3) throws IOException {
        Intrinsics.checkNotNullParameter(save, "$this$save");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(format, "format");
        if (i != -1 && i2 != -1) {
            save = Bitmap.createScaledBitmap(save, i, i2, false);
            Intrinsics.checkNotNullExpressionValue(save, "Bitmap.createScaledBitma…Width,targetHeight,false)");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        save.compress(format, i3, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static /* synthetic */ String toBase64String$default(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            compressFormat = Bitmap.CompressFormat.JPEG;
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return toBase64String(bitmap, compressFormat, i);
    }

    public static final String toBase64String(Bitmap toBase64String, Bitmap.CompressFormat format, int i) throws IOException {
        Intrinsics.checkNotNullParameter(toBase64String, "$this$toBase64String");
        Intrinsics.checkNotNullParameter(format, "format");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        toBase64String.compress(format, 100, byteArrayOutputStream);
        String base64String = Base64.encodeToString(byteArrayOutputStream.toByteArray(), i);
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        Intrinsics.checkNotNullExpressionValue(base64String, "base64String");
        return base64String;
    }
}
