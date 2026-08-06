package com.shangxian.pinkink.ui.ai.adapter.provider;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lazyee.klib.extension.AnyExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.bean.ChatAiDrawingResultBean;
import com.shangxian.pinkink.bean.ChatMsgBean;
import com.shangxian.pinkink.constants.ChatMsgType;
import com.shangxian.pinkink.ui.themes.TransThemesActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChatAiDrawingResultProvider.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/shangxian/pinkink/ui/ai/adapter/provider/ChatAiDrawingResultProvider;", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "Lcom/shangxian/pinkink/bean/ChatMsgBean;", "()V", "itemViewType", "", "getItemViewType", "()I", "layoutId", "getLayoutId", "themesItemHeight", "themesItemWidth", "convert", "", "helper", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "item", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ChatAiDrawingResultProvider extends BaseItemProvider<ChatMsgBean> {
    private final int themesItemHeight;
    private final int themesItemWidth;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_chat_ai_drawing_result;
    }

    public ChatAiDrawingResultProvider() {
        int screenWidth = AnyExtensionsKt.getScreenWidth(this) - NumberExtensionsKt.dp2px(80);
        this.themesItemWidth = screenWidth;
        this.themesItemHeight = (int) (((double) screenWidth) * 1.733d);
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return ChatMsgType.INSTANCE.getTYPE_AI_DRAWING_RESULT();
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, ChatMsgBean item) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        final ChatAiDrawingResultBean chatAiDrawingResultBean = (ChatAiDrawingResultBean) item;
        RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.ivAiDrawResult);
        TextView textView = (TextView) helper.getView(R.id.tvTime);
        roundedImageView.setLayoutParams(new LinearLayout.LayoutParams(this.themesItemWidth, this.themesItemHeight));
        roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.ai.adapter.provider.ChatAiDrawingResultProvider$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChatAiDrawingResultProvider.m130convert$lambda0(this.f$0, chatAiDrawingResultBean, view);
            }
        });
        Glide.with(getContext()).load(chatAiDrawingResultBean.getImgUrl()).into(roundedImageView);
        if (TextUtils.isEmpty(chatAiDrawingResultBean.getTime())) {
            ViewExtensionsKt.gone(textView);
        } else {
            textView.setText(chatAiDrawingResultBean.getTime());
            ViewExtensionsKt.visible(textView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: convert$lambda-0, reason: not valid java name */
    public static final void m130convert$lambda0(ChatAiDrawingResultProvider this$0, ChatAiDrawingResultBean chatAiDrawingResult, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(chatAiDrawingResult, "$chatAiDrawingResult");
        TransThemesActivity.Companion companion = TransThemesActivity.INSTANCE;
        Context context = this$0.getContext();
        String imgUrl = chatAiDrawingResult.getImgUrl();
        if (imgUrl == null) {
            imgUrl = "";
        }
        companion.gotoThis(context, imgUrl);
    }
}
