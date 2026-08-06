package com.shangxian.pinkink.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.databinding.ActivityMainBinding;
import com.shangxian.pinkink.event.BlueToothConnectStateEvent;
import com.shangxian.pinkink.manager.PinkInkBlueToothManager;
import com.shangxian.pinkink.ui.device.SwitchDeviceActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000  2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001 B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u001a\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u000bH\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\u0010\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0012\u0010\u0018\u001a\u00020\u000b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u000bH\u0014J\"\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/shangxian/pinkink/ui/main/MainActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityMainBinding;", "()V", "albumFragment", "Lcom/shangxian/pinkink/ui/main/AlbumFragment;", "indexFragment", "Lcom/shangxian/pinkink/ui/main/IndexFragment2;", "mineFragment", "Lcom/shangxian/pinkink/ui/main/MineFragment;", "changeTabSelectedState", "", "id", "", "hideFragment", "transaction", "Landroidx/fragment/app/FragmentTransaction;", "fragment", "Landroidx/fragment/app/Fragment;", "initData", "initView", "onBluetoothConnectStateChanged", NotificationCompat.CATEGORY_EVENT, "Lcom/shangxian/pinkink/event/BlueToothConnectStateEvent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "showFragment", "tag", "", "showTargetFragment", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MainActivity extends BaseActivity<ActivityMainBinding> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private IndexFragment2 indexFragment = (IndexFragment2) getSupportFragmentManager().findFragmentByTag("TAG_FRAGMENT_INDEX");
    private AlbumFragment albumFragment = (AlbumFragment) getSupportFragmentManager().findFragmentByTag("TAG_FRAGMENT_ALBUM");
    private MineFragment mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("TAG_FRAGMENT_MINE");

    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/shangxian/pinkink/ui/main/MainActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (AppConfig.INSTANCE.isInitDeviceType()) {
                context.startActivity(new Intent(context, (Class<?>) MainActivity.class));
            } else {
                context.startActivity(new Intent(context, (Class<?>) SwitchDeviceActivity.class));
            }
        }
    }

    @Override // com.shangxian.pinkink.base.BaseActivity, com.lazyee.klib.base.ViewBindingActivity, com.lazyee.klib.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        initData();
        ActivityMainBinding activityMainBinding = (ActivityMainBinding) getMViewBinding();
        changeTabSelectedState(activityMainBinding.llIndex.getId());
        activityMainBinding.llIndex.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MainActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m234initView$lambda3$lambda0(this.f$0, view);
            }
        });
        activityMainBinding.llAlbum.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MainActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m235initView$lambda3$lambda1(this.f$0, view);
            }
        });
        activityMainBinding.llMine.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.MainActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m236initView$lambda3$lambda2(this.f$0, view);
            }
        });
        activityMainBinding.llIndex.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-0, reason: not valid java name */
    public static final void m234initView$lambda3$lambda0(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeTabSelectedState(view.getId());
        this$0.showTargetFragment("TAG_FRAGMENT_INDEX");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-1, reason: not valid java name */
    public static final void m235initView$lambda3$lambda1(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeTabSelectedState(view.getId());
        this$0.showTargetFragment("TAG_FRAGMENT_ALBUM");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-2, reason: not valid java name */
    public static final void m236initView$lambda3$lambda2(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeTabSelectedState(view.getId());
        this$0.showTargetFragment("TAG_FRAGMENT_MINE");
    }

    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initData() {
        super.initData();
        String macAddress = AppConfig.INSTANCE.getMacAddress();
        if (TextUtils.isEmpty(macAddress)) {
            return;
        }
        PinkInkBlueToothManager.INSTANCE.connect(macAddress);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onBluetoothConnectStateChanged(BlueToothConnectStateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int state = event.getState();
        if (state == 0) {
            getLoadingDialog().dismiss();
            String string = getString(R.string.toast_eink_device_has_been_disconnected);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…ce_has_been_disconnected)");
            ContextExtensionsKt.toastShort(this, string);
            return;
        }
        if (state != 2) {
            return;
        }
        String string2 = getString(R.string.toast_eink_device_has_been_connected);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.toast…evice_has_been_connected)");
        ContextExtensionsKt.toastShort(this, string2);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void showTargetFragment(java.lang.String r4) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 54226141(0x33b6cdd, float:5.507933E-37)
            if (r0 == r1) goto L46
            r1 = 1670006533(0x638a4705, float:5.1015364E21)
            if (r0 == r1) goto L2d
            r1 = 1677455720(0x63fbf168, float:9.295056E21)
            if (r0 == r1) goto L14
            goto L4e
        L14:
            java.lang.String r0 = "TAG_FRAGMENT_INDEX"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L1d
            goto L4e
        L1d:
            com.shangxian.pinkink.ui.main.IndexFragment2 r0 = r3.indexFragment
            if (r0 != 0) goto L28
            com.shangxian.pinkink.ui.main.IndexFragment2 r0 = new com.shangxian.pinkink.ui.main.IndexFragment2
            r0.<init>()
            r3.indexFragment = r0
        L28:
            com.shangxian.pinkink.ui.main.IndexFragment2 r0 = r3.indexFragment
            androidx.fragment.app.Fragment r0 = (androidx.fragment.app.Fragment) r0
            goto L5f
        L2d:
            java.lang.String r0 = "TAG_FRAGMENT_ALBUM"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L36
            goto L4e
        L36:
            com.shangxian.pinkink.ui.main.AlbumFragment r0 = r3.albumFragment
            if (r0 != 0) goto L41
            com.shangxian.pinkink.ui.main.AlbumFragment r0 = new com.shangxian.pinkink.ui.main.AlbumFragment
            r0.<init>()
            r3.albumFragment = r0
        L41:
            com.shangxian.pinkink.ui.main.AlbumFragment r0 = r3.albumFragment
            androidx.fragment.app.Fragment r0 = (androidx.fragment.app.Fragment) r0
            goto L5f
        L46:
            java.lang.String r0 = "TAG_FRAGMENT_MINE"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L50
        L4e:
            r0 = 0
            goto L5f
        L50:
            com.shangxian.pinkink.ui.main.MineFragment r0 = r3.mineFragment
            if (r0 != 0) goto L5b
            com.shangxian.pinkink.ui.main.MineFragment r0 = new com.shangxian.pinkink.ui.main.MineFragment
            r0.<init>()
            r3.mineFragment = r0
        L5b:
            com.shangxian.pinkink.ui.main.MineFragment r0 = r3.mineFragment
            androidx.fragment.app.Fragment r0 = (androidx.fragment.app.Fragment) r0
        L5f:
            if (r0 != 0) goto L62
            return
        L62:
            androidx.fragment.app.FragmentManager r1 = r3.getSupportFragmentManager()
            androidx.fragment.app.FragmentTransaction r1 = r1.beginTransaction()
            java.lang.String r2 = "supportFragmentManager.beginTransaction()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            com.shangxian.pinkink.ui.main.IndexFragment2 r2 = r3.indexFragment
            androidx.fragment.app.Fragment r2 = (androidx.fragment.app.Fragment) r2
            r3.hideFragment(r1, r2)
            com.shangxian.pinkink.ui.main.AlbumFragment r2 = r3.albumFragment
            androidx.fragment.app.Fragment r2 = (androidx.fragment.app.Fragment) r2
            r3.hideFragment(r1, r2)
            com.shangxian.pinkink.ui.main.MineFragment r2 = r3.mineFragment
            androidx.fragment.app.Fragment r2 = (androidx.fragment.app.Fragment) r2
            r3.hideFragment(r1, r2)
            r3.showFragment(r1, r0, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.ui.main.MainActivity.showTargetFragment(java.lang.String):void");
    }

    private final void hideFragment(FragmentTransaction transaction, Fragment fragment) {
        if (fragment != null && fragment.isAdded()) {
            transaction.hide(fragment);
        }
    }

    private final void showFragment(FragmentTransaction transaction, Fragment fragment, String tag) {
        if (fragment == null) {
            return;
        }
        if (fragment.isAdded()) {
            transaction.show(fragment).commitAllowingStateLoss();
        } else {
            transaction.add(R.id.flContent, fragment, tag).commitAllowingStateLoss();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void changeTabSelectedState(int id) {
        ActivityMainBinding activityMainBinding = (ActivityMainBinding) getMViewBinding();
        activityMainBinding.llIndex.setEnabled(activityMainBinding.llIndex.getId() != id);
        activityMainBinding.llAlbum.setEnabled(activityMainBinding.llAlbum.getId() != id);
        activityMainBinding.llMine.setEnabled(activityMainBinding.llMine.getId() != id);
    }

    @Override // com.lazyee.klib.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
