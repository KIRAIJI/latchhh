package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: specialBuiltinMembers.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BuiltinMethodsWithDifferentJvmName extends SpecialGenericSignatures {
    public static final BuiltinMethodsWithDifferentJvmName INSTANCE = new BuiltinMethodsWithDifferentJvmName();

    private BuiltinMethodsWithDifferentJvmName() {
    }

    public final boolean getSameAsRenamedInJvmBuiltin(Name name) {
        Intrinsics.checkNotNullParameter(name, "<this>");
        return SpecialGenericSignatures.Companion.getORIGINAL_SHORT_NAMES().contains(name);
    }

    public final Name getJvmName(SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Map<String, Name> signature_to_jvm_representation_name = SpecialGenericSignatures.Companion.getSIGNATURE_TO_JVM_REPRESENTATION_NAME();
        String strComputeJvmSignature = MethodSignatureMappingKt.computeJvmSignature(functionDescriptor);
        if (strComputeJvmSignature == null) {
            return null;
        }
        return signature_to_jvm_representation_name.get(strComputeJvmSignature);
    }

    public final boolean isBuiltinFunctionWithDifferentNameInJvm(final SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return KotlinBuiltIns.isBuiltIn(functionDescriptor) && DescriptorUtilsKt.firstOverridden$default(functionDescriptor, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithDifferentJvmName.isBuiltinFunctionWithDifferentNameInJvm.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(CallableMemberDescriptor callableMemberDescriptor) {
                return Boolean.valueOf(invoke2(callableMemberDescriptor));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Map<String, Name> signature_to_jvm_representation_name = SpecialGenericSignatures.Companion.getSIGNATURE_TO_JVM_REPRESENTATION_NAME();
                String strComputeJvmSignature = MethodSignatureMappingKt.computeJvmSignature(functionDescriptor);
                Objects.requireNonNull(signature_to_jvm_representation_name, "null cannot be cast to non-null type kotlin.collections.Map<K, *>");
                return signature_to_jvm_representation_name.containsKey(strComputeJvmSignature);
            }
        }, 1, null) != null;
    }

    public final List<Name> getBuiltinFunctionNamesByJvmName(Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        List<Name> list = SpecialGenericSignatures.Companion.getJVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP().get(name);
        return list == null ? CollectionsKt.emptyList() : list;
    }

    public final boolean isRemoveAtByIndex(SimpleFunctionDescriptor simpleFunctionDescriptor) {
        Intrinsics.checkNotNullParameter(simpleFunctionDescriptor, "<this>");
        return Intrinsics.areEqual(simpleFunctionDescriptor.getName().asString(), "removeAt") && Intrinsics.areEqual(MethodSignatureMappingKt.computeJvmSignature(simpleFunctionDescriptor), SpecialGenericSignatures.Companion.getREMOVE_AT_NAME_AND_SIGNATURE().getSignature());
    }
}
