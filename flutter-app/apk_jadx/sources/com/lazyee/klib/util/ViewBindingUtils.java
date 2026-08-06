package com.lazyee.klib.util;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.exifinterface.media.ExifInterface;
import androidx.viewbinding.ViewBinding;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ViewBindingUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0002J+\u0010\b\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ=\u0010\b\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\u0006\u0010\t\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Lcom/lazyee/klib/util/ViewBindingUtils;", "", "()V", "getClass", "Ljava/lang/Class;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "clazz", "getViewBinding", "layoutInflater", "Landroid/view/LayoutInflater;", "(Ljava/lang/Class;Landroid/view/LayoutInflater;)Landroidx/viewbinding/ViewBinding;", "viewGroup", "Landroid/view/ViewGroup;", "isAttachToRoot", "", "(Ljava/lang/Class;Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)Landroidx/viewbinding/ViewBinding;", "library_release"}, k = 1, mv = {1, 4, 2})
public final class ViewBindingUtils {
    public static final ViewBindingUtils INSTANCE = new ViewBindingUtils();

    private ViewBindingUtils() {
    }

    private final <T extends ViewBinding> Class<T> getClass(Class<Object> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        Objects.requireNonNull(genericSuperclass, "null cannot be cast to non-null type java.lang.reflect.ParameterizedType");
        Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        Objects.requireNonNull(type, "null cannot be cast to non-null type java.lang.Class<T>");
        return (Class) type;
    }

    public final <T extends ViewBinding> T getViewBinding(Class<Object> clazz, LayoutInflater layoutInflater) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(layoutInflater, "layoutInflater");
        Method method = getClass(clazz).getMethod("inflate", LayoutInflater.class);
        Intrinsics.checkNotNullExpressionValue(method, "getClass<T>(clazz).getMe…youtInflater::class.java)");
        Object objInvoke = method.invoke(null, layoutInflater);
        Objects.requireNonNull(objInvoke, "null cannot be cast to non-null type T");
        T t = (T) objInvoke;
        if (t == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        return t;
    }

    public final <T extends ViewBinding> T getViewBinding(Class<Object> clazz, LayoutInflater layoutInflater, ViewGroup viewGroup, boolean isAttachToRoot) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(layoutInflater, "layoutInflater");
        Method method = getClass(clazz).getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
        Intrinsics.checkNotNullExpressionValue(method, "getClass<T>(clazz).getMe…java,Boolean::class.java)");
        Object objInvoke = method.invoke(null, layoutInflater, viewGroup, Boolean.valueOf(isAttachToRoot));
        Objects.requireNonNull(objInvoke, "null cannot be cast to non-null type T");
        T t = (T) objInvoke;
        if (t == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        return t;
    }
}
