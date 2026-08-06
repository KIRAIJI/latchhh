package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;

/* JADX INFO: compiled from: moduleByClassLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0005\u001a\u00020\u0006H\u0000\u001a\u0010\u0010\u0007\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\bH\u0000\" \u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"moduleByClassLoader", "Ljava/util/concurrent/ConcurrentMap;", "Lkotlin/reflect/jvm/internal/WeakClassLoaderBox;", "Ljava/lang/ref/WeakReference;", "Lkotlin/reflect/jvm/internal/impl/descriptors/runtime/components/RuntimeModuleData;", "clearModuleByClassLoaderCache", "", "getOrCreateModule", "Ljava/lang/Class;", "kotlin-reflection"}, k = 2, mv = {1, 4, 1})
public final class ModuleByClassLoaderKt {
    private static final ConcurrentMap<WeakClassLoaderBox, WeakReference<RuntimeModuleData>> moduleByClassLoader = new ConcurrentHashMap();

    public static final RuntimeModuleData getOrCreateModule(Class<?> getOrCreateModule) {
        Intrinsics.checkNotNullParameter(getOrCreateModule, "$this$getOrCreateModule");
        ClassLoader safeClassLoader = ReflectClassUtilKt.getSafeClassLoader(getOrCreateModule);
        WeakClassLoaderBox weakClassLoaderBox = new WeakClassLoaderBox(safeClassLoader);
        ConcurrentMap<WeakClassLoaderBox, WeakReference<RuntimeModuleData>> concurrentMap = moduleByClassLoader;
        WeakReference<RuntimeModuleData> weakReference = concurrentMap.get(weakClassLoaderBox);
        if (weakReference != null) {
            RuntimeModuleData it = weakReference.get();
            if (it != null) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                return it;
            }
            concurrentMap.remove(weakClassLoaderBox, weakReference);
        }
        RuntimeModuleData runtimeModuleDataCreate = RuntimeModuleData.Companion.create(safeClassLoader);
        while (true) {
            try {
                ConcurrentMap<WeakClassLoaderBox, WeakReference<RuntimeModuleData>> concurrentMap2 = moduleByClassLoader;
                WeakReference<RuntimeModuleData> weakReferencePutIfAbsent = concurrentMap2.putIfAbsent(weakClassLoaderBox, new WeakReference<>(runtimeModuleDataCreate));
                if (weakReferencePutIfAbsent == null) {
                    return runtimeModuleDataCreate;
                }
                RuntimeModuleData runtimeModuleData = weakReferencePutIfAbsent.get();
                if (runtimeModuleData != null) {
                    return runtimeModuleData;
                }
                concurrentMap2.remove(weakClassLoaderBox, weakReferencePutIfAbsent);
            } finally {
                weakClassLoaderBox.setTemporaryStrongRef((ClassLoader) null);
            }
        }
    }

    public static final void clearModuleByClassLoaderCache() {
        moduleByClassLoader.clear();
    }
}
