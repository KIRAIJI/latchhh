package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindExclude;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;

/* JADX INFO: compiled from: LazyJavaScope.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class LazyJavaScope extends MemberScopeImpl {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "propertyNamesLazy", "getPropertyNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "classNamesLazy", "getClassNamesLazy()Ljava/util/Set;"))};
    private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;
    private final LazyJavaResolverContext c;
    private final NotNullLazyValue classNamesLazy$delegate;
    private final MemoizedFunctionToNullable<Name, PropertyDescriptor> declaredField;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> declaredFunctions;
    private final NotNullLazyValue<DeclaredMemberIndex> declaredMemberIndex;
    private final NotNullLazyValue functionNamesLazy$delegate;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functions;
    private final LazyJavaScope mainScope;
    private final MemoizedFunctionToNotNull<Name, List<PropertyDescriptor>> properties;
    private final NotNullLazyValue propertyNamesLazy$delegate;

    protected abstract Set<Name> computeClassNames(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1);

    protected abstract Set<Name> computeFunctionNames(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1);

    protected void computeImplicitlyDeclaredFunctions(Collection<SimpleFunctionDescriptor> result, Name name) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name, "name");
    }

    protected abstract DeclaredMemberIndex computeMemberIndex();

    protected abstract void computeNonDeclaredFunctions(Collection<SimpleFunctionDescriptor> collection, Name name);

    protected abstract void computeNonDeclaredProperties(Name name, Collection<PropertyDescriptor> collection);

    protected abstract Set<Name> computePropertyNames(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1);

    protected abstract ReceiverParameterDescriptor getDispatchReceiverParameter();

    protected abstract DeclarationDescriptor getOwnerDescriptor();

    protected boolean isVisibleAsFunction(JavaMethodDescriptor javaMethodDescriptor) {
        Intrinsics.checkNotNullParameter(javaMethodDescriptor, "<this>");
        return true;
    }

    protected abstract MethodSignatureData resolveMethodSignature(JavaMethod javaMethod, List<? extends TypeParameterDescriptor> list, KotlinType kotlinType, List<? extends ValueParameterDescriptor> list2);

    public /* synthetic */ LazyJavaScope(LazyJavaResolverContext lazyJavaResolverContext, LazyJavaScope lazyJavaScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyJavaResolverContext, (i & 2) != 0 ? null : lazyJavaScope);
    }

    protected final LazyJavaResolverContext getC() {
        return this.c;
    }

    protected final LazyJavaScope getMainScope() {
        return this.mainScope;
    }

    public LazyJavaScope(LazyJavaResolverContext c, LazyJavaScope lazyJavaScope) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
        this.mainScope = lazyJavaScope;
        this.allDescriptors = c.getStorageManager().createRecursionTolerantLazyValue(new Function0<Collection<? extends DeclarationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$allDescriptors$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends DeclarationDescriptor> invoke() {
                return this.this$0.computeDescriptors(DescriptorKindFilter.ALL, MemberScope.Companion.getALL_NAME_FILTER());
            }
        }, CollectionsKt.emptyList());
        this.declaredMemberIndex = c.getStorageManager().createLazyValue(new Function0<DeclaredMemberIndex>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$declaredMemberIndex$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DeclaredMemberIndex invoke() {
                return this.this$0.computeMemberIndex();
            }
        });
        this.declaredFunctions = c.getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$declaredFunctions$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<SimpleFunctionDescriptor> invoke(Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                if (this.this$0.getMainScope() != null) {
                    return (Collection) this.this$0.getMainScope().declaredFunctions.invoke(name);
                }
                ArrayList arrayList = new ArrayList();
                for (JavaMethod javaMethod : this.this$0.getDeclaredMemberIndex().invoke().findMethodsByName(name)) {
                    JavaMethodDescriptor javaMethodDescriptorResolveMethodToFunctionDescriptor = this.this$0.resolveMethodToFunctionDescriptor(javaMethod);
                    if (this.this$0.isVisibleAsFunction(javaMethodDescriptorResolveMethodToFunctionDescriptor)) {
                        this.this$0.getC().getComponents().getJavaResolverCache().recordMethod(javaMethod, javaMethodDescriptorResolveMethodToFunctionDescriptor);
                        arrayList.add(javaMethodDescriptorResolveMethodToFunctionDescriptor);
                    }
                }
                ArrayList arrayList2 = arrayList;
                this.this$0.computeImplicitlyDeclaredFunctions(arrayList2, name);
                return arrayList2;
            }
        });
        this.declaredField = c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Name, PropertyDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$declaredField$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PropertyDescriptor invoke(Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                if (this.this$0.getMainScope() != null) {
                    return (PropertyDescriptor) this.this$0.getMainScope().declaredField.invoke(name);
                }
                JavaField javaFieldFindFieldByName = this.this$0.getDeclaredMemberIndex().invoke().findFieldByName(name);
                if (javaFieldFindFieldByName == null || javaFieldFindFieldByName.isEnumEntry()) {
                    return null;
                }
                return this.this$0.resolveProperty(javaFieldFindFieldByName);
            }
        });
        this.functions = c.getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$functions$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<SimpleFunctionDescriptor> invoke(Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                LinkedHashSet linkedHashSet = new LinkedHashSet((Collection) this.this$0.declaredFunctions.invoke(name));
                this.this$0.retainMostSpecificMethods(linkedHashSet);
                LinkedHashSet linkedHashSet2 = linkedHashSet;
                this.this$0.computeNonDeclaredFunctions(linkedHashSet2, name);
                return CollectionsKt.toList(this.this$0.getC().getComponents().getSignatureEnhancement().enhanceSignatures(this.this$0.getC(), linkedHashSet2));
            }
        });
        this.functionNamesLazy$delegate = c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$functionNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                return this.this$0.computeFunctionNames(DescriptorKindFilter.FUNCTIONS, null);
            }
        });
        this.propertyNamesLazy$delegate = c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$propertyNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                return this.this$0.computePropertyNames(DescriptorKindFilter.VARIABLES, null);
            }
        });
        this.classNamesLazy$delegate = c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$classNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                return this.this$0.computeClassNames(DescriptorKindFilter.CLASSIFIERS, null);
            }
        });
        this.properties = c.getStorageManager().createMemoizedFunction(new Function1<Name, List<? extends PropertyDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$properties$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<PropertyDescriptor> invoke(Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = arrayList;
                kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(arrayList2, this.this$0.declaredField.invoke(name));
                this.this$0.computeNonDeclaredProperties(name, arrayList2);
                if (DescriptorUtils.isAnnotationClass(this.this$0.getOwnerDescriptor())) {
                    return CollectionsKt.toList(arrayList);
                }
                return CollectionsKt.toList(this.this$0.getC().getComponents().getSignatureEnhancement().enhanceSignatures(this.this$0.getC(), arrayList2));
            }
        });
    }

    protected final NotNullLazyValue<Collection<DeclarationDescriptor>> getAllDescriptors() {
        return this.allDescriptors;
    }

    protected final NotNullLazyValue<DeclaredMemberIndex> getDeclaredMemberIndex() {
        return this.declaredMemberIndex;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void retainMostSpecificMethods(Set<SimpleFunctionDescriptor> set) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : set) {
            String strComputeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default((SimpleFunctionDescriptor) obj, false, false, 2, null);
            Object obj2 = linkedHashMap.get(strComputeJvmDescriptor$default);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(strComputeJvmDescriptor$default, obj2);
            }
            ((List) obj2).add(obj);
        }
        for (List list : linkedHashMap.values()) {
            if (list.size() != 1) {
                List list2 = list;
                Collection<? extends SimpleFunctionDescriptor> collectionSelectMostSpecificInEachOverridableGroup = OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(list2, new Function1<SimpleFunctionDescriptor, CallableDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$retainMostSpecificMethods$mostSpecificMethods$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CallableDescriptor invoke(SimpleFunctionDescriptor simpleFunctionDescriptor) {
                        Intrinsics.checkNotNullParameter(simpleFunctionDescriptor, "<this>");
                        return simpleFunctionDescriptor;
                    }
                });
                set.removeAll(list2);
                set.addAll(collectionSelectMostSpecificInEachOverridableGroup);
            }
        }
    }

    /* JADX INFO: compiled from: LazyJavaScope.kt */
    protected static final class MethodSignatureData {
        private final List<String> errors;
        private final boolean hasStableParameterNames;
        private final KotlinType receiverType;
        private final KotlinType returnType;
        private final List<TypeParameterDescriptor> typeParameters;
        private final List<ValueParameterDescriptor> valueParameters;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MethodSignatureData)) {
                return false;
            }
            MethodSignatureData methodSignatureData = (MethodSignatureData) obj;
            return Intrinsics.areEqual(this.returnType, methodSignatureData.returnType) && Intrinsics.areEqual(this.receiverType, methodSignatureData.receiverType) && Intrinsics.areEqual(this.valueParameters, methodSignatureData.valueParameters) && Intrinsics.areEqual(this.typeParameters, methodSignatureData.typeParameters) && this.hasStableParameterNames == methodSignatureData.hasStableParameterNames && Intrinsics.areEqual(this.errors, methodSignatureData.errors);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v11 */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v8, types: [int] */
        public int hashCode() {
            int iHashCode = this.returnType.hashCode() * 31;
            KotlinType kotlinType = this.receiverType;
            int iHashCode2 = (((((iHashCode + (kotlinType == null ? 0 : kotlinType.hashCode())) * 31) + this.valueParameters.hashCode()) * 31) + this.typeParameters.hashCode()) * 31;
            boolean z = this.hasStableParameterNames;
            ?? r1 = z;
            if (z) {
                r1 = 1;
            }
            return ((iHashCode2 + r1) * 31) + this.errors.hashCode();
        }

        public String toString() {
            return "MethodSignatureData(returnType=" + this.returnType + ", receiverType=" + this.receiverType + ", valueParameters=" + this.valueParameters + ", typeParameters=" + this.typeParameters + ", hasStableParameterNames=" + this.hasStableParameterNames + ", errors=" + this.errors + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MethodSignatureData(KotlinType returnType, KotlinType kotlinType, List<? extends ValueParameterDescriptor> valueParameters, List<? extends TypeParameterDescriptor> typeParameters, boolean z, List<String> errors) {
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
            Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
            Intrinsics.checkNotNullParameter(errors, "errors");
            this.returnType = returnType;
            this.receiverType = kotlinType;
            this.valueParameters = valueParameters;
            this.typeParameters = typeParameters;
            this.hasStableParameterNames = z;
            this.errors = errors;
        }

        public final KotlinType getReturnType() {
            return this.returnType;
        }

        public final KotlinType getReceiverType() {
            return this.receiverType;
        }

        public final List<ValueParameterDescriptor> getValueParameters() {
            return this.valueParameters;
        }

        public final List<TypeParameterDescriptor> getTypeParameters() {
            return this.typeParameters;
        }

        public final boolean getHasStableParameterNames() {
            return this.hasStableParameterNames;
        }

        public final List<String> getErrors() {
            return this.errors;
        }
    }

    protected final JavaMethodDescriptor resolveMethodToFunctionDescriptor(JavaMethod method) {
        Map<? extends CallableDescriptor.UserDataKey<?>, ?> mapEmptyMap;
        Intrinsics.checkNotNullParameter(method, "method");
        JavaMethodDescriptor javaMethodDescriptorCreateJavaMethod = JavaMethodDescriptor.createJavaMethod(getOwnerDescriptor(), LazyJavaAnnotationsKt.resolveAnnotations(this.c, method), method.getName(), this.c.getComponents().getSourceElementFactory().source(method), this.declaredMemberIndex.invoke().findRecordComponentByName(method.getName()) != null && method.getValueParameters().isEmpty());
        Intrinsics.checkNotNullExpressionValue(javaMethodDescriptorCreateJavaMethod, "createJavaMethod(\n            ownerDescriptor, annotations, method.name, c.components.sourceElementFactory.source(method),\n            declaredMemberIndex().findRecordComponentByName(method.name) != null && method.valueParameters.isEmpty()\n        )");
        LazyJavaResolverContext lazyJavaResolverContextChildForMethod$default = ContextKt.childForMethod$default(this.c, javaMethodDescriptorCreateJavaMethod, method, 0, 4, null);
        List<JavaTypeParameter> typeParameters = method.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            TypeParameterDescriptor typeParameterDescriptorResolveTypeParameter = lazyJavaResolverContextChildForMethod$default.getTypeParameterResolver().resolveTypeParameter((JavaTypeParameter) it.next());
            Intrinsics.checkNotNull(typeParameterDescriptorResolveTypeParameter);
            arrayList.add(typeParameterDescriptorResolveTypeParameter);
        }
        ResolvedValueParameters resolvedValueParametersResolveValueParameters = resolveValueParameters(lazyJavaResolverContextChildForMethod$default, javaMethodDescriptorCreateJavaMethod, method.getValueParameters());
        MethodSignatureData methodSignatureDataResolveMethodSignature = resolveMethodSignature(method, arrayList, computeMethodReturnType(method, lazyJavaResolverContextChildForMethod$default), resolvedValueParametersResolveValueParameters.getDescriptors());
        KotlinType receiverType = methodSignatureDataResolveMethodSignature.getReceiverType();
        ReceiverParameterDescriptor receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = receiverType == null ? null : DescriptorFactory.createExtensionReceiverParameterForCallable(javaMethodDescriptorCreateJavaMethod, receiverType, Annotations.Companion.getEMPTY());
        ReceiverParameterDescriptor dispatchReceiverParameter = getDispatchReceiverParameter();
        List<TypeParameterDescriptor> typeParameters2 = methodSignatureDataResolveMethodSignature.getTypeParameters();
        List<ValueParameterDescriptor> valueParameters = methodSignatureDataResolveMethodSignature.getValueParameters();
        KotlinType returnType = methodSignatureDataResolveMethodSignature.getReturnType();
        Modality modalityConvertFromFlags = Modality.Companion.convertFromFlags(false, method.isAbstract(), !method.isFinal());
        DescriptorVisibility descriptorVisibility = UtilsKt.toDescriptorVisibility(method.getVisibility());
        if (methodSignatureDataResolveMethodSignature.getReceiverType() != null) {
            mapEmptyMap = MapsKt.mapOf(TuplesKt.to(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER, CollectionsKt.first((List) resolvedValueParametersResolveValueParameters.getDescriptors())));
        } else {
            mapEmptyMap = MapsKt.emptyMap();
        }
        javaMethodDescriptorCreateJavaMethod.initialize(receiverParameterDescriptorCreateExtensionReceiverParameterForCallable, dispatchReceiverParameter, typeParameters2, valueParameters, returnType, modalityConvertFromFlags, descriptorVisibility, mapEmptyMap);
        javaMethodDescriptorCreateJavaMethod.setParameterNamesStatus(methodSignatureDataResolveMethodSignature.getHasStableParameterNames(), resolvedValueParametersResolveValueParameters.getHasSynthesizedNames());
        if (!methodSignatureDataResolveMethodSignature.getErrors().isEmpty()) {
            lazyJavaResolverContextChildForMethod$default.getComponents().getSignaturePropagator().reportSignatureErrors(javaMethodDescriptorCreateJavaMethod, methodSignatureDataResolveMethodSignature.getErrors());
        }
        return javaMethodDescriptorCreateJavaMethod;
    }

    protected final KotlinType computeMethodReturnType(JavaMethod method, LazyJavaResolverContext c) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(c, "c");
        return c.getTypeResolver().transformJavaType(method.getReturnType(), JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, method.getContainingClass().isAnnotationType(), null, 2, null));
    }

    /* JADX INFO: compiled from: LazyJavaScope.kt */
    protected static final class ResolvedValueParameters {
        private final List<ValueParameterDescriptor> descriptors;
        private final boolean hasSynthesizedNames;

        /* JADX WARN: Multi-variable type inference failed */
        public ResolvedValueParameters(List<? extends ValueParameterDescriptor> descriptors, boolean z) {
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            this.descriptors = descriptors;
            this.hasSynthesizedNames = z;
        }

        public final List<ValueParameterDescriptor> getDescriptors() {
            return this.descriptors;
        }

        public final boolean getHasSynthesizedNames() {
            return this.hasSynthesizedNames;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0124  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope.ResolvedValueParameters resolveValueParameters(kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r22, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r23, java.util.List<? extends kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter> r24) {
        /*
            Method dump skipped, instruction units count: 383
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope.resolveValueParameters(kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor, java.util.List):kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$ResolvedValueParameters");
    }

    private final Set<Name> getFunctionNamesLazy() {
        return (Set) StorageKt.getValue(this.functionNamesLazy$delegate, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final Set<Name> getPropertyNamesLazy() {
        return (Set) StorageKt.getValue(this.propertyNamesLazy$delegate, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    private final Set<Name> getClassNamesLazy() {
        return (Set) StorageKt.getValue(this.classNamesLazy$delegate, this, (KProperty<?>) $$delegatedProperties[2]);
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return getFunctionNamesLazy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return getPropertyNamesLazy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return getClassNamesLazy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return !getFunctionNames().contains(name) ? CollectionsKt.emptyList() : this.functions.invoke(name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PropertyDescriptor resolveProperty(final JavaField javaField) {
        final PropertyDescriptorImpl propertyDescriptorImplCreatePropertyDescriptor = createPropertyDescriptor(javaField);
        propertyDescriptorImplCreatePropertyDescriptor.initialize(null, null, null, null);
        propertyDescriptorImplCreatePropertyDescriptor.setType(getPropertyType(javaField), CollectionsKt.emptyList(), getDispatchReceiverParameter(), null);
        if (DescriptorUtils.shouldRecordInitializerForProperty(propertyDescriptorImplCreatePropertyDescriptor, propertyDescriptorImplCreatePropertyDescriptor.getType())) {
            propertyDescriptorImplCreatePropertyDescriptor.setCompileTimeInitializer(this.c.getStorageManager().createNullableLazyValue(new Function0<ConstantValue<?>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope.resolveProperty.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ConstantValue<?> invoke() {
                    return LazyJavaScope.this.getC().getComponents().getJavaPropertyInitializerEvaluator().getInitializerConstant(javaField, propertyDescriptorImplCreatePropertyDescriptor);
                }
            }));
        }
        PropertyDescriptorImpl propertyDescriptorImpl = propertyDescriptorImplCreatePropertyDescriptor;
        this.c.getComponents().getJavaResolverCache().recordField(javaField, propertyDescriptorImpl);
        return propertyDescriptorImpl;
    }

    private final PropertyDescriptorImpl createPropertyDescriptor(JavaField javaField) {
        JavaPropertyDescriptor javaPropertyDescriptorCreate = JavaPropertyDescriptor.create(getOwnerDescriptor(), LazyJavaAnnotationsKt.resolveAnnotations(this.c, javaField), Modality.FINAL, UtilsKt.toDescriptorVisibility(javaField.getVisibility()), !javaField.isFinal(), javaField.getName(), this.c.getComponents().getSourceElementFactory().source(javaField), isFinalStatic(javaField));
        Intrinsics.checkNotNullExpressionValue(javaPropertyDescriptorCreate, "create(\n            ownerDescriptor, annotations, Modality.FINAL, field.visibility.toDescriptorVisibility(), isVar, field.name,\n            c.components.sourceElementFactory.source(field), /* isConst = */ field.isFinalStatic\n        )");
        return javaPropertyDescriptorCreate;
    }

    private final boolean isFinalStatic(JavaField javaField) {
        return javaField.isFinal() && javaField.isStatic();
    }

    private final KotlinType getPropertyType(JavaField javaField) {
        boolean z = false;
        KotlinType kotlinTypeTransformJavaType = this.c.getTypeResolver().transformJavaType(javaField.getType(), JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null));
        if ((KotlinBuiltIns.isPrimitiveType(kotlinTypeTransformJavaType) || KotlinBuiltIns.isString(kotlinTypeTransformJavaType)) && isFinalStatic(javaField) && javaField.getHasConstantNotNullInitializer()) {
            z = true;
        }
        if (!z) {
            return kotlinTypeTransformJavaType;
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(kotlinTypeTransformJavaType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable, "makeNotNullable(propertyType)");
        return kotlinTypeMakeNotNullable;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return !getVariableNames().contains(name) ? CollectionsKt.emptyList() : this.properties.invoke(name);
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.allDescriptors.invoke();
    }

    protected final List<DeclarationDescriptor> computeDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        NoLookupLocation noLookupLocation = NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK())) {
            for (Name name : computeClassNames(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(linkedHashSet, mo1679getContributedClassifier(name, noLookupLocation));
                }
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getFUNCTIONS_MASK()) && !kindFilter.getExcludes().contains(DescriptorKindExclude.NonExtensions.INSTANCE)) {
            for (Name name2 : computeFunctionNames(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name2).booleanValue()) {
                    linkedHashSet.addAll(getContributedFunctions(name2, noLookupLocation));
                }
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getVARIABLES_MASK()) && !kindFilter.getExcludes().contains(DescriptorKindExclude.NonExtensions.INSTANCE)) {
            for (Name name3 : computePropertyNames(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name3).booleanValue()) {
                    linkedHashSet.addAll(getContributedVariables(name3, noLookupLocation));
                }
            }
        }
        return CollectionsKt.toList(linkedHashSet);
    }

    public String toString() {
        return Intrinsics.stringPlus("Lazy scope for ", getOwnerDescriptor());
    }
}
