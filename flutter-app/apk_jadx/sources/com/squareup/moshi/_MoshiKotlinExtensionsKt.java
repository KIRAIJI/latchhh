package com.squareup.moshi;

import androidx.exifinterface.media.ExifInterface;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.internal.NonNullJsonAdapter;
import com.squareup.moshi.internal.NullSafeJsonAdapter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KType;
import kotlin.reflect.TypesJVMKt;

/* JADX INFO: compiled from: -MoshiKotlinExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0087\b\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a#\u0010\u0006\u001a\u00020\u0007\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00072\f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0087\b¨\u0006\b"}, d2 = {"adapter", "Lcom/squareup/moshi/JsonAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/squareup/moshi/Moshi;", "ktype", "Lkotlin/reflect/KType;", "addAdapter", "Lcom/squareup/moshi/Moshi$Builder;", "moshi"}, k = 2, mv = {1, 4, 2})
public final class _MoshiKotlinExtensionsKt {
    public static final /* synthetic */ <T> JsonAdapter<T> adapter(Moshi adapter) {
        Intrinsics.checkNotNullParameter(adapter, "$this$adapter");
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        return adapter(adapter, null);
    }

    public static final /* synthetic */ <T> Moshi.Builder addAdapter(Moshi.Builder addAdapter, JsonAdapter<T> adapter) {
        Intrinsics.checkNotNullParameter(addAdapter, "$this$addAdapter");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        Moshi.Builder builderAdd = addAdapter.add(TypesJVMKt.getJavaType((KType) null), adapter);
        Intrinsics.checkNotNullExpressionValue(builderAdd, "add(typeOf<T>().javaType, adapter)");
        return builderAdd;
    }

    public static final <T> JsonAdapter<T> adapter(Moshi adapter, KType ktype) {
        Intrinsics.checkNotNullParameter(adapter, "$this$adapter");
        Intrinsics.checkNotNullParameter(ktype, "ktype");
        JsonAdapter<T> jsonAdapterAdapter = adapter.adapter(TypesJVMKt.getJavaType(ktype));
        if (!(jsonAdapterAdapter instanceof NullSafeJsonAdapter) && !(jsonAdapterAdapter instanceof NonNullJsonAdapter)) {
            if (ktype.isMarkedNullable()) {
                jsonAdapterAdapter = jsonAdapterAdapter.nullSafe();
            } else {
                jsonAdapterAdapter = jsonAdapterAdapter.nonNull();
            }
            Intrinsics.checkNotNullExpressionValue(jsonAdapterAdapter, "if (ktype.isMarkedNullab…    adapter.nonNull()\n  }");
        }
        return jsonAdapterAdapter;
    }
}
