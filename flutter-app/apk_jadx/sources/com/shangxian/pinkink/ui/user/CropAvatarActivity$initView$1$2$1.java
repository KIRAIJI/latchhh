package com.shangxian.pinkink.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityCropAvatarBinding;
import com.shangxian.pinkink.mvvm.viewmodel.UserViewModel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: CropAvatarActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.ui.user.CropAvatarActivity$initView$1$2$1", f = "CropAvatarActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class CropAvatarActivity$initView$1$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ActivityCropAvatarBinding $this_run;
    int label;
    final /* synthetic */ CropAvatarActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CropAvatarActivity$initView$1$2$1(CropAvatarActivity cropAvatarActivity, ActivityCropAvatarBinding activityCropAvatarBinding, Continuation<? super CropAvatarActivity$initView$1$2$1> continuation) {
        super(2, continuation);
        this.this$0 = cropAvatarActivity;
        this.$this_run = activityCropAvatarBinding;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CropAvatarActivity$initView$1$2$1(this.this$0, this.$this_run, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CropAvatarActivity$initView$1$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        UserViewModel userViewModel = this.this$0.getUserViewModel();
        Bitmap croppedImage = this.$this_run.cropImageView.getCroppedImage();
        Intrinsics.checkNotNullExpressionValue(croppedImage, "cropImageView.croppedImage");
        final CropAvatarActivity cropAvatarActivity = this.this$0;
        userViewModel.updateAvatar(croppedImage, new Function1<String, Unit>() { // from class: com.shangxian.pinkink.ui.user.CropAvatarActivity$initView$1$2$1.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intent intent = new Intent();
                intent.putExtra(Keys.URL, it);
                cropAvatarActivity.setResult(-1, intent);
                cropAvatarActivity.finish();
            }
        });
        return Unit.INSTANCE;
    }
}
