package com.shangxian.pinkink.constants;

import kotlin.Metadata;

/* JADX INFO: compiled from: AiDrawingStatus.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/constants/AiDrawingStatus;", "", "()V", "STATUS_DEFAULT", "", "STATUS_FAIL", "STATUS_GENERATING", "STATUS_IN_QUEUE", "STATUS_SUCCESS", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class AiDrawingStatus {
    public static final AiDrawingStatus INSTANCE = new AiDrawingStatus();
    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_FAIL = 4;
    public static final int STATUS_GENERATING = 3;
    public static final int STATUS_IN_QUEUE = 2;
    public static final int STATUS_SUCCESS = 5;

    private AiDrawingStatus() {
    }
}
