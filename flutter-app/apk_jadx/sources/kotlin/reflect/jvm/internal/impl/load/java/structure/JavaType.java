package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.load.java.structure.ListBasedJavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.name.FqName;

/* JADX INFO: compiled from: javaTypes.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface JavaType extends ListBasedJavaAnnotationOwner {

    /* JADX INFO: compiled from: javaTypes.kt */
    public static final class DefaultImpls {
        public static JavaAnnotation findAnnotation(JavaType javaType, FqName fqName) {
            return ListBasedJavaAnnotationOwner.DefaultImpls.findAnnotation(javaType, fqName);
        }
    }
}
