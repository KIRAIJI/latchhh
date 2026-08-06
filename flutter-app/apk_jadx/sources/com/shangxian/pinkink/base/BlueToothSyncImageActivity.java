package com.shangxian.pinkink.base;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.viewbinding.ViewBinding;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.event.BlueToothConnectStateEvent;
import com.shangxian.pinkink.event.SyncImageProgressEvent;
import com.shangxian.pinkink.ui.device.DeviceScanActivity;
import com.shangxian.pinkink.ui.dialog.SyncImageDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: compiled from: BlueToothSyncImageActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rJ\u001a\u0010\u0011\u001a\u00020\u000f2\u0010\u0010\u0012\u001a\f\u0012\u0004\u0012\u00020\u000f0\u0013j\u0002`\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0012\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u000fH\u0014J\u0010\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u001dH\u0007J\u0006\u0010\u001e\u001a\u00020\u000fJ\u0006\u0010\u001f\u001a\u00020\u000fR\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/shangxian/pinkink/base/BlueToothSyncImageActivity;", "VB", "Landroidx/viewbinding/ViewBinding;", "Lcom/shangxian/pinkink/base/BaseActivity;", "()V", "syncImageDialog", "Lcom/shangxian/pinkink/ui/dialog/SyncImageDialog;", "getSyncImageDialog", "()Lcom/shangxian/pinkink/ui/dialog/SyncImageDialog;", "syncImageDialog$delegate", "Lkotlin/Lazy;", "tempLocalImagePathList", "", "", "addLocalTmpFilePath", "", "path", "checkBlueToothPermission", "onGrantedCallback", "Lkotlin/Function0;", "Lcom/lazyee/klib/typed/VoidCallback;", "onBluetoothConnectStateChanged", NotificationCompat.CATEGORY_EVENT, "Lcom/shangxian/pinkink/event/BlueToothConnectStateEvent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onSyncImageProgressChanged", "Lcom/shangxian/pinkink/event/SyncImageProgressEvent;", "showConnectDeviceDialog", "showSyncImageDialog", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public class BlueToothSyncImageActivity<VB extends ViewBinding> extends BaseActivity<VB> {

    /* JADX INFO: renamed from: syncImageDialog$delegate, reason: from kotlin metadata */
    private final Lazy syncImageDialog = LazyKt.lazy(new Function0<SyncImageDialog>(this) { // from class: com.shangxian.pinkink.base.BlueToothSyncImageActivity$syncImageDialog$2
        final /* synthetic */ BlueToothSyncImageActivity<VB> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final SyncImageDialog invoke() {
            return new SyncImageDialog(this.this$0.getActivity());
        }
    });
    private final List<String> tempLocalImagePathList = new ArrayList();

    private final SyncImageDialog getSyncImageDialog() {
        return (SyncImageDialog) this.syncImageDialog.getValue();
    }

    @Override // com.shangxian.pinkink.base.BaseActivity, com.lazyee.klib.base.ViewBindingActivity, com.lazyee.klib.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onSyncImageProgressChanged(SyncImageProgressEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        getSyncImageDialog().updateSyncInfo(event.getCurrent(), event.getTotal(), event.getCurrentBitmapIndex(), event.getTotalBitmapCount());
        if (event.getCurrent() == event.getTotal() && event.getCurrentBitmapIndex() == event.getTotalBitmapCount()) {
            getMViewBinding().getRoot().postDelayed(new Runnable() { // from class: com.shangxian.pinkink.base.BlueToothSyncImageActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BlueToothSyncImageActivity.m105onSyncImageProgressChanged$lambda0(this.f$0);
                }
            }, 50L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onSyncImageProgressChanged$lambda-0, reason: not valid java name */
    public static final void m105onSyncImageProgressChanged$lambda0(BlueToothSyncImageActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getSyncImageDialog().dismiss();
        ContextExtensionsKt.toastShort(this$0, "传输完毕，请等待墨水屏刷新");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onBluetoothConnectStateChanged(BlueToothConnectStateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getState() == 0) {
            getSyncImageDialog().dismiss();
            ContextExtensionsKt.toastShort(this, "已与墨水瓶断开链接");
        }
    }

    public final void addLocalTmpFilePath(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.tempLocalImagePathList.add(path);
    }

    public final void showSyncImageDialog() {
        getSyncImageDialog().reset();
        getSyncImageDialog().show();
    }

    public final void showConnectDeviceDialog() {
        new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("当前App没有连接到墨水屏设备,请前往连接").setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.shangxian.pinkink.base.BlueToothSyncImageActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.shangxian.pinkink.base.BlueToothSyncImageActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BlueToothSyncImageActivity.m107showConnectDeviceDialog$lambda2(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: showConnectDeviceDialog$lambda-2, reason: not valid java name */
    public static final void m107showConnectDeviceDialog$lambda2(final BlueToothSyncImageActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        dialogInterface.dismiss();
        this$0.checkBlueToothPermission(new Function0<Unit>(this$0) { // from class: com.shangxian.pinkink.base.BlueToothSyncImageActivity$showConnectDeviceDialog$2$1
            final /* synthetic */ BlueToothSyncImageActivity<VB> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this$0;
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws Exception {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws Exception {
                ContextExtensionsKt.goto$default(this.this$0, DeviceScanActivity.class, null, null, null, null, 30, null);
            }
        });
    }

    private final void checkBlueToothPermission(final Function0<Unit> onGrantedCallback) {
        XXPermissions.with(getActivity()).permission(Permission.ACCESS_FINE_LOCATION, Permission.BLUETOOTH_SCAN, Permission.BLUETOOTH_CONNECT, Permission.BLUETOOTH_SCAN).request(new OnPermissionCallback() { // from class: com.shangxian.pinkink.base.BlueToothSyncImageActivity.checkBlueToothPermission.1
            @Override // com.hjq.permissions.OnPermissionCallback
            public void onGranted(List<String> permissions, boolean allGranted) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                onGrantedCallback.invoke();
            }

            @Override // com.hjq.permissions.OnPermissionCallback
            public void onDenied(List<String> permissions, boolean doNotAskAgain) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                OnPermissionCallback.CC.$default$onDenied(this, permissions, doNotAskAgain);
                BlueToothSyncImageActivity<VB> blueToothSyncImageActivity = this;
                BlueToothSyncImageActivity<VB> blueToothSyncImageActivity2 = blueToothSyncImageActivity;
                String string = blueToothSyncImageActivity.getString(R.string.toast_please_authorize_bluetooth_permission);
                Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…ize_bluetooth_permission)");
                ContextExtensionsKt.toastShort(blueToothSyncImageActivity2, string);
            }
        });
    }

    @Override // com.lazyee.klib.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00631(this, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.base.BlueToothSyncImageActivity$onDestroy$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BlueToothSyncImageActivity.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "VB", "Landroidx/viewbinding/ViewBinding;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.base.BlueToothSyncImageActivity$onDestroy$1", f = "BlueToothSyncImageActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C00631 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;
        final /* synthetic */ BlueToothSyncImageActivity<VB> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00631(BlueToothSyncImageActivity<VB> blueToothSyncImageActivity, Continuation<? super C00631> continuation) {
            super(2, continuation);
            this.this$0 = blueToothSyncImageActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00631(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00631) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Iterator it = ((BlueToothSyncImageActivity) this.this$0).tempLocalImagePathList.iterator();
            while (it.hasNext()) {
                File file = new File((String) it.next());
                if (file.exists()) {
                    file.delete();
                }
            }
            return Unit.INSTANCE;
        }
    }
}
