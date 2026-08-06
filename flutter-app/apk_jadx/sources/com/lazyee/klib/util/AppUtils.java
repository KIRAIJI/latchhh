package com.lazyee.klib.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.lazyee.klib.R;
import com.lazyee.klib.extension.StringExtensionsKt;
import java.io.File;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;

/* JADX INFO: compiled from: AppUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\"B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011J0\u0010\u0014\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J4\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00182\u001c\u0010\u0019\u001a\u0018\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\f0\u001aj\b\u0012\u0004\u0012\u00020\u001b`\u001cJ4\u0010\u0016\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00182\u001c\u0010\u0019\u001a\u0018\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\f0\u001aj\b\u0012\u0004\u0012\u00020\u001b`\u001cJ6\u0010\u0016\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\u00182\u001c\u0010\u0019\u001a\u0018\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\f0\u001aj\b\u0012\u0004\u0012\u00020\u001b`\u001cH\u0002J\u001e\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0011¨\u0006#"}, d2 = {"Lcom/lazyee/klib/util/AppUtils;", "", "()V", "getCurrentProcessName", "", "applicationContext", "Landroid/content/Context;", "hasNewVersion", "", "currentVersionName", "compareVersionName", "installApk", "", "activity", "Landroid/app/Activity;", "authority", "apkFile", "Ljava/io/File;", "fragment", "Landroidx/fragment/app/Fragment;", "interInstallApk", "Landroidx/fragment/app/FragmentActivity;", "registerSimpleActivityResult", "intent", "Landroid/content/Intent;", "callback", "Lkotlin/Function1;", "Landroidx/activity/result/ActivityResult;", "Lcom/lazyee/klib/typed/TCallback;", "fragmentManager", "Landroidx/fragment/app/FragmentManager;", "shareFile", "context", "file", "ActivityResultFragment", "library_release"}, k = 1, mv = {1, 4, 2})
public final class AppUtils {
    public static final AppUtils INSTANCE = new AppUtils();

    /* JADX INFO: renamed from: com.lazyee.klib.util.AppUtils$interInstallApk$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\n¢\u0006\u0002\b\b"}, d2 = {"realInstallApk", "", "context", "Landroid/content/Context;", "authority", "", "apkFile", "Ljava/io/File;", "invoke"}, k = 3, mv = {1, 4, 2})
    static final class AnonymousClass1 extends Lambda implements Function3<Context, String, File, Unit> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(3);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(Context context, String str, File file) {
            invoke2(context, str, file);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Context context, String authority, File apkFile) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(authority, "authority");
            Intrinsics.checkNotNullParameter(apkFile, "apkFile");
            Intent intent = new Intent("android.intent.action.VIEW");
            Uri uriForFile = FileProvider.getUriForFile(context, authority, apkFile);
            Intrinsics.checkNotNullExpressionValue(uriForFile, "FileProvider.getUriForFi…text, authority, apkFile)");
            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
            intent.addFlags(1);
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    private AppUtils() {
    }

    public final boolean hasNewVersion(String currentVersionName, String compareVersionName) {
        Intrinsics.checkNotNullParameter(currentVersionName, "currentVersionName");
        Intrinsics.checkNotNullParameter(compareVersionName, "compareVersionName");
        try {
            Object[] array = new Regex("\\.").split(currentVersionName, 0).toArray(new String[0]);
            if (array != null) {
                String[] strArr = (String[]) array;
                Object[] array2 = new Regex("\\.").split(compareVersionName, 0).toArray(new String[0]);
                if (array2 != null) {
                    String[] strArr2 = (String[]) array2;
                    int iCoerceAtLeast = RangesKt.coerceAtLeast(strArr2.length, strArr.length);
                    String[] strArr3 = new String[iCoerceAtLeast];
                    int length = strArr.length;
                    for (int i = 0; i < length; i++) {
                        strArr3[i] = strArr[i];
                    }
                    String[] strArr4 = new String[iCoerceAtLeast];
                    int length2 = strArr2.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        strArr4[i2] = strArr2[i2];
                    }
                    for (int i3 = 0; i3 < iCoerceAtLeast; i3++) {
                        long jSafeToLong = StringExtensionsKt.safeToLong(strArr3[i3]);
                        long jSafeToLong2 = StringExtensionsKt.safeToLong(strArr4[i3]);
                        if (jSafeToLong != jSafeToLong2) {
                            return jSafeToLong < jSafeToLong2;
                        }
                    }
                    return false;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final String getCurrentProcessName(Context applicationContext) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        int iMyPid = Process.myPid();
        Object systemService = applicationContext.getSystemService("activity");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) systemService).getRunningAppProcesses();
        Intrinsics.checkNotNullExpressionValue(runningAppProcesses, "manager.runningAppProcesses");
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    public final void shareFile(Context context, String authority, File file) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(authority, "authority");
        Intrinsics.checkNotNullParameter(file, "file");
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(context, authority, file));
        context.startActivity(Intent.createChooser(intent, "分享文件到"));
    }

    public final void installApk(Activity activity, String authority, File apkFile) throws Exception {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(authority, "authority");
        Intrinsics.checkNotNullParameter(apkFile, "apkFile");
        if (!(activity instanceof FragmentActivity)) {
            throw new Exception("必须要FragmentActivity才能执行此方法");
        }
        interInstallApk$default(this, (FragmentActivity) activity, null, authority, apkFile, 2, null);
    }

    public final void installApk(Fragment fragment, String authority, File apkFile) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(authority, "authority");
        Intrinsics.checkNotNullParameter(apkFile, "apkFile");
        interInstallApk$default(this, null, fragment, authority, apkFile, 1, null);
    }

    static /* synthetic */ void interInstallApk$default(AppUtils appUtils, FragmentActivity fragmentActivity, Fragment fragment, String str, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            fragmentActivity = (FragmentActivity) null;
        }
        if ((i & 2) != 0) {
            fragment = (Fragment) null;
        }
        appUtils.interInstallApk(fragmentActivity, fragment, str, file);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, android.content.Context] */
    /* JADX WARN: Type inference failed for: r5v1, types: [T, android.content.Context] */
    /* JADX WARN: Type inference failed for: r6v8, types: [T, android.content.Context] */
    private final void interInstallApk(FragmentActivity activity, Fragment fragment, final String authority, final File apkFile) {
        AnonymousClass1 anonymousClass1 = AnonymousClass1.INSTANCE;
        FragmentManager childFragmentManager = (FragmentManager) null;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = (Context) 0;
        if (activity != null) {
            objectRef.element = activity;
            childFragmentManager = activity.getSupportFragmentManager();
        } else if (fragment != null) {
            objectRef.element = fragment.getContext();
            childFragmentManager = fragment.getChildFragmentManager();
        }
        if (((Context) objectRef.element) == null || childFragmentManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (((Context) objectRef.element).getPackageManager().canRequestPackageInstalls()) {
                anonymousClass1.invoke2((Context) objectRef.element, authority, apkFile);
                return;
            }
            registerSimpleActivityResult(childFragmentManager, new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", Uri.parse("package:" + ((Context) objectRef.element).getPackageName())), new Function1<ActivityResult, Unit>() { // from class: com.lazyee.klib.util.AppUtils.interInstallApk.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ActivityResult activityResult) {
                    invoke2(activityResult);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ActivityResult result) {
                    Intrinsics.checkNotNullParameter(result, "result");
                    if (result.getResultCode() != -1) {
                        return;
                    }
                    AnonymousClass1.INSTANCE.invoke2((Context) objectRef.element, authority, apkFile);
                }
            });
            return;
        }
        anonymousClass1.invoke2((Context) objectRef.element, authority, apkFile);
    }

    public final void registerSimpleActivityResult(FragmentActivity activity, Intent intent, Function1<? super ActivityResult, Unit> callback) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(callback, "callback");
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "activity.supportFragmentManager");
        registerSimpleActivityResult(supportFragmentManager, intent, callback);
    }

    public final void registerSimpleActivityResult(Fragment fragment, Intent intent, Function1<? super ActivityResult, Unit> callback) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(callback, "callback");
        FragmentManager childFragmentManager = fragment.getChildFragmentManager();
        Intrinsics.checkNotNullExpressionValue(childFragmentManager, "fragment.childFragmentManager");
        registerSimpleActivityResult(childFragmentManager, intent, callback);
    }

    private final void registerSimpleActivityResult(FragmentManager fragmentManager, Intent intent, Function1<? super ActivityResult, Unit> callback) {
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag("ActivityResultFragment");
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "fragmentManager.beginTransaction()");
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
        }
        fragmentTransactionBeginTransaction.add(new ActivityResultFragment(intent, callback), "ActivityResultFragment");
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    /* JADX INFO: compiled from: AppUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001c\u0010\u0004\u001a\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\b\u0012\u0004\u0012\u00020\u0006`\b¢\u0006\u0002\u0010\tJ\u0012\u0010\r\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J&\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016R\u001c\u0010\n\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u00030\u00030\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0004\u001a\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\b\u0012\u0004\u0012\u00020\u0006`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/lazyee/klib/util/AppUtils$ActivityResultFragment;", "Landroidx/fragment/app/Fragment;", "intent", "Landroid/content/Intent;", "callback", "Lkotlin/Function1;", "Landroidx/activity/result/ActivityResult;", "", "Lcom/lazyee/klib/typed/TCallback;", "(Landroid/content/Intent;Lkotlin/jvm/functions/Function1;)V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "kotlin.jvm.PlatformType", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class ActivityResultFragment extends Fragment {
        private final ActivityResultLauncher<Intent> activityResultLauncher;
        private final Function1<ActivityResult, Unit> callback;
        private final Intent intent;

        /* JADX WARN: Multi-variable type inference failed */
        public ActivityResultFragment(Intent intent, Function1<? super ActivityResult, Unit> callback) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            Intrinsics.checkNotNullParameter(callback, "callback");
            this.intent = intent;
            this.callback = callback;
            ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.lazyee.klib.util.AppUtils$ActivityResultFragment$activityResultLauncher$1
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(ActivityResult result) {
                    this.this$0.getParentFragmentManager().beginTransaction().remove(this.this$0).commitAllowingStateLoss();
                    Function1 function1 = this.this$0.callback;
                    Intrinsics.checkNotNullExpressionValue(result, "result");
                    function1.invoke(result);
                }
            });
            Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul….invoke(result)\n        }");
            this.activityResultLauncher = activityResultLauncherRegisterForActivityResult;
        }

        @Override // androidx.fragment.app.Fragment
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.activityResultLauncher.launch(this.intent);
        }

        @Override // androidx.fragment.app.Fragment
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Intrinsics.checkNotNullParameter(inflater, "inflater");
            super.onCreateView(inflater, container, savedInstanceState);
            return inflater.inflate(R.layout.layout_debug_config, (ViewGroup) null, false);
        }
    }
}
