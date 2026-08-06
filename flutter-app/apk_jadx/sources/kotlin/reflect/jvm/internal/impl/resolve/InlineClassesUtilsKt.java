package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;

/* JADX INFO: compiled from: inlineClassesUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class InlineClassesUtilsKt {
    private static final FqName JVM_INLINE_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmInline");

    public static final ValueParameterDescriptor underlyingRepresentation(ClassDescriptor classDescriptor) {
        ClassConstructorDescriptor classConstructorDescriptorMo1669getUnsubstitutedPrimaryConstructor;
        List<ValueParameterDescriptor> valueParameters;
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        if (!isInlineClass(classDescriptor) || (classConstructorDescriptorMo1669getUnsubstitutedPrimaryConstructor = classDescriptor.mo1669getUnsubstitutedPrimaryConstructor()) == null || (valueParameters = classConstructorDescriptorMo1669getUnsubstitutedPrimaryConstructor.getValueParameters()) == null) {
            return null;
        }
        return (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
    }

    public static final boolean isInlineClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
            if (classDescriptor.isInline() || classDescriptor.isValue()) {
                return true;
            }
        }
        return false;
    }

    public static final ValueParameterDescriptor unsubstitutedUnderlyingParameter(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = kotlinType.getConstructor().mo1677getDeclarationDescriptor();
        if (!(classifierDescriptorMo1677getDeclarationDescriptor instanceof ClassDescriptor)) {
            classifierDescriptorMo1677getDeclarationDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptorMo1677getDeclarationDescriptor;
        if (classDescriptor == null) {
            return null;
        }
        return underlyingRepresentation(classDescriptor);
    }

    public static final boolean isInlineClassType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = kotlinType.getConstructor().mo1677getDeclarationDescriptor();
        if (classifierDescriptorMo1677getDeclarationDescriptor == null) {
            return false;
        }
        return isInlineClass(classifierDescriptorMo1677getDeclarationDescriptor);
    }

    public static final KotlinType substitutedUnderlyingType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ValueParameterDescriptor valueParameterDescriptorUnsubstitutedUnderlyingParameter = unsubstitutedUnderlyingParameter(kotlinType);
        if (valueParameterDescriptorUnsubstitutedUnderlyingParameter == null) {
            return null;
        }
        return TypeSubstitutor.create(kotlinType).substitute(valueParameterDescriptorUnsubstitutedUnderlyingParameter.getType(), Variance.INVARIANT);
    }

    public static final boolean isGetterOfUnderlyingPropertyOfInlineClass(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if (callableDescriptor instanceof PropertyGetterDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyGetterDescriptor) callableDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "correspondingProperty");
            if (isUnderlyingPropertyOfInlineClass(correspondingProperty)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isUnderlyingPropertyOfInlineClass(VariableDescriptor variableDescriptor) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "<this>");
        if (variableDescriptor.getExtensionReceiverParameter() != null) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "this.containingDeclaration");
        if (!isInlineClass(containingDeclaration)) {
            return false;
        }
        ValueParameterDescriptor valueParameterDescriptorUnderlyingRepresentation = underlyingRepresentation((ClassDescriptor) containingDeclaration);
        return Intrinsics.areEqual(valueParameterDescriptorUnderlyingRepresentation == null ? null : valueParameterDescriptorUnderlyingRepresentation.getName(), variableDescriptor.getName());
    }
}
