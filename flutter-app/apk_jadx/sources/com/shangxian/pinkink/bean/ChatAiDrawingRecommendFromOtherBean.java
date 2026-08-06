package com.shangxian.pinkink.bean;

import com.shangxian.pinkink.constants.ChatMsgType;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: ChatAiDrawingRecommendFromOtherBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lcom/shangxian/pinkink/bean/ChatAiDrawingRecommendFromOtherBean;", "Lcom/shangxian/pinkink/bean/ChatMsgBean;", "()V", "itemType", "", "getItemType", "()I", "recommendList", "", "", "getRecommendList", "()Ljava/util/List;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ChatAiDrawingRecommendFromOtherBean extends ChatMsgBean {
    private final List<String> recommendList = new ArrayList();

    public final List<String> getRecommendList() {
        return this.recommendList;
    }

    @Override // com.shangxian.pinkink.bean.ChatMsgBean, com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return ChatMsgType.INSTANCE.getTYPE_AI_DRAWING_RECOMMEND();
    }
}
