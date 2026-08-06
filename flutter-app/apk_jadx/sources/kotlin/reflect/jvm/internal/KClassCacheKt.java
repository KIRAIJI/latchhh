package kotlin.reflect.jvm.internal;

import androidx.exifinterface.media.ExifInterface;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.pcollections.HashPMap;

/* JADX INFO: compiled from: kClassCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0005\u001a\u00020\u0006H\u0000\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\t0\b\"\b\b\u0000\u0010\t*\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u000bH\u0000\"*\u0010\u0000\u001a\u001e\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00040\u00040\u0001X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"K_CLASS_CACHE", "Lkotlin/reflect/jvm/internal/pcollections/HashPMap;", "", "kotlin.jvm.PlatformType", "", "clearKClassCache", "", "getOrCreateKotlinClass", "Lkotlin/reflect/jvm/internal/KClassImpl;", ExifInterface.GPS_DIRECTION_TRUE, "jClass", "Ljava/lang/Class;", "kotlin-reflection"}, k = 2, mv = {1, 4, 1})
public final class KClassCacheKt {
    private static HashPMap<String, Object> K_CLASS_CACHE;

    static {
        HashPMap<String, Object> hashPMapEmpty = HashPMap.empty();
        Intrinsics.checkNotNullExpressionValue(hashPMapEmpty, "HashPMap.empty<String, Any>()");
        K_CLASS_CACHE = hashPMapEmpty;
    }

    public static final <T> KClassImpl<T> getOrCreateKotlinClass(Class<T> jClass) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        String name = jClass.getName();
        Object obj = K_CLASS_CACHE.get(name);
        if (obj instanceof WeakReference) {
            KClassImpl<T> kClassImpl = (KClassImpl) ((WeakReference) obj).get();
            if (Intrinsics.areEqual(kClassImpl != null ? kClassImpl.getJClass() : null, jClass)) {
                return kClassImpl;
            }
        } else if (obj != null) {
            for (WeakReference weakReference : (WeakReference[]) obj) {
                KClassImpl<T> kClassImpl2 = (KClassImpl) weakReference.get();
                if (Intrinsics.areEqual(kClassImpl2 != null ? kClassImpl2.getJClass() : null, jClass)) {
                    return kClassImpl2;
                }
            }
            int length = ((Object[]) obj).length;
            WeakReference[] weakReferenceArr = new WeakReference[length + 1];
            System.arraycopy(obj, 0, weakReferenceArr, 0, length);
            KClassImpl<T> kClassImpl3 = new KClassImpl<>(jClass);
            weakReferenceArr[length] = new WeakReference(kClassImpl3);
            HashPMap<String, Object> hashPMapPlus = K_CLASS_CACHE.plus(name, weakReferenceArr);
            Intrinsics.checkNotNullExpressionValue(hashPMapPlus, "K_CLASS_CACHE.plus(name, newArray)");
            K_CLASS_CACHE = hashPMapPlus;
            return kClassImpl3;
        }
        KClassImpl<T> kClassImpl4 = new KClassImpl<>(jClass);
        HashPMap<String, Object> hashPMapPlus2 = K_CLASS_CACHE.plus(name, new WeakReference(kClassImpl4));
        Intrinsics.checkNotNullExpressionValue(hashPMapPlus2, "K_CLASS_CACHE.plus(name, WeakReference(newKClass))");
        K_CLASS_CACHE = hashPMapPlus2;
        return kClassImpl4;
    }

    public static final void clearKClassCache() {
        HashPMap<String, Object> hashPMapEmpty = HashPMap.empty();
        Intrinsics.checkNotNullExpressionValue(hashPMapEmpty, "HashPMap.empty()");
        K_CLASS_CACHE = hashPMapEmpty;
    }
}
