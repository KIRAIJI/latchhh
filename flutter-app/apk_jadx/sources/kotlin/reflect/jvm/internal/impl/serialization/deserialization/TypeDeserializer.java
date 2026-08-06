package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionForAbsentTypeParameter;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.Typography;

/* JADX INFO: compiled from: TypeDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class TypeDeserializer {
    private final DeserializationContext c;
    private final Function1<Integer, ClassifierDescriptor> classifierDescriptors;
    private final String containerPresentableName;
    private final String debugName;
    private boolean experimentalSuspendFunctionTypeEncountered;
    private final TypeDeserializer parent;
    private final Function1<Integer, ClassifierDescriptor> typeAliasDescriptors;
    private final Map<Integer, TypeParameterDescriptor> typeParameterDescriptors;

    public TypeDeserializer(DeserializationContext c, TypeDeserializer typeDeserializer, List<ProtoBuf.TypeParameter> typeParameterProtos, String debugName, String containerPresentableName, boolean z) {
        LinkedHashMap linkedHashMapEmptyMap;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeParameterProtos, "typeParameterProtos");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(containerPresentableName, "containerPresentableName");
        this.c = c;
        this.parent = typeDeserializer;
        this.debugName = debugName;
        this.containerPresentableName = containerPresentableName;
        this.experimentalSuspendFunctionTypeEncountered = z;
        this.classifierDescriptors = c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Integer, ClassifierDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$classifierDescriptors$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ ClassifierDescriptor invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final ClassifierDescriptor invoke(int i) {
                return this.this$0.computeClassifierDescriptor(i);
            }
        });
        this.typeAliasDescriptors = c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Integer, ClassifierDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeAliasDescriptors$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ ClassifierDescriptor invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final ClassifierDescriptor invoke(int i) {
                return this.this$0.computeTypeAliasDescriptor(i);
            }
        });
        if (typeParameterProtos.isEmpty()) {
            linkedHashMapEmptyMap = MapsKt.emptyMap();
        } else {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int i = 0;
            for (ProtoBuf.TypeParameter typeParameter : typeParameterProtos) {
                linkedHashMap.put(Integer.valueOf(typeParameter.getId()), new DeserializedTypeParameterDescriptor(this.c, typeParameter, i));
                i++;
            }
            linkedHashMapEmptyMap = linkedHashMap;
        }
        this.typeParameterDescriptors = linkedHashMapEmptyMap;
    }

    public /* synthetic */ TypeDeserializer(DeserializationContext deserializationContext, TypeDeserializer typeDeserializer, List list, String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(deserializationContext, typeDeserializer, list, str, str2, (i & 32) != 0 ? false : z);
    }

    public final boolean getExperimentalSuspendFunctionTypeEncountered() {
        return this.experimentalSuspendFunctionTypeEncountered;
    }

    public final List<TypeParameterDescriptor> getOwnTypeParameters() {
        return CollectionsKt.toList(this.typeParameterDescriptors.values());
    }

    public final KotlinType type(ProtoBuf.Type proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        if (proto.hasFlexibleTypeCapabilitiesId()) {
            String string = this.c.getNameResolver().getString(proto.getFlexibleTypeCapabilitiesId());
            SimpleType simpleTypeSimpleType$default = simpleType$default(this, proto, false, 2, null);
            ProtoBuf.Type typeFlexibleUpperBound = ProtoTypeTableUtilKt.flexibleUpperBound(proto, this.c.getTypeTable());
            Intrinsics.checkNotNull(typeFlexibleUpperBound);
            return this.c.getComponents().getFlexibleTypeDeserializer().create(proto, string, simpleTypeSimpleType$default, simpleType$default(this, typeFlexibleUpperBound, false, 2, null));
        }
        return simpleType(proto, true);
    }

    public static /* synthetic */ SimpleType simpleType$default(TypeDeserializer typeDeserializer, ProtoBuf.Type type, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return typeDeserializer.simpleType(type, z);
    }

    public final SimpleType simpleType(final ProtoBuf.Type proto, boolean z) {
        SimpleType simpleTypeComputeLocalClassifierReplacementType;
        SimpleType simpleTypeSimpleType$default;
        SimpleType simpleTypeWithAbbreviation;
        Intrinsics.checkNotNullParameter(proto, "proto");
        if (proto.hasClassName()) {
            simpleTypeComputeLocalClassifierReplacementType = computeLocalClassifierReplacementType(proto.getClassName());
        } else {
            simpleTypeComputeLocalClassifierReplacementType = proto.hasTypeAliasName() ? computeLocalClassifierReplacementType(proto.getTypeAliasName()) : null;
        }
        if (simpleTypeComputeLocalClassifierReplacementType != null) {
            return simpleTypeComputeLocalClassifierReplacementType;
        }
        TypeConstructor typeConstructor = typeConstructor(proto);
        if (ErrorUtils.isError(typeConstructor.mo1677getDeclarationDescriptor())) {
            SimpleType simpleTypeCreateErrorTypeWithCustomConstructor = ErrorUtils.createErrorTypeWithCustomConstructor(typeConstructor.toString(), typeConstructor);
            Intrinsics.checkNotNullExpressionValue(simpleTypeCreateErrorTypeWithCustomConstructor, "createErrorTypeWithCustomConstructor(constructor.toString(), constructor)");
            return simpleTypeCreateErrorTypeWithCustomConstructor;
        }
        DeserializedAnnotations deserializedAnnotations = new DeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$simpleType$annotations$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AnnotationDescriptor> invoke() {
                return this.this$0.c.getComponents().getAnnotationAndConstantLoader().loadTypeAnnotations(proto, this.this$0.c.getNameResolver());
            }
        });
        List<ProtoBuf.Type.Argument> listSimpleType$collectAllArguments = simpleType$collectAllArguments(proto, this);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSimpleType$collectAllArguments, 10));
        int i = 0;
        for (Object obj : listSimpleType$collectAllArguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "constructor.parameters");
            arrayList.add(typeArgument((TypeParameterDescriptor) CollectionsKt.getOrNull(parameters, i), (ProtoBuf.Type.Argument) obj));
            i = i2;
        }
        List<? extends TypeProjection> list = CollectionsKt.toList(arrayList);
        ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = typeConstructor.mo1677getDeclarationDescriptor();
        if (z && (classifierDescriptorMo1677getDeclarationDescriptor instanceof TypeAliasDescriptor)) {
            KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
            SimpleType simpleTypeComputeExpandedType = KotlinTypeFactory.computeExpandedType((TypeAliasDescriptor) classifierDescriptorMo1677getDeclarationDescriptor, list);
            simpleTypeSimpleType$default = simpleTypeComputeExpandedType.makeNullableAsSpecified(KotlinTypeKt.isNullable(simpleTypeComputeExpandedType) || proto.getNullable()).replaceAnnotations(Annotations.Companion.create(CollectionsKt.plus((Iterable) deserializedAnnotations, (Iterable) simpleTypeComputeExpandedType.getAnnotations())));
        } else {
            Boolean bool = Flags.SUSPEND_TYPE.get(proto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "SUSPEND_TYPE.get(proto.flags)");
            if (bool.booleanValue()) {
                simpleTypeSimpleType$default = createSuspendFunctionType(deserializedAnnotations, typeConstructor, list, proto.getNullable());
            } else {
                KotlinTypeFactory kotlinTypeFactory2 = KotlinTypeFactory.INSTANCE;
                simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(deserializedAnnotations, typeConstructor, list, proto.getNullable(), null, 16, null);
            }
        }
        ProtoBuf.Type typeAbbreviatedType = ProtoTypeTableUtilKt.abbreviatedType(proto, this.c.getTypeTable());
        if (typeAbbreviatedType != null && (simpleTypeWithAbbreviation = SpecialTypesKt.withAbbreviation(simpleTypeSimpleType$default, simpleType(typeAbbreviatedType, false))) != null) {
            simpleTypeSimpleType$default = simpleTypeWithAbbreviation;
        }
        return proto.hasClassName() ? this.c.getComponents().getPlatformDependentTypeTransformer().transformPlatformType(NameResolverUtilKt.getClassId(this.c.getNameResolver(), proto.getClassName()), simpleTypeSimpleType$default) : simpleTypeSimpleType$default;
    }

    private static final List<ProtoBuf.Type.Argument> simpleType$collectAllArguments(ProtoBuf.Type type, TypeDeserializer typeDeserializer) {
        List<ProtoBuf.Type.Argument> argumentList = type.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "argumentList");
        List<ProtoBuf.Type.Argument> list = argumentList;
        ProtoBuf.Type typeOuterType = ProtoTypeTableUtilKt.outerType(type, typeDeserializer.c.getTypeTable());
        List<ProtoBuf.Type.Argument> listSimpleType$collectAllArguments = typeOuterType == null ? null : simpleType$collectAllArguments(typeOuterType, typeDeserializer);
        if (listSimpleType$collectAllArguments == null) {
            listSimpleType$collectAllArguments = CollectionsKt.emptyList();
        }
        return CollectionsKt.plus((Collection) list, (Iterable) listSimpleType$collectAllArguments);
    }

    private static final ClassDescriptor typeConstructor$notFoundClass(final TypeDeserializer typeDeserializer, ProtoBuf.Type type, int i) {
        ClassId classId = NameResolverUtilKt.getClassId(typeDeserializer.c.getNameResolver(), i);
        List<Integer> mutableList = SequencesKt.toMutableList(SequencesKt.map(SequencesKt.generateSequence(type, new Function1<ProtoBuf.Type, ProtoBuf.Type>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$notFoundClass$typeParametersCount$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ProtoBuf.Type invoke(ProtoBuf.Type it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ProtoTypeTableUtilKt.outerType(it, this.this$0.c.getTypeTable());
            }
        }), new Function1<ProtoBuf.Type, Integer>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$notFoundClass$typeParametersCount$2
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final int invoke2(ProtoBuf.Type it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getArgumentCount();
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Integer invoke(ProtoBuf.Type type2) {
                return Integer.valueOf(invoke2(type2));
            }
        }));
        int iCount = SequencesKt.count(SequencesKt.generateSequence(classId, TypeDeserializer$typeConstructor$notFoundClass$classNestingLevel$1.INSTANCE));
        while (mutableList.size() < iCount) {
            mutableList.add(0);
        }
        return typeDeserializer.c.getComponents().getNotFoundClasses().getClass(classId, mutableList);
    }

    private final TypeConstructor typeConstructor(ProtoBuf.Type type) {
        Object next;
        TypeConstructor typeConstructorCreateErrorTypeConstructor;
        if (type.hasClassName()) {
            ClassDescriptor classDescriptorInvoke = this.classifierDescriptors.invoke(Integer.valueOf(type.getClassName()));
            if (classDescriptorInvoke == null) {
                classDescriptorInvoke = typeConstructor$notFoundClass(this, type, type.getClassName());
            }
            TypeConstructor typeConstructor = classDescriptorInvoke.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor, "classifierDescriptors(proto.className) ?: notFoundClass(proto.className)).typeConstructor");
            return typeConstructor;
        }
        if (type.hasTypeParameter()) {
            TypeConstructor typeConstructorTypeParameterTypeConstructor = typeParameterTypeConstructor(type.getTypeParameter());
            if (typeConstructorTypeParameterTypeConstructor != null) {
                return typeConstructorTypeParameterTypeConstructor;
            }
            TypeConstructor typeConstructorCreateErrorTypeConstructor2 = ErrorUtils.createErrorTypeConstructor("Unknown type parameter " + type.getTypeParameter() + ". Please try recompiling module containing \"" + this.containerPresentableName + Typography.quote);
            Intrinsics.checkNotNullExpressionValue(typeConstructorCreateErrorTypeConstructor2, "createErrorTypeConstructor(\n                        \"Unknown type parameter ${proto.typeParameter}. Please try recompiling module containing \\\"$containerPresentableName\\\"\"\n                    )");
            return typeConstructorCreateErrorTypeConstructor2;
        }
        if (type.hasTypeParameterName()) {
            DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
            String string = this.c.getNameResolver().getString(type.getTypeParameterName());
            Iterator<T> it = getOwnTypeParameters().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (Intrinsics.areEqual(((TypeParameterDescriptor) next).getName().asString(), string)) {
                    break;
                }
            }
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) next;
            TypeConstructor typeConstructor2 = typeParameterDescriptor != null ? typeParameterDescriptor.getTypeConstructor() : null;
            if (typeConstructor2 == null) {
                typeConstructorCreateErrorTypeConstructor = ErrorUtils.createErrorTypeConstructor("Deserialized type parameter " + string + " in " + containingDeclaration);
            } else {
                typeConstructorCreateErrorTypeConstructor = typeConstructor2;
            }
            Intrinsics.checkNotNullExpressionValue(typeConstructorCreateErrorTypeConstructor, "{\n                val container = c.containingDeclaration\n                val name = c.nameResolver.getString(proto.typeParameterName)\n                val parameter = ownTypeParameters.find { it.name.asString() == name }\n                parameter?.typeConstructor ?: ErrorUtils.createErrorTypeConstructor(\"Deserialized type parameter $name in $container\")\n            }");
            return typeConstructorCreateErrorTypeConstructor;
        }
        if (!type.hasTypeAliasName()) {
            TypeConstructor typeConstructorCreateErrorTypeConstructor3 = ErrorUtils.createErrorTypeConstructor("Unknown type");
            Intrinsics.checkNotNullExpressionValue(typeConstructorCreateErrorTypeConstructor3, "createErrorTypeConstructor(\"Unknown type\")");
            return typeConstructorCreateErrorTypeConstructor3;
        }
        ClassDescriptor classDescriptorInvoke2 = this.typeAliasDescriptors.invoke(Integer.valueOf(type.getTypeAliasName()));
        if (classDescriptorInvoke2 == null) {
            classDescriptorInvoke2 = typeConstructor$notFoundClass(this, type, type.getTypeAliasName());
        }
        TypeConstructor typeConstructor3 = classDescriptorInvoke2.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor3, "typeAliasDescriptors(proto.typeAliasName) ?: notFoundClass(proto.typeAliasName)).typeConstructor");
        return typeConstructor3;
    }

    private final SimpleType createSuspendFunctionType(Annotations annotations, TypeConstructor typeConstructor, List<? extends TypeProjection> list, boolean z) {
        int size = typeConstructor.getParameters().size() - list.size();
        SimpleType simpleTypeCreateSuspendFunctionTypeForBasicCase = null;
        if (size == 0) {
            simpleTypeCreateSuspendFunctionTypeForBasicCase = createSuspendFunctionTypeForBasicCase(annotations, typeConstructor, list, z);
        } else if (size == 1) {
            int size2 = list.size() - 1;
            if (size2 >= 0) {
                KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
                TypeConstructor typeConstructor2 = typeConstructor.getBuiltIns().getSuspendFunction(size2).getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor2, "functionTypeConstructor.builtIns.getSuspendFunction(arity).typeConstructor");
                simpleTypeCreateSuspendFunctionTypeForBasicCase = KotlinTypeFactory.simpleType$default(annotations, typeConstructor2, list, z, null, 16, null);
            } else {
                simpleTypeCreateSuspendFunctionTypeForBasicCase = (SimpleType) null;
            }
        }
        if (simpleTypeCreateSuspendFunctionTypeForBasicCase != null) {
            return simpleTypeCreateSuspendFunctionTypeForBasicCase;
        }
        SimpleType simpleTypeCreateErrorTypeWithArguments = ErrorUtils.createErrorTypeWithArguments(Intrinsics.stringPlus("Bad suspend function in metadata with constructor: ", typeConstructor), list);
        Intrinsics.checkNotNullExpressionValue(simpleTypeCreateErrorTypeWithArguments, "createErrorTypeWithArguments(\n            \"Bad suspend function in metadata with constructor: $functionTypeConstructor\",\n            arguments\n        )");
        return simpleTypeCreateErrorTypeWithArguments;
    }

    private final SimpleType createSuspendFunctionTypeForBasicCase(Annotations annotations, TypeConstructor typeConstructor, List<? extends TypeProjection> list, boolean z) {
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        SimpleType simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(annotations, typeConstructor, list, z, null, 16, null);
        if (FunctionTypesKt.isFunctionType(simpleTypeSimpleType$default)) {
            return transformRuntimeFunctionTypeToSuspendFunction(simpleTypeSimpleType$default);
        }
        return null;
    }

    private final SimpleType transformRuntimeFunctionTypeToSuspendFunction(KotlinType kotlinType) {
        boolean releaseCoroutines = this.c.getComponents().getConfiguration().getReleaseCoroutines();
        TypeProjection typeProjection = (TypeProjection) CollectionsKt.lastOrNull((List) FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType));
        KotlinType type = typeProjection == null ? null : typeProjection.getType();
        if (type == null) {
            return null;
        }
        ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = type.getConstructor().mo1677getDeclarationDescriptor();
        FqName fqNameSafe = classifierDescriptorMo1677getDeclarationDescriptor == null ? null : DescriptorUtilsKt.getFqNameSafe(classifierDescriptorMo1677getDeclarationDescriptor);
        boolean z = true;
        if (type.getArguments().size() != 1 || (!SuspendFunctionTypesKt.isContinuation(fqNameSafe, true) && !SuspendFunctionTypesKt.isContinuation(fqNameSafe, false))) {
            return (SimpleType) kotlinType;
        }
        KotlinType type2 = ((TypeProjection) CollectionsKt.single((List) type.getArguments())).getType();
        Intrinsics.checkNotNullExpressionValue(type2, "continuationArgumentType.arguments.single().type");
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        if (!(containingDeclaration instanceof CallableDescriptor)) {
            containingDeclaration = null;
        }
        CallableDescriptor callableDescriptor = (CallableDescriptor) containingDeclaration;
        if (Intrinsics.areEqual(callableDescriptor != null ? DescriptorUtilsKt.fqNameOrNull(callableDescriptor) : null, SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
            return createSimpleSuspendFunctionType(kotlinType, type2);
        }
        if (!this.experimentalSuspendFunctionTypeEncountered && (!releaseCoroutines || !SuspendFunctionTypesKt.isContinuation(fqNameSafe, !releaseCoroutines))) {
            z = false;
        }
        this.experimentalSuspendFunctionTypeEncountered = z;
        return createSimpleSuspendFunctionType(kotlinType, type2);
    }

    private final SimpleType createSimpleSuspendFunctionType(KotlinType kotlinType, KotlinType kotlinType2) {
        KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(kotlinType);
        Annotations annotations = kotlinType.getAnnotations();
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(kotlinType);
        List listDropLast = CollectionsKt.dropLast(FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getType());
        }
        return FunctionTypesKt.createFunctionType(builtIns, annotations, receiverTypeFromFunctionType, arrayList, null, kotlinType2, true).makeNullableAsSpecified(kotlinType.isMarkedNullable());
    }

    private final TypeConstructor typeParameterTypeConstructor(int i) {
        TypeParameterDescriptor typeParameterDescriptor = this.typeParameterDescriptors.get(Integer.valueOf(i));
        TypeConstructor typeConstructor = typeParameterDescriptor == null ? null : typeParameterDescriptor.getTypeConstructor();
        if (typeConstructor != null) {
            return typeConstructor;
        }
        TypeDeserializer typeDeserializer = this.parent;
        if (typeDeserializer == null) {
            return null;
        }
        return typeDeserializer.typeParameterTypeConstructor(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassifierDescriptor computeClassifierDescriptor(int i) {
        ClassId classId = NameResolverUtilKt.getClassId(this.c.getNameResolver(), i);
        if (classId.isLocal()) {
            return this.c.getComponents().deserializeClass(classId);
        }
        return FindClassInModuleKt.findClassifierAcrossModuleDependencies(this.c.getComponents().getModuleDescriptor(), classId);
    }

    private final SimpleType computeLocalClassifierReplacementType(int i) {
        if (NameResolverUtilKt.getClassId(this.c.getNameResolver(), i).isLocal()) {
            return this.c.getComponents().getLocalClassifierTypeSettings().getReplacementTypeForLocalClassifiers();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassifierDescriptor computeTypeAliasDescriptor(int i) {
        ClassId classId = NameResolverUtilKt.getClassId(this.c.getNameResolver(), i);
        if (classId.isLocal()) {
            return null;
        }
        return FindClassInModuleKt.findTypeAliasAcrossModuleDependencies(this.c.getComponents().getModuleDescriptor(), classId);
    }

    private final TypeProjection typeArgument(TypeParameterDescriptor typeParameterDescriptor, ProtoBuf.Type.Argument argument) {
        if (argument.getProjection() == ProtoBuf.Type.Argument.Projection.STAR) {
            if (typeParameterDescriptor == null) {
                return new StarProjectionForAbsentTypeParameter(this.c.getComponents().getModuleDescriptor().getBuiltIns());
            }
            return new StarProjectionImpl(typeParameterDescriptor);
        }
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        ProtoBuf.Type.Argument.Projection projection = argument.getProjection();
        Intrinsics.checkNotNullExpressionValue(projection, "typeArgumentProto.projection");
        Variance variance = protoEnumFlags.variance(projection);
        ProtoBuf.Type type = ProtoTypeTableUtilKt.type(argument, this.c.getTypeTable());
        return type == null ? new TypeProjectionImpl(ErrorUtils.createErrorType("No type recorded")) : new TypeProjectionImpl(variance, type(type));
    }

    public String toString() {
        String str = this.debugName;
        TypeDeserializer typeDeserializer = this.parent;
        return Intrinsics.stringPlus(str, typeDeserializer == null ? "" : Intrinsics.stringPlus(". Child of ", typeDeserializer.debugName));
    }
}
