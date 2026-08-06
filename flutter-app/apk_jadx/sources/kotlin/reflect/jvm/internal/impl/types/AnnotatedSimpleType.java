package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: KotlinTypeFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
final class AnnotatedSimpleType extends DelegatingSimpleTypeImpl {
    private final Annotations annotations;

    @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType, kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.annotations;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnnotatedSimpleType(SimpleType delegate, Annotations annotations) {
        super(delegate);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.annotations = annotations;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType
    public AnnotatedSimpleType replaceDelegate(SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new AnnotatedSimpleType(delegate, getAnnotations());
    }
}
