package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.NullDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.StringDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.JavaTypeEnhancementState;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SignatureEnhancement {
    private final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
    private final JavaTypeEnhancementState javaTypeEnhancementState;
    private final JavaTypeEnhancement typeEnhancement;

    public SignatureEnhancement(AnnotationTypeQualifierResolver annotationTypeQualifierResolver, JavaTypeEnhancementState javaTypeEnhancementState, JavaTypeEnhancement typeEnhancement) {
        Intrinsics.checkNotNullParameter(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState, "javaTypeEnhancementState");
        Intrinsics.checkNotNullParameter(typeEnhancement, "typeEnhancement");
        this.annotationTypeQualifierResolver = annotationTypeQualifierResolver;
        this.javaTypeEnhancementState = javaTypeEnhancementState;
        this.typeEnhancement = typeEnhancement;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final NullabilityQualifierWithMigrationStatus extractNullabilityTypeFromArgument(AnnotationDescriptor annotationDescriptor, boolean z) {
        ConstantValue<?> constantValueFirstArgument = DescriptorUtilsKt.firstArgument(annotationDescriptor);
        EnumValue enumValue = constantValueFirstArgument instanceof EnumValue ? (EnumValue) constantValueFirstArgument : null;
        if (enumValue == null) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
        }
        String strAsString = enumValue.getEnumEntryName().asString();
        switch (strAsString.hashCode()) {
            case 73135176:
                if (!strAsString.equals("MAYBE")) {
                    return null;
                }
                break;
            case 74175084:
                if (!strAsString.equals("NEVER")) {
                    return null;
                }
                break;
            case 433141802:
                if (strAsString.equals("UNKNOWN")) {
                    return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, z);
                }
                return null;
            case 1933739535:
                if (strAsString.equals("ALWAYS")) {
                    return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
                }
                return null;
            default:
                return null;
        }
        return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
    }

    public final NullabilityQualifierWithMigrationStatus extractNullability(AnnotationDescriptor annotationDescriptor, boolean z, boolean z2) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations2 = extractNullabilityFromKnownAnnotations(annotationDescriptor, z, z2);
        if (nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations2 != null) {
            return nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations2;
        }
        AnnotationDescriptor annotationDescriptorResolveTypeQualifierAnnotation = this.annotationTypeQualifierResolver.resolveTypeQualifierAnnotation(annotationDescriptor);
        if (annotationDescriptorResolveTypeQualifierAnnotation == null) {
            return null;
        }
        ReportLevel reportLevelResolveJsr305AnnotationState = this.annotationTypeQualifierResolver.resolveJsr305AnnotationState(annotationDescriptor);
        if (reportLevelResolveJsr305AnnotationState.isIgnore() || (nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations = extractNullabilityFromKnownAnnotations(annotationDescriptorResolveTypeQualifierAnnotation, z, z2)) == null) {
            return null;
        }
        return NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations, null, reportLevelResolveJsr305AnnotationState.isWarning(), 1, null);
    }

    private final NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations(AnnotationDescriptor annotationDescriptor, boolean z, boolean z2) {
        FqName fqName = annotationDescriptor.getFqName();
        if (fqName == null) {
            return null;
        }
        boolean z3 = (annotationDescriptor instanceof LazyJavaAnnotationDescriptor) && (((LazyJavaAnnotationDescriptor) annotationDescriptor).isFreshlySupportedTypeUseAnnotation() || z2) && !z;
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusJspecifyMigrationStatus = jspecifyMigrationStatus(fqName);
        if (nullabilityQualifierWithMigrationStatusJspecifyMigrationStatus == null && (nullabilityQualifierWithMigrationStatusJspecifyMigrationStatus = commonMigrationStatus(fqName, annotationDescriptor, z3)) == null) {
            return null;
        }
        return (!nullabilityQualifierWithMigrationStatusJspecifyMigrationStatus.isForWarningOnly() && (annotationDescriptor instanceof PossiblyExternalAnnotationDescriptor) && ((PossiblyExternalAnnotationDescriptor) annotationDescriptor).isIdeExternalAnnotation()) ? NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusJspecifyMigrationStatus, null, true, 1, null) : nullabilityQualifierWithMigrationStatusJspecifyMigrationStatus;
    }

    private final NullabilityQualifierWithMigrationStatus jspecifyMigrationStatus(FqName fqName) {
        if (this.javaTypeEnhancementState.getJspecifyReportLevel() == ReportLevel.IGNORE) {
            return null;
        }
        boolean z = this.javaTypeEnhancementState.getJspecifyReportLevel() == ReportLevel.WARN;
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getJSPECIFY_NULLABLE())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getJSPECIFY_NULLNESS_UNKNOWN())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, z);
        }
        return null;
    }

    private final NullabilityQualifierWithMigrationStatus commonMigrationStatus(FqName fqName, AnnotationDescriptor annotationDescriptor, boolean z) {
        if (JvmAnnotationNamesKt.getNULLABLE_ANNOTATIONS().contains(fqName)) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
        }
        if (JvmAnnotationNamesKt.getNOT_NULL_ANNOTATIONS().contains(fqName)) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION())) {
            return extractNullabilityTypeFromArgument(annotationDescriptor, z);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getCOMPATQUAL_NULLABLE_ANNOTATION()) && this.javaTypeEnhancementState.getEnableCompatqualCheckerFrameworkAnnotations()) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getCOMPATQUAL_NONNULL_ANNOTATION()) && this.javaTypeEnhancementState.getEnableCompatqualCheckerFrameworkAnnotations()) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NON_NULL_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, true);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NULLABLE_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, true);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <D extends CallableMemberDescriptor> Collection<D> enhanceSignatures(LazyJavaResolverContext c, Collection<? extends D> platformSignatures) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(platformSignatures, "platformSignatures");
        Collection<? extends D> collection = platformSignatures;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(enhanceSignature((CallableMemberDescriptor) it.next(), c));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final <D extends kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor> D enhanceSignature(D r17, kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r18) {
        /*
            Method dump skipped, instruction units count: 642
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.enhanceSignature(kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor, kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext):kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor");
    }

    public final List<KotlinType> enhanceTypeParameterBounds(TypeParameterDescriptor typeParameter, List<? extends KotlinType> bounds, LazyJavaResolverContext context) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        Intrinsics.checkNotNullParameter(context, "context");
        List<? extends KotlinType> list = bounds;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KotlinType type : list) {
            if (!TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceTypeParameterBounds$1$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Boolean invoke(UnwrappedType unwrappedType) {
                    return Boolean.valueOf(invoke2(unwrappedType));
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final boolean invoke2(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it instanceof RawType;
                }
            })) {
                type = SignatureParts.enhance$default(new SignatureParts(this, typeParameter, type, CollectionsKt.emptyList(), false, context, AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS, true), null, 1, null).getType();
            }
            arrayList.add(type);
        }
        return arrayList;
    }

    public final KotlinType enhanceSuperType(KotlinType type, LazyJavaResolverContext context) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        return SignatureParts.enhance$default(new SignatureParts(null, type, CollectionsKt.emptyList(), false, context, AnnotationQualifierApplicabilityType.TYPE_USE, false, 64, null), null, 1, null).getType();
    }

    private final boolean hasDefaultValueInAnnotation(ValueParameterDescriptor valueParameterDescriptor, KotlinType kotlinType) {
        boolean zDeclaresDefaultValue;
        AnnotationDefaultValue defaultValueFromAnnotation = UtilKt.getDefaultValueFromAnnotation(valueParameterDescriptor);
        if (defaultValueFromAnnotation instanceof StringDefaultValue) {
            zDeclaresDefaultValue = UtilsKt.lexicalCastFrom(kotlinType, ((StringDefaultValue) defaultValueFromAnnotation).getValue()) != null;
        } else if (Intrinsics.areEqual(defaultValueFromAnnotation, NullDefaultValue.INSTANCE)) {
            zDeclaresDefaultValue = TypeUtils.acceptsNullable(kotlinType);
        } else if (defaultValueFromAnnotation == null) {
            zDeclaresDefaultValue = valueParameterDescriptor.declaresDefaultValue();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return zDeclaresDefaultValue && valueParameterDescriptor.getOverriddenDescriptors().isEmpty();
    }

    /* JADX INFO: compiled from: signatureEnhancement.kt */
    private final class SignatureParts {
        private final AnnotationQualifierApplicabilityType containerApplicabilityType;
        private final LazyJavaResolverContext containerContext;
        private final Collection<KotlinType> fromOverridden;
        private final KotlinType fromOverride;
        private final boolean isCovariant;
        private final Annotated typeContainer;
        private final boolean typeParameterBounds;

        /* JADX WARN: Multi-variable type inference failed */
        public SignatureParts(SignatureEnhancement this$0, Annotated annotated, KotlinType fromOverride, Collection<? extends KotlinType> fromOverridden, boolean z, LazyJavaResolverContext containerContext, AnnotationQualifierApplicabilityType containerApplicabilityType, boolean z2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(fromOverride, "fromOverride");
            Intrinsics.checkNotNullParameter(fromOverridden, "fromOverridden");
            Intrinsics.checkNotNullParameter(containerContext, "containerContext");
            Intrinsics.checkNotNullParameter(containerApplicabilityType, "containerApplicabilityType");
            SignatureEnhancement.this = this$0;
            this.typeContainer = annotated;
            this.fromOverride = fromOverride;
            this.fromOverridden = fromOverridden;
            this.isCovariant = z;
            this.containerContext = containerContext;
            this.containerApplicabilityType = containerApplicabilityType;
            this.typeParameterBounds = z2;
        }

        public /* synthetic */ SignatureParts(Annotated annotated, KotlinType kotlinType, Collection collection, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(SignatureEnhancement.this, annotated, kotlinType, collection, z, lazyJavaResolverContext, annotationQualifierApplicabilityType, (i & 64) != 0 ? false : z2);
        }

        private final boolean isForVarargParameter() {
            Annotated annotated = this.typeContainer;
            if (!(annotated instanceof ValueParameterDescriptor)) {
                annotated = null;
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) annotated;
            return (valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null) != null;
        }

        public static /* synthetic */ PartEnhancementResult enhance$default(SignatureParts signatureParts, TypeEnhancementInfo typeEnhancementInfo, int i, Object obj) {
            if ((i & 1) != 0) {
                typeEnhancementInfo = null;
            }
            return signatureParts.enhance(typeEnhancementInfo);
        }

        public final PartEnhancementResult enhance(final TypeEnhancementInfo typeEnhancementInfo) {
            final Function1<Integer, JavaTypeQualifiers> function1ComputeIndexedQualifiersForOverride = computeIndexedQualifiersForOverride();
            Function1<Integer, JavaTypeQualifiers> function1 = typeEnhancementInfo == null ? null : new Function1<Integer, JavaTypeQualifiers>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$enhance$qualifiersWithPredefined$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ JavaTypeQualifiers invoke(Integer num) {
                    return invoke(num.intValue());
                }

                public final JavaTypeQualifiers invoke(int i) {
                    JavaTypeQualifiers javaTypeQualifiers = typeEnhancementInfo.getMap().get(Integer.valueOf(i));
                    return javaTypeQualifiers == null ? function1ComputeIndexedQualifiersForOverride.invoke(Integer.valueOf(i)) : javaTypeQualifiers;
                }
            };
            boolean zContains = TypeUtils.contains(this.fromOverride, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$enhance$containsFunctionN$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType unwrappedType) {
                    ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = unwrappedType.getConstructor().mo1677getDeclarationDescriptor();
                    boolean z = false;
                    if (classifierDescriptorMo1677getDeclarationDescriptor == null) {
                        return false;
                    }
                    if (Intrinsics.areEqual(classifierDescriptorMo1677getDeclarationDescriptor.getName(), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME().shortName()) && Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(classifierDescriptorMo1677getDeclarationDescriptor), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME())) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
            });
            JavaTypeEnhancement javaTypeEnhancement = SignatureEnhancement.this.typeEnhancement;
            KotlinType kotlinType = this.fromOverride;
            if (function1 != null) {
                function1ComputeIndexedQualifiersForOverride = function1;
            }
            KotlinType kotlinTypeEnhance = javaTypeEnhancement.enhance(kotlinType, function1ComputeIndexedQualifiersForOverride);
            PartEnhancementResult partEnhancementResult = kotlinTypeEnhance != null ? new PartEnhancementResult(kotlinTypeEnhance, true, zContains) : null;
            return partEnhancementResult == null ? new PartEnhancementResult(this.fromOverride, false, zContains) : partEnhancementResult;
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0048  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers extractQualifiers(kotlin.reflect.jvm.internal.impl.types.KotlinType r12) {
            /*
                r11 = this;
                boolean r0 = kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt.isFlexible(r12)
                if (r0 == 0) goto L18
                kotlin.reflect.jvm.internal.impl.types.FlexibleType r0 = kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt.asFlexibleType(r12)
                kotlin.Pair r1 = new kotlin.Pair
                kotlin.reflect.jvm.internal.impl.types.SimpleType r2 = r0.getLowerBound()
                kotlin.reflect.jvm.internal.impl.types.SimpleType r0 = r0.getUpperBound()
                r1.<init>(r2, r0)
                goto L1d
            L18:
                kotlin.Pair r1 = new kotlin.Pair
                r1.<init>(r12, r12)
            L1d:
                java.lang.Object r0 = r1.component1()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r0
                java.lang.Object r1 = r1.component2()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r1
                kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMapper r2 = kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMapper.INSTANCE
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r10 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers
                boolean r3 = r0.isMarkedNullable()
                r4 = 0
                if (r3 == 0) goto L38
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r3 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NULLABLE
            L36:
                r5 = r3
                goto L42
            L38:
                boolean r3 = r1.isMarkedNullable()
                if (r3 != 0) goto L41
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r3 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NOT_NULL
                goto L36
            L41:
                r5 = r4
            L42:
                boolean r0 = r2.isReadOnly(r0)
                if (r0 == 0) goto L4b
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.READ_ONLY
                goto L55
            L4b:
                boolean r0 = r2.isMutable(r1)
                if (r0 == 0) goto L54
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.MUTABLE
                goto L55
            L54:
                r0 = r4
            L55:
                kotlin.reflect.jvm.internal.impl.types.UnwrappedType r12 = r12.unwrap()
                boolean r6 = r12 instanceof kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NotNullTypeParameter
                r7 = 0
                r8 = 8
                r9 = 0
                r3 = r10
                r4 = r5
                r5 = r0
                r3.<init>(r4, r5, r6, r7, r8, r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.extractQualifiers(kotlin.reflect.jvm.internal.impl.types.KotlinType):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
        }

        /* JADX WARN: Removed duplicated region for block: B:33:0x009c  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00e3  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers extractQualifiersFromAnnotations(kotlin.reflect.jvm.internal.impl.types.KotlinType r11, boolean r12, kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers r13, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor r14, boolean r15) {
            /*
                Method dump skipped, instruction units count: 314
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.extractQualifiersFromAnnotations(kotlin.reflect.jvm.internal.impl.types.KotlinType, boolean, kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor, boolean):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
        }

        private static final <T> T extractQualifiersFromAnnotations$ifPresent(List<FqName> list, Annotations annotations, T t) {
            List<FqName> list2 = list;
            boolean z = true;
            if ((list2 instanceof Collection) && list2.isEmpty()) {
                z = false;
            } else {
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    if (annotations.mo1670findAnnotation((FqName) it.next()) != null) {
                        break;
                    }
                }
                z = false;
            }
            if (z) {
                return t;
            }
            return null;
        }

        private static final <T> T extractQualifiersFromAnnotations$uniqueNotNull(T t, T t2) {
            if (t == null || t2 == null || Intrinsics.areEqual(t, t2)) {
                return t == null ? t2 : t;
            }
            return null;
        }

        private final NullabilityQualifierWithMigrationStatus computeNullabilityInfoInTheAbsenceOfExplicitAnnotation(NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus, JavaDefaultQualifiers javaDefaultQualifiers, TypeParameterDescriptor typeParameterDescriptor) {
            NullabilityQualifierWithMigrationStatus nullabilityQualifier;
            if (nullabilityQualifierWithMigrationStatus == null) {
                nullabilityQualifierWithMigrationStatus = (javaDefaultQualifiers == null || (nullabilityQualifier = javaDefaultQualifiers.getNullabilityQualifier()) == null) ? null : new NullabilityQualifierWithMigrationStatus(nullabilityQualifier.getQualifier(), nullabilityQualifier.isForWarningOnly());
            }
            NullabilityQualifier nullabilityQualifierBoundsNullability = typeParameterDescriptor == null ? null : boundsNullability(typeParameterDescriptor);
            if (nullabilityQualifierBoundsNullability == null) {
                return nullabilityQualifierWithMigrationStatus;
            }
            if (nullabilityQualifierWithMigrationStatus == null) {
                return new NullabilityQualifierWithMigrationStatus(nullabilityQualifierBoundsNullability, false, 2, null);
            }
            return new NullabilityQualifierWithMigrationStatus(mostSpecific(nullabilityQualifierBoundsNullability, nullabilityQualifierWithMigrationStatus.getQualifier()), false, 2, null);
        }

        private final NullabilityQualifier mostSpecific(NullabilityQualifier nullabilityQualifier, NullabilityQualifier nullabilityQualifier2) {
            if (nullabilityQualifier == NullabilityQualifier.FORCE_FLEXIBILITY) {
                return nullabilityQualifier2;
            }
            if (nullabilityQualifier2 == NullabilityQualifier.FORCE_FLEXIBILITY) {
                return nullabilityQualifier;
            }
            if (nullabilityQualifier == NullabilityQualifier.NULLABLE) {
                return nullabilityQualifier2;
            }
            if (nullabilityQualifier2 == NullabilityQualifier.NULLABLE) {
                return nullabilityQualifier;
            }
            if (nullabilityQualifier == nullabilityQualifier2) {
                NullabilityQualifier nullabilityQualifier3 = NullabilityQualifier.NOT_NULL;
            }
            return NullabilityQualifier.NOT_NULL;
        }

        private final Pair<NullabilityQualifierWithMigrationStatus, Boolean> nullabilityInfoBoundsForTypeParameterUsage(KotlinType kotlinType) {
            ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = kotlinType.getConstructor().mo1677getDeclarationDescriptor();
            TypeParameterDescriptor typeParameterDescriptor = classifierDescriptorMo1677getDeclarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) classifierDescriptorMo1677getDeclarationDescriptor : null;
            NullabilityQualifier nullabilityQualifierBoundsNullability = typeParameterDescriptor == null ? null : boundsNullability(typeParameterDescriptor);
            if (nullabilityQualifierBoundsNullability == null) {
                return new Pair<>(null, false);
            }
            return new Pair<>(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null), Boolean.valueOf(nullabilityQualifierBoundsNullability == NullabilityQualifier.NOT_NULL));
        }

        private final NullabilityQualifier boundsNullability(TypeParameterDescriptor typeParameterDescriptor) {
            boolean z;
            boolean z2;
            if (!(typeParameterDescriptor instanceof LazyJavaTypeParameterDescriptor)) {
                return null;
            }
            LazyJavaTypeParameterDescriptor lazyJavaTypeParameterDescriptor = (LazyJavaTypeParameterDescriptor) typeParameterDescriptor;
            List<KotlinType> upperBounds = lazyJavaTypeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "upperBounds");
            List<KotlinType> list = upperBounds;
            boolean z3 = false;
            if ((list instanceof Collection) && list.isEmpty()) {
                z = true;
            } else {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (!KotlinTypeKt.isError((KotlinType) it.next())) {
                        z = false;
                        break;
                    }
                }
                z = true;
            }
            if (z) {
                return null;
            }
            List<KotlinType> upperBounds2 = lazyJavaTypeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds2, "upperBounds");
            List<KotlinType> list2 = upperBounds2;
            if ((list2 instanceof Collection) && list2.isEmpty()) {
                z2 = true;
            } else {
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    if (!SignatureEnhancementKt.isNullabilityFlexible((KotlinType) it2.next())) {
                        z2 = false;
                        break;
                    }
                }
                z2 = true;
            }
            if (z2) {
                return null;
            }
            List<KotlinType> upperBounds3 = lazyJavaTypeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds3, "upperBounds");
            List<KotlinType> list3 = upperBounds3;
            if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                Iterator<T> it3 = list3.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    KotlinType it4 = (KotlinType) it3.next();
                    Intrinsics.checkNotNullExpressionValue(it4, "it");
                    if (!KotlinTypeKt.isNullable(it4)) {
                        z3 = true;
                        break;
                    }
                }
            }
            return z3 ? NullabilityQualifier.NOT_NULL : NullabilityQualifier.NULLABLE;
        }

        private final NullabilityQualifierWithMigrationStatus extractNullability(Annotations annotations, boolean z, boolean z2) {
            SignatureEnhancement signatureEnhancement = SignatureEnhancement.this;
            Iterator<AnnotationDescriptor> it = annotations.iterator();
            while (it.hasNext()) {
                NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability = signatureEnhancement.extractNullability(it.next(), z, z2);
                if (nullabilityQualifierWithMigrationStatusExtractNullability != null) {
                    return nullabilityQualifierWithMigrationStatusExtractNullability;
                }
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x006c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final kotlin.jvm.functions.Function1<java.lang.Integer, kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers> computeIndexedQualifiersForOverride() {
            /*
                Method dump skipped, instruction units count: 221
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.computeIndexedQualifiersForOverride():kotlin.jvm.functions.Function1");
        }

        private final List<TypeAndDefaultQualifiers> toIndexed(KotlinType kotlinType) {
            ArrayList arrayList = new ArrayList(1);
            toIndexed$add(this, arrayList, kotlinType, this.containerContext, null);
            return arrayList;
        }

        private static final void toIndexed$add(SignatureParts signatureParts, ArrayList<TypeAndDefaultQualifiers> arrayList, KotlinType kotlinType, LazyJavaResolverContext lazyJavaResolverContext, TypeParameterDescriptor typeParameterDescriptor) {
            AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType;
            JavaDefaultQualifiers javaDefaultQualifiers;
            LazyJavaResolverContext lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, kotlinType.getAnnotations());
            JavaTypeQualifiersByElementType defaultTypeQualifiers = lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers.getDefaultTypeQualifiers();
            if (defaultTypeQualifiers == null) {
                javaDefaultQualifiers = null;
            } else {
                if (signatureParts.typeParameterBounds) {
                    annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS;
                } else {
                    annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.TYPE_USE;
                }
                javaDefaultQualifiers = defaultTypeQualifiers.get(annotationQualifierApplicabilityType);
            }
            arrayList.add(new TypeAndDefaultQualifiers(kotlinType, javaDefaultQualifiers, typeParameterDescriptor, false));
            List<TypeProjection> arguments = kotlinType.getArguments();
            List<TypeParameterDescriptor> parameters = kotlinType.getConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "type.constructor.parameters");
            for (Pair pair : CollectionsKt.zip(arguments, parameters)) {
                TypeProjection typeProjection = (TypeProjection) pair.component1();
                TypeParameterDescriptor typeParameterDescriptor2 = (TypeParameterDescriptor) pair.component2();
                if (typeProjection.isStarProjection()) {
                    KotlinType type = typeProjection.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "arg.type");
                    arrayList.add(new TypeAndDefaultQualifiers(type, javaDefaultQualifiers, typeParameterDescriptor2, true));
                } else {
                    KotlinType type2 = typeProjection.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "arg.type");
                    toIndexed$add(signatureParts, arrayList, type2, lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, typeParameterDescriptor2);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:70:0x013c  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x013e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers computeQualifiersForOverride(kotlin.reflect.jvm.internal.impl.types.KotlinType r10, java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.types.KotlinType> r11, kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers r12, boolean r13, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor r14, boolean r15) {
            /*
                Method dump skipped, instruction units count: 341
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.computeQualifiersForOverride(kotlin.reflect.jvm.internal.impl.types.KotlinType, java.util.Collection, kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers, boolean, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor, boolean):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
        }
    }

    /* JADX INFO: compiled from: signatureEnhancement.kt */
    private static class PartEnhancementResult {
        private final boolean containsFunctionN;
        private final KotlinType type;
        private final boolean wereChanges;

        public PartEnhancementResult(KotlinType type, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
            this.wereChanges = z;
            this.containsFunctionN = z2;
        }

        public final KotlinType getType() {
            return this.type;
        }

        public final boolean getWereChanges() {
            return this.wereChanges;
        }

        public final boolean getContainsFunctionN() {
            return this.containsFunctionN;
        }
    }

    /* JADX INFO: compiled from: signatureEnhancement.kt */
    private static final class ValueParameterEnhancementResult extends PartEnhancementResult {
        private final boolean hasDefaultValue;

        public final boolean getHasDefaultValue() {
            return this.hasDefaultValue;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueParameterEnhancementResult(KotlinType type, boolean z, boolean z2, boolean z3) {
            super(type, z2, z3);
            Intrinsics.checkNotNullParameter(type, "type");
            this.hasDefaultValue = z;
        }
    }

    private final SignatureParts partsForValueParameter(CallableMemberDescriptor callableMemberDescriptor, ValueParameterDescriptor valueParameterDescriptor, LazyJavaResolverContext lazyJavaResolverContext, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        LazyJavaResolverContext lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers;
        return parts(callableMemberDescriptor, valueParameterDescriptor, false, (valueParameterDescriptor == null || (lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, valueParameterDescriptor.getAnnotations())) == null) ? lazyJavaResolverContext : lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, AnnotationQualifierApplicabilityType.VALUE_PARAMETER, function1);
    }

    private final SignatureParts parts(CallableMemberDescriptor callableMemberDescriptor, Annotated annotated, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        KotlinType kotlinTypeInvoke = function1.invoke(callableMemberDescriptor);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "this.overriddenDescriptors");
        Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (CallableMemberDescriptor it : collection) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            arrayList.add(function1.invoke(it));
        }
        return new SignatureParts(annotated, kotlinTypeInvoke, arrayList, z, ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, function1.invoke(callableMemberDescriptor).getAnnotations()), annotationQualifierApplicabilityType, false, 64, null);
    }
}
