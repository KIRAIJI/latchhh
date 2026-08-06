package com.lazyee.nfc.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class EncryUtils {
    private static Key generateKey(String str) throws Exception {
        return SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(FMUtil.hexToByte(str)));
    }

    public static String encryptDES(String str, String str2, String str3) {
        if (str2 == null || str2.length() < 8) {
            throw new RuntimeException("加密失败，key不能小于8位");
        }
        LogUtil.d(str);
        LogUtil.d(str2);
        LogUtil.d(str3);
        if (str3 == null) {
            return null;
        }
        try {
            Key keyGenerateKey = generateKey(str2);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
            cipher.init(1, keyGenerateKey, new IvParameterSpec(FMUtil.hexToByte(str)));
            return FMUtil.byteToHex(cipher.doFinal(FMUtil.hexToByte(str3)));
        } catch (Exception e) {
            e.printStackTrace();
            return str3;
        }
    }

    public static String decryptDES(String str, String str2, String str3) {
        if (str2 == null || str2.length() < 8) {
            throw new RuntimeException("加密失败，key不能小于8位");
        }
        if (str3 == null) {
            return null;
        }
        try {
            Key keyGenerateKey = generateKey(str2);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(2, keyGenerateKey, new IvParameterSpec(str.getBytes(com.bumptech.glide.load.Key.STRING_CHARSET_NAME)));
            return FMUtil.byteToHex(cipher.doFinal(str3.getBytes(com.bumptech.glide.load.Key.STRING_CHARSET_NAME)));
        } catch (Exception e) {
            e.printStackTrace();
            return str3;
        }
    }
}
