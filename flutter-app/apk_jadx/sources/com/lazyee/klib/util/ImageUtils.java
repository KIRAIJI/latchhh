package com.lazyee.klib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.app.NotificationCompat;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ImageUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u0015\u0016B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0004J&\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u000e2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0004H\u0002J(\u0010\u0011\u001a\u00020\b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010\u0012\u001a\u00020\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/lazyee/klib/util/ImageUtils;", "", "()V", "JPEG_MAX_SIZE", "", "TAG", "", "calculateInSampleSize", "", PictureMimeType.MIME_TYPE_PREFIX_IMAGE, "Lcom/lazyee/klib/util/ImageUtils$LongImageItem;", "checkMergeJPEGLongImageSizeIsTooLong", "", "filePathList", "", "maxWidth", "handleMergeLongImageInfo", "mergeLongImage", "outPath", "callback", "Lcom/lazyee/klib/util/ImageUtils$MergeLongImageCallback;", "LongImageItem", "MergeLongImageCallback", "library_release"}, k = 1, mv = {1, 4, 2})
public final class ImageUtils {
    public static final ImageUtils INSTANCE = new ImageUtils();
    private static final int JPEG_MAX_SIZE = 65500;
    private static final String TAG = "[ImageUtils]";

    /* JADX INFO: compiled from: ImageUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\bH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\bH&J\b\u0010\r\u001a\u00020\u0003H&¨\u0006\u000e"}, d2 = {"Lcom/lazyee/klib/util/ImageUtils$MergeLongImageCallback;", "", "onCompressImage", "", "onHandleImage", "index", "", "filePath", "", "onMergeComplete", "outPath", "onMergeFailed", NotificationCompat.CATEGORY_MESSAGE, "onMergeStart", "library_release"}, k = 1, mv = {1, 4, 2})
    public interface MergeLongImageCallback {
        void onCompressImage();

        void onHandleImage(int index, String filePath);

        void onMergeComplete(String outPath);

        void onMergeFailed(String msg);

        void onMergeStart();
    }

    private ImageUtils() {
    }

    public static /* synthetic */ boolean checkMergeJPEGLongImageSizeIsTooLong$default(ImageUtils imageUtils, List list, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 1000;
        }
        return imageUtils.checkMergeJPEGLongImageSizeIsTooLong(list, i);
    }

    public final boolean checkMergeJPEGLongImageSizeIsTooLong(List<String> filePathList, int maxWidth) {
        Intrinsics.checkNotNullParameter(filePathList, "filePathList");
        List<LongImageItem> listHandleMergeLongImageInfo = handleMergeLongImageInfo(filePathList, maxWidth);
        if (listHandleMergeLongImageInfo.isEmpty()) {
            return false;
        }
        int scaleWidth = ((LongImageItem) CollectionsKt.first((List) listHandleMergeLongImageInfo)).getScaleWidth();
        if (scaleWidth > JPEG_MAX_SIZE) {
            LogUtils.INSTANCE.e(TAG, "图片宽度为[" + scaleWidth + "],已经超出JPEG最大限制[65500]");
            return true;
        }
        Iterator<T> it = listHandleMergeLongImageInfo.iterator();
        int scaleHeight = 0;
        while (it.hasNext()) {
            scaleHeight += ((LongImageItem) it.next()).getScaleHeight();
        }
        if (scaleHeight <= JPEG_MAX_SIZE) {
            return false;
        }
        LogUtils.INSTANCE.e(TAG, "图片高度为[" + scaleHeight + "],已经超出JPEG最大限制[65500]");
        return true;
    }

    static /* synthetic */ List handleMergeLongImageInfo$default(ImageUtils imageUtils, List list, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 1000;
        }
        return imageUtils.handleMergeLongImageInfo(list, i);
    }

    private final List<LongImageItem> handleMergeLongImageInfo(List<String> filePathList, int maxWidth) {
        ArrayList<LongImageItem> arrayList = new ArrayList();
        int iMax = 0;
        for (String str : filePathList) {
            if (new File(str).exists()) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(str, options);
                    arrayList.add(new LongImageItem(str, options.outWidth, options.outHeight, 0, 0, 0, 56, null));
                    iMax = Math.max(Math.min(maxWidth, options.outWidth), iMax);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (LongImageItem longImageItem : arrayList) {
            longImageItem.setScaleWidth(iMax);
            longImageItem.setScaleHeight((int) ((longImageItem.getScaleWidth() / longImageItem.getOriginWidth()) * longImageItem.getOriginHeight()));
            INSTANCE.calculateInSampleSize(longImageItem);
        }
        return arrayList;
    }

    private final void calculateInSampleSize(LongImageItem image) {
        int i = 1;
        if (image.getOriginWidth() > image.getScaleWidth() || image.getOriginHeight() > image.getScaleHeight()) {
            int originWidth = image.getOriginWidth() / 2;
            int originHeight = image.getOriginHeight() / 2;
            while (originWidth / i >= image.getScaleWidth() && originHeight / i >= image.getScaleHeight()) {
                i *= 2;
            }
        }
        image.setInSampleSize(i);
    }

    public static /* synthetic */ void mergeLongImage$default(ImageUtils imageUtils, List list, String str, MergeLongImageCallback mergeLongImageCallback, int i, Object obj) {
        if ((i & 4) != 0) {
            mergeLongImageCallback = (MergeLongImageCallback) null;
        }
        imageUtils.mergeLongImage(list, str, mergeLongImageCallback);
    }

    public final void mergeLongImage(List<String> filePathList, String outPath, MergeLongImageCallback callback) {
        Intrinsics.checkNotNullParameter(filePathList, "filePathList");
        Intrinsics.checkNotNullParameter(outPath, "outPath");
        List listHandleMergeLongImageInfo$default = handleMergeLongImageInfo$default(this, filePathList, 0, 2, null);
        if (listHandleMergeLongImageInfo$default.isEmpty()) {
            if (callback != null) {
                callback.onMergeFailed("没有要合并的有效图片文件");
                return;
            }
            return;
        }
        int scaleWidth = ((LongImageItem) CollectionsKt.first(listHandleMergeLongImageInfo$default)).getScaleWidth();
        Iterator it = listHandleMergeLongImageInfo$default.iterator();
        int scaleHeight = 0;
        while (it.hasNext()) {
            scaleHeight += ((LongImageItem) it.next()).getScaleHeight();
        }
        if (scaleWidth == 0 || scaleHeight == 0) {
            if (callback != null) {
                callback.onMergeFailed("计算出长图的宽度或高度为0，无法合成长图");
                return;
            }
            return;
        }
        if (callback != null) {
            callback.onMergeStart();
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(scaleWidth, scaleHeight, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            int i = 0;
            int i2 = 0;
            for (Object obj : listHandleMergeLongImageInfo$default) {
                int i3 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                LongImageItem longImageItem = (LongImageItem) obj;
                if (callback != null) {
                    callback.onHandleImage(i, longImageItem.getFilePath());
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inSampleSize = longImageItem.getInSampleSize();
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(longImageItem.getFilePath(), options);
                int scaleHeight2 = longImageItem.getScaleHeight() + i2;
                canvas.drawBitmap(bitmapDecodeFile, (Rect) null, new Rect(0, i2, scaleWidth, scaleHeight2), (Paint) null);
                bitmapDecodeFile.recycle();
                i2 = scaleHeight2;
                i = i3;
            }
            if (callback != null) {
                callback.onCompressImage();
            }
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream);
            fileOutputStream.close();
            if (callback != null) {
                callback.onMergeComplete(outPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                String message = e.getMessage();
                if (message == null) {
                    message = "合并失败";
                }
                callback.onMergeFailed(message);
            }
        }
    }

    /* JADX INFO: compiled from: ImageUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\nJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003JE\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u0005HÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020\u0005HÖ\u0001J\t\u0010$\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u000e\"\u0004\b\u0018\u0010\u0010¨\u0006%"}, d2 = {"Lcom/lazyee/klib/util/ImageUtils$LongImageItem;", "", "filePath", "", "originWidth", "", "originHeight", "scaleWidth", "scaleHeight", "inSampleSize", "(Ljava/lang/String;IIIII)V", "getFilePath", "()Ljava/lang/String;", "getInSampleSize", "()I", "setInSampleSize", "(I)V", "getOriginHeight", "setOriginHeight", "getOriginWidth", "setOriginWidth", "getScaleHeight", "setScaleHeight", "getScaleWidth", "setScaleWidth", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "library_release"}, k = 1, mv = {1, 4, 2})
    private static final /* data */ class LongImageItem {
        private final String filePath;
        private int inSampleSize;
        private int originHeight;
        private int originWidth;
        private int scaleHeight;
        private int scaleWidth;

        public static /* synthetic */ LongImageItem copy$default(LongImageItem longImageItem, String str, int i, int i2, int i3, int i4, int i5, int i6, Object obj) {
            if ((i6 & 1) != 0) {
                str = longImageItem.filePath;
            }
            if ((i6 & 2) != 0) {
                i = longImageItem.originWidth;
            }
            int i7 = i;
            if ((i6 & 4) != 0) {
                i2 = longImageItem.originHeight;
            }
            int i8 = i2;
            if ((i6 & 8) != 0) {
                i3 = longImageItem.scaleWidth;
            }
            int i9 = i3;
            if ((i6 & 16) != 0) {
                i4 = longImageItem.scaleHeight;
            }
            int i10 = i4;
            if ((i6 & 32) != 0) {
                i5 = longImageItem.inSampleSize;
            }
            return longImageItem.copy(str, i7, i8, i9, i10, i5);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getFilePath() {
            return this.filePath;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getOriginWidth() {
            return this.originWidth;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getOriginHeight() {
            return this.originHeight;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final int getScaleWidth() {
            return this.scaleWidth;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final int getScaleHeight() {
            return this.scaleHeight;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final int getInSampleSize() {
            return this.inSampleSize;
        }

        public final LongImageItem copy(String filePath, int originWidth, int originHeight, int scaleWidth, int scaleHeight, int inSampleSize) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            return new LongImageItem(filePath, originWidth, originHeight, scaleWidth, scaleHeight, inSampleSize);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LongImageItem)) {
                return false;
            }
            LongImageItem longImageItem = (LongImageItem) other;
            return Intrinsics.areEqual(this.filePath, longImageItem.filePath) && this.originWidth == longImageItem.originWidth && this.originHeight == longImageItem.originHeight && this.scaleWidth == longImageItem.scaleWidth && this.scaleHeight == longImageItem.scaleHeight && this.inSampleSize == longImageItem.inSampleSize;
        }

        public int hashCode() {
            String str = this.filePath;
            return ((((((((((str != null ? str.hashCode() : 0) * 31) + this.originWidth) * 31) + this.originHeight) * 31) + this.scaleWidth) * 31) + this.scaleHeight) * 31) + this.inSampleSize;
        }

        public String toString() {
            return "LongImageItem(filePath=" + this.filePath + ", originWidth=" + this.originWidth + ", originHeight=" + this.originHeight + ", scaleWidth=" + this.scaleWidth + ", scaleHeight=" + this.scaleHeight + ", inSampleSize=" + this.inSampleSize + ")";
        }

        public LongImageItem(String filePath, int i, int i2, int i3, int i4, int i5) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            this.filePath = filePath;
            this.originWidth = i;
            this.originHeight = i2;
            this.scaleWidth = i3;
            this.scaleHeight = i4;
            this.inSampleSize = i5;
        }

        public final String getFilePath() {
            return this.filePath;
        }

        public final int getOriginWidth() {
            return this.originWidth;
        }

        public final void setOriginWidth(int i) {
            this.originWidth = i;
        }

        public final int getOriginHeight() {
            return this.originHeight;
        }

        public final void setOriginHeight(int i) {
            this.originHeight = i;
        }

        public final int getScaleWidth() {
            return this.scaleWidth;
        }

        public final void setScaleWidth(int i) {
            this.scaleWidth = i;
        }

        public final int getScaleHeight() {
            return this.scaleHeight;
        }

        public final void setScaleHeight(int i) {
            this.scaleHeight = i;
        }

        public /* synthetic */ LongImageItem(String str, int i, int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i6 & 2) != 0 ? 0 : i, (i6 & 4) != 0 ? 0 : i2, (i6 & 8) != 0 ? 0 : i3, (i6 & 16) == 0 ? i4 : 0, (i6 & 32) != 0 ? 1 : i5);
        }

        public final int getInSampleSize() {
            return this.inSampleSize;
        }

        public final void setInSampleSize(int i) {
            this.inSampleSize = i;
        }
    }
}
