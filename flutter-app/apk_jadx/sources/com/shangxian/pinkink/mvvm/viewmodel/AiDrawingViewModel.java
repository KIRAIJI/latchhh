package com.shangxian.pinkink.mvvm.viewmodel;

import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.MVVMBaseViewModel;
import com.shangxian.pinkink.bean.AiDrawingResultBean;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.ChatTextMsgFromMeBean;
import com.shangxian.pinkink.mvvm.repository.AiDrawingRepository;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: AiDrawingViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JL\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2,\u0010\u0010\u001a(\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012\u0012\u0004\u0012\u00020\n0\u0011j\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012`\u0014JJ\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172(\u0010\u0018\u001a$\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u0012\u0012\u0004\u0012\u00020\n0\u0011j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u0012`\u00142\u0010\u0010\u0019\u001a\f\u0012\u0004\u0012\u00020\n0\u001aj\u0002`\u001bR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001c"}, d2 = {"Lcom/shangxian/pinkink/mvvm/viewmodel/AiDrawingViewModel;", "Lcom/lazyee/klib/mvvm/MVVMBaseViewModel;", "()V", "aiDrawingRepository", "Lcom/shangxian/pinkink/mvvm/repository/AiDrawingRepository;", "getAiDrawingRepository", "()Lcom/shangxian/pinkink/mvvm/repository/AiDrawingRepository;", "aiDrawingRepository$delegate", "Lkotlin/Lazy;", "getAiDrawingTaskList", "", "pageNum", "", "pageSize", "isShowPageLoading", "", "callback", "Lkotlin/Function1;", "", "Lcom/shangxian/pinkink/bean/AiDrawingResultBean;", "Lcom/lazyee/klib/typed/TCallback;", "submitAiDrawingTask", "chatMsg", "Lcom/shangxian/pinkink/bean/ChatTextMsgFromMeBean;", "onComplete", "onFail", "Lkotlin/Function0;", "Lcom/lazyee/klib/typed/VoidCallback;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class AiDrawingViewModel extends MVVMBaseViewModel {

    /* JADX INFO: renamed from: aiDrawingRepository$delegate, reason: from kotlin metadata */
    private final Lazy aiDrawingRepository = LazyKt.lazy(new Function0<AiDrawingRepository>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.AiDrawingViewModel$aiDrawingRepository$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final AiDrawingRepository invoke() {
            return new AiDrawingRepository();
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public final AiDrawingRepository getAiDrawingRepository() {
        return (AiDrawingRepository) this.aiDrawingRepository.getValue();
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.AiDrawingViewModel$submitAiDrawingTask$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AiDrawingViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.AiDrawingViewModel$submitAiDrawingTask$1", f = "AiDrawingViewModel.kt", i = {}, l = {26, 27}, m = "invokeSuspend", n = {}, s = {})
    static final class C00881 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ChatTextMsgFromMeBean $chatMsg;
        final /* synthetic */ Function1<List<AiDrawingResultBean>, Unit> $onComplete;
        final /* synthetic */ Function0<Unit> $onFail;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C00881(ChatTextMsgFromMeBean chatTextMsgFromMeBean, Function0<Unit> function0, Function1<? super List<AiDrawingResultBean>, Unit> function1, Continuation<? super C00881> continuation) {
            super(2, continuation);
            this.$chatMsg = chatTextMsgFromMeBean;
            this.$onFail = function0;
            this.$onComplete = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AiDrawingViewModel.this.new C00881(this.$chatMsg, this.$onFail, this.$onComplete, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00881) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = AiDrawingViewModel.this.getAiDrawingRepository().submitAiDrawingTask(this.$chatMsg.getContent(), this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    if (i == 2) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.label = 2;
            if (BuildersKt.withContext(Dispatchers.getMain(), new C00151((List) obj, this.$onFail, this.$onComplete, null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.AiDrawingViewModel$submitAiDrawingTask$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AiDrawingViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.AiDrawingViewModel$submitAiDrawingTask$1$1", f = "AiDrawingViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00151 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ List<AiDrawingResultBean> $aiDrawingResultList;
            final /* synthetic */ Function1<List<AiDrawingResultBean>, Unit> $onComplete;
            final /* synthetic */ Function0<Unit> $onFail;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C00151(List<AiDrawingResultBean> list, Function0<Unit> function0, Function1<? super List<AiDrawingResultBean>, Unit> function1, Continuation<? super C00151> continuation) {
                super(2, continuation);
                this.$aiDrawingResultList = list;
                this.$onFail = function0;
                this.$onComplete = function1;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00151(this.$aiDrawingResultList, this.$onFail, this.$onComplete, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00151) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                List<AiDrawingResultBean> list = this.$aiDrawingResultList;
                if (list == null) {
                    this.$onFail.invoke();
                    return Unit.INSTANCE;
                }
                this.$onComplete.invoke(list);
                return Unit.INSTANCE;
            }
        }
    }

    public final void submitAiDrawingTask(ChatTextMsgFromMeBean chatMsg, Function1<? super List<AiDrawingResultBean>, Unit> onComplete, Function0<Unit> onFail) {
        Intrinsics.checkNotNullParameter(chatMsg, "chatMsg");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        Intrinsics.checkNotNullParameter(onFail, "onFail");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00881(chatMsg, onFail, onComplete, null), 3, null);
    }

    public final void getAiDrawingTaskList(int pageNum, int pageSize, boolean isShowPageLoading, final Function1<? super List<AiDrawingResultBean>, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (isShowPageLoading) {
            onPageLoading();
        }
        getAiDrawingRepository().getAiDrawingTaskList(pageNum, pageSize, (ApiCallback3) new ApiCallback3<ApiResult<List<? extends AiDrawingResultBean>>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.AiDrawingViewModel.getAiDrawingTaskList.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<List<AiDrawingResultBean>> result) {
                List<AiDrawingResultBean> data;
                AiDrawingViewModel.this.onPageLoadSuccess();
                List<AiDrawingResultBean> listReversed = null;
                if ((result == null ? null : result.getData()) == null) {
                    return;
                }
                Function1<List<AiDrawingResultBean>, Unit> function1 = callback;
                if (result != null && (data = result.getData()) != null) {
                    listReversed = CollectionsKt.reversed(data);
                }
                function1.invoke(listReversed);
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<List<AiDrawingResultBean>> result) {
                AiDrawingViewModel.this.onPageLoadFailure();
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                AiDrawingViewModel.this.onPageLoadFailure();
            }
        });
    }
}
