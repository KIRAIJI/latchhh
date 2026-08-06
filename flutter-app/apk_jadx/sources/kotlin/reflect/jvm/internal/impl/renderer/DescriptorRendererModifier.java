package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum DescriptorRendererModifier {
    VISIBILITY(true),
    MODALITY(true),
    OVERRIDE(true),
    ANNOTATIONS(false),
    INNER(true),
    MEMBER_KIND(true),
    DATA(true),
    INLINE(true),
    EXPECT(true),
    ACTUAL(true),
    CONST(true),
    LATEINIT(true),
    FUN(true),
    VALUE(true);

    public static final Set<DescriptorRendererModifier> ALL;
    public static final Set<DescriptorRendererModifier> ALL_EXCEPT_ANNOTATIONS;
    public static final Companion Companion = new Companion(null);
    private final boolean includeByDefault;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static DescriptorRendererModifier[] valuesCustom() {
        DescriptorRendererModifier[] descriptorRendererModifierArrValuesCustom = values();
        DescriptorRendererModifier[] descriptorRendererModifierArr = new DescriptorRendererModifier[descriptorRendererModifierArrValuesCustom.length];
        System.arraycopy(descriptorRendererModifierArrValuesCustom, 0, descriptorRendererModifierArr, 0, descriptorRendererModifierArrValuesCustom.length);
        return descriptorRendererModifierArr;
    }

    DescriptorRendererModifier(boolean z) {
        this.includeByDefault = z;
    }

    public final boolean getIncludeByDefault() {
        return this.includeByDefault;
    }

    static {
        DescriptorRendererModifier[] descriptorRendererModifierArrValuesCustom = valuesCustom();
        ArrayList arrayList = new ArrayList();
        for (DescriptorRendererModifier descriptorRendererModifier : descriptorRendererModifierArrValuesCustom) {
            if (descriptorRendererModifier.getIncludeByDefault()) {
                arrayList.add(descriptorRendererModifier);
            }
        }
        ALL_EXCEPT_ANNOTATIONS = CollectionsKt.toSet(arrayList);
        ALL = ArraysKt.toSet(valuesCustom());
    }

    /* JADX INFO: compiled from: DescriptorRenderer.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
