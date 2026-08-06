package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: PackageViewDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PackageViewDescriptor extends DeclarationDescriptor {
    FqName getFqName();

    List<PackageFragmentDescriptor> getFragments();

    MemberScope getMemberScope();

    ModuleDescriptor getModule();

    boolean isEmpty();

    /* JADX INFO: compiled from: PackageViewDescriptor.kt */
    public static final class DefaultImpls {
        public static boolean isEmpty(PackageViewDescriptor packageViewDescriptor) {
            Intrinsics.checkNotNullParameter(packageViewDescriptor, "this");
            return packageViewDescriptor.getFragments().isEmpty();
        }
    }
}
