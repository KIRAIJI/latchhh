package com.lazyee.klib.extension;

import android.text.TextUtils;
import android.util.Base64;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.lazyee.klib.util.DateUtils;
import com.lazyee.klib.util.LogUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.GlobalScope;

/* JADX INFO: compiled from: StringExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000`\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0002\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0003*\u00020\u0003\u001a\u0014\u0010\u0006\u001a\u00020\u0003*\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b\u001a7\u0010\u0006\u001a\u00020\t*\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2!\u0010\n\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\t0\u000b\u001a\n\u0010\u000f\u001a\u00020\u0003*\u00020\u0003\u001a\u0014\u0010\u0010\u001a\u00020\u0003*\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b\u001a7\u0010\u0010\u001a\u00020\t*\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2!\u0010\n\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\t0\u000b\u001a\n\u0010\u0011\u001a\u00020\u0003*\u00020\u0003\u001a\f\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u0003\u001a\u000e\u0010\u0014\u001a\u0004\u0018\u00010\u0003*\u0004\u0018\u00010\u0003\u001a\f\u0010\u0015\u001a\u00020\u0016*\u0004\u0018\u00010\u0003\u001a\f\u0010\u0017\u001a\u00020\u0016*\u0004\u0018\u00010\u0003\u001a\f\u0010\u0018\u001a\u00020\u0016*\u0004\u0018\u00010\u0003\u001a\f\u0010\u0019\u001a\u00020\u0016*\u0004\u0018\u00010\u0003\u001a\n\u0010\u001a\u001a\u00020\u0003*\u00020\u0003\u001a\f\u0010\u001b\u001a\u00020\u001c*\u0004\u0018\u00010\u0003\u001a\f\u0010\u001d\u001a\u00020\u001e*\u0004\u0018\u00010\u0003\u001a\f\u0010\u001f\u001a\u00020\b*\u0004\u0018\u00010\u0003\u001a\f\u0010 \u001a\u00020!*\u0004\u0018\u00010\u0003\u001a\u0010\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00030#*\u00020\u0003\u001a\"\u0010$\u001a\u0004\u0018\u00010%*\u0004\u0018\u00010\u00032\b\b\u0002\u0010&\u001a\u00020\u00032\b\b\u0002\u0010'\u001a\u00020(\u001a'\u0010)\u001a\u0004\u0018\u00010!*\u0004\u0018\u00010\u00032\b\b\u0002\u0010&\u001a\u00020\u00032\b\b\u0002\u0010'\u001a\u00020(¢\u0006\u0002\u0010*¨\u0006+"}, d2 = {"uniteBytes", "", "src0", "", "src1", "charToHex", "decodeBase64", "flags", "", "", "block", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", TypedValues.Custom.S_STRING, "decodeURL", "encodeBase64", "encodeURL", "hexToBytes", "", "hidePhone", "isChinaPhoneLegal", "", "isEmailLegal", "isHKPhoneLegal", "isPhoneLegal", "md5", "safeToDouble", "", "safeToFloat", "", "safeToInt", "safeToLong", "", "split2Combo", "", "toDate", "Ljava/util/Date;", "format", "locale", "Ljava/util/Locale;", "toTimeMillis", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Locale;)Ljava/lang/Long;", "library_release"}, k = 2, mv = {1, 4, 2})
public final class StringExtensionsKt {
    public static final String md5(String md5) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(md5, "$this$md5");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
        byte[] bytes2 = messageDigest.digest(bytes);
        Intrinsics.checkNotNullExpressionValue(bytes2, "bytes");
        return ArraysKt.joinToString$default(bytes2, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: com.lazyee.klib.extension.StringExtensionsKt.md5.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }

            public final CharSequence invoke(byte b) {
                String str = String.format("%02X", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(this, *args)");
                return str;
            }
        }, 30, (Object) null);
    }

    public static /* synthetic */ String encodeBase64$default(String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return encodeBase64(str, i);
    }

    public static final String encodeBase64(String encodeBase64, int i) {
        Intrinsics.checkNotNullParameter(encodeBase64, "$this$encodeBase64");
        byte[] bytes = encodeBase64.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
        String strEncodeToString = Base64.encodeToString(bytes, i);
        Intrinsics.checkNotNullExpressionValue(strEncodeToString, "Base64.encodeToString(toByteArray(), flags)");
        return strEncodeToString;
    }

    /* JADX INFO: renamed from: com.lazyee.klib.extension.StringExtensionsKt$encodeBase64$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: StringExtensions.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.lazyee.klib.extension.StringExtensionsKt$encodeBase64$1", f = "StringExtensions.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C00591 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1 $block;
        final /* synthetic */ int $flags;
        final /* synthetic */ String $this_encodeBase64;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00591(String str, Function1 function1, int i, Continuation continuation) {
            super(2, continuation);
            this.$this_encodeBase64 = str;
            this.$block = function1;
            this.$flags = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new C00591(this.$this_encodeBase64, this.$block, this.$flags, completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00591) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.$block.invoke(StringExtensionsKt.encodeBase64(this.$this_encodeBase64, this.$flags));
            long jLongValue = Boxing.boxLong(System.currentTimeMillis() - jCurrentTimeMillis).longValue();
            LogUtils.INSTANCE.i("StringExtensions", "encode to base64 spend time:" + jLongValue);
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ void encodeBase64$default(String str, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        encodeBase64(str, i, function1);
    }

    public static final void encodeBase64(String encodeBase64, int i, Function1<? super String, Unit> block) {
        Intrinsics.checkNotNullParameter(encodeBase64, "$this$encodeBase64");
        Intrinsics.checkNotNullParameter(block, "block");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, null, null, new C00591(encodeBase64, block, i, null), 3, null);
    }

    public static /* synthetic */ String decodeBase64$default(String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return decodeBase64(str, i);
    }

    public static final String decodeBase64(String decodeBase64, int i) {
        Intrinsics.checkNotNullParameter(decodeBase64, "$this$decodeBase64");
        byte[] bArrDecode = Base64.decode(decodeBase64, i);
        Intrinsics.checkNotNullExpressionValue(bArrDecode, "Base64.decode(this, flags)");
        return new String(bArrDecode, Charsets.UTF_8);
    }

    /* JADX INFO: renamed from: com.lazyee.klib.extension.StringExtensionsKt$decodeBase64$1, reason: invalid class name */
    /* JADX INFO: compiled from: StringExtensions.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.lazyee.klib.extension.StringExtensionsKt$decodeBase64$1", f = "StringExtensions.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1 $block;
        final /* synthetic */ int $flags;
        final /* synthetic */ String $this_decodeBase64;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, Function1 function1, int i, Continuation continuation) {
            super(2, continuation);
            this.$this_decodeBase64 = str;
            this.$block = function1;
            this.$flags = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new AnonymousClass1(this.$this_decodeBase64, this.$block, this.$flags, completion);
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
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.$block.invoke(StringExtensionsKt.decodeBase64(this.$this_decodeBase64, this.$flags));
            long jLongValue = Boxing.boxLong(System.currentTimeMillis() - jCurrentTimeMillis).longValue();
            LogUtils.INSTANCE.i("StringExtensions", "decode base64 string spend time:" + jLongValue);
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ void decodeBase64$default(String str, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        decodeBase64(str, i, function1);
    }

    public static final void decodeBase64(String decodeBase64, int i, Function1<? super String, Unit> block) {
        Intrinsics.checkNotNullParameter(decodeBase64, "$this$decodeBase64");
        Intrinsics.checkNotNullParameter(block, "block");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, null, null, new AnonymousClass1(decodeBase64, block, i, null), 3, null);
    }

    public static final String encodeURL(String encodeURL) throws UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(encodeURL, "$this$encodeURL");
        String strEncode = URLEncoder.encode(encodeURL, Charsets.UTF_8.displayName());
        Intrinsics.checkNotNullExpressionValue(strEncode, "URLEncoder.encode(this, …sets.UTF_8.displayName())");
        return strEncode;
    }

    public static final String decodeURL(String decodeURL) throws UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(decodeURL, "$this$decodeURL");
        String strDecode = URLDecoder.decode(decodeURL, Charsets.UTF_8.displayName());
        Intrinsics.checkNotNullExpressionValue(strDecode, "URLDecoder.decode(this, …sets.UTF_8.displayName())");
        return strDecode;
    }

    public static final long safeToLong(String str) {
        if (str != null) {
            try {
                if (TextUtils.isEmpty(str)) {
                    return 0L;
                }
                return Long.parseLong(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }

    public static final int safeToInt(String str) {
        if (str != null) {
            try {
                if (TextUtils.isEmpty(str)) {
                    return 0;
                }
                return Integer.parseInt(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static final double safeToDouble(String str) {
        if (str != null) {
            try {
                if (TextUtils.isEmpty(str)) {
                    return 0.0d;
                }
                return Double.parseDouble(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0.0d;
    }

    public static final float safeToFloat(String str) {
        if (str != null) {
            try {
                if (TextUtils.isEmpty(str)) {
                    return 0.0f;
                }
                return Float.parseFloat(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0.0f;
    }

    public static final String hidePhone(String str) {
        if (str == null) {
            return null;
        }
        return new Regex("(\\d{3})\\d{4}(\\d{4})").replace(str, "$1****$2");
    }

    public static final boolean isPhoneLegal(String str) {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    public static final boolean isChinaPhoneLegal(String str) {
        Pattern patternCompile = Pattern.compile("^(1[3-9])\\d{9}$");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "Pattern.compile(regExp)");
        Intrinsics.checkNotNull(str);
        Matcher matcher = patternCompile.matcher(str);
        Intrinsics.checkNotNullExpressionValue(matcher, "p.matcher(this!!)");
        return matcher.matches();
    }

    public static final boolean isHKPhoneLegal(String str) {
        Pattern patternCompile = Pattern.compile("^(5|6|8|9)\\d{7}$");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "Pattern.compile(regExp)");
        Intrinsics.checkNotNull(str);
        Matcher matcher = patternCompile.matcher(str);
        Intrinsics.checkNotNullExpressionValue(matcher, "p.matcher(this!!)");
        return matcher.matches();
    }

    public static final boolean isEmailLegal(String str) {
        Pattern patternCompile = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "Pattern.compile(regExp)");
        Intrinsics.checkNotNull(str);
        Matcher matcher = patternCompile.matcher(str);
        Intrinsics.checkNotNullExpressionValue(matcher, "p.matcher(this!!)");
        return matcher.matches();
    }

    public static /* synthetic */ Date toDate$default(String str, String str2, Locale locale, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = DateUtils.yyyyMMddHHmmss;
        }
        if ((i & 2) != 0) {
            locale = Locale.CHINA;
            Intrinsics.checkNotNullExpressionValue(locale, "Locale.CHINA");
        }
        return toDate(str, str2, locale);
    }

    public static final Date toDate(String str, String format, Locale locale) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(locale, "locale");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        DateUtils dateUtils = DateUtils.INSTANCE;
        Intrinsics.checkNotNull(str);
        return dateUtils.stringToDate(str, format, locale);
    }

    public static /* synthetic */ Long toTimeMillis$default(String str, String str2, Locale locale, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = DateUtils.yyyyMMddHHmmss;
        }
        if ((i & 2) != 0) {
            locale = Locale.CHINA;
            Intrinsics.checkNotNullExpressionValue(locale, "Locale.CHINA");
        }
        return toTimeMillis(str, str2, locale);
    }

    public static final Long toTimeMillis(String str, String format, Locale locale) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(locale, "locale");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        DateUtils dateUtils = DateUtils.INSTANCE;
        Intrinsics.checkNotNull(str);
        return dateUtils.stringToTimeMillis(str, format, locale);
    }

    public static final List<String> split2Combo(String split2Combo) {
        Object objPrevious;
        Intrinsics.checkNotNullParameter(split2Combo, "$this$split2Combo");
        ArrayList arrayList = new ArrayList();
        int length = split2Combo.length();
        for (int i = 0; i < length; i++) {
            int length2 = split2Combo.length();
            if (length2 >= i) {
                while (true) {
                    if (length2 != i) {
                        String strSubstring = split2Combo.substring(i, length2);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        if (!arrayList.contains(strSubstring)) {
                            ListIterator listIterator = arrayList.listIterator(arrayList.size());
                            while (true) {
                                if (!listIterator.hasPrevious()) {
                                    objPrevious = null;
                                    break;
                                }
                                objPrevious = listIterator.previous();
                                if (((String) objPrevious).length() >= strSubstring.length()) {
                                    break;
                                }
                            }
                            arrayList.add(CollectionsKt.indexOf((List<? extends String>) arrayList, (String) objPrevious) + 1, strSubstring);
                        }
                    }
                    if (length2 != i) {
                        length2--;
                    }
                }
            }
        }
        return arrayList;
    }

    public static final String charToHex(String charToHex) {
        Intrinsics.checkNotNullParameter(charToHex, "$this$charToHex");
        char[] charArray = charToHex.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "(this as java.lang.String).toCharArray()");
        StringBuilder sb = new StringBuilder(charArray.length);
        for (char c : charArray) {
            sb.append(Integer.toHexString(c));
        }
        return sb.toString();
    }

    public static final byte[] hexToBytes(String hexToBytes) {
        Intrinsics.checkNotNullParameter(hexToBytes, "$this$hexToBytes");
        int length = hexToBytes.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            int i3 = i2 + 1;
            String strSubstring = hexToBytes.substring(i2, i3);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            String strSubstring2 = hexToBytes.substring(i3, i3 + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            bArr[i] = uniteBytes(strSubstring, strSubstring2);
        }
        return bArr;
    }

    private static final byte uniteBytes(String str, String str2) {
        Byte bDecode = Byte.decode("0x" + str);
        Intrinsics.checkNotNullExpressionValue(bDecode, "java.lang.Byte.decode(\"0x$src0\")");
        byte bByteValue = (byte) (bDecode.byteValue() << 4);
        Byte bDecode2 = Byte.decode("0x" + str2);
        Intrinsics.checkNotNullExpressionValue(bDecode2, "java.lang.Byte.decode(\"0x$src1\")");
        return (byte) (bByteValue | bDecode2.byteValue());
    }
}
