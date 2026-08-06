package com.lazyee.klib.mvvm;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MVVMBaseView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&J\u0012\u0010\f\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H&J\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&J\u0012\u0010\u0011\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H&¨\u0006\u0012"}, d2 = {"Lcom/lazyee/klib/mvvm/MVVMBaseView;", "", "initViewModel", "", "owner", "Landroidx/lifecycle/LifecycleOwner;", "viewModel", "Lcom/lazyee/klib/mvvm/MVVMBaseViewModel;", "onLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onPageLoadingStateChanged", "onShowLongToast", "resId", "", NotificationCompat.CATEGORY_MESSAGE, "", "onShowShortToast", "library_release"}, k = 1, mv = {1, 4, 2})
public interface MVVMBaseView {
    void initViewModel(LifecycleOwner owner);

    void onLoadingStateChanged(LoadingState state);

    void onPageLoadingStateChanged(LoadingState state);

    void onShowLongToast(int resId);

    void onShowLongToast(String msg);

    void onShowShortToast(int resId);

    void onShowShortToast(String msg);

    /* JADX INFO: compiled from: MVVMBaseView.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    public static final class DefaultImpls {
        public static void initViewModel(MVVMBaseView mVVMBaseView, LifecycleOwner owner) {
            Annotation annotation;
            Intrinsics.checkNotNullParameter(owner, "owner");
            Field[] declaredFields = mVVMBaseView.getClass().getDeclaredFields();
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
                    if (annotation3 instanceof ViewModel) {
                        annotation2 = annotation3;
                        break;
                    }
                    i2++;
                }
                if (annotation2 != null) {
                    try {
                        field.setAccessible(true);
                        Object obj = field.get(mVVMBaseView);
                        if (obj != null && (obj instanceof MVVMBaseViewModel)) {
                            initViewModel(mVVMBaseView, owner, (MVVMBaseViewModel) obj);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                i++;
            }
            Method[] declaredMethods = mVVMBaseView.getClass().getDeclaredMethods();
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
                    if (annotation instanceof ViewModel) {
                        break;
                    } else {
                        i3++;
                    }
                }
                if (annotation != null) {
                    try {
                        method.setAccessible(true);
                        Object objInvoke = method.invoke(mVVMBaseView, new Object[0]);
                        if (objInvoke != null && (objInvoke instanceof MVVMBaseViewModel)) {
                            initViewModel(mVVMBaseView, owner, (MVVMBaseViewModel) objInvoke);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        private static void initViewModel(final MVVMBaseView mVVMBaseView, LifecycleOwner lifecycleOwner, MVVMBaseViewModel mVVMBaseViewModel) {
            Iterator<T> it = mVVMBaseViewModel.getRepositoryList().iterator();
            while (it.hasNext()) {
                lifecycleOwner.getLifecycle().addObserver((MVVMBaseRepository) it.next());
            }
            mVVMBaseViewModel.getLoadingStateLiveData().observe(lifecycleOwner, new Observer<LoadingState>() { // from class: com.lazyee.klib.mvvm.MVVMBaseView.initViewModel.4
                @Override // androidx.lifecycle.Observer
                public void onChanged(LoadingState state) {
                    if (state != null) {
                        MVVMBaseView.this.onLoadingStateChanged(state);
                    }
                }
            });
            mVVMBaseViewModel.getPageLoadingStateLiveData().observe(lifecycleOwner, new Observer<LoadingState>() { // from class: com.lazyee.klib.mvvm.MVVMBaseView.initViewModel.5
                @Override // androidx.lifecycle.Observer
                public void onChanged(LoadingState state) {
                    if (state != null) {
                        MVVMBaseView.this.onPageLoadingStateChanged(state);
                    }
                }
            });
            mVVMBaseViewModel.getToastLongMsgLiveData().observe(lifecycleOwner, new Observer<String>() { // from class: com.lazyee.klib.mvvm.MVVMBaseView.initViewModel.6
                @Override // androidx.lifecycle.Observer
                public final void onChanged(String str) {
                    if (str != null) {
                        MVVMBaseView.this.onShowLongToast(str);
                    }
                }
            });
            mVVMBaseViewModel.getToastShortMsgLiveData().observe(lifecycleOwner, new Observer<String>() { // from class: com.lazyee.klib.mvvm.MVVMBaseView.initViewModel.7
                @Override // androidx.lifecycle.Observer
                public final void onChanged(String str) {
                    if (str != null) {
                        MVVMBaseView.this.onShowShortToast(str);
                    }
                }
            });
            mVVMBaseViewModel.getToastLongResIdLiveData().observe(lifecycleOwner, new Observer<Integer>() { // from class: com.lazyee.klib.mvvm.MVVMBaseView.initViewModel.8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Integer num) {
                    if (num != null) {
                        num.intValue();
                        MVVMBaseView.this.onShowLongToast(num.intValue());
                    }
                }
            });
            mVVMBaseViewModel.getToastShortResIdLiveData().observe(lifecycleOwner, new Observer<Integer>() { // from class: com.lazyee.klib.mvvm.MVVMBaseView.initViewModel.9
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Integer num) {
                    if (num != null) {
                        num.intValue();
                        MVVMBaseView.this.onShowShortToast(num.intValue());
                    }
                }
            });
        }
    }
}
