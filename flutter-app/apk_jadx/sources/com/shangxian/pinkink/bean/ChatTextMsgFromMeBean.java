package com.shangxian.pinkink.bean;

import com.shangxian.pinkink.constants.ChatMsgSendStatus;
import com.shangxian.pinkink.constants.ChatMsgType;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChatTextMsgFromMeBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\f\"\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lcom/shangxian/pinkink/bean/ChatTextMsgFromMeBean;", "Lcom/shangxian/pinkink/bean/ChatMsgBean;", "()V", "content", "", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "itemType", "", "getItemType", "()I", "sendStatus", "getSendStatus", "setSendStatus", "(I)V", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ChatTextMsgFromMeBean extends ChatMsgBean {
    private String content = "";
    private int sendStatus = ChatMsgSendStatus.INSTANCE.getSTATUS_SENDING();

    public final String getContent() {
        return this.content;
    }

    public final void setContent(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.content = str;
    }

    public final int getSendStatus() {
        return this.sendStatus;
    }

    public final void setSendStatus(int i) {
        this.sendStatus = i;
    }

    @Override // com.shangxian.pinkink.bean.ChatMsgBean, com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return ChatMsgType.INSTANCE.getTYPE_TEXT_MSG_FROM_ME();
    }
}
