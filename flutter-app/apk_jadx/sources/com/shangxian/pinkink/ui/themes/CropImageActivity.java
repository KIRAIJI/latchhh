package com.shangxian.pinkink.ui.themes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.widget.LinearLayout;
import com.lazyee.klib.extension.BitmapExtensionsKt;
import com.luck.picture.lib.config.CustomIntentKey;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.InkScreenSize;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityCropImageBinding;
import java.io.File;
import java.io.IOException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CropImageActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0002R\u001d\u0010\u0004\u001a\u0004\u0018\u00010\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\t\u001a\u0004\b\r\u0010\u000fR\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/CropImageActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityCropImageBinding;", "()V", PictureMimeType.MIME_TYPE_PREFIX_IMAGE, "Lcom/luck/picture/lib/entity/LocalMedia;", "getImage", "()Lcom/luck/picture/lib/entity/LocalMedia;", "image$delegate", "Lkotlin/Lazy;", CustomIntentKey.EXTRA_IMAGE_HEIGHT, "", CustomIntentKey.EXTRA_IMAGE_WIDTH, "isAddBackground", "", "()Z", "isAddBackground$delegate", "isHorizontalCrop", "initView", "", "updateCropAspectRatio", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class CropImageActivity extends BaseActivity<ActivityCropImageBinding> {
    private int imageHeight;
    private int imageWidth;
    private boolean isHorizontalCrop;

    /* JADX INFO: renamed from: image$delegate, reason: from kotlin metadata */
    private final Lazy image = LazyKt.lazy(new Function0<LocalMedia>() { // from class: com.shangxian.pinkink.ui.themes.CropImageActivity$image$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final LocalMedia invoke() {
            return (LocalMedia) this.this$0.getIntent().getParcelableExtra(Keys.LOCAL_MEDIA);
        }
    });

    /* JADX INFO: renamed from: isAddBackground$delegate, reason: from kotlin metadata */
    private final Lazy isAddBackground = LazyKt.lazy(new Function0<Boolean>() { // from class: com.shangxian.pinkink.ui.themes.CropImageActivity.isAddBackground.2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(CropImageActivity.this.getIntent().getBooleanExtra(Keys.FLAG, false));
        }
    });

    private final LocalMedia getImage() {
        return (LocalMedia) this.image.getValue();
    }

    private final boolean isAddBackground() {
        return ((Boolean) this.isAddBackground.getValue()).booleanValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivityCropImageBinding activityCropImageBinding = (ActivityCropImageBinding) getMViewBinding();
        activityCropImageBinding.titleBar.tvTitle.setText(getString(R.string.title_crop_image));
        activityCropImageBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.CropImageActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CropImageActivity.m303initView$lambda4$lambda0(this.f$0, view);
            }
        });
        activityCropImageBinding.cropImageView.post(new Runnable() { // from class: com.shangxian.pinkink.ui.themes.CropImageActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CropImageActivity.m304initView$lambda4$lambda1(activityCropImageBinding, this);
            }
        });
        activityCropImageBinding.llRotateCropRect.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.CropImageActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CropImageActivity.m305initView$lambda4$lambda2(this.f$0, view);
            }
        });
        activityCropImageBinding.llCropImageComplete.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.CropImageActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                CropImageActivity.m306initView$lambda4$lambda3(activityCropImageBinding, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-0, reason: not valid java name */
    public static final void m303initView$lambda4$lambda0(CropImageActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-1, reason: not valid java name */
    public static final void m304initView$lambda4$lambda1(ActivityCropImageBinding this_run, CropImageActivity this$0) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int measuredHeight = this_run.llContent.getMeasuredHeight();
        int measuredWidth = this_run.llContent.getMeasuredWidth();
        LocalMedia image = this$0.getImage();
        Intrinsics.checkNotNull(image);
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(image.getRealPath());
        if (bitmapDecodeFile == null) {
            return;
        }
        this$0.imageWidth = bitmapDecodeFile.getWidth();
        int height = bitmapDecodeFile.getHeight();
        this$0.imageHeight = height;
        int i = this$0.imageWidth;
        float f = i / height;
        if (i > height) {
            this_run.cropImageView.setLayoutParams(new LinearLayout.LayoutParams(measuredWidth, (int) (measuredWidth / f)));
        } else {
            this_run.cropImageView.setLayoutParams(new LinearLayout.LayoutParams((int) (measuredHeight * f), measuredHeight));
        }
        this$0.isHorizontalCrop = this$0.imageWidth > this$0.imageHeight;
        this$0.updateCropAspectRatio();
        this_run.cropImageView.setImageBitmap(bitmapDecodeFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-2, reason: not valid java name */
    public static final void m305initView$lambda4$lambda2(CropImageActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isHorizontalCrop = !this$0.isHorizontalCrop;
        this$0.updateCropAspectRatio();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-3, reason: not valid java name */
    public static final void m306initView$lambda4$lambda3(ActivityCropImageBinding this_run, CropImageActivity this$0, View view) throws IOException {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Bitmap bitmap = this_run.cropImageView.getCroppedImage();
        if (this$0.isAddBackground() && bitmap.getWidth() > bitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90.0f);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        File file = new File(AppConfig.INSTANCE.getImageSavePath() + System.currentTimeMillis() + PictureMimeType.PNG);
        Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
        BitmapExtensionsKt.savePNGFile$default(bitmap, file, 0, 2, null);
        Intent intent = new Intent();
        intent.putExtra(Keys.FILE_PATH, file.getAbsolutePath());
        this$0.setResult(-1, intent);
        this$0.finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void updateCropAspectRatio() {
        InkScreenSize currentInkScreenSize = AppConfig.INSTANCE.getCurrentInkScreenSize();
        if (this.isHorizontalCrop) {
            ((ActivityCropImageBinding) getMViewBinding()).cropImageView.setAspectRatio(currentInkScreenSize.getHeight(), currentInkScreenSize.getWidth());
        } else {
            ((ActivityCropImageBinding) getMViewBinding()).cropImageView.setAspectRatio(currentInkScreenSize.getWidth(), currentInkScreenSize.getHeight());
        }
    }
}
