package com.theartofdev.edmodo.cropper;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes.dex */
final class CropWindowMoveHandler {
    private static final Matrix MATRIX = new Matrix();
    private final float mMaxCropHeight;
    private final float mMaxCropWidth;
    private final float mMinCropHeight;
    private final float mMinCropWidth;
    private final PointF mTouchOffset = new PointF();
    private final Type mType;

    public enum Type {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        CENTER
    }

    private static float calculateAspectRatio(float f, float f2, float f3, float f4) {
        return (f3 - f) / (f4 - f2);
    }

    public CropWindowMoveHandler(Type type, CropWindowHandler cropWindowHandler, float f, float f2) {
        this.mType = type;
        this.mMinCropWidth = cropWindowHandler.getMinCropWidth();
        this.mMinCropHeight = cropWindowHandler.getMinCropHeight();
        this.mMaxCropWidth = cropWindowHandler.getMaxCropWidth();
        this.mMaxCropHeight = cropWindowHandler.getMaxCropHeight();
        calculateTouchOffset(cropWindowHandler.getRect(), f, f2);
    }

    public void move(RectF rectF, float f, float f2, RectF rectF2, int i, int i2, float f3, boolean z, float f4) {
        float f5 = f + this.mTouchOffset.x;
        float f6 = f2 + this.mTouchOffset.y;
        if (this.mType == Type.CENTER) {
            moveCenter(rectF, f5, f6, rectF2, i, i2, f3);
        } else if (z) {
            moveSizeWithFixedAspectRatio(rectF, f5, f6, rectF2, i, i2, f3, f4);
        } else {
            moveSizeWithFreeAspectRatio(rectF, f5, f6, rectF2, i, i2, f3);
        }
    }

    /* JADX INFO: renamed from: com.theartofdev.edmodo.cropper.CropWindowMoveHandler$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type = iArr;
            try {
                iArr[Type.TOP_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[Type.TOP_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[Type.BOTTOM_LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[Type.BOTTOM_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[Type.LEFT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[Type.TOP.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[Type.RIGHT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[Type.BOTTOM.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[Type.CENTER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    private void calculateTouchOffset(RectF rectF, float f, float f2) {
        float fCenterY;
        float f3;
        float f4;
        float fCenterX = 0.0f;
        switch (AnonymousClass1.$SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[this.mType.ordinal()]) {
            case 1:
                fCenterX = rectF.left - f;
                fCenterY = rectF.top;
                f4 = fCenterY - f2;
                break;
            case 2:
                fCenterX = rectF.right - f;
                fCenterY = rectF.top;
                f4 = fCenterY - f2;
                break;
            case 3:
                fCenterX = rectF.left - f;
                fCenterY = rectF.bottom;
                f4 = fCenterY - f2;
                break;
            case 4:
                fCenterX = rectF.right - f;
                fCenterY = rectF.bottom;
                f4 = fCenterY - f2;
                break;
            case 5:
                f3 = rectF.left;
                fCenterX = f3 - f;
                f4 = 0.0f;
                break;
            case 6:
                fCenterY = rectF.top;
                f4 = fCenterY - f2;
                break;
            case 7:
                f3 = rectF.right;
                fCenterX = f3 - f;
                f4 = 0.0f;
                break;
            case 8:
                fCenterY = rectF.bottom;
                f4 = fCenterY - f2;
                break;
            case 9:
                fCenterX = rectF.centerX() - f;
                fCenterY = rectF.centerY();
                f4 = fCenterY - f2;
                break;
            default:
                f4 = 0.0f;
                break;
        }
        this.mTouchOffset.x = fCenterX;
        this.mTouchOffset.y = f4;
    }

    private void moveCenter(RectF rectF, float f, float f2, RectF rectF2, int i, int i2, float f3) {
        float fCenterX = f - rectF.centerX();
        float fCenterY = f2 - rectF.centerY();
        if (rectF.left + fCenterX < 0.0f || rectF.right + fCenterX > i || rectF.left + fCenterX < rectF2.left || rectF.right + fCenterX > rectF2.right) {
            fCenterX /= 1.05f;
            this.mTouchOffset.x -= fCenterX / 2.0f;
        }
        if (rectF.top + fCenterY < 0.0f || rectF.bottom + fCenterY > i2 || rectF.top + fCenterY < rectF2.top || rectF.bottom + fCenterY > rectF2.bottom) {
            fCenterY /= 1.05f;
            this.mTouchOffset.y -= fCenterY / 2.0f;
        }
        rectF.offset(fCenterX, fCenterY);
        snapEdgesToBounds(rectF, rectF2, f3);
    }

    private void moveSizeWithFreeAspectRatio(RectF rectF, float f, float f2, RectF rectF2, int i, int i2, float f3) {
        switch (AnonymousClass1.$SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[this.mType.ordinal()]) {
            case 1:
                adjustTop(rectF, f2, rectF2, f3, 0.0f, false, false);
                adjustLeft(rectF, f, rectF2, f3, 0.0f, false, false);
                break;
            case 2:
                adjustTop(rectF, f2, rectF2, f3, 0.0f, false, false);
                adjustRight(rectF, f, rectF2, i, f3, 0.0f, false, false);
                break;
            case 3:
                adjustBottom(rectF, f2, rectF2, i2, f3, 0.0f, false, false);
                adjustLeft(rectF, f, rectF2, f3, 0.0f, false, false);
                break;
            case 4:
                adjustBottom(rectF, f2, rectF2, i2, f3, 0.0f, false, false);
                adjustRight(rectF, f, rectF2, i, f3, 0.0f, false, false);
                break;
            case 5:
                adjustLeft(rectF, f, rectF2, f3, 0.0f, false, false);
                break;
            case 6:
                adjustTop(rectF, f2, rectF2, f3, 0.0f, false, false);
                break;
            case 7:
                adjustRight(rectF, f, rectF2, i, f3, 0.0f, false, false);
                break;
            case 8:
                adjustBottom(rectF, f2, rectF2, i2, f3, 0.0f, false, false);
                break;
        }
    }

    private void moveSizeWithFixedAspectRatio(RectF rectF, float f, float f2, RectF rectF2, int i, int i2, float f3, float f4) {
        switch (AnonymousClass1.$SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[this.mType.ordinal()]) {
            case 1:
                if (calculateAspectRatio(f, f2, rectF.right, rectF.bottom) < f4) {
                    adjustTop(rectF, f2, rectF2, f3, f4, true, false);
                    adjustLeftByAspectRatio(rectF, f4);
                } else {
                    adjustLeft(rectF, f, rectF2, f3, f4, true, false);
                    adjustTopByAspectRatio(rectF, f4);
                }
                break;
            case 2:
                if (calculateAspectRatio(rectF.left, f2, f, rectF.bottom) < f4) {
                    adjustTop(rectF, f2, rectF2, f3, f4, false, true);
                    adjustRightByAspectRatio(rectF, f4);
                } else {
                    adjustRight(rectF, f, rectF2, i, f3, f4, true, false);
                    adjustTopByAspectRatio(rectF, f4);
                }
                break;
            case 3:
                if (calculateAspectRatio(f, rectF.top, rectF.right, f2) < f4) {
                    adjustBottom(rectF, f2, rectF2, i2, f3, f4, true, false);
                    adjustLeftByAspectRatio(rectF, f4);
                } else {
                    adjustLeft(rectF, f, rectF2, f3, f4, false, true);
                    adjustBottomByAspectRatio(rectF, f4);
                }
                break;
            case 4:
                if (calculateAspectRatio(rectF.left, rectF.top, f, f2) < f4) {
                    adjustBottom(rectF, f2, rectF2, i2, f3, f4, false, true);
                    adjustRightByAspectRatio(rectF, f4);
                } else {
                    adjustRight(rectF, f, rectF2, i, f3, f4, false, true);
                    adjustBottomByAspectRatio(rectF, f4);
                }
                break;
            case 5:
                adjustLeft(rectF, f, rectF2, f3, f4, true, true);
                adjustTopBottomByAspectRatio(rectF, rectF2, f4);
                break;
            case 6:
                adjustTop(rectF, f2, rectF2, f3, f4, true, true);
                adjustLeftRightByAspectRatio(rectF, rectF2, f4);
                break;
            case 7:
                adjustRight(rectF, f, rectF2, i, f3, f4, true, true);
                adjustTopBottomByAspectRatio(rectF, rectF2, f4);
                break;
            case 8:
                adjustBottom(rectF, f2, rectF2, i2, f3, f4, true, true);
                adjustLeftRightByAspectRatio(rectF, rectF2, f4);
                break;
        }
    }

    private void snapEdgesToBounds(RectF rectF, RectF rectF2, float f) {
        if (rectF.left < rectF2.left + f) {
            rectF.offset(rectF2.left - rectF.left, 0.0f);
        }
        if (rectF.top < rectF2.top + f) {
            rectF.offset(0.0f, rectF2.top - rectF.top);
        }
        if (rectF.right > rectF2.right - f) {
            rectF.offset(rectF2.right - rectF.right, 0.0f);
        }
        if (rectF.bottom > rectF2.bottom - f) {
            rectF.offset(0.0f, rectF2.bottom - rectF.bottom);
        }
    }

    private void adjustLeft(RectF rectF, float f, RectF rectF2, float f2, float f3, boolean z, boolean z2) {
        if (f < 0.0f) {
            f /= 1.05f;
            this.mTouchOffset.x -= f / 1.1f;
        }
        if (f < rectF2.left) {
            this.mTouchOffset.x -= (f - rectF2.left) / 2.0f;
        }
        if (f - rectF2.left < f2) {
            f = rectF2.left;
        }
        if (rectF.right - f < this.mMinCropWidth) {
            f = rectF.right - this.mMinCropWidth;
        }
        if (rectF.right - f > this.mMaxCropWidth) {
            f = rectF.right - this.mMaxCropWidth;
        }
        if (f - rectF2.left < f2) {
            f = rectF2.left;
        }
        if (f3 > 0.0f) {
            float f4 = (rectF.right - f) / f3;
            if (f4 < this.mMinCropHeight) {
                f = Math.max(rectF2.left, rectF.right - (this.mMinCropHeight * f3));
                f4 = (rectF.right - f) / f3;
            }
            if (f4 > this.mMaxCropHeight) {
                f = Math.max(rectF2.left, rectF.right - (this.mMaxCropHeight * f3));
                f4 = (rectF.right - f) / f3;
            }
            if (z && z2) {
                f = Math.max(f, Math.max(rectF2.left, rectF.right - (rectF2.height() * f3)));
            } else {
                if (z && rectF.bottom - f4 < rectF2.top) {
                    f = Math.max(rectF2.left, rectF.right - ((rectF.bottom - rectF2.top) * f3));
                    f4 = (rectF.right - f) / f3;
                }
                if (z2 && rectF.top + f4 > rectF2.bottom) {
                    f = Math.max(f, Math.max(rectF2.left, rectF.right - ((rectF2.bottom - rectF.top) * f3)));
                }
            }
        }
        rectF.left = f;
    }

    private void adjustRight(RectF rectF, float f, RectF rectF2, int i, float f2, float f3, boolean z, boolean z2) {
        float f4 = i;
        if (f > f4) {
            f = ((f - f4) / 1.05f) + f4;
            this.mTouchOffset.x -= (f - f4) / 1.1f;
        }
        if (f > rectF2.right) {
            this.mTouchOffset.x -= (f - rectF2.right) / 2.0f;
        }
        if (rectF2.right - f < f2) {
            f = rectF2.right;
        }
        if (f - rectF.left < this.mMinCropWidth) {
            f = rectF.left + this.mMinCropWidth;
        }
        if (f - rectF.left > this.mMaxCropWidth) {
            f = rectF.left + this.mMaxCropWidth;
        }
        if (rectF2.right - f < f2) {
            f = rectF2.right;
        }
        if (f3 > 0.0f) {
            float f5 = (f - rectF.left) / f3;
            if (f5 < this.mMinCropHeight) {
                f = Math.min(rectF2.right, rectF.left + (this.mMinCropHeight * f3));
                f5 = (f - rectF.left) / f3;
            }
            if (f5 > this.mMaxCropHeight) {
                f = Math.min(rectF2.right, rectF.left + (this.mMaxCropHeight * f3));
                f5 = (f - rectF.left) / f3;
            }
            if (z && z2) {
                f = Math.min(f, Math.min(rectF2.right, rectF.left + (rectF2.height() * f3)));
            } else {
                if (z && rectF.bottom - f5 < rectF2.top) {
                    f = Math.min(rectF2.right, rectF.left + ((rectF.bottom - rectF2.top) * f3));
                    f5 = (f - rectF.left) / f3;
                }
                if (z2 && rectF.top + f5 > rectF2.bottom) {
                    f = Math.min(f, Math.min(rectF2.right, rectF.left + ((rectF2.bottom - rectF.top) * f3)));
                }
            }
        }
        rectF.right = f;
    }

    private void adjustTop(RectF rectF, float f, RectF rectF2, float f2, float f3, boolean z, boolean z2) {
        if (f < 0.0f) {
            f /= 1.05f;
            this.mTouchOffset.y -= f / 1.1f;
        }
        if (f < rectF2.top) {
            this.mTouchOffset.y -= (f - rectF2.top) / 2.0f;
        }
        if (f - rectF2.top < f2) {
            f = rectF2.top;
        }
        if (rectF.bottom - f < this.mMinCropHeight) {
            f = rectF.bottom - this.mMinCropHeight;
        }
        if (rectF.bottom - f > this.mMaxCropHeight) {
            f = rectF.bottom - this.mMaxCropHeight;
        }
        if (f - rectF2.top < f2) {
            f = rectF2.top;
        }
        if (f3 > 0.0f) {
            float f4 = (rectF.bottom - f) * f3;
            if (f4 < this.mMinCropWidth) {
                f = Math.max(rectF2.top, rectF.bottom - (this.mMinCropWidth / f3));
                f4 = (rectF.bottom - f) * f3;
            }
            if (f4 > this.mMaxCropWidth) {
                f = Math.max(rectF2.top, rectF.bottom - (this.mMaxCropWidth / f3));
                f4 = (rectF.bottom - f) * f3;
            }
            if (z && z2) {
                f = Math.max(f, Math.max(rectF2.top, rectF.bottom - (rectF2.width() / f3)));
            } else {
                if (z && rectF.right - f4 < rectF2.left) {
                    f = Math.max(rectF2.top, rectF.bottom - ((rectF.right - rectF2.left) / f3));
                    f4 = (rectF.bottom - f) * f3;
                }
                if (z2 && rectF.left + f4 > rectF2.right) {
                    f = Math.max(f, Math.max(rectF2.top, rectF.bottom - ((rectF2.right - rectF.left) / f3)));
                }
            }
        }
        rectF.top = f;
    }

    private void adjustBottom(RectF rectF, float f, RectF rectF2, int i, float f2, float f3, boolean z, boolean z2) {
        float f4 = i;
        if (f > f4) {
            f = ((f - f4) / 1.05f) + f4;
            this.mTouchOffset.y -= (f - f4) / 1.1f;
        }
        if (f > rectF2.bottom) {
            this.mTouchOffset.y -= (f - rectF2.bottom) / 2.0f;
        }
        if (rectF2.bottom - f < f2) {
            f = rectF2.bottom;
        }
        if (f - rectF.top < this.mMinCropHeight) {
            f = rectF.top + this.mMinCropHeight;
        }
        if (f - rectF.top > this.mMaxCropHeight) {
            f = rectF.top + this.mMaxCropHeight;
        }
        if (rectF2.bottom - f < f2) {
            f = rectF2.bottom;
        }
        if (f3 > 0.0f) {
            float f5 = (f - rectF.top) * f3;
            if (f5 < this.mMinCropWidth) {
                f = Math.min(rectF2.bottom, rectF.top + (this.mMinCropWidth / f3));
                f5 = (f - rectF.top) * f3;
            }
            if (f5 > this.mMaxCropWidth) {
                f = Math.min(rectF2.bottom, rectF.top + (this.mMaxCropWidth / f3));
                f5 = (f - rectF.top) * f3;
            }
            if (z && z2) {
                f = Math.min(f, Math.min(rectF2.bottom, rectF.top + (rectF2.width() / f3)));
            } else {
                if (z && rectF.right - f5 < rectF2.left) {
                    f = Math.min(rectF2.bottom, rectF.top + ((rectF.right - rectF2.left) / f3));
                    f5 = (f - rectF.top) * f3;
                }
                if (z2 && rectF.left + f5 > rectF2.right) {
                    f = Math.min(f, Math.min(rectF2.bottom, rectF.top + ((rectF2.right - rectF.left) / f3)));
                }
            }
        }
        rectF.bottom = f;
    }

    private void adjustLeftByAspectRatio(RectF rectF, float f) {
        rectF.left = rectF.right - (rectF.height() * f);
    }

    private void adjustTopByAspectRatio(RectF rectF, float f) {
        rectF.top = rectF.bottom - (rectF.width() / f);
    }

    private void adjustRightByAspectRatio(RectF rectF, float f) {
        rectF.right = rectF.left + (rectF.height() * f);
    }

    private void adjustBottomByAspectRatio(RectF rectF, float f) {
        rectF.bottom = rectF.top + (rectF.width() / f);
    }

    private void adjustLeftRightByAspectRatio(RectF rectF, RectF rectF2, float f) {
        rectF.inset((rectF.width() - (rectF.height() * f)) / 2.0f, 0.0f);
        if (rectF.left < rectF2.left) {
            rectF.offset(rectF2.left - rectF.left, 0.0f);
        }
        if (rectF.right > rectF2.right) {
            rectF.offset(rectF2.right - rectF.right, 0.0f);
        }
    }

    private void adjustTopBottomByAspectRatio(RectF rectF, RectF rectF2, float f) {
        rectF.inset(0.0f, (rectF.height() - (rectF.width() / f)) / 2.0f);
        if (rectF.top < rectF2.top) {
            rectF.offset(0.0f, rectF2.top - rectF.top);
        }
        if (rectF.bottom > rectF2.bottom) {
            rectF.offset(0.0f, rectF2.bottom - rectF.bottom);
        }
    }
}
