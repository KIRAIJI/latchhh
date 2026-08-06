package kotlin.reflect.jvm.internal.impl.renderer;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum RenderingFormat {
    PLAIN { // from class: kotlin.reflect.jvm.internal.impl.renderer.RenderingFormat.PLAIN
        @Override // kotlin.reflect.jvm.internal.impl.renderer.RenderingFormat
        public String escape(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return string;
        }
    },
    HTML { // from class: kotlin.reflect.jvm.internal.impl.renderer.RenderingFormat.HTML
        @Override // kotlin.reflect.jvm.internal.impl.renderer.RenderingFormat
        public String escape(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return StringsKt.replace$default(StringsKt.replace$default(string, "<", "&lt;", false, 4, (Object) null), ">", "&gt;", false, 4, (Object) null);
        }
    };

    /* synthetic */ RenderingFormat(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static RenderingFormat[] valuesCustom() {
        RenderingFormat[] renderingFormatArrValuesCustom = values();
        RenderingFormat[] renderingFormatArr = new RenderingFormat[renderingFormatArrValuesCustom.length];
        System.arraycopy(renderingFormatArrValuesCustom, 0, renderingFormatArr, 0, renderingFormatArrValuesCustom.length);
        return renderingFormatArr;
    }

    public abstract String escape(String str);
}
