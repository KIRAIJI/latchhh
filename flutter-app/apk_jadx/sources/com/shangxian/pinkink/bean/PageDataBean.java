package com.shangxian.pinkink.bean;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PageDataBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t¢\u0006\u0002\u0010\nJ\t\u0010\u0011\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J\u0011\u0010\u0014\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\tHÆ\u0003J?\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\tHÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u000bR\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u001b"}, d2 = {"Lcom/shangxian/pinkink/bean/PageDataBean;", ExifInterface.GPS_DIRECTION_TRUE, "", "isMore", "", "pageNum", "", "pageSize", "listData", "", "(ZIILjava/util/List;)V", "()Z", "getListData", "()Ljava/util/List;", "getPageNum", "()I", "getPageSize", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class PageDataBean<T> {
    private final boolean isMore;
    private final List<T> listData;
    private final int pageNum;
    private final int pageSize;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PageDataBean copy$default(PageDataBean pageDataBean, boolean z, int i, int i2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            z = pageDataBean.isMore;
        }
        if ((i3 & 2) != 0) {
            i = pageDataBean.pageNum;
        }
        if ((i3 & 4) != 0) {
            i2 = pageDataBean.pageSize;
        }
        if ((i3 & 8) != 0) {
            list = pageDataBean.listData;
        }
        return pageDataBean.copy(z, i, i2, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getIsMore() {
        return this.isMore;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getPageNum() {
        return this.pageNum;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getPageSize() {
        return this.pageSize;
    }

    public final List<T> component4() {
        return this.listData;
    }

    public final PageDataBean<T> copy(boolean isMore, int pageNum, int pageSize, List<T> listData) {
        return new PageDataBean<>(isMore, pageNum, pageSize, listData);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PageDataBean)) {
            return false;
        }
        PageDataBean pageDataBean = (PageDataBean) other;
        return this.isMore == pageDataBean.isMore && this.pageNum == pageDataBean.pageNum && this.pageSize == pageDataBean.pageSize && Intrinsics.areEqual(this.listData, pageDataBean.listData);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    public int hashCode() {
        boolean z = this.isMore;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = ((((r0 * 31) + this.pageNum) * 31) + this.pageSize) * 31;
        List<T> list = this.listData;
        return i + (list == null ? 0 : list.hashCode());
    }

    public String toString() {
        return "PageDataBean(isMore=" + this.isMore + ", pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + ", listData=" + this.listData + ')';
    }

    public PageDataBean(boolean z, int i, int i2, List<T> list) {
        this.isMore = z;
        this.pageNum = i;
        this.pageSize = i2;
        this.listData = list;
    }

    public final boolean isMore() {
        return this.isMore;
    }

    public final int getPageNum() {
        return this.pageNum;
    }

    public final int getPageSize() {
        return this.pageSize;
    }

    public final List<T> getListData() {
        return this.listData;
    }
}
