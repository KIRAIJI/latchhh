package com.shangxian.pinkink.ui.ai.adapter.provider;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.bean.ChatAiDrawingRecommendFromOtherBean;
import com.shangxian.pinkink.bean.ChatMsgBean;
import com.shangxian.pinkink.constants.ChatMsgType;
import com.shangxian.pinkink.ui.ai.AiDrawingChatActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChatAiDrawingRecommendProvider.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0002H\u0016R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/shangxian/pinkink/ui/ai/adapter/provider/ChatAiDrawingRecommendProvider;", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "Lcom/shangxian/pinkink/bean/ChatMsgBean;", "()V", "itemViewType", "", "getItemViewType", "()I", "layoutId", "getLayoutId", "clickRecommendItem", "", "text", "", "convert", "helper", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "item", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ChatAiDrawingRecommendProvider extends BaseItemProvider<ChatMsgBean> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_chat_ai_drawing_recommend;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return ChatMsgType.INSTANCE.getTYPE_AI_DRAWING_RECOMMEND();
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, ChatMsgBean item) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        ChatAiDrawingRecommendFromOtherBean chatAiDrawingRecommendFromOtherBean = (ChatAiDrawingRecommendFromOtherBean) item;
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.llRecommendItem1);
        LinearLayout linearLayout2 = (LinearLayout) helper.getView(R.id.llRecommendItem2);
        LinearLayout linearLayout3 = (LinearLayout) helper.getView(R.id.llRecommendItem3);
        final TextView textView = (TextView) helper.getView(R.id.tvRecommendItem1);
        final TextView textView2 = (TextView) helper.getView(R.id.tvRecommendItem2);
        final TextView textView3 = (TextView) helper.getView(R.id.tvRecommendItem3);
        textView.setText(chatAiDrawingRecommendFromOtherBean.getRecommendList().get(0));
        textView2.setText(chatAiDrawingRecommendFromOtherBean.getRecommendList().get(1));
        textView3.setText(chatAiDrawingRecommendFromOtherBean.getRecommendList().get(2));
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.ai.adapter.provider.ChatAiDrawingRecommendProvider$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChatAiDrawingRecommendProvider.m126convert$lambda0(this.f$0, textView, view);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.ai.adapter.provider.ChatAiDrawingRecommendProvider$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChatAiDrawingRecommendProvider.m127convert$lambda1(this.f$0, textView2, view);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.ai.adapter.provider.ChatAiDrawingRecommendProvider$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChatAiDrawingRecommendProvider.m128convert$lambda2(this.f$0, textView3, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: convert$lambda-0, reason: not valid java name */
    public static final void m126convert$lambda0(ChatAiDrawingRecommendProvider this$0, TextView tvRecommendItem1, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvRecommendItem1, "$tvRecommendItem1");
        this$0.clickRecommendItem(tvRecommendItem1.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: convert$lambda-1, reason: not valid java name */
    public static final void m127convert$lambda1(ChatAiDrawingRecommendProvider this$0, TextView tvRecommendItem2, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvRecommendItem2, "$tvRecommendItem2");
        this$0.clickRecommendItem(tvRecommendItem2.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: convert$lambda-2, reason: not valid java name */
    public static final void m128convert$lambda2(ChatAiDrawingRecommendProvider this$0, TextView tvRecommendItem3, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvRecommendItem3, "$tvRecommendItem3");
        this$0.clickRecommendItem(tvRecommendItem3.getText().toString());
    }

    private final void clickRecommendItem(String text) {
        if (getContext() instanceof AiDrawingChatActivity) {
            ((AiDrawingChatActivity) getContext()).sendMsg(text);
        }
    }
}
