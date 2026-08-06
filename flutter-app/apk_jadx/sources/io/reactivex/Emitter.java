package io.reactivex;

/* JADX INFO: loaded from: classes.dex */
public interface Emitter<T> {
    void onComplete();

    void onError(Throwable th);

    void onNext(T t);
}
