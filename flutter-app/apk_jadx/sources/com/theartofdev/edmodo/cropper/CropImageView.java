package com.theartofdev.edmodo.cropper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.exifinterface.media.ExifInterface;
import com.theartofdev.edmodo.cropper.BitmapCroppingWorkerTask;
import com.theartofdev.edmodo.cropper.BitmapLoadingWorkerTask;
import com.theartofdev.edmodo.cropper.BitmapUtils;
import com.theartofdev.edmodo.cropper.CropOverlayView;
import java.lang.ref.WeakReference;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class CropImageView extends FrameLayout {
    private CropImageAnimation mAnimation;
    private boolean mAutoZoomEnabled;
    private Bitmap mBitmap;
    private WeakReference<BitmapCroppingWorkerTask> mBitmapCroppingWorkerTask;
    private WeakReference<BitmapLoadingWorkerTask> mBitmapLoadingWorkerTask;
    private final CropOverlayView mCropOverlayView;
    private int mDegreesRotated;
    private boolean mFlipHorizontally;
    private boolean mFlipVertically;
    private final Matrix mImageInverseMatrix;
    private final Matrix mImageMatrix;
    private final float[] mImagePoints;
    private int mImageResource;
    private final ImageView mImageView;
    private int mInitialDegreesRotated;
    private int mLayoutHeight;
    private int mLayoutWidth;
    private Uri mLoadedImageUri;
    private int mLoadedSampleSize;
    private int mMaxZoom;
    private OnCropImageCompleteListener mOnCropImageCompleteListener;
    private OnSetCropOverlayReleasedListener mOnCropOverlayReleasedListener;
    private OnSetCropOverlayMovedListener mOnSetCropOverlayMovedListener;
    private OnSetCropWindowChangeListener mOnSetCropWindowChangeListener;
    private OnSetImageUriCompleteListener mOnSetImageUriCompleteListener;
    private final ProgressBar mProgressBar;
    private RectF mRestoreCropWindowRect;
    private int mRestoreDegreesRotated;
    private boolean mSaveBitmapToInstanceState;
    private Uri mSaveInstanceStateBitmapUri;
    private final float[] mScaleImagePoints;
    private ScaleType mScaleType;
    private boolean mShowCropOverlay;
    private boolean mShowProgressBar;
    private boolean mSizeChanged;
    private float mZoom;
    private float mZoomOffsetX;
    private float mZoomOffsetY;

    public enum CropShape {
        RECTANGLE,
        OVAL
    }

    public enum Guidelines {
        OFF,
        ON_TOUCH,
        ON
    }

    public interface OnCropImageCompleteListener {
        void onCropImageComplete(CropImageView cropImageView, CropResult cropResult);
    }

    public interface OnSetCropOverlayMovedListener {
        void onCropOverlayMoved(Rect rect);
    }

    public interface OnSetCropOverlayReleasedListener {
        void onCropOverlayReleased(Rect rect);
    }

    public interface OnSetCropWindowChangeListener {
        void onCropWindowChanged();
    }

    public interface OnSetImageUriCompleteListener {
        void onSetImageUriComplete(CropImageView cropImageView, Uri uri, Exception exc);
    }

    public enum RequestSizeOptions {
        NONE,
        SAMPLING,
        RESIZE_INSIDE,
        RESIZE_FIT,
        RESIZE_EXACT
    }

    public enum ScaleType {
        FIT_CENTER,
        CENTER,
        CENTER_CROP,
        CENTER_INSIDE
    }

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attributeSet) {
        Bundle bundleExtra;
        super(context, attributeSet);
        this.mImageMatrix = new Matrix();
        this.mImageInverseMatrix = new Matrix();
        this.mImagePoints = new float[8];
        this.mScaleImagePoints = new float[8];
        this.mSaveBitmapToInstanceState = false;
        this.mShowCropOverlay = true;
        this.mShowProgressBar = true;
        this.mAutoZoomEnabled = true;
        this.mLoadedSampleSize = 1;
        this.mZoom = 1.0f;
        CropImageOptions cropImageOptions = null;
        Intent intent = context instanceof Activity ? ((Activity) context).getIntent() : null;
        if (intent != null && (bundleExtra = intent.getBundleExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE)) != null) {
            cropImageOptions = (CropImageOptions) bundleExtra.getParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS);
        }
        if (cropImageOptions == null) {
            cropImageOptions = new CropImageOptions();
            if (attributeSet != null) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CropImageView, 0, 0);
                try {
                    cropImageOptions.fixAspectRatio = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CropImageView_cropFixAspectRatio, cropImageOptions.fixAspectRatio);
                    cropImageOptions.aspectRatioX = typedArrayObtainStyledAttributes.getInteger(R.styleable.CropImageView_cropAspectRatioX, cropImageOptions.aspectRatioX);
                    cropImageOptions.aspectRatioY = typedArrayObtainStyledAttributes.getInteger(R.styleable.CropImageView_cropAspectRatioY, cropImageOptions.aspectRatioY);
                    cropImageOptions.scaleType = ScaleType.values()[typedArrayObtainStyledAttributes.getInt(R.styleable.CropImageView_cropScaleType, cropImageOptions.scaleType.ordinal())];
                    cropImageOptions.autoZoomEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CropImageView_cropAutoZoomEnabled, cropImageOptions.autoZoomEnabled);
                    cropImageOptions.multiTouchEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CropImageView_cropMultiTouchEnabled, cropImageOptions.multiTouchEnabled);
                    cropImageOptions.maxZoom = typedArrayObtainStyledAttributes.getInteger(R.styleable.CropImageView_cropMaxZoom, cropImageOptions.maxZoom);
                    cropImageOptions.cropShape = CropShape.values()[typedArrayObtainStyledAttributes.getInt(R.styleable.CropImageView_cropShape, cropImageOptions.cropShape.ordinal())];
                    cropImageOptions.guidelines = Guidelines.values()[typedArrayObtainStyledAttributes.getInt(R.styleable.CropImageView_cropGuidelines, cropImageOptions.guidelines.ordinal())];
                    cropImageOptions.snapRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropSnapRadius, cropImageOptions.snapRadius);
                    cropImageOptions.touchRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropTouchRadius, cropImageOptions.touchRadius);
                    cropImageOptions.initialCropWindowPaddingRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.CropImageView_cropInitialCropWindowPaddingRatio, cropImageOptions.initialCropWindowPaddingRatio);
                    cropImageOptions.borderLineThickness = typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropBorderLineThickness, cropImageOptions.borderLineThickness);
                    cropImageOptions.borderLineColor = typedArrayObtainStyledAttributes.getInteger(R.styleable.CropImageView_cropBorderLineColor, cropImageOptions.borderLineColor);
                    cropImageOptions.borderCornerThickness = typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropBorderCornerThickness, cropImageOptions.borderCornerThickness);
                    cropImageOptions.borderCornerOffset = typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropBorderCornerOffset, cropImageOptions.borderCornerOffset);
                    cropImageOptions.borderCornerLength = typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropBorderCornerLength, cropImageOptions.borderCornerLength);
                    cropImageOptions.borderCornerColor = typedArrayObtainStyledAttributes.getInteger(R.styleable.CropImageView_cropBorderCornerColor, cropImageOptions.borderCornerColor);
                    cropImageOptions.guidelinesThickness = typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropGuidelinesThickness, cropImageOptions.guidelinesThickness);
                    cropImageOptions.guidelinesColor = typedArrayObtainStyledAttributes.getInteger(R.styleable.CropImageView_cropGuidelinesColor, cropImageOptions.guidelinesColor);
                    cropImageOptions.backgroundColor = typedArrayObtainStyledAttributes.getInteger(R.styleable.CropImageView_cropBackgroundColor, cropImageOptions.backgroundColor);
                    cropImageOptions.showCropOverlay = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CropImageView_cropShowCropOverlay, this.mShowCropOverlay);
                    cropImageOptions.showProgressBar = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CropImageView_cropShowProgressBar, this.mShowProgressBar);
                    cropImageOptions.borderCornerThickness = typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropBorderCornerThickness, cropImageOptions.borderCornerThickness);
                    cropImageOptions.minCropWindowWidth = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropMinCropWindowWidth, cropImageOptions.minCropWindowWidth);
                    cropImageOptions.minCropWindowHeight = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.CropImageView_cropMinCropWindowHeight, cropImageOptions.minCropWindowHeight);
                    cropImageOptions.minCropResultWidth = (int) typedArrayObtainStyledAttributes.getFloat(R.styleable.CropImageView_cropMinCropResultWidthPX, cropImageOptions.minCropResultWidth);
                    cropImageOptions.minCropResultHeight = (int) typedArrayObtainStyledAttributes.getFloat(R.styleable.CropImageView_cropMinCropResultHeightPX, cropImageOptions.minCropResultHeight);
                    cropImageOptions.maxCropResultWidth = (int) typedArrayObtainStyledAttributes.getFloat(R.styleable.CropImageView_cropMaxCropResultWidthPX, cropImageOptions.maxCropResultWidth);
                    cropImageOptions.maxCropResultHeight = (int) typedArrayObtainStyledAttributes.getFloat(R.styleable.CropImageView_cropMaxCropResultHeightPX, cropImageOptions.maxCropResultHeight);
                    cropImageOptions.flipHorizontally = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CropImageView_cropFlipHorizontally, cropImageOptions.flipHorizontally);
                    cropImageOptions.flipVertically = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CropImageView_cropFlipHorizontally, cropImageOptions.flipVertically);
                    this.mSaveBitmapToInstanceState = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CropImageView_cropSaveBitmapToInstanceState, this.mSaveBitmapToInstanceState);
                    if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CropImageView_cropAspectRatioX) && typedArrayObtainStyledAttributes.hasValue(R.styleable.CropImageView_cropAspectRatioX) && !typedArrayObtainStyledAttributes.hasValue(R.styleable.CropImageView_cropFixAspectRatio)) {
                        cropImageOptions.fixAspectRatio = true;
                    }
                } finally {
                    typedArrayObtainStyledAttributes.recycle();
                }
            }
        }
        cropImageOptions.validate();
        this.mScaleType = cropImageOptions.scaleType;
        this.mAutoZoomEnabled = cropImageOptions.autoZoomEnabled;
        this.mMaxZoom = cropImageOptions.maxZoom;
        this.mShowCropOverlay = cropImageOptions.showCropOverlay;
        this.mShowProgressBar = cropImageOptions.showProgressBar;
        this.mFlipHorizontally = cropImageOptions.flipHorizontally;
        this.mFlipVertically = cropImageOptions.flipVertically;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.crop_image_view, (ViewGroup) this, true);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.ImageView_image);
        this.mImageView = imageView;
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        CropOverlayView cropOverlayView = (CropOverlayView) viewInflate.findViewById(R.id.CropOverlayView);
        this.mCropOverlayView = cropOverlayView;
        cropOverlayView.setCropWindowChangeListener(new CropOverlayView.CropWindowChangeListener() { // from class: com.theartofdev.edmodo.cropper.CropImageView.1
            @Override // com.theartofdev.edmodo.cropper.CropOverlayView.CropWindowChangeListener
            public void onCropWindowChanged(boolean z) {
                CropImageView.this.handleCropWindowChanged(z, true);
                OnSetCropOverlayReleasedListener onSetCropOverlayReleasedListener = CropImageView.this.mOnCropOverlayReleasedListener;
                if (onSetCropOverlayReleasedListener != null && !z) {
                    onSetCropOverlayReleasedListener.onCropOverlayReleased(CropImageView.this.getCropRect());
                }
                OnSetCropOverlayMovedListener onSetCropOverlayMovedListener = CropImageView.this.mOnSetCropOverlayMovedListener;
                if (onSetCropOverlayMovedListener == null || !z) {
                    return;
                }
                onSetCropOverlayMovedListener.onCropOverlayMoved(CropImageView.this.getCropRect());
            }
        });
        cropOverlayView.setInitialAttributeValues(cropImageOptions);
        this.mProgressBar = (ProgressBar) viewInflate.findViewById(R.id.CropProgressBar);
        setProgressBarVisibility();
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != this.mScaleType) {
            this.mScaleType = scaleType;
            this.mZoom = 1.0f;
            this.mZoomOffsetY = 0.0f;
            this.mZoomOffsetX = 0.0f;
            this.mCropOverlayView.resetCropOverlayView();
            requestLayout();
        }
    }

    public CropShape getCropShape() {
        return this.mCropOverlayView.getCropShape();
    }

    public void setCropShape(CropShape cropShape) {
        this.mCropOverlayView.setCropShape(cropShape);
    }

    public boolean isAutoZoomEnabled() {
        return this.mAutoZoomEnabled;
    }

    public void setAutoZoomEnabled(boolean z) {
        if (this.mAutoZoomEnabled != z) {
            this.mAutoZoomEnabled = z;
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public void setMultiTouchEnabled(boolean z) {
        if (this.mCropOverlayView.setMultiTouchEnabled(z)) {
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public int getMaxZoom() {
        return this.mMaxZoom;
    }

    public void setMaxZoom(int i) {
        if (this.mMaxZoom == i || i <= 0) {
            return;
        }
        this.mMaxZoom = i;
        handleCropWindowChanged(false, false);
        this.mCropOverlayView.invalidate();
    }

    public void setMinCropResultSize(int i, int i2) {
        this.mCropOverlayView.setMinCropResultSize(i, i2);
    }

    public void setMaxCropResultSize(int i, int i2) {
        this.mCropOverlayView.setMaxCropResultSize(i, i2);
    }

    public int getRotatedDegrees() {
        return this.mDegreesRotated;
    }

    public void setRotatedDegrees(int i) {
        int i2 = this.mDegreesRotated;
        if (i2 != i) {
            rotateImage(i - i2);
        }
    }

    public boolean isFixAspectRatio() {
        return this.mCropOverlayView.isFixAspectRatio();
    }

    public void setFixedAspectRatio(boolean z) {
        this.mCropOverlayView.setFixedAspectRatio(z);
    }

    public boolean isFlippedHorizontally() {
        return this.mFlipHorizontally;
    }

    public void setFlippedHorizontally(boolean z) {
        if (this.mFlipHorizontally != z) {
            this.mFlipHorizontally = z;
            applyImageMatrix(getWidth(), getHeight(), true, false);
        }
    }

    public boolean isFlippedVertically() {
        return this.mFlipVertically;
    }

    public void setFlippedVertically(boolean z) {
        if (this.mFlipVertically != z) {
            this.mFlipVertically = z;
            applyImageMatrix(getWidth(), getHeight(), true, false);
        }
    }

    public Guidelines getGuidelines() {
        return this.mCropOverlayView.getGuidelines();
    }

    public void setGuidelines(Guidelines guidelines) {
        this.mCropOverlayView.setGuidelines(guidelines);
    }

    public Pair<Integer, Integer> getAspectRatio() {
        return new Pair<>(Integer.valueOf(this.mCropOverlayView.getAspectRatioX()), Integer.valueOf(this.mCropOverlayView.getAspectRatioY()));
    }

    public void setAspectRatio(int i, int i2) {
        this.mCropOverlayView.setAspectRatioX(i);
        this.mCropOverlayView.setAspectRatioY(i2);
        setFixedAspectRatio(true);
    }

    public void clearAspectRatio() {
        this.mCropOverlayView.setAspectRatioX(1);
        this.mCropOverlayView.setAspectRatioY(1);
        setFixedAspectRatio(false);
    }

    public void setSnapRadius(float f) {
        if (f >= 0.0f) {
            this.mCropOverlayView.setSnapRadius(f);
        }
    }

    public boolean isShowProgressBar() {
        return this.mShowProgressBar;
    }

    public void setShowProgressBar(boolean z) {
        if (this.mShowProgressBar != z) {
            this.mShowProgressBar = z;
            setProgressBarVisibility();
        }
    }

    public boolean isShowCropOverlay() {
        return this.mShowCropOverlay;
    }

    public void setShowCropOverlay(boolean z) {
        if (this.mShowCropOverlay != z) {
            this.mShowCropOverlay = z;
            setCropOverlayVisibility();
        }
    }

    public boolean isSaveBitmapToInstanceState() {
        return this.mSaveBitmapToInstanceState;
    }

    public void setSaveBitmapToInstanceState(boolean z) {
        this.mSaveBitmapToInstanceState = z;
    }

    public int getImageResource() {
        return this.mImageResource;
    }

    public Uri getImageUri() {
        return this.mLoadedImageUri;
    }

    public Rect getWholeImageRect() {
        int i = this.mLoadedSampleSize;
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return null;
        }
        return new Rect(0, 0, bitmap.getWidth() * i, bitmap.getHeight() * i);
    }

    public Rect getCropRect() {
        int i = this.mLoadedSampleSize;
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return null;
        }
        return BitmapUtils.getRectFromPoints(getCropPoints(), bitmap.getWidth() * i, i * bitmap.getHeight(), this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY());
    }

    public RectF getCropWindowRect() {
        CropOverlayView cropOverlayView = this.mCropOverlayView;
        if (cropOverlayView == null) {
            return null;
        }
        return cropOverlayView.getCropWindowRect();
    }

    public float[] getCropPoints() {
        RectF cropWindowRect = this.mCropOverlayView.getCropWindowRect();
        float[] fArr = new float[8];
        fArr[0] = cropWindowRect.left;
        fArr[1] = cropWindowRect.top;
        fArr[2] = cropWindowRect.right;
        fArr[3] = cropWindowRect.top;
        fArr[4] = cropWindowRect.right;
        fArr[5] = cropWindowRect.bottom;
        fArr[6] = cropWindowRect.left;
        fArr[7] = cropWindowRect.bottom;
        this.mImageMatrix.invert(this.mImageInverseMatrix);
        this.mImageInverseMatrix.mapPoints(fArr);
        for (int i = 0; i < 8; i++) {
            fArr[i] = fArr[i] * this.mLoadedSampleSize;
        }
        return fArr;
    }

    public void setCropRect(Rect rect) {
        this.mCropOverlayView.setInitialCropWindowRect(rect);
    }

    public void resetCropRect() {
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        this.mDegreesRotated = this.mInitialDegreesRotated;
        this.mFlipHorizontally = false;
        this.mFlipVertically = false;
        applyImageMatrix(getWidth(), getHeight(), false, false);
        this.mCropOverlayView.resetCropWindowRect();
    }

    public Bitmap getCroppedImage() {
        return getCroppedImage(0, 0, RequestSizeOptions.NONE);
    }

    public Bitmap getCroppedImage(int i, int i2) {
        return getCroppedImage(i, i2, RequestSizeOptions.RESIZE_INSIDE);
    }

    public Bitmap getCroppedImage(int i, int i2, RequestSizeOptions requestSizeOptions) {
        Bitmap bitmap;
        if (this.mBitmap == null) {
            return null;
        }
        this.mImageView.clearAnimation();
        int i3 = requestSizeOptions != RequestSizeOptions.NONE ? i : 0;
        int i4 = requestSizeOptions != RequestSizeOptions.NONE ? i2 : 0;
        if (this.mLoadedImageUri != null && (this.mLoadedSampleSize > 1 || requestSizeOptions == RequestSizeOptions.SAMPLING)) {
            bitmap = BitmapUtils.cropBitmap(getContext(), this.mLoadedImageUri, getCropPoints(), this.mDegreesRotated, this.mBitmap.getWidth() * this.mLoadedSampleSize, this.mBitmap.getHeight() * this.mLoadedSampleSize, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), i3, i4, this.mFlipHorizontally, this.mFlipVertically).bitmap;
        } else {
            bitmap = BitmapUtils.cropBitmapObjectHandleOOM(this.mBitmap, getCropPoints(), this.mDegreesRotated, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), this.mFlipHorizontally, this.mFlipVertically).bitmap;
        }
        return BitmapUtils.resizeBitmap(bitmap, i3, i4, requestSizeOptions);
    }

    public void getCroppedImageAsync() {
        getCroppedImageAsync(0, 0, RequestSizeOptions.NONE);
    }

    public void getCroppedImageAsync(int i, int i2) {
        getCroppedImageAsync(i, i2, RequestSizeOptions.RESIZE_INSIDE);
    }

    public void getCroppedImageAsync(int i, int i2, RequestSizeOptions requestSizeOptions) {
        if (this.mOnCropImageCompleteListener == null) {
            throw new IllegalArgumentException("mOnCropImageCompleteListener is not set");
        }
        startCropWorkerTask(i, i2, requestSizeOptions, null, null, 0);
    }

    public void saveCroppedImageAsync(Uri uri) {
        saveCroppedImageAsync(uri, Bitmap.CompressFormat.JPEG, 90, 0, 0, RequestSizeOptions.NONE);
    }

    public void saveCroppedImageAsync(Uri uri, Bitmap.CompressFormat compressFormat, int i) {
        saveCroppedImageAsync(uri, compressFormat, i, 0, 0, RequestSizeOptions.NONE);
    }

    public void saveCroppedImageAsync(Uri uri, Bitmap.CompressFormat compressFormat, int i, int i2, int i3) {
        saveCroppedImageAsync(uri, compressFormat, i, i2, i3, RequestSizeOptions.RESIZE_INSIDE);
    }

    public void saveCroppedImageAsync(Uri uri, Bitmap.CompressFormat compressFormat, int i, int i2, int i3, RequestSizeOptions requestSizeOptions) {
        if (this.mOnCropImageCompleteListener == null) {
            throw new IllegalArgumentException("mOnCropImageCompleteListener is not set");
        }
        startCropWorkerTask(i2, i3, requestSizeOptions, uri, compressFormat, i);
    }

    public void setOnSetCropOverlayReleasedListener(OnSetCropOverlayReleasedListener onSetCropOverlayReleasedListener) {
        this.mOnCropOverlayReleasedListener = onSetCropOverlayReleasedListener;
    }

    public void setOnSetCropOverlayMovedListener(OnSetCropOverlayMovedListener onSetCropOverlayMovedListener) {
        this.mOnSetCropOverlayMovedListener = onSetCropOverlayMovedListener;
    }

    public void setOnCropWindowChangedListener(OnSetCropWindowChangeListener onSetCropWindowChangeListener) {
        this.mOnSetCropWindowChangeListener = onSetCropWindowChangeListener;
    }

    public void setOnSetImageUriCompleteListener(OnSetImageUriCompleteListener onSetImageUriCompleteListener) {
        this.mOnSetImageUriCompleteListener = onSetImageUriCompleteListener;
    }

    public void setOnCropImageCompleteListener(OnCropImageCompleteListener onCropImageCompleteListener) {
        this.mOnCropImageCompleteListener = onCropImageCompleteListener;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mCropOverlayView.setInitialCropWindowRect(null);
        setBitmap(bitmap, 0, null, 1, 0);
    }

    public void setImageBitmap(Bitmap bitmap, ExifInterface exifInterface) {
        int i;
        Bitmap bitmap2;
        if (bitmap == null || exifInterface == null) {
            i = 0;
            bitmap2 = bitmap;
        } else {
            BitmapUtils.RotateBitmapResult rotateBitmapResultRotateBitmapByExif = BitmapUtils.rotateBitmapByExif(bitmap, exifInterface);
            Bitmap bitmap3 = rotateBitmapResultRotateBitmapByExif.bitmap;
            i = rotateBitmapResultRotateBitmapByExif.degrees;
            this.mInitialDegreesRotated = rotateBitmapResultRotateBitmapByExif.degrees;
            bitmap2 = bitmap3;
        }
        this.mCropOverlayView.setInitialCropWindowRect(null);
        setBitmap(bitmap2, 0, null, 1, i);
    }

    public void setImageResource(int i) {
        if (i != 0) {
            this.mCropOverlayView.setInitialCropWindowRect(null);
            setBitmap(BitmapFactory.decodeResource(getResources(), i), i, null, 1, 0);
        }
    }

    public void setImageUriAsync(Uri uri) {
        if (uri != null) {
            WeakReference<BitmapLoadingWorkerTask> weakReference = this.mBitmapLoadingWorkerTask;
            BitmapLoadingWorkerTask bitmapLoadingWorkerTask = weakReference != null ? weakReference.get() : null;
            if (bitmapLoadingWorkerTask != null) {
                bitmapLoadingWorkerTask.cancel(true);
            }
            clearImageInt();
            this.mRestoreCropWindowRect = null;
            this.mRestoreDegreesRotated = 0;
            this.mCropOverlayView.setInitialCropWindowRect(null);
            WeakReference<BitmapLoadingWorkerTask> weakReference2 = new WeakReference<>(new BitmapLoadingWorkerTask(this, uri));
            this.mBitmapLoadingWorkerTask = weakReference2;
            weakReference2.get().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            setProgressBarVisibility();
        }
    }

    public void clearImage() {
        clearImageInt();
        this.mCropOverlayView.setInitialCropWindowRect(null);
    }

    public void rotateImage(int i) {
        int i2;
        if (this.mBitmap != null) {
            if (i < 0) {
                i2 = (i % 360) + 360;
            } else {
                i2 = i % 360;
            }
            boolean z = !this.mCropOverlayView.isFixAspectRatio() && ((i2 > 45 && i2 < 135) || (i2 > 215 && i2 < 305));
            BitmapUtils.RECT.set(this.mCropOverlayView.getCropWindowRect());
            RectF rectF = BitmapUtils.RECT;
            float fHeight = (z ? rectF.height() : rectF.width()) / 2.0f;
            RectF rectF2 = BitmapUtils.RECT;
            float fWidth = (z ? rectF2.width() : rectF2.height()) / 2.0f;
            if (z) {
                boolean z2 = this.mFlipHorizontally;
                this.mFlipHorizontally = this.mFlipVertically;
                this.mFlipVertically = z2;
            }
            this.mImageMatrix.invert(this.mImageInverseMatrix);
            BitmapUtils.POINTS[0] = BitmapUtils.RECT.centerX();
            BitmapUtils.POINTS[1] = BitmapUtils.RECT.centerY();
            BitmapUtils.POINTS[2] = 0.0f;
            BitmapUtils.POINTS[3] = 0.0f;
            BitmapUtils.POINTS[4] = 1.0f;
            BitmapUtils.POINTS[5] = 0.0f;
            this.mImageInverseMatrix.mapPoints(BitmapUtils.POINTS);
            this.mDegreesRotated = (this.mDegreesRotated + i2) % 360;
            applyImageMatrix(getWidth(), getHeight(), true, false);
            this.mImageMatrix.mapPoints(BitmapUtils.POINTS2, BitmapUtils.POINTS);
            float fSqrt = (float) (((double) this.mZoom) / Math.sqrt(Math.pow(BitmapUtils.POINTS2[4] - BitmapUtils.POINTS2[2], 2.0d) + Math.pow(BitmapUtils.POINTS2[5] - BitmapUtils.POINTS2[3], 2.0d)));
            this.mZoom = fSqrt;
            this.mZoom = Math.max(fSqrt, 1.0f);
            applyImageMatrix(getWidth(), getHeight(), true, false);
            this.mImageMatrix.mapPoints(BitmapUtils.POINTS2, BitmapUtils.POINTS);
            double dSqrt = Math.sqrt(Math.pow(BitmapUtils.POINTS2[4] - BitmapUtils.POINTS2[2], 2.0d) + Math.pow(BitmapUtils.POINTS2[5] - BitmapUtils.POINTS2[3], 2.0d));
            float f = (float) (((double) fHeight) * dSqrt);
            float f2 = (float) (((double) fWidth) * dSqrt);
            BitmapUtils.RECT.set(BitmapUtils.POINTS2[0] - f, BitmapUtils.POINTS2[1] - f2, BitmapUtils.POINTS2[0] + f, BitmapUtils.POINTS2[1] + f2);
            this.mCropOverlayView.resetCropOverlayView();
            this.mCropOverlayView.setCropWindowRect(BitmapUtils.RECT);
            applyImageMatrix(getWidth(), getHeight(), true, false);
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.fixCurrentCropWindowRect();
        }
    }

    public void flipImageHorizontally() {
        this.mFlipHorizontally = !this.mFlipHorizontally;
        applyImageMatrix(getWidth(), getHeight(), true, false);
    }

    public void flipImageVertically() {
        this.mFlipVertically = !this.mFlipVertically;
        applyImageMatrix(getWidth(), getHeight(), true, false);
    }

    void onSetImageUriAsyncComplete(BitmapLoadingWorkerTask.Result result) {
        this.mBitmapLoadingWorkerTask = null;
        setProgressBarVisibility();
        if (result.error == null) {
            this.mInitialDegreesRotated = result.degreesRotated;
            setBitmap(result.bitmap, 0, result.uri, result.loadSampleSize, result.degreesRotated);
        }
        OnSetImageUriCompleteListener onSetImageUriCompleteListener = this.mOnSetImageUriCompleteListener;
        if (onSetImageUriCompleteListener != null) {
            onSetImageUriCompleteListener.onSetImageUriComplete(this, result.uri, result.error);
        }
    }

    void onImageCroppingAsyncComplete(BitmapCroppingWorkerTask.Result result) {
        this.mBitmapCroppingWorkerTask = null;
        setProgressBarVisibility();
        OnCropImageCompleteListener onCropImageCompleteListener = this.mOnCropImageCompleteListener;
        if (onCropImageCompleteListener != null) {
            onCropImageCompleteListener.onCropImageComplete(this, new CropResult(this.mBitmap, this.mLoadedImageUri, result.bitmap, result.uri, result.error, getCropPoints(), getCropRect(), getWholeImageRect(), getRotatedDegrees(), result.sampleSize));
        }
    }

    private void setBitmap(Bitmap bitmap, int i, Uri uri, int i2, int i3) {
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 == null || !bitmap2.equals(bitmap)) {
            this.mImageView.clearAnimation();
            clearImageInt();
            this.mBitmap = bitmap;
            this.mImageView.setImageBitmap(bitmap);
            this.mLoadedImageUri = uri;
            this.mImageResource = i;
            this.mLoadedSampleSize = i2;
            this.mDegreesRotated = i3;
            applyImageMatrix(getWidth(), getHeight(), true, false);
            CropOverlayView cropOverlayView = this.mCropOverlayView;
            if (cropOverlayView != null) {
                cropOverlayView.resetCropOverlayView();
                setCropOverlayVisibility();
            }
        }
    }

    private void clearImageInt() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null && (this.mImageResource > 0 || this.mLoadedImageUri != null)) {
            bitmap.recycle();
        }
        this.mBitmap = null;
        this.mImageResource = 0;
        this.mLoadedImageUri = null;
        this.mLoadedSampleSize = 1;
        this.mDegreesRotated = 0;
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        this.mImageMatrix.reset();
        this.mSaveInstanceStateBitmapUri = null;
        this.mImageView.setImageBitmap(null);
        setCropOverlayVisibility();
    }

    public void startCropWorkerTask(int i, int i2, RequestSizeOptions requestSizeOptions, Uri uri, Bitmap.CompressFormat compressFormat, int i3) {
        CropImageView cropImageView;
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            this.mImageView.clearAnimation();
            WeakReference<BitmapCroppingWorkerTask> weakReference = this.mBitmapCroppingWorkerTask;
            BitmapCroppingWorkerTask bitmapCroppingWorkerTask = weakReference != null ? weakReference.get() : null;
            if (bitmapCroppingWorkerTask != null) {
                bitmapCroppingWorkerTask.cancel(true);
            }
            int i4 = requestSizeOptions != RequestSizeOptions.NONE ? i : 0;
            int i5 = requestSizeOptions != RequestSizeOptions.NONE ? i2 : 0;
            int width = bitmap.getWidth() * this.mLoadedSampleSize;
            int height = bitmap.getHeight();
            int i6 = this.mLoadedSampleSize;
            int i7 = height * i6;
            if (this.mLoadedImageUri != null && (i6 > 1 || requestSizeOptions == RequestSizeOptions.SAMPLING)) {
                this.mBitmapCroppingWorkerTask = new WeakReference<>(new BitmapCroppingWorkerTask(this, this.mLoadedImageUri, getCropPoints(), this.mDegreesRotated, width, i7, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), i4, i5, this.mFlipHorizontally, this.mFlipVertically, requestSizeOptions, uri, compressFormat, i3));
                cropImageView = this;
            } else {
                cropImageView = this;
                cropImageView.mBitmapCroppingWorkerTask = new WeakReference<>(new BitmapCroppingWorkerTask(this, bitmap, getCropPoints(), this.mDegreesRotated, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), i4, i5, this.mFlipHorizontally, this.mFlipVertically, requestSizeOptions, uri, compressFormat, i3));
            }
            cropImageView.mBitmapCroppingWorkerTask.get().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            setProgressBarVisibility();
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        BitmapLoadingWorkerTask bitmapLoadingWorkerTask;
        if (this.mLoadedImageUri == null && this.mBitmap == null && this.mImageResource < 1) {
            return super.onSaveInstanceState();
        }
        Bundle bundle = new Bundle();
        Uri uriWriteTempStateStoreBitmap = this.mLoadedImageUri;
        if (this.mSaveBitmapToInstanceState && uriWriteTempStateStoreBitmap == null && this.mImageResource < 1) {
            uriWriteTempStateStoreBitmap = BitmapUtils.writeTempStateStoreBitmap(getContext(), this.mBitmap, this.mSaveInstanceStateBitmapUri);
            this.mSaveInstanceStateBitmapUri = uriWriteTempStateStoreBitmap;
        }
        if (uriWriteTempStateStoreBitmap != null && this.mBitmap != null) {
            String string = UUID.randomUUID().toString();
            BitmapUtils.mStateBitmap = new Pair<>(string, new WeakReference(this.mBitmap));
            bundle.putString("LOADED_IMAGE_STATE_BITMAP_KEY", string);
        }
        WeakReference<BitmapLoadingWorkerTask> weakReference = this.mBitmapLoadingWorkerTask;
        if (weakReference != null && (bitmapLoadingWorkerTask = weakReference.get()) != null) {
            bundle.putParcelable("LOADING_IMAGE_URI", bitmapLoadingWorkerTask.getUri());
        }
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putParcelable("LOADED_IMAGE_URI", uriWriteTempStateStoreBitmap);
        bundle.putInt("LOADED_IMAGE_RESOURCE", this.mImageResource);
        bundle.putInt("LOADED_SAMPLE_SIZE", this.mLoadedSampleSize);
        bundle.putInt("DEGREES_ROTATED", this.mDegreesRotated);
        bundle.putParcelable("INITIAL_CROP_RECT", this.mCropOverlayView.getInitialCropWindowRect());
        BitmapUtils.RECT.set(this.mCropOverlayView.getCropWindowRect());
        this.mImageMatrix.invert(this.mImageInverseMatrix);
        this.mImageInverseMatrix.mapRect(BitmapUtils.RECT);
        bundle.putParcelable("CROP_WINDOW_RECT", BitmapUtils.RECT);
        bundle.putString("CROP_SHAPE", this.mCropOverlayView.getCropShape().name());
        bundle.putBoolean("CROP_AUTO_ZOOM_ENABLED", this.mAutoZoomEnabled);
        bundle.putInt("CROP_MAX_ZOOM", this.mMaxZoom);
        bundle.putBoolean("CROP_FLIP_HORIZONTALLY", this.mFlipHorizontally);
        bundle.putBoolean("CROP_FLIP_VERTICALLY", this.mFlipVertically);
        return bundle;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            if (this.mBitmapLoadingWorkerTask == null && this.mLoadedImageUri == null && this.mBitmap == null && this.mImageResource == 0) {
                Uri uri = (Uri) bundle.getParcelable("LOADED_IMAGE_URI");
                if (uri != null) {
                    String string = bundle.getString("LOADED_IMAGE_STATE_BITMAP_KEY");
                    if (string != null) {
                        Bitmap bitmap = (BitmapUtils.mStateBitmap == null || !((String) BitmapUtils.mStateBitmap.first).equals(string)) ? null : (Bitmap) ((WeakReference) BitmapUtils.mStateBitmap.second).get();
                        BitmapUtils.mStateBitmap = null;
                        if (bitmap != null && !bitmap.isRecycled()) {
                            setBitmap(bitmap, 0, uri, bundle.getInt("LOADED_SAMPLE_SIZE"), 0);
                        }
                    }
                    if (this.mLoadedImageUri == null) {
                        setImageUriAsync(uri);
                    }
                } else {
                    int i = bundle.getInt("LOADED_IMAGE_RESOURCE");
                    if (i > 0) {
                        setImageResource(i);
                    } else {
                        Uri uri2 = (Uri) bundle.getParcelable("LOADING_IMAGE_URI");
                        if (uri2 != null) {
                            setImageUriAsync(uri2);
                        }
                    }
                }
                int i2 = bundle.getInt("DEGREES_ROTATED");
                this.mRestoreDegreesRotated = i2;
                this.mDegreesRotated = i2;
                Rect rect = (Rect) bundle.getParcelable("INITIAL_CROP_RECT");
                if (rect != null && (rect.width() > 0 || rect.height() > 0)) {
                    this.mCropOverlayView.setInitialCropWindowRect(rect);
                }
                RectF rectF = (RectF) bundle.getParcelable("CROP_WINDOW_RECT");
                if (rectF != null && (rectF.width() > 0.0f || rectF.height() > 0.0f)) {
                    this.mRestoreCropWindowRect = rectF;
                }
                this.mCropOverlayView.setCropShape(CropShape.valueOf(bundle.getString("CROP_SHAPE")));
                this.mAutoZoomEnabled = bundle.getBoolean("CROP_AUTO_ZOOM_ENABLED");
                this.mMaxZoom = bundle.getInt("CROP_MAX_ZOOM");
                this.mFlipHorizontally = bundle.getBoolean("CROP_FLIP_HORIZONTALLY");
                this.mFlipVertically = bundle.getBoolean("CROP_FLIP_VERTICALLY");
            }
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int width;
        int height;
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            if (size2 == 0) {
                size2 = bitmap.getHeight();
            }
            double width2 = size < this.mBitmap.getWidth() ? ((double) size) / ((double) this.mBitmap.getWidth()) : Double.POSITIVE_INFINITY;
            double height2 = size2 < this.mBitmap.getHeight() ? ((double) size2) / ((double) this.mBitmap.getHeight()) : Double.POSITIVE_INFINITY;
            if (width2 == Double.POSITIVE_INFINITY && height2 == Double.POSITIVE_INFINITY) {
                width = this.mBitmap.getWidth();
                height = this.mBitmap.getHeight();
            } else if (width2 <= height2) {
                height = (int) (((double) this.mBitmap.getHeight()) * width2);
                width = size;
            } else {
                width = (int) (((double) this.mBitmap.getWidth()) * height2);
                height = size2;
            }
            int onMeasureSpec = getOnMeasureSpec(mode, size, width);
            int onMeasureSpec2 = getOnMeasureSpec(mode2, size2, height);
            this.mLayoutWidth = onMeasureSpec;
            this.mLayoutHeight = onMeasureSpec2;
            setMeasuredDimension(onMeasureSpec, onMeasureSpec2);
            return;
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mLayoutWidth > 0 && this.mLayoutHeight > 0) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = this.mLayoutWidth;
            layoutParams.height = this.mLayoutHeight;
            setLayoutParams(layoutParams);
            if (this.mBitmap != null) {
                float f = i3 - i;
                float f2 = i4 - i2;
                applyImageMatrix(f, f2, true, false);
                if (this.mRestoreCropWindowRect != null) {
                    int i5 = this.mRestoreDegreesRotated;
                    if (i5 != this.mInitialDegreesRotated) {
                        this.mDegreesRotated = i5;
                        applyImageMatrix(f, f2, true, false);
                    }
                    this.mImageMatrix.mapRect(this.mRestoreCropWindowRect);
                    this.mCropOverlayView.setCropWindowRect(this.mRestoreCropWindowRect);
                    handleCropWindowChanged(false, false);
                    this.mCropOverlayView.fixCurrentCropWindowRect();
                    this.mRestoreCropWindowRect = null;
                    return;
                }
                if (this.mSizeChanged) {
                    this.mSizeChanged = false;
                    handleCropWindowChanged(false, false);
                    return;
                }
                return;
            }
            updateImageBounds(true);
            return;
        }
        updateImageBounds(true);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mSizeChanged = i3 > 0 && i4 > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void handleCropWindowChanged(boolean r12, boolean r13) {
        /*
            Method dump skipped, instruction units count: 257
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.theartofdev.edmodo.cropper.CropImageView.handleCropWindowChanged(boolean, boolean):void");
    }

    private void applyImageMatrix(float f, float f2, boolean z, boolean z2) {
        if (this.mBitmap != null) {
            if (f <= 0.0f || f2 <= 0.0f) {
                return;
            }
            this.mImageMatrix.invert(this.mImageInverseMatrix);
            RectF cropWindowRect = this.mCropOverlayView.getCropWindowRect();
            this.mImageInverseMatrix.mapRect(cropWindowRect);
            this.mImageMatrix.reset();
            this.mImageMatrix.postTranslate((f - this.mBitmap.getWidth()) / 2.0f, (f2 - this.mBitmap.getHeight()) / 2.0f);
            mapImagePointsByImageMatrix();
            int i = this.mDegreesRotated;
            if (i > 0) {
                this.mImageMatrix.postRotate(i, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                mapImagePointsByImageMatrix();
            }
            float fMin = Math.min(f / BitmapUtils.getRectWidth(this.mImagePoints), f2 / BitmapUtils.getRectHeight(this.mImagePoints));
            if (this.mScaleType == ScaleType.FIT_CENTER || ((this.mScaleType == ScaleType.CENTER_INSIDE && fMin < 1.0f) || (fMin > 1.0f && this.mAutoZoomEnabled))) {
                this.mImageMatrix.postScale(fMin, fMin, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                mapImagePointsByImageMatrix();
            }
            float f3 = this.mFlipHorizontally ? -this.mZoom : this.mZoom;
            float f4 = this.mFlipVertically ? -this.mZoom : this.mZoom;
            this.mImageMatrix.postScale(f3, f4, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
            mapImagePointsByImageMatrix();
            this.mImageMatrix.mapRect(cropWindowRect);
            if (z) {
                this.mZoomOffsetX = f > BitmapUtils.getRectWidth(this.mImagePoints) ? 0.0f : Math.max(Math.min((f / 2.0f) - cropWindowRect.centerX(), -BitmapUtils.getRectLeft(this.mImagePoints)), getWidth() - BitmapUtils.getRectRight(this.mImagePoints)) / f3;
                this.mZoomOffsetY = f2 <= BitmapUtils.getRectHeight(this.mImagePoints) ? Math.max(Math.min((f2 / 2.0f) - cropWindowRect.centerY(), -BitmapUtils.getRectTop(this.mImagePoints)), getHeight() - BitmapUtils.getRectBottom(this.mImagePoints)) / f4 : 0.0f;
            } else {
                this.mZoomOffsetX = Math.min(Math.max(this.mZoomOffsetX * f3, -cropWindowRect.left), (-cropWindowRect.right) + f) / f3;
                this.mZoomOffsetY = Math.min(Math.max(this.mZoomOffsetY * f4, -cropWindowRect.top), (-cropWindowRect.bottom) + f2) / f4;
            }
            this.mImageMatrix.postTranslate(this.mZoomOffsetX * f3, this.mZoomOffsetY * f4);
            cropWindowRect.offset(this.mZoomOffsetX * f3, this.mZoomOffsetY * f4);
            this.mCropOverlayView.setCropWindowRect(cropWindowRect);
            mapImagePointsByImageMatrix();
            this.mCropOverlayView.invalidate();
            if (z2) {
                this.mAnimation.setEndState(this.mImagePoints, this.mImageMatrix);
                this.mImageView.startAnimation(this.mAnimation);
            } else {
                this.mImageView.setImageMatrix(this.mImageMatrix);
            }
            updateImageBounds(false);
        }
    }

    private void mapImagePointsByImageMatrix() {
        float[] fArr = this.mImagePoints;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[2] = this.mBitmap.getWidth();
        float[] fArr2 = this.mImagePoints;
        fArr2[3] = 0.0f;
        fArr2[4] = this.mBitmap.getWidth();
        this.mImagePoints[5] = this.mBitmap.getHeight();
        float[] fArr3 = this.mImagePoints;
        fArr3[6] = 0.0f;
        fArr3[7] = this.mBitmap.getHeight();
        this.mImageMatrix.mapPoints(this.mImagePoints);
        float[] fArr4 = this.mScaleImagePoints;
        fArr4[0] = 0.0f;
        fArr4[1] = 0.0f;
        fArr4[2] = 100.0f;
        fArr4[3] = 0.0f;
        fArr4[4] = 100.0f;
        fArr4[5] = 100.0f;
        fArr4[6] = 0.0f;
        fArr4[7] = 100.0f;
        this.mImageMatrix.mapPoints(fArr4);
    }

    private static int getOnMeasureSpec(int i, int i2, int i3) {
        return i == 1073741824 ? i2 : i == Integer.MIN_VALUE ? Math.min(i3, i2) : i3;
    }

    private void setCropOverlayVisibility() {
        CropOverlayView cropOverlayView = this.mCropOverlayView;
        if (cropOverlayView != null) {
            cropOverlayView.setVisibility((!this.mShowCropOverlay || this.mBitmap == null) ? 4 : 0);
        }
    }

    private void setProgressBarVisibility() {
        this.mProgressBar.setVisibility(this.mShowProgressBar && ((this.mBitmap == null && this.mBitmapLoadingWorkerTask != null) || this.mBitmapCroppingWorkerTask != null) ? 0 : 4);
    }

    private void updateImageBounds(boolean z) {
        if (this.mBitmap != null && !z) {
            this.mCropOverlayView.setCropWindowLimits(getWidth(), getHeight(), (this.mLoadedSampleSize * 100.0f) / BitmapUtils.getRectWidth(this.mScaleImagePoints), (this.mLoadedSampleSize * 100.0f) / BitmapUtils.getRectHeight(this.mScaleImagePoints));
        }
        this.mCropOverlayView.setBounds(z ? null : this.mImagePoints, getWidth(), getHeight());
    }

    public static class CropResult {
        private final Bitmap mBitmap;
        private final float[] mCropPoints;
        private final Rect mCropRect;
        private final Exception mError;
        private final Bitmap mOriginalBitmap;
        private final Uri mOriginalUri;
        private final int mRotation;
        private final int mSampleSize;
        private final Uri mUri;
        private final Rect mWholeImageRect;

        CropResult(Bitmap bitmap, Uri uri, Bitmap bitmap2, Uri uri2, Exception exc, float[] fArr, Rect rect, Rect rect2, int i, int i2) {
            this.mOriginalBitmap = bitmap;
            this.mOriginalUri = uri;
            this.mBitmap = bitmap2;
            this.mUri = uri2;
            this.mError = exc;
            this.mCropPoints = fArr;
            this.mCropRect = rect;
            this.mWholeImageRect = rect2;
            this.mRotation = i;
            this.mSampleSize = i2;
        }

        public Bitmap getOriginalBitmap() {
            return this.mOriginalBitmap;
        }

        public Uri getOriginalUri() {
            return this.mOriginalUri;
        }

        public boolean isSuccessful() {
            return this.mError == null;
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public Exception getError() {
            return this.mError;
        }

        public float[] getCropPoints() {
            return this.mCropPoints;
        }

        public Rect getCropRect() {
            return this.mCropRect;
        }

        public Rect getWholeImageRect() {
            return this.mWholeImageRect;
        }

        public int getRotation() {
            return this.mRotation;
        }

        public int getSampleSize() {
            return this.mSampleSize;
        }
    }
}
