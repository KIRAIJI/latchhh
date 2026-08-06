package com.shangxian.pinkink.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UpdateAvatarResultBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/bean/UpdateAvatarResultBean;", "", "pic", "", "(Ljava/lang/String;)V", "getPic", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class UpdateAvatarResultBean {
    private final String pic;

    public static /* synthetic */ UpdateAvatarResultBean copy$default(UpdateAvatarResultBean updateAvatarResultBean, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = updateAvatarResultBean.pic;
        }
        return updateAvatarResultBean.copy(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPic() {
        return this.pic;
    }

    public final UpdateAvatarResultBean copy(String pic) {
        Intrinsics.checkNotNullParameter(pic, "pic");
        return new UpdateAvatarResultBean(pic);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof UpdateAvatarResultBean) && Intrinsics.areEqual(this.pic, ((UpdateAvatarResultBean) other).pic);
    }

    public int hashCode() {
        return this.pic.hashCode();
    }

    public String toString() {
        return "UpdateAvatarResultBean(pic=" + this.pic + ')';
    }

    public UpdateAvatarResultBean(String pic) {
        Intrinsics.checkNotNullParameter(pic, "pic");
        this.pic = pic;
    }

    public final String getPic() {
        return this.pic;
    }
}
