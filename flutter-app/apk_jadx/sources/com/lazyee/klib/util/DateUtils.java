package com.lazyee.klib.util;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DateUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u0004J\u0016\u0010\u0006\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0004J$\u0010\u000b\u001a\u0004\u0018\u00010\b2\u0006\u0010\f\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\r\u001a\u00020\u000eJ)\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/lazyee/klib/util/DateUtils;", "", "()V", "yyyyMMdd", "", "yyyyMMddHHmmss", "format", "date", "Ljava/util/Date;", "timeMillis", "", "stringToDate", TypedValues.Custom.S_STRING, "locale", "Ljava/util/Locale;", "stringToTimeMillis", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Locale;)Ljava/lang/Long;", "library_release"}, k = 1, mv = {1, 4, 2})
public final class DateUtils {
    public static final DateUtils INSTANCE = new DateUtils();
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    private DateUtils() {
    }

    public final String format(long timeMillis, String format) {
        Intrinsics.checkNotNullParameter(format, "format");
        return format(new Date(timeMillis), format);
    }

    public final String format(Date date, String format) {
        Intrinsics.checkNotNullParameter(date, "date");
        Intrinsics.checkNotNullParameter(format, "format");
        String str = new SimpleDateFormat(format, Locale.getDefault()).format(date);
        Intrinsics.checkNotNullExpressionValue(str, "simpleFormatter.format(date)");
        return str;
    }

    public static /* synthetic */ Date stringToDate$default(DateUtils dateUtils, String str, String str2, Locale locale, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = yyyyMMddHHmmss;
        }
        if ((i & 4) != 0) {
            locale = Locale.CHINA;
            Intrinsics.checkNotNullExpressionValue(locale, "Locale.CHINA");
        }
        return dateUtils.stringToDate(str, str2, locale);
    }

    public final Date stringToDate(String string, String format, Locale locale) {
        Intrinsics.checkNotNullParameter(string, "string");
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(locale, "locale");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return new SimpleDateFormat(format, locale).parse(string);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static /* synthetic */ Long stringToTimeMillis$default(DateUtils dateUtils, String str, String str2, Locale locale, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = yyyyMMddHHmmss;
        }
        if ((i & 4) != 0) {
            locale = Locale.CHINA;
            Intrinsics.checkNotNullExpressionValue(locale, "Locale.CHINA");
        }
        return dateUtils.stringToTimeMillis(str, str2, locale);
    }

    public final Long stringToTimeMillis(String string, String format, Locale locale) {
        Date dateStringToDate;
        Intrinsics.checkNotNullParameter(string, "string");
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(locale, "locale");
        if (TextUtils.isEmpty(string) || (dateStringToDate = stringToDate(string, format, locale)) == null) {
            return null;
        }
        return Long.valueOf(dateStringToDate.getTime());
    }
}
