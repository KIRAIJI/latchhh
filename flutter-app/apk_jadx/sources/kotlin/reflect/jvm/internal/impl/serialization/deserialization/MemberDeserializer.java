package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FieldDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoContainer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedTypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.NonEmptyDeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: MemberDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class MemberDeserializer {
    private final AnnotationDeserializer annotationDeserializer;
    private final DeserializationContext c;

    private final int loadOldFlags(int i) {
        return (i & 63) + ((i >> 8) << 6);
    }

    public MemberDeserializer(DeserializationContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
        this.annotationDeserializer = new AnnotationDeserializer(c.getComponents().getModuleDescriptor(), c.getComponents().getNotFoundClasses());
    }

    public final PropertyDescriptor loadProperty(final ProtoBuf.Property proto) {
        ProtoBuf.Property property;
        Annotations empty;
        KotlinType kotlinTypeType;
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl;
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl2;
        boolean z;
        PropertySetterDescriptorImpl propertySetterDescriptorImplCreateDefaultSetter;
        PropertyGetterDescriptorImpl propertyGetterDescriptorImplCreateDefaultGetter;
        Intrinsics.checkNotNullParameter(proto, "proto");
        int flags = proto.hasFlags() ? proto.getFlags() : loadOldFlags(proto.getOldFlags());
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        ProtoBuf.Property property2 = proto;
        Annotations annotations = getAnnotations(property2, flags, AnnotatedCallableKind.PROPERTY);
        Modality modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags));
        DescriptorVisibility descriptorVisibility = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(flags));
        Boolean bool = Flags.IS_VAR.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "IS_VAR.get(flags)");
        boolean zBooleanValue = bool.booleanValue();
        Name name = NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName());
        CallableMemberDescriptor.Kind kindMemberKind = ProtoEnumFlagsUtilsKt.memberKind(ProtoEnumFlags.INSTANCE, Flags.MEMBER_KIND.get(flags));
        Boolean bool2 = Flags.IS_LATEINIT.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool2, "IS_LATEINIT.get(flags)");
        boolean zBooleanValue2 = bool2.booleanValue();
        Boolean bool3 = Flags.IS_CONST.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool3, "IS_CONST.get(flags)");
        boolean zBooleanValue3 = bool3.booleanValue();
        Boolean bool4 = Flags.IS_EXTERNAL_PROPERTY.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool4, "IS_EXTERNAL_PROPERTY.get(flags)");
        boolean zBooleanValue4 = bool4.booleanValue();
        Boolean bool5 = Flags.IS_DELEGATED.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool5, "IS_DELEGATED.get(flags)");
        boolean zBooleanValue5 = bool5.booleanValue();
        Boolean bool6 = Flags.IS_EXPECT_PROPERTY.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool6, "IS_EXPECT_PROPERTY.get(flags)");
        final DeserializedPropertyDescriptor deserializedPropertyDescriptor = new DeserializedPropertyDescriptor(containingDeclaration, null, annotations, modality, descriptorVisibility, zBooleanValue, name, kindMemberKind, zBooleanValue2, zBooleanValue3, zBooleanValue4, zBooleanValue5, bool6.booleanValue(), proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "proto.typeParameterList");
        DeserializationContext deserializationContextChildContext$default = DeserializationContext.childContext$default(this.c, deserializedPropertyDescriptor, typeParameterList, null, null, null, null, 60, null);
        Boolean bool7 = Flags.HAS_GETTER.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool7, "HAS_GETTER.get(flags)");
        boolean zBooleanValue6 = bool7.booleanValue();
        if (zBooleanValue6 && ProtoTypeTableUtilKt.hasReceiver(proto)) {
            property = property2;
            empty = getReceiverParameterAnnotations(property, AnnotatedCallableKind.PROPERTY_GETTER);
        } else {
            property = property2;
            empty = Annotations.Companion.getEMPTY();
        }
        KotlinType kotlinTypeType2 = deserializationContextChildContext$default.getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(proto, this.c.getTypeTable()));
        List<TypeParameterDescriptor> ownTypeParameters = deserializationContextChildContext$default.getTypeDeserializer().getOwnTypeParameters();
        ReceiverParameterDescriptor dispatchReceiverParameter = getDispatchReceiverParameter();
        ProtoBuf.Type typeReceiverType = ProtoTypeTableUtilKt.receiverType(proto, this.c.getTypeTable());
        deserializedPropertyDescriptor.setType(kotlinTypeType2, ownTypeParameters, dispatchReceiverParameter, (typeReceiverType == null || (kotlinTypeType = deserializationContextChildContext$default.getTypeDeserializer().type(typeReceiverType)) == null) ? null : DescriptorFactory.createExtensionReceiverParameterForCallable(deserializedPropertyDescriptor, kotlinTypeType, empty));
        Boolean bool8 = Flags.HAS_ANNOTATIONS.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool8, "HAS_ANNOTATIONS.get(flags)");
        int accessorFlags = Flags.getAccessorFlags(bool8.booleanValue(), Flags.VISIBILITY.get(flags), Flags.MODALITY.get(flags), false, false, false);
        if (zBooleanValue6) {
            int getterFlags = proto.hasGetterFlags() ? proto.getGetterFlags() : accessorFlags;
            Boolean bool9 = Flags.IS_NOT_DEFAULT.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(bool9, "IS_NOT_DEFAULT.get(getterFlags)");
            boolean zBooleanValue7 = bool9.booleanValue();
            Boolean bool10 = Flags.IS_EXTERNAL_ACCESSOR.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(bool10, "IS_EXTERNAL_ACCESSOR.get(getterFlags)");
            boolean zBooleanValue8 = bool10.booleanValue();
            Boolean bool11 = Flags.IS_INLINE_ACCESSOR.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(bool11, "IS_INLINE_ACCESSOR.get(getterFlags)");
            boolean zBooleanValue9 = bool11.booleanValue();
            Annotations annotations2 = getAnnotations(property, getterFlags, AnnotatedCallableKind.PROPERTY_GETTER);
            if (zBooleanValue7) {
                propertyGetterDescriptorImplCreateDefaultGetter = new PropertyGetterDescriptorImpl(deserializedPropertyDescriptor, annotations2, ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(getterFlags)), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(getterFlags)), !zBooleanValue7, zBooleanValue8, zBooleanValue9, deserializedPropertyDescriptor.getKind(), null, SourceElement.NO_SOURCE);
            } else {
                propertyGetterDescriptorImplCreateDefaultGetter = DescriptorFactory.createDefaultGetter(deserializedPropertyDescriptor, annotations2);
                Intrinsics.checkNotNullExpressionValue(propertyGetterDescriptorImplCreateDefaultGetter, "{\n                DescriptorFactory.createDefaultGetter(property, annotations)\n            }");
            }
            propertyGetterDescriptorImplCreateDefaultGetter.initialize(deserializedPropertyDescriptor.getReturnType());
            propertyGetterDescriptorImpl = propertyGetterDescriptorImplCreateDefaultGetter;
        } else {
            propertyGetterDescriptorImpl = (PropertyGetterDescriptorImpl) null;
        }
        Boolean bool12 = Flags.HAS_SETTER.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool12, "HAS_SETTER.get(flags)");
        if (bool12.booleanValue()) {
            if (proto.hasSetterFlags()) {
                accessorFlags = proto.getSetterFlags();
            }
            Boolean bool13 = Flags.IS_NOT_DEFAULT.get(accessorFlags);
            Intrinsics.checkNotNullExpressionValue(bool13, "IS_NOT_DEFAULT.get(setterFlags)");
            boolean zBooleanValue10 = bool13.booleanValue();
            Boolean bool14 = Flags.IS_EXTERNAL_ACCESSOR.get(accessorFlags);
            Intrinsics.checkNotNullExpressionValue(bool14, "IS_EXTERNAL_ACCESSOR.get(setterFlags)");
            boolean zBooleanValue11 = bool14.booleanValue();
            Boolean bool15 = Flags.IS_INLINE_ACCESSOR.get(accessorFlags);
            Intrinsics.checkNotNullExpressionValue(bool15, "IS_INLINE_ACCESSOR.get(setterFlags)");
            boolean zBooleanValue12 = bool15.booleanValue();
            Annotations annotations3 = getAnnotations(property, accessorFlags, AnnotatedCallableKind.PROPERTY_SETTER);
            if (zBooleanValue10) {
                PropertySetterDescriptorImpl propertySetterDescriptorImpl = new PropertySetterDescriptorImpl(deserializedPropertyDescriptor, annotations3, ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(accessorFlags)), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(accessorFlags)), !zBooleanValue10, zBooleanValue11, zBooleanValue12, deserializedPropertyDescriptor.getKind(), null, SourceElement.NO_SOURCE);
                propertyGetterDescriptorImpl2 = propertyGetterDescriptorImpl;
                z = true;
                propertySetterDescriptorImpl.initialize((ValueParameterDescriptor) CollectionsKt.single((List) DeserializationContext.childContext$default(deserializationContextChildContext$default, propertySetterDescriptorImpl, CollectionsKt.emptyList(), null, null, null, null, 60, null).getMemberDeserializer().valueParameters(CollectionsKt.listOf(proto.getSetterValueParameter()), property, AnnotatedCallableKind.PROPERTY_SETTER)));
                propertySetterDescriptorImplCreateDefaultSetter = propertySetterDescriptorImpl;
            } else {
                propertyGetterDescriptorImpl2 = propertyGetterDescriptorImpl;
                z = true;
                propertySetterDescriptorImplCreateDefaultSetter = DescriptorFactory.createDefaultSetter(deserializedPropertyDescriptor, annotations3, Annotations.Companion.getEMPTY());
                Intrinsics.checkNotNullExpressionValue(propertySetterDescriptorImplCreateDefaultSetter, "{\n                DescriptorFactory.createDefaultSetter(\n                    property, annotations,\n                    Annotations.EMPTY /* Otherwise the setter is not default, see DescriptorResolver.resolvePropertySetterDescriptor */\n                )\n            }");
            }
        } else {
            propertyGetterDescriptorImpl2 = propertyGetterDescriptorImpl;
            z = true;
            propertySetterDescriptorImplCreateDefaultSetter = (PropertySetterDescriptorImpl) null;
        }
        Boolean bool16 = Flags.HAS_CONSTANT.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool16, "HAS_CONSTANT.get(flags)");
        if (bool16.booleanValue()) {
            deserializedPropertyDescriptor.setCompileTimeInitializer(this.c.getStorageManager().createNullableLazyValue(new Function0<ConstantValue<?>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.loadProperty.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ConstantValue<?> invoke() {
                    MemberDeserializer memberDeserializer = MemberDeserializer.this;
                    ProtoContainer protoContainerAsProtoContainer = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                    Intrinsics.checkNotNull(protoContainerAsProtoContainer);
                    AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> annotationAndConstantLoader = MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader();
                    ProtoBuf.Property property3 = proto;
                    KotlinType returnType = deserializedPropertyDescriptor.getReturnType();
                    Intrinsics.checkNotNullExpressionValue(returnType, "property.returnType");
                    return annotationAndConstantLoader.loadPropertyConstant(protoContainerAsProtoContainer, property3, returnType);
                }
            }));
        }
        DeserializedPropertyDescriptor deserializedPropertyDescriptor2 = deserializedPropertyDescriptor;
        deserializedPropertyDescriptor.initialize(propertyGetterDescriptorImpl2, propertySetterDescriptorImplCreateDefaultSetter, new FieldDescriptorImpl(getPropertyFieldAnnotations(proto, false), deserializedPropertyDescriptor2), new FieldDescriptorImpl(getPropertyFieldAnnotations(proto, z), deserializedPropertyDescriptor2), checkExperimentalCoroutine(deserializedPropertyDescriptor, deserializationContextChildContext$default.getTypeDeserializer()));
        return deserializedPropertyDescriptor2;
    }

    private final DeserializedMemberDescriptor.CoroutinesCompatibilityMode checkExperimentalCoroutine(DeserializedMemberDescriptor deserializedMemberDescriptor, TypeDeserializer typeDeserializer) {
        if (!versionAndReleaseCoroutinesMismatch(deserializedMemberDescriptor)) {
            return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
        }
        forceUpperBoundsComputation(typeDeserializer);
        if (typeDeserializer.getExperimentalSuspendFunctionTypeEncountered()) {
            return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
        }
        return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }

    private final void forceUpperBoundsComputation(TypeDeserializer typeDeserializer) {
        Iterator<T> it = typeDeserializer.getOwnTypeParameters().iterator();
        while (it.hasNext()) {
            ((TypeParameterDescriptor) it.next()).getUpperBounds();
        }
    }

    private final void initializeWithCoroutinesExperimentalityStatus(DeserializedSimpleFunctionDescriptor deserializedSimpleFunctionDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor2, List<? extends TypeParameterDescriptor> list, List<? extends ValueParameterDescriptor> list2, KotlinType kotlinType, Modality modality, DescriptorVisibility descriptorVisibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> map, boolean z) {
        deserializedSimpleFunctionDescriptor.initialize(receiverParameterDescriptor, receiverParameterDescriptor2, list, list2, kotlinType, modality, descriptorVisibility, map, computeExperimentalityModeForFunctions(deserializedSimpleFunctionDescriptor, receiverParameterDescriptor, list2, list, kotlinType, z));
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00ce A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[LOOP:3: B:32:0x008a->B:97:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor.CoroutinesCompatibilityMode computeExperimentalityModeForFunctions(kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor r4, kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor r5, java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor> r6, java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor> r7, kotlin.reflect.jvm.internal.impl.types.KotlinType r8, boolean r9) {
        /*
            Method dump skipped, instruction units count: 371
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.computeExperimentalityModeForFunctions(kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor, java.util.Collection, java.util.Collection, kotlin.reflect.jvm.internal.impl.types.KotlinType, boolean):kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor$CoroutinesCompatibilityMode");
    }

    private final boolean containsSuspendFunctionType(KotlinType kotlinType) {
        return TypeUtilsKt.contains(kotlinType, new PropertyReference1() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.containsSuspendFunctionType.1
            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "isSuspendFunctionType";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "isSuspendFunctionType(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(FunctionTypesKt.isSuspendFunctionType((KotlinType) obj));
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinPackage(FunctionTypesKt.class, "deserialization");
            }
        });
    }

    private final boolean versionAndReleaseCoroutinesMismatch(DeserializedMemberDescriptor deserializedMemberDescriptor) {
        boolean z;
        if (!this.c.getComponents().getConfiguration().getReleaseCoroutines()) {
            return false;
        }
        List<VersionRequirement> versionRequirements = deserializedMemberDescriptor.getVersionRequirements();
        if ((versionRequirements instanceof Collection) && versionRequirements.isEmpty()) {
            z = true;
        } else {
            for (VersionRequirement versionRequirement : versionRequirements) {
                if (Intrinsics.areEqual(versionRequirement.getVersion(), new VersionRequirement.Version(1, 3, 0, 4, null)) && versionRequirement.getKind() == ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION) {
                    z = false;
                    break;
                }
            }
            z = true;
        }
        return z;
    }

    public final SimpleFunctionDescriptor loadFunction(ProtoBuf.Function proto) {
        Annotations empty;
        VersionRequirementTable versionRequirementTable;
        KotlinType kotlinTypeType;
        Intrinsics.checkNotNullParameter(proto, "proto");
        int flags = proto.hasFlags() ? proto.getFlags() : loadOldFlags(proto.getOldFlags());
        ProtoBuf.Function function = proto;
        Annotations annotations = getAnnotations(function, flags, AnnotatedCallableKind.FUNCTION);
        if (ProtoTypeTableUtilKt.hasReceiver(proto)) {
            empty = getReceiverParameterAnnotations(function, AnnotatedCallableKind.FUNCTION);
        } else {
            empty = Annotations.Companion.getEMPTY();
        }
        if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(this.c.getContainingDeclaration()).child(NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName())), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
            versionRequirementTable = VersionRequirementTable.Companion.getEMPTY();
        } else {
            versionRequirementTable = this.c.getVersionRequirementTable();
        }
        DeserializedSimpleFunctionDescriptor deserializedSimpleFunctionDescriptor = new DeserializedSimpleFunctionDescriptor(this.c.getContainingDeclaration(), null, annotations, NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName()), ProtoEnumFlagsUtilsKt.memberKind(ProtoEnumFlags.INSTANCE, Flags.MEMBER_KIND.get(flags)), proto, this.c.getNameResolver(), this.c.getTypeTable(), versionRequirementTable, this.c.getContainerSource(), null, 1024, null);
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "proto.typeParameterList");
        DeserializationContext deserializationContextChildContext$default = DeserializationContext.childContext$default(this.c, deserializedSimpleFunctionDescriptor, typeParameterList, null, null, null, null, 60, null);
        ProtoBuf.Type typeReceiverType = ProtoTypeTableUtilKt.receiverType(proto, this.c.getTypeTable());
        ReceiverParameterDescriptor receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = (typeReceiverType == null || (kotlinTypeType = deserializationContextChildContext$default.getTypeDeserializer().type(typeReceiverType)) == null) ? null : DescriptorFactory.createExtensionReceiverParameterForCallable(deserializedSimpleFunctionDescriptor, kotlinTypeType, empty);
        ReceiverParameterDescriptor dispatchReceiverParameter = getDispatchReceiverParameter();
        List<TypeParameterDescriptor> ownTypeParameters = deserializationContextChildContext$default.getTypeDeserializer().getOwnTypeParameters();
        MemberDeserializer memberDeserializer = deserializationContextChildContext$default.getMemberDeserializer();
        List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "proto.valueParameterList");
        List<ValueParameterDescriptor> listValueParameters = memberDeserializer.valueParameters(valueParameterList, function, AnnotatedCallableKind.FUNCTION);
        KotlinType kotlinTypeType2 = deserializationContextChildContext$default.getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(proto, this.c.getTypeTable()));
        Modality modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags));
        DescriptorVisibility descriptorVisibility = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(flags));
        Map<? extends CallableDescriptor.UserDataKey<?>, ?> mapEmptyMap = MapsKt.emptyMap();
        Boolean bool = Flags.IS_SUSPEND.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "IS_SUSPEND.get(flags)");
        initializeWithCoroutinesExperimentalityStatus(deserializedSimpleFunctionDescriptor, receiverParameterDescriptorCreateExtensionReceiverParameterForCallable, dispatchReceiverParameter, ownTypeParameters, listValueParameters, kotlinTypeType2, modality, descriptorVisibility, mapEmptyMap, bool.booleanValue());
        Boolean bool2 = Flags.IS_OPERATOR.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool2, "IS_OPERATOR.get(flags)");
        deserializedSimpleFunctionDescriptor.setOperator(bool2.booleanValue());
        Boolean bool3 = Flags.IS_INFIX.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool3, "IS_INFIX.get(flags)");
        deserializedSimpleFunctionDescriptor.setInfix(bool3.booleanValue());
        Boolean bool4 = Flags.IS_EXTERNAL_FUNCTION.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool4, "IS_EXTERNAL_FUNCTION.get(flags)");
        deserializedSimpleFunctionDescriptor.setExternal(bool4.booleanValue());
        Boolean bool5 = Flags.IS_INLINE.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool5, "IS_INLINE.get(flags)");
        deserializedSimpleFunctionDescriptor.setInline(bool5.booleanValue());
        Boolean bool6 = Flags.IS_TAILREC.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool6, "IS_TAILREC.get(flags)");
        deserializedSimpleFunctionDescriptor.setTailrec(bool6.booleanValue());
        Boolean bool7 = Flags.IS_SUSPEND.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool7, "IS_SUSPEND.get(flags)");
        deserializedSimpleFunctionDescriptor.setSuspend(bool7.booleanValue());
        Boolean bool8 = Flags.IS_EXPECT_FUNCTION.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool8, "IS_EXPECT_FUNCTION.get(flags)");
        deserializedSimpleFunctionDescriptor.setExpect(bool8.booleanValue());
        deserializedSimpleFunctionDescriptor.setHasStableParameterNames(!Flags.IS_FUNCTION_WITH_NON_STABLE_PARAMETER_NAMES.get(flags).booleanValue());
        Pair<CallableDescriptor.UserDataKey<?>, Object> pairDeserializeContractFromFunction = this.c.getComponents().getContractDeserializer().deserializeContractFromFunction(proto, deserializedSimpleFunctionDescriptor, this.c.getTypeTable(), deserializationContextChildContext$default.getTypeDeserializer());
        if (pairDeserializeContractFromFunction != null) {
            deserializedSimpleFunctionDescriptor.putInUserDataMap(pairDeserializeContractFromFunction.getFirst(), pairDeserializeContractFromFunction.getSecond());
        }
        return deserializedSimpleFunctionDescriptor;
    }

    public final TypeAliasDescriptor loadTypeAlias(ProtoBuf.TypeAlias proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Annotations.Companion companion = Annotations.Companion;
        List<ProtoBuf.Annotation> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "proto.annotationList");
        List<ProtoBuf.Annotation> list = annotationList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ProtoBuf.Annotation it : list) {
            AnnotationDeserializer annotationDeserializer = this.annotationDeserializer;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            arrayList.add(annotationDeserializer.deserializeAnnotation(it, this.c.getNameResolver()));
        }
        DeserializedTypeAliasDescriptor deserializedTypeAliasDescriptor = new DeserializedTypeAliasDescriptor(this.c.getStorageManager(), this.c.getContainingDeclaration(), companion.create(arrayList), NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName()), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(proto.getFlags())), proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "proto.typeParameterList");
        DeserializationContext deserializationContextChildContext$default = DeserializationContext.childContext$default(this.c, deserializedTypeAliasDescriptor, typeParameterList, null, null, null, null, 60, null);
        deserializedTypeAliasDescriptor.initialize(deserializationContextChildContext$default.getTypeDeserializer().getOwnTypeParameters(), deserializationContextChildContext$default.getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.underlyingType(proto, this.c.getTypeTable()), false), deserializationContextChildContext$default.getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.expandedType(proto, this.c.getTypeTable()), false), checkExperimentalCoroutine(deserializedTypeAliasDescriptor, deserializationContextChildContext$default.getTypeDeserializer()));
        return deserializedTypeAliasDescriptor;
    }

    private final ReceiverParameterDescriptor getDispatchReceiverParameter() {
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null) {
            return null;
        }
        return classDescriptor.getThisAsReceiverParameter();
    }

    public final ClassConstructorDescriptor loadConstructor(ProtoBuf.Constructor proto, boolean z) {
        DeserializedClassConstructorDescriptor deserializedClassConstructorDescriptor;
        DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesCompatibilityModeComputeExperimentalityModeForFunctions;
        TypeDeserializer typeDeserializer;
        Intrinsics.checkNotNullParameter(proto, "proto");
        ClassDescriptor classDescriptor = (ClassDescriptor) this.c.getContainingDeclaration();
        ProtoBuf.Constructor constructor = proto;
        DeserializedClassConstructorDescriptor deserializedClassConstructorDescriptor2 = new DeserializedClassConstructorDescriptor(classDescriptor, null, getAnnotations(constructor, proto.getFlags(), AnnotatedCallableKind.FUNCTION), z, CallableMemberDescriptor.Kind.DECLARATION, proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource(), null, 1024, null);
        MemberDeserializer memberDeserializer = DeserializationContext.childContext$default(this.c, deserializedClassConstructorDescriptor2, CollectionsKt.emptyList(), null, null, null, null, 60, null).getMemberDeserializer();
        List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "proto.valueParameterList");
        deserializedClassConstructorDescriptor2.initialize(memberDeserializer.valueParameters(valueParameterList, constructor, AnnotatedCallableKind.FUNCTION), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(proto.getFlags())));
        deserializedClassConstructorDescriptor2.setReturnType(classDescriptor.getDefaultType());
        deserializedClassConstructorDescriptor2.setHasStableParameterNames(!Flags.IS_CONSTRUCTOR_WITH_NON_STABLE_PARAMETER_NAMES.get(proto.getFlags()).booleanValue());
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        Boolean boolValueOf = null;
        DeserializedClassDescriptor deserializedClassDescriptor = containingDeclaration instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) containingDeclaration : null;
        DeserializationContext c = deserializedClassDescriptor == null ? null : deserializedClassDescriptor.getC();
        if (c != null && (typeDeserializer = c.getTypeDeserializer()) != null) {
            boolValueOf = Boolean.valueOf(typeDeserializer.getExperimentalSuspendFunctionTypeEncountered());
        }
        if (Intrinsics.areEqual((Object) boolValueOf, (Object) true) && versionAndReleaseCoroutinesMismatch(deserializedClassConstructorDescriptor2)) {
            coroutinesCompatibilityModeComputeExperimentalityModeForFunctions = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
            deserializedClassConstructorDescriptor = deserializedClassConstructorDescriptor2;
        } else {
            List<ValueParameterDescriptor> valueParameters = deserializedClassConstructorDescriptor2.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "descriptor.valueParameters");
            List<ValueParameterDescriptor> list = valueParameters;
            List<TypeParameterDescriptor> typeParameters = deserializedClassConstructorDescriptor2.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "descriptor.typeParameters");
            deserializedClassConstructorDescriptor = deserializedClassConstructorDescriptor2;
            coroutinesCompatibilityModeComputeExperimentalityModeForFunctions = computeExperimentalityModeForFunctions(deserializedClassConstructorDescriptor2, null, list, typeParameters, deserializedClassConstructorDescriptor2.getReturnType(), false);
        }
        deserializedClassConstructorDescriptor.setCoroutinesExperimentalCompatibilityMode$deserialization(coroutinesCompatibilityModeComputeExperimentalityModeForFunctions);
        return deserializedClassConstructorDescriptor;
    }

    private final Annotations getAnnotations(final MessageLite messageLite, int i, final AnnotatedCallableKind annotatedCallableKind) {
        if (!Flags.HAS_ANNOTATIONS.get(i).booleanValue()) {
            return Annotations.Companion.getEMPTY();
        }
        return new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.getAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AnnotationDescriptor> invoke() {
                List<? extends AnnotationDescriptor> list;
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer protoContainerAsProtoContainer = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                if (protoContainerAsProtoContainer == null) {
                    list = null;
                } else {
                    list = CollectionsKt.toList(MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader().loadCallableAnnotations(protoContainerAsProtoContainer, messageLite, annotatedCallableKind));
                }
                return list != null ? list : CollectionsKt.emptyList();
            }
        });
    }

    private final Annotations getPropertyFieldAnnotations(final ProtoBuf.Property property, final boolean z) {
        if (!Flags.HAS_ANNOTATIONS.get(property.getFlags()).booleanValue()) {
            return Annotations.Companion.getEMPTY();
        }
        return new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.getPropertyFieldAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AnnotationDescriptor> invoke() {
                List<? extends AnnotationDescriptor> list;
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer protoContainerAsProtoContainer = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                if (protoContainerAsProtoContainer == null) {
                    list = null;
                } else {
                    boolean z2 = z;
                    MemberDeserializer memberDeserializer2 = MemberDeserializer.this;
                    ProtoBuf.Property property2 = property;
                    list = z2 ? CollectionsKt.toList(memberDeserializer2.c.getComponents().getAnnotationAndConstantLoader().loadPropertyDelegateFieldAnnotations(protoContainerAsProtoContainer, property2)) : CollectionsKt.toList(memberDeserializer2.c.getComponents().getAnnotationAndConstantLoader().loadPropertyBackingFieldAnnotations(protoContainerAsProtoContainer, property2));
                }
                return list != null ? list : CollectionsKt.emptyList();
            }
        });
    }

    private final Annotations getReceiverParameterAnnotations(final MessageLite messageLite, final AnnotatedCallableKind annotatedCallableKind) {
        return new DeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.getReceiverParameterAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AnnotationDescriptor> invoke() {
                List<AnnotationDescriptor> listLoadExtensionReceiverParameterAnnotations;
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer protoContainerAsProtoContainer = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                if (protoContainerAsProtoContainer == null) {
                    listLoadExtensionReceiverParameterAnnotations = null;
                } else {
                    listLoadExtensionReceiverParameterAnnotations = MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader().loadExtensionReceiverParameterAnnotations(protoContainerAsProtoContainer, messageLite, annotatedCallableKind);
                }
                return listLoadExtensionReceiverParameterAnnotations != null ? listLoadExtensionReceiverParameterAnnotations : CollectionsKt.emptyList();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.util.List<kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor> valueParameters(java.util.List<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter> r26, final kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r27, final kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotatedCallableKind r28) {
        /*
            Method dump skipped, instruction units count: 288
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.valueParameters(java.util.List, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite, kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotatedCallableKind):java.util.List");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProtoContainer asProtoContainer(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof PackageFragmentDescriptor) {
            return new ProtoContainer.Package(((PackageFragmentDescriptor) declarationDescriptor).getFqName(), this.c.getNameResolver(), this.c.getTypeTable(), this.c.getContainerSource());
        }
        if (declarationDescriptor instanceof DeserializedClassDescriptor) {
            return ((DeserializedClassDescriptor) declarationDescriptor).getThisAsProtoContainer$deserialization();
        }
        return null;
    }
}
