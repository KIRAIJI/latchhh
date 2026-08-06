package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BuiltInsResourceLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BuiltInsResourceLoader {
    public final InputStream loadResource(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resourceAsStream = classLoader == null ? null : classLoader.getResourceAsStream(path);
        return resourceAsStream == null ? ClassLoader.getSystemResourceAsStream(path) : resourceAsStream;
    }
}
