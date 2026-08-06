package com.gyf.immersionbar.ktx;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.gyf.immersionbar.ImmersionBar;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ImmersionBar.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000d\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001e\u0010%\u001a\u00020&*\u00020\u00062\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a\u001e\u0010%\u001a\u00020&*\u00020\t2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a\u001e\u0010%\u001a\u00020&*\u00020\u000b2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a\u0012\u0010*\u001a\u00020&*\u00020\u00062\u0006\u0010+\u001a\u00020\u000e\u001a\u0012\u0010*\u001a\u00020&*\u00020\t2\u0006\u0010+\u001a\u00020\u000e\u001a\u0012\u0010*\u001a\u00020&*\u00020\u000b2\u0006\u0010+\u001a\u00020\u000e\u001a#\u0010,\u001a\u00020&*\u00020\u00062\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0-\"\u00020\u000e¢\u0006\u0002\u0010.\u001a#\u0010,\u001a\u00020&*\u00020\t2\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0-\"\u00020\u000e¢\u0006\u0002\u0010/\u001a#\u0010,\u001a\u00020&*\u00020\u000b2\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0-\"\u00020\u000e¢\u0006\u0002\u00100\u001a#\u00101\u001a\u00020&*\u00020\u00062\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0-\"\u00020\u000e¢\u0006\u0002\u0010.\u001a#\u00101\u001a\u00020&*\u00020\t2\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0-\"\u00020\u000e¢\u0006\u0002\u0010/\u001a#\u00101\u001a\u00020&*\u00020\u000b2\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0-\"\u00020\u000e¢\u0006\u0002\u00100\u001a\n\u00102\u001a\u00020&*\u00020\u0006\u001a\n\u00102\u001a\u00020&*\u00020\t\u001a\n\u00102\u001a\u00020&*\u00020\u000b\u001a\u001e\u00103\u001a\u00020&*\u00020\u00062\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a;\u00103\u001a\u00020&*\u00020\u00062\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\u0016\u00103\u001a\u00020&*\u00020\u00062\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a3\u00103\u001a\u00020&*\u00020\u00062\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\u001e\u00103\u001a\u00020&*\u00020(2\u0006\u00108\u001a\u00020\u00062\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a;\u00103\u001a\u00020&*\u00020(2\u0006\u00108\u001a\u00020\u00062\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\u0016\u00103\u001a\u00020&*\u0002092\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a3\u00103\u001a\u00020&*\u0002092\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\u001e\u00103\u001a\u00020&*\u00020\t2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a;\u00103\u001a\u00020&*\u00020\t2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\u0016\u00103\u001a\u00020&*\u00020\t2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a3\u00103\u001a\u00020&*\u00020\t2\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\u0016\u00103\u001a\u00020&*\u00020:2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a3\u00103\u001a\u00020&*\u00020:2\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\u001e\u00103\u001a\u00020&*\u00020\u000b2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a;\u00103\u001a\u00020&*\u00020\u000b2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\u0016\u00103\u001a\u00020&*\u00020\u000b2\b\b\u0002\u0010)\u001a\u00020\u0001H\u0007\u001a3\u00103\u001a\u00020&*\u00020\u000b2\b\b\u0002\u0010)\u001a\u00020\u00012\u0017\u00104\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020&05¢\u0006\u0002\b7H\u0087\bø\u0001\u0000\u001a\n\u0010;\u001a\u00020&*\u00020\u0006\u001a\n\u0010;\u001a\u00020&*\u00020\t\u001a\n\u0010;\u001a\u00020&*\u00020\u000b\u001a\n\u0010<\u001a\u00020&*\u00020\u0006\u001a\n\u0010<\u001a\u00020&*\u00020\t\u001a\n\u0010<\u001a\u00020&*\u00020\u000b\"\u0011\u0010\u0000\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0002\"\u0011\u0010\u0003\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0002\"\u0015\u0010\u0004\u001a\u00020\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0015\u0010\u0004\u001a\u00020\u0005*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n\"\u0015\u0010\u0004\u001a\u00020\u0005*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\f\"\u0015\u0010\r\u001a\u00020\u0001*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\"\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014\"\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0016\"\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0017\"\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0013\"\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0014\"\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0010\"\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017\"\u0015\u0010\u001a\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0014\"\u0015\u0010\u001a\u001a\u00020\u0001*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016\"\u0015\u0010\u001a\u001a\u00020\u0001*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017\"\u0015\u0010\u001b\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0013\"\u0015\u0010\u001b\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0014\"\u0015\u0010\u001b\u001a\u00020\u0001*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0017\"\u0015\u0010\u001c\u001a\u00020\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\b\"\u0015\u0010\u001c\u001a\u00020\u0005*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\n\"\u0015\u0010\u001c\u001a\u00020\u0005*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\"\u0015\u0010\u001c\u001a\u00020\u0005*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\f\"\u0015\u0010\u001f\u001a\u00020\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b \u0010\b\"\u0015\u0010\u001f\u001a\u00020\u0005*\u00020\t8F¢\u0006\u0006\u001a\u0004\b \u0010\n\"\u0015\u0010\u001f\u001a\u00020\u0005*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b \u0010\u001e\"\u0015\u0010\u001f\u001a\u00020\u0005*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b \u0010\f\"\u0015\u0010!\u001a\u00020\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\"\u0010\b\"\u0015\u0010!\u001a\u00020\u0005*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\"\u0010\n\"\u0015\u0010!\u001a\u00020\u0005*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\"\u0010\f\"\u0015\u0010#\u001a\u00020\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b$\u0010\b\"\u0015\u0010#\u001a\u00020\u0005*\u00020\t8F¢\u0006\u0006\u001a\u0004\b$\u0010\n\"\u0015\u0010#\u001a\u00020\u0005*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b$\u0010\u001e\"\u0015\u0010#\u001a\u00020\u0005*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b$\u0010\f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006="}, d2 = {"isSupportNavigationIconDark", "", "()Z", "isSupportStatusBarDarkFont", "actionBarHeight", "", "Landroid/app/Activity;", "getActionBarHeight", "(Landroid/app/Activity;)I", "Landroid/app/Fragment;", "(Landroid/app/Fragment;)I", "Landroidx/fragment/app/Fragment;", "(Landroidx/fragment/app/Fragment;)I", "checkFitsSystemWindows", "Landroid/view/View;", "getCheckFitsSystemWindows", "(Landroid/view/View;)Z", "hasNavigationBar", "getHasNavigationBar", "(Landroid/app/Activity;)Z", "(Landroid/app/Fragment;)Z", "Landroid/content/Context;", "(Landroid/content/Context;)Z", "(Landroidx/fragment/app/Fragment;)Z", "hasNotchScreen", "getHasNotchScreen", "isGesture", "isNavigationAtBottom", "navigationBarHeight", "getNavigationBarHeight", "(Landroid/content/Context;)I", "navigationBarWidth", "getNavigationBarWidth", "notchHeight", "getNotchHeight", "statusBarHeight", "getStatusBarHeight", "destroyImmersionBar", "", "dialog", "Landroid/app/Dialog;", "isOnly", "fitsStatusBarView", "view", "fitsTitleBar", "", "(Landroid/app/Activity;[Landroid/view/View;)V", "(Landroid/app/Fragment;[Landroid/view/View;)V", "(Landroidx/fragment/app/Fragment;[Landroid/view/View;)V", "fitsTitleBarMarginTop", "hideStatusBar", "immersionBar", "block", "Lkotlin/Function1;", "Lcom/gyf/immersionbar/ImmersionBar;", "Lkotlin/ExtensionFunctionType;", "activity", "Landroid/app/DialogFragment;", "Landroidx/fragment/app/DialogFragment;", "setFitsSystemWindows", "showStatusBar", "immersionbar-ktx_release"}, k = 2, mv = {1, 4, 2})
public final class ImmersionBarKt {
    public static final void destroyImmersionBar(Activity activity, Dialog dialog) {
        destroyImmersionBar$default(activity, dialog, false, 2, (Object) null);
    }

    public static final void destroyImmersionBar(Fragment fragment, Dialog dialog) {
        destroyImmersionBar$default(fragment, dialog, false, 2, (Object) null);
    }

    public static final void destroyImmersionBar(androidx.fragment.app.Fragment fragment, Dialog dialog) {
        destroyImmersionBar$default(fragment, dialog, false, 2, (Object) null);
    }

    public static final void immersionBar(Activity activity) {
        immersionBar$default(activity, false, 1, (Object) null);
    }

    public static final void immersionBar(Activity activity, Dialog dialog) {
        immersionBar$default(activity, dialog, false, 2, (Object) null);
    }

    public static final void immersionBar(Activity activity, Dialog dialog, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(activity, dialog, false, (Function1) function1, 2, (Object) null);
    }

    public static final void immersionBar(Activity activity, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(activity, false, (Function1) function1, 1, (Object) null);
    }

    public static final void immersionBar(Dialog dialog, Activity activity) {
        immersionBar$default(dialog, activity, false, 2, (Object) null);
    }

    public static final void immersionBar(Dialog dialog, Activity activity, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(dialog, activity, false, (Function1) function1, 2, (Object) null);
    }

    public static final void immersionBar(DialogFragment dialogFragment) {
        immersionBar$default(dialogFragment, false, 1, (Object) null);
    }

    public static final void immersionBar(DialogFragment dialogFragment, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(dialogFragment, false, (Function1) function1, 1, (Object) null);
    }

    public static final void immersionBar(Fragment fragment) {
        immersionBar$default(fragment, false, 1, (Object) null);
    }

    public static final void immersionBar(Fragment fragment, Dialog dialog) {
        immersionBar$default(fragment, dialog, false, 2, (Object) null);
    }

    public static final void immersionBar(Fragment fragment, Dialog dialog, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(fragment, dialog, false, (Function1) function1, 2, (Object) null);
    }

    public static final void immersionBar(Fragment fragment, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(fragment, false, (Function1) function1, 1, (Object) null);
    }

    public static final void immersionBar(androidx.fragment.app.DialogFragment dialogFragment) {
        immersionBar$default(dialogFragment, false, 1, (Object) null);
    }

    public static final void immersionBar(androidx.fragment.app.DialogFragment dialogFragment, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(dialogFragment, false, (Function1) function1, 1, (Object) null);
    }

    public static final void immersionBar(androidx.fragment.app.Fragment fragment) {
        immersionBar$default(fragment, false, 1, (Object) null);
    }

    public static final void immersionBar(androidx.fragment.app.Fragment fragment, Dialog dialog) {
        immersionBar$default(fragment, dialog, false, 2, (Object) null);
    }

    public static final void immersionBar(androidx.fragment.app.Fragment fragment, Dialog dialog, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(fragment, dialog, false, (Function1) function1, 2, (Object) null);
    }

    public static final void immersionBar(androidx.fragment.app.Fragment fragment, Function1<? super ImmersionBar, Unit> function1) {
        immersionBar$default(fragment, false, (Function1) function1, 1, (Object) null);
    }

    public static /* synthetic */ void immersionBar$default(Activity immersionBar, boolean z, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static final void immersionBar(Activity immersionBar, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static /* synthetic */ void immersionBar$default(androidx.fragment.app.Fragment immersionBar, boolean z, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static final void immersionBar(androidx.fragment.app.Fragment immersionBar, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static /* synthetic */ void immersionBar$default(Fragment immersionBar, boolean z, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static final void immersionBar(Fragment immersionBar, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static /* synthetic */ void immersionBar$default(androidx.fragment.app.DialogFragment immersionBar, boolean z, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static final void immersionBar(androidx.fragment.app.DialogFragment immersionBar, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static /* synthetic */ void immersionBar$default(DialogFragment immersionBar, boolean z, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static final void immersionBar(DialogFragment immersionBar, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static /* synthetic */ void immersionBar$default(Dialog immersionBar, Activity activity, boolean z, Function1 block, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(activity, immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static final void immersionBar(Dialog immersionBar, Activity activity, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(activity, immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static /* synthetic */ void immersionBar$default(Activity immersionBar, Dialog dialog, boolean z, Function1 block, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, dialog, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static final void immersionBar(Activity immersionBar, Dialog dialog, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(block, "block");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, dialog, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        block.invoke(immersionBarWith);
        immersionBarWith.init();
    }

    public static /* synthetic */ void immersionBar$default(androidx.fragment.app.Fragment immersionBar, Dialog dialog, boolean z, Function1 block, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(block, "block");
        FragmentActivity activity = immersionBar.getActivity();
        if (activity != null) {
            ImmersionBar immersionBarWith = ImmersionBar.with(activity, dialog, z);
            Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
            block.invoke(immersionBarWith);
            immersionBarWith.init();
        }
    }

    public static final void immersionBar(androidx.fragment.app.Fragment immersionBar, Dialog dialog, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(block, "block");
        FragmentActivity activity = immersionBar.getActivity();
        if (activity != null) {
            ImmersionBar immersionBarWith = ImmersionBar.with(activity, dialog, z);
            Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
            block.invoke(immersionBarWith);
            immersionBarWith.init();
        }
    }

    public static /* synthetic */ void immersionBar$default(Fragment immersionBar, Dialog dialog, boolean z, Function1 block, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(block, "block");
        Activity activity = immersionBar.getActivity();
        if (activity != null) {
            ImmersionBar immersionBarWith = ImmersionBar.with(activity, dialog, z);
            Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
            block.invoke(immersionBarWith);
            immersionBarWith.init();
        }
    }

    public static final void immersionBar(Fragment immersionBar, Dialog dialog, boolean z, Function1<? super ImmersionBar, Unit> block) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(block, "block");
        Activity activity = immersionBar.getActivity();
        if (activity != null) {
            ImmersionBar immersionBarWith = ImmersionBar.with(activity, dialog, z);
            Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
            block.invoke(immersionBarWith);
            immersionBarWith.init();
        }
    }

    public static /* synthetic */ void immersionBar$default(Activity activity, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        immersionBar(activity, z);
    }

    public static /* synthetic */ void immersionBar$default(androidx.fragment.app.Fragment fragment, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        immersionBar(fragment, z);
    }

    public static /* synthetic */ void immersionBar$default(Fragment fragment, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        immersionBar(fragment, z);
    }

    public static /* synthetic */ void immersionBar$default(androidx.fragment.app.DialogFragment dialogFragment, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        immersionBar(dialogFragment, z);
    }

    public static /* synthetic */ void immersionBar$default(DialogFragment dialogFragment, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        immersionBar(dialogFragment, z);
    }

    public static /* synthetic */ void immersionBar$default(Dialog dialog, Activity activity, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        immersionBar(dialog, activity, z);
    }

    public static /* synthetic */ void immersionBar$default(Activity activity, Dialog dialog, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        immersionBar(activity, dialog, z);
    }

    public static /* synthetic */ void immersionBar$default(androidx.fragment.app.Fragment fragment, Dialog dialog, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        immersionBar(fragment, dialog, z);
    }

    public static /* synthetic */ void immersionBar$default(Fragment fragment, Dialog dialog, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        immersionBar(fragment, dialog, z);
    }

    public static /* synthetic */ void destroyImmersionBar$default(Activity activity, Dialog dialog, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        destroyImmersionBar(activity, dialog, z);
    }

    public static final void destroyImmersionBar(Activity destroyImmersionBar, Dialog dialog, boolean z) {
        Intrinsics.checkNotNullParameter(destroyImmersionBar, "$this$destroyImmersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        ImmersionBar.destroy(destroyImmersionBar, dialog, z);
    }

    public static /* synthetic */ void destroyImmersionBar$default(androidx.fragment.app.Fragment fragment, Dialog dialog, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        destroyImmersionBar(fragment, dialog, z);
    }

    public static final void destroyImmersionBar(androidx.fragment.app.Fragment destroyImmersionBar, Dialog dialog, boolean z) {
        Intrinsics.checkNotNullParameter(destroyImmersionBar, "$this$destroyImmersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        FragmentActivity activity = destroyImmersionBar.getActivity();
        if (activity != null) {
            ImmersionBar.destroy(activity, dialog, z);
        }
    }

    public static /* synthetic */ void destroyImmersionBar$default(Fragment fragment, Dialog dialog, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        destroyImmersionBar(fragment, dialog, z);
    }

    public static final void destroyImmersionBar(Fragment destroyImmersionBar, Dialog dialog, boolean z) {
        Intrinsics.checkNotNullParameter(destroyImmersionBar, "$this$destroyImmersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Activity activity = destroyImmersionBar.getActivity();
        if (activity != null) {
            ImmersionBar.destroy(activity, dialog, z);
        }
    }

    public static final int getStatusBarHeight(Activity statusBarHeight) {
        Intrinsics.checkNotNullParameter(statusBarHeight, "$this$statusBarHeight");
        return ImmersionBar.getStatusBarHeight(statusBarHeight);
    }

    public static final int getStatusBarHeight(androidx.fragment.app.Fragment statusBarHeight) {
        Intrinsics.checkNotNullParameter(statusBarHeight, "$this$statusBarHeight");
        return ImmersionBar.getStatusBarHeight(statusBarHeight);
    }

    public static final int getStatusBarHeight(Fragment statusBarHeight) {
        Intrinsics.checkNotNullParameter(statusBarHeight, "$this$statusBarHeight");
        return ImmersionBar.getStatusBarHeight(statusBarHeight);
    }

    public static final int getStatusBarHeight(Context statusBarHeight) {
        Intrinsics.checkNotNullParameter(statusBarHeight, "$this$statusBarHeight");
        return ImmersionBar.getStatusBarHeight(statusBarHeight);
    }

    public static final int getNavigationBarHeight(Activity navigationBarHeight) {
        Intrinsics.checkNotNullParameter(navigationBarHeight, "$this$navigationBarHeight");
        return ImmersionBar.getNavigationBarHeight(navigationBarHeight);
    }

    public static final int getNavigationBarHeight(androidx.fragment.app.Fragment navigationBarHeight) {
        Intrinsics.checkNotNullParameter(navigationBarHeight, "$this$navigationBarHeight");
        return ImmersionBar.getNavigationBarHeight(navigationBarHeight);
    }

    public static final int getNavigationBarHeight(Fragment navigationBarHeight) {
        Intrinsics.checkNotNullParameter(navigationBarHeight, "$this$navigationBarHeight");
        return ImmersionBar.getNavigationBarHeight(navigationBarHeight);
    }

    public static final int getNavigationBarHeight(Context navigationBarHeight) {
        Intrinsics.checkNotNullParameter(navigationBarHeight, "$this$navigationBarHeight");
        return ImmersionBar.getNavigationBarHeight(navigationBarHeight);
    }

    public static final int getNavigationBarWidth(Activity navigationBarWidth) {
        Intrinsics.checkNotNullParameter(navigationBarWidth, "$this$navigationBarWidth");
        return ImmersionBar.getNavigationBarWidth(navigationBarWidth);
    }

    public static final int getNavigationBarWidth(androidx.fragment.app.Fragment navigationBarWidth) {
        Intrinsics.checkNotNullParameter(navigationBarWidth, "$this$navigationBarWidth");
        return ImmersionBar.getNavigationBarWidth(navigationBarWidth);
    }

    public static final int getNavigationBarWidth(Fragment navigationBarWidth) {
        Intrinsics.checkNotNullParameter(navigationBarWidth, "$this$navigationBarWidth");
        return ImmersionBar.getNavigationBarWidth(navigationBarWidth);
    }

    public static final int getNavigationBarWidth(Context navigationBarWidth) {
        Intrinsics.checkNotNullParameter(navigationBarWidth, "$this$navigationBarWidth");
        return ImmersionBar.getNavigationBarWidth(navigationBarWidth);
    }

    public static final int getActionBarHeight(Activity actionBarHeight) {
        Intrinsics.checkNotNullParameter(actionBarHeight, "$this$actionBarHeight");
        return ImmersionBar.getActionBarHeight(actionBarHeight);
    }

    public static final int getActionBarHeight(androidx.fragment.app.Fragment actionBarHeight) {
        Intrinsics.checkNotNullParameter(actionBarHeight, "$this$actionBarHeight");
        return ImmersionBar.getActionBarHeight(actionBarHeight);
    }

    public static final int getActionBarHeight(Fragment actionBarHeight) {
        Intrinsics.checkNotNullParameter(actionBarHeight, "$this$actionBarHeight");
        return ImmersionBar.getActionBarHeight(actionBarHeight);
    }

    public static final boolean getHasNavigationBar(Activity hasNavigationBar) {
        Intrinsics.checkNotNullParameter(hasNavigationBar, "$this$hasNavigationBar");
        return ImmersionBar.hasNavigationBar(hasNavigationBar);
    }

    public static final boolean getHasNavigationBar(androidx.fragment.app.Fragment hasNavigationBar) {
        Intrinsics.checkNotNullParameter(hasNavigationBar, "$this$hasNavigationBar");
        return ImmersionBar.hasNavigationBar(hasNavigationBar);
    }

    public static final boolean getHasNavigationBar(Fragment hasNavigationBar) {
        Intrinsics.checkNotNullParameter(hasNavigationBar, "$this$hasNavigationBar");
        return ImmersionBar.hasNavigationBar(hasNavigationBar);
    }

    public static final boolean getHasNavigationBar(Context hasNavigationBar) {
        Intrinsics.checkNotNullParameter(hasNavigationBar, "$this$hasNavigationBar");
        return ImmersionBar.hasNavigationBar(hasNavigationBar);
    }

    public static final boolean getHasNotchScreen(Activity hasNotchScreen) {
        Intrinsics.checkNotNullParameter(hasNotchScreen, "$this$hasNotchScreen");
        return ImmersionBar.hasNotchScreen(hasNotchScreen);
    }

    public static final boolean getHasNotchScreen(androidx.fragment.app.Fragment hasNotchScreen) {
        Intrinsics.checkNotNullParameter(hasNotchScreen, "$this$hasNotchScreen");
        return ImmersionBar.hasNotchScreen(hasNotchScreen);
    }

    public static final boolean getHasNotchScreen(Fragment hasNotchScreen) {
        Intrinsics.checkNotNullParameter(hasNotchScreen, "$this$hasNotchScreen");
        return ImmersionBar.hasNotchScreen(hasNotchScreen);
    }

    public static final boolean getHasNotchScreen(View hasNotchScreen) {
        Intrinsics.checkNotNullParameter(hasNotchScreen, "$this$hasNotchScreen");
        return ImmersionBar.hasNotchScreen(hasNotchScreen);
    }

    public static final int getNotchHeight(Activity notchHeight) {
        Intrinsics.checkNotNullParameter(notchHeight, "$this$notchHeight");
        return ImmersionBar.getNotchHeight(notchHeight);
    }

    public static final int getNotchHeight(androidx.fragment.app.Fragment notchHeight) {
        Intrinsics.checkNotNullParameter(notchHeight, "$this$notchHeight");
        return ImmersionBar.getNotchHeight(notchHeight);
    }

    public static final int getNotchHeight(Fragment notchHeight) {
        Intrinsics.checkNotNullParameter(notchHeight, "$this$notchHeight");
        return ImmersionBar.getNotchHeight(notchHeight);
    }

    public static final boolean isSupportStatusBarDarkFont() {
        return ImmersionBar.isSupportStatusBarDarkFont();
    }

    public static final boolean isSupportNavigationIconDark() {
        return ImmersionBar.isSupportNavigationIconDark();
    }

    public static final boolean getCheckFitsSystemWindows(View checkFitsSystemWindows) {
        Intrinsics.checkNotNullParameter(checkFitsSystemWindows, "$this$checkFitsSystemWindows");
        return ImmersionBar.checkFitsSystemWindows(checkFitsSystemWindows);
    }

    public static final boolean isNavigationAtBottom(Activity isNavigationAtBottom) {
        Intrinsics.checkNotNullParameter(isNavigationAtBottom, "$this$isNavigationAtBottom");
        return ImmersionBar.isNavigationAtBottom(isNavigationAtBottom);
    }

    public static final boolean isNavigationAtBottom(androidx.fragment.app.Fragment isNavigationAtBottom) {
        Intrinsics.checkNotNullParameter(isNavigationAtBottom, "$this$isNavigationAtBottom");
        return ImmersionBar.isNavigationAtBottom(isNavigationAtBottom);
    }

    public static final boolean isNavigationAtBottom(Fragment isNavigationAtBottom) {
        Intrinsics.checkNotNullParameter(isNavigationAtBottom, "$this$isNavigationAtBottom");
        return ImmersionBar.isNavigationAtBottom(isNavigationAtBottom);
    }

    public static final boolean isGesture(Context isGesture) {
        Intrinsics.checkNotNullParameter(isGesture, "$this$isGesture");
        return ImmersionBar.isGesture(isGesture);
    }

    public static final boolean isGesture(androidx.fragment.app.Fragment isGesture) {
        Intrinsics.checkNotNullParameter(isGesture, "$this$isGesture");
        return ImmersionBar.isGesture(isGesture);
    }

    public static final boolean isGesture(Fragment isGesture) {
        Intrinsics.checkNotNullParameter(isGesture, "$this$isGesture");
        return ImmersionBar.isGesture(isGesture);
    }

    public static final void fitsStatusBarView(Activity fitsStatusBarView, View view) {
        Intrinsics.checkNotNullParameter(fitsStatusBarView, "$this$fitsStatusBarView");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setStatusBarView(fitsStatusBarView, view);
    }

    public static final void fitsStatusBarView(androidx.fragment.app.Fragment fitsStatusBarView, View view) {
        Intrinsics.checkNotNullParameter(fitsStatusBarView, "$this$fitsStatusBarView");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setStatusBarView(fitsStatusBarView, view);
    }

    public static final void fitsStatusBarView(Fragment fitsStatusBarView, View view) {
        Intrinsics.checkNotNullParameter(fitsStatusBarView, "$this$fitsStatusBarView");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setStatusBarView(fitsStatusBarView, view);
    }

    public static final void fitsTitleBar(Activity fitsTitleBar, View... view) {
        Intrinsics.checkNotNullParameter(fitsTitleBar, "$this$fitsTitleBar");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setTitleBar(fitsTitleBar, (View[]) Arrays.copyOf(view, view.length));
    }

    public static final void fitsTitleBar(androidx.fragment.app.Fragment fitsTitleBar, View... view) {
        Intrinsics.checkNotNullParameter(fitsTitleBar, "$this$fitsTitleBar");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setTitleBar(fitsTitleBar, (View[]) Arrays.copyOf(view, view.length));
    }

    public static final void fitsTitleBar(Fragment fitsTitleBar, View... view) {
        Intrinsics.checkNotNullParameter(fitsTitleBar, "$this$fitsTitleBar");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setTitleBar(fitsTitleBar, (View[]) Arrays.copyOf(view, view.length));
    }

    public static final void fitsTitleBarMarginTop(Activity fitsTitleBarMarginTop, View... view) {
        Intrinsics.checkNotNullParameter(fitsTitleBarMarginTop, "$this$fitsTitleBarMarginTop");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setTitleBarMarginTop(fitsTitleBarMarginTop, (View[]) Arrays.copyOf(view, view.length));
    }

    public static final void fitsTitleBarMarginTop(androidx.fragment.app.Fragment fitsTitleBarMarginTop, View... view) {
        Intrinsics.checkNotNullParameter(fitsTitleBarMarginTop, "$this$fitsTitleBarMarginTop");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setTitleBarMarginTop(fitsTitleBarMarginTop, (View[]) Arrays.copyOf(view, view.length));
    }

    public static final void fitsTitleBarMarginTop(Fragment fitsTitleBarMarginTop, View... view) {
        Intrinsics.checkNotNullParameter(fitsTitleBarMarginTop, "$this$fitsTitleBarMarginTop");
        Intrinsics.checkNotNullParameter(view, "view");
        ImmersionBar.setTitleBarMarginTop(fitsTitleBarMarginTop, (View[]) Arrays.copyOf(view, view.length));
    }

    public static final void hideStatusBar(Activity hideStatusBar) {
        Intrinsics.checkNotNullParameter(hideStatusBar, "$this$hideStatusBar");
        ImmersionBar.hideStatusBar(hideStatusBar.getWindow());
    }

    public static final void hideStatusBar(androidx.fragment.app.Fragment hideStatusBar) {
        Intrinsics.checkNotNullParameter(hideStatusBar, "$this$hideStatusBar");
        FragmentActivity activity = hideStatusBar.getActivity();
        if (activity != null) {
            ImmersionBar.hideStatusBar(activity.getWindow());
        }
    }

    public static final void hideStatusBar(Fragment hideStatusBar) {
        Intrinsics.checkNotNullParameter(hideStatusBar, "$this$hideStatusBar");
        Activity activity = hideStatusBar.getActivity();
        if (activity != null) {
            ImmersionBar.hideStatusBar(activity.getWindow());
        }
    }

    public static final void showStatusBar(Activity showStatusBar) {
        Intrinsics.checkNotNullParameter(showStatusBar, "$this$showStatusBar");
        ImmersionBar.showStatusBar(showStatusBar.getWindow());
    }

    public static final void showStatusBar(androidx.fragment.app.Fragment showStatusBar) {
        Intrinsics.checkNotNullParameter(showStatusBar, "$this$showStatusBar");
        FragmentActivity activity = showStatusBar.getActivity();
        if (activity != null) {
            ImmersionBar.showStatusBar(activity.getWindow());
        }
    }

    public static final void showStatusBar(Fragment showStatusBar) {
        Intrinsics.checkNotNullParameter(showStatusBar, "$this$showStatusBar");
        Activity activity = showStatusBar.getActivity();
        if (activity != null) {
            ImmersionBar.showStatusBar(activity.getWindow());
        }
    }

    public static final void setFitsSystemWindows(Activity setFitsSystemWindows) {
        Intrinsics.checkNotNullParameter(setFitsSystemWindows, "$this$setFitsSystemWindows");
        ImmersionBar.setFitsSystemWindows(setFitsSystemWindows);
    }

    public static final void setFitsSystemWindows(androidx.fragment.app.Fragment setFitsSystemWindows) {
        Intrinsics.checkNotNullParameter(setFitsSystemWindows, "$this$setFitsSystemWindows");
        ImmersionBar.setFitsSystemWindows(setFitsSystemWindows);
    }

    public static final void setFitsSystemWindows(Fragment setFitsSystemWindows) {
        Intrinsics.checkNotNullParameter(setFitsSystemWindows, "$this$setFitsSystemWindows");
        ImmersionBar.setFitsSystemWindows(setFitsSystemWindows);
    }

    public static final void immersionBar(Activity immersionBar, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.init();
    }

    public static final void immersionBar(androidx.fragment.app.Fragment immersionBar, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.init();
    }

    public static final void immersionBar(Fragment immersionBar, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.init();
    }

    public static final void immersionBar(androidx.fragment.app.DialogFragment immersionBar, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.init();
    }

    public static final void immersionBar(DialogFragment immersionBar, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.init();
    }

    public static final void immersionBar(Dialog immersionBar, Activity activity, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(activity, "activity");
        ImmersionBar immersionBarWith = ImmersionBar.with(activity, immersionBar, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.init();
    }

    public static final void immersionBar(Activity immersionBar, Dialog dialog, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        ImmersionBar immersionBarWith = ImmersionBar.with(immersionBar, dialog, z);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.init();
    }

    public static final void immersionBar(androidx.fragment.app.Fragment immersionBar, Dialog dialog, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        FragmentActivity activity = immersionBar.getActivity();
        if (activity != null) {
            ImmersionBar immersionBarWith = ImmersionBar.with(activity, dialog, z);
            Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
            immersionBarWith.init();
        }
    }

    public static final void immersionBar(Fragment immersionBar, Dialog dialog, boolean z) {
        Intrinsics.checkNotNullParameter(immersionBar, "$this$immersionBar");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Activity activity = immersionBar.getActivity();
        if (activity != null) {
            ImmersionBar immersionBarWith = ImmersionBar.with(activity, dialog, z);
            Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
            immersionBarWith.init();
        }
    }
}
