package com.chad.library.adapter.base;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.chad.library.adapter.base.animation.SlideInBottomAnimation;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.chad.library.adapter.base.animation.SlideInRightAnimation;
import com.chad.library.adapter.base.diff.BrvahAsyncDiffer;
import com.chad.library.adapter.base.diff.BrvahAsyncDifferConfig;
import com.chad.library.adapter.base.diff.BrvahListUpdateCallback;
import com.chad.library.adapter.base.listener.GridSpanSizeLookup;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.BaseUpFetchModule;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.util.AdapterUtilsKt;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseQuickAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u008e\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 ï\u0001*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004:\u0004î\u0001ï\u0001B#\b\u0007\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0006\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020qH\u0002J\u0014\u0010r\u001a\u00020o2\f\b\u0001\u0010s\u001a\u00020t\"\u00020\u0006J\u0014\u0010u\u001a\u00020o2\f\b\u0001\u0010s\u001a\u00020t\"\u00020\u0006J\u0017\u0010v\u001a\u00020o2\b\b\u0001\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010wJ\u001f\u0010v\u001a\u00020o2\b\b\u0001\u0010x\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010yJ \u0010v\u001a\u00020o2\b\b\u0001\u0010x\u001a\u00020\u00062\f\u0010z\u001a\b\u0012\u0004\u0012\u00028\u00000{H\u0016J\u0018\u0010v\u001a\u00020o2\u000e\b\u0001\u0010z\u001a\b\u0012\u0004\u0012\u00028\u00000{H\u0016J%\u0010|\u001a\u00020\u00062\u0006\u0010}\u001a\u00020~2\b\b\u0002\u0010\u007f\u001a\u00020\u00062\t\b\u0002\u0010\u0080\u0001\u001a\u00020\u0006H\u0007J&\u0010\u0081\u0001\u001a\u00020\u00062\u0006\u0010}\u001a\u00020~2\b\b\u0002\u0010\u007f\u001a\u00020\u00062\t\b\u0002\u0010\u0080\u0001\u001a\u00020\u0006H\u0007J!\u0010\u0082\u0001\u001a\u00020o2\u0007\u0010\u0083\u0001\u001a\u00028\u00012\u0007\u0010\u0084\u0001\u001a\u00020\u0006H\u0014¢\u0006\u0003\u0010\u0085\u0001J\t\u0010\u0086\u0001\u001a\u00020oH\u0002J\u0012\u0010\u0087\u0001\u001a\u00020o2\u0007\u0010\u0088\u0001\u001a\u00020\u0006H\u0004J \u0010\u0089\u0001\u001a\u00020o2\u0006\u0010p\u001a\u00028\u00012\u0007\u0010\u008a\u0001\u001a\u00028\u0000H$¢\u0006\u0003\u0010\u008b\u0001J1\u0010\u0089\u0001\u001a\u00020o2\u0006\u0010p\u001a\u00028\u00012\u0007\u0010\u008a\u0001\u001a\u00028\u00002\u000f\u0010\u008c\u0001\u001a\n\u0012\u0005\u0012\u00030\u008e\u00010\u008d\u0001H\u0014¢\u0006\u0003\u0010\u008f\u0001J'\u0010\u0090\u0001\u001a\u0004\u0018\u00018\u00012\f\u0010\u0091\u0001\u001a\u0007\u0012\u0002\b\u00030\u0092\u00012\u0006\u0010}\u001a\u00020~H\u0002¢\u0006\u0003\u0010\u0093\u0001J\u0017\u0010\u0094\u0001\u001a\u00028\u00012\u0006\u0010}\u001a\u00020~H\u0014¢\u0006\u0003\u0010\u0095\u0001J#\u0010\u0094\u0001\u001a\u00028\u00012\b\u0010\u0096\u0001\u001a\u00030\u0097\u00012\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0014¢\u0006\u0003\u0010\u0098\u0001J\r\u0010\u0099\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0018J\r\u0010\u009a\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0018J\t\u0010\u009b\u0001\u001a\u00020\u0006H\u0014J\u0011\u0010\u009c\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020\u0006H\u0014J\u000f\u0010\u009d\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000OH\u0007J\r\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000OJ\u001e\u0010\u009f\u0001\u001a\t\u0012\u0002\b\u0003\u0018\u00010\u0092\u00012\f\u0010\u0091\u0001\u001a\u0007\u0012\u0002\b\u00030\u0092\u0001H\u0002J\u0019\u0010 \u0001\u001a\u00028\u00002\b\b\u0001\u0010x\u001a\u00020\u0006H\u0016¢\u0006\u0003\u0010¡\u0001J\t\u0010¢\u0001\u001a\u00020\u0006H\u0016J\u0012\u0010£\u0001\u001a\u00030¤\u00012\u0006\u0010x\u001a\u00020\u0006H\u0016J\u001b\u0010¥\u0001\u001a\u0004\u0018\u00018\u00002\b\b\u0001\u0010x\u001a\u00020\u0006H\u0016¢\u0006\u0003\u0010¡\u0001J\u001a\u0010¦\u0001\u001a\u00020\u00062\t\u0010\u008a\u0001\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0003\u0010§\u0001J\u0011\u0010¨\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020\u0006H\u0016J\t\u0010©\u0001\u001a\u0004\u0018\u00010ZJ\t\u0010ª\u0001\u001a\u0004\u0018\u00010\\J\t\u0010«\u0001\u001a\u0004\u0018\u00010^J\t\u0010¬\u0001\u001a\u0004\u0018\u00010`J\u001c\u0010\u00ad\u0001\u001a\u0004\u0018\u00010~2\u0006\u0010x\u001a\u00020\u00062\t\b\u0001\u0010®\u0001\u001a\u00020\u0006J\u0007\u0010¯\u0001\u001a\u00020\u0012J\u0007\u0010°\u0001\u001a\u00020\u0012J\u0007\u0010±\u0001\u001a\u00020\u0012J\u0012\u0010²\u0001\u001a\u00020\u00122\u0007\u0010³\u0001\u001a\u00020\u0006H\u0014J\u0011\u0010´\u0001\u001a\u00020o2\u0006\u0010e\u001a\u00020fH\u0016J\u001f\u0010µ\u0001\u001a\u00020o2\u0006\u0010p\u001a\u00028\u00012\u0006\u0010x\u001a\u00020\u0006H\u0016¢\u0006\u0003\u0010\u0085\u0001J/\u0010µ\u0001\u001a\u00020o2\u0006\u0010p\u001a\u00028\u00012\u0006\u0010x\u001a\u00020\u00062\u000e\u0010\u008c\u0001\u001a\t\u0012\u0005\u0012\u00030\u008e\u00010\bH\u0016¢\u0006\u0003\u0010¶\u0001J\"\u0010·\u0001\u001a\u00028\u00012\b\u0010\u0096\u0001\u001a\u00030\u0097\u00012\u0007\u0010\u0084\u0001\u001a\u00020\u0006H\u0014¢\u0006\u0003\u0010\u0098\u0001J\"\u0010¸\u0001\u001a\u00028\u00012\b\u0010\u0096\u0001\u001a\u00030\u0097\u00012\u0007\u0010\u0084\u0001\u001a\u00020\u0006H\u0016¢\u0006\u0003\u0010\u0098\u0001J\u0011\u0010¹\u0001\u001a\u00020o2\u0006\u0010e\u001a\u00020fH\u0016J!\u0010º\u0001\u001a\u00020o2\u0007\u0010\u0083\u0001\u001a\u00028\u00012\u0007\u0010\u0084\u0001\u001a\u00020\u0006H\u0014¢\u0006\u0003\u0010\u0085\u0001J\u0017\u0010»\u0001\u001a\u00020o2\u0006\u0010p\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¼\u0001J\u0016\u0010½\u0001\u001a\u00020o2\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010wJ\u0013\u0010½\u0001\u001a\u00020o2\b\b\u0001\u0010x\u001a\u00020\u0006H\u0017J\u0007\u0010¾\u0001\u001a\u00020oJ\u0007\u0010¿\u0001\u001a\u00020oJ\u0013\u0010À\u0001\u001a\u00020o2\b\b\u0001\u0010x\u001a\u00020\u0006H\u0016J\u0007\u0010Á\u0001\u001a\u00020oJ\u0010\u0010Â\u0001\u001a\u00020o2\u0007\u0010Ã\u0001\u001a\u00020~J\u0010\u0010Ä\u0001\u001a\u00020o2\u0007\u0010Å\u0001\u001a\u00020~J\u0017\u0010Æ\u0001\u001a\u00020o2\f\u0010z\u001a\b\u0012\u0004\u0012\u00028\u00000{H\u0017J\u0011\u0010Ç\u0001\u001a\u00020o2\b\u0010È\u0001\u001a\u00030É\u0001J \u0010Ê\u0001\u001a\u00020o2\b\b\u0001\u0010\u007f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010yJ\u0017\u0010Ë\u0001\u001a\u00020o2\u000e\u0010Ì\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000Í\u0001J\u0017\u0010Î\u0001\u001a\u00020o2\u000e\u0010Ï\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000Ð\u0001J$\u0010Ñ\u0001\u001a\u00020o2\n\b\u0001\u0010Ò\u0001\u001a\u00030Ó\u00012\r\u0010Ô\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J(\u0010Ñ\u0001\u001a\u00020o2\u000f\u0010Ô\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b2\f\b\u0002\u0010Õ\u0001\u001a\u0005\u0018\u00010Ö\u0001H\u0017J\u0010\u0010×\u0001\u001a\u00020o2\u0007\u0010Ø\u0001\u001a\u00020~J\u000f\u0010×\u0001\u001a\u00020o2\u0006\u0010\u0005\u001a\u00020\u0006J&\u0010Ù\u0001\u001a\u00020\u00062\u0006\u0010}\u001a\u00020~2\b\b\u0002\u0010\u007f\u001a\u00020\u00062\t\b\u0002\u0010\u0080\u0001\u001a\u00020\u0006H\u0007J\u0011\u0010Ú\u0001\u001a\u00020o2\u0006\u0010p\u001a\u00020qH\u0014J\u0012\u0010Û\u0001\u001a\u00020o2\t\u0010Ü\u0001\u001a\u0004\u0018\u00010bJ&\u0010Ý\u0001\u001a\u00020\u00062\u0006\u0010}\u001a\u00020~2\b\b\u0002\u0010\u007f\u001a\u00020\u00062\t\b\u0002\u0010\u0080\u0001\u001a\u00020\u0006H\u0007J\u001a\u0010Þ\u0001\u001a\u00020o2\u000f\u0010Ô\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010{H\u0016J\u0019\u0010ß\u0001\u001a\u00020o2\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\bH\u0017J\u001a\u0010à\u0001\u001a\u00020o2\u000f\u0010Ô\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\bH\u0016J\u001a\u0010á\u0001\u001a\u00020o2\u0007\u0010â\u0001\u001a\u00020~2\u0006\u0010x\u001a\u00020\u0006H\u0014J\u0012\u0010ã\u0001\u001a\u00020o2\t\u0010ä\u0001\u001a\u0004\u0018\u00010ZJ\u001a\u0010å\u0001\u001a\u00020\u00122\u0007\u0010â\u0001\u001a\u00020~2\u0006\u0010x\u001a\u00020\u0006H\u0014J\u0012\u0010æ\u0001\u001a\u00020o2\t\u0010ä\u0001\u001a\u0004\u0018\u00010\\J\u001a\u0010ç\u0001\u001a\u00020o2\u0007\u0010â\u0001\u001a\u00020~2\u0006\u0010x\u001a\u00020\u0006H\u0014J\u0012\u0010è\u0001\u001a\u00020o2\t\u0010ä\u0001\u001a\u0004\u0018\u00010^J\u001a\u0010é\u0001\u001a\u00020\u00122\u0007\u0010â\u0001\u001a\u00020~2\u0006\u0010x\u001a\u00020\u0006H\u0014J\u0012\u0010ê\u0001\u001a\u00020o2\t\u0010ä\u0001\u001a\u0004\u0018\u00010`J\u001b\u0010ë\u0001\u001a\u00020o2\b\u0010ì\u0001\u001a\u00030í\u00012\u0006\u0010\u007f\u001a\u00020\u0006H\u0014R(\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR0\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\b@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020$8F¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0013\u0010'\u001a\u0004\u0018\u00010(8F¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0013\u0010+\u001a\u0004\u0018\u00010,8F¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0011\u0010/\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b0\u00101R\u001a\u00102\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0014\"\u0004\b4\u0010\u0016R\u0011\u00105\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b6\u00101R\u001a\u00107\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u0014\"\u0004\b9\u0010\u0016R\u0013\u0010:\u001a\u0004\u0018\u00010,8F¢\u0006\u0006\u001a\u0004\b;\u0010.R\u0011\u0010<\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b=\u00101R\u001a\u0010>\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u0014\"\u0004\b@\u0010\u0016R\u0011\u0010A\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\bB\u00101R\u001a\u0010C\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010\u0014\"\u0004\bE\u0010\u0016R\u001a\u0010F\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010\u0014\"\u0004\bG\u0010\u0016R\u001a\u0010H\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\u0014\"\u0004\bI\u0010\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010J\u001a\u00020K8F¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0016\u0010N\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010OX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010P\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020(X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020,X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020,X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010U\u001a\u0004\u0018\u00010KX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010M\"\u0004\bW\u0010XR\u0010\u0010Y\u001a\u0004\u0018\u00010ZX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010[\u001a\u0004\u0018\u00010\\X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010]\u001a\u0004\u0018\u00010^X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010_\u001a\u0004\u0018\u00010`X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010a\u001a\u0004\u0018\u00010bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010c\u001a\u0004\u0018\u00010dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010e\u001a\u00020f8F¢\u0006\u0006\u001a\u0004\bg\u0010hR\"\u0010i\u001a\u0004\u0018\u00010f2\b\u0010\u001e\u001a\u0004\u0018\u00010f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\bj\u0010hR\u0011\u0010k\u001a\u00020d8F¢\u0006\u0006\u001a\u0004\bl\u0010m¨\u0006ð\u0001"}, d2 = {"Lcom/chad/library/adapter/base/BaseQuickAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "VH", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "layoutResId", "", "data", "", "(ILjava/util/List;)V", "value", "Lcom/chad/library/adapter/base/animation/BaseAnimation;", "adapterAnimation", "getAdapterAnimation", "()Lcom/chad/library/adapter/base/animation/BaseAnimation;", "setAdapterAnimation", "(Lcom/chad/library/adapter/base/animation/BaseAnimation;)V", "animationEnable", "", "getAnimationEnable", "()Z", "setAnimationEnable", "(Z)V", "childClickViewIds", "Ljava/util/LinkedHashSet;", "childLongClickViewIds", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "<set-?>", "getData", "()Ljava/util/List;", "setData$com_github_CymChad_brvah", "(Ljava/util/List;)V", "draggableModule", "Lcom/chad/library/adapter/base/module/BaseDraggableModule;", "getDraggableModule", "()Lcom/chad/library/adapter/base/module/BaseDraggableModule;", "emptyLayout", "Landroid/widget/FrameLayout;", "getEmptyLayout", "()Landroid/widget/FrameLayout;", "footerLayout", "Landroid/widget/LinearLayout;", "getFooterLayout", "()Landroid/widget/LinearLayout;", "footerLayoutCount", "getFooterLayoutCount", "()I", "footerViewAsFlow", "getFooterViewAsFlow", "setFooterViewAsFlow", "footerViewPosition", "getFooterViewPosition", "footerWithEmptyEnable", "getFooterWithEmptyEnable", "setFooterWithEmptyEnable", "headerLayout", "getHeaderLayout", "headerLayoutCount", "getHeaderLayoutCount", "headerViewAsFlow", "getHeaderViewAsFlow", "setHeaderViewAsFlow", "headerViewPosition", "getHeaderViewPosition", "headerWithEmptyEnable", "getHeaderWithEmptyEnable", "setHeaderWithEmptyEnable", "isAnimationFirstOnly", "setAnimationFirstOnly", "isUseEmpty", "setUseEmpty", "loadMoreModule", "Lcom/chad/library/adapter/base/module/BaseLoadMoreModule;", "getLoadMoreModule", "()Lcom/chad/library/adapter/base/module/BaseLoadMoreModule;", "mDiffHelper", "Lcom/chad/library/adapter/base/diff/BrvahAsyncDiffer;", "mDraggableModule", "mEmptyLayout", "mFooterLayout", "mHeaderLayout", "mLastPosition", "mLoadMoreModule", "getMLoadMoreModule$com_github_CymChad_brvah", "setMLoadMoreModule$com_github_CymChad_brvah", "(Lcom/chad/library/adapter/base/module/BaseLoadMoreModule;)V", "mOnItemChildClickListener", "Lcom/chad/library/adapter/base/listener/OnItemChildClickListener;", "mOnItemChildLongClickListener", "Lcom/chad/library/adapter/base/listener/OnItemChildLongClickListener;", "mOnItemClickListener", "Lcom/chad/library/adapter/base/listener/OnItemClickListener;", "mOnItemLongClickListener", "Lcom/chad/library/adapter/base/listener/OnItemLongClickListener;", "mSpanSizeLookup", "Lcom/chad/library/adapter/base/listener/GridSpanSizeLookup;", "mUpFetchModule", "Lcom/chad/library/adapter/base/module/BaseUpFetchModule;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "recyclerViewOrNull", "getRecyclerViewOrNull", "upFetchModule", "getUpFetchModule", "()Lcom/chad/library/adapter/base/module/BaseUpFetchModule;", "addAnimation", "", "holder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "addChildClickViewIds", "viewIds", "", "addChildLongClickViewIds", "addData", "(Ljava/lang/Object;)V", "position", "(ILjava/lang/Object;)V", "newData", "", "addFooterView", "view", "Landroid/view/View;", "index", "orientation", "addHeaderView", "bindViewClickListener", "viewHolder", "viewType", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;I)V", "checkModule", "compatibilityDataSizeChanged", "size", "convert", "item", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;Ljava/lang/Object;)V", "payloads", "", "", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;Ljava/lang/Object;Ljava/util/List;)V", "createBaseGenericKInstance", "z", "Ljava/lang/Class;", "(Ljava/lang/Class;Landroid/view/View;)Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "createBaseViewHolder", "(Landroid/view/View;)Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "parent", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;I)Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "getChildClickViewIds", "getChildLongClickViewIds", "getDefItemCount", "getDefItemViewType", "getDiffHelper", "getDiffer", "getInstancedGenericKClass", "getItem", "(I)Ljava/lang/Object;", "getItemCount", "getItemId", "", "getItemOrNull", "getItemPosition", "(Ljava/lang/Object;)I", "getItemViewType", "getOnItemChildClickListener", "getOnItemChildLongClickListener", "getOnItemClickListener", "getOnItemLongClickListener", "getViewByPosition", "viewId", "hasEmptyView", "hasFooterLayout", "hasHeaderLayout", "isFixedViewType", "type", "onAttachedToRecyclerView", "onBindViewHolder", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;ILjava/util/List;)V", "onCreateDefViewHolder", "onCreateViewHolder", "onDetachedFromRecyclerView", "onItemViewHolderCreated", "onViewAttachedToWindow", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;)V", "remove", "removeAllFooterView", "removeAllHeaderView", "removeAt", "removeEmptyView", "removeFooterView", "footer", "removeHeaderView", "header", "replaceData", "setAnimationWithDefault", "animationType", "Lcom/chad/library/adapter/base/BaseQuickAdapter$AnimationType;", "setData", "setDiffCallback", "diffCallback", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "setDiffConfig", "config", "Lcom/chad/library/adapter/base/diff/BrvahAsyncDifferConfig;", "setDiffNewData", "diffResult", "Landroidx/recyclerview/widget/DiffUtil$DiffResult;", "list", "commitCallback", "Ljava/lang/Runnable;", "setEmptyView", "emptyView", "setFooterView", "setFullSpan", "setGridSpanSizeLookup", "spanSizeLookup", "setHeaderView", "setList", "setNewData", "setNewInstance", "setOnItemChildClick", "v", "setOnItemChildClickListener", "listener", "setOnItemChildLongClick", "setOnItemChildLongClickListener", "setOnItemClick", "setOnItemClickListener", "setOnItemLongClick", "setOnItemLongClickListener", "startAnim", "anim", "Landroid/animation/Animator;", "AnimationType", "Companion", "com.github.CymChad.brvah"}, k = 1, mv = {1, 6, 0}, xi = 48)
public abstract class BaseQuickAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    public static final int EMPTY_VIEW = 268436821;
    public static final int FOOTER_VIEW = 268436275;
    public static final int HEADER_VIEW = 268435729;
    public static final int LOAD_MORE_VIEW = 268436002;
    private BaseAnimation adapterAnimation;
    private boolean animationEnable;
    private final LinkedHashSet<Integer> childClickViewIds;
    private final LinkedHashSet<Integer> childLongClickViewIds;
    private List<T> data;
    private boolean footerViewAsFlow;
    private boolean footerWithEmptyEnable;
    private boolean headerViewAsFlow;
    private boolean headerWithEmptyEnable;
    private boolean isAnimationFirstOnly;
    private boolean isUseEmpty;
    private final int layoutResId;
    private BrvahAsyncDiffer<T> mDiffHelper;
    private BaseDraggableModule mDraggableModule;
    private FrameLayout mEmptyLayout;
    private LinearLayout mFooterLayout;
    private LinearLayout mHeaderLayout;
    private int mLastPosition;
    private BaseLoadMoreModule mLoadMoreModule;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private GridSpanSizeLookup mSpanSizeLookup;
    private BaseUpFetchModule mUpFetchModule;
    private RecyclerView recyclerViewOrNull;

    /* JADX INFO: compiled from: BaseQuickAdapter.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lcom/chad/library/adapter/base/BaseQuickAdapter$AnimationType;", "", "(Ljava/lang/String;I)V", "AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "com.github.CymChad.brvah"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public enum AnimationType {
        AlphaIn,
        ScaleIn,
        SlideInBottom,
        SlideInLeft,
        SlideInRight
    }

    /* JADX INFO: compiled from: BaseQuickAdapter.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnimationType.values().length];
            iArr[AnimationType.AlphaIn.ordinal()] = 1;
            iArr[AnimationType.ScaleIn.ordinal()] = 2;
            iArr[AnimationType.SlideInBottom.ordinal()] = 3;
            iArr[AnimationType.SlideInLeft.ordinal()] = 4;
            iArr[AnimationType.SlideInRight.ordinal()] = 5;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BaseQuickAdapter(int i) {
        this(i, null, 2, 0 == true ? 1 : 0);
    }

    public final int addFooterView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        return addFooterView$default(this, view, 0, 0, 6, null);
    }

    public final int addFooterView(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "view");
        return addFooterView$default(this, view, i, 0, 4, null);
    }

    public final int addHeaderView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        return addHeaderView$default(this, view, 0, 0, 6, null);
    }

    public final int addHeaderView(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "view");
        return addHeaderView$default(this, view, i, 0, 4, null);
    }

    protected abstract void convert(VH holder, T item);

    protected void convert(VH holder, T item, List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return position;
    }

    protected boolean isFixedViewType(int type) {
        return type == 268436821 || type == 268435729 || type == 268436275 || type == 268436002;
    }

    protected void onItemViewHolderCreated(VH viewHolder, int viewType) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
    }

    public final void setDiffNewData(List<T> list) {
        setDiffNewData$default(this, list, null, 2, null);
    }

    public final int setFooterView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        return setFooterView$default(this, view, 0, 0, 6, null);
    }

    public final int setFooterView(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "view");
        return setFooterView$default(this, view, i, 0, 4, null);
    }

    public final int setHeaderView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        return setHeaderView$default(this, view, 0, 0, 6, null);
    }

    public final int setHeaderView(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "view");
        return setHeaderView$default(this, view, i, 0, 4, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        onBindViewHolder((BaseViewHolder) viewHolder, i, (List<Object>) list);
    }

    public /* synthetic */ BaseQuickAdapter(int i, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : list);
    }

    public BaseQuickAdapter(int i, ArrayList arrayList) {
        this.layoutResId = i;
        this.data = arrayList == null ? new ArrayList() : arrayList;
        this.isUseEmpty = true;
        this.isAnimationFirstOnly = true;
        this.mLastPosition = -1;
        checkModule();
        this.childClickViewIds = new LinkedHashSet<>();
        this.childLongClickViewIds = new LinkedHashSet<>();
    }

    public final List<T> getData() {
        return this.data;
    }

    public final void setData$com_github_CymChad_brvah(List<T> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.data = list;
    }

    public final boolean getHeaderWithEmptyEnable() {
        return this.headerWithEmptyEnable;
    }

    public final void setHeaderWithEmptyEnable(boolean z) {
        this.headerWithEmptyEnable = z;
    }

    public final boolean getFooterWithEmptyEnable() {
        return this.footerWithEmptyEnable;
    }

    public final void setFooterWithEmptyEnable(boolean z) {
        this.footerWithEmptyEnable = z;
    }

    /* JADX INFO: renamed from: isUseEmpty, reason: from getter */
    public final boolean getIsUseEmpty() {
        return this.isUseEmpty;
    }

    public final void setUseEmpty(boolean z) {
        this.isUseEmpty = z;
    }

    public final boolean getHeaderViewAsFlow() {
        return this.headerViewAsFlow;
    }

    public final void setHeaderViewAsFlow(boolean z) {
        this.headerViewAsFlow = z;
    }

    public final boolean getFooterViewAsFlow() {
        return this.footerViewAsFlow;
    }

    public final void setFooterViewAsFlow(boolean z) {
        this.footerViewAsFlow = z;
    }

    public final boolean getAnimationEnable() {
        return this.animationEnable;
    }

    public final void setAnimationEnable(boolean z) {
        this.animationEnable = z;
    }

    /* JADX INFO: renamed from: isAnimationFirstOnly, reason: from getter */
    public final boolean getIsAnimationFirstOnly() {
        return this.isAnimationFirstOnly;
    }

    public final void setAnimationFirstOnly(boolean z) {
        this.isAnimationFirstOnly = z;
    }

    public final BaseAnimation getAdapterAnimation() {
        return this.adapterAnimation;
    }

    public final void setAdapterAnimation(BaseAnimation baseAnimation) {
        this.animationEnable = true;
        this.adapterAnimation = baseAnimation;
    }

    public final BaseLoadMoreModule getLoadMoreModule() {
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule == null) {
            throw new IllegalStateException("Please first implements LoadMoreModule".toString());
        }
        Intrinsics.checkNotNull(baseLoadMoreModule);
        return baseLoadMoreModule;
    }

    public final BaseUpFetchModule getUpFetchModule() {
        BaseUpFetchModule baseUpFetchModule = this.mUpFetchModule;
        if (baseUpFetchModule == null) {
            throw new IllegalStateException("Please first implements UpFetchModule".toString());
        }
        Intrinsics.checkNotNull(baseUpFetchModule);
        return baseUpFetchModule;
    }

    public final BaseDraggableModule getDraggableModule() {
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule == null) {
            throw new IllegalStateException("Please first implements DraggableModule".toString());
        }
        Intrinsics.checkNotNull(baseDraggableModule);
        return baseDraggableModule;
    }

    /* JADX INFO: renamed from: getMLoadMoreModule$com_github_CymChad_brvah, reason: from getter */
    public final BaseLoadMoreModule getMLoadMoreModule() {
        return this.mLoadMoreModule;
    }

    public final void setMLoadMoreModule$com_github_CymChad_brvah(BaseLoadMoreModule baseLoadMoreModule) {
        this.mLoadMoreModule = baseLoadMoreModule;
    }

    public final RecyclerView getRecyclerViewOrNull() {
        return this.recyclerViewOrNull;
    }

    public final RecyclerView getRecyclerView() {
        RecyclerView recyclerView = this.recyclerViewOrNull;
        if (recyclerView == null) {
            throw new IllegalStateException("Please get it after onAttachedToRecyclerView()".toString());
        }
        Intrinsics.checkNotNull(recyclerView);
        return recyclerView;
    }

    public final Context getContext() {
        Context context = getRecyclerView().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "recyclerView.context");
        return context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkModule() {
        if (this instanceof LoadMoreModule) {
            this.mLoadMoreModule = ((LoadMoreModule) this).addLoadMoreModule(this);
        }
        if (this instanceof UpFetchModule) {
            this.mUpFetchModule = ((UpFetchModule) this).addUpFetchModule(this);
        }
        if (this instanceof DraggableModule) {
            this.mDraggableModule = ((DraggableModule) this).addDraggableModule(this);
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = null;
        switch (viewType) {
            case HEADER_VIEW /* 268435729 */:
                LinearLayout linearLayout = this.mHeaderLayout;
                if (linearLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                    linearLayout = null;
                }
                ViewParent parent2 = linearLayout.getParent();
                if (parent2 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) parent2;
                    LinearLayout linearLayout2 = this.mHeaderLayout;
                    if (linearLayout2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                        linearLayout2 = null;
                    }
                    viewGroup.removeView(linearLayout2);
                }
                LinearLayout linearLayout3 = this.mHeaderLayout;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                } else {
                    view = linearLayout3;
                }
                return (VH) createBaseViewHolder(view);
            case LOAD_MORE_VIEW /* 268436002 */:
                BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
                Intrinsics.checkNotNull(baseLoadMoreModule);
                VH vh = (VH) createBaseViewHolder(baseLoadMoreModule.getLoadMoreView().getRootView(parent));
                BaseLoadMoreModule baseLoadMoreModule2 = this.mLoadMoreModule;
                Intrinsics.checkNotNull(baseLoadMoreModule2);
                baseLoadMoreModule2.setupViewHolder$com_github_CymChad_brvah(vh);
                return vh;
            case FOOTER_VIEW /* 268436275 */:
                LinearLayout linearLayout4 = this.mFooterLayout;
                if (linearLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                    linearLayout4 = null;
                }
                ViewParent parent3 = linearLayout4.getParent();
                if (parent3 instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) parent3;
                    LinearLayout linearLayout5 = this.mFooterLayout;
                    if (linearLayout5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                        linearLayout5 = null;
                    }
                    viewGroup2.removeView(linearLayout5);
                }
                LinearLayout linearLayout6 = this.mFooterLayout;
                if (linearLayout6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                } else {
                    view = linearLayout6;
                }
                return (VH) createBaseViewHolder(view);
            case EMPTY_VIEW /* 268436821 */:
                FrameLayout frameLayout = this.mEmptyLayout;
                if (frameLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
                    frameLayout = null;
                }
                ViewParent parent4 = frameLayout.getParent();
                if (parent4 instanceof ViewGroup) {
                    ViewGroup viewGroup3 = (ViewGroup) parent4;
                    FrameLayout frameLayout2 = this.mEmptyLayout;
                    if (frameLayout2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
                        frameLayout2 = null;
                    }
                    viewGroup3.removeView(frameLayout2);
                }
                FrameLayout frameLayout3 = this.mEmptyLayout;
                if (frameLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
                } else {
                    view = frameLayout3;
                }
                return (VH) createBaseViewHolder(view);
            default:
                VH vh2 = (VH) onCreateDefViewHolder(parent, viewType);
                bindViewClickListener(vh2, viewType);
                BaseDraggableModule baseDraggableModule = this.mDraggableModule;
                if (baseDraggableModule != null) {
                    baseDraggableModule.initView$com_github_CymChad_brvah(vh2);
                }
                onItemViewHolderCreated(vh2, viewType);
                return vh2;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (hasEmptyView()) {
            if (this.headerWithEmptyEnable && hasHeaderLayout()) {
                i = 2;
            }
            return (this.footerWithEmptyEnable && hasFooterLayout()) ? i + 1 : i;
        }
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        return getHeaderLayoutCount() + getDefItemCount() + getFooterLayoutCount() + ((baseLoadMoreModule == null || !baseLoadMoreModule.hasLoadMoreView()) ? 0 : 1);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [boolean] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        if (hasEmptyView()) {
            boolean z = this.headerWithEmptyEnable && hasHeaderLayout();
            if (position != 0) {
                return position != 1 ? FOOTER_VIEW : FOOTER_VIEW;
            }
            if (z) {
                return HEADER_VIEW;
            }
            return EMPTY_VIEW;
        }
        boolean zHasHeaderLayout = hasHeaderLayout();
        if (zHasHeaderLayout && position == 0) {
            return HEADER_VIEW;
        }
        if (zHasHeaderLayout) {
            position--;
        }
        int size = this.data.size();
        if (position < size) {
            return getDefItemViewType(position);
        }
        return position - size < hasFooterLayout() ? FOOTER_VIEW : LOAD_MORE_VIEW;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(VH holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        BaseUpFetchModule baseUpFetchModule = this.mUpFetchModule;
        if (baseUpFetchModule != null) {
            baseUpFetchModule.autoUpFetch$com_github_CymChad_brvah(position);
        }
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule != null) {
            baseLoadMoreModule.autoLoadMore$com_github_CymChad_brvah(position);
        }
        switch (holder.getItemViewType()) {
            case HEADER_VIEW /* 268435729 */:
            case FOOTER_VIEW /* 268436275 */:
            case EMPTY_VIEW /* 268436821 */:
                break;
            case LOAD_MORE_VIEW /* 268436002 */:
                BaseLoadMoreModule baseLoadMoreModule2 = this.mLoadMoreModule;
                if (baseLoadMoreModule2 != null) {
                    baseLoadMoreModule2.getLoadMoreView().convert(holder, position, baseLoadMoreModule2.getLoadMoreStatus());
                }
                break;
            default:
                convert(holder, getItem(position - getHeaderLayoutCount()));
                break;
        }
    }

    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        if (payloads.isEmpty()) {
            onBindViewHolder((BaseViewHolder) holder, position);
        }
        BaseUpFetchModule baseUpFetchModule = this.mUpFetchModule;
        if (baseUpFetchModule != null) {
            baseUpFetchModule.autoUpFetch$com_github_CymChad_brvah(position);
        }
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule != null) {
            baseLoadMoreModule.autoLoadMore$com_github_CymChad_brvah(position);
        }
        switch (holder.getItemViewType()) {
            case HEADER_VIEW /* 268435729 */:
            case FOOTER_VIEW /* 268436275 */:
            case EMPTY_VIEW /* 268436821 */:
                break;
            case LOAD_MORE_VIEW /* 268436002 */:
                BaseLoadMoreModule baseLoadMoreModule2 = this.mLoadMoreModule;
                if (baseLoadMoreModule2 != null) {
                    baseLoadMoreModule2.getLoadMoreView().convert(holder, position, baseLoadMoreModule2.getLoadMoreStatus());
                }
                break;
            default:
                convert(holder, getItem(position - getHeaderLayoutCount()), payloads);
                break;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(VH holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        VH vh = holder;
        super.onViewAttachedToWindow(vh);
        if (isFixedViewType(holder.getItemViewType())) {
            setFullSpan(vh);
        } else {
            addAnimation(vh);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerViewOrNull = recyclerView;
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule != null) {
            baseDraggableModule.attachToRecyclerView(recyclerView);
        }
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(this) { // from class: com.chad.library.adapter.base.BaseQuickAdapter.onAttachedToRecyclerView.1
                final /* synthetic */ BaseQuickAdapter<T, VH> this$0;

                {
                    this.this$0 = this;
                }

                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    int itemViewType = this.this$0.getItemViewType(position);
                    if (itemViewType == 268435729 && this.this$0.getHeaderViewAsFlow()) {
                        return 1;
                    }
                    if (itemViewType == 268436275 && this.this$0.getFooterViewAsFlow()) {
                        return 1;
                    }
                    if (((BaseQuickAdapter) this.this$0).mSpanSizeLookup == null) {
                        return this.this$0.isFixedViewType(itemViewType) ? ((GridLayoutManager) layoutManager).getSpanCount() : spanSizeLookup.getSpanSize(position);
                    }
                    if (!this.this$0.isFixedViewType(itemViewType)) {
                        GridSpanSizeLookup gridSpanSizeLookup = ((BaseQuickAdapter) this.this$0).mSpanSizeLookup;
                        Intrinsics.checkNotNull(gridSpanSizeLookup);
                        return gridSpanSizeLookup.getSpanSize((GridLayoutManager) layoutManager, itemViewType, position - this.this$0.getHeaderLayoutCount());
                    }
                    return ((GridLayoutManager) layoutManager).getSpanCount();
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerViewOrNull = null;
    }

    public T getItem(int position) {
        return this.data.get(position);
    }

    public T getItemOrNull(int position) {
        return (T) CollectionsKt.getOrNull(this.data, position);
    }

    public int getItemPosition(T item) {
        if (item == null || !(!this.data.isEmpty())) {
            return -1;
        }
        return this.data.indexOf(item);
    }

    public final LinkedHashSet<Integer> getChildClickViewIds() {
        return this.childClickViewIds;
    }

    public final void addChildClickViewIds(int... viewIds) {
        Intrinsics.checkNotNullParameter(viewIds, "viewIds");
        for (int i : viewIds) {
            this.childClickViewIds.add(Integer.valueOf(i));
        }
    }

    public final LinkedHashSet<Integer> getChildLongClickViewIds() {
        return this.childLongClickViewIds;
    }

    public final void addChildLongClickViewIds(int... viewIds) {
        Intrinsics.checkNotNullParameter(viewIds, "viewIds");
        for (int i : viewIds) {
            this.childLongClickViewIds.add(Integer.valueOf(i));
        }
    }

    protected void bindViewClickListener(final VH viewHolder, int viewType) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        if (this.mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseQuickAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BaseQuickAdapter.m73bindViewClickListener$lambda7$lambda6(viewHolder, this, view);
                }
            });
        }
        if (this.mOnItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.BaseQuickAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return BaseQuickAdapter.m74bindViewClickListener$lambda9$lambda8(viewHolder, this, view);
                }
            });
        }
        if (this.mOnItemChildClickListener != null) {
            for (Integer id : getChildClickViewIds()) {
                View view = viewHolder.itemView;
                Intrinsics.checkNotNullExpressionValue(id, "id");
                View viewFindViewById = view.findViewById(id.intValue());
                if (viewFindViewById != null) {
                    Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<View>(id)");
                    if (!viewFindViewById.isClickable()) {
                        viewFindViewById.setClickable(true);
                    }
                    viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseQuickAdapter$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            BaseQuickAdapter.m71bindViewClickListener$lambda12$lambda11$lambda10(viewHolder, this, view2);
                        }
                    });
                }
            }
        }
        if (this.mOnItemChildLongClickListener != null) {
            for (Integer id2 : getChildLongClickViewIds()) {
                View view2 = viewHolder.itemView;
                Intrinsics.checkNotNullExpressionValue(id2, "id");
                View viewFindViewById2 = view2.findViewById(id2.intValue());
                if (viewFindViewById2 != null) {
                    Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<View>(id)");
                    if (!viewFindViewById2.isLongClickable()) {
                        viewFindViewById2.setLongClickable(true);
                    }
                    viewFindViewById2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.BaseQuickAdapter$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnLongClickListener
                        public final boolean onLongClick(View view3) {
                            return BaseQuickAdapter.m72bindViewClickListener$lambda15$lambda14$lambda13(viewHolder, this, view3);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindViewClickListener$lambda-7$lambda-6, reason: not valid java name */
    public static final void m73bindViewClickListener$lambda7$lambda6(BaseViewHolder viewHolder, BaseQuickAdapter this$0, View v) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1) {
            return;
        }
        int headerLayoutCount = bindingAdapterPosition - this$0.getHeaderLayoutCount();
        Intrinsics.checkNotNullExpressionValue(v, "v");
        this$0.setOnItemClick(v, headerLayoutCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindViewClickListener$lambda-9$lambda-8, reason: not valid java name */
    public static final boolean m74bindViewClickListener$lambda9$lambda8(BaseViewHolder viewHolder, BaseQuickAdapter this$0, View v) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1) {
            return false;
        }
        int headerLayoutCount = bindingAdapterPosition - this$0.getHeaderLayoutCount();
        Intrinsics.checkNotNullExpressionValue(v, "v");
        return this$0.setOnItemLongClick(v, headerLayoutCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindViewClickListener$lambda-12$lambda-11$lambda-10, reason: not valid java name */
    public static final void m71bindViewClickListener$lambda12$lambda11$lambda10(BaseViewHolder viewHolder, BaseQuickAdapter this$0, View v) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1) {
            return;
        }
        int headerLayoutCount = bindingAdapterPosition - this$0.getHeaderLayoutCount();
        Intrinsics.checkNotNullExpressionValue(v, "v");
        this$0.setOnItemChildClick(v, headerLayoutCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindViewClickListener$lambda-15$lambda-14$lambda-13, reason: not valid java name */
    public static final boolean m72bindViewClickListener$lambda15$lambda14$lambda13(BaseViewHolder viewHolder, BaseQuickAdapter this$0, View v) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1) {
            return false;
        }
        int headerLayoutCount = bindingAdapterPosition - this$0.getHeaderLayoutCount();
        Intrinsics.checkNotNullExpressionValue(v, "v");
        return this$0.setOnItemChildLongClick(v, headerLayoutCount);
    }

    protected void setOnItemClick(View v, int position) {
        Intrinsics.checkNotNullParameter(v, "v");
        OnItemClickListener onItemClickListener = this.mOnItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(this, v, position);
        }
    }

    protected boolean setOnItemLongClick(View v, int position) {
        Intrinsics.checkNotNullParameter(v, "v");
        OnItemLongClickListener onItemLongClickListener = this.mOnItemLongClickListener;
        if (onItemLongClickListener != null) {
            return onItemLongClickListener.onItemLongClick(this, v, position);
        }
        return false;
    }

    protected void setOnItemChildClick(View v, int position) {
        Intrinsics.checkNotNullParameter(v, "v");
        OnItemChildClickListener onItemChildClickListener = this.mOnItemChildClickListener;
        if (onItemChildClickListener != null) {
            onItemChildClickListener.onItemChildClick(this, v, position);
        }
    }

    protected boolean setOnItemChildLongClick(View v, int position) {
        Intrinsics.checkNotNullParameter(v, "v");
        OnItemChildLongClickListener onItemChildLongClickListener = this.mOnItemChildLongClickListener;
        if (onItemChildLongClickListener != null) {
            return onItemChildLongClickListener.onItemChildLongClick(this, v, position);
        }
        return false;
    }

    protected int getDefItemCount() {
        return this.data.size();
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    protected VH onCreateDefViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        return (VH) createBaseViewHolder(parent, this.layoutResId);
    }

    protected VH createBaseViewHolder(ViewGroup parent, int layoutResId) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        return (VH) createBaseViewHolder(AdapterUtilsKt.getItemView(parent, layoutResId));
    }

    protected VH createBaseViewHolder(View view) {
        VH vh;
        Intrinsics.checkNotNullParameter(view, "view");
        Class<?> instancedGenericKClass = null;
        for (Class<?> superclass = getClass(); instancedGenericKClass == null && superclass != null; superclass = superclass.getSuperclass()) {
            instancedGenericKClass = getInstancedGenericKClass(superclass);
        }
        if (instancedGenericKClass == null) {
            vh = (VH) new BaseViewHolder(view);
        } else {
            vh = (VH) createBaseGenericKInstance(instancedGenericKClass, view);
        }
        return vh == null ? (VH) new BaseViewHolder(view) : vh;
    }

    private final Class<?> getInstancedGenericKClass(Class<?> z) {
        try {
            Type genericSuperclass = z.getGenericSuperclass();
            if (!(genericSuperclass instanceof ParameterizedType)) {
                return null;
            }
            Type[] types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            Intrinsics.checkNotNullExpressionValue(types, "types");
            for (Type type : types) {
                if (type instanceof Class) {
                    if (BaseViewHolder.class.isAssignableFrom((Class) type)) {
                        return (Class) type;
                    }
                } else if (type instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) type).getRawType();
                    if ((rawType instanceof Class) && BaseViewHolder.class.isAssignableFrom((Class) rawType)) {
                        return (Class) rawType;
                    }
                } else {
                    continue;
                }
            }
            return null;
        } catch (TypeNotPresentException e) {
            e.printStackTrace();
            return null;
        } catch (GenericSignatureFormatError e2) {
            e2.printStackTrace();
            return null;
        } catch (MalformedParameterizedTypeException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private final VH createBaseGenericKInstance(Class<?> z, View view) {
        try {
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                Constructor<?> declaredConstructor = z.getDeclaredConstructor(getClass(), View.class);
                Intrinsics.checkNotNullExpressionValue(declaredConstructor, "z.getDeclaredConstructor…aClass, View::class.java)");
                declaredConstructor.setAccessible(true);
                Object objNewInstance = declaredConstructor.newInstance(this, view);
                if (objNewInstance != null) {
                    return (VH) objNewInstance;
                }
                throw new NullPointerException("null cannot be cast to non-null type VH of com.chad.library.adapter.base.BaseQuickAdapter");
            }
            Constructor<?> declaredConstructor2 = z.getDeclaredConstructor(View.class);
            Intrinsics.checkNotNullExpressionValue(declaredConstructor2, "z.getDeclaredConstructor(View::class.java)");
            declaredConstructor2.setAccessible(true);
            Object objNewInstance2 = declaredConstructor2.newInstance(view);
            if (objNewInstance2 != null) {
                return (VH) objNewInstance2;
            }
            throw new NullPointerException("null cannot be cast to non-null type VH of com.chad.library.adapter.base.BaseQuickAdapter");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }

    public final View getViewByPosition(int position, int viewId) {
        BaseViewHolder baseViewHolder;
        RecyclerView recyclerView = this.recyclerViewOrNull;
        if (recyclerView == null || (baseViewHolder = (BaseViewHolder) recyclerView.findViewHolderForLayoutPosition(position)) == null) {
            return null;
        }
        return baseViewHolder.getViewOrNull(viewId);
    }

    public static /* synthetic */ int addHeaderView$default(BaseQuickAdapter baseQuickAdapter, View view, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addHeaderView");
        }
        if ((i3 & 2) != 0) {
            i = -1;
        }
        if ((i3 & 4) != 0) {
            i2 = 1;
        }
        return baseQuickAdapter.addHeaderView(view, i, i2);
    }

    public final int addHeaderView(View view, int index, int orientation) {
        int headerViewPosition;
        RecyclerView.LayoutParams layoutParams;
        Intrinsics.checkNotNullParameter(view, "view");
        LinearLayout linearLayout = null;
        if (this.mHeaderLayout == null) {
            LinearLayout linearLayout2 = new LinearLayout(view.getContext());
            this.mHeaderLayout = linearLayout2;
            linearLayout2.setOrientation(orientation);
            LinearLayout linearLayout3 = this.mHeaderLayout;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                linearLayout3 = null;
            }
            if (orientation == 1) {
                layoutParams = new RecyclerView.LayoutParams(-1, -2);
            } else {
                layoutParams = new RecyclerView.LayoutParams(-2, -1);
            }
            linearLayout3.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout4 = this.mHeaderLayout;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
            linearLayout4 = null;
        }
        int childCount = linearLayout4.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        LinearLayout linearLayout5 = this.mHeaderLayout;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
            linearLayout5 = null;
        }
        linearLayout5.addView(view, index);
        LinearLayout linearLayout6 = this.mHeaderLayout;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
        } else {
            linearLayout = linearLayout6;
        }
        if (linearLayout.getChildCount() == 1 && (headerViewPosition = getHeaderViewPosition()) != -1) {
            notifyItemInserted(headerViewPosition);
        }
        return index;
    }

    public static /* synthetic */ int setHeaderView$default(BaseQuickAdapter baseQuickAdapter, View view, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setHeaderView");
        }
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = 1;
        }
        return baseQuickAdapter.setHeaderView(view, i, i2);
    }

    public final int setHeaderView(View view, int index, int orientation) {
        Intrinsics.checkNotNullParameter(view, "view");
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout != null) {
            LinearLayout linearLayout2 = null;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                linearLayout = null;
            }
            if (linearLayout.getChildCount() > index) {
                LinearLayout linearLayout3 = this.mHeaderLayout;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                    linearLayout3 = null;
                }
                linearLayout3.removeViewAt(index);
                LinearLayout linearLayout4 = this.mHeaderLayout;
                if (linearLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                } else {
                    linearLayout2 = linearLayout4;
                }
                linearLayout2.addView(view, index);
                return index;
            }
        }
        return addHeaderView(view, index, orientation);
    }

    public final boolean hasHeaderLayout() {
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout == null) {
            return false;
        }
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
            linearLayout = null;
        }
        return linearLayout.getChildCount() > 0;
    }

    public final void removeHeaderView(View header) {
        int headerViewPosition;
        Intrinsics.checkNotNullParameter(header, "header");
        if (hasHeaderLayout()) {
            LinearLayout linearLayout = this.mHeaderLayout;
            LinearLayout linearLayout2 = null;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                linearLayout = null;
            }
            linearLayout.removeView(header);
            LinearLayout linearLayout3 = this.mHeaderLayout;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
            } else {
                linearLayout2 = linearLayout3;
            }
            if (linearLayout2.getChildCount() != 0 || (headerViewPosition = getHeaderViewPosition()) == -1) {
                return;
            }
            notifyItemRemoved(headerViewPosition);
        }
    }

    public final void removeAllHeaderView() {
        if (hasHeaderLayout()) {
            LinearLayout linearLayout = this.mHeaderLayout;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
                linearLayout = null;
            }
            linearLayout.removeAllViews();
            int headerViewPosition = getHeaderViewPosition();
            if (headerViewPosition != -1) {
                notifyItemRemoved(headerViewPosition);
            }
        }
    }

    public final int getHeaderViewPosition() {
        return (!hasEmptyView() || this.headerWithEmptyEnable) ? 0 : -1;
    }

    public final int getHeaderLayoutCount() {
        return hasHeaderLayout() ? 1 : 0;
    }

    public final LinearLayout getHeaderLayout() {
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout == null) {
            return (LinearLayout) null;
        }
        if (linearLayout != null) {
            return linearLayout;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mHeaderLayout");
        return null;
    }

    public static /* synthetic */ int addFooterView$default(BaseQuickAdapter baseQuickAdapter, View view, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addFooterView");
        }
        if ((i3 & 2) != 0) {
            i = -1;
        }
        if ((i3 & 4) != 0) {
            i2 = 1;
        }
        return baseQuickAdapter.addFooterView(view, i, i2);
    }

    public final int addFooterView(View view, int index, int orientation) {
        int footerViewPosition;
        RecyclerView.LayoutParams layoutParams;
        Intrinsics.checkNotNullParameter(view, "view");
        LinearLayout linearLayout = null;
        if (this.mFooterLayout == null) {
            LinearLayout linearLayout2 = new LinearLayout(view.getContext());
            this.mFooterLayout = linearLayout2;
            linearLayout2.setOrientation(orientation);
            LinearLayout linearLayout3 = this.mFooterLayout;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                linearLayout3 = null;
            }
            if (orientation == 1) {
                layoutParams = new RecyclerView.LayoutParams(-1, -2);
            } else {
                layoutParams = new RecyclerView.LayoutParams(-2, -1);
            }
            linearLayout3.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout4 = this.mFooterLayout;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
            linearLayout4 = null;
        }
        int childCount = linearLayout4.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        LinearLayout linearLayout5 = this.mFooterLayout;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
            linearLayout5 = null;
        }
        linearLayout5.addView(view, index);
        LinearLayout linearLayout6 = this.mFooterLayout;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
        } else {
            linearLayout = linearLayout6;
        }
        if (linearLayout.getChildCount() == 1 && (footerViewPosition = getFooterViewPosition()) != -1) {
            notifyItemInserted(footerViewPosition);
        }
        return index;
    }

    public static /* synthetic */ int setFooterView$default(BaseQuickAdapter baseQuickAdapter, View view, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setFooterView");
        }
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = 1;
        }
        return baseQuickAdapter.setFooterView(view, i, i2);
    }

    public final int setFooterView(View view, int index, int orientation) {
        Intrinsics.checkNotNullParameter(view, "view");
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout != null) {
            LinearLayout linearLayout2 = null;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                linearLayout = null;
            }
            if (linearLayout.getChildCount() > index) {
                LinearLayout linearLayout3 = this.mFooterLayout;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                    linearLayout3 = null;
                }
                linearLayout3.removeViewAt(index);
                LinearLayout linearLayout4 = this.mFooterLayout;
                if (linearLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                } else {
                    linearLayout2 = linearLayout4;
                }
                linearLayout2.addView(view, index);
                return index;
            }
        }
        return addFooterView(view, index, orientation);
    }

    public final void removeFooterView(View footer) {
        int footerViewPosition;
        Intrinsics.checkNotNullParameter(footer, "footer");
        if (hasFooterLayout()) {
            LinearLayout linearLayout = this.mFooterLayout;
            LinearLayout linearLayout2 = null;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                linearLayout = null;
            }
            linearLayout.removeView(footer);
            LinearLayout linearLayout3 = this.mFooterLayout;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
            } else {
                linearLayout2 = linearLayout3;
            }
            if (linearLayout2.getChildCount() != 0 || (footerViewPosition = getFooterViewPosition()) == -1) {
                return;
            }
            notifyItemRemoved(footerViewPosition);
        }
    }

    public final void removeAllFooterView() {
        if (hasFooterLayout()) {
            LinearLayout linearLayout = this.mFooterLayout;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
                linearLayout = null;
            }
            linearLayout.removeAllViews();
            int footerViewPosition = getFooterViewPosition();
            if (footerViewPosition != -1) {
                notifyItemRemoved(footerViewPosition);
            }
        }
    }

    public final boolean hasFooterLayout() {
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout == null) {
            return false;
        }
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
            linearLayout = null;
        }
        return linearLayout.getChildCount() > 0;
    }

    public final int getFooterViewPosition() {
        if (hasEmptyView()) {
            int i = 1;
            if (this.headerWithEmptyEnable && hasHeaderLayout()) {
                i = 2;
            }
            if (this.footerWithEmptyEnable) {
                return i;
            }
            return -1;
        }
        return getHeaderLayoutCount() + this.data.size();
    }

    public final int getFooterLayoutCount() {
        return hasFooterLayout() ? 1 : 0;
    }

    public final LinearLayout getFooterLayout() {
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout == null) {
            return (LinearLayout) null;
        }
        if (linearLayout != null) {
            return linearLayout;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mFooterLayout");
        return null;
    }

    public final void setEmptyView(View emptyView) {
        boolean z;
        ViewGroup.LayoutParams layoutParams;
        Intrinsics.checkNotNullParameter(emptyView, "emptyView");
        int itemCount = getItemCount();
        int i = 0;
        FrameLayout frameLayout = null;
        if (this.mEmptyLayout == null) {
            FrameLayout frameLayout2 = new FrameLayout(emptyView.getContext());
            this.mEmptyLayout = frameLayout2;
            ViewGroup.LayoutParams layoutParams2 = emptyView.getLayoutParams();
            if (layoutParams2 != null) {
                layoutParams = new ViewGroup.LayoutParams(layoutParams2.width, layoutParams2.height);
            } else {
                layoutParams = new ViewGroup.LayoutParams(-1, -1);
            }
            frameLayout2.setLayoutParams(layoutParams);
            z = true;
        } else {
            ViewGroup.LayoutParams layoutParams3 = emptyView.getLayoutParams();
            if (layoutParams3 != null) {
                FrameLayout frameLayout3 = this.mEmptyLayout;
                if (frameLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
                    frameLayout3 = null;
                }
                ViewGroup.LayoutParams layoutParams4 = frameLayout3.getLayoutParams();
                layoutParams4.width = layoutParams3.width;
                layoutParams4.height = layoutParams3.height;
                FrameLayout frameLayout4 = this.mEmptyLayout;
                if (frameLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
                    frameLayout4 = null;
                }
                frameLayout4.setLayoutParams(layoutParams4);
            }
            z = false;
        }
        FrameLayout frameLayout5 = this.mEmptyLayout;
        if (frameLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
            frameLayout5 = null;
        }
        frameLayout5.removeAllViews();
        FrameLayout frameLayout6 = this.mEmptyLayout;
        if (frameLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
        } else {
            frameLayout = frameLayout6;
        }
        frameLayout.addView(emptyView);
        this.isUseEmpty = true;
        if (z && hasEmptyView()) {
            if (this.headerWithEmptyEnable && hasHeaderLayout()) {
                i = 1;
            }
            if (getItemCount() > itemCount) {
                notifyItemInserted(i);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public final void setEmptyView(int layoutResId) {
        RecyclerView recyclerView = this.recyclerViewOrNull;
        if (recyclerView != null) {
            View view = LayoutInflater.from(recyclerView.getContext()).inflate(layoutResId, (ViewGroup) recyclerView, false);
            Intrinsics.checkNotNullExpressionValue(view, "view");
            setEmptyView(view);
        }
    }

    public final void removeEmptyView() {
        FrameLayout frameLayout = this.mEmptyLayout;
        if (frameLayout != null) {
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
                frameLayout = null;
            }
            frameLayout.removeAllViews();
        }
    }

    public final boolean hasEmptyView() {
        FrameLayout frameLayout = this.mEmptyLayout;
        if (frameLayout != null) {
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
                frameLayout = null;
            }
            if (frameLayout.getChildCount() != 0 && this.isUseEmpty) {
                return this.data.isEmpty();
            }
            return false;
        }
        return false;
    }

    public final FrameLayout getEmptyLayout() {
        FrameLayout frameLayout = this.mEmptyLayout;
        if (frameLayout == null) {
            return (FrameLayout) null;
        }
        if (frameLayout != null) {
            return frameLayout;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mEmptyLayout");
        return null;
    }

    private final void addAnimation(RecyclerView.ViewHolder holder) {
        if (this.animationEnable) {
            if (!this.isAnimationFirstOnly || holder.getLayoutPosition() > this.mLastPosition) {
                AlphaInAnimation alphaInAnimation = this.adapterAnimation;
                if (alphaInAnimation == null) {
                    alphaInAnimation = new AlphaInAnimation(0.0f, 1, null);
                }
                View view = holder.itemView;
                Intrinsics.checkNotNullExpressionValue(view, "holder.itemView");
                Animator[] animatorArrAnimators = alphaInAnimation.animators(view);
                for (Animator animator : animatorArrAnimators) {
                    startAnim(animator, holder.getLayoutPosition());
                }
                this.mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    protected void startAnim(Animator anim, int index) {
        Intrinsics.checkNotNullParameter(anim, "anim");
        anim.start();
    }

    public final void setAnimationWithDefault(AnimationType animationType) {
        AlphaInAnimation alphaInAnimation;
        Intrinsics.checkNotNullParameter(animationType, "animationType");
        int i = WhenMappings.$EnumSwitchMapping$0[animationType.ordinal()];
        if (i == 1) {
            alphaInAnimation = new AlphaInAnimation(0.0f, 1, null);
        } else if (i == 2) {
            alphaInAnimation = new ScaleInAnimation(0.0f, 1, null);
        } else if (i == 3) {
            alphaInAnimation = new SlideInBottomAnimation();
        } else if (i == 4) {
            alphaInAnimation = new SlideInLeftAnimation();
        } else {
            if (i != 5) {
                throw new NoWhenBranchMatchedException();
            }
            alphaInAnimation = new SlideInRightAnimation();
        }
        setAdapterAnimation(alphaInAnimation);
    }

    @Deprecated(message = "Please use setNewInstance(), This method will be removed in the next version", replaceWith = @ReplaceWith(expression = "setNewInstance(data)", imports = {}))
    public void setNewData(List<T> data) {
        setNewInstance(data);
    }

    public void setNewInstance(List<T> list) {
        if (list == this.data) {
            return;
        }
        if (list == null) {
            list = new ArrayList();
        }
        this.data = list;
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule != null) {
            baseLoadMoreModule.reset$com_github_CymChad_brvah();
        }
        this.mLastPosition = -1;
        notifyDataSetChanged();
        BaseLoadMoreModule baseLoadMoreModule2 = this.mLoadMoreModule;
        if (baseLoadMoreModule2 != null) {
            baseLoadMoreModule2.checkDisableLoadMoreIfNotFullPage();
        }
    }

    @Deprecated(message = "Please use setData()", replaceWith = @ReplaceWith(expression = "setList(newData)", imports = {}))
    public void replaceData(Collection<? extends T> newData) {
        Intrinsics.checkNotNullParameter(newData, "newData");
        setList(newData);
    }

    public void setList(Collection<? extends T> list) {
        List<T> list2 = this.data;
        if (list != list2) {
            list2.clear();
            if (!(list == null || list.isEmpty())) {
                this.data.addAll(list);
            }
        } else {
            if (!(list == null || list.isEmpty())) {
                ArrayList arrayList = new ArrayList(list);
                this.data.clear();
                this.data.addAll(arrayList);
            } else {
                this.data.clear();
            }
        }
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule != null) {
            baseLoadMoreModule.reset$com_github_CymChad_brvah();
        }
        this.mLastPosition = -1;
        notifyDataSetChanged();
        BaseLoadMoreModule baseLoadMoreModule2 = this.mLoadMoreModule;
        if (baseLoadMoreModule2 != null) {
            baseLoadMoreModule2.checkDisableLoadMoreIfNotFullPage();
        }
    }

    public void setData(int index, T data) {
        if (index >= this.data.size()) {
            return;
        }
        this.data.set(index, data);
        notifyItemChanged(index + getHeaderLayoutCount());
    }

    public void addData(int position, T data) {
        this.data.add(position, data);
        notifyItemInserted(position + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void addData(T data) {
        this.data.add(data);
        notifyItemInserted(this.data.size() + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void addData(int position, Collection<? extends T> newData) {
        Intrinsics.checkNotNullParameter(newData, "newData");
        this.data.addAll(position, newData);
        notifyItemRangeInserted(position + getHeaderLayoutCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void addData(Collection<? extends T> newData) {
        Intrinsics.checkNotNullParameter(newData, "newData");
        this.data.addAll(newData);
        notifyItemRangeInserted((this.data.size() - newData.size()) + getHeaderLayoutCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    @Deprecated(message = "Please use removeAt()", replaceWith = @ReplaceWith(expression = "removeAt(position)", imports = {}))
    public void remove(int position) {
        removeAt(position);
    }

    public void removeAt(int position) {
        if (position >= this.data.size()) {
            return;
        }
        this.data.remove(position);
        int headerLayoutCount = position + getHeaderLayoutCount();
        notifyItemRemoved(headerLayoutCount);
        compatibilityDataSizeChanged(0);
        notifyItemRangeChanged(headerLayoutCount, this.data.size() - headerLayoutCount);
    }

    public void remove(T data) {
        int iIndexOf = this.data.indexOf(data);
        if (iIndexOf == -1) {
            return;
        }
        removeAt(iIndexOf);
    }

    protected final void compatibilityDataSizeChanged(int size) {
        if (this.data.size() == size) {
            notifyDataSetChanged();
        }
    }

    public final void setDiffCallback(DiffUtil.ItemCallback<T> diffCallback) {
        Intrinsics.checkNotNullParameter(diffCallback, "diffCallback");
        setDiffConfig(new BrvahAsyncDifferConfig.Builder(diffCallback).build());
    }

    public final void setDiffConfig(BrvahAsyncDifferConfig<T> config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.mDiffHelper = new BrvahAsyncDiffer<>(this, config);
    }

    @Deprecated(message = "User getDiffer()", replaceWith = @ReplaceWith(expression = "getDiffer()", imports = {}))
    public final BrvahAsyncDiffer<T> getDiffHelper() {
        return getDiffer();
    }

    public final BrvahAsyncDiffer<T> getDiffer() {
        BrvahAsyncDiffer<T> brvahAsyncDiffer = this.mDiffHelper;
        if (brvahAsyncDiffer == null) {
            throw new IllegalStateException("Please use setDiffCallback() or setDiffConfig() first!".toString());
        }
        Intrinsics.checkNotNull(brvahAsyncDiffer);
        return brvahAsyncDiffer;
    }

    public static /* synthetic */ void setDiffNewData$default(BaseQuickAdapter baseQuickAdapter, List list, Runnable runnable, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setDiffNewData");
        }
        if ((i & 2) != 0) {
            runnable = null;
        }
        baseQuickAdapter.setDiffNewData(list, runnable);
    }

    public void setDiffNewData(List<T> list, Runnable commitCallback) {
        if (hasEmptyView()) {
            setNewInstance(list);
            if (commitCallback != null) {
                commitCallback.run();
                return;
            }
            return;
        }
        BrvahAsyncDiffer<T> brvahAsyncDiffer = this.mDiffHelper;
        if (brvahAsyncDiffer != null) {
            brvahAsyncDiffer.submitList(list, commitCallback);
        }
    }

    public void setDiffNewData(DiffUtil.DiffResult diffResult, List<T> list) {
        Intrinsics.checkNotNullParameter(diffResult, "diffResult");
        Intrinsics.checkNotNullParameter(list, "list");
        if (hasEmptyView()) {
            setNewInstance(list);
        } else {
            diffResult.dispatchUpdatesTo(new BrvahListUpdateCallback(this));
            this.data = list;
        }
    }

    public final void setGridSpanSizeLookup(GridSpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public final void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public final void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public final void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.mOnItemChildClickListener = listener;
    }

    public final void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
        this.mOnItemChildLongClickListener = listener;
    }

    /* JADX INFO: renamed from: getOnItemClickListener, reason: from getter */
    public final OnItemClickListener getMOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    /* JADX INFO: renamed from: getOnItemLongClickListener, reason: from getter */
    public final OnItemLongClickListener getMOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    /* JADX INFO: renamed from: getOnItemChildClickListener, reason: from getter */
    public final OnItemChildClickListener getMOnItemChildClickListener() {
        return this.mOnItemChildClickListener;
    }

    /* JADX INFO: renamed from: getOnItemChildLongClickListener, reason: from getter */
    public final OnItemChildLongClickListener getMOnItemChildLongClickListener() {
        return this.mOnItemChildLongClickListener;
    }
}
