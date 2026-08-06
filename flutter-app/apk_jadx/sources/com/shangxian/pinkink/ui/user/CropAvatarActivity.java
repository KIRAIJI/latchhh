package com.shangxian.pinkink.ui.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.lazyee.klib.mvvm.ViewModel;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityCropAvatarBinding;
import com.shangxian.pinkink.mvvm.viewmodel.UserViewModel;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: CropAvatarActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u001d\u0010\u0004\u001a\u0004\u0018\u00010\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u000b8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/shangxian/pinkink/ui/user/CropAvatarActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityCropAvatarBinding;", "()V", PictureMimeType.MIME_TYPE_PREFIX_IMAGE, "Lcom/luck/picture/lib/entity/LocalMedia;", "getImage", "()Lcom/luck/picture/lib/entity/LocalMedia;", "image$delegate", "Lkotlin/Lazy;", "userViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "getUserViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "userViewModel$delegate", "initView", "", "setBitmapToView", "realPath", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class CropAvatarActivity extends BaseActivity<ActivityCropAvatarBinding> {

    /* JADX INFO: renamed from: userViewModel$delegate, reason: from kotlin metadata */
    private final Lazy userViewModel = LazyKt.lazy(new Function0<UserViewModel>() { // from class: com.shangxian.pinkink.ui.user.CropAvatarActivity$userViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UserViewModel invoke() {
            return (UserViewModel) new ViewModelProvider(this.this$0).get(UserViewModel.class);
        }
    });

    /* JADX INFO: renamed from: image$delegate, reason: from kotlin metadata */
    private final Lazy image = LazyKt.lazy(new Function0<LocalMedia>() { // from class: com.shangxian.pinkink.ui.user.CropAvatarActivity$image$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final LocalMedia invoke() {
            return (LocalMedia) this.this$0.getIntent().getParcelableExtra(Keys.LOCAL_MEDIA);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    @ViewModel
    public final UserViewModel getUserViewModel() {
        return (UserViewModel) this.userViewModel.getValue();
    }

    private final LocalMedia getImage() {
        return (LocalMedia) this.image.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivityCropAvatarBinding activityCropAvatarBinding = (ActivityCropAvatarBinding) getMViewBinding();
        activityCropAvatarBinding.titleBar.tvTitle.setText(getString(R.string.title_crop_image));
        activityCropAvatarBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.user.CropAvatarActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CropAvatarActivity.m373initView$lambda2$lambda0(this.f$0, view);
            }
        });
        LocalMedia image = getImage();
        Intrinsics.checkNotNull(image);
        String realPath = image.getRealPath();
        Intrinsics.checkNotNullExpressionValue(realPath, "image!!.realPath");
        setBitmapToView(realPath);
        activityCropAvatarBinding.llCropImageComplete.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.user.CropAvatarActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CropAvatarActivity.m374initView$lambda2$lambda1(this.f$0, activityCropAvatarBinding, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-0, reason: not valid java name */
    public static final void m373initView$lambda2$lambda0(CropAvatarActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-1, reason: not valid java name */
    public static final void m374initView$lambda2$lambda1(CropAvatarActivity this$0, ActivityCropAvatarBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new CropAvatarActivity$initView$1$2$1(this$0, this_run, null), 3, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void setBitmapToView(String realPath) {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(realPath);
        ActivityCropAvatarBinding activityCropAvatarBinding = (ActivityCropAvatarBinding) getMViewBinding();
        activityCropAvatarBinding.cropImageView.setAspectRatio(1, 1);
        activityCropAvatarBinding.cropImageView.setImageBitmap(bitmapDecodeFile);
    }
}
