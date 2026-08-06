package com.shangxian.pinkink.bean;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UserCreateThemeBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0005HÆ\u0003J;\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u0005HÆ\u0001J\t\u0010%\u001a\u00020&HÖ\u0001J\u0013\u0010'\u001a\u00020\u00172\b\u0010(\u001a\u0004\u0018\u00010)HÖ\u0003J\t\u0010*\u001a\u00020&HÖ\u0001J\t\u0010+\u001a\u00020\u0005HÖ\u0001J\u0019\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020&HÖ\u0001R\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\b\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001e\u0010\u0007\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R\u001e\u0010\u0016\u001a\u00020\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001e\u0010\u001b\u001a\u00020\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0018\"\u0004\b\u001c\u0010\u001aR\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u000f\"\u0004\b\u001e\u0010\u0011¨\u00061"}, d2 = {"Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "Landroid/os/Parcelable;", "columnId", "", "userId", "", "albumColumnId", "composeImagePath", "backgroundImagePath", "(JLjava/lang/String;JLjava/lang/String;Ljava/lang/String;)V", "getAlbumColumnId", "()J", "setAlbumColumnId", "(J)V", "getBackgroundImagePath", "()Ljava/lang/String;", "setBackgroundImagePath", "(Ljava/lang/String;)V", "getColumnId", "setColumnId", "getComposeImagePath", "setComposeImagePath", "isHandleImage", "", "()Z", "setHandleImage", "(Z)V", "isSelected", "setSelected", "getUserId", "setUserId", "component1", "component2", "component3", "component4", "component5", "copy", "describeContents", "", "equals", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class UserCreateThemeBean implements Parcelable {
    public static final Parcelable.Creator<UserCreateThemeBean> CREATOR = new Creator();
    private long albumColumnId;
    private String backgroundImagePath;
    private long columnId;
    private String composeImagePath;
    private boolean isHandleImage;
    private boolean isSelected;
    private String userId;

    /* JADX INFO: compiled from: UserCreateThemeBean.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<UserCreateThemeBean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final UserCreateThemeBean createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new UserCreateThemeBean(parcel.readLong(), parcel.readString(), parcel.readLong(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final UserCreateThemeBean[] newArray(int i) {
            return new UserCreateThemeBean[i];
        }
    }

    public UserCreateThemeBean() {
        this(0L, null, 0L, null, null, 31, null);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getColumnId() {
        return this.columnId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final long getAlbumColumnId() {
        return this.albumColumnId;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getComposeImagePath() {
        return this.composeImagePath;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getBackgroundImagePath() {
        return this.backgroundImagePath;
    }

    public final UserCreateThemeBean copy(long columnId, String userId, long albumColumnId, String composeImagePath, String backgroundImagePath) {
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(composeImagePath, "composeImagePath");
        Intrinsics.checkNotNullParameter(backgroundImagePath, "backgroundImagePath");
        return new UserCreateThemeBean(columnId, userId, albumColumnId, composeImagePath, backgroundImagePath);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UserCreateThemeBean)) {
            return false;
        }
        UserCreateThemeBean userCreateThemeBean = (UserCreateThemeBean) other;
        return this.columnId == userCreateThemeBean.columnId && Intrinsics.areEqual(this.userId, userCreateThemeBean.userId) && this.albumColumnId == userCreateThemeBean.albumColumnId && Intrinsics.areEqual(this.composeImagePath, userCreateThemeBean.composeImagePath) && Intrinsics.areEqual(this.backgroundImagePath, userCreateThemeBean.backgroundImagePath);
    }

    public int hashCode() {
        return (((((((AlbumBean$$ExternalSyntheticBackport0.m(this.columnId) * 31) + this.userId.hashCode()) * 31) + AlbumBean$$ExternalSyntheticBackport0.m(this.albumColumnId)) * 31) + this.composeImagePath.hashCode()) * 31) + this.backgroundImagePath.hashCode();
    }

    public String toString() {
        return "UserCreateThemeBean(columnId=" + this.columnId + ", userId=" + this.userId + ", albumColumnId=" + this.albumColumnId + ", composeImagePath=" + this.composeImagePath + ", backgroundImagePath=" + this.backgroundImagePath + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeLong(this.columnId);
        parcel.writeString(this.userId);
        parcel.writeLong(this.albumColumnId);
        parcel.writeString(this.composeImagePath);
        parcel.writeString(this.backgroundImagePath);
    }

    public UserCreateThemeBean(long j, String userId, long j2, String composeImagePath, String backgroundImagePath) {
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(composeImagePath, "composeImagePath");
        Intrinsics.checkNotNullParameter(backgroundImagePath, "backgroundImagePath");
        this.columnId = j;
        this.userId = userId;
        this.albumColumnId = j2;
        this.composeImagePath = composeImagePath;
        this.backgroundImagePath = backgroundImagePath;
    }

    public /* synthetic */ UserCreateThemeBean(long j, String str, long j2, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j, (i & 2) != 0 ? "" : str, (i & 4) == 0 ? j2 : 0L, (i & 8) != 0 ? "" : str2, (i & 16) != 0 ? "" : str3);
    }

    public final long getColumnId() {
        return this.columnId;
    }

    public final void setColumnId(long j) {
        this.columnId = j;
    }

    public final String getUserId() {
        return this.userId;
    }

    public final void setUserId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.userId = str;
    }

    public final long getAlbumColumnId() {
        return this.albumColumnId;
    }

    public final void setAlbumColumnId(long j) {
        this.albumColumnId = j;
    }

    public final String getComposeImagePath() {
        return this.composeImagePath;
    }

    public final void setComposeImagePath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.composeImagePath = str;
    }

    public final String getBackgroundImagePath() {
        return this.backgroundImagePath;
    }

    public final void setBackgroundImagePath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.backgroundImagePath = str;
    }

    /* JADX INFO: renamed from: isSelected, reason: from getter */
    public final boolean getIsSelected() {
        return this.isSelected;
    }

    public final void setSelected(boolean z) {
        this.isSelected = z;
    }

    /* JADX INFO: renamed from: isHandleImage, reason: from getter */
    public final boolean getIsHandleImage() {
        return this.isHandleImage;
    }

    public final void setHandleImage(boolean z) {
        this.isHandleImage = z;
    }
}
