package com.shangxian.pinkink.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.lazyee.klib.http.ApiCallback2;
import com.lazyee.klib.mvvm.MVVMBaseViewModel;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.FontBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.db.PinkInkDB;
import com.shangxian.pinkink.db.dao.UserFontDao;
import com.shangxian.pinkink.mvvm.repository.FontRepository;
import com.shangxian.pinkink.mvvm.repository.UserRepository;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: FontViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0006J\u0006\u0010\u0016\u001a\u00020\u0014J\u0006\u0010\u0017\u001a\u00020\u0014R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/shangxian/pinkink/mvvm/viewmodel/FontViewModel;", "Lcom/lazyee/klib/mvvm/MVVMBaseViewModel;", "()V", "fontListLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/shangxian/pinkink/bean/FontBean;", "getFontListLiveData", "()Landroidx/lifecycle/MutableLiveData;", "fontRepository", "Lcom/shangxian/pinkink/mvvm/repository/FontRepository;", "getFontRepository", "()Lcom/shangxian/pinkink/mvvm/repository/FontRepository;", "fontRepository$delegate", "Lkotlin/Lazy;", "userFontListLiveData", "getUserFontListLiveData", "userRepository", "Lcom/shangxian/pinkink/mvvm/repository/UserRepository;", "addFontToUser", "", "font", "getFontList", "getUserFontList", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class FontViewModel extends MVVMBaseViewModel {

    /* JADX INFO: renamed from: fontRepository$delegate, reason: from kotlin metadata */
    private final Lazy fontRepository = LazyKt.lazy(new Function0<FontRepository>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.FontViewModel$fontRepository$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FontRepository invoke() {
            return new FontRepository();
        }
    });
    private final UserRepository userRepository = UserRepository.INSTANCE.getUserRepository();
    private final MutableLiveData<List<FontBean>> fontListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<FontBean>> userFontListLiveData = new MutableLiveData<>();

    private final FontRepository getFontRepository() {
        return (FontRepository) this.fontRepository.getValue();
    }

    public final MutableLiveData<List<FontBean>> getFontListLiveData() {
        return this.fontListLiveData;
    }

    public final MutableLiveData<List<FontBean>> getUserFontListLiveData() {
        return this.userFontListLiveData;
    }

    public final void getFontList() {
        onPageLoading();
        getFontRepository().getFontList(new ApiCallback2<ApiResult<List<FontBean>>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.FontViewModel.getFontList.1
            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<List<FontBean>> result) {
                FontViewModel.this.onPageLoadFailure();
            }

            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<List<FontBean>> result) {
                FontViewModel.this.onPageLoadSuccess();
                FontViewModel.this.getFontListLiveData().postValue(result == null ? null : result.getData());
            }
        });
    }

    public final void addFontToUser(FontBean font) {
        Intrinsics.checkNotNullParameter(font, "font");
        UserInfoBean userInfo = this.userRepository.getUserInfo();
        if (userInfo == null) {
            return;
        }
        font.setUserId(userInfo.getId());
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(font, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.FontViewModel$addFontToUser$1, reason: invalid class name */
    /* JADX INFO: compiled from: FontViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.FontViewModel$addFontToUser$1", f = "FontViewModel.kt", i = {}, l = {46}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ FontBean $font;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(FontBean fontBean, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$font = fontBean;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$font, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (PinkInkDB.INSTANCE.db().userFontDao().addFont(this.$font, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final void getUserFontList() {
        UserInfoBean userInfo = this.userRepository.getUserInfo();
        if (userInfo == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00901(userInfo, this, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.FontViewModel$getUserFontList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FontViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.FontViewModel$getUserFontList$1", f = "FontViewModel.kt", i = {}, l = {54}, m = "invokeSuspend", n = {}, s = {})
    static final class C00901 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ UserInfoBean $userInfo;
        int label;
        final /* synthetic */ FontViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00901(UserInfoBean userInfoBean, FontViewModel fontViewModel, Continuation<? super C00901> continuation) {
            super(2, continuation);
            this.$userInfo = userInfoBean;
            this.this$0 = fontViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00901(this.$userInfo, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00901) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UserFontDao userFontDao = PinkInkDB.INSTANCE.db().userFontDao();
                String id = this.$userInfo.getId();
                Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
                this.label = 1;
                obj = userFontDao.getUserFontList(id, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.this$0.getUserFontListLiveData().postValue((List) obj);
            return Unit.INSTANCE;
        }
    }
}
