package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsCustomizer;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaPackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.resolve.sam.SamConversionResolverImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ContractDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.LocalClassifierTypeSettings;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: DeserializationComponentsForJava.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DeserializationComponentsForJava {
    private final DeserializationComponents components;

    public DeserializationComponentsForJava(StorageManager storageManager, ModuleDescriptor moduleDescriptor, DeserializationConfiguration configuration, JavaClassDataFinder classDataFinder, BinaryClassAnnotationAndConstantLoaderImpl annotationAndConstantLoader, LazyJavaPackageFragmentProvider packageFragmentProvider, NotFoundClasses notFoundClasses, ErrorReporter errorReporter, LookupTracker lookupTracker, ContractDeserializer contractDeserializer, NewKotlinTypeChecker kotlinTypeChecker) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(classDataFinder, "classDataFinder");
        Intrinsics.checkNotNullParameter(annotationAndConstantLoader, "annotationAndConstantLoader");
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "packageFragmentProvider");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(errorReporter, "errorReporter");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(contractDeserializer, "contractDeserializer");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        KotlinBuiltIns builtIns = moduleDescriptor.getBuiltIns();
        JvmBuiltIns jvmBuiltIns = builtIns instanceof JvmBuiltIns ? (JvmBuiltIns) builtIns : null;
        JavaClassDataFinder javaClassDataFinder = classDataFinder;
        BinaryClassAnnotationAndConstantLoaderImpl binaryClassAnnotationAndConstantLoaderImpl = annotationAndConstantLoader;
        LazyJavaPackageFragmentProvider lazyJavaPackageFragmentProvider = packageFragmentProvider;
        LocalClassifierTypeSettings.Default r22 = LocalClassifierTypeSettings.Default.INSTANCE;
        JavaFlexibleTypeDeserializer javaFlexibleTypeDeserializer = JavaFlexibleTypeDeserializer.INSTANCE;
        List listEmptyList = CollectionsKt.emptyList();
        AdditionalClassPartsProvider customizer = jvmBuiltIns == null ? null : jvmBuiltIns.getCustomizer();
        AdditionalClassPartsProvider additionalClassPartsProvider = customizer == null ? AdditionalClassPartsProvider.None.INSTANCE : customizer;
        JvmBuiltInsCustomizer customizer2 = jvmBuiltIns != null ? jvmBuiltIns.getCustomizer() : null;
        this.components = new DeserializationComponents(storageManager, moduleDescriptor, configuration, javaClassDataFinder, binaryClassAnnotationAndConstantLoaderImpl, lazyJavaPackageFragmentProvider, r22, errorReporter, lookupTracker, javaFlexibleTypeDeserializer, listEmptyList, notFoundClasses, contractDeserializer, additionalClassPartsProvider, customizer2 == null ? PlatformDependentDeclarationFilter.NoPlatformDependent.INSTANCE : customizer2, JvmProtoBufUtil.INSTANCE.getEXTENSION_REGISTRY(), kotlinTypeChecker, new SamConversionResolverImpl(storageManager, CollectionsKt.emptyList()), null, 262144, null);
    }

    public final DeserializationComponents getComponents() {
        return this.components;
    }
}
