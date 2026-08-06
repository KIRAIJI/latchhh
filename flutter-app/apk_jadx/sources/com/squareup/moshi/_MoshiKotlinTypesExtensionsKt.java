package com.squareup.moshi;

import androidx.exifinterface.media.ExifInterface;
import com.squareup.moshi.internal.Util;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlin.reflect.TypesJVMKt;

/* JADX INFO: compiled from: -MoshiKotlinTypesExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u001b\n\u0000\u001a\u0011\u0010\u0005\u001a\u00020\u0006\"\u0006\b\u0000\u0010\u0007\u0018\u0001H\u0087\b\u001a\u0011\u0010\b\u001a\u00020\u0006\"\u0006\b\u0000\u0010\u0007\u0018\u0001H\u0087\b\u001a\n\u0010\t\u001a\u00020\n*\u00020\u0002\u001a\u000e\u0010\t\u001a\u00020\n*\u0006\u0012\u0002\b\u00030\u000b\u001a\f\u0010\t\u001a\u00020\n*\u00020\fH\u0007\u001a'\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\u000f*\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0086\b\"\u0019\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0010"}, d2 = {"rawType", "Ljava/lang/Class;", "Ljava/lang/reflect/Type;", "getRawType", "(Ljava/lang/reflect/Type;)Ljava/lang/Class;", "subtypeOf", "Ljava/lang/reflect/WildcardType;", ExifInterface.GPS_DIRECTION_TRUE, "supertypeOf", "asArrayType", "Ljava/lang/reflect/GenericArrayType;", "Lkotlin/reflect/KClass;", "Lkotlin/reflect/KType;", "nextAnnotations", "", "", "moshi"}, k = 2, mv = {1, 4, 2})
public final class _MoshiKotlinTypesExtensionsKt {
    public static final Class<?> getRawType(Type rawType) {
        Intrinsics.checkNotNullParameter(rawType, "$this$rawType");
        Class<?> rawType2 = Types.getRawType(rawType);
        Intrinsics.checkNotNullExpressionValue(rawType2, "Types.getRawType(this)");
        return rawType2;
    }

    public static final /* synthetic */ <T extends Annotation> Set<Annotation> nextAnnotations(Set<? extends Annotation> nextAnnotations) {
        Intrinsics.checkNotNullParameter(nextAnnotations, "$this$nextAnnotations");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return Types.nextAnnotations(nextAnnotations, Annotation.class);
    }

    public static final /* synthetic */ <T> WildcardType subtypeOf() {
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        Class javaType = TypesJVMKt.getJavaType((KType) null);
        if (javaType instanceof Class) {
            Class clsBoxIfPrimitive = Util.boxIfPrimitive((Class) javaType);
            Intrinsics.checkNotNullExpressionValue(clsBoxIfPrimitive, "Util.boxIfPrimitive(type)");
            javaType = clsBoxIfPrimitive;
        }
        WildcardType wildcardTypeSubtypeOf = Types.subtypeOf(javaType);
        Intrinsics.checkNotNullExpressionValue(wildcardTypeSubtypeOf, "Types.subtypeOf(type)");
        return wildcardTypeSubtypeOf;
    }

    public static final /* synthetic */ <T> WildcardType supertypeOf() {
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        Class javaType = TypesJVMKt.getJavaType((KType) null);
        if (javaType instanceof Class) {
            Class clsBoxIfPrimitive = Util.boxIfPrimitive((Class) javaType);
            Intrinsics.checkNotNullExpressionValue(clsBoxIfPrimitive, "Util.boxIfPrimitive(type)");
            javaType = clsBoxIfPrimitive;
        }
        WildcardType wildcardTypeSupertypeOf = Types.supertypeOf(javaType);
        Intrinsics.checkNotNullExpressionValue(wildcardTypeSupertypeOf, "Types.supertypeOf(type)");
        return wildcardTypeSupertypeOf;
    }

    public static final GenericArrayType asArrayType(KType asArrayType) {
        Intrinsics.checkNotNullParameter(asArrayType, "$this$asArrayType");
        return asArrayType(TypesJVMKt.getJavaType(asArrayType));
    }

    public static final GenericArrayType asArrayType(KClass<?> asArrayType) {
        Intrinsics.checkNotNullParameter(asArrayType, "$this$asArrayType");
        return asArrayType(JvmClassMappingKt.getJavaClass((KClass) asArrayType));
    }

    public static final GenericArrayType asArrayType(Type asArrayType) {
        Intrinsics.checkNotNullParameter(asArrayType, "$this$asArrayType");
        GenericArrayType genericArrayTypeArrayOf = Types.arrayOf(asArrayType);
        Intrinsics.checkNotNullExpressionValue(genericArrayTypeArrayOf, "Types.arrayOf(this)");
        return genericArrayTypeArrayOf;
    }
}
