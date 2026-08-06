package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefinerKt;

/* JADX INFO: compiled from: AbstractTypeConstructor.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractTypeConstructor implements TypeConstructor {
    private final boolean shouldReportCyclicScopeWithCompanionWarning;
    private final NotNullLazyValue<Supertypes> supertypes;

    protected abstract Collection<KotlinType> computeSupertypes();

    protected KotlinType defaultSupertypeIfEmpty() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public abstract ClassifierDescriptor mo1677getDeclarationDescriptor();

    protected abstract SupertypeLoopChecker getSupertypeLoopChecker();

    protected List<KotlinType> processSupertypesWithoutCycles(List<KotlinType> supertypes) {
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        return supertypes;
    }

    protected void reportScopesLoopError(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
    }

    protected void reportSupertypeLoopError(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
    }

    public AbstractTypeConstructor(StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.supertypes = storageManager.createLazyValueWithPostCompute(new Function0<Supertypes>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AbstractTypeConstructor.Supertypes invoke() {
                return new AbstractTypeConstructor.Supertypes(this.this$0.computeSupertypes());
            }
        }, new Function1<Boolean, Supertypes>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ AbstractTypeConstructor.Supertypes invoke(Boolean bool) {
                return invoke(bool.booleanValue());
            }

            public final AbstractTypeConstructor.Supertypes invoke(boolean z) {
                return new AbstractTypeConstructor.Supertypes(CollectionsKt.listOf(ErrorUtils.ERROR_TYPE_FOR_LOOP_IN_SUPERTYPES));
            }
        }, new Function1<Supertypes, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AbstractTypeConstructor.Supertypes supertypes) {
                invoke2(supertypes);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AbstractTypeConstructor.Supertypes supertypes) {
                Intrinsics.checkNotNullParameter(supertypes, "supertypes");
                SupertypeLoopChecker supertypeLoopChecker = this.this$0.getSupertypeLoopChecker();
                AbstractTypeConstructor abstractTypeConstructor = this.this$0;
                Collection<KotlinType> allSupertypes = supertypes.getAllSupertypes();
                final AbstractTypeConstructor abstractTypeConstructor2 = this.this$0;
                Function1<TypeConstructor, Iterable<? extends KotlinType>> function1 = new Function1<TypeConstructor, Iterable<? extends KotlinType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3$resultWithoutCycles$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Iterable<KotlinType> invoke(TypeConstructor it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return abstractTypeConstructor2.computeNeighbours(it, false);
                    }
                };
                final AbstractTypeConstructor abstractTypeConstructor3 = this.this$0;
                List listFindLoopsInSupertypesAndDisconnect = supertypeLoopChecker.findLoopsInSupertypesAndDisconnect(abstractTypeConstructor, allSupertypes, function1, new Function1<KotlinType, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3$resultWithoutCycles$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType) {
                        invoke2(kotlinType);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(KotlinType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        abstractTypeConstructor3.reportSupertypeLoopError(it);
                    }
                });
                if (listFindLoopsInSupertypesAndDisconnect.isEmpty()) {
                    KotlinType kotlinTypeDefaultSupertypeIfEmpty = this.this$0.defaultSupertypeIfEmpty();
                    List listListOf = kotlinTypeDefaultSupertypeIfEmpty == null ? null : CollectionsKt.listOf(kotlinTypeDefaultSupertypeIfEmpty);
                    if (listListOf == null) {
                        listListOf = CollectionsKt.emptyList();
                    }
                    listFindLoopsInSupertypesAndDisconnect = listListOf;
                }
                if (this.this$0.getShouldReportCyclicScopeWithCompanionWarning()) {
                    SupertypeLoopChecker supertypeLoopChecker2 = this.this$0.getSupertypeLoopChecker();
                    AbstractTypeConstructor abstractTypeConstructor4 = this.this$0;
                    final AbstractTypeConstructor abstractTypeConstructor5 = this.this$0;
                    Function1<TypeConstructor, Iterable<? extends KotlinType>> function12 = new Function1<TypeConstructor, Iterable<? extends KotlinType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3.2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Iterable<KotlinType> invoke(TypeConstructor it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return abstractTypeConstructor5.computeNeighbours(it, true);
                        }
                    };
                    final AbstractTypeConstructor abstractTypeConstructor6 = this.this$0;
                    supertypeLoopChecker2.findLoopsInSupertypesAndDisconnect(abstractTypeConstructor4, listFindLoopsInSupertypesAndDisconnect, function12, new Function1<KotlinType, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3.3
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType) {
                            invoke2(kotlinType);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(KotlinType it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            abstractTypeConstructor6.reportScopesLoopError(it);
                        }
                    });
                }
                AbstractTypeConstructor abstractTypeConstructor7 = this.this$0;
                List<KotlinType> list = listFindLoopsInSupertypesAndDisconnect instanceof List ? (List) listFindLoopsInSupertypesAndDisconnect : null;
                if (list == null) {
                    list = CollectionsKt.toList(listFindLoopsInSupertypesAndDisconnect);
                }
                supertypes.setSupertypesWithoutCycles(abstractTypeConstructor7.processSupertypesWithoutCycles(list));
            }
        });
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes */
    public List<KotlinType> mo1678getSupertypes() {
        return this.supertypes.invoke().getSupertypesWithoutCycles();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new ModuleViewTypeConstructor(this, kotlinTypeRefiner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AbstractTypeConstructor.kt */
    final class ModuleViewTypeConstructor implements TypeConstructor {
        private final KotlinTypeRefiner kotlinTypeRefiner;
        private final Lazy refinedSupertypes$delegate;
        final /* synthetic */ AbstractTypeConstructor this$0;

        public ModuleViewTypeConstructor(final AbstractTypeConstructor this$0, KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            this.this$0 = this$0;
            this.kotlinTypeRefiner = kotlinTypeRefiner;
            this.refinedSupertypes$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends KotlinType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$ModuleViewTypeConstructor$refinedSupertypes$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends KotlinType> invoke() {
                    return KotlinTypeRefinerKt.refineTypes(this.this$0.kotlinTypeRefiner, this$0.mo1678getSupertypes());
                }
            });
        }

        private final List<KotlinType> getRefinedSupertypes() {
            return (List) this.refinedSupertypes$delegate.getValue();
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        public List<TypeParameterDescriptor> getParameters() {
            List<TypeParameterDescriptor> parameters = this.this$0.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "this@AbstractTypeConstructor.parameters");
            return parameters;
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        /* JADX INFO: renamed from: getSupertypes */
        public List<KotlinType> mo1678getSupertypes() {
            return getRefinedSupertypes();
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        public boolean isDenotable() {
            return this.this$0.isDenotable();
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        /* JADX INFO: renamed from: getDeclarationDescriptor */
        public ClassifierDescriptor mo1677getDeclarationDescriptor() {
            return this.this$0.mo1677getDeclarationDescriptor();
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        public KotlinBuiltIns getBuiltIns() {
            KotlinBuiltIns builtIns = this.this$0.getBuiltIns();
            Intrinsics.checkNotNullExpressionValue(builtIns, "this@AbstractTypeConstructor.builtIns");
            return builtIns;
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            return this.this$0.refine(kotlinTypeRefiner);
        }

        public boolean equals(Object obj) {
            return this.this$0.equals(obj);
        }

        public int hashCode() {
            return this.this$0.hashCode();
        }

        public String toString() {
            return this.this$0.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AbstractTypeConstructor.kt */
    static final class Supertypes {
        private final Collection<KotlinType> allSupertypes;
        private List<? extends KotlinType> supertypesWithoutCycles;

        /* JADX WARN: Multi-variable type inference failed */
        public Supertypes(Collection<? extends KotlinType> allSupertypes) {
            Intrinsics.checkNotNullParameter(allSupertypes, "allSupertypes");
            this.allSupertypes = allSupertypes;
            this.supertypesWithoutCycles = CollectionsKt.listOf(ErrorUtils.ERROR_TYPE_FOR_LOOP_IN_SUPERTYPES);
        }

        public final Collection<KotlinType> getAllSupertypes() {
            return this.allSupertypes;
        }

        public final List<KotlinType> getSupertypesWithoutCycles() {
            return this.supertypesWithoutCycles;
        }

        public final void setSupertypesWithoutCycles(List<? extends KotlinType> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.supertypesWithoutCycles = list;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<KotlinType> computeNeighbours(TypeConstructor typeConstructor, boolean z) {
        AbstractTypeConstructor abstractTypeConstructor = typeConstructor instanceof AbstractTypeConstructor ? (AbstractTypeConstructor) typeConstructor : null;
        List listPlus = abstractTypeConstructor != null ? CollectionsKt.plus((Collection) abstractTypeConstructor.supertypes.invoke().getAllSupertypes(), (Iterable) abstractTypeConstructor.getAdditionalNeighboursInSupertypeGraph(z)) : null;
        if (listPlus != null) {
            return listPlus;
        }
        Collection<KotlinType> supertypes = typeConstructor.mo1678getSupertypes();
        Intrinsics.checkNotNullExpressionValue(supertypes, "supertypes");
        return supertypes;
    }

    protected boolean getShouldReportCyclicScopeWithCompanionWarning() {
        return this.shouldReportCyclicScopeWithCompanionWarning;
    }

    protected Collection<KotlinType> getAdditionalNeighboursInSupertypeGraph(boolean z) {
        return CollectionsKt.emptyList();
    }
}
