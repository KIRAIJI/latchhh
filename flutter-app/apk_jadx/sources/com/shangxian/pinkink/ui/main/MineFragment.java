package com.shangxian.pinkink.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lazyee.klib.app.AppManager;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.manager.LocaleManager;
import com.lazyee.klib.mvvm.ViewModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.app.GlideEngine;
import com.shangxian.pinkink.base.BaseFragment;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.FragmentMineBinding;
import com.shangxian.pinkink.event.DeviceTypeChangedEvent;
import com.shangxian.pinkink.mvvm.viewmodel.UserViewModel;
import com.shangxian.pinkink.ui.dialog.SwitchDeviceTypeDialog;
import com.shangxian.pinkink.ui.dialog.UpdateNickNameDialog;
import com.shangxian.pinkink.ui.login.LoginActivity;
import com.shangxian.pinkink.ui.mine.AboutUsActivity;
import com.shangxian.pinkink.ui.mine.CancelAccountStep1Activity;
import com.shangxian.pinkink.ui.mine.FontLibraryActivity;
import com.shangxian.pinkink.ui.mine.UserFontLibraryActivity;
import com.shangxian.pinkink.ui.themes.UserLikeThemesActivity;
import com.shangxian.pinkink.ui.themes.UserThemesActivity;
import com.shangxian.pinkink.ui.user.CropAvatarActivity;
import com.shangxian.pinkink.ui.webview.CommonWebViewActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: compiled from: MineFragment.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0015H\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0015H\u0002J\b\u0010\u001a\u001a\u00020\u0015H\u0002J\b\u0010\u001b\u001a\u00020\u0015H\u0002J\b\u0010\u001c\u001a\u00020\u0015H\u0002R\u001c\u0010\u0004\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001d"}, d2 = {"Lcom/shangxian/pinkink/ui/main/MineFragment;", "Lcom/shangxian/pinkink/base/BaseFragment;", "Lcom/shangxian/pinkink/databinding/FragmentMineBinding;", "()V", "addImageActivityResult", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "loopTimeStringIdList", "", "", "getLoopTimeStringIdList", "()Ljava/util/List;", "loopTimeStringIdList$delegate", "Lkotlin/Lazy;", "userViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "getUserViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "userViewModel$delegate", "clearCache", "", "initView", "logout", "onResume", "showLoopTimePicker", "showSwitchDeviceTypeDialog", "showUpdateNickNameDialog", "updateAvatar", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MineFragment extends BaseFragment<FragmentMineBinding> {
    private final ActivityResultLauncher<Intent> addImageActivityResult;

    /* JADX INFO: renamed from: userViewModel$delegate, reason: from kotlin metadata */
    private final Lazy userViewModel = LazyKt.lazy(new Function0<UserViewModel>() { // from class: com.shangxian.pinkink.ui.main.MineFragment$userViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UserViewModel invoke() {
            return (UserViewModel) new ViewModelProvider(this.this$0).get(UserViewModel.class);
        }
    });

    /* JADX INFO: renamed from: loopTimeStringIdList$delegate, reason: from kotlin metadata */
    private final Lazy loopTimeStringIdList = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: com.shangxian.pinkink.ui.main.MineFragment$loopTimeStringIdList$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final List<? extends String> invoke() {
            return CollectionsKt.listOf((Object[]) new String[]{this.this$0.getString(R.string.loop_time_10_min), this.this$0.getString(R.string.loop_time_20_min), this.this$0.getString(R.string.loop_time_30_min), this.this$0.getString(R.string.loop_time_40_min), this.this$0.getString(R.string.loop_time_50_min), this.this$0.getString(R.string.loop_time_60_min), this.this$0.getString(R.string.loop_time_70_min), this.this$0.getString(R.string.loop_time_80_min), this.this$0.getString(R.string.loop_time_90_min), this.this$0.getString(R.string.loop_time_100_min)});
        }
    });

    public MineFragment() {
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda10
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MineFragment.m248addImageActivityResult$lambda22(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…ewBinding.ivAvatar)\n    }");
        this.addImageActivityResult = activityResultLauncherRegisterForActivityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ViewModel
    public final UserViewModel getUserViewModel() {
        return (UserViewModel) this.userViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingFragment
    public void initView() {
        super.initView();
        FragmentMineBinding fragmentMineBinding = (FragmentMineBinding) getMViewBinding();
        fragmentMineBinding.ivAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m253initView$lambda18$lambda0(this.f$0, view);
            }
        });
        FragmentActivity activity = getActivity();
        if (activity != null) {
            RequestManager requestManagerWith = Glide.with(activity);
            UserInfoBean userInfo = getUserViewModel().getUserInfo();
            requestManagerWith.load(userInfo == null ? null : userInfo.getAvatar()).placeholder(R.drawable.ic_user_avatar_def).error(R.drawable.ic_user_avatar_def).into(fragmentMineBinding.ivAvatar);
        }
        TextView textView = fragmentMineBinding.tvNickName;
        UserInfoBean userInfo2 = getUserViewModel().getUserInfo();
        textView.setText(userInfo2 != null ? userInfo2.getNikeName() : null);
        fragmentMineBinding.tvNickName.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m261initView$lambda18$lambda2(this.f$0, view);
            }
        });
        fragmentMineBinding.llUserLikeThemes.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m262initView$lambda18$lambda3(this.f$0, view);
            }
        });
        fragmentMineBinding.llUserCreateThemes.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m263initView$lambda18$lambda4(this.f$0, view);
            }
        });
        fragmentMineBinding.llSwitchDeviceType.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m264initView$lambda18$lambda5(this.f$0, view);
            }
        });
        fragmentMineBinding.llFontLibrary.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Exception {
                MineFragment.m265initView$lambda18$lambda6(this.f$0, view);
            }
        });
        fragmentMineBinding.llMyFontLibrary.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Exception {
                MineFragment.m266initView$lambda18$lambda7(this.f$0, view);
            }
        });
        fragmentMineBinding.llAboutUs.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Exception {
                MineFragment.m267initView$lambda18$lambda8(this.f$0, view);
            }
        });
        fragmentMineBinding.llHelp.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m254initView$lambda18$lambda10(this.f$0, view);
            }
        });
        fragmentMineBinding.llCancelAccount.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Exception {
                MineFragment.m255initView$lambda18$lambda11(this.f$0, view);
            }
        });
        fragmentMineBinding.llPictureLoopSetting.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda23
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m256initView$lambda18$lambda12(this.f$0, view);
            }
        });
        fragmentMineBinding.llClearCache.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m257initView$lambda18$lambda13(this.f$0, view);
            }
        });
        fragmentMineBinding.ivLogout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MineFragment.m258initView$lambda18$lambda14(this.f$0, view);
            }
        });
        fragmentMineBinding.switchPictureSetting.setChecked(AppConfig.INSTANCE.isLoopPictureEnable());
        fragmentMineBinding.switchPictureSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                MineFragment.m259initView$lambda18$lambda15(compoundButton, z);
            }
        });
        fragmentMineBinding.tvDeviceType.setText(AppConfig.INSTANCE.isNfcDevice() ? "NFC" : getString(R.string.string_bluetooth));
        fragmentMineBinding.switchLanguage.setChecked(AppConfig.INSTANCE.isEnglishLanguage());
        fragmentMineBinding.switchLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                MineFragment.m260initView$lambda18$lambda17(this.f$0, compoundButton, z);
            }
        });
        MineFragment mineFragment = this;
        getUserViewModel().getLikeCount().observe(mineFragment, new Observer() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MineFragment.m268initView$lambda19(this.f$0, (Integer) obj);
            }
        });
        getUserViewModel().getCreateCount().observe(mineFragment, new Observer() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MineFragment.m269initView$lambda20(this.f$0, (Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-0, reason: not valid java name */
    public static final void m253initView$lambda18$lambda0(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateAvatar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-2, reason: not valid java name */
    public static final void m261initView$lambda18$lambda2(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showUpdateNickNameDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-3, reason: not valid java name */
    public static final void m262initView$lambda18$lambda3(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UserLikeThemesActivity.INSTANCE.gotoThis(this$0.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-4, reason: not valid java name */
    public static final void m263initView$lambda18$lambda4(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UserThemesActivity.INSTANCE.gotoThis(this$0.getContext(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-5, reason: not valid java name */
    public static final void m264initView$lambda18$lambda5(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showSwitchDeviceTypeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-6, reason: not valid java name */
    public static final void m265initView$lambda18$lambda6(MineFragment this$0, View view) throws Exception {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Context context = this$0.getContext();
        if (context == null) {
            return;
        }
        ContextExtensionsKt.goto$default(context, FontLibraryActivity.class, null, null, null, null, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-7, reason: not valid java name */
    public static final void m266initView$lambda18$lambda7(MineFragment this$0, View view) throws Exception {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Context context = this$0.getContext();
        if (context == null) {
            return;
        }
        ContextExtensionsKt.goto$default(context, UserFontLibraryActivity.class, null, null, null, null, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-8, reason: not valid java name */
    public static final void m267initView$lambda18$lambda8(MineFragment this$0, View view) throws Exception {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Context context = this$0.getContext();
        if (context == null) {
            return;
        }
        ContextExtensionsKt.goto$default(context, AboutUsActivity.class, null, null, null, null, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-10, reason: not valid java name */
    public static final void m254initView$lambda18$lambda10(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Context context = this$0.getContext();
        if (context == null) {
            return;
        }
        CommonWebViewActivity.INSTANCE.gotoThis(context, AppConfig.INSTANCE.getHelpUrl());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-11, reason: not valid java name */
    public static final void m255initView$lambda18$lambda11(MineFragment this$0, View view) throws Exception {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Context context = this$0.getContext();
        if (context == null) {
            return;
        }
        ContextExtensionsKt.goto$default(context, CancelAccountStep1Activity.class, null, null, null, null, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-12, reason: not valid java name */
    public static final void m256initView$lambda18$lambda12(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showLoopTimePicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-13, reason: not valid java name */
    public static final void m257initView$lambda18$lambda13(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.clearCache();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-14, reason: not valid java name */
    public static final void m258initView$lambda18$lambda14(MineFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.logout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-15, reason: not valid java name */
    public static final void m259initView$lambda18$lambda15(CompoundButton compoundButton, boolean z) {
        AppConfig.INSTANCE.setLoopPictureEnable(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-18$lambda-17, reason: not valid java name */
    public static final void m260initView$lambda18$lambda17(MineFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AppConfig.INSTANCE.setEnglishLanguage(z);
        final FragmentActivity activity = this$0.getActivity();
        if (activity == null) {
            return;
        }
        LocaleManager localeManager = new LocaleManager(activity);
        Locale locale = z ? Locale.ENGLISH : Locale.CHINA;
        Intrinsics.checkNotNullExpressionValue(locale, "if(isChecked) Locale.ENGLISH else Locale.CHINA");
        localeManager.changeLocale(locale, new Function0<Unit>() { // from class: com.shangxian.pinkink.ui.main.MineFragment$initView$1$16$1$1
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
                FragmentActivity fragmentActivity = activity;
                Intrinsics.checkNotNullExpressionValue(fragmentActivity, "");
                ContextExtensionsKt.goto$default(fragmentActivity, MainActivity.class, null, null, null, null, 30, null);
                AppManager.INSTANCE.finishAll();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-19, reason: not valid java name */
    public static final void m268initView$lambda19(MineFragment this$0, Integer num) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ((FragmentMineBinding) this$0.getMViewBinding()).tvLikeCount.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-20, reason: not valid java name */
    public static final void m269initView$lambda20(MineFragment this$0, Integer num) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ((FragmentMineBinding) this$0.getMViewBinding()).tvCreateCount.setText(String.valueOf(num));
    }

    private final List<String> getLoopTimeStringIdList() {
        return (List) this.loopTimeStringIdList.getValue();
    }

    private final void updateAvatar() {
        PictureSelector.create(this).openGallery(SelectMimeType.ofImage()).setSelectionMode(1).isDisplayCamera(false).setImageEngine(GlideEngine.createGlideEngine()).forResult(new OnResultCallbackListener<LocalMedia>() { // from class: com.shangxian.pinkink.ui.main.MineFragment.updateAvatar.1
            @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
            public void onCancel() {
            }

            @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
            public void onResult(ArrayList<LocalMedia> result) {
                Intrinsics.checkNotNullParameter(result, "result");
                LocalMedia localMedia = (LocalMedia) CollectionsKt.first((List) result);
                if (localMedia == null) {
                    return;
                }
                Intent intent = new Intent(MineFragment.this.getActivity(), (Class<?>) CropAvatarActivity.class);
                intent.putExtra(Keys.LOCAL_MEDIA, localMedia);
                MineFragment.this.addImageActivityResult.launch(intent);
            }
        });
    }

    private final void showSwitchDeviceTypeDialog() {
        final FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        new SwitchDeviceTypeDialog(activity).setActionCallback(new SwitchDeviceTypeDialog.ActionCallback() { // from class: com.shangxian.pinkink.ui.main.MineFragment$showSwitchDeviceTypeDialog$1$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.shangxian.pinkink.ui.dialog.SwitchDeviceTypeDialog.ActionCallback
            public void onSwitchDeviceType(String type) {
                Intrinsics.checkNotNullParameter(type, "type");
                EventBus.getDefault().post(new DeviceTypeChangedEvent(type));
                AppConfig.INSTANCE.setDeviceType(type);
                ((FragmentMineBinding) this.this$0.getMViewBinding()).tvDeviceType.setText(AppConfig.INSTANCE.isNfcDevice() ? "NFC" : activity.getString(R.string.string_bluetooth));
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: addImageActivityResult$lambda-22, reason: not valid java name */
    public static final void m248addImageActivityResult$lambda22(MineFragment this$0, ActivityResult activityResult) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (activityResult.getResultCode() == -1 && activityResult.getData() != null) {
            Intent data = activityResult.getData();
            Intrinsics.checkNotNull(data);
            String stringExtra = data.getStringExtra(Keys.URL);
            if (stringExtra == null) {
                return;
            }
            Glide.with(this$0).load(stringExtra).placeholder(R.drawable.ic_user_avatar_def).error(R.drawable.ic_user_avatar_def).into(((FragmentMineBinding) this$0.getMViewBinding()).ivAvatar);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void showUpdateNickNameDialog() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        new UpdateNickNameDialog(activity, ((FragmentMineBinding) getMViewBinding()).tvNickName.getText().toString()).setActionCallback(new UpdateNickNameDialog.ActionCallback() { // from class: com.shangxian.pinkink.ui.main.MineFragment$showUpdateNickNameDialog$1$1
            @Override // com.shangxian.pinkink.ui.dialog.UpdateNickNameDialog.ActionCallback
            public void onUpdateNickName(final String newNickName) {
                Intrinsics.checkNotNullParameter(newNickName, "newNickName");
                UserViewModel userViewModel = this.this$0.getUserViewModel();
                final MineFragment mineFragment = this.this$0;
                userViewModel.updateNickName(newNickName, new Function1<String, Unit>() { // from class: com.shangxian.pinkink.ui.main.MineFragment$showUpdateNickNameDialog$1$1$onUpdateNickName$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str) {
                        invoke2(str);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        ((FragmentMineBinding) mineFragment.getMViewBinding()).tvNickName.setText(newNickName);
                    }
                });
            }
        }).show();
    }

    private final void showLoopTimePicker() {
        if (getActivity() == null) {
            return;
        }
        OptionsPickerView optionsPickerViewBuild = new OptionsPickerBuilder(requireActivity(), new OnOptionsSelectListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda14
            @Override // com.bigkoo.pickerview.listener.OnOptionsSelectListener
            public final void onOptionsSelect(int i, int i2, int i3, View view) {
                MineFragment.m272showLoopTimePicker$lambda24(i, i2, i3, view);
            }
        }).setTitleText(getString(R.string.string_loop_play_time)).setCancelText(getString(R.string.string_cancel)).setSubmitText(getString(R.string.string_confirm)).build();
        optionsPickerViewBuild.setPicker(getLoopTimeStringIdList());
        optionsPickerViewBuild.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: showLoopTimePicker$lambda-24, reason: not valid java name */
    public static final void m272showLoopTimePicker$lambda24(int i, int i2, int i3, View view) {
        AppConfig.INSTANCE.setLoopTime((i + 1) * 10);
    }

    private final void logout() {
        new AlertDialog.Builder(requireContext()).setTitle(getString(R.string.string_tip)).setMessage(getString(R.string.string_alert_confirm_logout)).setNegativeButton(getString(R.string.string_cancel), new DialogInterface.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton(getString(R.string.string_confirm), new DialogInterface.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MineFragment.m271logout$lambda26(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: logout$lambda-26, reason: not valid java name */
    public static final void m271logout$lambda26(final MineFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        dialogInterface.dismiss();
        this$0.getUserViewModel().logout(new Function0<Unit>() { // from class: com.shangxian.pinkink.ui.main.MineFragment$logout$2$1
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
                FragmentActivity activity = this.this$0.getActivity();
                if (activity != null) {
                    ContextExtensionsKt.goto$default(activity, LoginActivity.class, null, null, null, null, 30, null);
                }
                AppManager.INSTANCE.finishAllExcept(LoginActivity.class);
            }
        });
    }

    private final void clearCache() {
        new AlertDialog.Builder(requireContext()).setTitle(getString(R.string.string_tip)).setMessage(getString(R.string.string_alert_confirm_clear_cache)).setNegativeButton(getString(R.string.string_cancel), new DialogInterface.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton(getString(R.string.string_confirm), new DialogInterface.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MineFragment.m250clearCache$lambda30(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: clearCache$lambda-30, reason: not valid java name */
    public static final void m250clearCache$lambda30(final MineFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        dialogInterface.dismiss();
        this$0.getLoadingDialog().show();
        new Thread(new Runnable() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                MineFragment.m251clearCache$lambda30$lambda29(this.f$0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: clearCache$lambda-30$lambda-29, reason: not valid java name */
    public static final void m251clearCache$lambda30$lambda29(final MineFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Glide.get(this$0.requireContext()).clearDiskCache();
        this$0.runOnUiThread(new Runnable() { // from class: com.shangxian.pinkink.ui.main.MineFragment$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                MineFragment.m252clearCache$lambda30$lambda29$lambda28(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: clearCache$lambda-30$lambda-29$lambda-28, reason: not valid java name */
    public static final void m252clearCache$lambda30$lambda29$lambda28(MineFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getLoadingDialog().dismiss();
        this$0.onShowShortToast(this$0.getString(R.string.toast_clear_cache_success));
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getUserViewModel().getUserThemeInfo();
    }
}
