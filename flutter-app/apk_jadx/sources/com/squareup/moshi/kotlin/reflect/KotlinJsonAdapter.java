package com.squareup.moshi.kotlin.reflect;

import androidx.exifinterface.media.ExifInterface;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.internal.Util;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty1;

/* JADX INFO: compiled from: KotlinJsonAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0002 !BU\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u001c\u0010\u0005\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00070\u0006\u0012\u001a\u0010\t\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00070\u0006\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0015\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u0016H\u0016¢\u0006\u0002\u0010\u0017J\u001f\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010\u001dJ\b\u0010\u001e\u001a\u00020\u001fH\u0016R'\u0010\u0005\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R%\u0010\t\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\""}, d2 = {"Lcom/squareup/moshi/kotlin/reflect/KotlinJsonAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/squareup/moshi/JsonAdapter;", "constructor", "Lkotlin/reflect/KFunction;", "allBindings", "", "Lcom/squareup/moshi/kotlin/reflect/KotlinJsonAdapter$Binding;", "", "nonTransientBindings", "options", "Lcom/squareup/moshi/JsonReader$Options;", "(Lkotlin/reflect/KFunction;Ljava/util/List;Ljava/util/List;Lcom/squareup/moshi/JsonReader$Options;)V", "getAllBindings", "()Ljava/util/List;", "getConstructor", "()Lkotlin/reflect/KFunction;", "getNonTransientBindings", "getOptions", "()Lcom/squareup/moshi/JsonReader$Options;", "fromJson", "reader", "Lcom/squareup/moshi/JsonReader;", "(Lcom/squareup/moshi/JsonReader;)Ljava/lang/Object;", "toJson", "", "writer", "Lcom/squareup/moshi/JsonWriter;", "value", "(Lcom/squareup/moshi/JsonWriter;Ljava/lang/Object;)V", "toString", "", "Binding", "IndexedParameterMap", "reflect"}, k = 1, mv = {1, 4, 2})
public final class KotlinJsonAdapter<T> extends JsonAdapter<T> {
    private final List<Binding<T, Object>> allBindings;
    private final KFunction<T> constructor;
    private final List<Binding<T, Object>> nonTransientBindings;
    private final JsonReader.Options options;

    public final KFunction<T> getConstructor() {
        return this.constructor;
    }

    public final List<Binding<T, Object>> getAllBindings() {
        return this.allBindings;
    }

    public final List<Binding<T, Object>> getNonTransientBindings() {
        return this.nonTransientBindings;
    }

    public final JsonReader.Options getOptions() {
        return this.options;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinJsonAdapter(KFunction<? extends T> constructor, List<Binding<T, Object>> allBindings, List<Binding<T, Object>> nonTransientBindings, JsonReader.Options options) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(allBindings, "allBindings");
        Intrinsics.checkNotNullParameter(nonTransientBindings, "nonTransientBindings");
        Intrinsics.checkNotNullParameter(options, "options");
        this.constructor = constructor;
        this.allBindings = allBindings;
        this.nonTransientBindings = nonTransientBindings;
        this.options = options;
    }

    @Override // com.squareup.moshi.JsonAdapter
    public T fromJson(JsonReader reader) throws IOException {
        T tCallBy;
        Intrinsics.checkNotNullParameter(reader, "reader");
        int size = this.constructor.getParameters().size();
        int size2 = this.allBindings.size();
        Object[] objArr = new Object[size2];
        for (int i = 0; i < size2; i++) {
            objArr[i] = KotlinJsonAdapterKt.ABSENT_VALUE;
        }
        reader.beginObject();
        while (reader.hasNext()) {
            int iSelectName = reader.selectName(this.options);
            if (iSelectName == -1) {
                reader.skipName();
                reader.skipValue();
            } else {
                Binding<T, Object> binding = this.nonTransientBindings.get(iSelectName);
                int propertyIndex = binding.getPropertyIndex();
                if (objArr[propertyIndex] != KotlinJsonAdapterKt.ABSENT_VALUE) {
                    throw new JsonDataException("Multiple values for '" + binding.getProperty().getName() + "' at " + reader.getPath());
                }
                objArr[propertyIndex] = binding.getAdapter().fromJson(reader);
                if (objArr[propertyIndex] == null && !binding.getProperty().getReturnType().isMarkedNullable()) {
                    JsonDataException jsonDataExceptionUnexpectedNull = Util.unexpectedNull(binding.getProperty().getName(), binding.getJsonName(), reader);
                    Intrinsics.checkNotNullExpressionValue(jsonDataExceptionUnexpectedNull, "Util.unexpectedNull(\n   …         reader\n        )");
                    throw jsonDataExceptionUnexpectedNull;
                }
            }
        }
        reader.endObject();
        boolean z = this.allBindings.size() == size;
        for (int i2 = 0; i2 < size; i2++) {
            if (objArr[i2] == KotlinJsonAdapterKt.ABSENT_VALUE) {
                if (this.constructor.getParameters().get(i2).isOptional()) {
                    z = false;
                } else {
                    if (!this.constructor.getParameters().get(i2).getType().isMarkedNullable()) {
                        String name = this.constructor.getParameters().get(i2).getName();
                        Binding<T, Object> binding2 = this.allBindings.get(i2);
                        JsonDataException jsonDataExceptionMissingProperty = Util.missingProperty(name, binding2 != null ? binding2.getJsonName() : null, reader);
                        Intrinsics.checkNotNullExpressionValue(jsonDataExceptionMissingProperty, "Util.missingProperty(\n  …       reader\n          )");
                        throw jsonDataExceptionMissingProperty;
                    }
                    objArr[i2] = null;
                }
            }
        }
        if (z) {
            tCallBy = this.constructor.call(Arrays.copyOf(objArr, size2));
        } else {
            tCallBy = this.constructor.callBy(new IndexedParameterMap(this.constructor.getParameters(), objArr));
        }
        int size3 = this.allBindings.size();
        while (size < size3) {
            Binding binding3 = this.allBindings.get(size);
            Intrinsics.checkNotNull(binding3);
            binding3.set(tCallBy, objArr[size]);
            size++;
        }
        return tCallBy;
    }

    @Override // com.squareup.moshi.JsonAdapter
    public void toJson(JsonWriter writer, T value) throws IOException {
        Intrinsics.checkNotNullParameter(writer, "writer");
        if (value == null) {
            throw new NullPointerException("value == null");
        }
        writer.beginObject();
        for (Binding<T, Object> binding : this.allBindings) {
            if (binding != null) {
                writer.name(binding.getName());
                binding.getAdapter().toJson(writer, binding.get(value));
            }
        }
        writer.endObject();
    }

    public String toString() {
        return "KotlinJsonAdapter(" + this.constructor.getReturnType() + ')';
    }

    /* JADX INFO: compiled from: KotlinJsonAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\u00020\u0003BK\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00020\bHÆ\u0003J\u0015\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\nHÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\fHÆ\u0003J\t\u0010 \u001a\u00020\u000eHÆ\u0003Jg\u0010!\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00020\b2\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000eHÆ\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\u0013\u0010%\u001a\u00028\u00022\u0006\u0010&\u001a\u00028\u0001¢\u0006\u0002\u0010'J\t\u0010(\u001a\u00020\u000eHÖ\u0001J\u001b\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00028\u00012\u0006\u0010&\u001a\u00028\u0002¢\u0006\u0002\u0010,J\t\u0010-\u001a\u00020\u0005HÖ\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006."}, d2 = {"Lcom/squareup/moshi/kotlin/reflect/KotlinJsonAdapter$Binding;", "K", "P", "", "name", "", "jsonName", "adapter", "Lcom/squareup/moshi/JsonAdapter;", "property", "Lkotlin/reflect/KProperty1;", "parameter", "Lkotlin/reflect/KParameter;", "propertyIndex", "", "(Ljava/lang/String;Ljava/lang/String;Lcom/squareup/moshi/JsonAdapter;Lkotlin/reflect/KProperty1;Lkotlin/reflect/KParameter;I)V", "getAdapter", "()Lcom/squareup/moshi/JsonAdapter;", "getJsonName", "()Ljava/lang/String;", "getName", "getParameter", "()Lkotlin/reflect/KParameter;", "getProperty", "()Lkotlin/reflect/KProperty1;", "getPropertyIndex", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "get", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "set", "", "result", "(Ljava/lang/Object;Ljava/lang/Object;)V", "toString", "reflect"}, k = 1, mv = {1, 4, 2})
    public static final /* data */ class Binding<K, P> {
        private final JsonAdapter<P> adapter;
        private final String jsonName;
        private final String name;
        private final KParameter parameter;
        private final KProperty1<K, P> property;
        private final int propertyIndex;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Binding copy$default(Binding binding, String str, String str2, JsonAdapter jsonAdapter, KProperty1 kProperty1, KParameter kParameter, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = binding.name;
            }
            if ((i2 & 2) != 0) {
                str2 = binding.jsonName;
            }
            String str3 = str2;
            if ((i2 & 4) != 0) {
                jsonAdapter = binding.adapter;
            }
            JsonAdapter jsonAdapter2 = jsonAdapter;
            if ((i2 & 8) != 0) {
                kProperty1 = binding.property;
            }
            KProperty1 kProperty12 = kProperty1;
            if ((i2 & 16) != 0) {
                kParameter = binding.parameter;
            }
            KParameter kParameter2 = kParameter;
            if ((i2 & 32) != 0) {
                i = binding.propertyIndex;
            }
            return binding.copy(str, str3, jsonAdapter2, kProperty12, kParameter2, i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getJsonName() {
            return this.jsonName;
        }

        public final JsonAdapter<P> component3() {
            return this.adapter;
        }

        public final KProperty1<K, P> component4() {
            return this.property;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final KParameter getParameter() {
            return this.parameter;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final int getPropertyIndex() {
            return this.propertyIndex;
        }

        public final Binding<K, P> copy(String name, String jsonName, JsonAdapter<P> adapter, KProperty1<K, ? extends P> property, KParameter parameter, int propertyIndex) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            Intrinsics.checkNotNullParameter(property, "property");
            return new Binding<>(name, jsonName, adapter, property, parameter, propertyIndex);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Binding)) {
                return false;
            }
            Binding binding = (Binding) other;
            return Intrinsics.areEqual(this.name, binding.name) && Intrinsics.areEqual(this.jsonName, binding.jsonName) && Intrinsics.areEqual(this.adapter, binding.adapter) && Intrinsics.areEqual(this.property, binding.property) && Intrinsics.areEqual(this.parameter, binding.parameter) && this.propertyIndex == binding.propertyIndex;
        }

        public int hashCode() {
            String str = this.name;
            int iHashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.jsonName;
            int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            JsonAdapter<P> jsonAdapter = this.adapter;
            int iHashCode3 = (iHashCode2 + (jsonAdapter != null ? jsonAdapter.hashCode() : 0)) * 31;
            KProperty1<K, P> kProperty1 = this.property;
            int iHashCode4 = (iHashCode3 + (kProperty1 != null ? kProperty1.hashCode() : 0)) * 31;
            KParameter kParameter = this.parameter;
            return ((iHashCode4 + (kParameter != null ? kParameter.hashCode() : 0)) * 31) + this.propertyIndex;
        }

        public String toString() {
            return "Binding(name=" + this.name + ", jsonName=" + this.jsonName + ", adapter=" + this.adapter + ", property=" + this.property + ", parameter=" + this.parameter + ", propertyIndex=" + this.propertyIndex + ")";
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Binding(String name, String str, JsonAdapter<P> adapter, KProperty1<K, ? extends P> property, KParameter kParameter, int i) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            Intrinsics.checkNotNullParameter(property, "property");
            this.name = name;
            this.jsonName = str;
            this.adapter = adapter;
            this.property = property;
            this.parameter = kParameter;
            this.propertyIndex = i;
        }

        public final String getName() {
            return this.name;
        }

        public final String getJsonName() {
            return this.jsonName;
        }

        public final JsonAdapter<P> getAdapter() {
            return this.adapter;
        }

        public final KProperty1<K, P> getProperty() {
            return this.property;
        }

        public final KParameter getParameter() {
            return this.parameter;
        }

        public final int getPropertyIndex() {
            return this.propertyIndex;
        }

        public final P get(K value) {
            return this.property.get(value);
        }

        public final void set(K result, P value) {
            if (value != KotlinJsonAdapterKt.ABSENT_VALUE) {
                KProperty1<K, P> kProperty1 = this.property;
                Objects.requireNonNull(kProperty1, "null cannot be cast to non-null type kotlin.reflect.KMutableProperty1<K, P>");
                ((KMutableProperty1) kProperty1).set(result, value);
            }
        }
    }

    /* JADX INFO: compiled from: KotlinJsonAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B#\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0002H\u0016J\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0011\u001a\u00020\u0002H\u0096\u0002J\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0011\u001a\u00020\u00022\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u0016R(\u0010\t\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000e¨\u0006\u0015"}, d2 = {"Lcom/squareup/moshi/kotlin/reflect/KotlinJsonAdapter$IndexedParameterMap;", "Lkotlin/collections/AbstractMutableMap;", "Lkotlin/reflect/KParameter;", "", "parameterKeys", "", "parameterValues", "", "(Ljava/util/List;[Ljava/lang/Object;)V", "entries", "", "", "getEntries", "()Ljava/util/Set;", "[Ljava/lang/Object;", "containsKey", "", "key", "get", "put", "value", "reflect"}, k = 1, mv = {1, 4, 2})
    public static final class IndexedParameterMap extends AbstractMutableMap<KParameter, Object> {
        private final List<KParameter> parameterKeys;
        private final Object[] parameterValues;

        @Override // kotlin.collections.AbstractMutableMap, java.util.AbstractMap, java.util.Map
        public Object put(KParameter key, Object value) {
            Intrinsics.checkNotNullParameter(key, "key");
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final /* bridge */ boolean containsKey(Object obj) {
            if (obj instanceof KParameter) {
                return containsKey((KParameter) obj);
            }
            return false;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final /* bridge */ Object get(Object obj) {
            if (obj instanceof KParameter) {
                return get((KParameter) obj);
            }
            return null;
        }

        @Override // java.util.Map
        public final /* bridge */ Object getOrDefault(Object obj, Object obj2) {
            return obj instanceof KParameter ? getOrDefault((KParameter) obj, obj2) : obj2;
        }

        public /* bridge */ Object getOrDefault(KParameter kParameter, Object obj) {
            return super.getOrDefault((Object) kParameter, obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final /* bridge */ Object remove(Object obj) {
            if (obj instanceof KParameter) {
                return remove((KParameter) obj);
            }
            return null;
        }

        public /* bridge */ Object remove(KParameter kParameter) {
            return super.remove((Object) kParameter);
        }

        @Override // java.util.Map
        public final /* bridge */ boolean remove(Object obj, Object obj2) {
            if (obj instanceof KParameter) {
                return remove((KParameter) obj, obj2);
            }
            return false;
        }

        public /* bridge */ boolean remove(KParameter kParameter, Object obj) {
            return super.remove((Object) kParameter, obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public IndexedParameterMap(List<? extends KParameter> parameterKeys, Object[] parameterValues) {
            Intrinsics.checkNotNullParameter(parameterKeys, "parameterKeys");
            Intrinsics.checkNotNullParameter(parameterValues, "parameterValues");
            this.parameterKeys = parameterKeys;
            this.parameterValues = parameterValues;
        }

        @Override // kotlin.collections.AbstractMutableMap
        public Set<Map.Entry<KParameter, Object>> getEntries() {
            List<KParameter> list = this.parameterKeys;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            int i = 0;
            for (T t : list) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                arrayList.add(new AbstractMap.SimpleEntry((KParameter) t, this.parameterValues[i]));
                i = i2;
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (T t2 : arrayList) {
                if (((AbstractMap.SimpleEntry) t2).getValue() != KotlinJsonAdapterKt.ABSENT_VALUE) {
                    linkedHashSet.add(t2);
                }
            }
            return linkedHashSet;
        }

        public boolean containsKey(KParameter key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return this.parameterValues[key.getIndex()] != KotlinJsonAdapterKt.ABSENT_VALUE;
        }

        public Object get(KParameter key) {
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = this.parameterValues[key.getIndex()];
            if (obj != KotlinJsonAdapterKt.ABSENT_VALUE) {
                return obj;
            }
            return null;
        }
    }
}
