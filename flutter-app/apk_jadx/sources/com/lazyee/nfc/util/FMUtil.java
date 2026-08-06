package com.lazyee.nfc.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Objects;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class FMUtil {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static byte[] concat(byte[]... bArr) {
        int length = 0;
        for (byte[] bArr2 : bArr) {
            length += bArr2.length;
        }
        byte[] bArr3 = new byte[length];
        int length2 = 0;
        for (byte[] bArr4 : bArr) {
            System.arraycopy(bArr4, 0, bArr3, length2, bArr4.length);
            length2 += bArr4.length;
        }
        return bArr3;
    }

    public static byte[] intToBytes(int i, int i2) {
        return intToBytes(i, i2, true);
    }

    public static byte[] intToBytes(int i, int i2, boolean z) {
        if (i2 < 1) {
            return null;
        }
        byte[] bArr = new byte[i2];
        if (z) {
            for (int i3 = i2 - 1; i3 > -1; i3--) {
                bArr[i3] = Integer.valueOf(i & 255).byteValue();
                i >>= 8;
            }
        } else {
            for (int i4 = 0; i4 < i2; i4++) {
                bArr[i4] = Integer.valueOf(i & 255).byteValue();
                i >>= 8;
            }
        }
        return bArr;
    }

    public static long byteToInt(byte[] bArr) {
        long length = 0;
        for (int i = 0; i < bArr.length; i++) {
            length |= (long) ((bArr[i] & UByte.MAX_VALUE) << (((bArr.length - 1) - i) * 8));
        }
        return length;
    }

    public static byte[] longToBytes(long j) {
        return longToBytes(j, true);
    }

    public static byte[] longToBytes(long j, boolean z) {
        return longToBytes(j, z, 8);
    }

    public static byte[] longToBytes(long j, int i) {
        return longToBytes(j, true, i);
    }

    public static byte[] longToBytes(long j, boolean z, int i) {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            if (z) {
                bArr[(i - 1) - i2] = (byte) (255 & (j >> (i2 * 8)));
            } else {
                bArr[i2] = (byte) (255 & (j >> (i2 * 8)));
            }
        }
        return bArr;
    }

    public static byte[] byteConvert32Bytes(BigInteger bigInteger) {
        if (bigInteger == null) {
            return null;
        }
        if (bigInteger.toByteArray().length == 33) {
            byte[] bArr = new byte[32];
            System.arraycopy(bigInteger.toByteArray(), 1, bArr, 0, 32);
            return bArr;
        }
        if (bigInteger.toByteArray().length == 32) {
            return bigInteger.toByteArray();
        }
        byte[] bArr2 = new byte[32];
        for (int i = 0; i < 32 - bigInteger.toByteArray().length; i++) {
            bArr2[i] = 0;
        }
        System.arraycopy(bigInteger.toByteArray(), 0, bArr2, 32 - bigInteger.toByteArray().length, bigInteger.toByteArray().length);
        return bArr2;
    }

    public static BigInteger byteConvertInteger(byte[] bArr) {
        if (bArr[0] < 0) {
            byte[] bArr2 = new byte[bArr.length + 1];
            bArr2[0] = 0;
            System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
            return new BigInteger(bArr2);
        }
        return new BigInteger(bArr);
    }

    public static String getHexString(byte[] bArr) {
        return getHexString(bArr, true);
    }

    public static String getHexString(byte[] bArr, boolean z) {
        String str = "";
        for (byte b : bArr) {
            str = str + Integer.toString((b & UByte.MAX_VALUE) + 256, 16).substring(1);
        }
        return z ? str.toUpperCase() : str;
    }

    public static void printHexString(byte[] bArr) {
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            System.out.print("0x" + hexString.toUpperCase() + ",");
        }
        System.out.println("");
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, true);
    }

    public static char[] encodeHex(byte[] bArr, boolean z) {
        return encodeHex(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] bArr, char[] cArr) {
        int length = bArr.length;
        char[] cArr2 = new char[length << 1];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return cArr2;
    }

    public static String encodeHexString(byte[] bArr) {
        return encodeHexString(bArr, true);
    }

    public static String encodeHexString(byte[] bArr, boolean z) {
        return encodeHexString(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static String encodeHexString(byte[] bArr, char[] cArr) {
        return new String(encodeHex(bArr, cArr));
    }

    public static byte[] decodeHex(char[] cArr) {
        int length = cArr.length;
        if ((length & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] bArr = new byte[length >> 1];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int digit = toDigit(cArr[i], i) << 4;
            int i3 = i + 1;
            int digit2 = digit | toDigit(cArr[i3], i3);
            i = i3 + 1;
            bArr[i2] = (byte) (digit2 & 255);
            i2++;
        }
        return bArr;
    }

    protected static int toDigit(char c, int i) {
        int iDigit = Character.digit(c, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new RuntimeException("Illegal hexadecimal character " + c + " at index " + i);
    }

    public static String StringToAsciiString(String str) {
        int length = str.length();
        String str2 = "";
        for (int i = 0; i < length; i++) {
            str2 = str2 + Integer.toHexString(str.charAt(i));
        }
        return str2;
    }

    public static String hexStringToString(String str, int i) {
        int length = str.length() / i;
        String str2 = "";
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 * i;
            i2++;
            str2 = str2 + ((char) hexStringToAlgorism(str.substring(i3, i2 * i)));
        }
        return str2;
    }

    public static int hexStringToAlgorism(String str) {
        String upperCase = str.toUpperCase();
        int iPow = 0;
        for (int length = upperCase.length(); length > 0; length--) {
            char cCharAt = upperCase.charAt(length - 1);
            iPow = (int) (((double) iPow) + (Math.pow(16.0d, r0 - length) * ((double) ((cCharAt < '0' || cCharAt > '9') ? cCharAt - '7' : cCharAt - '0'))));
        }
        return iPow;
    }

    public static String binaryString2hexString(String str) {
        if (str != null && !str.equals("")) {
            int length = str.length() % 8;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i += 4) {
            int i2 = 0;
            for (int i3 = 0; i3 < 4; i3++) {
                int i4 = i + i3;
                i2 += Integer.parseInt(str.substring(i4, i4 + 1)) << ((4 - i3) - 1);
            }
            stringBuffer.append(Integer.toHexString(i2));
        }
        return stringBuffer.toString();
    }

    public static String hexStringToBinary(String str) {
        String upperCase = str.toUpperCase();
        int length = upperCase.length();
        String str2 = "";
        for (int i = 0; i < length; i++) {
            char cCharAt = upperCase.charAt(i);
            switch (cCharAt) {
                case '0':
                    str2 = str2 + "0000";
                    break;
                case '1':
                    str2 = str2 + "0001";
                    break;
                case '2':
                    str2 = str2 + "0010";
                    break;
                case '3':
                    str2 = str2 + "0011";
                    break;
                case '4':
                    str2 = str2 + "0100";
                    break;
                case '5':
                    str2 = str2 + "0101";
                    break;
                case '6':
                    str2 = str2 + "0110";
                    break;
                case '7':
                    str2 = str2 + "0111";
                    break;
                case '8':
                    str2 = str2 + "1000";
                    break;
                case '9':
                    str2 = str2 + "1001";
                    break;
                default:
                    switch (cCharAt) {
                        case 'A':
                            str2 = str2 + "1010";
                            break;
                        case 'B':
                            str2 = str2 + "1011";
                            break;
                        case 'C':
                            str2 = str2 + "1100";
                            break;
                        case 'D':
                            str2 = str2 + "1101";
                            break;
                        case 'E':
                            str2 = str2 + "1110";
                            break;
                        case 'F':
                            str2 = str2 + "1111";
                            break;
                    }
                    break;
            }
        }
        return str2;
    }

    public static String AsciiStringToString(String str) {
        int length = str.length() / 2;
        String str2 = "";
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            str2 = str2 + String.valueOf((char) hexStringToAlgorism(str.substring(i2, i2 + 2)));
        }
        return str2;
    }

    public static String algorismToHexString(int i, int i2) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() % 2 == 1) {
            hexString = "0" + hexString;
        }
        return patchHexString(hexString.toUpperCase(), i2);
    }

    public static String byteToString(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            str = str + ((char) b);
        }
        return str;
    }

    public static int binaryToAlgorism(String str) {
        int iPow = 0;
        for (int length = str.length(); length > 0; length--) {
            iPow = (int) (((double) iPow) + (Math.pow(2.0d, r0 - length) * ((double) (str.charAt(length - 1) - '0'))));
        }
        return iPow;
    }

    public static String algorismToHEXString(int i) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() % 2 == 1) {
            hexString = "0" + hexString;
        }
        return hexString.toUpperCase();
    }

    public static String patchHexString(String str, int i) {
        String str2 = "";
        for (int i2 = 0; i2 < i - str.length(); i2++) {
            str2 = "0" + str2;
        }
        return (str2 + str).substring(0, i);
    }

    public static int parseToInt(String str, int i, int i2) {
        try {
            return Integer.parseInt(str, i2);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public static int parseToInt(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i;
        }
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

    public static byte[] subByteArray(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static byte[] copyOfRange(byte[] bArr, int i, int i2) {
        Objects.requireNonNull(bArr, " original Arrays is null");
        int i3 = i2 - i;
        if (i3 <= 0) {
            throw new IllegalArgumentException(" from[" + i + "]>to[" + i2 + "]");
        }
        if (bArr.length >= i2 && bArr.length >= i) {
            byte[] bArr2 = new byte[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                bArr2[i4] = bArr[i + i4];
            }
            return bArr2;
        }
        throw new IllegalArgumentException(" ");
    }

    public static byte[] Sm3digest(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SM3", "DoubleCA-JCE");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException | NoSuchProviderException unused) {
            return null;
        }
    }

    public static String padding16Byte(String str) {
        byte[] bArrHexStringToBytes = hexStringToBytes(StringToAsciiString(str));
        byte[] bArr = new byte[16];
        for (int i = 0; i < 16; i++) {
            bArr[i] = -1;
        }
        if (bArrHexStringToBytes.length < 16) {
            System.arraycopy(bArrHexStringToBytes, 0, bArr, 0, bArrHexStringToBytes.length);
        }
        return byteToHex(bArr);
    }

    public static String bytesToHexString(byte[] bArr, String str) {
        StringBuilder sb = new StringBuilder();
        String strSubstring = "0";
        if (bArr != null && bArr.length > 0) {
            for (byte b : bArr) {
                int i = b & UByte.MAX_VALUE;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
                if (!"0".equals(str)) {
                    sb.append(str);
                }
            }
            strSubstring = sb.substring(0, sb.length() - 1);
        }
        return strSubstring.toUpperCase();
    }

    public static String getTagId(byte[] bArr) {
        if (bArr.length == 8) {
            byte[] bArr2 = new byte[bArr.length];
            for (int i = 0; i < bArr.length; i++) {
                bArr2[i] = bArr[(bArr.length - 1) - i];
            }
            return bytesToHexString(bArr2, ":").toUpperCase();
        }
        return bytesToHexString(bArr, ":").toUpperCase();
    }

    public static String bytesToMatrixHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null || bArr.length <= 0) {
            return "0";
        }
        byte b = 0;
        for (int i = 0; i < bArr.length; i++) {
            if (i % 6 == 0) {
                sb.append(String.format("[ %02x ] ", Byte.valueOf(b)).toUpperCase());
                b = (byte) (b + 6);
            }
            int i2 = bArr[i] & UByte.MAX_VALUE;
            if (i2 < 16) {
                sb.append("0x0" + Integer.toHexString(i2).toUpperCase());
            } else {
                sb.append("0x" + Integer.toHexString(i2).toUpperCase());
            }
            sb.append("  ");
            if (i != 0 && (i + 1) % 6 == 0) {
                sb.append("\n");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static byte[] reverseByte(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[(bArr.length - 1) - i];
        }
        return bArr2;
    }
}
