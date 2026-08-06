package com.lazyee.nfc.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class BroadcastManager {
    private static BroadcastManager instance;
    private Context mContext;
    private Map<String, BroadcastReceiver> receiverMap = new HashMap();

    private BroadcastManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static BroadcastManager getInstance(Context context) {
        BroadcastManager broadcastManager = instance;
        if (broadcastManager == null && broadcastManager == null) {
            instance = new BroadcastManager(context);
        }
        return instance;
    }

    public void addAction(String str, BroadcastReceiver broadcastReceiver) {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(str);
            this.mContext.registerReceiver(broadcastReceiver, intentFilter);
            this.receiverMap.put(str, broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBroadcast(String str) {
        sendBroadcast(str, "");
    }

    public void sendBroadcast(String str, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(str);
        intent.putExtras(bundle);
        this.mContext.sendBroadcast(intent);
    }

    public void sendBroadcast(String str, String str2) {
        Intent intent = new Intent();
        intent.setAction(str);
        intent.putExtra("String", str2);
        this.mContext.sendBroadcast(intent);
    }

    public void sendBroadcast(String str, boolean z) {
        Intent intent = new Intent();
        intent.setAction(str);
        intent.putExtra(TypedValues.Custom.S_BOOLEAN, z);
        this.mContext.sendBroadcast(intent);
    }

    public void destroy(String str) {
        BroadcastReceiver broadcastReceiverRemove;
        Map<String, BroadcastReceiver> map = this.receiverMap;
        if (map == null || (broadcastReceiverRemove = map.remove(str)) == null) {
            return;
        }
        this.mContext.unregisterReceiver(broadcastReceiverRemove);
    }
}
