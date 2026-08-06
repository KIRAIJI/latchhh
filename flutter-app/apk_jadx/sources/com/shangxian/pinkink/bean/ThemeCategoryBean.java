package com.shangxian.pinkink.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThemeCategoryBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/shangxian/pinkink/bean/ThemeCategoryBean;", "", "id", "", "name", "nameEn", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getName", "getNameEn", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class ThemeCategoryBean {
    private final String id;
    private final String name;
    private final String nameEn;

    public static /* synthetic */ ThemeCategoryBean copy$default(ThemeCategoryBean themeCategoryBean, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = themeCategoryBean.id;
        }
        if ((i & 2) != 0) {
            str2 = themeCategoryBean.name;
        }
        if ((i & 4) != 0) {
            str3 = themeCategoryBean.nameEn;
        }
        return themeCategoryBean.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getNameEn() {
        return this.nameEn;
    }

    public final ThemeCategoryBean copy(String id, String name, String nameEn) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(nameEn, "nameEn");
        return new ThemeCategoryBean(id, name, nameEn);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ThemeCategoryBean)) {
            return false;
        }
        ThemeCategoryBean themeCategoryBean = (ThemeCategoryBean) other;
        return Intrinsics.areEqual(this.id, themeCategoryBean.id) && Intrinsics.areEqual(this.name, themeCategoryBean.name) && Intrinsics.areEqual(this.nameEn, themeCategoryBean.nameEn);
    }

    public int hashCode() {
        return (((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.nameEn.hashCode();
    }

    public String toString() {
        return "ThemeCategoryBean(id=" + this.id + ", name=" + this.name + ", nameEn=" + this.nameEn + ')';
    }

    public ThemeCategoryBean(String id, String name, String nameEn) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(nameEn, "nameEn");
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getNameEn() {
        return this.nameEn;
    }
}
