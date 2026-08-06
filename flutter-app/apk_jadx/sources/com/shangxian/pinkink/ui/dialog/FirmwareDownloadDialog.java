package com.shangxian.pinkink.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lazyee.klib.base.ViewBindingDialog;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.StringExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.listener.OnFileDownloadListener;
import com.lazyee.klib.util.FileUtils;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.bean.FirmwareVersionBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.databinding.DialogFontDownloadBinding;
import java.io.File;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: FirmwareDownloadDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001%B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0014\u0010\u001a\u001a\u00020\u00142\n\u0010\u001b\u001a\u00060\u001cj\u0002`\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\nH\u0016J\u0018\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\u0016J\u0010\u0010\"\u001a\u00020\u00002\b\u0010#\u001a\u0004\u0018\u00010\rJ\b\u0010$\u001a\u00020\u0014H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010¨\u0006&"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/FirmwareDownloadDialog;", "Lcom/lazyee/klib/base/ViewBindingDialog;", "Lcom/shangxian/pinkink/databinding/DialogFontDownloadBinding;", "Lcom/lazyee/klib/listener/OnFileDownloadListener;", "context", "Landroid/content/Context;", "firmwareVersion", "Lcom/shangxian/pinkink/bean/FirmwareVersionBean;", "(Landroid/content/Context;Lcom/shangxian/pinkink/bean/FirmwareVersionBean;)V", "dp28", "", "minProgressWidth", "onDownloadCompleteListener", "Lcom/shangxian/pinkink/ui/dialog/FirmwareDownloadDialog$OnDownloadCompleteListener;", "progressWidth", "getProgressWidth", "()I", "progressWidth$delegate", "Lkotlin/Lazy;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDownloadComplete", "file", "Ljava/io/File;", "onDownloadFailure", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onDownloadStart", "totalSize", "onDownloading", "currentSize", "setOnDownloadCompleteListener", "listener", "startDownload", "OnDownloadCompleteListener", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class FirmwareDownloadDialog extends ViewBindingDialog<DialogFontDownloadBinding> implements OnFileDownloadListener {
    private final int dp28;
    private final FirmwareVersionBean firmwareVersion;
    private final int minProgressWidth;
    private OnDownloadCompleteListener onDownloadCompleteListener;

    /* JADX INFO: renamed from: progressWidth$delegate, reason: from kotlin metadata */
    private final Lazy progressWidth;

    /* JADX INFO: compiled from: FirmwareDownloadDialog.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/FirmwareDownloadDialog$OnDownloadCompleteListener;", "", "onDownloadComplete", "", "firmwareVersion", "Lcom/shangxian/pinkink/bean/FirmwareVersionBean;", "file", "Ljava/io/File;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface OnDownloadCompleteListener {
        void onDownloadComplete(FirmwareVersionBean firmwareVersion, File file);
    }

    @Override // com.lazyee.klib.listener.OnFileDownloadListener
    public void onDownloadStart(int totalSize) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirmwareDownloadDialog(Context context, FirmwareVersionBean firmwareVersion) {
        super(context, R.style.Dialog_FontDownload);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(firmwareVersion, "firmwareVersion");
        this.firmwareVersion = firmwareVersion;
        this.minProgressWidth = NumberExtensionsKt.dp2px(50);
        this.dp28 = NumberExtensionsKt.dp2px(28);
        this.progressWidth = LazyKt.lazy(new Function0<Integer>() { // from class: com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$progressWidth$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                DialogFontDownloadBinding mViewBinding = this.this$0.getMViewBinding();
                Intrinsics.checkNotNull(mViewBinding);
                return Integer.valueOf(mViewBinding.llProgressBg.getMeasuredWidth() - this.this$0.minProgressWidth);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getProgressWidth() {
        return ((Number) this.progressWidth.getValue()).intValue();
    }

    @Override // com.lazyee.klib.base.ViewBindingDialog, android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        startDownload();
    }

    public final FirmwareDownloadDialog setOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
        this.onDownloadCompleteListener = listener;
        return this;
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$startDownload$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirmwareDownloadDialog.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$startDownload$1", f = "FirmwareDownloadDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C01091 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C01091(Continuation<? super C01091> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FirmwareDownloadDialog.this.new C01091(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01091) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            File file = new File(AppConfig.INSTANCE.getFirmwareSavePath() + '_' + StringExtensionsKt.md5(FirmwareDownloadDialog.this.firmwareVersion.getUrl()) + '.' + ((String) CollectionsKt.last(StringsKt.split$default((CharSequence) FirmwareDownloadDialog.this.firmwareVersion.getUrl(), new String[]{"."}, false, 0, 6, (Object) null))));
            if (file.exists()) {
                file.delete();
            }
            FileUtils.INSTANCE.download(FirmwareDownloadDialog.this.firmwareVersion.getUrl(), file, FirmwareDownloadDialog.this);
            return Unit.INSTANCE;
        }
    }

    private final void startDownload() {
        if (this.firmwareVersion.getUrl() == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C01091(null), 3, null);
    }

    @Override // com.lazyee.klib.listener.OnFileDownloadListener
    public void onDownloadComplete(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        String name = file.getName();
        Intrinsics.checkNotNullExpressionValue(name, "file.name");
        File file2 = new File(Intrinsics.stringPlus(AppConfig.INSTANCE.getFirmwareSavePath(), StringsKt.replace$default(name, "_", "", false, 4, (Object) null)));
        file.renameTo(file2);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getMain()), null, null, new AnonymousClass1(file2, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$onDownloadComplete$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirmwareDownloadDialog.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$onDownloadComplete$1", f = "FirmwareDownloadDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ File $newFile;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(File file, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$newFile = file;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FirmwareDownloadDialog.this.new AnonymousClass1(this.$newFile, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            OnDownloadCompleteListener onDownloadCompleteListener = FirmwareDownloadDialog.this.onDownloadCompleteListener;
            if (onDownloadCompleteListener != null) {
                onDownloadCompleteListener.onDownloadComplete(FirmwareDownloadDialog.this.firmwareVersion, this.$newFile);
            }
            FirmwareDownloadDialog.this.dismiss();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$onDownloadFailure$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirmwareDownloadDialog.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$onDownloadFailure$1", f = "FirmwareDownloadDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C01071 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C01071(Continuation<? super C01071> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FirmwareDownloadDialog.this.new C01071(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01071) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Context context = FirmwareDownloadDialog.this.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            String string = FirmwareDownloadDialog.this.getContext().getString(R.string.toast_download_fail);
            Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.toast_download_fail)");
            ContextExtensionsKt.toastShort(context, string);
            FirmwareDownloadDialog.this.dismiss();
            return Unit.INSTANCE;
        }
    }

    @Override // com.lazyee.klib.listener.OnFileDownloadListener
    public void onDownloadFailure(Exception e) {
        Intrinsics.checkNotNullParameter(e, "e");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getMain()), null, null, new C01071(null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$onDownloading$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirmwareDownloadDialog.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.ui.dialog.FirmwareDownloadDialog$onDownloading$1", f = "FirmwareDownloadDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C01081 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $currentSize;
        final /* synthetic */ int $totalSize;
        int label;
        final /* synthetic */ FirmwareDownloadDialog this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01081(int i, int i2, FirmwareDownloadDialog firmwareDownloadDialog, Continuation<? super C01081> continuation) {
            super(2, continuation);
            this.$currentSize = i;
            this.$totalSize = i2;
            this.this$0 = firmwareDownloadDialog;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C01081(this.$currentSize, this.$totalSize, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01081) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            LinearLayout linearLayout;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            float f = this.$currentSize / this.$totalSize;
            float progressWidth = this.this$0.getProgressWidth() * f;
            DialogFontDownloadBinding mViewBinding = this.this$0.getMViewBinding();
            if (mViewBinding != null && (linearLayout = mViewBinding.llProgress) != null) {
                ViewExtensionsKt.setSize(linearLayout, Boxing.boxInt(((int) progressWidth) + this.this$0.minProgressWidth), Boxing.boxInt(this.this$0.dp28));
            }
            DialogFontDownloadBinding mViewBinding2 = this.this$0.getMViewBinding();
            TextView textView = mViewBinding2 == null ? null : mViewBinding2.tvDownloadProgress;
            if (textView != null) {
                StringBuilder sb = new StringBuilder();
                sb.append((int) (f * 100));
                sb.append('%');
                textView.setText(sb.toString());
            }
            return Unit.INSTANCE;
        }
    }

    @Override // com.lazyee.klib.listener.OnFileDownloadListener
    public void onDownloading(int currentSize, int totalSize) {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getMain()), null, null, new C01081(currentSize, totalSize, this, null), 3, null);
    }
}
