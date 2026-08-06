package retrofit2.converter.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.Moshi;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/* JADX INFO: loaded from: classes2.dex */
public final class MoshiConverterFactory extends Converter.Factory {
    private final boolean failOnUnknown;
    private final boolean lenient;
    private final Moshi moshi;
    private final boolean serializeNulls;

    public static MoshiConverterFactory create() {
        return create(new Moshi.Builder().build());
    }

    public static MoshiConverterFactory create(Moshi moshi) {
        Objects.requireNonNull(moshi, "moshi == null");
        return new MoshiConverterFactory(moshi, false, false, false);
    }

    private MoshiConverterFactory(Moshi moshi, boolean z, boolean z2, boolean z3) {
        this.moshi = moshi;
        this.lenient = z;
        this.failOnUnknown = z2;
        this.serializeNulls = z3;
    }

    public MoshiConverterFactory asLenient() {
        return new MoshiConverterFactory(this.moshi, true, this.failOnUnknown, this.serializeNulls);
    }

    public MoshiConverterFactory failOnUnknown() {
        return new MoshiConverterFactory(this.moshi, this.lenient, true, this.serializeNulls);
    }

    public MoshiConverterFactory withNullSerialization() {
        return new MoshiConverterFactory(this.moshi, this.lenient, this.failOnUnknown, true);
    }

    @Override // retrofit2.Converter.Factory
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        JsonAdapter jsonAdapterAdapter = this.moshi.adapter(type, jsonAnnotations(annotationArr));
        if (this.lenient) {
            jsonAdapterAdapter = jsonAdapterAdapter.lenient();
        }
        if (this.failOnUnknown) {
            jsonAdapterAdapter = jsonAdapterAdapter.failOnUnknown();
        }
        if (this.serializeNulls) {
            jsonAdapterAdapter = jsonAdapterAdapter.serializeNulls();
        }
        return new MoshiResponseBodyConverter(jsonAdapterAdapter);
    }

    @Override // retrofit2.Converter.Factory
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit) {
        JsonAdapter jsonAdapterAdapter = this.moshi.adapter(type, jsonAnnotations(annotationArr));
        if (this.lenient) {
            jsonAdapterAdapter = jsonAdapterAdapter.lenient();
        }
        if (this.failOnUnknown) {
            jsonAdapterAdapter = jsonAdapterAdapter.failOnUnknown();
        }
        if (this.serializeNulls) {
            jsonAdapterAdapter = jsonAdapterAdapter.serializeNulls();
        }
        return new MoshiRequestBodyConverter(jsonAdapterAdapter);
    }

    private static Set<? extends Annotation> jsonAnnotations(Annotation[] annotationArr) {
        LinkedHashSet linkedHashSet = null;
        for (Annotation annotation : annotationArr) {
            if (annotation.annotationType().isAnnotationPresent(JsonQualifier.class)) {
                if (linkedHashSet == null) {
                    linkedHashSet = new LinkedHashSet();
                }
                linkedHashSet.add(annotation);
            }
        }
        return linkedHashSet != null ? Collections.unmodifiableSet(linkedHashSet) : Collections.emptySet();
    }
}
