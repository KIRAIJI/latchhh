package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* JADX INFO: compiled from: util.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class UtilKt {
    public static final List<ValueParameterDescriptor> copyValueParameters(Collection<ValueParameterData> newValueParametersTypes, Collection<? extends ValueParameterDescriptor> oldValueParameters, CallableDescriptor newOwner) {
        Intrinsics.checkNotNullParameter(newValueParametersTypes, "newValueParametersTypes");
        Intrinsics.checkNotNullParameter(oldValueParameters, "oldValueParameters");
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        newValueParametersTypes.size();
        oldValueParameters.size();
        List<Pair> listZip = CollectionsKt.zip(newValueParametersTypes, oldValueParameters);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
        for (Pair pair : listZip) {
            ValueParameterData valueParameterData = (ValueParameterData) pair.component1();
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
            int index = valueParameterDescriptor.getIndex();
            Annotations annotations = valueParameterDescriptor.getAnnotations();
            Name name = valueParameterDescriptor.getName();
            Intrinsics.checkNotNullExpressionValue(name, "oldParameter.name");
            KotlinType type = valueParameterData.getType();
            boolean hasDefaultValue = valueParameterData.getHasDefaultValue();
            boolean zIsCrossinline = valueParameterDescriptor.isCrossinline();
            boolean zIsNoinline = valueParameterDescriptor.isNoinline();
            KotlinType arrayElementType = valueParameterDescriptor.getVarargElementType() != null ? DescriptorUtilsKt.getModule(newOwner).getBuiltIns().getArrayElementType(valueParameterData.getType()) : null;
            SourceElement source = valueParameterDescriptor.getSource();
            Intrinsics.checkNotNullExpressionValue(source, "oldParameter.source");
            arrayList.add(new ValueParameterDescriptorImpl(newOwner, null, index, annotations, name, type, hasDefaultValue, zIsCrossinline, zIsNoinline, arrayElementType, source));
        }
        return arrayList;
    }

    public static final LazyJavaStaticClassScope getParentJavaStaticClassScope(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(classDescriptor);
        if (superClassNotAny == null) {
            return null;
        }
        MemberScope staticScope = superClassNotAny.getStaticScope();
        LazyJavaStaticClassScope lazyJavaStaticClassScope = staticScope instanceof LazyJavaStaticClassScope ? (LazyJavaStaticClassScope) staticScope : null;
        return lazyJavaStaticClassScope == null ? getParentJavaStaticClassScope(superClassNotAny) : lazyJavaStaticClassScope;
    }

    public static final AnnotationDefaultValue getDefaultValueFromAnnotation(ValueParameterDescriptor valueParameterDescriptor) {
        ConstantValue<?> constantValueFirstArgument;
        StringValue stringValue;
        String value;
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        Annotations annotations = valueParameterDescriptor.getAnnotations();
        FqName DEFAULT_VALUE_FQ_NAME = JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_VALUE_FQ_NAME, "DEFAULT_VALUE_FQ_NAME");
        AnnotationDescriptor annotationDescriptorMo1670findAnnotation = annotations.mo1670findAnnotation(DEFAULT_VALUE_FQ_NAME);
        if (annotationDescriptorMo1670findAnnotation == null || (constantValueFirstArgument = DescriptorUtilsKt.firstArgument(annotationDescriptorMo1670findAnnotation)) == null) {
            stringValue = null;
        } else {
            if (!(constantValueFirstArgument instanceof StringValue)) {
                constantValueFirstArgument = null;
            }
            stringValue = (StringValue) constantValueFirstArgument;
        }
        if (stringValue != null && (value = stringValue.getValue()) != null) {
            return new StringDefaultValue(value);
        }
        Annotations annotations2 = valueParameterDescriptor.getAnnotations();
        FqName DEFAULT_NULL_FQ_NAME = JvmAnnotationNames.DEFAULT_NULL_FQ_NAME;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_NULL_FQ_NAME, "DEFAULT_NULL_FQ_NAME");
        if (annotations2.hasAnnotation(DEFAULT_NULL_FQ_NAME)) {
            return NullDefaultValue.INSTANCE;
        }
        return null;
    }
}
