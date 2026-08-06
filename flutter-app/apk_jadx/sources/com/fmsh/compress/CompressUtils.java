package com.fmsh.compress;

import android.nfc.tech.IsoDep;
import android.util.Log;
import com.contrarywind.timer.MessageHandler;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class CompressUtils {
    private static int[] dict = new int[131072];

    public static String compress(IsoDep isoDep, byte[] bArr, int i) throws Exception {
        if (bArr.length > 2000) {
            int length = bArr.length % MessageHandler.WHAT_SMOOTH_SCROLL;
            int length2 = bArr.length / MessageHandler.WHAT_SMOOTH_SCROLL;
            int i2 = 0;
            for (int i3 = 0; i3 < length2; i3++) {
                byte[] bArr2 = new byte[MessageHandler.WHAT_SMOOTH_SCROLL];
                System.arraycopy(bArr, i3 * MessageHandler.WHAT_SMOOTH_SCROLL, bArr2, 0, MessageHandler.WHAT_SMOOTH_SCROLL);
                MInt mInt = new MInt();
                mInt.v = 2192;
                byte[] bArr3 = new byte[mInt.v];
                MiniLZO.lzo1x_1_compress(bArr2, MessageHandler.WHAT_SMOOTH_SCROLL, bArr3, mInt, dict);
                int i4 = mInt.v;
                byte[] bArr4 = new byte[i4];
                System.arraycopy(bArr3, 0, bArr4, 0, i4);
                String strSendApdu = sendApdu(isoDep, bArr4, i, i2);
                if (!"9000".equals(strSendApdu)) {
                    return strSendApdu;
                }
                i2++;
            }
            if (length != 0) {
                byte[] bArr5 = new byte[length];
                System.arraycopy(bArr, length2 * MessageHandler.WHAT_SMOOTH_SCROLL, bArr5, 0, length);
                String strSendApdu2 = sendApdu(isoDep, compress(bArr5), i, i2);
                if (!"9000".equals(strSendApdu2)) {
                    return strSendApdu2;
                }
            }
        } else {
            String strSendApdu3 = sendApdu(isoDep, compress(bArr), i, 0);
            if (!"9000".equals(strSendApdu3)) {
                return strSendApdu3;
            }
        }
        return "9000";
    }

    private static String sendApdu(IsoDep isoDep, byte[] bArr, int i, int i2) throws Exception {
        byte b = -45;
        char c = 3;
        char c2 = 2;
        if (bArr.length > 250) {
            int length = bArr.length % 250;
            int length2 = bArr.length / 250;
            int i3 = 0;
            int i4 = 0;
            while (i4 < length2) {
                byte[] bArr2 = new byte[257];
                bArr2[0] = -16;
                bArr2[1] = b;
                bArr2[c2] = (byte) i;
                bArr2[c] = 0;
                if (length == 0 && i4 == length2 - 1) {
                    bArr2[c] = 1;
                }
                bArr2[4] = (byte) 252;
                bArr2[5] = (byte) i2;
                bArr2[6] = (byte) i3;
                System.arraycopy(bArr, i4 * 250, bArr2, 7, 250);
                String strSend = send(isoDep, bArr2);
                if (!"9000".equals(strSend)) {
                    return strSend;
                }
                i3++;
                i4++;
                b = -45;
                c = 3;
                c2 = 2;
            }
            if (length == 0) {
                return "9000";
            }
            byte[] bArr3 = new byte[length + 7];
            bArr3[0] = -16;
            bArr3[1] = -45;
            bArr3[2] = (byte) i;
            bArr3[3] = 1;
            bArr3[4] = (byte) (length + 2);
            bArr3[5] = (byte) i2;
            bArr3[6] = (byte) i3;
            System.arraycopy(bArr, length2 * 250, bArr3, 7, length);
            return send(isoDep, bArr3);
        }
        byte[] bArr4 = new byte[bArr.length + 7];
        bArr4[0] = -16;
        bArr4[1] = -45;
        bArr4[2] = (byte) i;
        bArr4[3] = 1;
        bArr4[4] = (byte) (bArr.length + 2);
        bArr4[5] = (byte) i2;
        bArr4[6] = 0;
        System.arraycopy(bArr, 0, bArr4, 7, bArr.length);
        return send(isoDep, bArr4);
    }

    private static String send(IsoDep isoDep, byte[] bArr) throws Exception {
        Log.d("wyjsend", byteToHex(bArr));
        String strByteToHex = byteToHex(isoDep.transceive(bArr));
        Log.d("wyjrec", strByteToHex);
        return !"9000".equals(strByteToHex.substring(strByteToHex.length() + (-4))) ? strByteToHex : "9000";
    }

    private static byte[] compress(byte[] bArr) {
        MInt mInt = new MInt();
        mInt.v = bArr.length + (bArr.length / 16) + 64 + 3;
        byte[] bArr2 = new byte[mInt.v];
        MiniLZO.lzo1x_1_compress(bArr, bArr.length, bArr2, mInt, dict);
        int i = mInt.v;
        byte[] bArr3 = new byte[i];
        System.arraycopy(bArr2, 0, bArr3, 0, i);
        return bArr3;
    }

    public static byte[] decompress(byte[] bArr) {
        MInt mInt = new MInt();
        mInt.v = 5000;
        byte[] bArr2 = new byte[mInt.v];
        MiniLZO.lzo1x_decompress(bArr, bArr.length, bArr2, mInt);
        int i = mInt.v;
        byte[] bArr3 = new byte[i];
        System.arraycopy(bArr2, 0, bArr3, 0, i);
        return bArr3;
    }

    public static void main(String[] strArr) {
        System.out.println(byteToHex(decompress(hexToByte("02FFFFFFFFFF280100C0282800840120203C00110000"))));
    }

    public static String byteToHex(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            str = hexString.length() == 1 ? str + "0" + hexString : str + hexString;
        }
        return str.toUpperCase();
    }

    public static byte[] hexToByte(String str) throws IllegalArgumentException {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[str.length() / 2];
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            StringBuilder sb = new StringBuilder();
            sb.append("");
            int i3 = i + 1;
            sb.append(charArray[i]);
            sb.append(charArray[i3]);
            bArr[i2] = new Integer(Integer.parseInt(sb.toString(), 16) & 255).byteValue();
            i = i3 + 1;
            i2++;
        }
        return bArr;
    }
}
