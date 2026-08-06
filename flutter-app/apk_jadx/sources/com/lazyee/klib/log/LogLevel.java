package com.lazyee.klib.log;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: LogLevel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/lazyee/klib/log/LogLevel;", "", "name", "", "(Ljava/lang/String;ILjava/lang/String;)V", ExifInterface.LONGITUDE_WEST, "I", ExifInterface.LONGITUDE_EAST, ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "D", "library_release"}, k = 1, mv = {1, 4, 2})
public enum LogLevel {
    W("Warring"),
    I("Info"),
    E("Error"),
    V("Verbose"),
    D("Debug");

    LogLevel(String str) {
    }
}
