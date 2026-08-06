package com.lazyee.klib.json;

import androidx.exifinterface.media.ExifInterface;
import com.lazyee.klib.json.MoshiArrayListJsonAdapter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MoshiArrayListJsonAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000 \u0015*\u0010\b\u0000\u0010\u0001*\n\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0002*\u0004\b\u0001\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004:\u0001\u0015B\u0015\b\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004¢\u0006\u0002\u0010\u0006J\u0015\u0010\u0007\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\nJ\r\u0010\u000b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/lazyee/klib/json/MoshiArrayListJsonAdapter;", "C", "", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/squareup/moshi/JsonAdapter;", "elementAdapter", "(Lcom/squareup/moshi/JsonAdapter;)V", "fromJson", "reader", "Lcom/squareup/moshi/JsonReader;", "(Lcom/squareup/moshi/JsonReader;)Ljava/util/Collection;", "newCollection", "()Ljava/util/Collection;", "toJson", "", "writer", "Lcom/squareup/moshi/JsonWriter;", "value", "(Lcom/squareup/moshi/JsonWriter;Ljava/util/Collection;)V", "toString", "", "Companion", "library_release"}, k = 1, mv = {1, 4, 2})
public abstract class MoshiArrayListJsonAdapter<C extends Collection<T>, T> extends JsonAdapter<C> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final JsonAdapter.Factory FACTORY = new JsonAdapter.Factory() { // from class: com.lazyee.klib.json.MoshiArrayListJsonAdapter$Companion$FACTORY$1
        @Override // com.squareup.moshi.JsonAdapter.Factory
        public final JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
            Class<?> rawType = Types.getRawType(type);
            Intrinsics.checkNotNullExpressionValue(annotations, "annotations");
            if ((!annotations.isEmpty()) || !Intrinsics.areEqual(rawType, ArrayList.class)) {
                return null;
            }
            MoshiArrayListJsonAdapter.Companion companion = MoshiArrayListJsonAdapter.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(type, "type");
            Intrinsics.checkNotNullExpressionValue(moshi, "moshi");
            return companion.newArrayListAdapter(type, moshi).nullSafe();
        }
    };
    private final JsonAdapter<T> elementAdapter;

    public abstract C newCollection();

    public /* synthetic */ MoshiArrayListJsonAdapter(JsonAdapter jsonAdapter, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsonAdapter);
    }

    private MoshiArrayListJsonAdapter(JsonAdapter<T> jsonAdapter) {
        this.elementAdapter = jsonAdapter;
    }

    @Override // com.squareup.moshi.JsonAdapter
    public C fromJson(JsonReader reader) throws IOException {
        Intrinsics.checkNotNullParameter(reader, "reader");
        C c = (C) newCollection();
        reader.beginArray();
        while (reader.hasNext()) {
            if (c != null) {
                T tFromJson = this.elementAdapter.fromJson(reader);
                Intrinsics.checkNotNull(tFromJson);
                c.add(tFromJson);
            }
        }
        reader.endArray();
        return c;
    }

    @Override // com.squareup.moshi.JsonAdapter
    public void toJson(JsonWriter writer, C value) throws IOException {
        Intrinsics.checkNotNullParameter(writer, "writer");
        writer.beginArray();
        Intrinsics.checkNotNull(value);
        Iterator it = value.iterator();
        while (it.hasNext()) {
            this.elementAdapter.toJson(writer, (T) it.next());
        }
        writer.endArray();
    }

    public String toString() {
        return this.elementAdapter + ".collection()";
    }

    /* JADX INFO: compiled from: MoshiArrayListJsonAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\t0\b\"\u0004\b\u0002\u0010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/lazyee/klib/json/MoshiArrayListJsonAdapter$Companion;", "", "()V", "FACTORY", "Lcom/squareup/moshi/JsonAdapter$Factory;", "getFACTORY", "()Lcom/squareup/moshi/JsonAdapter$Factory;", "newArrayListAdapter", "Lcom/squareup/moshi/JsonAdapter;", "", ExifInterface.GPS_DIRECTION_TRUE, "type", "Ljava/lang/reflect/Type;", "moshi", "Lcom/squareup/moshi/Moshi;", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final JsonAdapter.Factory getFACTORY() {
            return MoshiArrayListJsonAdapter.FACTORY;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final <T> JsonAdapter<Collection<T>> newArrayListAdapter(Type type, Moshi moshi) {
            final JsonAdapter<T> jsonAdapterAdapter = moshi.adapter(Types.collectionElementType(type, Collection.class));
            Intrinsics.checkNotNullExpressionValue(jsonAdapterAdapter, "moshi.adapter(elementType)");
            return new MoshiArrayListJsonAdapter<Collection<T>, T>(jsonAdapterAdapter) { // from class: com.lazyee.klib.json.MoshiArrayListJsonAdapter$Companion$newArrayListAdapter$1
                {
                    DefaultConstructorMarker defaultConstructorMarker = null;
                }

                @Override // com.lazyee.klib.json.MoshiArrayListJsonAdapter
                public Collection<T> newCollection() {
                    return new ArrayList();
                }
            };
        }
    }
}
