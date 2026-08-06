package com.shangxian.pinkink.ui.ai.adapter;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.shangxian.pinkink.bean.ChatMsgBean;
import com.shangxian.pinkink.ui.ai.adapter.provider.ChatAiDrawingRecommendProvider;
import com.shangxian.pinkink.ui.ai.adapter.provider.ChatAiDrawingResultProvider;
import com.shangxian.pinkink.ui.ai.adapter.provider.ChatAiDrawingThinkingProvider;
import com.shangxian.pinkink.ui.ai.adapter.provider.ChatTextMsgFromMeProvider;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChatAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0014¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/ui/ai/adapter/ChatAdapter;", "Lcom/chad/library/adapter/base/BaseProviderMultiAdapter;", "Lcom/shangxian/pinkink/bean/ChatMsgBean;", "()V", "getItemType", "", "data", "", "position", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ChatAdapter extends BaseProviderMultiAdapter<ChatMsgBean> {
    public ChatAdapter() {
        super(null, 1, null);
        addItemProvider(new ChatTextMsgFromMeProvider());
        addItemProvider(new ChatAiDrawingRecommendProvider());
        addItemProvider(new ChatAiDrawingThinkingProvider());
        addItemProvider(new ChatAiDrawingResultProvider());
    }

    @Override // com.chad.library.adapter.base.BaseProviderMultiAdapter
    protected int getItemType(List<? extends ChatMsgBean> data, int position) {
        Intrinsics.checkNotNullParameter(data, "data");
        return data.get(position).getItemType();
    }
}
