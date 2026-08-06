package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

/* JADX INFO: compiled from: JvmBuiltInClassDescriptorFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JvmBuiltInClassDescriptorFactory implements ClassDescriptorFactory {
    private static final ClassId CLONEABLE_CLASS_ID;
    private static final Name CLONEABLE_NAME;
    private final NotNullLazyValue cloneable$delegate;
    private final Function1<ModuleDescriptor, DeclarationDescriptor> computeContainingDeclaration;
    private final ModuleDescriptor moduleDescriptor;
    public static final Companion Companion = new Companion(null);
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInClassDescriptorFactory.class), "cloneable", "getCloneable()Lorg/jetbrains/kotlin/descriptors/impl/ClassDescriptorImpl;"))};
    private static final FqName KOTLIN_FQ_NAME = StandardNames.BUILT_INS_PACKAGE_FQ_NAME;

    /* JADX WARN: Multi-variable type inference failed */
    public JvmBuiltInClassDescriptorFactory(final StorageManager storageManager, ModuleDescriptor moduleDescriptor, Function1<? super ModuleDescriptor, ? extends DeclarationDescriptor> computeContainingDeclaration) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(computeContainingDeclaration, "computeContainingDeclaration");
        this.moduleDescriptor = moduleDescriptor;
        this.computeContainingDeclaration = computeContainingDeclaration;
        this.cloneable$delegate = storageManager.createLazyValue(new Function0<ClassDescriptorImpl>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInClassDescriptorFactory$cloneable$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptorImpl invoke() {
                ClassDescriptorImpl classDescriptorImpl = new ClassDescriptorImpl((DeclarationDescriptor) this.this$0.computeContainingDeclaration.invoke(this.this$0.moduleDescriptor), JvmBuiltInClassDescriptorFactory.CLONEABLE_NAME, Modality.ABSTRACT, ClassKind.INTERFACE, CollectionsKt.listOf(this.this$0.moduleDescriptor.getBuiltIns().getAnyType()), SourceElement.NO_SOURCE, false, storageManager);
                classDescriptorImpl.initialize(new CloneableClassScope(storageManager, classDescriptorImpl), SetsKt.emptySet(), null);
                return classDescriptorImpl;
            }
        });
    }

    public /* synthetic */ JvmBuiltInClassDescriptorFactory(StorageManager storageManager, ModuleDescriptor moduleDescriptor, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(storageManager, moduleDescriptor, (i & 4) != 0 ? new Function1<ModuleDescriptor, BuiltInsPackageFragment>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInClassDescriptorFactory.1
            @Override // kotlin.jvm.functions.Function1
            public final BuiltInsPackageFragment invoke(ModuleDescriptor module) {
                Intrinsics.checkNotNullParameter(module, "module");
                List<PackageFragmentDescriptor> fragments = module.getPackage(JvmBuiltInClassDescriptorFactory.KOTLIN_FQ_NAME).getFragments();
                ArrayList arrayList = new ArrayList();
                for (Object obj : fragments) {
                    if (obj instanceof BuiltInsPackageFragment) {
                        arrayList.add(obj);
                    }
                }
                return (BuiltInsPackageFragment) CollectionsKt.first((List) arrayList);
            }
        } : anonymousClass1);
    }

    static {
        Name nameShortName = StandardNames.FqNames.cloneable.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "cloneable.shortName()");
        CLONEABLE_NAME = nameShortName;
        ClassId classId = ClassId.topLevel(StandardNames.FqNames.cloneable.toSafe());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(StandardNames.FqNames.cloneable.toSafe())");
        CLONEABLE_CLASS_ID = classId;
    }

    private final ClassDescriptorImpl getCloneable() {
        return (ClassDescriptorImpl) StorageKt.getValue(this.cloneable$delegate, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    public boolean shouldCreateClass(FqName packageFqName, Name name) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name, "name");
        return Intrinsics.areEqual(name, CLONEABLE_NAME) && Intrinsics.areEqual(packageFqName, KOTLIN_FQ_NAME);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    public ClassDescriptor createClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (Intrinsics.areEqual(classId, Companion.getCLONEABLE_CLASS_ID())) {
            return getCloneable();
        }
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    public Collection<ClassDescriptor> getAllContributedClassesIfPossible(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return Intrinsics.areEqual(packageFqName, KOTLIN_FQ_NAME) ? SetsKt.setOf(getCloneable()) : SetsKt.emptySet();
    }

    /* JADX INFO: compiled from: JvmBuiltInClassDescriptorFactory.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ClassId getCLONEABLE_CLASS_ID() {
            return JvmBuiltInClassDescriptorFactory.CLONEABLE_CLASS_ID;
        }
    }
}
