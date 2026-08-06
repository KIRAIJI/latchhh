package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.KotlinTarget;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationTargetMapper;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.JavaTypeEnhancementState;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;

/* JADX INFO: compiled from: AnnotationTypeQualifierResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AnnotationTypeQualifierResolver {
    private final JavaTypeEnhancementState javaTypeEnhancementState;
    private final MemoizedFunctionToNullable<ClassDescriptor, AnnotationDescriptor> resolvedNicknames;

    /* JADX INFO: compiled from: AnnotationTypeQualifierResolver.kt */
    public static final class TypeQualifierWithApplicability {
        private final int applicability;
        private final AnnotationDescriptor typeQualifier;

        public TypeQualifierWithApplicability(AnnotationDescriptor typeQualifier, int i) {
            Intrinsics.checkNotNullParameter(typeQualifier, "typeQualifier");
            this.typeQualifier = typeQualifier;
            this.applicability = i;
        }

        public final AnnotationDescriptor component1() {
            return this.typeQualifier;
        }

        public final List<AnnotationQualifierApplicabilityType> component2() {
            AnnotationQualifierApplicabilityType[] annotationQualifierApplicabilityTypeArrValuesCustom = AnnotationQualifierApplicabilityType.valuesCustom();
            ArrayList arrayList = new ArrayList();
            for (AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType : annotationQualifierApplicabilityTypeArrValuesCustom) {
                if (isApplicableTo(annotationQualifierApplicabilityType)) {
                    arrayList.add(annotationQualifierApplicabilityType);
                }
            }
            return arrayList;
        }

        private final boolean isApplicableTo(AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType) {
            if (isApplicableConsideringMask(annotationQualifierApplicabilityType)) {
                return true;
            }
            return isApplicableConsideringMask(AnnotationQualifierApplicabilityType.TYPE_USE) && annotationQualifierApplicabilityType != AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS;
        }

        private final boolean isApplicableConsideringMask(AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType) {
            return ((1 << annotationQualifierApplicabilityType.ordinal()) & this.applicability) != 0;
        }
    }

    public AnnotationTypeQualifierResolver(StorageManager storageManager, JavaTypeEnhancementState javaTypeEnhancementState) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState, "javaTypeEnhancementState");
        this.javaTypeEnhancementState = javaTypeEnhancementState;
        this.resolvedNicknames = storageManager.createMemoizedFunctionWithNullableValues(new AnnotationTypeQualifierResolver$resolvedNicknames$1(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AnnotationDescriptor computeTypeQualifierNickname(ClassDescriptor classDescriptor) {
        if (!classDescriptor.getAnnotations().hasAnnotation(AnnotationQualifiersFqNamesKt.getTYPE_QUALIFIER_NICKNAME_FQNAME())) {
            return null;
        }
        Iterator<AnnotationDescriptor> it = classDescriptor.getAnnotations().iterator();
        while (it.hasNext()) {
            AnnotationDescriptor annotationDescriptorResolveTypeQualifierAnnotation = resolveTypeQualifierAnnotation(it.next());
            if (annotationDescriptorResolveTypeQualifierAnnotation != null) {
                return annotationDescriptorResolveTypeQualifierAnnotation;
            }
        }
        return null;
    }

    private final AnnotationDescriptor resolveTypeQualifierNickname(ClassDescriptor classDescriptor) {
        if (classDescriptor.getKind() != ClassKind.ANNOTATION_CLASS) {
            return null;
        }
        return this.resolvedNicknames.invoke(classDescriptor);
    }

    public final AnnotationDescriptor resolveTypeQualifierAnnotation(AnnotationDescriptor annotationDescriptor) {
        ClassDescriptor annotationClass;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        if (this.javaTypeEnhancementState.getDisabledJsr305() || (annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor)) == null) {
            return null;
        }
        return AnnotationTypeQualifierResolverKt.isAnnotatedWithTypeQualifier(annotationClass) ? annotationDescriptor : resolveTypeQualifierNickname(annotationClass);
    }

    public final JavaDefaultQualifiers resolveQualifierBuiltInDefaultAnnotation(AnnotationDescriptor annotationDescriptor) {
        JavaDefaultQualifiers javaDefaultQualifiers;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        if (this.javaTypeEnhancementState.getDisabledDefaultAnnotations() || (javaDefaultQualifiers = AnnotationQualifiersFqNamesKt.getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS().get(annotationDescriptor.getFqName())) == null) {
            return null;
        }
        ReportLevel reportLevelResolveDefaultAnnotationState = resolveDefaultAnnotationState(annotationDescriptor);
        if (!(reportLevelResolveDefaultAnnotationState != ReportLevel.IGNORE)) {
            reportLevelResolveDefaultAnnotationState = null;
        }
        if (reportLevelResolveDefaultAnnotationState == null) {
            return null;
        }
        return JavaDefaultQualifiers.copy$default(javaDefaultQualifiers, NullabilityQualifierWithMigrationStatus.copy$default(javaDefaultQualifiers.getNullabilityQualifier(), null, reportLevelResolveDefaultAnnotationState.isWarning(), 1, null), null, false, 6, null);
    }

    private final ReportLevel resolveDefaultAnnotationState(AnnotationDescriptor annotationDescriptor) {
        if (AnnotationQualifiersFqNamesKt.getJSPECIFY_DEFAULT_ANNOTATIONS().containsKey(annotationDescriptor.getFqName())) {
            return this.javaTypeEnhancementState.getJspecifyReportLevel();
        }
        return resolveJsr305AnnotationState(annotationDescriptor);
    }

    public final TypeQualifierWithApplicability resolveTypeQualifierDefaultAnnotation(AnnotationDescriptor annotationDescriptor) {
        AnnotationDescriptor next;
        List<AnnotationQualifierApplicabilityType> listEmptyList;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        if (this.javaTypeEnhancementState.getDisabledJsr305()) {
            return null;
        }
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        if (annotationClass == null || !annotationClass.getAnnotations().hasAnnotation(AnnotationQualifiersFqNamesKt.getTYPE_QUALIFIER_DEFAULT_FQNAME())) {
            annotationClass = null;
        }
        if (annotationClass == null) {
            return null;
        }
        ClassDescriptor annotationClass2 = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        Intrinsics.checkNotNull(annotationClass2);
        AnnotationDescriptor annotationDescriptorMo1670findAnnotation = annotationClass2.getAnnotations().mo1670findAnnotation(AnnotationQualifiersFqNamesKt.getTYPE_QUALIFIER_DEFAULT_FQNAME());
        Intrinsics.checkNotNull(annotationDescriptorMo1670findAnnotation);
        Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptorMo1670findAnnotation.getAllValueArguments();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Name, ConstantValue<?>> entry : allValueArguments.entrySet()) {
            Name key = entry.getKey();
            ConstantValue<?> value = entry.getValue();
            if (Intrinsics.areEqual(key, JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME)) {
                listEmptyList = mapJavaConstantToQualifierApplicabilityTypes(value);
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        Iterator it = arrayList.iterator();
        int iOrdinal = 0;
        while (it.hasNext()) {
            iOrdinal |= 1 << ((AnnotationQualifierApplicabilityType) it.next()).ordinal();
        }
        Iterator<AnnotationDescriptor> it2 = annotationClass.getAnnotations().iterator();
        while (true) {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
            if (resolveTypeQualifierAnnotation(next) != null) {
                break;
            }
        }
        AnnotationDescriptor annotationDescriptor2 = next;
        if (annotationDescriptor2 == null) {
            return null;
        }
        return new TypeQualifierWithApplicability(annotationDescriptor2, iOrdinal);
    }

    public final TypeQualifierWithApplicability resolveAnnotation(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        if (annotationClass == null) {
            return null;
        }
        Annotations annotations = annotationClass.getAnnotations();
        FqName TARGET_ANNOTATION = JvmAnnotationNames.TARGET_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(TARGET_ANNOTATION, "TARGET_ANNOTATION");
        AnnotationDescriptor annotationDescriptorMo1670findAnnotation = annotations.mo1670findAnnotation(TARGET_ANNOTATION);
        if (annotationDescriptorMo1670findAnnotation == null) {
            return null;
        }
        Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptorMo1670findAnnotation.getAllValueArguments();
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Name, ConstantValue<?>>> it = allValueArguments.entrySet().iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, mapKotlinConstantToQualifierApplicabilityTypes(it.next().getValue()));
        }
        int iOrdinal = 0;
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            iOrdinal |= 1 << ((AnnotationQualifierApplicabilityType) it2.next()).ordinal();
        }
        return new TypeQualifierWithApplicability(annotationDescriptor, iOrdinal);
    }

    public final ReportLevel resolveJsr305AnnotationState(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        ReportLevel reportLevelResolveJsr305CustomState = resolveJsr305CustomState(annotationDescriptor);
        return reportLevelResolveJsr305CustomState == null ? this.javaTypeEnhancementState.getGlobalJsr305Level() : reportLevelResolveJsr305CustomState;
    }

    public final ReportLevel resolveJsr305CustomState(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        Map<String, ReportLevel> userDefinedLevelForSpecificJsr305Annotation = this.javaTypeEnhancementState.getUserDefinedLevelForSpecificJsr305Annotation();
        FqName fqName = annotationDescriptor.getFqName();
        ReportLevel reportLevel = userDefinedLevelForSpecificJsr305Annotation.get(fqName == null ? null : fqName.asString());
        if (reportLevel != null) {
            return reportLevel;
        }
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        if (annotationClass == null) {
            return null;
        }
        return migrationAnnotationStatus(annotationClass);
    }

    private final ReportLevel migrationAnnotationStatus(ClassDescriptor classDescriptor) {
        AnnotationDescriptor annotationDescriptorMo1670findAnnotation = classDescriptor.getAnnotations().mo1670findAnnotation(AnnotationQualifiersFqNamesKt.getMIGRATION_ANNOTATION_FQNAME());
        ConstantValue<?> constantValueFirstArgument = annotationDescriptorMo1670findAnnotation == null ? null : DescriptorUtilsKt.firstArgument(annotationDescriptorMo1670findAnnotation);
        EnumValue enumValue = constantValueFirstArgument instanceof EnumValue ? (EnumValue) constantValueFirstArgument : null;
        if (enumValue == null) {
            return null;
        }
        ReportLevel migrationLevelForJsr305 = this.javaTypeEnhancementState.getMigrationLevelForJsr305();
        if (migrationLevelForJsr305 != null) {
            return migrationLevelForJsr305;
        }
        String strAsString = enumValue.getEnumEntryName().asString();
        int iHashCode = strAsString.hashCode();
        if (iHashCode == -2137067054) {
            if (strAsString.equals("IGNORE")) {
                return ReportLevel.IGNORE;
            }
            return null;
        }
        if (iHashCode == -1838656823) {
            if (strAsString.equals("STRICT")) {
                return ReportLevel.STRICT;
            }
            return null;
        }
        if (iHashCode == 2656902 && strAsString.equals("WARN")) {
            return ReportLevel.WARN;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<String> toKotlinTargetNames(String str) {
        Set<KotlinTarget> setMapJavaTargetArgumentByName = JavaAnnotationTargetMapper.INSTANCE.mapJavaTargetArgumentByName(str);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setMapJavaTargetArgumentByName, 10));
        Iterator<T> it = setMapJavaTargetArgumentByName.iterator();
        while (it.hasNext()) {
            arrayList.add(((KotlinTarget) it.next()).name());
        }
        return arrayList;
    }

    private final List<AnnotationQualifierApplicabilityType> mapConstantToQualifierApplicabilityTypes(ConstantValue<?> constantValue, Function2<? super EnumValue, ? super AnnotationQualifierApplicabilityType, Boolean> function2) {
        AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType;
        if (!(constantValue instanceof ArrayValue)) {
            if (!(constantValue instanceof EnumValue)) {
                return CollectionsKt.emptyList();
            }
            AnnotationQualifierApplicabilityType[] annotationQualifierApplicabilityTypeArrValuesCustom = AnnotationQualifierApplicabilityType.valuesCustom();
            int length = annotationQualifierApplicabilityTypeArrValuesCustom.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    annotationQualifierApplicabilityType = null;
                    break;
                }
                annotationQualifierApplicabilityType = annotationQualifierApplicabilityTypeArrValuesCustom[i];
                if (function2.invoke(constantValue, annotationQualifierApplicabilityType).booleanValue()) {
                    break;
                }
                i++;
            }
            return CollectionsKt.listOfNotNull(annotationQualifierApplicabilityType);
        }
        List<? extends ConstantValue<?>> value = ((ArrayValue) constantValue).getValue();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = value.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, mapConstantToQualifierApplicabilityTypes((ConstantValue) it.next(), function2));
        }
        return arrayList;
    }

    private final List<AnnotationQualifierApplicabilityType> mapJavaConstantToQualifierApplicabilityTypes(ConstantValue<?> constantValue) {
        return mapConstantToQualifierApplicabilityTypes(constantValue, new Function2<EnumValue, AnnotationQualifierApplicabilityType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver.mapJavaConstantToQualifierApplicabilityTypes.1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Boolean invoke(EnumValue enumValue, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType) {
                return Boolean.valueOf(invoke2(enumValue, annotationQualifierApplicabilityType));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(EnumValue enumValue, AnnotationQualifierApplicabilityType it) {
                Intrinsics.checkNotNullParameter(enumValue, "<this>");
                Intrinsics.checkNotNullParameter(it, "it");
                return Intrinsics.areEqual(enumValue.getEnumEntryName().getIdentifier(), it.getJavaTarget());
            }
        });
    }

    private final List<AnnotationQualifierApplicabilityType> mapKotlinConstantToQualifierApplicabilityTypes(ConstantValue<?> constantValue) {
        return mapConstantToQualifierApplicabilityTypes(constantValue, new Function2<EnumValue, AnnotationQualifierApplicabilityType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver.mapKotlinConstantToQualifierApplicabilityTypes.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Boolean invoke(EnumValue enumValue, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType) {
                return Boolean.valueOf(invoke2(enumValue, annotationQualifierApplicabilityType));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(EnumValue enumValue, AnnotationQualifierApplicabilityType it) {
                Intrinsics.checkNotNullParameter(enumValue, "<this>");
                Intrinsics.checkNotNullParameter(it, "it");
                return AnnotationTypeQualifierResolver.this.toKotlinTargetNames(it.getJavaTarget()).contains(enumValue.getEnumEntryName().getIdentifier());
            }
        });
    }
}
