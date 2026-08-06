package com.shangxian.pinkink.ui.login;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.lazyee.klib.common.SP;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.extension.StringExtensionsKt;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.ViewModel;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.databinding.ActivityLoginBinding;
import com.shangxian.pinkink.mvvm.viewmodel.UserViewModel;
import com.shangxian.pinkink.ui.main.MainActivity;
import com.shangxian.pinkink.ui.webview.CommonWebViewActivity;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* JADX INFO: compiled from: LoginActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0002J\b\u0010\u0011\u001a\u00020\u000fH\u0002J\u0018\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0007H\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\b\u0010\u001a\u001a\u00020\u0016H\u0016J\b\u0010\u001b\u001a\u00020\u0016H\u0002J\b\u0010\u001c\u001a\u00020\u0016H\u0014R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u001d"}, d2 = {"Lcom/shangxian/pinkink/ui/login/LoginActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityLoginBinding;", "()V", "countDownTimer", "Landroid/os/CountDownTimer;", "lastGetVerifyTime", "", "userViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "getUserViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "userViewModel$delegate", "Lkotlin/Lazy;", "checkAgreementCheckState", "", "checkPhone", "checkVerifyCode", "createGetVerifyCodeCountDownTimer", "millisInFuture", "countDownInterval", "getVerifyCode", "", "initGetVerifyCodeBtn", "initRequestLoginState", "initRequestVerifyCodeState", "initView", "loginOrRegister", "onDestroy", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private CountDownTimer countDownTimer;
    private long lastGetVerifyTime;

    /* JADX INFO: renamed from: userViewModel$delegate, reason: from kotlin metadata */
    private final Lazy userViewModel = LazyKt.lazy(new Function0<UserViewModel>() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$userViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UserViewModel invoke() {
            return (UserViewModel) new ViewModelProvider(this.this$0).get(UserViewModel.class);
        }
    });

    public LoginActivity() {
        SP commonSP = AppConfig.INSTANCE.getCommonSP();
        this.lastGetVerifyTime = commonSP == null ? 0L : commonSP.m97long("KEY_LAST_GET_VERIFY_CODE_TIME");
    }

    @ViewModel
    private final UserViewModel getUserViewModel() {
        return (UserViewModel) this.userViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivityLoginBinding activityLoginBinding = (ActivityLoginBinding) getMViewBinding();
        EditText etAccount = activityLoginBinding.etAccount;
        Intrinsics.checkNotNullExpressionValue(etAccount, "etAccount");
        etAccount.addTextChangedListener(new TextWatcher() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$initView$lambda-8$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence text, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                activityLoginBinding.ivAccountClear.setVisibility(TextUtils.isEmpty(activityLoginBinding.etAccount.getText()) ? 4 : 0);
            }
        });
        EditText etVerifyCode = activityLoginBinding.etVerifyCode;
        Intrinsics.checkNotNullExpressionValue(etVerifyCode, "etVerifyCode");
        etVerifyCode.addTextChangedListener(new TextWatcher() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$initView$lambda-8$$inlined$addTextChangedListener$default$2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence text, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                activityLoginBinding.ivVerifyCodeClear.setVisibility(TextUtils.isEmpty(activityLoginBinding.etVerifyCode.getText()) ? 4 : 0);
            }
        });
        activityLoginBinding.ivAccountClear.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.m205initView$lambda8$lambda2(activityLoginBinding, view);
            }
        });
        activityLoginBinding.ivVerifyCodeClear.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.m206initView$lambda8$lambda3(activityLoginBinding, view);
            }
        });
        activityLoginBinding.tvUserProtocol.setPaintFlags(activityLoginBinding.tvUserProtocol.getPaintFlags() | 8);
        activityLoginBinding.tvUserProtocol.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.m207initView$lambda8$lambda4(this.f$0, view);
            }
        });
        activityLoginBinding.tvPrivacyAgreement.setPaintFlags(activityLoginBinding.tvPrivacyAgreement.getPaintFlags() | 8);
        activityLoginBinding.tvPrivacyAgreement.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.m208initView$lambda8$lambda5(this.f$0, view);
            }
        });
        activityLoginBinding.llAgreement.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.m209initView$lambda8$lambda6(activityLoginBinding, view);
            }
        });
        activityLoginBinding.btnLoginOrRegister.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.m210initView$lambda8$lambda7(this.f$0, view);
            }
        });
        initGetVerifyCodeBtn();
        initRequestVerifyCodeState();
        initRequestLoginState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-8$lambda-2, reason: not valid java name */
    public static final void m205initView$lambda8$lambda2(ActivityLoginBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this_run.etAccount.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-8$lambda-3, reason: not valid java name */
    public static final void m206initView$lambda8$lambda3(ActivityLoginBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this_run.etVerifyCode.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-8$lambda-4, reason: not valid java name */
    public static final void m207initView$lambda8$lambda4(LoginActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CommonWebViewActivity.INSTANCE.gotoThis(this$0, AppConfig.INSTANCE.getUserProtocolUrl());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-8$lambda-5, reason: not valid java name */
    public static final void m208initView$lambda8$lambda5(LoginActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CommonWebViewActivity.INSTANCE.gotoThis(this$0, AppConfig.INSTANCE.getPrivacyAgreementUrl());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-8$lambda-6, reason: not valid java name */
    public static final void m209initView$lambda8$lambda6(ActivityLoginBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this_run.ivAgreementChecked.setSelected(!this_run.ivAgreementChecked.isSelected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-8$lambda-7, reason: not valid java name */
    public static final void m210initView$lambda8$lambda7(LoginActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.loginOrRegister();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void initGetVerifyCodeBtn() {
        ActivityLoginBinding activityLoginBinding = (ActivityLoginBinding) getMViewBinding();
        long jCurrentTimeMillis = System.currentTimeMillis() - this.lastGetVerifyTime;
        if (jCurrentTimeMillis > 60000) {
            activityLoginBinding.tvGetVerifyCode.setEnabled(true);
        } else {
            activityLoginBinding.tvGetVerifyCode.setEnabled(false);
            createGetVerifyCodeCountDownTimer(((long) 60000) - jCurrentTimeMillis, 1000L).start();
        }
        activityLoginBinding.tvGetVerifyCode.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.m202initGetVerifyCodeBtn$lambda10$lambda9(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initGetVerifyCodeBtn$lambda-10$lambda-9, reason: not valid java name */
    public static final void m202initGetVerifyCodeBtn$lambda10$lambda9(LoginActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getVerifyCode();
    }

    private final void initRequestVerifyCodeState() {
        getUserViewModel().getRequestLoginVerifyCodeStatus().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LoginActivity.m204initRequestVerifyCodeState$lambda12(this.f$0, (LoadingState) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initRequestVerifyCodeState$lambda-12, reason: not valid java name */
    public static final void m204initRequestVerifyCodeState$lambda12(LoginActivity this$0, LoadingState loadingState) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (loadingState == LoadingState.SUCCESS) {
            ((ActivityLoginBinding) this$0.getMViewBinding()).tvGetVerifyCode.setEnabled(false);
            SP commonSP = AppConfig.INSTANCE.getCommonSP();
            if (commonSP != null) {
                commonSP.put("KEY_LAST_GET_VERIFY_CODE_TIME", Long.valueOf(System.currentTimeMillis()));
            }
            this$0.createGetVerifyCodeCountDownTimer(60000L, 1000L).start();
        }
    }

    private final void initRequestLoginState() {
        getUserViewModel().getRequestLoginStatus().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.login.LoginActivity$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LoginActivity.m203initRequestLoginState$lambda13(this.f$0, (LoadingState) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initRequestLoginState$lambda-13, reason: not valid java name */
    public static final void m203initRequestLoginState$lambda13(LoginActivity this$0, LoadingState loadingState) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (loadingState == LoadingState.SUCCESS) {
            MainActivity.INSTANCE.gotoThis(this$0);
            this$0.finish();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void getVerifyCode() {
        if (checkPhone()) {
            getUserViewModel().getLoginVerifyCode(((ActivityLoginBinding) getMViewBinding()).etAccount.getText().toString());
        }
    }

    private final CountDownTimer createGetVerifyCodeCountDownTimer(long millisInFuture, long countDownInterval) {
        CountDownTimer countDownTimer = new CountDownTimer(millisInFuture, countDownInterval, this) { // from class: com.shangxian.pinkink.ui.login.LoginActivity.createGetVerifyCodeCountDownTimer.1
            final /* synthetic */ long $countDownInterval;
            final /* synthetic */ long $millisInFuture;
            final /* synthetic */ LoginActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(millisInFuture, countDownInterval);
                this.$millisInFuture = millisInFuture;
                this.$countDownInterval = countDownInterval;
                this.this$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.os.CountDownTimer
            public void onTick(long time) {
                TextView textView = ((ActivityLoginBinding) this.this$0.getMViewBinding()).tvGetVerifyCode;
                StringBuilder sb = new StringBuilder();
                sb.append(time / ((long) 1000));
                sb.append('s');
                textView.setText(sb.toString());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.os.CountDownTimer
            public void onFinish() {
                ((ActivityLoginBinding) this.this$0.getMViewBinding()).tvGetVerifyCode.setText(this.this$0.getString(R.string.string_get_verify_code));
                ((ActivityLoginBinding) this.this$0.getMViewBinding()).tvGetVerifyCode.setEnabled(true);
            }
        };
        this.countDownTimer = countDownTimer;
        Intrinsics.checkNotNull(countDownTimer);
        return countDownTimer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean checkVerifyCode() {
        if (new Regex("^\\d{4}$").matches(((ActivityLoginBinding) getMViewBinding()).etVerifyCode.getText().toString())) {
            return true;
        }
        String string = getString(R.string.toast_verify_code_error);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast_verify_code_error)");
        ContextExtensionsKt.toastShort(this, string);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean checkPhone() {
        String string = ((ActivityLoginBinding) getMViewBinding()).etAccount.getText().toString();
        if (StringExtensionsKt.isChinaPhoneLegal(string) || StringExtensionsKt.isEmailLegal(string)) {
            return true;
        }
        String string2 = getString(R.string.toast_account_format_error);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.toast_account_format_error)");
        ContextExtensionsKt.toastShort(this, string2);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean checkAgreementCheckState() {
        if (((ActivityLoginBinding) getMViewBinding()).ivAgreementChecked.isSelected()) {
            return true;
        }
        String string = getString(R.string.toast_must_agree_protocol);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast_must_agree_protocol)");
        ContextExtensionsKt.toastShort(this, string);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void loginOrRegister() {
        if (checkPhone() && checkVerifyCode() && checkAgreementCheckState()) {
            ActivityLoginBinding activityLoginBinding = (ActivityLoginBinding) getMViewBinding();
            getUserViewModel().phoneLogin(activityLoginBinding.etAccount.getText().toString(), activityLoginBinding.etVerifyCode.getText().toString());
        }
    }

    @Override // com.lazyee.klib.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer == null) {
            return;
        }
        countDownTimer.cancel();
    }
}
