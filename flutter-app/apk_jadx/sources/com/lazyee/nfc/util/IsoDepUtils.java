package com.lazyee.nfc.util;

import android.content.Context;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.util.Log;
import com.fmsh.compress.CompressUtils;
import com.lazyee.nfc.R;
import com.lazyee.nfc.bean.DeviceInfo;
import com.lazyee.nfc.constant.NfcConstant;
import com.lazyee.nfc.event.LoadDeviceInfoSuccessEvent;
import com.lazyee.nfc.event.ShowFailedDialogEvent;
import com.lazyee.nfc.event.ShowSuccessDialogEvent;
import com.lazyee.nfc.manager.BroadcastManager;
import com.lazyee.nfc.manager.NfcManager;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class IsoDepUtils {
    private static String mBmpPath = null;
    private static int sendApdu_back6986_index = 5;

    public static boolean isContains(Tag tag) {
        return IsoDep.get(tag) != null;
    }

    public static byte[] getATS(Tag tag) {
        IsoDep isoDep = IsoDep.get(tag);
        return isoDep != null ? isoDep.getHistoricalBytes() : new byte[2];
    }

    public static void startIsoDep(Context context, Bundle bundle) {
        IsoDep isoDep;
        Tag tag = (Tag) bundle.getParcelable(NfcConstant.KEY_TAG);
        if (tag == null || (isoDep = IsoDep.get(tag)) == null) {
            return;
        }
        try {
            try {
                try {
                    isoDep.setTimeout(50000);
                    if (!isoDep.isConnected()) {
                        isoDep.connect();
                    }
                    LogUtil.e(FMUtil.byteToHex(isoDep.transceive(FMUtil.hexToByte("00A4040007D2760000850101"))));
                    LogUtil.e("start", Long.valueOf(System.currentTimeMillis()));
                    int i = bundle.getInt("position");
                    if (i == 0) {
                        getDeviceInfo(isoDep);
                    } else if (i != 1) {
                        if (i != 2) {
                            if (i == 3) {
                                updatePin(context, isoDep, bundle.getString("oldPin"), bundle.getString("pin"));
                            }
                        } else if (!bundle.getBoolean("isPin") || verifyPinCode(context, isoDep, bundle.getString("pin"))) {
                            sendApdu(isoDep, bundle.getString("apdu"));
                        }
                    } else if (!NfcManager.getDeviceInfo().getPin() || verifyPinCode(context, isoDep, bundle.getString("pin"))) {
                        mBmpPath = bundle.getString("path");
                        handlerImage(context, isoDep, bundle.getBoolean("isLvl"));
                    }
                    isoDep.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
                    isoDep.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                isoDep.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }

    private static void sendApdu(IsoDep isoDep, String str) throws IOException {
        EventBus.getDefault().post(new ShowSuccessDialogEvent(FMUtil.byteToHex(isoDep.transceive(FMUtil.hexToByte(str)))));
    }

    private static void handlerImage(Context context, IsoDep isoDep, boolean z) throws IOException {
        if (BmpUtils.GetBmpFormat(mBmpPath, IUtils.loadDeviceInfo(context, FMUtil.byteToHex(isoDep.transceive(FMUtil.hexToByte("00D1000000"))) + FMUtil.byteToHex(isoDep.transceive(FMUtil.hexToByte("F0D8000005000000000E")))))) {
            int colorCount = NfcManager.getDeviceInfo().getColorCount();
            if (colorCount == 2) {
                writeBlackWhiteScreen(context, isoDep);
                return;
            } else if (colorCount == 3) {
                write24ColorScreen(context, isoDep, z);
                return;
            } else {
                write4ColorScreen(context, isoDep, z);
                return;
            }
        }
        EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.bmp_error)));
    }

    private static void writeBlackWhiteScreen(Context context, IsoDep isoDep) {
        byte[] bArrReadBmp8File = ImageUtils.ReadBmp8File(mBmpPath);
        if (bArrReadBmp8File == null) {
            EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
            return;
        }
        LogUtil.d(FMUtil.byteToHex(bArrReadBmp8File));
        int iCeil = (int) Math.ceil(((double) getDeviceInfo().getWidth()) / 8.0d);
        int i = iCeil % 4;
        int i2 = i != 0 ? (iCeil - i) + 4 + 0 : iCeil;
        int iCeil2 = (int) Math.ceil(((double) getDeviceInfo().getHeight()) / 8.0d);
        byte[] bArrHorizontalScanning = new byte[bArrReadBmp8File.length];
        if (getDeviceInfo().getRefreshScan() == 0) {
            ImageUtils.VerticalScanning(bArrHorizontalScanning, bArrReadBmp8File, iCeil, iCeil2, i2);
            LogUtil.d("转换", Long.valueOf(System.currentTimeMillis()));
        } else {
            bArrHorizontalScanning = ImageUtils.HorizontalScanning(bArrReadBmp8File, iCeil, getDeviceInfo().getHeight(), i2);
        }
        if (WriteBmpdataEx(context, isoDep, bArrHorizontalScanning, (int) Math.ceil(((double) ((getDeviceInfo().getWidth() * getDeviceInfo().getHeight()) / 250)) / 8.0d), 0)) {
            RefreshScreenAfterWriteBmpdataex(context, isoDep, 0, false, false);
        }
    }

    private static void write4ColorScreen(Context context, IsoDep isoDep, boolean z) {
        byte[] bArrColor24horizontalScanning;
        byte[] bArrColor4horizontalScanning;
        byte[] bArrReadBmp24File = ImageUtils.ReadBmp24File(mBmpPath, z);
        if (bArrReadBmp24File == null) {
            EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
            return;
        }
        int width = getDeviceInfo().getWidth() * getDeviceInfo().getHeight();
        byte[] bArr = new byte[width];
        byte[] bArr2 = new byte[width];
        ImageUtils.getColorDataBmp4(bArrReadBmp24File, bArr);
        String colorDesc = getDeviceInfo().getColorDesc();
        byte[] bArrColor24horizontalScanning2 = null;
        if (colorDesc == null) {
            if (getDeviceInfo().getRefreshScan() == 0) {
                bArrColor24horizontalScanning2 = ImageUtils.Color24VerticalScanning(bArr);
                bArrColor24horizontalScanning = ImageUtils.Color24VerticalScanning(bArr2);
            } else {
                bArrColor24horizontalScanning2 = ImageUtils.color24horizontalScanning(bArr);
                bArrColor24horizontalScanning = ImageUtils.color24horizontalScanning(bArr2);
            }
        } else if (getDeviceInfo().getColor().contentEquals("黑白红黄四色")) {
            if (getDeviceInfo().getRefreshScan() == 0) {
                bArrColor4horizontalScanning = ImageUtils.Color4VerticalScanning(bArr);
            } else {
                bArrColor4horizontalScanning = ImageUtils.color4horizontalScanning(bArr);
            }
            bArrColor24horizontalScanning2 = bArrColor4horizontalScanning;
            bArrColor24horizontalScanning = null;
        } else {
            bArrColor24horizontalScanning = null;
        }
        int iCeil = (int) Math.ceil(((double) (width / 250)) / 8.0d);
        if (colorDesc == null) {
            if (getDeviceInfo().getSize() == 1) {
                if (!WriteBmpdataEx(context, isoDep, bArrColor24horizontalScanning2, iCeil, 0)) {
                    return;
                }
            } else if (getDeviceInfo().getSize() == 2 && (!WriteBmpdataEx(context, isoDep, bArrColor24horizontalScanning2, iCeil, 0) || !WriteBmpdataEx(context, isoDep, bArrColor24horizontalScanning, iCeil, 1))) {
                return;
            }
        } else if (getDeviceInfo().getColor().contentEquals("黑白红黄四色") && !WriteBmpdataEx(context, isoDep, bArrColor24horizontalScanning2, iCeil * 2, 0)) {
            return;
        }
        RefreshScreenAfterWriteBmpdataex(context, isoDep, 0, false, false);
    }

    private static void write24ColorScreen(Context context, IsoDep isoDep, boolean z) {
        byte[] bArrColor24horizontalScanning;
        byte[] bArrColor24horizontalScanning2;
        byte[] bArrReadBmp24File = ImageUtils.ReadBmp24File(mBmpPath, z);
        if (bArrReadBmp24File == null) {
            EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
            return;
        }
        int width = getDeviceInfo().getWidth() * getDeviceInfo().getHeight();
        byte[] bArr = new byte[width];
        byte[] bArr2 = new byte[width];
        ImageUtils.getColorDataBmp24(bArrReadBmp24File, bArr, bArr2);
        if (getDeviceInfo().getRefreshScan() == 0) {
            bArrColor24horizontalScanning = ImageUtils.Color24VerticalScanning(bArr);
            bArrColor24horizontalScanning2 = ImageUtils.Color24VerticalScanning(bArr2);
        } else {
            bArrColor24horizontalScanning = ImageUtils.color24horizontalScanning(bArr);
            bArrColor24horizontalScanning2 = ImageUtils.color24horizontalScanning(bArr2);
        }
        int iCeil = (int) Math.ceil(((double) (width / 250)) / 8.0d);
        if (getDeviceInfo().getSize() == 1) {
            if (!WriteBmpdataEx(context, isoDep, bArrColor24horizontalScanning, iCeil, 0)) {
                return;
            }
        } else if (getDeviceInfo().getSize() == 2 && (!WriteBmpdataEx(context, isoDep, bArrColor24horizontalScanning, iCeil, 0) || !WriteBmpdataEx(context, isoDep, bArrColor24horizontalScanning2, iCeil, 1))) {
            return;
        }
        RefreshScreenAfterWriteBmpdataex(context, isoDep, 0, false, false);
    }

    private static boolean WriteBmpdataEx(Context context, IsoDep isoDep, byte[] bArr, int i, int i2) {
        int i3;
        boolean zIsCompress = getDeviceInfo().isCompress();
        int width = getDeviceInfo().getWidth();
        int height = getDeviceInfo().getHeight();
        if (zIsCompress) {
            try {
                LogUtil.d("压缩", Long.valueOf(System.currentTimeMillis()));
                LogUtil.d(FMUtil.byteToHex(bArr));
                int i4 = height % 8;
                if (i4 != 0) {
                    height += 8 - i4;
                }
                if (getDeviceInfo().getColorDesc() == null) {
                    i3 = (width * height) / 8;
                } else {
                    i3 = getDeviceInfo().getColor().contentEquals("黑白红黄四色") ? (width * height) / 4 : 0;
                }
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArr, 0, bArr2, 0, i3);
                LogUtil.d(FMUtil.byteToHex(bArr2));
                String strCompress = CompressUtils.compress(isoDep, bArr2, i2);
                LogUtil.d("压缩完成", Long.valueOf(System.currentTimeMillis()));
                if ("9000".equals(strCompress)) {
                    return true;
                }
                EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
                return false;
            }
        }
        LogUtil.d("发送", Long.valueOf(System.currentTimeMillis()));
        int i5 = i * 250;
        if (bArr.length < i5) {
            byte[] bArr3 = new byte[i5];
            System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
            bArr = bArr3;
        }
        byte[] bArr4 = new byte[255];
        bArr4[0] = -16;
        bArr4[1] = -46;
        bArr4[2] = (byte) i2;
        bArr4[4] = -6;
        for (int i6 = 0; i6 < i; i6++) {
            bArr4[3] = (byte) i6;
            for (int i7 = 0; i7 < 250; i7++) {
                bArr4[i7 + 5] = bArr[(i6 * 250) + i7];
            }
            try {
                LogUtil.d(FMUtil.byteToHex(bArr4));
                String strByteToHex = FMUtil.byteToHex(isoDep.transceive(bArr4));
                if (!strByteToHex.substring(strByteToHex.length() - 4).equals("9000")) {
                    EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
                    return false;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
                return false;
            }
        }
        return true;
    }

    private static void RefreshScreenAfterWriteBmpdataex(Context context, IsoDep isoDep, int i, boolean z, boolean z2) {
        LogUtil.d("刷屏", Long.valueOf(System.currentTimeMillis()));
        byte[] bArr = {-16, -44, 5, 0, 0};
        if (z2) {
            bArr[2] = -123;
        }
        try {
            LogUtil.d(FMUtil.byteToHex(bArr));
            String strByteToHex = FMUtil.byteToHex(isoDep.transceive(bArr));
            LogUtil.d("RefreshScreenAfterWriteBmpdataex:" + strByteToHex);
            if (strByteToHex.equals("698A")) {
                EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
            } else if (strByteToHex.equals("6986")) {
                int i2 = sendApdu_back6986_index;
                if (i2 > 0) {
                    sendApdu_back6986_index = i2 - 1;
                    RefreshScreenAfterWriteBmpdataex(context, isoDep, i, false, false);
                } else {
                    EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.string_res_37)));
                    sendApdu_back6986_index = 5;
                }
            } else if ("68C6".equals(strByteToHex)) {
                if (!z) {
                    RefreshScreenAfterWriteBmpdataex(context, isoDep, i, true, false);
                } else if (!z2) {
                    RefreshScreenAfterWriteBmpdataex(context, isoDep, i, true, true);
                } else {
                    EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.string_res_37)));
                }
            } else if (strByteToHex.equals("019000")) {
                sendApdu_back6986_index = 5;
                EventBus.getDefault().post(new ShowSuccessDialogEvent(context.getString(R.string.text_success)));
            } else if (strByteToHex.equals("009000") || strByteToHex.equals("9000")) {
                sendApdu_back6986_index = 5;
                GetRefreshResult(context, isoDep, i, z, z2);
            } else {
                bArr[3] = (byte) i;
                if (!FMUtil.byteToHex(isoDep.transceive(bArr)).equals("9000")) {
                    EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
                } else {
                    EventBus.getDefault().post(new ShowSuccessDialogEvent(context.getString(R.string.text_success)));
                }
            }
            LogUtil.d("刷屏", Long.valueOf(System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
        }
    }

    private static void GetRefreshResult(Context context, IsoDep isoDep, int i, boolean z, boolean z2) {
        byte[] bArr = {-16, -34, 0, 0, 1};
        for (int i2 = 0; i2 < 1000; i2++) {
            try {
                LogUtil.d(FMUtil.byteToHex(bArr));
                String strByteToHex = FMUtil.byteToHex(isoDep.transceive(bArr));
                Log.d("<-", strByteToHex);
                if (!strByteToHex.equals("009000") && !strByteToHex.equals("019000")) {
                    if (strByteToHex.equals("698A")) {
                        EventBus.getDefault().post(new ShowFailedDialogEvent(context.getResources().getString(R.string.hint_error) + strByteToHex));
                        return;
                    }
                    if (strByteToHex.equals("6986")) {
                        EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.string_res_37)));
                        return;
                    }
                    if (!"68C6".equals(strByteToHex)) {
                        EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
                        return;
                    }
                    if (!z) {
                        RefreshScreenAfterWriteBmpdataex(context, isoDep, i, true, z2);
                    } else if (!z2) {
                        RefreshScreenAfterWriteBmpdataex(context, isoDep, i, z, true);
                    } else {
                        EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.string_res_37)));
                    }
                }
                EventBus.getDefault().post(new ShowSuccessDialogEvent(context.getString(R.string.text_success)));
                LogUtil.d("success", Long.valueOf(System.currentTimeMillis()));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.hint_error)));
                return;
            }
        }
    }

    private static void getDeviceInfo(IsoDep isoDep) throws IOException {
        byte[] bArrTransceive = isoDep.transceive(FMUtil.hexToByte("00D1000000"));
        String strByteToHex = FMUtil.byteToHex(isoDep.transceive(FMUtil.hexToByte("F0D8000005000000000E")));
        EventBus.getDefault().post(new LoadDeviceInfoSuccessEvent(FMUtil.byteToHex(bArrTransceive) + strByteToHex));
    }

    private static DeviceInfo getDeviceInfo() {
        return NfcManager.getDeviceInfo();
    }

    private static void updatePin(Context context, IsoDep isoDep, String str, String str2) {
        String str3;
        try {
            byte[] bArrHexToByte = FMUtil.hexToByte(getDeviceInfo().getAppID());
            byte[] bArrHexToByte2 = FMUtil.hexToByte(getDeviceInfo().getUID());
            for (int i = 0; i < 4; i++) {
                bArrHexToByte[i] = (byte) (~bArrHexToByte[i]);
                bArrHexToByte2[i] = (byte) (~bArrHexToByte2[i]);
            }
            String str4 = FMUtil.byteToHex(bArrHexToByte2) + FMUtil.byteToHex(bArrHexToByte);
            LogUtil.d(str4);
            byte[] bArr = new byte[8];
            System.arraycopy(isoDep.transceive(new byte[]{0, -124, 0, 0, 4}), 0, bArr, 0, 4);
            String strByteToHex = FMUtil.byteToHex(bArr);
            LogUtil.d(strByteToHex);
            String str5 = "80D90101" + String.format("%02x", Integer.valueOf((str.length() / 2) + (str2.length() / 2) + 5)) + str + "FF" + str2;
            if ((str5.length() & 16) == 0) {
                str3 = str5 + "8000000000000000";
            } else {
                str3 = str5 + "80";
                while (str3.length() % 16 != 0) {
                    str3 = str3 + "00";
                }
            }
            LogUtil.d(str3);
            int i2 = 0;
            while (i2 < str3.length() / 16) {
                int i3 = i2 * 16;
                i2++;
                strByteToHex = EncryUtils.encryptDES(strByteToHex, str4, str3.substring(i3, i2 * 16));
                LogUtil.d(strByteToHex);
            }
            String str6 = str5 + strByteToHex.substring(0, 8);
            LogUtil.d(str6);
            String strByteToHex2 = FMUtil.byteToHex(isoDep.transceive(FMUtil.hexToByte(str6)));
            LogUtil.d(strByteToHex2);
            BroadcastManager.getInstance(context).sendBroadcast("pin", strByteToHex2);
        } catch (Exception e) {
            e.printStackTrace();
            BroadcastManager.getInstance(context).sendBroadcast("pin", e.getMessage());
        }
    }

    private static boolean verifyPinCode(Context context, IsoDep isoDep, String str) throws IOException {
        byte[] bArrTransceive = isoDep.transceive(FMUtil.hexToByte(String.format("00200001%02x%s", Integer.valueOf(str.length() / 2), str)));
        String strByteToHex = FMUtil.byteToHex(bArrTransceive);
        LogUtil.d(bArrTransceive);
        if ("9000".equals(strByteToHex)) {
            return true;
        }
        EventBus.getDefault().post(new ShowFailedDialogEvent(context.getString(R.string.string_res_5) + "(" + strByteToHex + ")"));
        return false;
    }
}
