package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ReflectionTypes.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ReflectionTypes {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    public static final Companion Companion = new Companion(null);
    private final ClassLookup kClass$delegate;
    private final ClassLookup kMutableProperty0$delegate;
    private final ClassLookup kMutableProperty1$delegate;
    private final ClassLookup kMutableProperty2$delegate;
    private final ClassLookup kProperty$delegate;
    private final ClassLookup kProperty0$delegate;
    private final ClassLookup kProperty1$delegate;
    private final ClassLookup kProperty2$delegate;
    private final Lazy kotlinReflectScope$delegate;
    private final NotFoundClasses notFoundClasses;

    public ReflectionTypes(final ModuleDescriptor module, NotFoundClasses notFoundClasses) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        this.notFoundClasses = notFoundClasses;
        this.kotlinReflectScope$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<MemberScope>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypes$kotlinReflectScope$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberScope invoke() {
                return module.getPackage(StandardNames.KOTLIN_REFLECT_FQ_NAME).getMemberScope();
            }
        });
        this.kClass$delegate = new ClassLookup(1);
        this.kProperty$delegate = new ClassLookup(1);
        this.kProperty0$delegate = new ClassLookup(1);
        this.kProperty1$delegate = new ClassLookup(2);
        this.kProperty2$delegate = new ClassLookup(3);
        this.kMutableProperty0$delegate = new ClassLookup(1);
        this.kMutableProperty1$delegate = new ClassLookup(2);
        this.kMutableProperty2$delegate = new ClassLookup(3);
    }

    private final MemberScope getKotlinReflectScope() {
        return (MemberScope) this.kotlinReflectScope$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor find(String str, int i) {
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(className)");
        ClassifierDescriptor contributedClassifier = getKotlinReflectScope().mo1679getContributedClassifier(nameIdentifier, NoLookupLocation.FROM_REFLECTION);
        ClassDescriptor classDescriptor = contributedClassifier instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier : null;
        return classDescriptor == null ? this.notFoundClasses.getClass(new ClassId(StandardNames.KOTLIN_REFLECT_FQ_NAME, nameIdentifier), CollectionsKt.listOf(Integer.valueOf(i))) : classDescriptor;
    }

    /* JADX INFO: compiled from: ReflectionTypes.kt */
    private static final class ClassLookup {
        private final int numberOfTypeParameters;

        public ClassLookup(int i) {
            this.numberOfTypeParameters = i;
        }

        public final ClassDescriptor getValue(ReflectionTypes types, KProperty<?> property) {
            Intrinsics.checkNotNullParameter(types, "types");
            Intrinsics.checkNotNullParameter(property, "property");
            return types.find(StringsKt.capitalize(property.getName()), this.numberOfTypeParameters);
        }
    }

    static {
        KProperty<Object>[] kPropertyArr = new KProperty[9];
        kPropertyArr[1] = Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kClass", "getKClass()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"));
        kPropertyArr[2] = Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty", "getKProperty()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"));
        kPropertyArr[3] = Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty0", "getKProperty0()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"));
        kPropertyArr[4] = Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty1", "getKProperty1()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"));
        kPropertyArr[5] = Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty2", "getKProperty2()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"));
        kPropertyArr[6] = Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty0", "getKMutableProperty0()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"));
        kPropertyArr[7] = Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty1", "getKMutableProperty1()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"));
        kPropertyArr[8] = Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty2", "getKMutableProperty2()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"));
        $$delegatedProperties = kPropertyArr;
    }

    public final ClassDescriptor getKClass() {
        return this.kClass$delegate.getValue(this, $$delegatedProperties[1]);
    }

    /* JADX INFO: compiled from: ReflectionTypes.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinType createKPropertyStarType(ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
            ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(module, StandardNames.FqNames.kProperty);
            if (classDescriptorFindClassAcrossModuleDependencies == null) {
                return null;
            }
            KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
            Annotations empty = Annotations.Companion.getEMPTY();
            List<TypeParameterDescriptor> parameters = classDescriptorFindClassAcrossModuleDependencies.getTypeConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "kPropertyClass.typeConstructor.parameters");
            Object objSingle = CollectionsKt.single((List<? extends Object>) parameters);
            Intrinsics.checkNotNullExpressionValue(objSingle, "kPropertyClass.typeConstructor.parameters.single()");
            return KotlinTypeFactory.simpleNotNullType(empty, classDescriptorFindClassAcrossModuleDependencies, CollectionsKt.listOf(new StarProjectionImpl((TypeParameterDescriptor) objSingle)));
        }
    }
}
