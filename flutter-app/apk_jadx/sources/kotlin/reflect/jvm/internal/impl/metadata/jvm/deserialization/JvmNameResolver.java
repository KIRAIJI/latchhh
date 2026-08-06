package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;

/* JADX INFO: compiled from: JvmNameResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JvmNameResolver implements NameResolver {
    public static final Companion Companion;
    private static final List<String> PREDEFINED_STRINGS;
    private static final Map<String, Integer> PREDEFINED_STRINGS_MAP;

    /* JADX INFO: renamed from: kotlin, reason: collision with root package name */
    private static final String f5kotlin;
    private final Set<Integer> localNameIndices;
    private final List<JvmProtoBuf.StringTableTypes.Record> records;
    private final String[] strings;
    private final JvmProtoBuf.StringTableTypes types;

    /* JADX INFO: compiled from: JvmNameResolver.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JvmProtoBuf.StringTableTypes.Record.Operation.values().length];
            iArr[JvmProtoBuf.StringTableTypes.Record.Operation.NONE.ordinal()] = 1;
            iArr[JvmProtoBuf.StringTableTypes.Record.Operation.INTERNAL_TO_CLASS_ID.ordinal()] = 2;
            iArr[JvmProtoBuf.StringTableTypes.Record.Operation.DESC_TO_CLASS_ID.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public JvmNameResolver(JvmProtoBuf.StringTableTypes types, String[] strings) {
        Set<Integer> set;
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(strings, "strings");
        this.types = types;
        this.strings = strings;
        List<Integer> localNameList = types.getLocalNameList();
        if (localNameList.isEmpty()) {
            set = SetsKt.emptySet();
        } else {
            Intrinsics.checkNotNullExpressionValue(localNameList, "");
            set = CollectionsKt.toSet(localNameList);
        }
        this.localNameIndices = set;
        ArrayList arrayList = new ArrayList();
        List<JvmProtoBuf.StringTableTypes.Record> recordList = getTypes().getRecordList();
        arrayList.ensureCapacity(recordList.size());
        for (JvmProtoBuf.StringTableTypes.Record record : recordList) {
            int range = record.getRange();
            for (int i = 0; i < range; i++) {
                arrayList.add(record);
            }
        }
        arrayList.trimToSize();
        Unit unit = Unit.INSTANCE;
        this.records = arrayList;
    }

    public final JvmProtoBuf.StringTableTypes getTypes() {
        return this.types;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0047  */
    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String getString(int r18) {
        /*
            Method dump skipped, instruction units count: 288
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver.getString(int):java.lang.String");
    }

    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    public String getQualifiedClassName(int i) {
        return getString(i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    public boolean isLocalClassName(int i) {
        return this.localNameIndices.contains(Integer.valueOf(i));
    }

    /* JADX INFO: compiled from: JvmNameResolver.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<String> getPREDEFINED_STRINGS() {
            return JvmNameResolver.PREDEFINED_STRINGS;
        }
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        String strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.listOf((Object[]) new Character[]{'k', 'o', 't', 'l', 'i', 'n'}), "", null, null, 0, null, null, 62, null);
        f5kotlin = strJoinToString$default;
        PREDEFINED_STRINGS = CollectionsKt.listOf((Object[]) new String[]{Intrinsics.stringPlus(strJoinToString$default, "/Any"), Intrinsics.stringPlus(strJoinToString$default, "/Nothing"), Intrinsics.stringPlus(strJoinToString$default, "/Unit"), Intrinsics.stringPlus(strJoinToString$default, "/Throwable"), Intrinsics.stringPlus(strJoinToString$default, "/Number"), Intrinsics.stringPlus(strJoinToString$default, "/Byte"), Intrinsics.stringPlus(strJoinToString$default, "/Double"), Intrinsics.stringPlus(strJoinToString$default, "/Float"), Intrinsics.stringPlus(strJoinToString$default, "/Int"), Intrinsics.stringPlus(strJoinToString$default, "/Long"), Intrinsics.stringPlus(strJoinToString$default, "/Short"), Intrinsics.stringPlus(strJoinToString$default, "/Boolean"), Intrinsics.stringPlus(strJoinToString$default, "/Char"), Intrinsics.stringPlus(strJoinToString$default, "/CharSequence"), Intrinsics.stringPlus(strJoinToString$default, "/String"), Intrinsics.stringPlus(strJoinToString$default, "/Comparable"), Intrinsics.stringPlus(strJoinToString$default, "/Enum"), Intrinsics.stringPlus(strJoinToString$default, "/Array"), Intrinsics.stringPlus(strJoinToString$default, "/ByteArray"), Intrinsics.stringPlus(strJoinToString$default, "/DoubleArray"), Intrinsics.stringPlus(strJoinToString$default, "/FloatArray"), Intrinsics.stringPlus(strJoinToString$default, "/IntArray"), Intrinsics.stringPlus(strJoinToString$default, "/LongArray"), Intrinsics.stringPlus(strJoinToString$default, "/ShortArray"), Intrinsics.stringPlus(strJoinToString$default, "/BooleanArray"), Intrinsics.stringPlus(strJoinToString$default, "/CharArray"), Intrinsics.stringPlus(strJoinToString$default, "/Cloneable"), Intrinsics.stringPlus(strJoinToString$default, "/Annotation"), Intrinsics.stringPlus(strJoinToString$default, "/collections/Iterable"), Intrinsics.stringPlus(strJoinToString$default, "/collections/MutableIterable"), Intrinsics.stringPlus(strJoinToString$default, "/collections/Collection"), Intrinsics.stringPlus(strJoinToString$default, "/collections/MutableCollection"), Intrinsics.stringPlus(strJoinToString$default, "/collections/List"), Intrinsics.stringPlus(strJoinToString$default, "/collections/MutableList"), Intrinsics.stringPlus(strJoinToString$default, "/collections/Set"), Intrinsics.stringPlus(strJoinToString$default, "/collections/MutableSet"), Intrinsics.stringPlus(strJoinToString$default, "/collections/Map"), Intrinsics.stringPlus(strJoinToString$default, "/collections/MutableMap"), Intrinsics.stringPlus(strJoinToString$default, "/collections/Map.Entry"), Intrinsics.stringPlus(strJoinToString$default, "/collections/MutableMap.MutableEntry"), Intrinsics.stringPlus(strJoinToString$default, "/collections/Iterator"), Intrinsics.stringPlus(strJoinToString$default, "/collections/MutableIterator"), Intrinsics.stringPlus(strJoinToString$default, "/collections/ListIterator"), Intrinsics.stringPlus(strJoinToString$default, "/collections/MutableListIterator")});
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(companion.getPREDEFINED_STRINGS());
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            linkedHashMap.put((String) indexedValue.getValue(), Integer.valueOf(indexedValue.getIndex()));
        }
        PREDEFINED_STRINGS_MAP = linkedHashMap;
    }
}
