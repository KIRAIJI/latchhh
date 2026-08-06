package com.shangxian.pinkink.constants;

import kotlin.Metadata;

/* JADX INFO: compiled from: ChatMsgType.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006¨\u0006\r"}, d2 = {"Lcom/shangxian/pinkink/constants/ChatMsgType;", "", "()V", "TYPE_AI_DRAWING_RECOMMEND", "", "getTYPE_AI_DRAWING_RECOMMEND", "()I", "TYPE_AI_DRAWING_RESULT", "getTYPE_AI_DRAWING_RESULT", "TYPE_AI_DRAWING_THINKING", "getTYPE_AI_DRAWING_THINKING", "TYPE_TEXT_MSG_FROM_ME", "getTYPE_TEXT_MSG_FROM_ME", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ChatMsgType {
    public static final ChatMsgType INSTANCE = new ChatMsgType();
    private static final int TYPE_AI_DRAWING_RECOMMEND = 1;
    private static final int TYPE_TEXT_MSG_FROM_ME = 2;
    private static final int TYPE_AI_DRAWING_THINKING = 3;
    private static final int TYPE_AI_DRAWING_RESULT = 4;

    private ChatMsgType() {
    }

    public final int getTYPE_AI_DRAWING_RECOMMEND() {
        return TYPE_AI_DRAWING_RECOMMEND;
    }

    public final int getTYPE_TEXT_MSG_FROM_ME() {
        return TYPE_TEXT_MSG_FROM_ME;
    }

    public final int getTYPE_AI_DRAWING_THINKING() {
        return TYPE_AI_DRAWING_THINKING;
    }

    public final int getTYPE_AI_DRAWING_RESULT() {
        return TYPE_AI_DRAWING_RESULT;
    }
}
