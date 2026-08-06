package io.reactivex;

/* JADX INFO: loaded from: classes.dex */
public interface ObservableSource<T> {
    void subscribe(Observer<? super T> observer);
}
