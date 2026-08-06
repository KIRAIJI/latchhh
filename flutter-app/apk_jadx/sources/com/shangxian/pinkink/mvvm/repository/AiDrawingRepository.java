package com.shangxian.pinkink.mvvm.repository;

import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.MVVMBaseRepository;
import com.shangxian.pinkink.api.AiDrawingService;
import com.shangxian.pinkink.api.Api;
import com.shangxian.pinkink.bean.AiDrawingResultBean;
import com.shangxian.pinkink.bean.ApiResult;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AiDrawingRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0018\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u00100\u000fJ!\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0016R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, d2 = {"Lcom/shangxian/pinkink/mvvm/repository/AiDrawingRepository;", "Lcom/lazyee/klib/mvvm/MVVMBaseRepository;", "()V", "aiDrawingService", "Lcom/shangxian/pinkink/api/AiDrawingService;", "getAiDrawingService", "()Lcom/shangxian/pinkink/api/AiDrawingService;", "aiDrawingService$delegate", "Lkotlin/Lazy;", "getAiDrawingTaskList", "", "pageNum", "", "pageSize", "callback", "Lcom/lazyee/klib/http/ApiCallback3;", "Lcom/shangxian/pinkink/bean/ApiResult;", "", "Lcom/shangxian/pinkink/bean/AiDrawingResultBean;", "submitAiDrawingTask", "text", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class AiDrawingRepository extends MVVMBaseRepository {

    /* JADX INFO: renamed from: aiDrawingService$delegate, reason: from kotlin metadata */
    private final Lazy aiDrawingService = LazyKt.lazy(new Function0<AiDrawingService>() { // from class: com.shangxian.pinkink.mvvm.repository.AiDrawingRepository$aiDrawingService$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final AiDrawingService invoke() {
            return (AiDrawingService) Api.INSTANCE.getInstance().create(AiDrawingService.class);
        }
    });

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.AiDrawingRepository$submitAiDrawingTask$1, reason: invalid class name */
    /* JADX INFO: compiled from: AiDrawingRepository.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.AiDrawingRepository", f = "AiDrawingRepository.kt", i = {0}, l = {20, 22}, m = "submitAiDrawingTask", n = {"this"}, s = {"L$0"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AiDrawingRepository.this.submitAiDrawingTask(null, this);
        }
    }

    private final AiDrawingService getAiDrawingService() {
        return (AiDrawingService) this.aiDrawingService.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x007d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object submitAiDrawingTask(java.lang.String r7, kotlin.coroutines.Continuation<? super java.util.List<com.shangxian.pinkink.bean.AiDrawingResultBean>> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.shangxian.pinkink.mvvm.repository.AiDrawingRepository.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r8
            com.shangxian.pinkink.mvvm.repository.AiDrawingRepository$submitAiDrawingTask$1 r0 = (com.shangxian.pinkink.mvvm.repository.AiDrawingRepository.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            com.shangxian.pinkink.mvvm.repository.AiDrawingRepository$submitAiDrawingTask$1 r0 = new com.shangxian.pinkink.mvvm.repository.AiDrawingRepository$submitAiDrawingTask$1
            r0.<init>(r8)
        L19:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 0
            r5 = 1
            if (r2 == 0) goto L3e
            if (r2 == r5) goto L36
            if (r2 != r3) goto L2e
            kotlin.ResultKt.throwOnFailure(r8)
            goto L79
        L2e:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L36:
            java.lang.Object r7 = r0.L$0
            com.shangxian.pinkink.mvvm.repository.AiDrawingRepository r7 = (com.shangxian.pinkink.mvvm.repository.AiDrawingRepository) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5b
        L3e:
            kotlin.ResultKt.throwOnFailure(r8)
            com.shangxian.pinkink.api.Api$Companion r8 = com.shangxian.pinkink.api.Api.INSTANCE
            com.lazyee.klib.http.ApiManager r8 = r8.getInstance()
            com.shangxian.pinkink.api.AiDrawingService r2 = r6.getAiDrawingService()
            retrofit2.Call r7 = r2.text2Img(r7)
            r0.L$0 = r6
            r0.label = r5
            java.lang.Object r8 = r8.request(r6, r7, r0)
            if (r8 != r1) goto L5a
            return r1
        L5a:
            r7 = r6
        L5b:
            com.shangxian.pinkink.bean.ApiResult r8 = (com.shangxian.pinkink.bean.ApiResult) r8
            if (r8 != 0) goto L60
            return r4
        L60:
            com.shangxian.pinkink.api.Api$Companion r8 = com.shangxian.pinkink.api.Api.INSTANCE
            com.lazyee.klib.http.ApiManager r8 = r8.getInstance()
            com.shangxian.pinkink.api.AiDrawingService r2 = r7.getAiDrawingService()
            retrofit2.Call r2 = r2.getAiDrawingTaskList(r5, r5)
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r8 = r8.request(r7, r2, r0)
            if (r8 != r1) goto L79
            return r1
        L79:
            com.shangxian.pinkink.bean.ApiResult r8 = (com.shangxian.pinkink.bean.ApiResult) r8
            if (r8 != 0) goto L7e
            return r4
        L7e:
            java.lang.Object r7 = r8.getData()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.mvvm.repository.AiDrawingRepository.submitAiDrawingTask(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void getAiDrawingTaskList(int pageNum, int pageSize, ApiCallback3<ApiResult<List<AiDrawingResultBean>>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getAiDrawingService().getAiDrawingTaskList(pageNum, pageSize), callback);
    }
}
