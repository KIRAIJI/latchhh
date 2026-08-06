package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ErasedOverridabilityCondition.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ErasedOverridabilityCondition implements ExternalOverridabilityCondition {

    /* JADX INFO: compiled from: ErasedOverridabilityCondition.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OverridingUtil.OverrideCompatibilityInfo.Result.values().length];
            iArr[OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE.ordinal()] = 1;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    public ExternalOverridabilityCondition.Result isOverridable(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor, ClassDescriptor classDescriptor) {
        boolean z;
        SimpleFunctionDescriptor simpleFunctionDescriptorSubstitute;
        Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
        Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
        if (subDescriptor instanceof JavaMethodDescriptor) {
            JavaMethodDescriptor javaMethodDescriptor = (JavaMethodDescriptor) subDescriptor;
            Intrinsics.checkNotNullExpressionValue(javaMethodDescriptor.getTypeParameters(), "subDescriptor.typeParameters");
            if (!(!r0.isEmpty())) {
                OverridingUtil.OverrideCompatibilityInfo basicOverridabilityProblem = OverridingUtil.getBasicOverridabilityProblem(superDescriptor, subDescriptor);
                if ((basicOverridabilityProblem == null ? null : basicOverridabilityProblem.getResult()) != null) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                List<ValueParameterDescriptor> valueParameters = javaMethodDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "subDescriptor.valueParameters");
                Sequence map = SequencesKt.map(CollectionsKt.asSequence(valueParameters), new Function1<ValueParameterDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.ErasedOverridabilityCondition$isOverridable$signatureTypes$1
                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinType invoke(ValueParameterDescriptor valueParameterDescriptor) {
                        return valueParameterDescriptor.getType();
                    }
                });
                KotlinType returnType = javaMethodDescriptor.getReturnType();
                Intrinsics.checkNotNull(returnType);
                Sequence sequencePlus = SequencesKt.plus((Sequence<? extends KotlinType>) map, returnType);
                ReceiverParameterDescriptor extensionReceiverParameter = javaMethodDescriptor.getExtensionReceiverParameter();
                Iterator it = SequencesKt.plus(sequencePlus, (Iterable) CollectionsKt.listOfNotNull(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null)).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    KotlinType kotlinType = (KotlinType) it.next();
                    if ((kotlinType.getArguments().isEmpty() ^ true) && !(kotlinType.unwrap() instanceof RawTypeImpl)) {
                        z = true;
                        break;
                    }
                }
                if (!z && (simpleFunctionDescriptorSubstitute = superDescriptor.substitute(RawSubstitution.INSTANCE.buildSubstitutor())) != null) {
                    if (simpleFunctionDescriptorSubstitute instanceof SimpleFunctionDescriptor) {
                        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) simpleFunctionDescriptorSubstitute;
                        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptor.getTypeParameters(), "erasedSuper.typeParameters");
                        if (!r0.isEmpty()) {
                            SimpleFunctionDescriptor simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) simpleFunctionDescriptor.newCopyBuilder().setTypeParameters(CollectionsKt.emptyList()).build();
                            Intrinsics.checkNotNull(simpleFunctionDescriptor2);
                            simpleFunctionDescriptorSubstitute = simpleFunctionDescriptor2;
                        }
                    }
                    OverridingUtil.OverrideCompatibilityInfo.Result result = OverridingUtil.DEFAULT.isOverridableByWithoutExternalConditions(simpleFunctionDescriptorSubstitute, subDescriptor, false).getResult();
                    Intrinsics.checkNotNullExpressionValue(result, "DEFAULT.isOverridableByWithoutExternalConditions(erasedSuper, subDescriptor, false).result");
                    if (WhenMappings.$EnumSwitchMapping$0[result.ordinal()] == 1) {
                        return ExternalOverridabilityCondition.Result.OVERRIDABLE;
                    }
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                return ExternalOverridabilityCondition.Result.UNKNOWN;
            }
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.SUCCESS_ONLY;
    }
}
