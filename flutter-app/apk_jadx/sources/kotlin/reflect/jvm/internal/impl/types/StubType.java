package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.model.StubTypeMarker;

/* JADX INFO: compiled from: StubType.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class StubType extends AbstractStubType implements StubTypeMarker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StubType(TypeConstructor originalTypeVariable, boolean z, TypeConstructor constructor, MemberScope memberScope) {
        super(originalTypeVariable, z, constructor, memberScope);
        Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractStubType
    public AbstractStubType materialize(boolean z) {
        return new StubType(getOriginalTypeVariable(), z, getConstructor(), getMemberScope());
    }
}
