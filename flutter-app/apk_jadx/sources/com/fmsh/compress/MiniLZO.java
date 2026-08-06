package com.fmsh.compress;

import java.io.File;
import java.io.FileInputStream;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public final class MiniLZO implements LZOConstants {
    public static final int c0_last = 7;
    public static final int c0_literal = 3;
    public static final int c0_m3_m4_len = 5;
    public static final int c0_m3_m4_offset = 6;
    public static final int c0_match = 4;
    public static final int c0_top = 1;
    public static final int c0_try_match = 2;
    public static final int c_copy_match = 4;
    public static final int c_eof_found = 7;
    public static final int c_first_literal_run = 2;
    public static final int c_input_overrun = 8;
    public static final int c_lookbehind_overrun = 10;
    public static final int c_match = 3;
    public static final int c_match_done = 5;
    public static final int c_match_next = 6;
    public static final int c_output_overrun = 9;
    public static final int c_top_loop = 1;
    private static final boolean debug = false;

    private static final int U(byte b) {
        return b & UByte.MAX_VALUE;
    }

    public static void main(String[] strArr) {
        int i = 0;
        int iIntValue = Integer.getInteger("ZERO_FILL", 0).intValue();
        String property = System.getProperty("IFILE", "IFILE");
        try {
            File file = new File(System.getProperty("OFILE", "IFILE"));
            long length = file.length();
            int i2 = (int) length;
            byte[] bArr = new byte[i2];
            File file2 = new File(property);
            byte[] bArr2 = new byte[(int) file2.length()];
            byte[] bArr3 = new byte[(int) file2.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            FileInputStream fileInputStream2 = new FileInputStream(property);
            if (fileInputStream.read(bArr) > 0) {
                MInt mInt = new MInt();
                System.out.println("Decompressing byte.length=" + length);
                lzo1x_decompress(bArr, i2, bArr2, mInt);
                System.out.println("Got decompressed length:" + mInt.v);
                if (iIntValue > 0) {
                    System.out.println("Doing zero fill check");
                    while (i < mInt.v) {
                        if (bArr2[i] != 0) {
                            throw new AssertionError("Decompreesed values not matching to Zero @:" + i);
                        }
                        i++;
                    }
                    return;
                }
                fileInputStream2.read(bArr3);
                while (i < mInt.v) {
                    if (bArr2[i] != bArr3[i]) {
                        throw new AssertionError("Decompreesed values not matching to Zero @:" + i);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x0016. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01c6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01c5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final int _lzo1x_1_do_compress(byte[] r20, int r21, byte[] r22, com.fmsh.compress.MInt r23, int[] r24) {
        /*
            Method dump skipped, instruction units count: 608
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fmsh.compress.MiniLZO._lzo1x_1_do_compress(byte[], int, byte[], com.fmsh.compress.MInt, int[]):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0060 A[LOOP:0: B:24:0x0052->B:27:0x0060, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0062 A[EDGE_INSN: B:30:0x0062->B:28:0x0062 BREAK  A[LOOP:0: B:24:0x0052->B:27:0x0060], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final int lzo1x_1_compress(byte[] r5, int r6, byte[] r7, com.fmsh.compress.MInt r8, int[] r9) {
        /*
            r0 = 0
            r1 = 13
            if (r6 > r1) goto L8
            r9 = r6
            r1 = r0
            goto Lf
        L8:
            int r9 = _lzo1x_1_do_compress(r5, r6, r7, r8, r9)
            int r1 = r8.v
            int r1 = r1 + r0
        Lf:
            if (r9 <= 0) goto L62
            int r6 = r6 + r0
            int r6 = r6 - r9
            if (r1 != 0) goto L22
            r2 = 238(0xee, float:3.34E-43)
            if (r9 > r2) goto L22
            int r2 = r1 + 1
            int r3 = r9 + 17
            byte r3 = (byte) r3
            r7[r1] = r3
        L20:
            r1 = r2
            goto L52
        L22:
            r2 = 3
            if (r9 > r2) goto L2f
            int r2 = r1 + (-2)
            r3 = r7[r2]
            byte r4 = (byte) r9
            r3 = r3 | r4
            byte r3 = (byte) r3
            r7[r2] = r3
            goto L52
        L2f:
            r2 = 18
            if (r9 > r2) goto L3b
            int r2 = r1 + 1
            int r3 = r9 + (-3)
            byte r3 = (byte) r3
            r7[r1] = r3
            goto L20
        L3b:
            int r2 = r9 + (-18)
            int r3 = r1 + 1
            r7[r1] = r0
        L41:
            r1 = 255(0xff, float:3.57E-43)
            if (r2 <= r1) goto L4d
            int r2 = r2 + (-255)
            int r1 = r3 + 1
            r7[r3] = r0
            r3 = r1
            goto L41
        L4d:
            int r1 = r3 + 1
            byte r2 = (byte) r2
            r7[r3] = r2
        L52:
            int r2 = r1 + 1
            int r3 = r6 + 1
            r6 = r5[r6]
            r7[r1] = r6
            int r9 = r9 + (-1)
            r1 = r2
            if (r9 > 0) goto L60
            goto L62
        L60:
            r6 = r3
            goto L52
        L62:
            int r5 = r1 + 1
            r6 = 17
            r7[r1] = r6
            int r6 = r5 + 1
            r7[r5] = r0
            int r5 = r6 + 1
            r7[r6] = r0
            int r5 = r5 - r0
            r8.v = r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fmsh.compress.MiniLZO.lzo1x_1_compress(byte[], int, byte[], com.fmsh.compress.MInt, int[]):int");
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:14:0x0040. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0286 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x028c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x019c A[PHI: r3 r11 r12 r13 r14 r15
  0x019c: PHI (r3v21 int) = (r3v3 int), (r3v24 int) binds: [B:141:0x019c, B:85:0x0197] A[DONT_GENERATE, DONT_INLINE]
  0x019c: PHI (r11v14 int) = (r11v2 int), (r11v15 int) binds: [B:141:0x019c, B:85:0x0197] A[DONT_GENERATE, DONT_INLINE]
  0x019c: PHI (r12v10 int) = (r12v2 int), (r12v14 int) binds: [B:141:0x019c, B:85:0x0197] A[DONT_GENERATE, DONT_INLINE]
  0x019c: PHI (r13v28 int) = (r13v2 int), (r13v32 int) binds: [B:141:0x019c, B:85:0x0197] A[DONT_GENERATE, DONT_INLINE]
  0x019c: PHI (r14v14 int) = (r14v2 int), (r14v15 int) binds: [B:141:0x019c, B:85:0x0197] A[DONT_GENERATE, DONT_INLINE]
  0x019c: PHI (r15v32 int) = (r15v2 int), (r15v33 int) binds: [B:141:0x019c, B:85:0x0197] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01bb A[PHI: r2 r10
  0x01bb: PHI (r2v37 int) = (r2v36 int), (r2v39 int) binds: [B:88:0x01a4, B:91:0x01b0] A[DONT_GENERATE, DONT_INLINE]
  0x01bb: PHI (r10v39 int) = (r10v38 int), (r10v44 int) binds: [B:88:0x01a4, B:91:0x01b0] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final int lzo1x_decompress(byte[] r19, int r20, byte[] r21, com.fmsh.compress.MInt r22) {
        /*
            Method dump skipped, instruction units count: 722
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fmsh.compress.MiniLZO.lzo1x_decompress(byte[], int, byte[], com.fmsh.compress.MInt):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:200:0x01b3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x01c2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x02d9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x01bc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x02db A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x01b3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02e0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01b8 A[PHI: r4 r13 r14 r18
  0x01b8: PHI (r4v25 int) = (r4v3 int), (r4v29 int) binds: [B:229:0x01b8, B:96:0x01b1] A[DONT_GENERATE, DONT_INLINE]
  0x01b8: PHI (r13v12 int) = (r13v2 int), (r13v17 int) binds: [B:229:0x01b8, B:96:0x01b1] A[DONT_GENERATE, DONT_INLINE]
  0x01b8: PHI (r14v28 int) = (r14v2 int), (r14v30 int) binds: [B:229:0x01b8, B:96:0x01b1] A[DONT_GENERATE, DONT_INLINE]
  0x01b8: PHI (r18v18 int) = (r18v1 int), (r18v19 int) binds: [B:229:0x01b8, B:96:0x01b1] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final int lzo1x_decompress_safe(byte[] r21, int r22, byte[] r23, com.fmsh.compress.MInt r24) {
        /*
            Method dump skipped, instruction units count: 862
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fmsh.compress.MiniLZO.lzo1x_decompress_safe(byte[], int, byte[], com.fmsh.compress.MInt):int");
    }
}
