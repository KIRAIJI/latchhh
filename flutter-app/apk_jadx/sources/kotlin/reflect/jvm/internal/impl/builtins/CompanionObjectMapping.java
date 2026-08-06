package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;

/* JADX INFO: compiled from: CompanionObjectMapping.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CompanionObjectMapping {
    public static final CompanionObjectMapping INSTANCE = new CompanionObjectMapping();
    private static final Set<ClassId> classIds;

    private CompanionObjectMapping() {
    }

    public final Set<ClassId> getClassIds() {
        return classIds;
    }

    static {
        Set<PrimitiveType> set = PrimitiveType.NUMBER_TYPES;
        StandardNames standardNames = StandardNames.INSTANCE;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(StandardNames.getPrimitiveFqName((PrimitiveType) it.next()));
        }
        FqName safe = StandardNames.FqNames.string.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "string.toSafe()");
        List listPlus = CollectionsKt.plus((Collection<? extends FqName>) arrayList, safe);
        FqName safe2 = StandardNames.FqNames._boolean.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe2, "_boolean.toSafe()");
        List listPlus2 = CollectionsKt.plus((Collection<? extends FqName>) listPlus, safe2);
        FqName safe3 = StandardNames.FqNames._enum.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe3, "_enum.toSafe()");
        List listPlus3 = CollectionsKt.plus((Collection<? extends FqName>) listPlus2, safe3);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it2 = listPlus3.iterator();
        while (it2.hasNext()) {
            linkedHashSet.add(ClassId.topLevel((FqName) it2.next()));
        }
        classIds = linkedHashSet;
    }

    public final Set<ClassId> allClassesWithIntrinsicCompanions() {
        return classIds;
    }
}
