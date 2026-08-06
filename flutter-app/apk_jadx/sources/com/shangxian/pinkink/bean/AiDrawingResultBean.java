package com.shangxian.pinkink.bean;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AiDrawingResultBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nJ\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\bHÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003JK\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\bHÖ\u0001J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006\u001f"}, d2 = {"Lcom/shangxian/pinkink/bean/AiDrawingResultBean;", "", "txt", "", "img", "id", NotificationCompat.CATEGORY_MESSAGE, "state", "", "time", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getId", "()Ljava/lang/String;", "getImg", "getMsg", "getState", "()I", "getTime", "getTxt", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class AiDrawingResultBean {
    private final String id;
    private final String img;
    private final String msg;
    private final int state;
    private final String time;
    private final String txt;

    public static /* synthetic */ AiDrawingResultBean copy$default(AiDrawingResultBean aiDrawingResultBean, String str, String str2, String str3, String str4, int i, String str5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = aiDrawingResultBean.txt;
        }
        if ((i2 & 2) != 0) {
            str2 = aiDrawingResultBean.img;
        }
        String str6 = str2;
        if ((i2 & 4) != 0) {
            str3 = aiDrawingResultBean.id;
        }
        String str7 = str3;
        if ((i2 & 8) != 0) {
            str4 = aiDrawingResultBean.msg;
        }
        String str8 = str4;
        if ((i2 & 16) != 0) {
            i = aiDrawingResultBean.state;
        }
        int i3 = i;
        if ((i2 & 32) != 0) {
            str5 = aiDrawingResultBean.time;
        }
        return aiDrawingResultBean.copy(str, str6, str7, str8, i3, str5);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getTxt() {
        return this.txt;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getImg() {
        return this.img;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getMsg() {
        return this.msg;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getState() {
        return this.state;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getTime() {
        return this.time;
    }

    public final AiDrawingResultBean copy(String txt, String img, String id, String msg, int state, String time) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(msg, "msg");
        return new AiDrawingResultBean(txt, img, id, msg, state, time);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AiDrawingResultBean)) {
            return false;
        }
        AiDrawingResultBean aiDrawingResultBean = (AiDrawingResultBean) other;
        return Intrinsics.areEqual(this.txt, aiDrawingResultBean.txt) && Intrinsics.areEqual(this.img, aiDrawingResultBean.img) && Intrinsics.areEqual(this.id, aiDrawingResultBean.id) && Intrinsics.areEqual(this.msg, aiDrawingResultBean.msg) && this.state == aiDrawingResultBean.state && Intrinsics.areEqual(this.time, aiDrawingResultBean.time);
    }

    public int hashCode() {
        String str = this.txt;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.img;
        int iHashCode2 = (((((((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + this.id.hashCode()) * 31) + this.msg.hashCode()) * 31) + this.state) * 31;
        String str3 = this.time;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        return "AiDrawingResultBean(txt=" + ((Object) this.txt) + ", img=" + ((Object) this.img) + ", id=" + this.id + ", msg=" + this.msg + ", state=" + this.state + ", time=" + ((Object) this.time) + ')';
    }

    public AiDrawingResultBean(String str, String str2, String id, String msg, int i, String str3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(msg, "msg");
        this.txt = str;
        this.img = str2;
        this.id = id;
        this.msg = msg;
        this.state = i;
        this.time = str3;
    }

    public final String getId() {
        return this.id;
    }

    public final String getImg() {
        return this.img;
    }

    public final String getMsg() {
        return this.msg;
    }

    public final int getState() {
        return this.state;
    }

    public final String getTime() {
        return this.time;
    }

    public final String getTxt() {
        return this.txt;
    }
}
