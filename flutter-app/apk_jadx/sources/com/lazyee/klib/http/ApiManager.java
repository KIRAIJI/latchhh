package com.lazyee.klib.http;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.lazyee.klib.http.ApiManager;
import com.lazyee.klib.http.interceptor.AddCookiesInterceptor;
import com.lazyee.klib.http.interceptor.ApiResultInterceptor;
import com.lazyee.klib.http.interceptor.HttpParamsInterceptor;
import com.lazyee.klib.http.interceptor.ParamsProvider;
import com.lazyee.klib.http.interceptor.ReceivedCookiesInterceptor;
import com.lazyee.klib.util.LogUtils;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/* JADX INFO: compiled from: ApiManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 72\u00020\u0001:\u000267Be\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\fJ\u000e\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\nJ\u0006\u0010\u001c\u001a\u00020\u0019J\u001f\u0010\u001d\u001a\u0002H\u001e\"\u0004\b\u0000\u0010\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u001e0 ¢\u0006\u0002\u0010!J,\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u001e0#\"\u0004\b\u0000\u0010\u001e2\u0006\u0010$\u001a\u00020\u00012\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u0002H\u001e\u0018\u00010&J\b\u0010'\u001a\u00020\u0019H\u0002J\b\u0010(\u001a\u00020)H\u0002J\u0006\u0010*\u001a\u00020\u0015J+\u0010+\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u001e2\b\u0010,\u001a\u0004\u0018\u0001H\u001e2\u000e\u0010%\u001a\n\u0012\u0004\u0012\u0002H\u001e\u0018\u00010&¢\u0006\u0002\u0010-J\u0014\u0010.\u001a\u00020\u00052\n\u0010,\u001a\u0006\u0012\u0002\b\u00030/H\u0002J4\u00100\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u001e2\u0006\u0010$\u001a\u00020\u00012\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u001e022\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u0002H\u001e\u0018\u00010&J/\u00100\u001a\u0004\u0018\u0001H\u001e\"\u0004\b\u0000\u0010\u001e2\u0006\u0010$\u001a\u00020\u00012\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u001e04H\u0086@ø\u0001\u0000¢\u0006\u0002\u00105J4\u00100\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u001e2\u0006\u0010$\u001a\u00020\u00012\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u001e042\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u0002H\u001e\u0018\u00010&R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u00068"}, d2 = {"Lcom/lazyee/klib/http/ApiManager;", "", "baseUrl", "", "isSupportCookie", "", "paramsProvider", "Lcom/lazyee/klib/http/interceptor/ParamsProvider;", "interceptors", "", "Lokhttp3/Interceptor;", "apiResultInterceptors", "Lcom/lazyee/klib/http/interceptor/ApiResultInterceptor;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "x509TrustManager", "Ljavax/net/ssl/X509TrustManager;", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "(Ljava/lang/String;ZLcom/lazyee/klib/http/interceptor/ParamsProvider;Ljava/util/List;Ljava/util/List;Ljavax/net/ssl/SSLSocketFactory;Ljavax/net/ssl/X509TrustManager;Ljavax/net/ssl/HostnameVerifier;)V", "okHttpClient", "Lokhttp3/OkHttpClient;", "retrofit", "Lretrofit2/Retrofit;", "addApiResultInterceptor", "", "interceptor", "addInterceptor", "clearApiResultInterceptor", "create", ExifInterface.GPS_DIRECTION_TRUE, "clazz", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "createHttpObserver", "Lio/reactivex/Observer;", "tag", "callback", "Lcom/lazyee/klib/http/ApiCallback;", "createRetrofit", "getHttpLoggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "getOkHttpClient", "handleHttpResult", "result", "(Ljava/lang/Object;Lcom/lazyee/klib/http/ApiCallback;)V", "isApiResultIntercept", "Lcom/lazyee/klib/http/IApiResult;", "request", "observable", "Lio/reactivex/Observable;", NotificationCompat.CATEGORY_CALL, "Lretrofit2/Call;", "(Ljava/lang/Object;Lretrofit2/Call;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Builder", "Companion", "library_release"}, k = 1, mv = {1, 4, 2})
public final class ApiManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final HashMap<Object, List<HttpTask>> tasks = new HashMap<>();
    private final List<ApiResultInterceptor> apiResultInterceptors;
    private final String baseUrl;
    private final HostnameVerifier hostnameVerifier;
    private final List<Interceptor> interceptors;
    private final boolean isSupportCookie;
    private OkHttpClient okHttpClient;
    private ParamsProvider paramsProvider;
    private Retrofit retrofit;
    private final SSLSocketFactory sslSocketFactory;
    private final X509TrustManager x509TrustManager;

    /* JADX INFO: renamed from: com.lazyee.klib.http.ApiManager$request$2, reason: invalid class name */
    /* JADX INFO: compiled from: ApiManager.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0007H\u0086@"}, d2 = {"request", "", ExifInterface.GPS_DIRECTION_TRUE, "tag", NotificationCompat.CATEGORY_CALL, "Lretrofit2/Call;", "continuation", "Lkotlin/coroutines/Continuation;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.lazyee.klib.http.ApiManager", f = "ApiManager.kt", i = {0, 0, 0}, l = {157}, m = "request", n = {"tag", "task", "body"}, s = {"L$0", "L$1", "L$2"})
    static final class AnonymousClass2 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ApiManager.this.request((Object) null, (Call) null, this);
        }
    }

    private ApiManager(String str, boolean z, ParamsProvider paramsProvider, List<Interceptor> list, List<ApiResultInterceptor> list2, SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager, HostnameVerifier hostnameVerifier) {
        this.baseUrl = str;
        this.isSupportCookie = z;
        this.paramsProvider = paramsProvider;
        this.interceptors = list;
        this.apiResultInterceptors = list2;
        this.sslSocketFactory = sSLSocketFactory;
        this.x509TrustManager = x509TrustManager;
        this.hostnameVerifier = hostnameVerifier;
        createRetrofit();
    }

    public /* synthetic */ ApiManager(String str, boolean z, ParamsProvider paramsProvider, List list, List list2, SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager, HostnameVerifier hostnameVerifier, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, paramsProvider, list, list2, sSLSocketFactory, x509TrustManager, hostnameVerifier);
    }

    /* synthetic */ ApiManager(String str, boolean z, ParamsProvider paramsProvider, List list, List list2, SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager, HostnameVerifier hostnameVerifier, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? (ParamsProvider) null : paramsProvider, list, list2, (i & 32) != 0 ? (SSLSocketFactory) null : sSLSocketFactory, (i & 64) != 0 ? (X509TrustManager) null : x509TrustManager, (i & 128) != 0 ? (HostnameVerifier) null : hostnameVerifier);
    }

    public static /* synthetic */ Observer createHttpObserver$default(ApiManager apiManager, Object obj, ApiCallback apiCallback, int i, Object obj2) {
        if ((i & 2) != 0) {
            apiCallback = (ApiCallback) null;
        }
        return apiManager.createHttpObserver(obj, apiCallback);
    }

    public final <T> Observer<T> createHttpObserver(final Object tag, final ApiCallback<T> callback) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = null;
        return new Observer<T>() { // from class: com.lazyee.klib.http.ApiManager$createHttpObserver$observer$1
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                Intrinsics.checkNotNullParameter(disposable, "disposable");
                objectRef.element = (T) new RxJavaHttpTask(disposable);
                ApiManager.Companion companion = ApiManager.INSTANCE;
                Object obj = tag;
                T t = objectRef.element;
                if (t == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("task");
                }
                companion.addTask(obj, (RxJavaHttpTask) t);
            }

            @Override // io.reactivex.Observer
            public void onNext(T data) {
                ApiManager.Companion companion = ApiManager.INSTANCE;
                Object obj = tag;
                T t = objectRef.element;
                if (t == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("task");
                }
                companion.removeTask(obj, (RxJavaHttpTask) t);
                this.this$0.handleHttpResult(data, callback);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                ApiCallback3 apiCallback3;
                Intrinsics.checkNotNullParameter(e, "e");
                e.printStackTrace();
                ApiManager.Companion companion = ApiManager.INSTANCE;
                Object obj = tag;
                T t = objectRef.element;
                if (t == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("task");
                }
                companion.removeTask(obj, (RxJavaHttpTask) t);
                ApiCallback apiCallback = callback;
                if (!(apiCallback != null ? apiCallback instanceof ApiCallback3 : true) || (apiCallback3 = (ApiCallback3) apiCallback) == null) {
                    return;
                }
                apiCallback3.onRequestFailure(e);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ApiManager.Companion companion = ApiManager.INSTANCE;
                Object obj = tag;
                T t = objectRef.element;
                if (t == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("task");
                }
                companion.removeTask(obj, (RxJavaHttpTask) t);
            }
        };
    }

    public final <T> T create(Class<T> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Retrofit retrofit = this.retrofit;
        if (retrofit == null) {
            Intrinsics.throwUninitializedPropertyAccessException("retrofit");
        }
        return (T) retrofit.create(clazz);
    }

    public static /* synthetic */ void request$default(ApiManager apiManager, Object obj, Observable observable, ApiCallback apiCallback, int i, Object obj2) {
        if ((i & 4) != 0) {
            apiCallback = (ApiCallback) null;
        }
        apiManager.request(obj, observable, apiCallback);
    }

    public final <T> void request(Object tag, Observable<T> observable, ApiCallback<T> callback) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(observable, "observable");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(createHttpObserver(tag, callback));
    }

    public static /* synthetic */ void request$default(ApiManager apiManager, Object obj, Call call, ApiCallback apiCallback, int i, Object obj2) {
        if ((i & 4) != 0) {
            apiCallback = (ApiCallback) null;
        }
        apiManager.request(obj, call, apiCallback);
    }

    public final <T> void request(final Object tag, Call<T> call, final ApiCallback<T> callback) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(call, "call");
        final RetrofitCallHttpTask retrofitCallHttpTask = new RetrofitCallHttpTask(call);
        INSTANCE.addTask(tag, retrofitCallHttpTask);
        call.enqueue(new Callback<T>() { // from class: com.lazyee.klib.http.ApiManager.request.1
            @Override // retrofit2.Callback
            public void onResponse(Call<T> call2, Response<T> response) {
                Intrinsics.checkNotNullParameter(call2, "call");
                Intrinsics.checkNotNullParameter(response, "response");
                ApiManager.INSTANCE.removeTask(tag, retrofitCallHttpTask);
                ApiManager.this.handleHttpResult(response.body(), callback);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<T> call2, Throwable t) {
                ApiCallback3 apiCallback3;
                Intrinsics.checkNotNullParameter(t, "t");
                t.printStackTrace();
                ApiManager.INSTANCE.removeTask(tag, retrofitCallHttpTask);
                ApiCallback apiCallback = callback;
                if (!(apiCallback != null ? apiCallback instanceof ApiCallback3 : true) || (apiCallback3 = (ApiCallback3) apiCallback) == null) {
                    return;
                }
                apiCallback3.onRequestFailure(t);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final <T> java.lang.Object request(java.lang.Object r7, retrofit2.Call<T> r8, kotlin.coroutines.Continuation<? super T> r9) throws java.lang.Throwable {
        /*
            r6 = this;
            boolean r0 = r9 instanceof com.lazyee.klib.http.ApiManager.AnonymousClass2
            if (r0 == 0) goto L14
            r0 = r9
            com.lazyee.klib.http.ApiManager$request$2 r0 = (com.lazyee.klib.http.ApiManager.AnonymousClass2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            com.lazyee.klib.http.ApiManager$request$2 r0 = new com.lazyee.klib.http.ApiManager$request$2
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L41
            if (r2 != r4) goto L39
            java.lang.Object r7 = r0.L$2
            kotlin.jvm.internal.Ref$ObjectRef r7 = (kotlin.jvm.internal.Ref.ObjectRef) r7
            java.lang.Object r8 = r0.L$1
            com.lazyee.klib.http.RetrofitCallHttpTask r8 = (com.lazyee.klib.http.RetrofitCallHttpTask) r8
            java.lang.Object r0 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Exception -> L37
            r2 = r7
            r7 = r0
            goto L83
        L37:
            r7 = move-exception
            goto L96
        L39:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L41:
            kotlin.ResultKt.throwOnFailure(r9)
            com.lazyee.klib.http.RetrofitCallHttpTask r9 = new com.lazyee.klib.http.RetrofitCallHttpTask
            r9.<init>(r8)
            com.lazyee.klib.http.ApiManager$Companion r2 = com.lazyee.klib.http.ApiManager.INSTANCE     // Catch: java.lang.Exception -> L92
            r5 = r9
            com.lazyee.klib.http.HttpTask r5 = (com.lazyee.klib.http.HttpTask) r5     // Catch: java.lang.Exception -> L92
            com.lazyee.klib.http.ApiManager.Companion.access$addTask(r2, r7, r5)     // Catch: java.lang.Exception -> L92
            retrofit2.Response r8 = r8.execute()     // Catch: java.lang.Exception -> L92
            kotlin.jvm.internal.Ref$ObjectRef r2 = new kotlin.jvm.internal.Ref$ObjectRef     // Catch: java.lang.Exception -> L92
            r2.<init>()     // Catch: java.lang.Exception -> L92
            java.lang.Object r8 = r8.body()     // Catch: java.lang.Exception -> L92
            r2.element = r8     // Catch: java.lang.Exception -> L92
            T r8 = r2.element     // Catch: java.lang.Exception -> L92
            boolean r8 = r8 instanceof com.lazyee.klib.http.IApiResult     // Catch: java.lang.Exception -> L92
            if (r8 == 0) goto L82
            kotlinx.coroutines.MainCoroutineDispatcher r8 = kotlinx.coroutines.Dispatchers.getMain()     // Catch: java.lang.Exception -> L92
            kotlin.coroutines.CoroutineContext r8 = (kotlin.coroutines.CoroutineContext) r8     // Catch: java.lang.Exception -> L92
            com.lazyee.klib.http.ApiManager$request$3 r5 = new com.lazyee.klib.http.ApiManager$request$3     // Catch: java.lang.Exception -> L92
            r5.<init>(r2, r3)     // Catch: java.lang.Exception -> L92
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5     // Catch: java.lang.Exception -> L92
            r0.L$0 = r7     // Catch: java.lang.Exception -> L92
            r0.L$1 = r9     // Catch: java.lang.Exception -> L92
            r0.L$2 = r2     // Catch: java.lang.Exception -> L92
            r0.label = r4     // Catch: java.lang.Exception -> L92
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r5, r0)     // Catch: java.lang.Exception -> L92
            if (r8 != r1) goto L82
            return r1
        L82:
            r8 = r9
        L83:
            com.lazyee.klib.http.ApiManager$Companion r9 = com.lazyee.klib.http.ApiManager.INSTANCE     // Catch: java.lang.Exception -> L8e
            r0 = r8
            com.lazyee.klib.http.HttpTask r0 = (com.lazyee.klib.http.HttpTask) r0     // Catch: java.lang.Exception -> L8e
            com.lazyee.klib.http.ApiManager.Companion.access$removeTask(r9, r7, r0)     // Catch: java.lang.Exception -> L8e
            T r7 = r2.element     // Catch: java.lang.Exception -> L8e
            return r7
        L8e:
            r9 = move-exception
            r0 = r7
            r7 = r9
            goto L96
        L92:
            r8 = move-exception
            r0 = r7
            r7 = r8
            r8 = r9
        L96:
            r7.printStackTrace()
            com.lazyee.klib.http.ApiManager$Companion r7 = com.lazyee.klib.http.ApiManager.INSTANCE
            com.lazyee.klib.http.HttpTask r8 = (com.lazyee.klib.http.HttpTask) r8
            com.lazyee.klib.http.ApiManager.Companion.access$removeTask(r7, r0, r8)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lazyee.klib.http.ApiManager.request(java.lang.Object, retrofit2.Call, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: com.lazyee.klib.http.ApiManager$request$3, reason: invalid class name */
    /* JADX INFO: compiled from: ApiManager.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.lazyee.klib.http.ApiManager$request$3", f = "ApiManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.ObjectRef $body;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Ref.ObjectRef objectRef, Continuation continuation) {
            super(2, continuation);
            this.$body = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return ApiManager.this.new AnonymousClass3(this.$body, completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Exception {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                if (ApiManager.this.isApiResultIntercept((IApiResult) this.$body.element)) {
                    throw new Exception("api result intercept success");
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final <T> void handleHttpResult(T result, ApiCallback<T> callback) {
        ApiCallback2 apiCallback2;
        ApiCallback2 apiCallback22;
        if (result == null) {
            if (callback != null) {
                callback.onSuccess(null);
                return;
            }
            return;
        }
        if (!(result instanceof IApiResult)) {
            if (callback != null) {
                callback.onSuccess(result);
                return;
            }
            return;
        }
        IApiResult<?> iApiResult = (IApiResult) result;
        if (isApiResultIntercept(iApiResult)) {
            if (!(callback != null ? callback instanceof ApiCallback2 : true) || (apiCallback22 = (ApiCallback2) callback) == null) {
                return;
            }
            apiCallback22.onFailure(result);
            return;
        }
        if (ApiCode.INSTANCE.isSuccessful(iApiResult.getICode())) {
            if (callback != null) {
                callback.onSuccess(result);
            }
        } else {
            if (!(callback != null ? callback instanceof ApiCallback2 : true) || (apiCallback2 = (ApiCallback2) callback) == null) {
                return;
            }
            apiCallback2.onFailure(result);
        }
    }

    public final OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = this.okHttpClient;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("okHttpClient");
        }
        return okHttpClient;
    }

    private final void createRetrofit() {
        X509TrustManager x509TrustManager;
        OkHttpClient.Builder builderNewBuilder = new OkHttpClient().newBuilder();
        SSLSocketFactory sSLSocketFactory = this.sslSocketFactory;
        if (sSLSocketFactory != null && (x509TrustManager = this.x509TrustManager) != null) {
            builderNewBuilder.sslSocketFactory(sSLSocketFactory, x509TrustManager);
        }
        HostnameVerifier hostnameVerifier = this.hostnameVerifier;
        if (hostnameVerifier != null) {
            builderNewBuilder.hostnameVerifier(hostnameVerifier);
        }
        ParamsProvider paramsProvider = this.paramsProvider;
        if (paramsProvider != null) {
            Intrinsics.checkNotNull(paramsProvider);
            builderNewBuilder.addInterceptor(new HttpParamsInterceptor(paramsProvider));
        }
        if (this.isSupportCookie) {
            builderNewBuilder.addInterceptor(new AddCookiesInterceptor());
            builderNewBuilder.addInterceptor(new ReceivedCookiesInterceptor());
        }
        Iterator<T> it = this.interceptors.iterator();
        while (it.hasNext()) {
            builderNewBuilder.addInterceptor((Interceptor) it.next());
        }
        builderNewBuilder.addInterceptor(getHttpLoggingInterceptor());
        MoshiConverterFactory moshiConverterFactoryCreate = MoshiConverterFactory.create(new Moshi.Builder().add((JsonAdapter.Factory) new KotlinJsonAdapterFactory()).build());
        this.okHttpClient = builderNewBuilder.build();
        Retrofit.Builder builderAddCallAdapterFactory = new Retrofit.Builder().baseUrl(this.baseUrl).addConverterFactory(moshiConverterFactoryCreate).addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        OkHttpClient okHttpClient = this.okHttpClient;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("okHttpClient");
        }
        Retrofit retrofitBuild = builderAddCallAdapterFactory.client(okHttpClient).build();
        Intrinsics.checkNotNullExpressionValue(retrofitBuild, "Retrofit.Builder()\n     …ent)\n            .build()");
        this.retrofit = retrofitBuild;
    }

    private final HttpLoggingInterceptor getHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: com.lazyee.klib.http.ApiManager.getHttpLoggingInterceptor.1
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public final void log(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                LogUtils.INSTANCE.d("[ApiManager]", it);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isApiResultIntercept(IApiResult<?> result) {
        Iterator<ApiResultInterceptor> it = this.apiResultInterceptors.iterator();
        while (it.hasNext()) {
            if (it.next().intercept(result)) {
                return true;
            }
        }
        return false;
    }

    public final void addInterceptor(Interceptor interceptor) {
        Intrinsics.checkNotNullParameter(interceptor, "interceptor");
        this.interceptors.add(interceptor);
    }

    public final void addApiResultInterceptor(ApiResultInterceptor interceptor) {
        Intrinsics.checkNotNullParameter(interceptor, "interceptor");
        this.apiResultInterceptors.add(interceptor);
    }

    public final void clearApiResultInterceptor() {
        this.apiResultInterceptors.clear();
    }

    /* JADX INFO: compiled from: ApiManager.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0006H\u0002J\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0001J\u0018\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0006H\u0002R6\u0010\u0003\u001a*\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004j\u0014\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/lazyee/klib/http/ApiManager$Companion;", "", "()V", "tasks", "Ljava/util/HashMap;", "", "Lcom/lazyee/klib/http/HttpTask;", "Lkotlin/collections/HashMap;", "addTask", "", "tag", "task", "cancel", "removeTask", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void addTask(Object tag, HttpTask task) {
            if (ApiManager.tasks.containsKey(tag)) {
                Object obj = ApiManager.tasks.get(tag);
                Intrinsics.checkNotNull(obj);
                ((List) obj).add(task);
            } else {
                ApiManager.tasks.put(tag, new ArrayList());
                Object obj2 = ApiManager.tasks.get(tag);
                Intrinsics.checkNotNull(obj2);
                ((List) obj2).add(task);
            }
        }

        public final void cancel(Object tag) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            if (((List) ApiManager.tasks.get(tag)) != null) {
                Object obj = ApiManager.tasks.get(tag);
                Intrinsics.checkNotNull(obj);
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    HttpTask httpTask = (HttpTask) it.next();
                    if (!httpTask.isInvalid()) {
                        httpTask.cancelTask();
                    }
                    it.remove();
                }
                Object obj2 = ApiManager.tasks.get(tag);
                Intrinsics.checkNotNull(obj2);
                if (((List) obj2).isEmpty()) {
                    ApiManager.tasks.remove(tag);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void removeTask(Object tag, HttpTask task) {
            List list = (List) ApiManager.tasks.get(tag);
            if (list != null) {
                list.remove(task);
            }
        }
    }

    /* JADX INFO: compiled from: ApiManager.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0005J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u000fJ\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\rJ\u0016\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\u0007J\u000e\u0010 \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u0013R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/lazyee/klib/http/ApiManager$Builder;", "", "()V", "apiResultInterceptors", "", "Lcom/lazyee/klib/http/interceptor/ApiResultInterceptor;", "baseUrl", "", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "interceptors", "Lokhttp3/Interceptor;", "isSupportCookie", "", "paramsProvider", "Lcom/lazyee/klib/http/interceptor/ParamsProvider;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "x509TrustManager", "Ljavax/net/ssl/X509TrustManager;", "addApiResultInterceptor", "interceptor", "addInterceptor", "build", "Lcom/lazyee/klib/http/ApiManager;", "setHostnameVerifier", "setParamsProvider", "provider", "setSSLSocketFactory", "setSupportCookie", "isSupport", "cookieKeyName", "setX509TrustManager", "trustManager", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Builder {
        private HostnameVerifier hostnameVerifier;
        private boolean isSupportCookie;
        private ParamsProvider paramsProvider;
        private SSLSocketFactory sslSocketFactory;
        private X509TrustManager x509TrustManager;
        private String baseUrl = "";
        private final List<Interceptor> interceptors = new ArrayList();
        private final List<ApiResultInterceptor> apiResultInterceptors = new ArrayList();

        public final Builder setParamsProvider(ParamsProvider provider) {
            Intrinsics.checkNotNullParameter(provider, "provider");
            this.paramsProvider = provider;
            return this;
        }

        public final Builder addInterceptor(Interceptor interceptor) {
            Intrinsics.checkNotNullParameter(interceptor, "interceptor");
            this.interceptors.add(interceptor);
            return this;
        }

        public final Builder addApiResultInterceptor(ApiResultInterceptor interceptor) {
            Intrinsics.checkNotNullParameter(interceptor, "interceptor");
            this.apiResultInterceptors.add(interceptor);
            return this;
        }

        public final Builder baseUrl(String baseUrl) {
            Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
            this.baseUrl = baseUrl;
            return this;
        }

        public final Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            Intrinsics.checkNotNullParameter(hostnameVerifier, "hostnameVerifier");
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public final Builder setX509TrustManager(X509TrustManager trustManager) {
            Intrinsics.checkNotNullParameter(trustManager, "trustManager");
            this.x509TrustManager = trustManager;
            return this;
        }

        public final Builder setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            Intrinsics.checkNotNullParameter(sslSocketFactory, "sslSocketFactory");
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public final Builder setSupportCookie(boolean isSupport) {
            this.isSupportCookie = isSupport;
            return this;
        }

        public final Builder setSupportCookie(boolean isSupport, String cookieKeyName) {
            Intrinsics.checkNotNullParameter(cookieKeyName, "cookieKeyName");
            this.isSupportCookie = isSupport;
            AddCookiesInterceptor.INSTANCE.setCookieKeyName(cookieKeyName);
            return this;
        }

        public final ApiManager build() {
            String str = this.baseUrl;
            boolean z = this.isSupportCookie;
            ParamsProvider paramsProvider = this.paramsProvider;
            List<Interceptor> list = this.interceptors;
            List<ApiResultInterceptor> list2 = this.apiResultInterceptors;
            HostnameVerifier hostnameVerifier = this.hostnameVerifier;
            if (hostnameVerifier == null) {
                hostnameVerifier = ApiManagerKt.defaultHostnameVerifier;
            }
            HostnameVerifier hostnameVerifier2 = hostnameVerifier;
            SSLSocketFactory sSLSocketFactory = this.sslSocketFactory;
            if (sSLSocketFactory == null) {
                sSLSocketFactory = ApiManagerKt.defaultSSLSocketFactory;
            }
            return new ApiManager(str, z, paramsProvider, list, list2, sSLSocketFactory, this.x509TrustManager, hostnameVerifier2, null);
        }
    }
}
