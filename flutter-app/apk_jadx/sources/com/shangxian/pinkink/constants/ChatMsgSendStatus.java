package com.shangxian.pinkink.constants;

import kotlin.Metadata;

/* JADX INFO: compiled from: ChatMsgSendStatus.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/shangxian/pinkink/constants/ChatMsgSendStatus;", "", "()V", "STATUS_SENDING", "", "getSTATUS_SENDING", "()I", "STATUS_SEND_COMPLETE", "getSTATUS_SEND_COMPLETE", "STATUS_SEND_FAIL", "getSTATUS_SEND_FAIL", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ChatMsgSendStatus {
    public static final ChatMsgSendStatus INSTANCE = new ChatMsgSendStatus();
    private static final int STATUS_SENDING = 1;
    private static final int STATUS_SEND_FAIL = 2;
    private static final int STATUS_SEND_COMPLETE = 3;

    private ChatMsgSendStatus() {
    }

    public final int getSTATUS_SENDING() {
        return STATUS_SENDING;
    }

    public final int getSTATUS_SEND_FAIL() {
        return STATUS_SEND_FAIL;
    }

    public final int getSTATUS_SEND_COMPLETE() {
        return STATUS_SEND_COMPLETE;
    }
}
