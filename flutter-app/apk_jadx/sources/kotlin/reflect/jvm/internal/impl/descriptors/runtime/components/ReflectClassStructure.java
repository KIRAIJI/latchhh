package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: ReflectKotlinClass.kt */
/* JADX INFO: loaded from: classes2.dex */
final class ReflectClassStructure {
    public static final ReflectClassStructure INSTANCE = new ReflectClassStructure();

    private ReflectClassStructure() {
    }

    public final void loadClassAnnotations(Class<?> klass, KotlinJvmBinaryClass.AnnotationVisitor visitor) throws InvocationTargetException {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Annotation[] declaredAnnotations = klass.getDeclaredAnnotations();
        Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "klass.declaredAnnotations");
        int length = declaredAnnotations.length;
        int i = 0;
        while (i < length) {
            Annotation annotation = declaredAnnotations[i];
            i++;
            Intrinsics.checkNotNullExpressionValue(annotation, "annotation");
            processAnnotation(visitor, annotation);
        }
        visitor.visitEnd();
    }

    public final void visitMembers(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(memberVisitor, "memberVisitor");
        loadMethodAnnotations(klass, memberVisitor);
        loadConstructorAnnotations(klass, memberVisitor);
        loadFieldAnnotations(klass, memberVisitor);
    }

    private final void loadMethodAnnotations(Class<?> cls, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Method[] methodArr;
        int i;
        Method[] declaredMethods = cls.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "klass.declaredMethods");
        int length = declaredMethods.length;
        int i2 = 0;
        while (i2 < length) {
            Method method = declaredMethods[i2];
            i2++;
            Name nameIdentifier = Name.identifier(method.getName());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(method.name)");
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(method, "method");
            KotlinJvmBinaryClass.MethodAnnotationVisitor methodAnnotationVisitorVisitMethod = memberVisitor.visitMethod(nameIdentifier, signatureSerializer.methodDesc(method));
            if (methodAnnotationVisitorVisitMethod == null) {
                methodArr = declaredMethods;
                i = length;
            } else {
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "method.declaredAnnotations");
                int length2 = declaredAnnotations.length;
                int i3 = 0;
                while (i3 < length2) {
                    Annotation annotation = declaredAnnotations[i3];
                    i3++;
                    Intrinsics.checkNotNullExpressionValue(annotation, "annotation");
                    processAnnotation(methodAnnotationVisitorVisitMethod, annotation);
                }
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                Intrinsics.checkNotNullExpressionValue(parameterAnnotations, "method.parameterAnnotations");
                Annotation[][] annotationArr = parameterAnnotations;
                int length3 = annotationArr.length;
                int i4 = 0;
                while (i4 < length3) {
                    Annotation[] annotations = annotationArr[i4];
                    int i5 = i4 + 1;
                    Intrinsics.checkNotNullExpressionValue(annotations, "annotations");
                    int length4 = annotations.length;
                    int i6 = 0;
                    while (i6 < length4) {
                        Annotation annotation2 = annotations[i6];
                        i6++;
                        Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation2));
                        Method[] methodArr2 = declaredMethods;
                        ClassId classId = ReflectClassUtilKt.getClassId(javaClass);
                        int i7 = length;
                        Intrinsics.checkNotNullExpressionValue(annotation2, "annotation");
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitParameterAnnotation = methodAnnotationVisitorVisitMethod.visitParameterAnnotation(i4, classId, new ReflectAnnotationSource(annotation2));
                        if (annotationArgumentVisitorVisitParameterAnnotation != null) {
                            processAnnotationArguments(annotationArgumentVisitorVisitParameterAnnotation, annotation2, javaClass);
                        }
                        declaredMethods = methodArr2;
                        length = i7;
                    }
                    i4 = i5;
                }
                methodArr = declaredMethods;
                i = length;
                methodAnnotationVisitorVisitMethod.visitEnd();
            }
            declaredMethods = methodArr;
            length = i;
        }
    }

    private final void loadConstructorAnnotations(Class<?> cls, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Constructor<?>[] constructorArr;
        int i;
        int i2;
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        Intrinsics.checkNotNullExpressionValue(declaredConstructors, "klass.declaredConstructors");
        int length = declaredConstructors.length;
        int i3 = 0;
        while (i3 < length) {
            Constructor<?> constructor = declaredConstructors[i3];
            int i4 = i3 + 1;
            Name nameSpecial = Name.special("<init>");
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(\"<init>\")");
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(constructor, "constructor");
            KotlinJvmBinaryClass.MethodAnnotationVisitor methodAnnotationVisitorVisitMethod = memberVisitor.visitMethod(nameSpecial, signatureSerializer.constructorDesc(constructor));
            if (methodAnnotationVisitorVisitMethod == null) {
                constructorArr = declaredConstructors;
                i = length;
                i2 = i4;
            } else {
                Annotation[] declaredAnnotations = constructor.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "constructor.declaredAnnotations");
                int length2 = declaredAnnotations.length;
                int i5 = 0;
                while (i5 < length2) {
                    Annotation annotation = declaredAnnotations[i5];
                    i5++;
                    Intrinsics.checkNotNullExpressionValue(annotation, "annotation");
                    processAnnotation(methodAnnotationVisitorVisitMethod, annotation);
                }
                Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
                Intrinsics.checkNotNullExpressionValue(parameterAnnotations, "parameterAnnotations");
                Annotation[][] annotationArr = parameterAnnotations;
                if (!(annotationArr.length == 0)) {
                    int length3 = constructor.getParameterTypes().length - annotationArr.length;
                    int length4 = annotationArr.length;
                    int i6 = 0;
                    while (i6 < length4) {
                        Annotation[] annotations = parameterAnnotations[i6];
                        int i7 = i6 + 1;
                        Intrinsics.checkNotNullExpressionValue(annotations, "annotations");
                        int length5 = annotations.length;
                        int i8 = 0;
                        while (i8 < length5) {
                            Annotation annotation2 = annotations[i8];
                            i8++;
                            Constructor<?>[] constructorArr2 = declaredConstructors;
                            Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation2));
                            int i9 = length;
                            int i10 = i4;
                            ClassId classId = ReflectClassUtilKt.getClassId(javaClass);
                            int i11 = length3;
                            Intrinsics.checkNotNullExpressionValue(annotation2, "annotation");
                            KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitParameterAnnotation = methodAnnotationVisitorVisitMethod.visitParameterAnnotation(i6 + length3, classId, new ReflectAnnotationSource(annotation2));
                            if (annotationArgumentVisitorVisitParameterAnnotation != null) {
                                processAnnotationArguments(annotationArgumentVisitorVisitParameterAnnotation, annotation2, javaClass);
                            }
                            length = i9;
                            declaredConstructors = constructorArr2;
                            i4 = i10;
                            length3 = i11;
                        }
                        i6 = i7;
                    }
                }
                constructorArr = declaredConstructors;
                i = length;
                i2 = i4;
                methodAnnotationVisitorVisitMethod.visitEnd();
            }
            length = i;
            declaredConstructors = constructorArr;
            i3 = i2;
        }
    }

    private final void loadFieldAnnotations(Class<?> cls, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Field[] declaredFields = cls.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "klass.declaredFields");
        int length = declaredFields.length;
        int i = 0;
        while (i < length) {
            Field field = declaredFields[i];
            i++;
            Name nameIdentifier = Name.identifier(field.getName());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(field.name)");
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(field, "field");
            KotlinJvmBinaryClass.AnnotationVisitor annotationVisitorVisitField = memberVisitor.visitField(nameIdentifier, signatureSerializer.fieldDesc(field), null);
            if (annotationVisitorVisitField != null) {
                Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "field.declaredAnnotations");
                int length2 = declaredAnnotations.length;
                int i2 = 0;
                while (i2 < length2) {
                    Annotation annotation = declaredAnnotations[i2];
                    i2++;
                    Intrinsics.checkNotNullExpressionValue(annotation, "annotation");
                    processAnnotation(annotationVisitorVisitField, annotation);
                }
                annotationVisitorVisitField.visitEnd();
            }
        }
    }

    private final void processAnnotation(KotlinJvmBinaryClass.AnnotationVisitor annotationVisitor, Annotation annotation) throws InvocationTargetException {
        Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation));
        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = annotationVisitor.visitAnnotation(ReflectClassUtilKt.getClassId(javaClass), new ReflectAnnotationSource(annotation));
        if (annotationArgumentVisitorVisitAnnotation == null) {
            return;
        }
        processAnnotationArguments(annotationArgumentVisitorVisitAnnotation, annotation, javaClass);
    }

    private final void processAnnotationArguments(KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor, Annotation annotation, Class<?> cls) throws InvocationTargetException {
        Method[] declaredMethods = cls.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "annotationType.declaredMethods");
        int length = declaredMethods.length;
        int i = 0;
        while (i < length) {
            Method method = declaredMethods[i];
            i++;
            try {
                Object objInvoke = method.invoke(annotation, new Object[0]);
                Intrinsics.checkNotNull(objInvoke);
                Name nameIdentifier = Name.identifier(method.getName());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(method.name)");
                processAnnotationArgumentValue(annotationArgumentVisitor, nameIdentifier, objInvoke);
            } catch (IllegalAccessException unused) {
            }
        }
        annotationArgumentVisitor.visitEnd();
    }

    private final ClassLiteralValue classLiteralValue(Class<?> cls) {
        int i = 0;
        while (cls.isArray()) {
            i++;
            cls = cls.getComponentType();
            Intrinsics.checkNotNullExpressionValue(cls, "currentClass.componentType");
        }
        if (cls.isPrimitive()) {
            if (Intrinsics.areEqual(cls, Void.TYPE)) {
                ClassId classId = ClassId.topLevel(StandardNames.FqNames.unit.toSafe());
                Intrinsics.checkNotNullExpressionValue(classId, "topLevel(StandardNames.FqNames.unit.toSafe())");
                return new ClassLiteralValue(classId, i);
            }
            PrimitiveType primitiveType = JvmPrimitiveType.get(cls.getName()).getPrimitiveType();
            Intrinsics.checkNotNullExpressionValue(primitiveType, "get(currentClass.name).primitiveType");
            if (i > 0) {
                ClassId classId2 = ClassId.topLevel(primitiveType.getArrayTypeFqName());
                Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(primitiveType.arrayTypeFqName)");
                return new ClassLiteralValue(classId2, i - 1);
            }
            ClassId classId3 = ClassId.topLevel(primitiveType.getTypeFqName());
            Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(primitiveType.typeFqName)");
            return new ClassLiteralValue(classId3, i);
        }
        ClassId classId4 = ReflectClassUtilKt.getClassId(cls);
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqName fqNameAsSingleFqName = classId4.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "javaClassId.asSingleFqName()");
        ClassId classIdMapJavaToKotlin = javaToKotlinClassMap.mapJavaToKotlin(fqNameAsSingleFqName);
        if (classIdMapJavaToKotlin != null) {
            classId4 = classIdMapJavaToKotlin;
        }
        return new ClassLiteralValue(classId4, i);
    }

    private final void processAnnotationArgumentValue(KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor, Name name, Object obj) throws InvocationTargetException {
        Class<?> enclosingClass = obj.getClass();
        if (!Intrinsics.areEqual(enclosingClass, Class.class)) {
            if (ReflectKotlinClassKt.TYPES_ELIGIBLE_FOR_SIMPLE_VISIT.contains(enclosingClass)) {
                annotationArgumentVisitor.visit(name, obj);
                return;
            }
            if (ReflectClassUtilKt.isEnumClassOrSpecializedEnumEntryClass(enclosingClass)) {
                if (!enclosingClass.isEnum()) {
                    enclosingClass = enclosingClass.getEnclosingClass();
                }
                Intrinsics.checkNotNullExpressionValue(enclosingClass, "if (clazz.isEnum) clazz else clazz.enclosingClass");
                ClassId classId = ReflectClassUtilKt.getClassId(enclosingClass);
                Name nameIdentifier = Name.identifier(((Enum) obj).name());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier((value as Enum<*>).name)");
                annotationArgumentVisitor.visitEnum(name, classId, nameIdentifier);
                return;
            }
            if (Annotation.class.isAssignableFrom(enclosingClass)) {
                Class<?>[] interfaces = enclosingClass.getInterfaces();
                Intrinsics.checkNotNullExpressionValue(interfaces, "clazz.interfaces");
                Class<?> annotationClass = (Class) ArraysKt.single(interfaces);
                Intrinsics.checkNotNullExpressionValue(annotationClass, "annotationClass");
                KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = annotationArgumentVisitor.visitAnnotation(name, ReflectClassUtilKt.getClassId(annotationClass));
                if (annotationArgumentVisitorVisitAnnotation == null) {
                    return;
                }
                processAnnotationArguments(annotationArgumentVisitorVisitAnnotation, (Annotation) obj, annotationClass);
                return;
            }
            if (enclosingClass.isArray()) {
                KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor annotationArrayArgumentVisitorVisitArray = annotationArgumentVisitor.visitArray(name);
                if (annotationArrayArgumentVisitorVisitArray == null) {
                    return;
                }
                Class<?> componentType = enclosingClass.getComponentType();
                int i = 0;
                if (componentType.isEnum()) {
                    Intrinsics.checkNotNullExpressionValue(componentType, "componentType");
                    ClassId classId2 = ReflectClassUtilKt.getClassId(componentType);
                    Object[] objArr = (Object[]) obj;
                    int length = objArr.length;
                    while (i < length) {
                        Object obj2 = objArr[i];
                        i++;
                        Objects.requireNonNull(obj2, "null cannot be cast to non-null type kotlin.Enum<*>");
                        Name nameIdentifier2 = Name.identifier(((Enum) obj2).name());
                        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier((element as Enum<*>).name)");
                        annotationArrayArgumentVisitorVisitArray.visitEnum(classId2, nameIdentifier2);
                    }
                } else if (Intrinsics.areEqual(componentType, Class.class)) {
                    Object[] objArr2 = (Object[]) obj;
                    int length2 = objArr2.length;
                    while (i < length2) {
                        Object obj3 = objArr2[i];
                        i++;
                        Objects.requireNonNull(obj3, "null cannot be cast to non-null type java.lang.Class<*>");
                        annotationArrayArgumentVisitorVisitArray.visitClassLiteral(classLiteralValue((Class) obj3));
                    }
                } else {
                    Object[] objArr3 = (Object[]) obj;
                    int length3 = objArr3.length;
                    while (i < length3) {
                        Object obj4 = objArr3[i];
                        i++;
                        annotationArrayArgumentVisitorVisitArray.visit(obj4);
                    }
                }
                annotationArrayArgumentVisitorVisitArray.visitEnd();
                return;
            }
            throw new UnsupportedOperationException("Unsupported annotation argument value (" + enclosingClass + "): " + obj);
        }
        annotationArgumentVisitor.visitClassLiteral(name, classLiteralValue((Class) obj));
    }
}
