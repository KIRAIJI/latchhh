package com.shangxian.pinkink.ui.ai.adapter.provider;

import android.widget.TextView;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.bean.ChatMsgBean;
import com.shangxian.pinkink.bean.ChatTextMsgFromMeBean;
import com.shangxian.pinkink.constants.ChatMsgType;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChatTextMsgFromMeProvider.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0002H\u0016R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/ui/ai/adapter/provider/ChatTextMsgFromMeProvider;", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "Lcom/shangxian/pinkink/bean/ChatMsgBean;", "()V", "itemViewType", "", "getItemViewType", "()I", "layoutId", "getLayoutId", "convert", "", "helper", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "item", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ChatTextMsgFromMeProvider extends BaseItemProvider<ChatMsgBean> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_chat_text_msg_from_me;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return ChatMsgType.INSTANCE.getTYPE_TEXT_MSG_FROM_ME();
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, ChatMsgBean item) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        ((TextView) helper.getView(R.id.tvTextMsg)).setText(((ChatTextMsgFromMeBean) item).getContent());
    }
}
