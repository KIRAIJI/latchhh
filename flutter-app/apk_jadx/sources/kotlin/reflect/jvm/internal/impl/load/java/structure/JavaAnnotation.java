package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface JavaAnnotation extends JavaElement {

    /* JADX INFO: compiled from: javaElements.kt */
    public static final class DefaultImpls {
        public static boolean isFreshlySupportedTypeUseAnnotation(JavaAnnotation javaAnnotation) {
            Intrinsics.checkNotNullParameter(javaAnnotation, "this");
            return false;
        }

        public static boolean isIdeExternalAnnotation(JavaAnnotation javaAnnotation) {
            Intrinsics.checkNotNullParameter(javaAnnotation, "this");
            return false;
        }
    }

    Collection<JavaAnnotationArgument> getArguments();

    ClassId getClassId();

    boolean isFreshlySupportedTypeUseAnnotation();

    boolean isIdeExternalAnnotation();

    JavaClass resolve();
}
