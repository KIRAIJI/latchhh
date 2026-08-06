package com.lazyee.klib.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.core.app.NotificationCompat;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* JADX INFO: compiled from: SimpleHandler.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0003\u001a\f\u0012\u0004\u0012\u00020\u00040\u0005j\u0002`\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/lazyee/klib/handler/SimpleHandler;", "Landroid/os/Handler;", "()V", "callback", "", "Lkotlin/Function0;", "Lcom/lazyee/klib/typed/VoidCallback;", "handleMessage", NotificationCompat.CATEGORY_MESSAGE, "Landroid/os/Message;", "library_release"}, k = 1, mv = {1, 4, 2})
public final class SimpleHandler extends Handler {
    public SimpleHandler() {
        super(Looper.getMainLooper());
    }

    public final void callback(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Message message = new Message();
        message.obj = callback;
        sendMessage(message);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        super.handleMessage(msg);
        Object obj = msg.obj;
        Objects.requireNonNull(obj, "null cannot be cast to non-null type com.lazyee.klib.typed.VoidCallback /* = () -> kotlin.Unit */");
        ((Function0) TypeIntrinsics.beforeCheckcastToFunctionOfArity(obj, 0)).invoke();
    }
}
