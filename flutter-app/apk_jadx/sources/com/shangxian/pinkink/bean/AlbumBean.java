package com.shangxian.pinkink.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AlbumBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b(\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 B2\u00020\u0001:\u0001BBo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0011HÆ\u0003J\t\u0010.\u001a\u00020\u0005HÆ\u0003J\t\u0010/\u001a\u00020\u0005HÆ\u0003J\t\u00100\u001a\u00020\u0005HÆ\u0003J\t\u00101\u001a\u00020\u0003HÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\t\u00103\u001a\u00020\u0005HÆ\u0003J\t\u00104\u001a\u00020\fHÆ\u0003J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eHÆ\u0003Js\u00106\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u0011HÆ\u0001J\t\u00107\u001a\u00020\u0011HÖ\u0001J\u0013\u00108\u001a\u00020\f2\b\u00109\u001a\u0004\u0018\u00010:HÖ\u0003J\t\u0010;\u001a\u00020\u0011HÖ\u0001J\t\u0010<\u001a\u00020\u0005HÖ\u0001J\u0019\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u0011HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001e\u0010\n\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0014\"\u0004\b\u001c\u0010\u0016R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0018\"\u0004\b\u001e\u0010\u001aR\u001e\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u001f\"\u0004\b \u0010!R\u0016\u0010\u0010\u001a\u00020\u00118\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001e\u0010\u0006\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0018\"\u0004\b%\u0010\u001aR\u001e\u0010\t\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0014\"\u0004\b'\u0010\u0016R\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001e\u0010\u0007\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0018\"\u0004\b+\u0010\u001a¨\u0006C"}, d2 = {"Lcom/shangxian/pinkink/bean/AlbumBean;", "Landroid/os/Parcelable;", "columnId", "", "id", "", "name", "userId", "createTime", "updateTime", "cover", "isSelected", "", "userCreateThemeList", "", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "itemType", "", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JJLjava/lang/String;ZLjava/util/List;I)V", "getColumnId", "()J", "setColumnId", "(J)V", "getCover", "()Ljava/lang/String;", "setCover", "(Ljava/lang/String;)V", "getCreateTime", "setCreateTime", "getId", "setId", "()Z", "setSelected", "(Z)V", "getItemType", "()I", "getName", "setName", "getUpdateTime", "setUpdateTime", "getUserCreateThemeList", "()Ljava/util/List;", "getUserId", "setUserId", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "describeContents", "equals", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class AlbumBean implements Parcelable {
    private static final int ITEM_TYPE_AI = 0;
    private long columnId;
    private String cover;
    private long createTime;
    private String id;
    private boolean isSelected;
    private final int itemType;
    private String name;
    private long updateTime;
    private final List<UserCreateThemeBean> userCreateThemeList;
    private String userId;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final Parcelable.Creator<AlbumBean> CREATOR = new Creator();
    private static final int ITEM_TYPE_USER_CREATE = 1;
    private static final int ITEM_TYPE_NORMAL = 2;

    /* JADX INFO: compiled from: AlbumBean.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<AlbumBean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AlbumBean createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            long j = parcel.readLong();
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            long j2 = parcel.readLong();
            long j3 = parcel.readLong();
            String string4 = parcel.readString();
            boolean z = parcel.readInt() != 0;
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 != i; i2++) {
                arrayList.add(UserCreateThemeBean.CREATOR.createFromParcel(parcel));
            }
            return new AlbumBean(j, string, string2, string3, j2, j3, string4, z, arrayList, parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AlbumBean[] newArray(int i) {
            return new AlbumBean[i];
        }
    }

    public AlbumBean() {
        this(0L, null, null, null, 0L, 0L, null, false, null, 0, 1023, null);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getColumnId() {
        return this.columnId;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final int getItemType() {
        return this.itemType;
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
    public final String getUserId() {
        return this.userId;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final long getCreateTime() {
        return this.createTime;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final long getUpdateTime() {
        return this.updateTime;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final boolean getIsSelected() {
        return this.isSelected;
    }

    public final List<UserCreateThemeBean> component9() {
        return this.userCreateThemeList;
    }

    public final AlbumBean copy(long columnId, String id, String name, String userId, long createTime, long updateTime, String cover, boolean isSelected, List<UserCreateThemeBean> userCreateThemeList, int itemType) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(cover, "cover");
        Intrinsics.checkNotNullParameter(userCreateThemeList, "userCreateThemeList");
        return new AlbumBean(columnId, id, name, userId, createTime, updateTime, cover, isSelected, userCreateThemeList, itemType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AlbumBean)) {
            return false;
        }
        AlbumBean albumBean = (AlbumBean) other;
        return this.columnId == albumBean.columnId && Intrinsics.areEqual(this.id, albumBean.id) && Intrinsics.areEqual(this.name, albumBean.name) && Intrinsics.areEqual(this.userId, albumBean.userId) && this.createTime == albumBean.createTime && this.updateTime == albumBean.updateTime && Intrinsics.areEqual(this.cover, albumBean.cover) && this.isSelected == albumBean.isSelected && Intrinsics.areEqual(this.userCreateThemeList, albumBean.userCreateThemeList) && this.itemType == albumBean.itemType;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v13, types: [int] */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    public int hashCode() {
        int iM = ((((((((((((AlbumBean$$ExternalSyntheticBackport0.m(this.columnId) * 31) + this.id.hashCode()) * 31) + this.name.hashCode()) * 31) + this.userId.hashCode()) * 31) + AlbumBean$$ExternalSyntheticBackport0.m(this.createTime)) * 31) + AlbumBean$$ExternalSyntheticBackport0.m(this.updateTime)) * 31) + this.cover.hashCode()) * 31;
        boolean z = this.isSelected;
        ?? r1 = z;
        if (z) {
            r1 = 1;
        }
        return ((((iM + r1) * 31) + this.userCreateThemeList.hashCode()) * 31) + this.itemType;
    }

    public String toString() {
        return "AlbumBean(columnId=" + this.columnId + ", id=" + this.id + ", name=" + this.name + ", userId=" + this.userId + ", createTime=" + this.createTime + ", updateTime=" + this.updateTime + ", cover=" + this.cover + ", isSelected=" + this.isSelected + ", userCreateThemeList=" + this.userCreateThemeList + ", itemType=" + this.itemType + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeLong(this.columnId);
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.userId);
        parcel.writeLong(this.createTime);
        parcel.writeLong(this.updateTime);
        parcel.writeString(this.cover);
        parcel.writeInt(this.isSelected ? 1 : 0);
        List<UserCreateThemeBean> list = this.userCreateThemeList;
        parcel.writeInt(list.size());
        Iterator<UserCreateThemeBean> it = list.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, flags);
        }
        parcel.writeInt(this.itemType);
    }

    public AlbumBean(long j, String id, String name, String userId, long j2, long j3, String cover, boolean z, List<UserCreateThemeBean> userCreateThemeList, int i) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(cover, "cover");
        Intrinsics.checkNotNullParameter(userCreateThemeList, "userCreateThemeList");
        this.columnId = j;
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.createTime = j2;
        this.updateTime = j3;
        this.cover = cover;
        this.isSelected = z;
        this.userCreateThemeList = userCreateThemeList;
        this.itemType = i;
    }

    public final long getColumnId() {
        return this.columnId;
    }

    public final void setColumnId(long j) {
        this.columnId = j;
    }

    public final String getId() {
        return this.id;
    }

    public final void setId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final String getUserId() {
        return this.userId;
    }

    public final void setUserId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.userId = str;
    }

    public final long getCreateTime() {
        return this.createTime;
    }

    public final void setCreateTime(long j) {
        this.createTime = j;
    }

    public final long getUpdateTime() {
        return this.updateTime;
    }

    public final void setUpdateTime(long j) {
        this.updateTime = j;
    }

    public final String getCover() {
        return this.cover;
    }

    public final void setCover(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.cover = str;
    }

    public final boolean isSelected() {
        return this.isSelected;
    }

    public final void setSelected(boolean z) {
        this.isSelected = z;
    }

    public /* synthetic */ AlbumBean(long j, String str, String str2, String str3, long j2, long j3, String str4, boolean z, List list, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) != 0 ? 0L : j2, (i2 & 32) == 0 ? j3 : 0L, (i2 & 64) == 0 ? str4 : "", (i2 & 128) != 0 ? false : z, (i2 & 256) != 0 ? new ArrayList() : list, (i2 & 512) != 0 ? ITEM_TYPE_NORMAL : i);
    }

    public final List<UserCreateThemeBean> getUserCreateThemeList() {
        return this.userCreateThemeList;
    }

    public final int getItemType() {
        return this.itemType;
    }

    /* JADX INFO: compiled from: AlbumBean.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/shangxian/pinkink/bean/AlbumBean$Companion;", "", "()V", "ITEM_TYPE_AI", "", "getITEM_TYPE_AI", "()I", "ITEM_TYPE_NORMAL", "getITEM_TYPE_NORMAL", "ITEM_TYPE_USER_CREATE", "getITEM_TYPE_USER_CREATE", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getITEM_TYPE_AI() {
            return AlbumBean.ITEM_TYPE_AI;
        }

        public final int getITEM_TYPE_USER_CREATE() {
            return AlbumBean.ITEM_TYPE_USER_CREATE;
        }

        public final int getITEM_TYPE_NORMAL() {
            return AlbumBean.ITEM_TYPE_NORMAL;
        }
    }
}
