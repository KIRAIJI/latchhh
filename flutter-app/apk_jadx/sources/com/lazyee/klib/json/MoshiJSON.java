package com.lazyee.klib.json;

import androidx.exifinterface.media.ExifInterface;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import java.lang.reflect.Type;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MoshiJSON.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J-\u0010\f\u001a\u0004\u0018\u0001H\r\"\u0004\b\u0000\u0010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\r0\u0011H\u0016¢\u0006\u0002\u0010\u0012J\u001f\u0010\u0013\u001a\u0004\u0018\u00010\u000f\"\u0004\b\u0000\u0010\r2\b\u0010\u0014\u001a\u0004\u0018\u0001H\rH\u0016¢\u0006\u0002\u0010\u0015R\u0014\u0010\u0004\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R#\u0010\u0007\u001a\n \b*\u0004\u0018\u00010\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\t\u0010\u0006¨\u0006\u0016"}, d2 = {"Lcom/lazyee/klib/json/MoshiJSON;", "Lcom/lazyee/klib/json/JSON;", "Lcom/squareup/moshi/Moshi;", "()V", "instance", "getInstance", "()Lcom/squareup/moshi/Moshi;", "moshi", "kotlin.jvm.PlatformType", "getMoshi", "moshi$delegate", "Lkotlin/Lazy;", "fromJson", ExifInterface.GPS_DIRECTION_TRUE, "json", "", "clazz", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "toJson", "entity", "(Ljava/lang/Object;)Ljava/lang/String;", "library_release"}, k = 1, mv = {1, 4, 2})
public final class MoshiJSON implements JSON<Moshi> {
    public static final MoshiJSON INSTANCE;
    private static final Moshi instance;

    /* JADX INFO: renamed from: moshi$delegate, reason: from kotlin metadata */
    private static final Lazy moshi;

    private final Moshi getMoshi() {
        return (Moshi) moshi.getValue();
    }

    static {
        MoshiJSON moshiJSON = new MoshiJSON();
        INSTANCE = moshiJSON;
        moshi = LazyKt.lazy(new Function0<Moshi>() { // from class: com.lazyee.klib.json.MoshiJSON$moshi$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Moshi invoke() {
                return new Moshi.Builder().add((JsonAdapter.Factory) new KotlinJsonAdapterFactory()).add(MoshiArrayListJsonAdapter.Companion.getFACTORY()).build();
            }
        });
        Moshi moshi2 = moshiJSON.getMoshi();
        Intrinsics.checkNotNullExpressionValue(moshi2, "moshi");
        instance = moshi2;
    }

    private MoshiJSON() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.lazyee.klib.json.JSON
    public Moshi getInstance() {
        return instance;
    }

    @Override // com.lazyee.klib.json.JSON
    public <T> String toJson(T entity) {
        if (entity != null) {
            return getInstance().adapter((Type) entity.getClass()).toJson(entity);
        }
        return null;
    }

    @Override // com.lazyee.klib.json.JSON
    public <T> T fromJson(String json, Class<T> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        if (json != null) {
            return getInstance().adapter((Class) clazz).fromJson(json);
        }
        return null;
    }
}
