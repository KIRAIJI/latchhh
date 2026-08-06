package io.reactivex;

/* JADX INFO: loaded from: classes.dex */
public interface FlowableOnSubscribe<T> {
    void subscribe(FlowableEmitter<T> flowableEmitter) throws Exception;
}
