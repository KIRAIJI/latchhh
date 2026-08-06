package com.lazyee.klib.common;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.lazyee.klib.util.LogUtils;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SP.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0012J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0016J#\u0010\u0017\u001a\u0004\u0018\u0001H\u0018\"\u0004\b\u0000\u0010\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001aH\u0002¢\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0005J\u001f\u0010\u001c\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u0007¢\u0006\u0002\u0010\u001dJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u001fJ\u0018\u0010 \u001a\u00020!2\u0006\u0010\u0013\u001a\u00020\u00052\b\u0010\"\u001a\u0004\u0018\u00010\u0001J\u001e\u0010#\u001a\u00020!2\u0006\u0010\u0013\u001a\u00020\u00052\u000e\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010%J\u000e\u0010&\u001a\u00020!2\u0006\u0010\u0013\u001a\u00020\u0005J\u0010\u0010'\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u001a\u0010'\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0013\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005J\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010%2\u0006\u0010\u0013\u001a\u00020\u0005J&\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010%2\u0006\u0010\u0013\u001a\u00020\u00052\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010%R\u000e\u0010\t\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R#\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006)"}, d2 = {"Lcom/lazyee/klib/common/SP;", "", "context", "Landroid/content/Context;", "name", "", "mode", "", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Integer;)V", "defaultName", "sharedPreferences", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "sharedPreferences$delegate", "Lkotlin/Lazy;", TypedValues.Custom.S_BOOLEAN, "", "key", "defaultValue", TypedValues.Custom.S_FLOAT, "", "get", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "int", "(Ljava/lang/String;I)Ljava/lang/Integer;", "long", "", "put", "", "value", "putStringSet", "values", "", "removeByKey", TypedValues.Custom.S_STRING, "stringSet", "library_release"}, k = 1, mv = {1, 4, 2})
public final class SP {
    private final String defaultName;

    /* JADX INFO: renamed from: sharedPreferences$delegate, reason: from kotlin metadata */
    private final Lazy sharedPreferences;

    /* JADX INFO: Access modifiers changed from: private */
    public final SharedPreferences getSharedPreferences() {
        return (SharedPreferences) this.sharedPreferences.getValue();
    }

    public SP(final Context context, final String str, final Integer num) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.defaultName = "sharedPreferences";
        this.sharedPreferences = LazyKt.lazy(new Function0<SharedPreferences>() { // from class: com.lazyee.klib.common.SP$sharedPreferences$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SharedPreferences invoke() {
                Context context2 = context;
                String str2 = str;
                if (str2 == null) {
                    str2 = this.this$0.defaultName;
                }
                Integer num2 = num;
                return context2.getSharedPreferences(str2, num2 != null ? num2.intValue() : 0);
            }
        });
    }

    public /* synthetic */ SP(Context context, String str, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? (String) null : str, (i & 4) != 0 ? (Integer) null : num);
    }

    public final void put(String key, Object value) {
        Intrinsics.checkNotNullParameter(key, "key");
        LogUtils.INSTANCE.e("[SP]", "key:" + key + ",value:" + value);
        if (value == null) {
            removeByKey(key);
            return;
        }
        SharedPreferences.Editor editorEdit = getSharedPreferences().edit();
        if (value instanceof String) {
            editorEdit.putString(key, (String) value).apply();
            return;
        }
        if (value instanceof Boolean) {
            editorEdit.putBoolean(key, ((Boolean) value).booleanValue()).apply();
            return;
        }
        if (value instanceof Integer) {
            editorEdit.putInt(key, ((Number) value).intValue()).apply();
        } else if (value instanceof Long) {
            editorEdit.putLong(key, ((Number) value).longValue()).apply();
        } else if (value instanceof Float) {
            editorEdit.putFloat(key, ((Number) value).floatValue()).apply();
        }
    }

    public final void putStringSet(String key, Set<String> values) {
        Intrinsics.checkNotNullParameter(key, "key");
        getSharedPreferences().edit().putStringSet(key, values).apply();
    }

    public final void removeByKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        getSharedPreferences().edit().remove(key).apply();
    }

    public final String string(final String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (String) get(new Function0<String>() { // from class: com.lazyee.klib.common.SP.string.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return SP.this.getSharedPreferences().getString(key, null);
            }
        });
    }

    public final String string(final String key, final String defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (String) get(new Function0<String>() { // from class: com.lazyee.klib.common.SP.string.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return SP.this.getSharedPreferences().getString(key, defaultValue);
            }
        });
    }

    public final Set<String> stringSet(final String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (Set) get(new Function0<Set<String>>() { // from class: com.lazyee.klib.common.SP.stringSet.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<String> invoke() {
                return SP.this.getSharedPreferences().getStringSet(key, null);
            }
        });
    }

    public final Set<String> stringSet(final String key, final Set<String> defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (Set) get(new Function0<Set<String>>() { // from class: com.lazyee.klib.common.SP.stringSet.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<String> invoke() {
                return SP.this.getSharedPreferences().getStringSet(key, defaultValue);
            }
        });
    }

    /* JADX INFO: renamed from: int, reason: not valid java name */
    public final int m95int(final String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Integer num = (Integer) get(new Function0<Integer>() { // from class: com.lazyee.klib.common.SP.int.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Integer invoke() {
                return Integer.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final int invoke2() {
                return SP.this.getSharedPreferences().getInt(key, 0);
            }
        });
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static /* synthetic */ Integer int$default(SP sp, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return sp.m96int(str, i);
    }

    /* JADX INFO: renamed from: int, reason: not valid java name */
    public final Integer m96int(final String key, final int defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (Integer) get(new Function0<Integer>() { // from class: com.lazyee.klib.common.SP.int.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Integer invoke() {
                return Integer.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final int invoke2() {
                return SP.this.getSharedPreferences().getInt(key, defaultValue);
            }
        });
    }

    /* JADX INFO: renamed from: boolean, reason: not valid java name */
    public final boolean m91boolean(final String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean bool = (Boolean) get(new Function0<Boolean>() { // from class: com.lazyee.klib.common.SP.boolean.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Boolean invoke() {
                return Boolean.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2() {
                return SP.this.getSharedPreferences().getBoolean(key, false);
            }
        });
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    /* JADX INFO: renamed from: boolean, reason: not valid java name */
    public final boolean m92boolean(final String key, final boolean defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean bool = (Boolean) get(new Function0<Boolean>() { // from class: com.lazyee.klib.common.SP.boolean.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Boolean invoke() {
                return Boolean.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2() {
                return SP.this.getSharedPreferences().getBoolean(key, defaultValue);
            }
        });
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    /* JADX INFO: renamed from: float, reason: not valid java name */
    public final float m93float(final String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Float f = (Float) get(new Function0<Float>() { // from class: com.lazyee.klib.common.SP.float.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Float invoke() {
                return Float.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final float invoke2() {
                return SP.this.getSharedPreferences().getFloat(key, 0.0f);
            }
        });
        if (f != null) {
            return f.floatValue();
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: float, reason: not valid java name */
    public final float m94float(final String key, final float defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Float f = (Float) get(new Function0<Float>() { // from class: com.lazyee.klib.common.SP.float.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Float invoke() {
                return Float.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final float invoke2() {
                return SP.this.getSharedPreferences().getFloat(key, defaultValue);
            }
        });
        if (f != null) {
            return f.floatValue();
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: long, reason: not valid java name */
    public final long m97long(final String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Long l = (Long) get(new Function0<Long>() { // from class: com.lazyee.klib.common.SP.long.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Long invoke() {
                return Long.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final long invoke2() {
                return SP.this.getSharedPreferences().getLong(key, 0L);
            }
        });
        if (l != null) {
            return l.longValue();
        }
        return 0L;
    }

    /* JADX INFO: renamed from: long, reason: not valid java name */
    public final long m98long(final String key, final long defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Long l = (Long) get(new Function0<Long>() { // from class: com.lazyee.klib.common.SP.long.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Long invoke() {
                return Long.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final long invoke2() {
                return SP.this.getSharedPreferences().getLong(key, defaultValue);
            }
        });
        if (l != null) {
            return l.longValue();
        }
        return 0L;
    }

    private final <T> T get(Function0<? extends T> block) {
        try {
            return block.invoke();
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
}
