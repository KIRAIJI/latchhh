package com.shangxian.pinkink.ui.ai;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.listener.OnKeyboardVisibleListener;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.ViewModel;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.bean.AiDrawingResultBean;
import com.shangxian.pinkink.bean.ChatAiDrawingRecommendFromOtherBean;
import com.shangxian.pinkink.bean.ChatAiDrawingResultBean;
import com.shangxian.pinkink.bean.ChatAiThinkingBean;
import com.shangxian.pinkink.bean.ChatMsgBean;
import com.shangxian.pinkink.bean.ChatTextMsgFromMeBean;
import com.shangxian.pinkink.constants.ChatMsgSendStatus;
import com.shangxian.pinkink.databinding.ActivityAiDrawingChatBinding;
import com.shangxian.pinkink.mvvm.viewmodel.AiDrawingViewModel;
import com.shangxian.pinkink.ui.ai.adapter.ChatAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AiDrawingChatActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0019H\u0016J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u0016H\u0016J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00140\"2\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u0019H\u0002J\u0010\u0010&\u001a\u00020\u00192\u0006\u0010'\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\u0016H\u0002R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/shangxian/pinkink/ui/ai/AiDrawingChatActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityAiDrawingChatBinding;", "Lcom/lazyee/klib/listener/OnKeyboardVisibleListener;", "()V", "aiDrawingRecommendFromOther", "Lcom/shangxian/pinkink/bean/ChatAiDrawingRecommendFromOtherBean;", "getAiDrawingRecommendFromOther", "()Lcom/shangxian/pinkink/bean/ChatAiDrawingRecommendFromOtherBean;", "aiDrawingRecommendFromOther$delegate", "Lkotlin/Lazy;", "aiDrawingViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/AiDrawingViewModel;", "getAiDrawingViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/AiDrawingViewModel;", "aiDrawingViewModel$delegate", "chatAdapter", "Lcom/shangxian/pinkink/ui/ai/adapter/ChatAdapter;", "chatList", "", "Lcom/shangxian/pinkink/bean/ChatMsgBean;", "pageNum", "", "pageSize", "getAiDrawingTaskList", "", "initView", "onPageLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onSoftKeyboardHide", "onSoftKeyboardShow", "keyboardHeight", "parseAiDrawingResult", "", "bean", "Lcom/shangxian/pinkink/bean/AiDrawingResultBean;", "scrollToBottom", "sendMsg", NotificationCompat.CATEGORY_MESSAGE, "", "setKeyboardMarginBottom", "value", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class AiDrawingChatActivity extends BaseActivity<ActivityAiDrawingChatBinding> implements OnKeyboardVisibleListener {

    /* JADX INFO: renamed from: aiDrawingViewModel$delegate, reason: from kotlin metadata */
    private final Lazy aiDrawingViewModel = LazyKt.lazy(new Function0<AiDrawingViewModel>() { // from class: com.shangxian.pinkink.ui.ai.AiDrawingChatActivity$aiDrawingViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final AiDrawingViewModel invoke() {
            return (AiDrawingViewModel) new ViewModelProvider(this.this$0).get(AiDrawingViewModel.class);
        }
    });
    private final List<ChatMsgBean> chatList = new ArrayList();
    private final ChatAdapter chatAdapter = new ChatAdapter();
    private int pageNum = 1;
    private int pageSize = 200;

    /* JADX INFO: renamed from: aiDrawingRecommendFromOther$delegate, reason: from kotlin metadata */
    private final Lazy aiDrawingRecommendFromOther = LazyKt.lazy(new Function0<ChatAiDrawingRecommendFromOtherBean>() { // from class: com.shangxian.pinkink.ui.ai.AiDrawingChatActivity$aiDrawingRecommendFromOther$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ChatAiDrawingRecommendFromOtherBean invoke() {
            ChatAiDrawingRecommendFromOtherBean chatAiDrawingRecommendFromOtherBean = new ChatAiDrawingRecommendFromOtherBean();
            AiDrawingChatActivity aiDrawingChatActivity = this.this$0;
            List<String> recommendList = chatAiDrawingRecommendFromOtherBean.getRecommendList();
            String string = aiDrawingChatActivity.getString(R.string.string_ai_example_prompt1);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.string_ai_example_prompt1)");
            recommendList.add(string);
            List<String> recommendList2 = chatAiDrawingRecommendFromOtherBean.getRecommendList();
            String string2 = aiDrawingChatActivity.getString(R.string.string_ai_example_prompt2);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.string_ai_example_prompt2)");
            recommendList2.add(string2);
            List<String> recommendList3 = chatAiDrawingRecommendFromOtherBean.getRecommendList();
            String string3 = aiDrawingChatActivity.getString(R.string.string_ai_example_prompt3);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(R.string.string_ai_example_prompt3)");
            recommendList3.add(string3);
            return chatAiDrawingRecommendFromOtherBean;
        }
    });

    /* JADX INFO: compiled from: AiDrawingChatActivity.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoadingState.values().length];
            iArr[LoadingState.LOADING.ordinal()] = 1;
            iArr[LoadingState.SUCCESS.ordinal()] = 2;
            iArr[LoadingState.FAILURE.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @ViewModel
    private final AiDrawingViewModel getAiDrawingViewModel() {
        return (AiDrawingViewModel) this.aiDrawingViewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChatAiDrawingRecommendFromOtherBean getAiDrawingRecommendFromOther() {
        return (ChatAiDrawingRecommendFromOtherBean) this.aiDrawingRecommendFromOther.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivityAiDrawingChatBinding activityAiDrawingChatBinding = (ActivityAiDrawingChatBinding) getMViewBinding();
        Iterator it = activityAiDrawingChatBinding.pageStateSwitcher.getTargetViews(R.id.tvRefresh).iterator();
        while (it.hasNext()) {
            ((TextView) it.next()).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.ai.AiDrawingChatActivity$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AiDrawingChatActivity.m122initView$lambda4$lambda1$lambda0(this.f$0, view);
                }
            });
        }
        activityAiDrawingChatBinding.titleBar.tvTitle.setText(getString(R.string.title_ai_drawing));
        activityAiDrawingChatBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.ai.AiDrawingChatActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AiDrawingChatActivity.m123initView$lambda4$lambda2(this.f$0, view);
            }
        });
        activityAiDrawingChatBinding.ivSend.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.ai.AiDrawingChatActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AiDrawingChatActivity.m124initView$lambda4$lambda3(this.f$0, activityAiDrawingChatBinding, view);
            }
        });
        this.chatList.add(getAiDrawingRecommendFromOther());
        this.chatAdapter.setNewInstance(this.chatList);
        activityAiDrawingChatBinding.refreshLayout.setEnableRefresh(false);
        activityAiDrawingChatBinding.rvContent.setLayoutManager(new LinearLayoutManager(getActivity(), 1, true));
        activityAiDrawingChatBinding.rvContent.setAdapter(this.chatAdapter);
        addOnKeyboardVisibleListener(this);
        getAiDrawingTaskList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-1$lambda-0, reason: not valid java name */
    public static final void m122initView$lambda4$lambda1$lambda0(AiDrawingChatActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getAiDrawingTaskList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-2, reason: not valid java name */
    public static final void m123initView$lambda4$lambda2(AiDrawingChatActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-3, reason: not valid java name */
    public static final void m124initView$lambda4$lambda3(AiDrawingChatActivity this$0, ActivityAiDrawingChatBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this$0.sendMsg(this_run.etMsg.getText().toString());
        ((ActivityAiDrawingChatBinding) this$0.getMViewBinding()).etMsg.setText("");
    }

    private final void getAiDrawingTaskList() {
        getAiDrawingViewModel().getAiDrawingTaskList(this.pageNum, this.pageSize, true, new Function1<List<? extends AiDrawingResultBean>, Unit>() { // from class: com.shangxian.pinkink.ui.ai.AiDrawingChatActivity.getAiDrawingTaskList.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends AiDrawingResultBean> list) {
                invoke2((List<AiDrawingResultBean>) list);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<AiDrawingResultBean> list) {
                AiDrawingChatActivity.this.chatList.clear();
                AiDrawingChatActivity.this.chatList.add(AiDrawingChatActivity.this.getAiDrawingRecommendFromOther());
                if (list != null) {
                    AiDrawingChatActivity aiDrawingChatActivity = AiDrawingChatActivity.this;
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        aiDrawingChatActivity.chatList.addAll(0, aiDrawingChatActivity.parseAiDrawingResult((AiDrawingResultBean) it.next()));
                    }
                }
                AiDrawingChatActivity.this.chatAdapter.notifyDataSetChanged();
                AiDrawingChatActivity.this.scrollToBottom();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<ChatMsgBean> parseAiDrawingResult(AiDrawingResultBean bean) {
        ArrayList arrayList = new ArrayList();
        ChatTextMsgFromMeBean chatTextMsgFromMeBean = new ChatTextMsgFromMeBean();
        chatTextMsgFromMeBean.setSendStatus(ChatMsgSendStatus.INSTANCE.getSTATUS_SEND_COMPLETE());
        String txt = bean.getTxt();
        if (txt == null) {
            txt = "";
        }
        chatTextMsgFromMeBean.setContent(txt);
        arrayList.add(0, chatTextMsgFromMeBean);
        ChatAiThinkingBean chatAiThinkingBean = new ChatAiThinkingBean();
        chatAiThinkingBean.setContent(bean.getMsg());
        chatAiThinkingBean.setDrawingStatus(bean.getState());
        arrayList.add(0, chatAiThinkingBean);
        if (bean.getState() == 5) {
            ChatAiDrawingResultBean chatAiDrawingResultBean = new ChatAiDrawingResultBean();
            chatAiDrawingResultBean.setImgUrl(bean.getImg());
            String time = bean.getTime();
            chatAiDrawingResultBean.setTime(time != null ? time : "");
            arrayList.add(0, chatAiDrawingResultBean);
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.BaseActivity, com.lazyee.klib.mvvm.MVVMBaseView
    public void onPageLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        super.onPageLoadingStateChanged(state);
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            ((ActivityAiDrawingChatBinding) getMViewBinding()).pageStateSwitcher.showLoadingView();
        } else if (i == 2) {
            ((ActivityAiDrawingChatBinding) getMViewBinding()).pageStateSwitcher.showContentView();
        } else {
            if (i != 3) {
                return;
            }
            ((ActivityAiDrawingChatBinding) getMViewBinding()).pageStateSwitcher.showNetworkErrorView();
        }
    }

    public final void sendMsg(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        ChatTextMsgFromMeBean chatTextMsgFromMeBean = new ChatTextMsgFromMeBean();
        chatTextMsgFromMeBean.setContent(msg);
        getAiDrawingViewModel().submitAiDrawingTask(chatTextMsgFromMeBean, new Function1<List<? extends AiDrawingResultBean>, Unit>() { // from class: com.shangxian.pinkink.ui.ai.AiDrawingChatActivity.sendMsg.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends AiDrawingResultBean> list) {
                invoke2((List<AiDrawingResultBean>) list);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<AiDrawingResultBean> list) {
                Intrinsics.checkNotNullParameter(list, "list");
                AiDrawingChatActivity aiDrawingChatActivity = AiDrawingChatActivity.this;
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    aiDrawingChatActivity.chatList.addAll(0, aiDrawingChatActivity.parseAiDrawingResult((AiDrawingResultBean) it.next()));
                }
                AiDrawingChatActivity.this.chatAdapter.notifyDataSetChanged();
                AiDrawingChatActivity.this.scrollToBottom();
            }
        }, new Function0<Unit>() { // from class: com.shangxian.pinkink.ui.ai.AiDrawingChatActivity.sendMsg.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                AiDrawingChatActivity aiDrawingChatActivity = AiDrawingChatActivity.this;
                AiDrawingChatActivity aiDrawingChatActivity2 = aiDrawingChatActivity;
                String string = aiDrawingChatActivity.getString(R.string.toast_submit_ai_drawing_task_failed);
                Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…t_ai_drawing_task_failed)");
                ContextExtensionsKt.toastShort(aiDrawingChatActivity2, string);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void scrollToBottom() {
        ((ActivityAiDrawingChatBinding) getMViewBinding()).rvContent.scrollToPosition(0);
    }

    @Override // com.lazyee.klib.listener.OnKeyboardVisibleListener
    public void onSoftKeyboardHide() {
        setKeyboardMarginBottom(0);
    }

    @Override // com.lazyee.klib.listener.OnKeyboardVisibleListener
    public void onSoftKeyboardShow(int keyboardHeight) {
        setKeyboardMarginBottom(keyboardHeight);
        scrollToBottom();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void setKeyboardMarginBottom(int value) {
        ViewGroup.LayoutParams layoutParams = ((ActivityAiDrawingChatBinding) getMViewBinding()).llBottom.getLayoutParams();
        Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
        layoutParams2.bottomMargin = value;
        ((ActivityAiDrawingChatBinding) getMViewBinding()).llBottom.setLayoutParams(layoutParams2);
    }
}
