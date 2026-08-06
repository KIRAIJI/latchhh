package com.shangxian.pinkink.constants;

import android.content.Context;
import android.text.TextUtils;
import com.lazyee.klib.common.SP;
import com.lazyee.klib.json.MoshiJSON;
import com.shangxian.pinkink.bean.EInkDeviceBean;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010!\u001a\u0004\u0018\u00010\u000fJ\u0006\u0010\"\u001a\u00020\u0011J\b\u0010#\u001a\u0004\u0018\u00010\u0015J\u0006\u0010$\u001a\u00020\u0004J\u0006\u0010%\u001a\u00020\u0004J\u0006\u0010&\u001a\u00020\u0004J\u0006\u0010'\u001a\u00020\u0004J\u0006\u0010(\u001a\u00020\u0004J\u0006\u0010)\u001a\u00020\u001eJ\u0006\u0010*\u001a\u00020\u0004J\u0006\u0010+\u001a\u00020\u0004J\u0006\u0010,\u001a\u00020\u0004J\u0006\u0010-\u001a\u00020\u0004J\u000e\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201J\u0006\u00102\u001a\u00020\u001bJ\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u00103\u001a\u00020\u001bJ\u0006\u00104\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001bJ\u0006\u00105\u001a\u00020\u001bJ\u0010\u00106\u001a\u00020/2\b\u00107\u001a\u0004\u0018\u00010\u0015J\u000e\u00108\u001a\u00020/2\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u00109\u001a\u00020/2\u0006\u0010:\u001a\u00020\u001bJ\u000e\u0010;\u001a\u00020/2\u0006\u0010<\u001a\u00020\u001bJ\u000e\u0010=\u001a\u00020/2\u0006\u0010>\u001a\u00020\u001eJ\u000e\u0010?\u001a\u00020/2\u0006\u0010@\u001a\u00020\u0004J\u0010\u0010A\u001a\u00020/2\b\u0010 \u001a\u0004\u0018\u00010\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u0014\u0010\f\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0006R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lcom/shangxian/pinkink/constants/AppConfig;", "", "()V", "aiAlbumCoverUrl", "", "getAiAlbumCoverUrl", "()Ljava/lang/String;", "setAiAlbumCoverUrl", "(Ljava/lang/String;)V", "aiThemeCategoryId", "getAiThemeCategoryId", "setAiThemeCategoryId", "baseUrl", "getBaseUrl", "commonSP", "Lcom/lazyee/klib/common/SP;", "currentInkScreenSize", "Lcom/shangxian/pinkink/constants/InkScreenSize;", "deviceType", "domain", "eInkDevice", "Lcom/shangxian/pinkink/bean/EInkDeviceBean;", "firmwareSavePath", "fontSavePath", "imageSavePath", "imageTempPath", "isEnglishLanguage", "", "isLoopPictureEnable", "loopTime", "", "macAddress", "token", "getCommonSP", "getCurrentInkScreenSize", "getEInkDevice", "getFirmwareSavePath", "getFontSavePath", "getHelpUrl", "getImageSavePath", "getImageTempPath", "getLoopTime", "getMacAddress", "getPrivacyAgreementUrl", "getToken", "getUserProtocolUrl", "init", "", "context", "Landroid/content/Context;", "isBlueToothDevice", "isInitDeviceType", "isLogin", "isNfcDevice", "saveEInkDevice", "device", "setDeviceType", "setEnglishLanguage", "isEnglish", "setLoopPictureEnable", "enable", "setLoopTime", "time", "setMacAddress", "address", "setToken", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class AppConfig {
    private static SP commonSP;
    private static EInkDeviceBean eInkDevice;
    private static boolean isEnglishLanguage;
    private static boolean isLoopPictureEnable;
    public static final AppConfig INSTANCE = new AppConfig();
    private static String token = "";
    private static String imageSavePath = "";
    private static String imageTempPath = "";
    private static String fontSavePath = "";
    private static String firmwareSavePath = "";
    private static final String domain = "http://api.szcdt.net";
    private static final String baseUrl = Intrinsics.stringPlus("http://api.szcdt.net", "/api/pinkink/");
    private static InkScreenSize currentInkScreenSize = InkScreenSize.MODEL1;
    private static String aiAlbumCoverUrl = "";
    private static String aiThemeCategoryId = "1";
    private static int loopTime = 10;
    private static String macAddress = "";
    private static String deviceType = DeviceType.TYPE_NFC;

    private AppConfig() {
    }

    public final String getBaseUrl() {
        return baseUrl;
    }

    public final String getAiAlbumCoverUrl() {
        return aiAlbumCoverUrl;
    }

    public final void setAiAlbumCoverUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        aiAlbumCoverUrl = str;
    }

    public final String getAiThemeCategoryId() {
        return aiThemeCategoryId;
    }

    public final void setAiThemeCategoryId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        aiThemeCategoryId = str;
    }

    public final String getUserProtocolUrl() {
        return Intrinsics.stringPlus(domain, "/doc/agreement_zh.html");
    }

    public final String getPrivacyAgreementUrl() {
        return Intrinsics.stringPlus(domain, "/doc/privacy_zh.html");
    }

    public final String getHelpUrl() {
        return Intrinsics.stringPlus(domain, "/doc/help.html");
    }

    public final void init(Context context) {
        String strString;
        Integer numM96int;
        Intrinsics.checkNotNullParameter(context, "context");
        SP sp = new SP(context, "common", null, 4, null);
        commonSP = sp;
        String strString2 = sp.string(Keys.TOKEN);
        String str = "";
        if (strString2 == null) {
            strString2 = "";
        }
        token = strString2;
        SP sp2 = commonSP;
        int iIntValue = 10;
        if (sp2 != null && (numM96int = sp2.m96int(Keys.KEY_SP_PICTURE_LOOP_TIME, 10)) != null) {
            iIntValue = numM96int.intValue();
        }
        loopTime = iIntValue;
        SP sp3 = commonSP;
        isLoopPictureEnable = sp3 == null ? false : sp3.m92boolean(Keys.KEY_SP_PICTURE_LOOP_ENABLE, false);
        SP sp4 = commonSP;
        isEnglishLanguage = sp4 != null ? sp4.m92boolean(Keys.KEY_SP_IS_ENGLISH_LANGUAGE, false) : false;
        SP sp5 = commonSP;
        if (sp5 != null && (strString = sp5.string(Keys.KEY_SP_CONNECTED_BLUETOOTH_DEVICE_ADDRESS, "")) != null) {
            str = strString;
        }
        macAddress = str;
        SP sp6 = commonSP;
        deviceType = sp6 != null ? sp6.string(Keys.KEY_SP_DEVICE_TYPE, null) : null;
        imageSavePath = context.getFilesDir().getAbsolutePath() + ((Object) File.separator) + "images" + ((Object) File.separator);
        new File(imageSavePath).mkdirs();
        imageTempPath = context.getFilesDir().getAbsolutePath() + ((Object) File.separator) + "temp" + ((Object) File.separator);
        new File(imageTempPath).mkdirs();
        fontSavePath = context.getFilesDir().getAbsolutePath() + ((Object) File.separator) + "font" + ((Object) File.separator);
        new File(fontSavePath).mkdirs();
        firmwareSavePath = context.getFilesDir().getAbsolutePath() + ((Object) File.separator) + "firmware" + ((Object) File.separator);
        new File(firmwareSavePath).mkdirs();
    }

    public final boolean isLogin() {
        return !TextUtils.isEmpty(token);
    }

    public final void setToken(String token2) {
        if (token2 == null) {
            token2 = "";
        }
        token = token2;
        SP sp = commonSP;
        if (sp == null) {
            return;
        }
        sp.put(Keys.TOKEN, token2);
    }

    public final String getToken() {
        return token;
    }

    public final SP getCommonSP() {
        return commonSP;
    }

    public final String getImageSavePath() {
        return imageSavePath;
    }

    public final String getImageTempPath() {
        return imageTempPath;
    }

    public final String getFontSavePath() {
        return fontSavePath;
    }

    public final String getFirmwareSavePath() {
        return firmwareSavePath;
    }

    public final InkScreenSize getCurrentInkScreenSize() {
        return currentInkScreenSize;
    }

    public final void saveEInkDevice(EInkDeviceBean device) {
        eInkDevice = device;
        String json = MoshiJSON.INSTANCE.toJson(device);
        SP commonSP2 = getCommonSP();
        if (commonSP2 == null) {
            return;
        }
        commonSP2.put(Keys.EINK_DEVICE, json);
    }

    public final EInkDeviceBean getEInkDevice() {
        if (eInkDevice == null) {
            SP commonSP2 = getCommonSP();
            String strString = commonSP2 == null ? null : commonSP2.string(Keys.EINK_DEVICE);
            if (TextUtils.isEmpty(strString)) {
                return null;
            }
            eInkDevice = (EInkDeviceBean) MoshiJSON.INSTANCE.fromJson(strString, EInkDeviceBean.class);
        }
        return eInkDevice;
    }

    public final int getLoopTime() {
        return loopTime;
    }

    public final void setLoopTime(int time) {
        loopTime = time;
        SP sp = commonSP;
        if (sp == null) {
            return;
        }
        sp.put(Keys.KEY_SP_PICTURE_LOOP_TIME, Integer.valueOf(time));
    }

    public final boolean isLoopPictureEnable() {
        return isLoopPictureEnable;
    }

    public final void setLoopPictureEnable(boolean enable) {
        isLoopPictureEnable = enable;
        SP sp = commonSP;
        if (sp == null) {
            return;
        }
        sp.put(Keys.KEY_SP_PICTURE_LOOP_ENABLE, Boolean.valueOf(enable));
    }

    public final String getMacAddress() {
        return macAddress;
    }

    public final void setMacAddress(String address) {
        Intrinsics.checkNotNullParameter(address, "address");
        macAddress = address;
        SP sp = commonSP;
        if (sp == null) {
            return;
        }
        sp.put(Keys.KEY_SP_CONNECTED_BLUETOOTH_DEVICE_ADDRESS, address);
    }

    public final void setEnglishLanguage(boolean isEnglish) {
        isEnglishLanguage = isEnglish;
        SP sp = commonSP;
        if (sp == null) {
            return;
        }
        sp.put(Keys.KEY_SP_IS_ENGLISH_LANGUAGE, Boolean.valueOf(isEnglish));
    }

    public final boolean isEnglishLanguage() {
        return isEnglishLanguage;
    }

    public final boolean isInitDeviceType() {
        return deviceType != null;
    }

    public final void setDeviceType(String deviceType2) {
        Intrinsics.checkNotNullParameter(deviceType2, "deviceType");
        SP sp = commonSP;
        if (sp != null) {
            sp.put(Keys.KEY_SP_DEVICE_TYPE, deviceType2);
        }
        deviceType = deviceType2;
    }

    public final boolean isNfcDevice() {
        return Intrinsics.areEqual(deviceType, DeviceType.TYPE_NFC);
    }

    public final boolean isBlueToothDevice() {
        return Intrinsics.areEqual(deviceType, DeviceType.TYPE_BLUE_TOOTH);
    }
}
