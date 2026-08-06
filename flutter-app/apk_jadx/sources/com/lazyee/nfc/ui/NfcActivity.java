package com.lazyee.nfc.ui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.lazyee.nfc.R;
import com.lazyee.nfc.bean.DeviceInfo;
import com.lazyee.nfc.constant.NfcConstant;
import com.lazyee.nfc.event.LoadDeviceInfoSuccessEvent;
import com.lazyee.nfc.event.ShowFailedDialogEvent;
import com.lazyee.nfc.event.ShowSuccessDialogEvent;
import com.lazyee.nfc.manager.BroadcastManager;
import com.lazyee.nfc.manager.NfcManager;
import com.lazyee.nfc.util.IUtils;
import com.lazyee.nfc.util.LogUtil;
import com.lazyee.nfc.util.SpUtils;
import com.shangxian.pinkink.constants.DeviceType;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class NfcActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {
    private Button btnOperate;
    private boolean isScanning = false;
    private LinearLayout llBindDevice;
    private LinearLayout llScanNFC;
    private LinearLayout llWriteImage;
    private LinearLayout llWriteImageResult;
    private String mBmpFilePath;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private String mPin;
    public Tag mTag;
    private TextView tvWriteImageResult;

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
    }

    public static void gotoThis(Activity activity, String str) {
        Intent intent = new Intent(activity, (Class<?>) NfcActivity.class);
        intent.putExtra("imagePath", str);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_nfc_dialog_in, 0);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View decorView = getWindow().getDecorView();
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) decorView.getLayoutParams();
        layoutParams.gravity = 80;
        layoutParams.width = -1;
        layoutParams.height = -2;
        getWindowManager().updateViewLayout(decorView, layoutParams);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_nfc);
        setFinishOnTouchOutside(false);
        this.mBmpFilePath = getIntent().getStringExtra("imagePath");
        EventBus.getDefault().register(this);
        this.llBindDevice = (LinearLayout) findViewById(R.id.llBindDevice);
        this.llScanNFC = (LinearLayout) findViewById(R.id.llScanNFC);
        this.llWriteImage = (LinearLayout) findViewById(R.id.llWriteImage);
        this.llWriteImageResult = (LinearLayout) findViewById(R.id.llWriteImageResult);
        this.tvWriteImageResult = (TextView) findViewById(R.id.tvWriteImageResult);
        this.btnOperate = (Button) findViewById(R.id.btnOperate);
        String stringValue = SpUtils.getStringValue(this, "info", "");
        if (stringValue.isEmpty()) {
            showBindDeviceView();
        } else {
            DeviceInfo deviceInfo = (DeviceInfo) new Gson().fromJson(stringValue, DeviceInfo.class);
            deviceInfo.setBitmap(BitmapFactory.decodeFile(this.mBmpFilePath));
            NfcManager.setDeviceInfo(deviceInfo);
            showScanNfcLabelView();
        }
        BroadcastManager.getInstance(this).addAction(NfcConstant.KEY_TAG, new AnonymousClass1());
    }

    /* JADX INFO: renamed from: com.lazyee.nfc.ui.NfcActivity$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (NfcActivity.this.isScanning) {
                NfcActivity.this.runOnUiThread(new Runnable() { // from class: com.lazyee.nfc.ui.NfcActivity$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m103lambda$onReceive$0$comlazyeenfcuiNfcActivity$1();
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putParcelable(NfcConstant.KEY_TAG, NfcActivity.this.mTag);
                bundle.putInt("position", 1);
                bundle.putString("path", NfcActivity.this.mBmpFilePath);
                bundle.putString("pin", NfcActivity.this.mPin);
                NfcManager.startIsoDep(NfcActivity.this, bundle);
            }
        }

        /* JADX INFO: renamed from: lambda$onReceive$0$com-lazyee-nfc-ui-NfcActivity$1, reason: not valid java name */
        public /* synthetic */ void m103lambda$onReceive$0$comlazyeenfcuiNfcActivity$1() {
            NfcActivity.this.showWriteImageView();
        }
    }

    private void showBindDeviceView() {
        this.llBindDevice.setVisibility(0);
        this.llWriteImage.setVisibility(8);
        this.llScanNFC.setVisibility(8);
        this.llWriteImageResult.setVisibility(8);
        this.btnOperate.setVisibility(0);
        this.btnOperate.setText(R.string.string_res_3);
        this.btnOperate.setOnClickListener(new View.OnClickListener() { // from class: com.lazyee.nfc.ui.NfcActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m100lambda$showBindDeviceView$0$comlazyeenfcuiNfcActivity(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showBindDeviceView$0$com-lazyee-nfc-ui-NfcActivity, reason: not valid java name */
    public /* synthetic */ void m100lambda$showBindDeviceView$0$comlazyeenfcuiNfcActivity(View view) {
        finishThis();
    }

    private void showScanNfcLabelView() {
        this.isScanning = true;
        this.llBindDevice.setVisibility(8);
        this.llWriteImage.setVisibility(8);
        this.llScanNFC.setVisibility(0);
        this.llWriteImageResult.setVisibility(8);
        this.btnOperate.setVisibility(0);
        this.btnOperate.setText(R.string.string_res_3);
        this.btnOperate.setOnClickListener(new View.OnClickListener() { // from class: com.lazyee.nfc.ui.NfcActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m102lambda$showScanNfcLabelView$1$comlazyeenfcuiNfcActivity(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showScanNfcLabelView$1$com-lazyee-nfc-ui-NfcActivity, reason: not valid java name */
    public /* synthetic */ void m102lambda$showScanNfcLabelView$1$comlazyeenfcuiNfcActivity(View view) {
        finishThis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWriteImageView() {
        this.llBindDevice.setVisibility(8);
        this.llWriteImage.setVisibility(0);
        this.llScanNFC.setVisibility(8);
        this.llWriteImageResult.setVisibility(8);
        this.btnOperate.setVisibility(8);
    }

    private void showNfcResultView(String str) {
        this.llBindDevice.setVisibility(8);
        this.llWriteImage.setVisibility(8);
        this.llScanNFC.setVisibility(8);
        this.llWriteImageResult.setVisibility(0);
        this.tvWriteImageResult.setText(str);
        this.btnOperate.setVisibility(0);
        this.btnOperate.setText(R.string.string_res_4);
        this.btnOperate.setOnClickListener(new View.OnClickListener() { // from class: com.lazyee.nfc.ui.NfcActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m101lambda$showNfcResultView$2$comlazyeenfcuiNfcActivity(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showNfcResultView$2$com-lazyee-nfc-ui-NfcActivity, reason: not valid java name */
    public /* synthetic */ void m101lambda$showNfcResultView$2$comlazyeenfcuiNfcActivity(View view) {
        finishThis();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        hasNfc(this);
        if (this.mNfcAdapter != null) {
            Intent intent = new Intent(this, getClass());
            intent.addFlags(536870912);
            if (Build.VERSION.SDK_INT >= 31) {
                this.mPendingIntent = PendingIntent.getActivity(this, 0, intent, 67108864);
            } else {
                this.mPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        LogUtil.d(i + "---" + i2);
        if (i != 88 || hasNfc(this)) {
            return;
        }
        startAppSettings();
    }

    public boolean hasNfc(Activity activity) {
        NfcAdapter defaultAdapter = ((android.nfc.NfcManager) activity.getSystemService(DeviceType.TYPE_NFC)).getDefaultAdapter();
        this.mNfcAdapter = defaultAdapter;
        return defaultAdapter != null && defaultAdapter.isEnabled();
    }

    private void startAppSettings() {
        try {
            startActivityForResult(new Intent("android.settings.NFC_SETTINGS"), 88);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null || intent.getExtras() == null) {
            return;
        }
        Bundle bundle = new Bundle();
        Tag tag = (Tag) intent.getExtras().getParcelable("android.nfc.extra.TAG");
        this.mTag = tag;
        bundle.putParcelable(NfcConstant.KEY_TAG, tag);
        BroadcastManager.getInstance(this).sendBroadcast(NfcConstant.KEY_TAG, bundle);
        if (NfcManager.getDeviceInfo() == null) {
            bundle.putInt("position", 0);
            NfcManager.startIsoDep(this, bundle);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, this.mPendingIntent, null, null);
            Bundle bundle = new Bundle();
            bundle.putInt("presence", 250);
            this.mNfcAdapter.enableReaderMode(this, this, 287, bundle);
        }
    }

    @Override // android.nfc.NfcAdapter.ReaderCallback
    public void onTagDiscovered(Tag tag) {
        Log.e("TAG", "onTagDiscovered,tag:" + tag);
        if (tag == null) {
            return;
        }
        Bundle bundle = new Bundle();
        this.mTag = tag;
        bundle.putParcelable(NfcConstant.KEY_TAG, tag);
        BroadcastManager.getInstance(this).sendBroadcast(NfcConstant.KEY_TAG, bundle);
        if (NfcManager.getDeviceInfo() == null) {
            bundle.putInt("position", 0);
            NfcManager.startIsoDep(this, bundle);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowSuccessDialog(ShowSuccessDialogEvent showSuccessDialogEvent) {
        showNfcResultView(showSuccessDialogEvent.getString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowFailedDialog(ShowFailedDialogEvent showFailedDialogEvent) {
        showNfcResultView(showFailedDialogEvent.getString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadDeviceInfoSuccess(LoadDeviceInfoSuccessEvent loadDeviceInfoSuccessEvent) {
        if (loadDeviceInfoSuccessEvent.getInfo() == null) {
            Toast.makeText(this, R.string.text_error, 0).show();
            return;
        }
        DeviceInfo deviceInfoLoadDeviceInfo = IUtils.loadDeviceInfo(this, loadDeviceInfoSuccessEvent.getInfo());
        SpUtils.putStringValue(this, "info", new Gson().toJson(deviceInfoLoadDeviceInfo));
        NfcManager.setDeviceInfo(deviceInfoLoadDeviceInfo);
        showScanNfcLabelView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        BroadcastManager.getInstance(this).destroy(NfcConstant.KEY_TAG);
    }

    private void finishThis() {
        finish();
        overridePendingTransition(0, R.anim.anim_nfc_dialog_out);
    }
}
