package io.reactivex;

/* JADX INFO: loaded from: classes.dex */
public interface ObservableOnSubscribe<T> {
    void subscribe(ObservableEmitter<T> observableEmitter) throws Exception;
}
