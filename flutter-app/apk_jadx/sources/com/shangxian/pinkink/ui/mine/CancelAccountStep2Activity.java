package com.shangxian.pinkink.ui.mine;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.lazyee.klib.app.AppManager;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.mvvm.ViewModel;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.databinding.ActivityCancelAccountStep2Binding;
import com.shangxian.pinkink.mvvm.viewmodel.UserViewModel;
import com.shangxian.pinkink.ui.login.LoginActivity;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CancelAccountStep2Activity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\r\u001a\u00020\u000bH\u0016R\u001b\u0010\u0004\u001a\u00020\u00058CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lcom/shangxian/pinkink/ui/mine/CancelAccountStep2Activity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityCancelAccountStep2Binding;", "()V", "userViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "getUserViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "userViewModel$delegate", "Lkotlin/Lazy;", "cancelUser", "", "getCancelUserImageCode", "initView", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class CancelAccountStep2Activity extends BaseActivity<ActivityCancelAccountStep2Binding> {

    /* JADX INFO: renamed from: userViewModel$delegate, reason: from kotlin metadata */
    private final Lazy userViewModel = LazyKt.lazy(new Function0<UserViewModel>() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep2Activity$userViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UserViewModel invoke() {
            return (UserViewModel) new ViewModelProvider(this.this$0).get(UserViewModel.class);
        }
    });

    @ViewModel
    private final UserViewModel getUserViewModel() {
        return (UserViewModel) this.userViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        ActivityCancelAccountStep2Binding activityCancelAccountStep2Binding = (ActivityCancelAccountStep2Binding) getMViewBinding();
        activityCancelAccountStep2Binding.titleBar.tvTitle.setText(getString(R.string.title_apply_account_cancellation_step1));
        activityCancelAccountStep2Binding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep2Activity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CancelAccountStep2Activity.m288initView$lambda3$lambda0(this.f$0, view);
            }
        });
        activityCancelAccountStep2Binding.ivGetVerifyCode.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep2Activity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CancelAccountStep2Activity.m289initView$lambda3$lambda1(this.f$0, view);
            }
        });
        activityCancelAccountStep2Binding.btnConfirmCancel.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep2Activity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CancelAccountStep2Activity.m290initView$lambda3$lambda2(this.f$0, view);
            }
        });
        getCancelUserImageCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-0, reason: not valid java name */
    public static final void m288initView$lambda3$lambda0(CancelAccountStep2Activity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-1, reason: not valid java name */
    public static final void m289initView$lambda3$lambda1(CancelAccountStep2Activity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCancelUserImageCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-2, reason: not valid java name */
    public static final void m290initView$lambda3$lambda2(CancelAccountStep2Activity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.cancelUser();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getCancelUserImageCode() {
        getUserViewModel().getCancelUserImageCode(new Function1<Bitmap, Unit>() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep2Activity.getCancelUserImageCode.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Bitmap bitmap) {
                invoke2(bitmap);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Bitmap it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ((ActivityCancelAccountStep2Binding) CancelAccountStep2Activity.this.getMViewBinding()).ivGetVerifyCode.setImageBitmap(it);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void cancelUser() {
        String string = ((ActivityCancelAccountStep2Binding) getMViewBinding()).etVerifyCode.getText().toString();
        if (TextUtils.isEmpty(string)) {
            String string2 = getString(R.string.toast_verify_code_must_cannot_be_empty);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.toast…ode_must_cannot_be_empty)");
            ContextExtensionsKt.toastShort(this, string2);
            return;
        }
        getUserViewModel().cancelUser(string, new Function0<Unit>() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep2Activity.cancelUser.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws Exception {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws Exception {
                AppConfig.INSTANCE.setToken("");
                ContextExtensionsKt.goto$default(CancelAccountStep2Activity.this, LoginActivity.class, null, null, null, null, 30, null);
                AppManager.INSTANCE.finishAllExcept(LoginActivity.class);
            }
        }, new Function0<Unit>() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep2Activity.cancelUser.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                CancelAccountStep2Activity.this.getCancelUserImageCode();
            }
        });
    }
}
