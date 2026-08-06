package com.shangxian.pinkink.event;

import com.shangxian.pinkink.bean.AlbumBean$$ExternalSyntheticBackport0;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UserCreateThemeAddedEvent.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u0011\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005HÆ\u0003J%\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/shangxian/pinkink/event/UserCreateThemeAddedEvent;", "", "albumColumnId", "", "addedUserCreateThemeList", "", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "(JLjava/util/List;)V", "getAddedUserCreateThemeList", "()Ljava/util/List;", "getAlbumColumnId", "()J", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class UserCreateThemeAddedEvent {
    private final List<UserCreateThemeBean> addedUserCreateThemeList;
    private final long albumColumnId;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UserCreateThemeAddedEvent copy$default(UserCreateThemeAddedEvent userCreateThemeAddedEvent, long j, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            j = userCreateThemeAddedEvent.albumColumnId;
        }
        if ((i & 2) != 0) {
            list = userCreateThemeAddedEvent.addedUserCreateThemeList;
        }
        return userCreateThemeAddedEvent.copy(j, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getAlbumColumnId() {
        return this.albumColumnId;
    }

    public final List<UserCreateThemeBean> component2() {
        return this.addedUserCreateThemeList;
    }

    public final UserCreateThemeAddedEvent copy(long albumColumnId, List<UserCreateThemeBean> addedUserCreateThemeList) {
        Intrinsics.checkNotNullParameter(addedUserCreateThemeList, "addedUserCreateThemeList");
        return new UserCreateThemeAddedEvent(albumColumnId, addedUserCreateThemeList);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UserCreateThemeAddedEvent)) {
            return false;
        }
        UserCreateThemeAddedEvent userCreateThemeAddedEvent = (UserCreateThemeAddedEvent) other;
        return this.albumColumnId == userCreateThemeAddedEvent.albumColumnId && Intrinsics.areEqual(this.addedUserCreateThemeList, userCreateThemeAddedEvent.addedUserCreateThemeList);
    }

    public int hashCode() {
        return (AlbumBean$$ExternalSyntheticBackport0.m(this.albumColumnId) * 31) + this.addedUserCreateThemeList.hashCode();
    }

    public String toString() {
        return "UserCreateThemeAddedEvent(albumColumnId=" + this.albumColumnId + ", addedUserCreateThemeList=" + this.addedUserCreateThemeList + ')';
    }

    public UserCreateThemeAddedEvent(long j, List<UserCreateThemeBean> addedUserCreateThemeList) {
        Intrinsics.checkNotNullParameter(addedUserCreateThemeList, "addedUserCreateThemeList");
        this.albumColumnId = j;
        this.addedUserCreateThemeList = addedUserCreateThemeList;
    }

    public final List<UserCreateThemeBean> getAddedUserCreateThemeList() {
        return this.addedUserCreateThemeList;
    }

    public final long getAlbumColumnId() {
        return this.albumColumnId;
    }
}
