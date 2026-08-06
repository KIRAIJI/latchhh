package com.lazyee.klib.http;

import io.reactivex.disposables.Disposable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HttpTask.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/lazyee/klib/http/RxJavaHttpTask;", "Lcom/lazyee/klib/http/HttpTask;", "disposable", "Lio/reactivex/disposables/Disposable;", "(Lio/reactivex/disposables/Disposable;)V", "cancelTask", "", "isInvalid", "", "library_release"}, k = 1, mv = {1, 4, 2})
public final class RxJavaHttpTask implements HttpTask {
    private final Disposable disposable;

    public RxJavaHttpTask(Disposable disposable) {
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        this.disposable = disposable;
    }

    @Override // com.lazyee.klib.http.HttpTask
    public void cancelTask() {
        if (isInvalid()) {
            return;
        }
        this.disposable.dispose();
    }

    @Override // com.lazyee.klib.http.HttpTask
    public boolean isInvalid() {
        return this.disposable.isDisposed();
    }
}
