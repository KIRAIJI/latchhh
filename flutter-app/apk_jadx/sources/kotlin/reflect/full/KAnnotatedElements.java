package kotlin.reflect.full;

import androidx.exifinterface.media.ExifInterface;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KAnnotatedElement;

/* JADX INFO: compiled from: KAnnotatedElements.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a \u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0087\b¢\u0006\u0002\u0010\u0004\u001a\u0019\u0010\u0005\u001a\u00020\u0006\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0087\b¨\u0006\u0007"}, d2 = {"findAnnotation", ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlin/reflect/KAnnotatedElement;", "(Lkotlin/reflect/KAnnotatedElement;)Ljava/lang/annotation/Annotation;", "hasAnnotation", "", "kotlin-reflection"}, k = 2, mv = {1, 4, 1})
public final class KAnnotatedElements {
    public static final /* synthetic */ <T extends Annotation> T findAnnotation(KAnnotatedElement findAnnotation) {
        Object next;
        Intrinsics.checkNotNullParameter(findAnnotation, "$this$findAnnotation");
        Iterator<T> it = findAnnotation.getAnnotations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (((Annotation) next) instanceof Annotation) {
                break;
            }
        }
        Intrinsics.reifiedOperationMarker(1, "T?");
        return (T) next;
    }

    public static final /* synthetic */ <T extends Annotation> boolean hasAnnotation(KAnnotatedElement hasAnnotation) {
        Object next;
        Intrinsics.checkNotNullParameter(hasAnnotation, "$this$hasAnnotation");
        Iterator<T> it = hasAnnotation.getAnnotations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (((Annotation) next) instanceof Annotation) {
                break;
            }
        }
        Intrinsics.reifiedOperationMarker(1, "T?");
        return ((Annotation) next) != null;
    }
}
