package com.fmsh.compress;

import java.util.Arrays;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class Min1Comp {
    private static final int IN_LEN = 1048576;
    private static final int OUT_LEN = 2097152;

    private static void clearDict(int[] iArr) {
        Arrays.fill(iArr, 0);
    }

    private static String R(long j, long j2, long j3) {
        long j4 = j + 1;
        return ", millis:" + j4 + ", MB/sec:" + (((1000 * j2) / j4) / 1000000) + ", ratio:" + (((j3 + 1) * 100) / j2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x032e, code lost:
    
        throw new java.lang.AssertionError(r10 + r5 + r0 + ")  Decompreesed values not matching to Zero @:" + r4);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v36 */
    /* JADX WARN: Type inference failed for: r1v37 */
    /* JADX WARN: Type inference failed for: r1v38 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void main(java.lang.String[] r35) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 894
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fmsh.compress.Min1Comp.main(java.lang.String[]):void");
    }

    private static void fillPartillyRandom(int i, byte[] bArr, Random random, boolean z) {
        int i2 = 0;
        do {
            int iNextInt = z ? random.nextInt(10) : 1;
            byte bNextInt = (byte) random.nextInt(i);
            int i3 = 0;
            while (i3 < iNextInt && i2 < bArr.length) {
                bArr[i2] = bNextInt;
                i3++;
                i2++;
            }
        } while (i2 < bArr.length);
    }
}
