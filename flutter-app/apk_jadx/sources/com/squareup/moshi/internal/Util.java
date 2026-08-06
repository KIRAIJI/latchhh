package com.squareup.moshi.internal;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonClass;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import okhttp3.HttpUrl;

/* JADX INFO: loaded from: classes.dex */
public final class Util {

    @Nullable
    public static final Class<?> DEFAULT_CONSTRUCTOR_MARKER;

    @Nullable
    private static final Class<? extends Annotation> METADATA;
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
    public static final Set<Annotation> NO_ANNOTATIONS = Collections.emptySet();
    public static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    private static String getKotlinMetadataClassName() {
        return "kotlin.Metadata";
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        Class<? extends Annotation> cls;
        Class<?> cls2 = null;
        try {
            cls = Class.forName(getKotlinMetadataClassName());
        } catch (ClassNotFoundException unused) {
            cls = 0;
        }
        METADATA = cls;
        try {
            cls2 = Class.forName("kotlin.jvm.internal.DefaultConstructorMarker");
        } catch (ClassNotFoundException unused2) {
        }
        DEFAULT_CONSTRUCTOR_MARKER = cls2;
        LinkedHashMap linkedHashMap = new LinkedHashMap(16);
        linkedHashMap.put(Boolean.TYPE, Boolean.class);
        linkedHashMap.put(Byte.TYPE, Byte.class);
        linkedHashMap.put(Character.TYPE, Character.class);
        linkedHashMap.put(Double.TYPE, Double.class);
        linkedHashMap.put(Float.TYPE, Float.class);
        linkedHashMap.put(Integer.TYPE, Integer.class);
        linkedHashMap.put(Long.TYPE, Long.class);
        linkedHashMap.put(Short.TYPE, Short.class);
        linkedHashMap.put(Void.TYPE, Void.class);
        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(linkedHashMap);
    }

    private Util() {
    }

    public static boolean typesMatch(Type type, Type type2) {
        return Types.equals(type, type2);
    }

    public static Set<? extends Annotation> jsonAnnotations(AnnotatedElement annotatedElement) {
        return jsonAnnotations(annotatedElement.getAnnotations());
    }

    public static Set<? extends Annotation> jsonAnnotations(Annotation[] annotationArr) {
        LinkedHashSet linkedHashSet = null;
        for (Annotation annotation : annotationArr) {
            if (annotation.annotationType().isAnnotationPresent(JsonQualifier.class)) {
                if (linkedHashSet == null) {
                    linkedHashSet = new LinkedHashSet();
                }
                linkedHashSet.add(annotation);
            }
        }
        return linkedHashSet != null ? Collections.unmodifiableSet(linkedHashSet) : NO_ANNOTATIONS;
    }

    public static boolean isAnnotationPresent(Set<? extends Annotation> set, Class<? extends Annotation> cls) {
        if (set.isEmpty()) {
            return false;
        }
        Iterator<? extends Annotation> it = set.iterator();
        while (it.hasNext()) {
            if (it.next().annotationType() == cls) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNullable(Annotation[] annotationArr) {
        for (Annotation annotation : annotationArr) {
            if (annotation.annotationType().getSimpleName().equals("Nullable")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPlatformType(Class<?> cls) {
        String name = cls.getName();
        return name.startsWith("android.") || name.startsWith("androidx.") || name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("kotlin.") || name.startsWith("kotlinx.") || name.startsWith("scala.");
    }

    public static RuntimeException rethrowCause(InvocationTargetException invocationTargetException) {
        Throwable targetException = invocationTargetException.getTargetException();
        if (targetException instanceof RuntimeException) {
            throw ((RuntimeException) targetException);
        }
        if (targetException instanceof Error) {
            throw ((Error) targetException);
        }
        throw new RuntimeException(targetException);
    }

    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            return cls.isArray() ? new GenericArrayTypeImpl(canonicalize(cls.getComponentType())) : cls;
        }
        if (type instanceof ParameterizedType) {
            if (type instanceof ParameterizedTypeImpl) {
                return type;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new ParameterizedTypeImpl(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            return type instanceof GenericArrayTypeImpl ? type : new GenericArrayTypeImpl(((GenericArrayType) type).getGenericComponentType());
        }
        if (!(type instanceof WildcardType) || (type instanceof WildcardTypeImpl)) {
            return type;
        }
        WildcardType wildcardType = (WildcardType) type;
        return new WildcardTypeImpl(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
    }

    public static Type removeSubtypeWildcard(Type type) {
        if (!(type instanceof WildcardType)) {
            return type;
        }
        WildcardType wildcardType = (WildcardType) type;
        if (wildcardType.getLowerBounds().length != 0) {
            return type;
        }
        Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length != 1) {
            throw new IllegalArgumentException();
        }
        return upperBounds[0];
    }

    public static Type resolve(Type type, Class<?> cls, Type type2) {
        return resolve(type, cls, type2, new LinkedHashSet());
    }

    private static Type resolve(Type type, Class<?> cls, Type type2, Collection<TypeVariable<?>> collection) {
        while (type2 instanceof TypeVariable) {
            TypeVariable<?> typeVariable = (TypeVariable) type2;
            if (collection.contains(typeVariable)) {
                return type2;
            }
            collection.add(typeVariable);
            type2 = resolveTypeVariable(type, cls, typeVariable);
            if (type2 == typeVariable) {
                return type2;
            }
        }
        if (type2 instanceof Class) {
            Class cls2 = (Class) type2;
            if (cls2.isArray()) {
                Class<?> componentType = cls2.getComponentType();
                Type typeResolve = resolve(type, cls, componentType, collection);
                return componentType == typeResolve ? cls2 : Types.arrayOf(typeResolve);
            }
        }
        if (type2 instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type2;
            Type genericComponentType = genericArrayType.getGenericComponentType();
            Type typeResolve2 = resolve(type, cls, genericComponentType, collection);
            return genericComponentType == typeResolve2 ? genericArrayType : Types.arrayOf(typeResolve2);
        }
        if (type2 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type2;
            Type ownerType = parameterizedType.getOwnerType();
            Type typeResolve3 = resolve(type, cls, ownerType, collection);
            boolean z = typeResolve3 != ownerType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            int length = actualTypeArguments.length;
            for (int i = 0; i < length; i++) {
                Type typeResolve4 = resolve(type, cls, actualTypeArguments[i], collection);
                if (typeResolve4 != actualTypeArguments[i]) {
                    if (!z) {
                        actualTypeArguments = (Type[]) actualTypeArguments.clone();
                        z = true;
                    }
                    actualTypeArguments[i] = typeResolve4;
                }
            }
            return z ? new ParameterizedTypeImpl(typeResolve3, parameterizedType.getRawType(), actualTypeArguments) : parameterizedType;
        }
        boolean z2 = type2 instanceof WildcardType;
        Type type3 = type2;
        if (z2) {
            WildcardType wildcardType = (WildcardType) type2;
            Type[] lowerBounds = wildcardType.getLowerBounds();
            Type[] upperBounds = wildcardType.getUpperBounds();
            if (lowerBounds.length == 1) {
                Type typeResolve5 = resolve(type, cls, lowerBounds[0], collection);
                type3 = wildcardType;
                if (typeResolve5 != lowerBounds[0]) {
                    return Types.supertypeOf(typeResolve5);
                }
            } else {
                type3 = wildcardType;
                if (upperBounds.length == 1) {
                    Type typeResolve6 = resolve(type, cls, upperBounds[0], collection);
                    type3 = wildcardType;
                    if (typeResolve6 != upperBounds[0]) {
                        return Types.subtypeOf(typeResolve6);
                    }
                }
            }
        }
        return type3;
    }

    static Type resolveTypeVariable(Type type, Class<?> cls, TypeVariable<?> typeVariable) {
        Class<?> clsDeclaringClassOf = declaringClassOf(typeVariable);
        if (clsDeclaringClassOf == null) {
            return typeVariable;
        }
        Type genericSupertype = getGenericSupertype(type, cls, clsDeclaringClassOf);
        if (!(genericSupertype instanceof ParameterizedType)) {
            return typeVariable;
        }
        return ((ParameterizedType) genericSupertype).getActualTypeArguments()[indexOf(clsDeclaringClassOf.getTypeParameters(), typeVariable)];
    }

    public static Type getGenericSupertype(Type type, Class<?> cls, Class<?> cls2) {
        if (cls2 == cls) {
            return type;
        }
        if (cls2.isInterface()) {
            Class<?>[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == cls2) {
                    return cls.getGenericInterfaces()[i];
                }
                if (cls2.isAssignableFrom(interfaces[i])) {
                    return getGenericSupertype(cls.getGenericInterfaces()[i], interfaces[i], cls2);
                }
            }
        }
        if (!cls.isInterface()) {
            while (cls != Object.class) {
                Class<? super Object> superclass = cls.getSuperclass();
                if (superclass == cls2) {
                    return cls.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    return getGenericSupertype(cls.getGenericSuperclass(), superclass, cls2);
                }
                cls = superclass;
            }
        }
        return cls2;
    }

    static int hashCodeOrZero(@Nullable Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    static String typeToString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    static int indexOf(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (obj.equals(objArr[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    @Nullable
    static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    static void checkNotPrimitive(Type type) {
        if ((type instanceof Class) && ((Class) type).isPrimitive()) {
            throw new IllegalArgumentException("Unexpected primitive " + type + ". Use the boxed type.");
        }
    }

    public static final class ParameterizedTypeImpl implements ParameterizedType {

        @Nullable
        private final Type ownerType;
        private final Type rawType;
        public final Type[] typeArguments;

        public ParameterizedTypeImpl(@Nullable Type type, Type type2, Type... typeArr) {
            if (type2 instanceof Class) {
                Class<?> enclosingClass = ((Class) type2).getEnclosingClass();
                if (type != null) {
                    if (enclosingClass == null || Types.getRawType(type) != enclosingClass) {
                        throw new IllegalArgumentException("unexpected owner type for " + type2 + ": " + type);
                    }
                } else if (enclosingClass != null) {
                    throw new IllegalArgumentException("unexpected owner type for " + type2 + ": null");
                }
            }
            this.ownerType = type == null ? null : Util.canonicalize(type);
            this.rawType = Util.canonicalize(type2);
            this.typeArguments = (Type[]) typeArr.clone();
            int i = 0;
            while (true) {
                Type[] typeArr2 = this.typeArguments;
                if (i >= typeArr2.length) {
                    return;
                }
                Objects.requireNonNull(typeArr2[i]);
                Util.checkNotPrimitive(typeArr2[i]);
                Type[] typeArr3 = this.typeArguments;
                typeArr3[i] = Util.canonicalize(typeArr3[i]);
                i++;
            }
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type[] getActualTypeArguments() {
            return (Type[]) this.typeArguments.clone();
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getRawType() {
            return this.rawType;
        }

        @Override // java.lang.reflect.ParameterizedType
        @Nullable
        public Type getOwnerType() {
            return this.ownerType;
        }

        public boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && Types.equals(this, (ParameterizedType) obj);
        }

        public int hashCode() {
            return (Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode()) ^ Util.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder((this.typeArguments.length + 1) * 30);
            sb.append(Util.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return sb.toString();
            }
            sb.append("<");
            sb.append(Util.typeToString(this.typeArguments[0]));
            for (int i = 1; i < this.typeArguments.length; i++) {
                sb.append(", ");
                sb.append(Util.typeToString(this.typeArguments[i]));
            }
            sb.append(">");
            return sb.toString();
        }
    }

    public static final class GenericArrayTypeImpl implements GenericArrayType {
        private final Type componentType;

        public GenericArrayTypeImpl(Type type) {
            this.componentType = Util.canonicalize(type);
        }

        @Override // java.lang.reflect.GenericArrayType
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && Types.equals(this, (GenericArrayType) obj);
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return Util.typeToString(this.componentType) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
    }

    public static final class WildcardTypeImpl implements WildcardType {

        @Nullable
        private final Type lowerBound;
        private final Type upperBound;

        public WildcardTypeImpl(Type[] typeArr, Type[] typeArr2) {
            if (typeArr2.length > 1) {
                throw new IllegalArgumentException();
            }
            if (typeArr.length != 1) {
                throw new IllegalArgumentException();
            }
            if (typeArr2.length == 1) {
                Objects.requireNonNull(typeArr2[0]);
                Util.checkNotPrimitive(typeArr2[0]);
                if (typeArr[0] != Object.class) {
                    throw new IllegalArgumentException();
                }
                this.lowerBound = Util.canonicalize(typeArr2[0]);
                this.upperBound = Object.class;
                return;
            }
            Objects.requireNonNull(typeArr[0]);
            Util.checkNotPrimitive(typeArr[0]);
            this.lowerBound = null;
            this.upperBound = Util.canonicalize(typeArr[0]);
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getLowerBounds() {
            Type type = this.lowerBound;
            return type != null ? new Type[]{type} : Util.EMPTY_TYPE_ARRAY;
        }

        public boolean equals(Object obj) {
            return (obj instanceof WildcardType) && Types.equals(this, (WildcardType) obj);
        }

        public int hashCode() {
            Type type = this.lowerBound;
            return (type != null ? type.hashCode() + 31 : 1) ^ (this.upperBound.hashCode() + 31);
        }

        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + Util.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + Util.typeToString(this.upperBound);
        }
    }

    public static String typeAnnotatedWithAnnotations(Type type, Set<? extends Annotation> set) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        if (set.isEmpty()) {
            str = " (with no annotations)";
        } else {
            str = " annotated " + set;
        }
        sb.append(str);
        return sb.toString();
    }

    @Nullable
    public static JsonAdapter<?> generatedAdapter(Moshi moshi, Type type, Class<?> cls) {
        Constructor<?> declaredConstructor;
        Object[] objArr;
        JsonClass jsonClass = (JsonClass) cls.getAnnotation(JsonClass.class);
        Class<?> cls2 = null;
        if (jsonClass == null || !jsonClass.generateAdapter()) {
            return null;
        }
        try {
            try {
                cls2 = Class.forName(Types.generatedJsonAdapterName(cls.getName()), true, cls.getClassLoader());
                if (type instanceof ParameterizedType) {
                    Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                    try {
                        declaredConstructor = cls2.getDeclaredConstructor(Moshi.class, Type[].class);
                        objArr = new Object[]{moshi, actualTypeArguments};
                    } catch (NoSuchMethodException unused) {
                        declaredConstructor = cls2.getDeclaredConstructor(Type[].class);
                        objArr = new Object[]{actualTypeArguments};
                    }
                } else {
                    try {
                        declaredConstructor = cls2.getDeclaredConstructor(Moshi.class);
                        objArr = new Object[]{moshi};
                    } catch (NoSuchMethodException unused2) {
                        declaredConstructor = cls2.getDeclaredConstructor(new Class[0]);
                        objArr = new Object[0];
                    }
                }
                declaredConstructor.setAccessible(true);
                return ((JsonAdapter) declaredConstructor.newInstance(objArr)).nullSafe();
            } catch (NoSuchMethodException e) {
                if (!(type instanceof ParameterizedType) && cls2.getTypeParameters().length != 0) {
                    throw new RuntimeException("Failed to find the generated JsonAdapter constructor for '" + type + "'. Suspiciously, the type was not parameterized but the target class '" + cls2.getCanonicalName() + "' is generic. Consider using Types#newParameterizedType() to define these missing type variables.", e);
                }
                throw new RuntimeException("Failed to find the generated JsonAdapter constructor for " + type, e);
            }
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException("Failed to find the generated JsonAdapter class for " + type, e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException("Failed to access the generated JsonAdapter for " + type, e3);
        } catch (InstantiationException e4) {
            throw new RuntimeException("Failed to instantiate the generated JsonAdapter for " + type, e4);
        } catch (InvocationTargetException e5) {
            throw rethrowCause(e5);
        }
    }

    public static boolean isKotlin(Class<?> cls) {
        Class<? extends Annotation> cls2 = METADATA;
        return cls2 != null && cls.isAnnotationPresent(cls2);
    }

    public static <T> Constructor<T> lookupDefaultsConstructor(Class<T> cls) {
        if (DEFAULT_CONSTRUCTOR_MARKER == null) {
            throw new IllegalStateException("DefaultConstructorMarker not on classpath. Make sure the Kotlin stdlib is on the classpath.");
        }
        Constructor<T> constructorFindConstructor = findConstructor(cls);
        constructorFindConstructor.setAccessible(true);
        return constructorFindConstructor;
    }

    private static <T> Constructor<T> findConstructor(Class<T> cls) {
        for (Object obj : cls.getDeclaredConstructors()) {
            Constructor<T> constructor = (Constructor<T>) obj;
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length != 0 && parameterTypes[parameterTypes.length - 1].equals(DEFAULT_CONSTRUCTOR_MARKER)) {
                return constructor;
            }
        }
        throw new IllegalStateException("No defaults constructor found for " + cls);
    }

    public static JsonDataException missingProperty(String str, String str2, JsonReader jsonReader) {
        String str3;
        String path = jsonReader.getPath();
        if (str2.equals(str)) {
            str3 = String.format("Required value '%s' missing at %s", str, path);
        } else {
            str3 = String.format("Required value '%s' (JSON name '%s') missing at %s", str, str2, path);
        }
        return new JsonDataException(str3);
    }

    public static JsonDataException unexpectedNull(String str, String str2, JsonReader jsonReader) {
        String str3;
        String path = jsonReader.getPath();
        if (str2.equals(str)) {
            str3 = String.format("Non-null value '%s' was null at %s", str, path);
        } else {
            str3 = String.format("Non-null value '%s' (JSON name '%s') was null at %s", str, str2, path);
        }
        return new JsonDataException(str3);
    }

    public static <T> Class<T> boxIfPrimitive(Class<T> cls) {
        Class<T> cls2 = (Class) PRIMITIVE_TO_WRAPPER_TYPE.get(cls);
        return cls2 == null ? cls : cls2;
    }
}
