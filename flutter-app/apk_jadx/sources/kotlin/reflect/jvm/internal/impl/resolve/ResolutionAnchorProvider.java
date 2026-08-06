package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: ResolutionAnchorProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ResolutionAnchorProvider {
    public static final Companion Companion = Companion.$$INSTANCE;

    ModuleDescriptor getResolutionAnchor(ModuleDescriptor moduleDescriptor);

    /* JADX INFO: compiled from: ResolutionAnchorProvider.kt */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ResolutionAnchorProvider Default = new ResolutionAnchorProvider() { // from class: kotlin.reflect.jvm.internal.impl.resolve.ResolutionAnchorProvider$Companion$Default$1
            @Override // kotlin.reflect.jvm.internal.impl.resolve.ResolutionAnchorProvider
            public ModuleDescriptor getResolutionAnchor(ModuleDescriptor moduleDescriptor) {
                Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
                return null;
            }
        };

        private Companion() {
        }
    }
}
