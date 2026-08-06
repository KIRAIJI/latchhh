package io.reactivex;

/* JADX INFO: loaded from: classes.dex */
public interface MaybeOnSubscribe<T> {
    void subscribe(MaybeEmitter<T> maybeEmitter) throws Exception;
}
