package com.shangxian.pinkink.ui.device;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.lazyee.klib.app.AppManager;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.databinding.ActivityDeviceListBinding;
import com.shangxian.pinkink.databinding.ItemBlueToothDeviceBinding;
import com.shangxian.pinkink.event.BlueToothConnectStateEvent;
import com.shangxian.pinkink.manager.PinkInkBlueToothManager;
import com.shangxian.pinkink.ui.album.AlbumDetailActivity;
import com.shangxian.pinkink.ui.device.DeviceListActivity;
import com.shangxian.pinkink.ui.main.MainActivity;
import com.shangxian.pinkink.ui.themes.TransThemesActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: compiled from: DeviceListActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\r\u000eB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0012\u0010\t\u001a\u00020\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\b\u0010\f\u001a\u00020\u0005H\u0014¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/ui/device/DeviceListActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityDeviceListBinding;", "()V", "initView", "", "onBluetoothConnectStateChanged", NotificationCompat.CATEGORY_EVENT, "Lcom/shangxian/pinkink/event/BlueToothConnectStateEvent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "BlueToothDeviceAdapter", "BlueToothDeviceViewHolder", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class DeviceListActivity extends BaseActivity<ActivityDeviceListBinding> {
    @Override // com.shangxian.pinkink.base.BaseActivity, com.lazyee.klib.base.ViewBindingActivity, com.lazyee.klib.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        ActivityDeviceListBinding activityDeviceListBinding = (ActivityDeviceListBinding) getMViewBinding();
        activityDeviceListBinding.titleBar.tvTitle.setText(getString(R.string.title_add_device));
        activityDeviceListBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.device.DeviceListActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DeviceListActivity.m153initView$lambda1$lambda0(this.f$0, view);
            }
        });
        activityDeviceListBinding.rvDeviceList.setAdapter(new BlueToothDeviceAdapter(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-1$lambda-0, reason: not valid java name */
    public static final void m153initView$lambda1$lambda0(DeviceListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onBluetoothConnectStateChanged(BlueToothConnectStateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getState() == 2) {
            getLoadingDialog().dismiss();
            if (AppManager.INSTANCE.contains(TransThemesActivity.class)) {
                AppManager.INSTANCE.backTo(TransThemesActivity.class);
            } else if (AppManager.INSTANCE.contains(AlbumDetailActivity.class)) {
                AppManager.INSTANCE.backTo(AlbumDetailActivity.class);
            } else {
                AppManager.INSTANCE.finishAllExcept(MainActivity.class);
            }
        }
    }

    @Override // com.lazyee.klib.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /* JADX INFO: compiled from: DeviceListActivity.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u001c\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\u0002R\u00020\u00032\u0006\u0010\n\u001a\u00020\u0006H\u0016J\u001c\u0010\u000b\u001a\u00060\u0002R\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/ui/device/DeviceListActivity$BlueToothDeviceAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/shangxian/pinkink/ui/device/DeviceListActivity$BlueToothDeviceViewHolder;", "Lcom/shangxian/pinkink/ui/device/DeviceListActivity;", "(Lcom/shangxian/pinkink/ui/device/DeviceListActivity;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    private final class BlueToothDeviceAdapter extends RecyclerView.Adapter<BlueToothDeviceViewHolder> {
        final /* synthetic */ DeviceListActivity this$0;

        public BlueToothDeviceAdapter(DeviceListActivity this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public BlueToothDeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            DeviceListActivity deviceListActivity = this.this$0;
            ItemBlueToothDeviceBinding itemBlueToothDeviceBindingInflate = ItemBlueToothDeviceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            Intrinsics.checkNotNullExpressionValue(itemBlueToothDeviceBindingInflate, "inflate(LayoutInflater.f…nt.context),parent,false)");
            return new BlueToothDeviceViewHolder(deviceListActivity, itemBlueToothDeviceBindingInflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return PinkInkBlueToothManager.INSTANCE.getDeviceList().size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(BlueToothDeviceViewHolder holder, int position) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            BluetoothDevice bluetoothDevice = PinkInkBlueToothManager.INSTANCE.getDeviceList().get(position);
            Intrinsics.checkNotNullExpressionValue(bluetoothDevice, "PinkInkBlueToothManager.deviceList[position]");
            holder.bind(bluetoothDevice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeviceListActivity.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/ui/device/DeviceListActivity$BlueToothDeviceViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/shangxian/pinkink/databinding/ItemBlueToothDeviceBinding;", "(Lcom/shangxian/pinkink/ui/device/DeviceListActivity;Lcom/shangxian/pinkink/databinding/ItemBlueToothDeviceBinding;)V", "bind", "", "device", "Landroid/bluetooth/BluetoothDevice;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class BlueToothDeviceViewHolder extends RecyclerView.ViewHolder {
        private final ItemBlueToothDeviceBinding binding;
        final /* synthetic */ DeviceListActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BlueToothDeviceViewHolder(DeviceListActivity this$0, ItemBlueToothDeviceBinding binding) {
            super(binding.getRoot());
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(binding, "binding");
            this.this$0 = this$0;
            this.binding = binding;
        }

        public final void bind(final BluetoothDevice device) {
            Intrinsics.checkNotNullParameter(device, "device");
            ItemBlueToothDeviceBinding itemBlueToothDeviceBinding = this.binding;
            final DeviceListActivity deviceListActivity = this.this$0;
            itemBlueToothDeviceBinding.tvDeviceMac.setText(device.getAddress());
            itemBlueToothDeviceBinding.tvDeviceName.setText(device.getName());
            itemBlueToothDeviceBinding.tvAddDevice.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.device.DeviceListActivity$BlueToothDeviceViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DeviceListActivity.BlueToothDeviceViewHolder.m154bind$lambda1$lambda0(deviceListActivity, device, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bind$lambda-1$lambda-0, reason: not valid java name */
        public static final void m154bind$lambda1$lambda0(DeviceListActivity this$0, BluetoothDevice device, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(device, "$device");
            this$0.getLoadingDialog().show();
            PinkInkBlueToothManager pinkInkBlueToothManager = PinkInkBlueToothManager.INSTANCE;
            String address = device.getAddress();
            Intrinsics.checkNotNullExpressionValue(address, "device.address");
            pinkInkBlueToothManager.connect(address);
        }
    }
}
