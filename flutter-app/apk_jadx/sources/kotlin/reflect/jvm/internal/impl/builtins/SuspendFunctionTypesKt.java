package kotlin.reflect.jvm.internal.impl.builtins;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.MutableClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: suspendFunctionTypes.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SuspendFunctionTypesKt {
    private static final MutableClassDescriptor FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL;
    private static final MutableClassDescriptor FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE;

    static {
        ModuleDescriptor errorModule = ErrorUtils.getErrorModule();
        Intrinsics.checkNotNullExpressionValue(errorModule, "getErrorModule()");
        MutableClassDescriptor mutableClassDescriptor = new MutableClassDescriptor(new EmptyPackageFragmentDescriptor(errorModule, StandardNames.COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL), ClassKind.INTERFACE, false, false, StandardNames.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL.shortName(), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
        mutableClassDescriptor.setModality(Modality.ABSTRACT);
        mutableClassDescriptor.setVisibility(DescriptorVisibilities.PUBLIC);
        mutableClassDescriptor.setTypeParameterDescriptors(CollectionsKt.listOf(TypeParameterDescriptorImpl.createWithDefaultBound(mutableClassDescriptor, Annotations.Companion.getEMPTY(), false, Variance.IN_VARIANCE, Name.identifier(ExifInterface.GPS_DIRECTION_TRUE), 0, LockBasedStorageManager.NO_LOCKS)));
        mutableClassDescriptor.createTypeConstructor();
        FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL = mutableClassDescriptor;
        ModuleDescriptor errorModule2 = ErrorUtils.getErrorModule();
        Intrinsics.checkNotNullExpressionValue(errorModule2, "getErrorModule()");
        MutableClassDescriptor mutableClassDescriptor2 = new MutableClassDescriptor(new EmptyPackageFragmentDescriptor(errorModule2, StandardNames.COROUTINES_PACKAGE_FQ_NAME_RELEASE), ClassKind.INTERFACE, false, false, StandardNames.CONTINUATION_INTERFACE_FQ_NAME_RELEASE.shortName(), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
        mutableClassDescriptor2.setModality(Modality.ABSTRACT);
        mutableClassDescriptor2.setVisibility(DescriptorVisibilities.PUBLIC);
        mutableClassDescriptor2.setTypeParameterDescriptors(CollectionsKt.listOf(TypeParameterDescriptorImpl.createWithDefaultBound(mutableClassDescriptor2, Annotations.Companion.getEMPTY(), false, Variance.IN_VARIANCE, Name.identifier(ExifInterface.GPS_DIRECTION_TRUE), 0, LockBasedStorageManager.NO_LOCKS)));
        mutableClassDescriptor2.createTypeConstructor();
        FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE = mutableClassDescriptor2;
    }

    public static final SimpleType transformSuspendFunctionToRuntimeFunctionType(KotlinType suspendFunType, boolean z) {
        TypeConstructor typeConstructor;
        Intrinsics.checkNotNullParameter(suspendFunType, "suspendFunType");
        FunctionTypesKt.isSuspendFunctionType(suspendFunType);
        KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(suspendFunType);
        Annotations annotations = suspendFunType.getAnnotations();
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(suspendFunType);
        List<TypeProjection> valueParameterTypesFromFunctionType = FunctionTypesKt.getValueParameterTypesFromFunctionType(suspendFunType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterTypesFromFunctionType, 10));
        Iterator<T> it = valueParameterTypesFromFunctionType.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getType());
        }
        ArrayList arrayList2 = arrayList;
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        Annotations empty = Annotations.Companion.getEMPTY();
        if (z) {
            typeConstructor = FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE.getTypeConstructor();
        } else {
            typeConstructor = FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL.getTypeConstructor();
        }
        TypeConstructor typeConstructor2 = typeConstructor;
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "if (isReleaseCoroutines) FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE.typeConstructor\n                    else FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL.typeConstructor");
        List listPlus = CollectionsKt.plus((Collection<? extends SimpleType>) arrayList2, KotlinTypeFactory.simpleType$default(empty, typeConstructor2, CollectionsKt.listOf(TypeUtilsKt.asTypeProjection(FunctionTypesKt.getReturnTypeFromFunctionType(suspendFunType))), false, null, 16, null));
        SimpleType nullableAnyType = TypeUtilsKt.getBuiltIns(suspendFunType).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "suspendFunType.builtIns.nullableAnyType");
        return FunctionTypesKt.createFunctionType$default(builtIns, annotations, receiverTypeFromFunctionType, listPlus, null, nullableAnyType, false, 64, null).makeNullableAsSpecified(suspendFunType.isMarkedNullable());
    }

    public static final boolean isContinuation(FqName fqName, boolean z) {
        if (z) {
            return Intrinsics.areEqual(fqName, StandardNames.CONTINUATION_INTERFACE_FQ_NAME_RELEASE);
        }
        return Intrinsics.areEqual(fqName, StandardNames.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL);
    }
}
