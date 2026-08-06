package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: descriptorUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DescriptorUtilKt {
    public static final ClassDescriptor resolveClassByFqName(ModuleDescriptor moduleDescriptor, FqName fqName, LookupLocation lookupLocation) {
        MemberScope unsubstitutedInnerClassesScope;
        ClassifierDescriptor contributedClassifier;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(lookupLocation, "lookupLocation");
        if (fqName.isRoot()) {
            return null;
        }
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "fqName.parent()");
        MemberScope memberScope = moduleDescriptor.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "fqName.shortName()");
        ClassifierDescriptor contributedClassifier2 = memberScope.mo1679getContributedClassifier(nameShortName, lookupLocation);
        ClassDescriptor classDescriptor = contributedClassifier2 instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier2 : null;
        if (classDescriptor != null) {
            return classDescriptor;
        }
        FqName fqNameParent2 = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent2, "fqName.parent()");
        ClassDescriptor classDescriptorResolveClassByFqName = resolveClassByFqName(moduleDescriptor, fqNameParent2, lookupLocation);
        if (classDescriptorResolveClassByFqName == null || (unsubstitutedInnerClassesScope = classDescriptorResolveClassByFqName.getUnsubstitutedInnerClassesScope()) == null) {
            contributedClassifier = null;
        } else {
            Name nameShortName2 = fqName.shortName();
            Intrinsics.checkNotNullExpressionValue(nameShortName2, "fqName.shortName()");
            contributedClassifier = unsubstitutedInnerClassesScope.mo1679getContributedClassifier(nameShortName2, lookupLocation);
        }
        if (contributedClassifier instanceof ClassDescriptor) {
            return (ClassDescriptor) contributedClassifier;
        }
        return null;
    }
}
