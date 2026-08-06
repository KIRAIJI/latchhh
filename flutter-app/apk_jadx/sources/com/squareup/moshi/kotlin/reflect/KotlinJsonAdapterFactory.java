package com.squareup.moshi.kotlin.reflect;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;
import com.squareup.moshi._MoshiKotlinTypesExtensionsKt;
import com.squareup.moshi.internal.Util;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.KCallablesJvm;
import kotlin.reflect.jvm.ReflectJvmMapping;

/* JADX INFO: compiled from: KotlinJsonAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/squareup/moshi/kotlin/reflect/KotlinJsonAdapterFactory;", "Lcom/squareup/moshi/JsonAdapter$Factory;", "()V", "create", "Lcom/squareup/moshi/JsonAdapter;", "type", "Ljava/lang/reflect/Type;", "annotations", "", "", "moshi", "Lcom/squareup/moshi/Moshi;", "reflect"}, k = 1, mv = {1, 4, 2})
public final class KotlinJsonAdapterFactory implements JsonAdapter.Factory {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r13v21 */
    /* JADX WARN: Type inference failed for: r13v8 */
    /* JADX WARN: Type inference failed for: r13v9 */
    @Override // com.squareup.moshi.JsonAdapter.Factory
    public JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
        Object next;
        String name;
        String strName;
        Object next2;
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(moshi, "moshi");
        boolean z = true;
        Object obj = null;
        if (!annotations.isEmpty()) {
            return null;
        }
        Class<?> rawType = _MoshiKotlinTypesExtensionsKt.getRawType(type);
        if (rawType.isInterface() || rawType.isEnum() || !rawType.isAnnotationPresent(KotlinJsonAdapterKt.KOTLIN_METADATA) || Util.isPlatformType(rawType)) {
            return null;
        }
        try {
            JsonAdapter<?> jsonAdapterGeneratedAdapter = Util.generatedAdapter(moshi, type, rawType);
            if (jsonAdapterGeneratedAdapter != null) {
                return jsonAdapterGeneratedAdapter;
            }
        } catch (RuntimeException e) {
            if (!(e.getCause() instanceof ClassNotFoundException)) {
                throw e;
            }
        }
        if (!(!rawType.isLocalClass())) {
            throw new IllegalArgumentException(("Cannot serialize local class or object expression " + rawType.getName()).toString());
        }
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(rawType);
        if (!(!kotlinClass.isAbstract())) {
            throw new IllegalArgumentException(("Cannot serialize abstract class " + rawType.getName()).toString());
        }
        if (!(!kotlinClass.isInner())) {
            throw new IllegalArgumentException(("Cannot serialize inner class " + rawType.getName()).toString());
        }
        int i = 0;
        if (!(kotlinClass.getObjectInstance() == null)) {
            throw new IllegalArgumentException(("Cannot serialize object declaration " + rawType.getName()).toString());
        }
        if (!(!kotlinClass.isSealed())) {
            throw new IllegalArgumentException(("Cannot reflectively serialize sealed class " + rawType.getName() + ". Please register an adapter.").toString());
        }
        KFunction primaryConstructor = KClasses.getPrimaryConstructor(kotlinClass);
        if (primaryConstructor == null) {
            return null;
        }
        List<KParameter> parameters = primaryConstructor.getParameters();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(parameters, 10)), 16));
        for (Object obj2 : parameters) {
            linkedHashMap.put(((KParameter) obj2).getName(), obj2);
        }
        KCallablesJvm.setAccessible(primaryConstructor, true);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (KProperty1 kProperty1 : KClasses.getMemberProperties(kotlinClass)) {
            KParameter kParameter = (KParameter) linkedHashMap.get(kProperty1.getName());
            Field javaField = ReflectJvmMapping.getJavaField(kProperty1);
            if (Modifier.isTransient(javaField != null ? javaField.getModifiers() : i)) {
                if (((kParameter == null || kParameter.isOptional()) ? z : i) == 0) {
                    throw new IllegalArgumentException(("No default value for transient constructor " + kParameter).toString());
                }
            } else {
                if (((kParameter == null || Intrinsics.areEqual(kParameter.getType(), kProperty1.getReturnType())) ? z : i) == 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append('\'');
                    sb.append(kProperty1.getName());
                    sb.append("' has a constructor parameter of type ");
                    Intrinsics.checkNotNull(kParameter);
                    sb.append(kParameter.getType());
                    sb.append(" but a property of type ");
                    sb.append(kProperty1.getReturnType());
                    sb.append('.');
                    throw new IllegalArgumentException(sb.toString().toString());
                }
                if ((kProperty1 instanceof KMutableProperty1) || kParameter != null) {
                    KCallablesJvm.setAccessible(kProperty1, z);
                    List mutableList = CollectionsKt.toMutableList((Collection) kProperty1.getAnnotations());
                    Iterator it = kProperty1.getAnnotations().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = obj;
                            break;
                        }
                        next = it.next();
                        if (((Annotation) next) instanceof Json) {
                            break;
                        }
                    }
                    Json json = (Json) next;
                    if (kParameter != null) {
                        CollectionsKt.addAll(mutableList, kParameter.getAnnotations());
                        if (json == null) {
                            Iterator it2 = kParameter.getAnnotations().iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    next2 = null;
                                    break;
                                }
                                next2 = it2.next();
                                if (((Annotation) next2) instanceof Json) {
                                    break;
                                }
                            }
                            json = (Json) next2;
                        }
                    }
                    if (json == null || (name = json.name()) == null) {
                        name = kProperty1.getName();
                    }
                    String str = name;
                    Type typeResolve = Util.resolve(type, rawType, ReflectJvmMapping.getJavaType(kProperty1.getReturnType()));
                    Object[] array = mutableList.toArray(new Annotation[i]);
                    Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T>");
                    JsonAdapter adapter = moshi.adapter(typeResolve, Util.jsonAnnotations((Annotation[]) array), kProperty1.getName());
                    LinkedHashMap linkedHashMap3 = linkedHashMap2;
                    String name2 = kProperty1.getName();
                    if (json == null || (strName = json.name()) == null) {
                        strName = str;
                    }
                    Intrinsics.checkNotNullExpressionValue(adapter, "adapter");
                    Objects.requireNonNull(kProperty1, "null cannot be cast to non-null type kotlin.reflect.KProperty1<kotlin.Any, kotlin.Any?>");
                    linkedHashMap3.put(name2, new KotlinJsonAdapter.Binding(str, strName, adapter, kProperty1, kParameter, kParameter != null ? kParameter.getIndex() : -1));
                }
            }
            z = true;
            obj = null;
            i = 0;
        }
        ArrayList arrayList = new ArrayList();
        for (KParameter kParameter2 : primaryConstructor.getParameters()) {
            KotlinJsonAdapter.Binding binding = (KotlinJsonAdapter.Binding) TypeIntrinsics.asMutableMap(linkedHashMap2).remove(kParameter2.getName());
            if (!(binding != null || kParameter2.isOptional())) {
                throw new IllegalArgumentException(("No property for required constructor " + kParameter2).toString());
            }
            arrayList.add(binding);
        }
        int size = arrayList.size();
        Iterator it3 = linkedHashMap2.entrySet().iterator();
        while (true) {
            int i2 = size;
            if (!it3.hasNext()) {
                break;
            }
            size = i2 + 1;
            arrayList.add(KotlinJsonAdapter.Binding.copy$default((KotlinJsonAdapter.Binding) ((Map.Entry) it3.next()).getValue(), null, null, null, null, null, i2, 31, null));
        }
        List listFilterNotNull = CollectionsKt.filterNotNull(arrayList);
        List list = listFilterNotNull;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it4 = list.iterator();
        while (it4.hasNext()) {
            arrayList2.add(((KotlinJsonAdapter.Binding) it4.next()).getName());
        }
        Object[] array2 = arrayList2.toArray(new String[0]);
        Objects.requireNonNull(array2, "null cannot be cast to non-null type kotlin.Array<T>");
        String[] strArr = (String[]) array2;
        JsonReader.Options options = JsonReader.Options.of((String[]) Arrays.copyOf(strArr, strArr.length));
        Intrinsics.checkNotNullExpressionValue(options, "options");
        return new KotlinJsonAdapter(primaryConstructor, arrayList, listFilterNotNull, options).nullSafe();
    }
}
