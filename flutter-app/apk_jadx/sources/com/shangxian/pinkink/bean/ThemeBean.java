package com.shangxian.pinkink.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThemeBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B3\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J=\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001J\u0013\u0010 \u001a\u00020\u00102\b\u0010!\u001a\u0004\u0018\u00010\"HÖ\u0003J\t\u0010#\u001a\u00020\u001fHÖ\u0001J\u0006\u0010$\u001a\u00020\u0010J\t\u0010%\u001a\u00020\u0005HÖ\u0001J\u0019\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u001fHÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0007\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\r¨\u0006+"}, d2 = {"Lcom/shangxian/pinkink/bean/ThemeBean;", "Landroid/os/Parcelable;", "columnId", "", "id", "", "name", "likeUserId", "cover", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getColumnId", "()J", "getCover", "()Ljava/lang/String;", "getId", "isSelected", "", "()Z", "setSelected", "(Z)V", "getLikeUserId", "setLikeUserId", "(Ljava/lang/String;)V", "getName", "component1", "component2", "component3", "component4", "component5", "copy", "describeContents", "", "equals", "other", "", "hashCode", "isLike", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class ThemeBean implements Parcelable {
    public static final Parcelable.Creator<ThemeBean> CREATOR = new Creator();
    private final long columnId;
    private final String cover;
    private final String id;
    private boolean isSelected;
    private String likeUserId;
    private final String name;

    /* JADX INFO: compiled from: ThemeBean.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<ThemeBean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ThemeBean createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new ThemeBean(parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ThemeBean[] newArray(int i) {
            return new ThemeBean[i];
        }
    }

    public static /* synthetic */ ThemeBean copy$default(ThemeBean themeBean, long j, String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            j = themeBean.columnId;
        }
        long j2 = j;
        if ((i & 2) != 0) {
            str = themeBean.id;
        }
        String str5 = str;
        if ((i & 4) != 0) {
            str2 = themeBean.name;
        }
        String str6 = str2;
        if ((i & 8) != 0) {
            str3 = themeBean.likeUserId;
        }
        String str7 = str3;
        if ((i & 16) != 0) {
            str4 = themeBean.cover;
        }
        return themeBean.copy(j2, str5, str6, str7, str4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getColumnId() {
        return this.columnId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getLikeUserId() {
        return this.likeUserId;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    public final ThemeBean copy(long columnId, String id, String name, String likeUserId, String cover) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(likeUserId, "likeUserId");
        return new ThemeBean(columnId, id, name, likeUserId, cover);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ThemeBean)) {
            return false;
        }
        ThemeBean themeBean = (ThemeBean) other;
        return this.columnId == themeBean.columnId && Intrinsics.areEqual(this.id, themeBean.id) && Intrinsics.areEqual(this.name, themeBean.name) && Intrinsics.areEqual(this.likeUserId, themeBean.likeUserId) && Intrinsics.areEqual(this.cover, themeBean.cover);
    }

    public int hashCode() {
        int iM = ((((((AlbumBean$$ExternalSyntheticBackport0.m(this.columnId) * 31) + this.id.hashCode()) * 31) + this.name.hashCode()) * 31) + this.likeUserId.hashCode()) * 31;
        String str = this.cover;
        return iM + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "ThemeBean(columnId=" + this.columnId + ", id=" + this.id + ", name=" + this.name + ", likeUserId=" + this.likeUserId + ", cover=" + ((Object) this.cover) + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeLong(this.columnId);
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.likeUserId);
        parcel.writeString(this.cover);
    }

    public ThemeBean(long j, String id, String name, String likeUserId, String str) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(likeUserId, "likeUserId");
        this.columnId = j;
        this.id = id;
        this.name = name;
        this.likeUserId = likeUserId;
        this.cover = str;
    }

    public /* synthetic */ ThemeBean(long j, String str, String str2, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j, str, str2, (i & 8) != 0 ? "" : str3, str4);
    }

    public final long getColumnId() {
        return this.columnId;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getLikeUserId() {
        return this.likeUserId;
    }

    public final void setLikeUserId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.likeUserId = str;
    }

    public final String getCover() {
        return this.cover;
    }

    /* JADX INFO: renamed from: isSelected, reason: from getter */
    public final boolean getIsSelected() {
        return this.isSelected;
    }

    public final void setSelected(boolean z) {
        this.isSelected = z;
    }

    public final boolean isLike() {
        return !TextUtils.isEmpty(this.likeUserId);
    }
}
