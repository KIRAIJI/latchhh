package com.lazyee.nfc.util;

import android.content.Context;
import com.lazyee.nfc.R;
import com.lazyee.nfc.bean.DeviceInfo;

/* JADX INFO: loaded from: classes.dex */
public class IUtils {
    public static DeviceInfo loadDeviceInfo(Context context, String str) {
        String string;
        String str2;
        String string2;
        String string3;
        DeviceInfo deviceInfo = new DeviceInfo();
        try {
            byte[] bArrHexToByte = FMUtil.hexToByte(str);
            if (bArrHexToByte[0] == -96) {
                String string4 = context.getString(R.string.text_other);
                byte b = bArrHexToByte[2];
                if (b == -128) {
                    string4 = context.getString(R.string.text_weixinnuo);
                } else if (b == -16) {
                    string4 = context.getString(R.string.text_other);
                } else if (b == 0) {
                    string4 = context.getString(R.string.text_jiaxian);
                } else if (b == 16) {
                    string4 = context.getString(R.string.text_yuantai);
                } else if (b == 32) {
                    string4 = context.getString(R.string.text_aoyi);
                } else if (b == 48) {
                    string4 = context.getString(R.string.text_weifeng);
                } else if (b == 64) {
                    string4 = context.getString(R.string.text_pdi);
                } else if (b == 96) {
                    string4 = context.getString(R.string.text_jdf);
                } else if (b == 112) {
                    string4 = context.getString(R.string.text_dke);
                }
                deviceInfo.setManufacturer(String.format("%s(%02x)", string4, Byte.valueOf(bArrHexToByte[2])).toUpperCase());
                byte b2 = bArrHexToByte[4];
                if (b2 == 32) {
                    string2 = context.getString(R.string.text_color_20_cn);
                    string3 = context.getString(R.string.text_color_20);
                } else if (b2 == 48) {
                    string2 = context.getString(R.string.text_color_30_cn);
                    string3 = context.getString(R.string.text_color_30);
                } else if (b2 != 49) {
                    string2 = "";
                    string3 = string2;
                } else {
                    string2 = context.getString(R.string.text_color_31_cn);
                    string3 = context.getString(R.string.text_color_31);
                }
                deviceInfo.setColor(String.format("%s", string2));
                deviceInfo.setEN_Color(String.format("%s", string3));
                int i = Integer.parseInt(str.substring(10, 14), 16);
                int i2 = Integer.parseInt(str.substring(14, 18), 16);
                deviceInfo.setScreen(i2 + "x" + i);
                deviceInfo.setWidth(i2);
                deviceInfo.setHeight(i);
            }
            int i3 = bArrHexToByte[1] + 0 + 2;
            if (bArrHexToByte[i3] == -95) {
                int i4 = i3 + 2;
                byte b3 = bArrHexToByte[i4];
                if (b3 == 0) {
                    string = context.getString(R.string.text_screen_scan_vertical);
                } else {
                    string = b3 != 1 ? "" : context.getString(R.string.text_screen_scan_level);
                }
                deviceInfo.setRefreshScan(bArrHexToByte[i4]);
                deviceInfo.setScanType(String.format("(%s)", string));
                int i5 = i3 + 3;
                deviceInfo.setSize((bArrHexToByte[i5] >> 4) & 255);
                int i6 = bArrHexToByte[i5] & 15;
                deviceInfo.setColorCount(i6);
                StringBuilder sb = new StringBuilder();
                for (int i7 = 0; i7 < i6; i7++) {
                    int i8 = i3 + 4 + i7;
                    byte b4 = (byte) ((bArrHexToByte[i8] >> 5) & 255);
                    if (b4 == 0) {
                        int i9 = (bArrHexToByte[i8] >> ((3 - i6) + 3)) & (i6 == 2 ? 1 : 3);
                        deviceInfo.setBlack(i9);
                        str2 = String.format("%s(%s)", context.getString(R.string.text_color_black), Integer.valueOf(i9));
                    } else if (b4 == 1) {
                        int i10 = (bArrHexToByte[i8] >> ((3 - i6) + 3)) & (i6 == 2 ? 1 : 3);
                        deviceInfo.setWhite(i10);
                        str2 = String.format("%s(%s)", context.getString(R.string.text_color_white), Integer.valueOf(i10));
                    } else if (b4 == 2) {
                        int i11 = (bArrHexToByte[i8] >> ((3 - i6) + 3)) & (i6 == 2 ? 1 : 3);
                        deviceInfo.setRed(i11);
                        deviceInfo.setDeviceType(1);
                        str2 = String.format("%s(%s)", context.getString(R.string.text_color_red), Integer.valueOf(i11));
                    } else if (b4 != 3) {
                        str2 = "";
                    } else {
                        int i12 = (bArrHexToByte[i8] >> ((3 - i6) + 3)) & (i6 == 2 ? 1 : 3);
                        deviceInfo.setYellow(i12);
                        deviceInfo.setDeviceType(2);
                        str2 = String.format("%s(%s)", context.getString(R.string.text_color_yellow), Integer.valueOf(i12));
                    }
                    sb.append(str2);
                }
                deviceInfo.setColorType(sb.toString());
            }
            int i13 = i3 + bArrHexToByte[i3 + 1] + 2;
            if (bArrHexToByte[i13] == -79) {
                deviceInfo.setPictureCapacity(bArrHexToByte[i13 + 2]);
            }
            int i14 = i13 + bArrHexToByte[i13 + 1] + 2;
            if (bArrHexToByte[i14] == -78) {
                deviceInfo.setUserData(bArrHexToByte[i14 + 2]);
            }
            int i15 = i14 + bArrHexToByte[i14 + 1] + 2;
            if (bArrHexToByte[i15] == -77) {
                if (bArrHexToByte[i15 + 2] == 0) {
                    deviceInfo.setBattery(false);
                } else {
                    deviceInfo.setBattery(true);
                }
            }
            int i16 = i15 + bArrHexToByte[i15 + 1] + 2;
            if (bArrHexToByte[i16] == -64) {
                byte[] bArr = new byte[4];
                System.arraycopy(bArrHexToByte, i16 + 2, bArr, 0, 4);
                deviceInfo.setAppID(FMUtil.byteToHex(bArr));
            }
            int i17 = i16 + bArrHexToByte[i16 + 1] + 2;
            if (bArrHexToByte[i17] == -63) {
                byte[] bArr2 = new byte[4];
                System.arraycopy(bArrHexToByte, i17 + 2, bArr2, 0, 4);
                deviceInfo.setUID(FMUtil.byteToHex(bArr2));
            }
            int i18 = i17 + bArrHexToByte[i17 + 1] + 2;
            if (bArrHexToByte[i18] == -47) {
                if (bArrHexToByte[i18 + 2] == 0) {
                    deviceInfo.setCompress(false);
                } else {
                    deviceInfo.setCompress(true);
                }
                if (bArrHexToByte[i18 + 3] == 32) {
                    deviceInfo.setCosVersion(2);
                }
            }
            byte b5 = bArrHexToByte[i18 + 1];
            if (bArrHexToByte[bArrHexToByte.length - 2] == -112 && bArrHexToByte[bArrHexToByte.length - 1] == 0) {
                byte[] bArr3 = new byte[14];
                String colorDesc = null;
                System.arraycopy(bArrHexToByte, bArrHexToByte.length - 16, bArr3, 0, 14);
                if ("4_color Screen".contentEquals(FMUtil.byteToString(bArr3))) {
                    deviceInfo.setColorDesc(FMUtil.byteToString(bArr3));
                    colorDesc = deviceInfo.getColorDesc();
                }
                if (colorDesc != null && 14 == deviceInfo.getColorDesc().length() && "4_color Screen".contentEquals(deviceInfo.getColorDesc())) {
                    int i19 = Integer.parseInt(str.substring(10, 14), 16) / 2;
                    int i20 = Integer.parseInt(str.substring(14, 18), 16);
                    deviceInfo.setScreen(i20 + "x" + i19);
                    deviceInfo.setWidth(i20);
                    deviceInfo.setHeight(i19);
                    deviceInfo.setColor(context.getString(R.string.text_color_40_cn));
                    deviceInfo.setEN_Color("4-color");
                    deviceInfo.setDeviceType(3);
                    deviceInfo.setColorCount(4);
                    deviceInfo.setBlack(0);
                    deviceInfo.setWhite(1);
                    deviceInfo.setRed(3);
                    deviceInfo.setYellow(2);
                    deviceInfo.setColorType(String.format("黑(0)白(1)红(3)黄(2)", new Object[0]));
                }
                deviceInfo.setPin(false);
            } else if (bArrHexToByte[bArrHexToByte.length - 2] == 105 && bArrHexToByte[bArrHexToByte.length - 1] == -123) {
                deviceInfo.setPin(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceInfo;
    }

    public static boolean isCN(Context context) {
        String language = context.getResources().getConfiguration().locale.getLanguage();
        LogUtil.d(language);
        return "zh".equals(language);
    }
}
