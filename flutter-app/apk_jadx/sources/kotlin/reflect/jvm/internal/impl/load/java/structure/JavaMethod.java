package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface JavaMethod extends JavaMember, JavaTypeParameterListOwner {
    JavaAnnotationArgument getAnnotationParameterDefaultValue();

    boolean getHasAnnotationParameterDefaultValue();

    JavaType getReturnType();

    List<JavaValueParameter> getValueParameters();

    /* JADX INFO: compiled from: javaElements.kt */
    public static final class DefaultImpls {
        public static boolean getHasAnnotationParameterDefaultValue(JavaMethod javaMethod) {
            Intrinsics.checkNotNullParameter(javaMethod, "this");
            return javaMethod.getAnnotationParameterDefaultValue() != null;
        }
    }
}
