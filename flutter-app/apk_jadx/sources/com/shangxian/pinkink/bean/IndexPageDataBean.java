package com.shangxian.pinkink.bean;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IndexPageDataBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lcom/shangxian/pinkink/bean/IndexPageDataBean;", "", "()V", "ads", "", "Lcom/shangxian/pinkink/bean/IndexAdsBean;", "getAds", "()Ljava/util/List;", "setAds", "(Ljava/util/List;)V", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class IndexPageDataBean {
    private List<IndexAdsBean> ads = new ArrayList();

    public final List<IndexAdsBean> getAds() {
        return this.ads;
    }

    public final void setAds(List<IndexAdsBean> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.ads = list;
    }
}
