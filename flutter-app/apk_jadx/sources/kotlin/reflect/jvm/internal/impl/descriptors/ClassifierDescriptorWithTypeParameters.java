package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface ClassifierDescriptorWithTypeParameters extends ClassifierDescriptor, DeclarationDescriptorWithVisibility, MemberDescriptor, Substitutable<ClassifierDescriptorWithTypeParameters> {
    List<TypeParameterDescriptor> getDeclaredTypeParameters();

    boolean isInner();
}
