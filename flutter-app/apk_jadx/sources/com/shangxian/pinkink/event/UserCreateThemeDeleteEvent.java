package com.shangxian.pinkink.event;

import com.shangxian.pinkink.bean.AlbumBean$$ExternalSyntheticBackport0;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UserCreateThemeDeleteEvent.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u0011\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005HÆ\u0003J%\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/shangxian/pinkink/event/UserCreateThemeDeleteEvent;", "", "albumColumnId", "", "deleteUserCreateThemeList", "", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "(JLjava/util/List;)V", "getAlbumColumnId", "()J", "getDeleteUserCreateThemeList", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class UserCreateThemeDeleteEvent {
    private final long albumColumnId;
    private final List<UserCreateThemeBean> deleteUserCreateThemeList;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UserCreateThemeDeleteEvent copy$default(UserCreateThemeDeleteEvent userCreateThemeDeleteEvent, long j, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            j = userCreateThemeDeleteEvent.albumColumnId;
        }
        if ((i & 2) != 0) {
            list = userCreateThemeDeleteEvent.deleteUserCreateThemeList;
        }
        return userCreateThemeDeleteEvent.copy(j, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getAlbumColumnId() {
        return this.albumColumnId;
    }

    public final List<UserCreateThemeBean> component2() {
        return this.deleteUserCreateThemeList;
    }

    public final UserCreateThemeDeleteEvent copy(long albumColumnId, List<UserCreateThemeBean> deleteUserCreateThemeList) {
        Intrinsics.checkNotNullParameter(deleteUserCreateThemeList, "deleteUserCreateThemeList");
        return new UserCreateThemeDeleteEvent(albumColumnId, deleteUserCreateThemeList);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UserCreateThemeDeleteEvent)) {
            return false;
        }
        UserCreateThemeDeleteEvent userCreateThemeDeleteEvent = (UserCreateThemeDeleteEvent) other;
        return this.albumColumnId == userCreateThemeDeleteEvent.albumColumnId && Intrinsics.areEqual(this.deleteUserCreateThemeList, userCreateThemeDeleteEvent.deleteUserCreateThemeList);
    }

    public int hashCode() {
        return (AlbumBean$$ExternalSyntheticBackport0.m(this.albumColumnId) * 31) + this.deleteUserCreateThemeList.hashCode();
    }

    public String toString() {
        return "UserCreateThemeDeleteEvent(albumColumnId=" + this.albumColumnId + ", deleteUserCreateThemeList=" + this.deleteUserCreateThemeList + ')';
    }

    public UserCreateThemeDeleteEvent(long j, List<UserCreateThemeBean> deleteUserCreateThemeList) {
        Intrinsics.checkNotNullParameter(deleteUserCreateThemeList, "deleteUserCreateThemeList");
        this.albumColumnId = j;
        this.deleteUserCreateThemeList = deleteUserCreateThemeList;
    }

    public final long getAlbumColumnId() {
        return this.albumColumnId;
    }

    public final List<UserCreateThemeBean> getDeleteUserCreateThemeList() {
        return this.deleteUserCreateThemeList;
    }
}
