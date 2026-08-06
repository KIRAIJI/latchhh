package io.reactivex;

import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes.dex */
public interface FlowableTransformer<Upstream, Downstream> {
    Publisher<Downstream> apply(Flowable<Upstream> flowable);
}
