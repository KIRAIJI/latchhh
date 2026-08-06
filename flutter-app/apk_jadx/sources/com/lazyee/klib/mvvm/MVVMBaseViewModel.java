package com.lazyee.klib.mvvm;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MVVMBaseViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u0018J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u001aJ\u0006\u0010\u001c\u001a\u00020\u001aJ\u0006\u0010\u001d\u001a\u00020\u001aJ\u0006\u0010\u001e\u001a\u00020\u001aJ\u0006\u0010\u001f\u001a\u00020\u001aJ\u000e\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u0011J\u0010\u0010 \u001a\u00020\u001a2\b\u0010\"\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010#\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u0011J\u0010\u0010#\u001a\u00020\u001a2\b\u0010\"\u001a\u0004\u0018\u00010\u000eR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0019\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0007R\u0019\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0007R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0007¨\u0006$"}, d2 = {"Lcom/lazyee/klib/mvvm/MVVMBaseViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "loadingStateLiveData", "Landroidx/lifecycle/MutableLiveData;", "Lcom/lazyee/klib/mvvm/LoadingState;", "getLoadingStateLiveData", "()Landroidx/lifecycle/MutableLiveData;", "mRepositoryList", "", "Lcom/lazyee/klib/mvvm/MVVMBaseRepository;", "pageLoadingStateLiveData", "getPageLoadingStateLiveData", "toastLongMsgLiveData", "", "getToastLongMsgLiveData", "toastLongResIdLiveData", "", "getToastLongResIdLiveData", "toastShortMsgLiveData", "getToastShortMsgLiveData", "toastShortResIdLiveData", "getToastShortResIdLiveData", "getRepositoryList", "", "onLoadFailure", "", "onLoadSuccess", "onLoading", "onPageLoadFailure", "onPageLoadSuccess", "onPageLoading", "toastLong", "resId", NotificationCompat.CATEGORY_MESSAGE, "toastShort", "library_release"}, k = 1, mv = {1, 4, 2})
public class MVVMBaseViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<LoadingState> loadingStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoadingState> pageLoadingStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> toastLongMsgLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> toastShortMsgLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> toastLongResIdLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> toastShortResIdLiveData = new MutableLiveData<>();
    private final List<MVVMBaseRepository> mRepositoryList = new ArrayList();

    public final MutableLiveData<LoadingState> getLoadingStateLiveData() {
        return this.loadingStateLiveData;
    }

    public final MutableLiveData<LoadingState> getPageLoadingStateLiveData() {
        return this.pageLoadingStateLiveData;
    }

    public final MutableLiveData<String> getToastLongMsgLiveData() {
        return this.toastLongMsgLiveData;
    }

    public final MutableLiveData<String> getToastShortMsgLiveData() {
        return this.toastShortMsgLiveData;
    }

    public final MutableLiveData<Integer> getToastLongResIdLiveData() {
        return this.toastLongResIdLiveData;
    }

    public final MutableLiveData<Integer> getToastShortResIdLiveData() {
        return this.toastShortResIdLiveData;
    }

    public final List<MVVMBaseRepository> getRepositoryList() {
        Annotation annotation;
        if (this.mRepositoryList.isEmpty()) {
            Field[] declaredFields = getClass().getDeclaredFields();
            Intrinsics.checkNotNullExpressionValue(declaredFields, "javaClass.declaredFields");
            int length = declaredFields.length;
            int i = 0;
            while (true) {
                Annotation annotation2 = null;
                if (i >= length) {
                    break;
                }
                Field field = declaredFields[i];
                Intrinsics.checkNotNullExpressionValue(field, "field");
                Annotation[] annotations = field.getAnnotations();
                Intrinsics.checkNotNullExpressionValue(annotations, "field.annotations");
                int length2 = annotations.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length2) {
                        break;
                    }
                    Annotation annotation3 = annotations[i2];
                    if (annotation3 instanceof androidx.lifecycle.ViewModel) {
                        annotation2 = annotation3;
                        break;
                    }
                    i2++;
                }
                if (annotation2 != null) {
                    try {
                        field.setAccessible(true);
                        Object obj = field.get(this);
                        if (obj != null && (obj instanceof MVVMBaseRepository)) {
                            this.mRepositoryList.add((MVVMBaseRepository) obj);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                i++;
            }
            Method[] declaredMethods = getClass().getDeclaredMethods();
            Intrinsics.checkNotNullExpressionValue(declaredMethods, "javaClass.declaredMethods");
            for (Method method : declaredMethods) {
                Intrinsics.checkNotNullExpressionValue(method, "method");
                Annotation[] annotations2 = method.getAnnotations();
                Intrinsics.checkNotNullExpressionValue(annotations2, "method.annotations");
                int length3 = annotations2.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length3) {
                        annotation = null;
                        break;
                    }
                    annotation = annotations2[i3];
                    if (annotation instanceof androidx.lifecycle.ViewModel) {
                        break;
                    }
                    i3++;
                }
                if (annotation != null) {
                    try {
                        method.setAccessible(true);
                        Object objInvoke = method.invoke(this, new Object[0]);
                        if (objInvoke != null && (objInvoke instanceof MVVMBaseRepository)) {
                            this.mRepositoryList.add((MVVMBaseRepository) objInvoke);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return this.mRepositoryList;
    }

    public final void onLoading() {
        this.loadingStateLiveData.postValue(LoadingState.LOADING);
    }

    public final void onLoadSuccess() {
        this.loadingStateLiveData.postValue(LoadingState.SUCCESS);
    }

    public final void onLoadFailure() {
        this.loadingStateLiveData.postValue(LoadingState.FAILURE);
    }

    public final void onPageLoading() {
        this.pageLoadingStateLiveData.postValue(LoadingState.LOADING);
    }

    public final void onPageLoadSuccess() {
        this.pageLoadingStateLiveData.postValue(LoadingState.SUCCESS);
    }

    public final void onPageLoadFailure() {
        this.pageLoadingStateLiveData.postValue(LoadingState.FAILURE);
    }

    public final void toastLong(String msg) {
        this.toastLongMsgLiveData.postValue(msg);
    }

    public final void toastShort(String msg) {
        this.toastShortMsgLiveData.postValue(msg);
    }

    public final void toastLong(int resId) {
        this.toastLongResIdLiveData.postValue(Integer.valueOf(resId));
    }

    public final void toastShort(int resId) {
        this.toastShortResIdLiveData.postValue(Integer.valueOf(resId));
    }
}
