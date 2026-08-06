package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

/* JADX INFO: compiled from: JvmBuiltIns.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JvmBuiltIns extends KotlinBuiltIns {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltIns.class), "customizer", "getCustomizer()Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltInsCustomizer;"))};
    private final NotNullLazyValue customizer$delegate;
    private Function0<Settings> settingsComputation;

    /* JADX INFO: compiled from: JvmBuiltIns.kt */
    public enum Kind {
        FROM_DEPENDENCIES,
        FROM_CLASS_LOADER,
        FALLBACK;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Kind[] valuesCustom() {
            Kind[] kindArrValuesCustom = values();
            Kind[] kindArr = new Kind[kindArrValuesCustom.length];
            System.arraycopy(kindArrValuesCustom, 0, kindArr, 0, kindArrValuesCustom.length);
            return kindArr;
        }
    }

    /* JADX INFO: compiled from: JvmBuiltIns.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Kind.valuesCustom().length];
            iArr[Kind.FROM_DEPENDENCIES.ordinal()] = 1;
            iArr[Kind.FROM_CLASS_LOADER.ordinal()] = 2;
            iArr[Kind.FALLBACK.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmBuiltIns(final StorageManager storageManager, Kind kind) {
        super(storageManager);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.customizer$delegate = storageManager.createLazyValue(new Function0<JvmBuiltInsCustomizer>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns$customizer$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JvmBuiltInsCustomizer invoke() {
                ModuleDescriptorImpl builtInsModule = this.this$0.getBuiltInsModule();
                Intrinsics.checkNotNullExpressionValue(builtInsModule, "builtInsModule");
                StorageManager storageManager2 = storageManager;
                final JvmBuiltIns jvmBuiltIns = this.this$0;
                return new JvmBuiltInsCustomizer(builtInsModule, storageManager2, new Function0<JvmBuiltIns.Settings>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns$customizer$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final JvmBuiltIns.Settings invoke() {
                        Function0 function0 = jvmBuiltIns.settingsComputation;
                        if (function0 == null) {
                            throw new AssertionError("JvmBuiltins instance has not been initialized properly");
                        }
                        JvmBuiltIns.Settings settings = (JvmBuiltIns.Settings) function0.invoke();
                        jvmBuiltIns.settingsComputation = null;
                        return settings;
                    }
                });
            }
        });
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 2) {
            createBuiltInsModule(false);
        } else {
            if (i != 3) {
                return;
            }
            createBuiltInsModule(true);
        }
    }

    /* JADX INFO: compiled from: JvmBuiltIns.kt */
    public static final class Settings {
        private final boolean isAdditionalBuiltInsFeatureSupported;
        private final ModuleDescriptor ownerModuleDescriptor;

        public Settings(ModuleDescriptor ownerModuleDescriptor, boolean z) {
            Intrinsics.checkNotNullParameter(ownerModuleDescriptor, "ownerModuleDescriptor");
            this.ownerModuleDescriptor = ownerModuleDescriptor;
            this.isAdditionalBuiltInsFeatureSupported = z;
        }

        public final ModuleDescriptor getOwnerModuleDescriptor() {
            return this.ownerModuleDescriptor;
        }

        public final boolean isAdditionalBuiltInsFeatureSupported() {
            return this.isAdditionalBuiltInsFeatureSupported;
        }
    }

    public final void setPostponedSettingsComputation(Function0<Settings> computation) {
        Intrinsics.checkNotNullParameter(computation, "computation");
        this.settingsComputation = computation;
    }

    public final void initialize(final ModuleDescriptor moduleDescriptor, final boolean z) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        setPostponedSettingsComputation(new Function0<Settings>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns.initialize.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Settings invoke() {
                return new Settings(moduleDescriptor, z);
            }
        });
    }

    public final JvmBuiltInsCustomizer getCustomizer() {
        return (JvmBuiltInsCustomizer) StorageKt.getValue(this.customizer$delegate, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns
    protected PlatformDependentDeclarationFilter getPlatformDependentDeclarationFilter() {
        return getCustomizer();
    }

    @Override // kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns
    protected AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
        return getCustomizer();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns
    public List<ClassDescriptorFactory> getClassDescriptorFactories() {
        Iterable<ClassDescriptorFactory> classDescriptorFactories = super.getClassDescriptorFactories();
        Intrinsics.checkNotNullExpressionValue(classDescriptorFactories, "super.getClassDescriptorFactories()");
        StorageManager storageManager = getStorageManager();
        Intrinsics.checkNotNullExpressionValue(storageManager, "storageManager");
        ModuleDescriptorImpl builtInsModule = getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "builtInsModule");
        return CollectionsKt.plus(classDescriptorFactories, new JvmBuiltInClassDescriptorFactory(storageManager, builtInsModule, null, 4, null));
    }
}
