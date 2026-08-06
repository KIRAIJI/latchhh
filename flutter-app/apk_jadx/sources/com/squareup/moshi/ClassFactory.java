package com.squareup.moshi;

import com.squareup.moshi.internal.Util;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
abstract class ClassFactory<T> {
    abstract T newInstance() throws IllegalAccessException, InstantiationException, InvocationTargetException;

    ClassFactory() {
    }

    public static <T> ClassFactory<T> get(final Class<?> cls) {
        try {
            final Constructor<?> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            declaredConstructor.setAccessible(true);
            return new ClassFactory<T>() { // from class: com.squareup.moshi.ClassFactory.1
                @Override // com.squareup.moshi.ClassFactory
                public T newInstance() throws IllegalAccessException, InstantiationException, InvocationTargetException {
                    return (T) declaredConstructor.newInstance(null);
                }

                public String toString() {
                    return cls.getName();
                }
            };
        } catch (NoSuchMethodException unused) {
            try {
                Class<?> cls2 = Class.forName("sun.misc.Unsafe");
                Field declaredField = cls2.getDeclaredField("theUnsafe");
                declaredField.setAccessible(true);
                final Object obj = declaredField.get(null);
                final Method method = cls2.getMethod("allocateInstance", Class.class);
                return new ClassFactory<T>() { // from class: com.squareup.moshi.ClassFactory.2
                    @Override // com.squareup.moshi.ClassFactory
                    public T newInstance() throws IllegalAccessException, InvocationTargetException {
                        return (T) method.invoke(obj, cls);
                    }

                    public String toString() {
                        return cls.getName();
                    }
                };
            } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException unused2) {
                try {
                    try {
                        Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
                        declaredMethod.setAccessible(true);
                        final int iIntValue = ((Integer) declaredMethod.invoke(null, Object.class)).intValue();
                        final Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
                        declaredMethod2.setAccessible(true);
                        return new ClassFactory<T>() { // from class: com.squareup.moshi.ClassFactory.3
                            @Override // com.squareup.moshi.ClassFactory
                            public T newInstance() throws IllegalAccessException, InvocationTargetException {
                                return (T) declaredMethod2.invoke(null, cls, Integer.valueOf(iIntValue));
                            }

                            public String toString() {
                                return cls.getName();
                            }
                        };
                    } catch (Exception unused3) {
                        throw new IllegalArgumentException("cannot construct instances of " + cls.getName());
                    }
                } catch (IllegalAccessException unused4) {
                    throw new AssertionError();
                } catch (NoSuchMethodException unused5) {
                    final Method declaredMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
                    declaredMethod3.setAccessible(true);
                    return new ClassFactory<T>() { // from class: com.squareup.moshi.ClassFactory.4
                        @Override // com.squareup.moshi.ClassFactory
                        public T newInstance() throws IllegalAccessException, InvocationTargetException {
                            return (T) declaredMethod3.invoke(null, cls, Object.class);
                        }

                        public String toString() {
                            return cls.getName();
                        }
                    };
                } catch (InvocationTargetException e) {
                    throw Util.rethrowCause(e);
                }
            } catch (IllegalAccessException unused6) {
                throw new AssertionError();
            }
        }
    }
}
