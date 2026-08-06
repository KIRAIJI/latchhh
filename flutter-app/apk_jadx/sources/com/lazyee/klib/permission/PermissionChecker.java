package com.lazyee.klib.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.hjq.permissions.Permission;
import com.lazyee.klib.R;
import com.lazyee.klib.util.AppUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PermissionChecker.kt */
/* JADX INFO: loaded from: classes.dex */
@Deprecated(message = "不推荐使用这个权限检查类，这个类目前做得很不完善", replaceWith = @ReplaceWith(expression = "XXPermissions", imports = {}))
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 12\u00020\u0001:\u000212B\u000f\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0012\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001f\u0010 \u001a\u00020\u001a2\u0012\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0\u0011\"\u00020\u001f¢\u0006\u0002\u0010!J\u001f\u0010\"\u001a\u00020\u001a2\u0012\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0\u0011\"\u00020\u001f¢\u0006\u0002\u0010!J\u0006\u0010#\u001a\u00020\u0000J\u0018\u0010$\u001a\u00020\u00002\u0010\u0010%\u001a\f\u0012\u0004\u0012\u00020\u000b0\nj\u0002`\fJ3\u0010&\u001a\u00020\u00002+\u0010%\u001a'\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00120\u0011¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u000b0\u0010j\u0002`\u0016JH\u0010'\u001a\u00020\u00002@\u0010%\u001a<\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001b\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00120\u0011¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u000b0\u0019j\u0002`\u001cJ\u001f\u0010(\u001a\u00020\u00002\u0012\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0\u0011\"\u00020\u001f¢\u0006\u0002\u0010)J\u0010\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020,H\u0002J\u0006\u0010-\u001a\u00020\u000bJ$\u0010.\u001a\u00020\u000b2\u001c\u0010%\u001a\u0018\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020\u000b0\u0010j\b\u0012\u0004\u0012\u00020/`0R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nj\u0004\u0018\u0001`\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R7\u0010\u000f\u001a+\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00120\u0011¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0010j\u0004\u0018\u0001`\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000RL\u0010\u0018\u001a@\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001b\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00120\u0011¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0019j\u0004\u0018\u0001`\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lcom/lazyee/klib/permission/PermissionChecker;", "", "activity", "Landroidx/fragment/app/FragmentActivity;", "(Landroidx/fragment/app/FragmentActivity;)V", "fragment", "Landroidx/fragment/app/Fragment;", "(Landroidx/fragment/app/Fragment;)V", "mActivity", "mAllGrantedCallback", "Lkotlin/Function0;", "", "Lcom/lazyee/klib/typed/AllGrantedCallback;", "mContext", "Landroid/content/Context;", "mDeniedCallback", "Lkotlin/Function1;", "", "Lcom/lazyee/klib/permission/PermissionStatus;", "Lkotlin/ParameterName;", "name", "permissions", "Lcom/lazyee/klib/typed/DeniedCallback;", "mFragment", "mGrantedCallback", "Lkotlin/Function2;", "", "isAllGranted", "Lcom/lazyee/klib/typed/GrantedCallback;", "mPermissions", "", "", "isDenied", "([Ljava/lang/String;)Z", "isGranted", "manageExternalStoragePermission", "onAllGranted", "callback", "onDenied", "onGranted", "permission", "([Ljava/lang/String;)Lcom/lazyee/klib/permission/PermissionChecker;", "realRequest", "fragmentManager", "Landroidx/fragment/app/FragmentManager;", "request", "startPermissionActivity", "Landroidx/activity/result/ActivityResult;", "Lcom/lazyee/klib/typed/TCallback;", "Companion", "PermissionFragment", "library_release"}, k = 1, mv = {1, 4, 2})
public final class PermissionChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String MANAGE_EXTERNAL_STORAGE = Permission.MANAGE_EXTERNAL_STORAGE;
    private FragmentActivity mActivity;
    private Function0<Unit> mAllGrantedCallback;
    private Context mContext;
    private Function1<? super PermissionStatus[], Unit> mDeniedCallback;
    private Fragment mFragment;
    private Function2<? super Boolean, ? super PermissionStatus[], Unit> mGrantedCallback;
    private final List<String> mPermissions;

    public /* synthetic */ PermissionChecker(Fragment fragment, DefaultConstructorMarker defaultConstructorMarker) {
        this(fragment);
    }

    public /* synthetic */ PermissionChecker(FragmentActivity fragmentActivity, DefaultConstructorMarker defaultConstructorMarker) {
        this(fragmentActivity);
    }

    private PermissionChecker(FragmentActivity fragmentActivity) {
        this.mPermissions = new ArrayList();
        this.mActivity = fragmentActivity;
        this.mContext = fragmentActivity;
    }

    private PermissionChecker(Fragment fragment) {
        this.mPermissions = new ArrayList();
        this.mFragment = fragment;
        FragmentActivity fragmentActivityRequireActivity = fragment.requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "fragment.requireActivity()");
        this.mContext = fragmentActivityRequireActivity;
    }

    /* JADX INFO: compiled from: PermissionChecker.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fR\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lcom/lazyee/klib/permission/PermissionChecker$Companion;", "", "()V", "MANAGE_EXTERNAL_STORAGE", "", "getMANAGE_EXTERNAL_STORAGE", "()Ljava/lang/String;", "with", "Lcom/lazyee/klib/permission/PermissionChecker;", "fragment", "Landroidx/fragment/app/Fragment;", "activity", "Landroidx/fragment/app/FragmentActivity;", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PermissionChecker with(FragmentActivity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            return new PermissionChecker(activity, (DefaultConstructorMarker) null);
        }

        public final PermissionChecker with(Fragment fragment) {
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            return new PermissionChecker(fragment, (DefaultConstructorMarker) null);
        }

        public final String getMANAGE_EXTERNAL_STORAGE() {
            return PermissionChecker.MANAGE_EXTERNAL_STORAGE;
        }
    }

    public final void startPermissionActivity(Function1<? super ActivityResult, Unit> callback) {
        FragmentActivity activity;
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        FragmentActivity fragmentActivity = this.mActivity;
        String packageName = fragmentActivity != null ? fragmentActivity.getPackageName() : null;
        if (packageName == null) {
            Fragment fragment = this.mFragment;
            packageName = (fragment == null || (activity = fragment.getActivity()) == null) ? null : activity.getPackageName();
        }
        intent.setData(Uri.fromParts("package", packageName, null));
        if (this.mActivity != null) {
            AppUtils appUtils = AppUtils.INSTANCE;
            FragmentActivity fragmentActivity2 = this.mActivity;
            Intrinsics.checkNotNull(fragmentActivity2);
            appUtils.registerSimpleActivityResult(fragmentActivity2, intent, callback);
            return;
        }
        if (this.mFragment != null) {
            AppUtils appUtils2 = AppUtils.INSTANCE;
            Fragment fragment2 = this.mFragment;
            Intrinsics.checkNotNull(fragment2);
            appUtils2.registerSimpleActivityResult(fragment2, intent, callback);
        }
    }

    public final PermissionChecker permission(String... permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        this.mPermissions.clear();
        CollectionsKt.addAll(this.mPermissions, permissions);
        return this;
    }

    public final PermissionChecker manageExternalStoragePermission() {
        this.mPermissions.clear();
        if (Build.VERSION.SDK_INT >= 30) {
            this.mPermissions.add(MANAGE_EXTERNAL_STORAGE);
        } else {
            this.mPermissions.add("android.permission.READ_EXTERNAL_STORAGE");
            this.mPermissions.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        return this;
    }

    public final PermissionChecker onAllGranted(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mAllGrantedCallback = callback;
        return this;
    }

    public final PermissionChecker onGranted(Function2<? super Boolean, ? super PermissionStatus[], Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mGrantedCallback = callback;
        return this;
    }

    public final PermissionChecker onDenied(Function1<? super PermissionStatus[], Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mDeniedCallback = callback;
        return this;
    }

    public final void request() {
        FragmentActivity fragmentActivity = this.mActivity;
        if (fragmentActivity != null) {
            Intrinsics.checkNotNull(fragmentActivity);
            FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
            Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "mActivity!!.supportFragmentManager");
            realRequest(supportFragmentManager);
            return;
        }
        Fragment fragment = this.mFragment;
        if (fragment != null) {
            Intrinsics.checkNotNull(fragment);
            FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            Intrinsics.checkNotNullExpressionValue(childFragmentManager, "mFragment!!.childFragmentManager");
            realRequest(childFragmentManager);
        }
    }

    private final void realRequest(FragmentManager fragmentManager) {
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag("PermissionFragment");
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "fragmentManager.beginTransaction()");
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
        }
        fragmentTransactionBeginTransaction.add(new PermissionFragment(this.mPermissions, this.mAllGrantedCallback, this.mGrantedCallback, this.mDeniedCallback), "PermissionFragment");
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    /* JADX INFO: compiled from: PermissionChecker.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B¦\u0001\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0016\b\u0002\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006j\u0004\u0018\u0001`\b\u0012F\b\u0002\u0010\t\u001a@\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u0007\u0018\u00010\nj\u0004\u0018\u0001`\u0011\u00121\b\u0002\u0010\u0012\u001a+\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0013j\u0004\u0018\u0001`\u0014¢\u0006\u0002\u0010\u0015J\u0012\u0010\u0019\u001a\u00020\u00072\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J&\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016R:\u0010\u0016\u001a.\u0012*\u0012(\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00040\u0004 \u0018*\u0014\u0012\u000e\b\u0001\u0012\n \u0018*\u0004\u0018\u00010\u00040\u0004\u0018\u00010\u000f0\u000f0\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006j\u0004\u0018\u0001`\bX\u0082\u0004¢\u0006\u0002\n\u0000R7\u0010\u0012\u001a+\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0013j\u0004\u0018\u0001`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000RL\u0010\t\u001a@\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u0007\u0018\u00010\nj\u0004\u0018\u0001`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/lazyee/klib/permission/PermissionChecker$PermissionFragment;", "Landroidx/fragment/app/Fragment;", "permissions", "", "", "allGrantedCallback", "Lkotlin/Function0;", "", "Lcom/lazyee/klib/typed/AllGrantedCallback;", "grantedCallback", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "isAllGranted", "", "Lcom/lazyee/klib/permission/PermissionStatus;", "Lcom/lazyee/klib/typed/GrantedCallback;", "deniedCallback", "Lkotlin/Function1;", "Lcom/lazyee/klib/typed/DeniedCallback;", "(Ljava/util/List;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "kotlin.jvm.PlatformType", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class PermissionFragment extends Fragment {
        private final ActivityResultLauncher<String[]> activityResultLauncher;
        private final Function0<Unit> allGrantedCallback;
        private final Function1<PermissionStatus[], Unit> deniedCallback;
        private final Function2<Boolean, PermissionStatus[], Unit> grantedCallback;
        private final List<String> permissions;

        public /* synthetic */ PermissionFragment(List list, Function0 function0, Function2 function2, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, (i & 2) != 0 ? (Function0) null : function0, (i & 4) != 0 ? (Function2) null : function2, (i & 8) != 0 ? (Function1) null : function1);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public PermissionFragment(List<String> permissions, Function0<Unit> function0, Function2<? super Boolean, ? super PermissionStatus[], Unit> function2, Function1<? super PermissionStatus[], Unit> function1) {
            Intrinsics.checkNotNullParameter(permissions, "permissions");
            this.permissions = permissions;
            this.allGrantedCallback = function0;
            this.grantedCallback = function2;
            this.deniedCallback = function1;
            ActivityResultLauncher<String[]> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() { // from class: com.lazyee.klib.permission.PermissionChecker$PermissionFragment$activityResultLauncher$1
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Map<String, Boolean> result) {
                    Function0 function02;
                    Function1 function12;
                    Function2 function22;
                    this.this$0.getParentFragmentManager().beginTransaction().remove(this.this$0).commitAllowingStateLoss();
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    for (String key : result.keySet()) {
                        Intrinsics.checkNotNullExpressionValue(key, "key");
                        PermissionStatus permissionStatus = new PermissionStatus(key, false, false, 6, null);
                        Intrinsics.checkNotNullExpressionValue(result, "result");
                        Object value = MapsKt.getValue(result, key);
                        Intrinsics.checkNotNullExpressionValue(value, "result.getValue(key)");
                        permissionStatus.setGranted(((Boolean) value).booleanValue());
                        permissionStatus.setDoNotAskAgain(!ActivityCompat.shouldShowRequestPermissionRationale(this.this$0.requireActivity(), key));
                        if (permissionStatus.isGranted()) {
                            arrayList.add(permissionStatus);
                        } else {
                            arrayList2.add(permissionStatus);
                        }
                    }
                    ArrayList arrayList3 = arrayList;
                    if ((!arrayList3.isEmpty()) && (function22 = this.this$0.grantedCallback) != null) {
                        Boolean boolValueOf = Boolean.valueOf(arrayList2.isEmpty());
                        Object[] array = arrayList3.toArray(new PermissionStatus[0]);
                        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T>");
                    }
                    ArrayList arrayList4 = arrayList2;
                    if ((!arrayList4.isEmpty()) && (function12 = this.this$0.deniedCallback) != null) {
                        Object[] array2 = arrayList4.toArray(new PermissionStatus[0]);
                        Objects.requireNonNull(array2, "null cannot be cast to non-null type kotlin.Array<T>");
                    }
                    if (!arrayList2.isEmpty() || (function02 = this.this$0.allGrantedCallback) == null) {
                        return;
                    }
                }
            });
            Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…\n            }\n\n        }");
            this.activityResultLauncher = activityResultLauncherRegisterForActivityResult;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // androidx.fragment.app.Fragment
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ActivityResultLauncher<String[]> activityResultLauncher = this.activityResultLauncher;
            Object[] array = this.permissions.toArray(new String[0]);
            Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T>");
            activityResultLauncher.launch(array);
        }

        @Override // androidx.fragment.app.Fragment
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Intrinsics.checkNotNullParameter(inflater, "inflater");
            super.onCreateView(inflater, container, savedInstanceState);
            return inflater.inflate(R.layout.layout_debug_config, (ViewGroup) null, false);
        }
    }

    public final boolean isGranted(String... permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(this.mContext, str) == -1) {
                return false;
            }
        }
        return true;
    }

    public final boolean isDenied(String... permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(this.mContext, str) == 0) {
                return false;
            }
        }
        return true;
    }
}
