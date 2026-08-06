package com.squareup.moshi;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import okio.BufferedSink;
import okio.BufferedSource;

/* JADX INFO: loaded from: classes.dex */
public abstract class JsonWriter implements Closeable, Flushable {
    String indent;
    boolean lenient;
    boolean promoteValueToName;
    boolean serializeNulls;
    private Map<Class<?>, Object> tags;
    int stackSize = 0;
    int[] scopes = new int[32];
    String[] pathNames = new String[32];
    int[] pathIndices = new int[32];
    int flattenStackSize = -1;

    public abstract JsonWriter beginArray() throws IOException;

    public abstract JsonWriter beginObject() throws IOException;

    public abstract JsonWriter endArray() throws IOException;

    public abstract JsonWriter endObject() throws IOException;

    public abstract JsonWriter name(String str) throws IOException;

    public abstract JsonWriter nullValue() throws IOException;

    public abstract JsonWriter value(double d) throws IOException;

    public abstract JsonWriter value(long j) throws IOException;

    public abstract JsonWriter value(@Nullable Boolean bool) throws IOException;

    public abstract JsonWriter value(@Nullable Number number) throws IOException;

    public abstract JsonWriter value(@Nullable String str) throws IOException;

    public abstract JsonWriter value(boolean z) throws IOException;

    @CheckReturnValue
    public abstract BufferedSink valueSink() throws IOException;

    @CheckReturnValue
    public static JsonWriter of(BufferedSink bufferedSink) {
        return new JsonUtf8Writer(bufferedSink);
    }

    JsonWriter() {
    }

    final int peekScope() {
        int i = this.stackSize;
        if (i == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        return this.scopes[i - 1];
    }

    final boolean checkStack() {
        int i = this.stackSize;
        int[] iArr = this.scopes;
        if (i != iArr.length) {
            return false;
        }
        if (i == 256) {
            throw new JsonDataException("Nesting too deep at " + getPath() + ": circular reference?");
        }
        this.scopes = Arrays.copyOf(iArr, iArr.length * 2);
        String[] strArr = this.pathNames;
        this.pathNames = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
        int[] iArr2 = this.pathIndices;
        this.pathIndices = Arrays.copyOf(iArr2, iArr2.length * 2);
        if (!(this instanceof JsonValueWriter)) {
            return true;
        }
        JsonValueWriter jsonValueWriter = (JsonValueWriter) this;
        jsonValueWriter.stack = Arrays.copyOf(jsonValueWriter.stack, jsonValueWriter.stack.length * 2);
        return true;
    }

    final void pushScope(int i) {
        int[] iArr = this.scopes;
        int i2 = this.stackSize;
        this.stackSize = i2 + 1;
        iArr[i2] = i;
    }

    final void replaceTop(int i) {
        this.scopes[this.stackSize - 1] = i;
    }

    public void setIndent(String str) {
        if (str.isEmpty()) {
            str = null;
        }
        this.indent = str;
    }

    @CheckReturnValue
    public final String getIndent() {
        String str = this.indent;
        return str != null ? str : "";
    }

    public final void setLenient(boolean z) {
        this.lenient = z;
    }

    @CheckReturnValue
    public final boolean isLenient() {
        return this.lenient;
    }

    public final void setSerializeNulls(boolean z) {
        this.serializeNulls = z;
    }

    @CheckReturnValue
    public final boolean getSerializeNulls() {
        return this.serializeNulls;
    }

    public final JsonWriter value(BufferedSource bufferedSource) throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("BufferedSource cannot be used as a map key in JSON at path " + getPath());
        }
        BufferedSink bufferedSinkValueSink = valueSink();
        try {
            bufferedSource.readAll(bufferedSinkValueSink);
            if (bufferedSinkValueSink != null) {
                bufferedSinkValueSink.close();
            }
            return this;
        } catch (Throwable th) {
            if (bufferedSinkValueSink != null) {
                try {
                    bufferedSinkValueSink.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final JsonWriter jsonValue(@Nullable Object obj) throws IOException {
        if (obj instanceof Map) {
            beginObject();
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                if (!(key instanceof String)) {
                    throw new IllegalArgumentException(key == null ? "Map keys must be non-null" : "Map keys must be of type String: " + key.getClass().getName());
                }
                name((String) key);
                jsonValue(entry.getValue());
            }
            endObject();
        } else if (obj instanceof List) {
            beginArray();
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                jsonValue(it.next());
            }
            endArray();
        } else if (obj instanceof String) {
            value((String) obj);
        } else if (obj instanceof Boolean) {
            value(((Boolean) obj).booleanValue());
        } else if (obj instanceof Double) {
            value(((Double) obj).doubleValue());
        } else if (obj instanceof Long) {
            value(((Long) obj).longValue());
        } else if (obj instanceof Number) {
            value((Number) obj);
        } else if (obj == null) {
            nullValue();
        } else {
            throw new IllegalArgumentException("Unsupported type: " + obj.getClass().getName());
        }
        return this;
    }

    public final void promoteValueToName() throws IOException {
        int iPeekScope = peekScope();
        if (iPeekScope != 5 && iPeekScope != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        this.promoteValueToName = true;
    }

    @CheckReturnValue
    public final int beginFlatten() {
        int iPeekScope = peekScope();
        if (iPeekScope != 5 && iPeekScope != 3 && iPeekScope != 2 && iPeekScope != 1) {
            throw new IllegalStateException("Nesting problem.");
        }
        int i = this.flattenStackSize;
        this.flattenStackSize = this.stackSize;
        return i;
    }

    public final void endFlatten(int i) {
        this.flattenStackSize = i;
    }

    @CheckReturnValue
    public final String getPath() {
        return JsonScope.getPath(this.stackSize, this.scopes, this.pathNames, this.pathIndices);
    }

    @CheckReturnValue
    @Nullable
    public final <T> T tag(Class<T> cls) {
        Map<Class<?>, Object> map = this.tags;
        if (map == null) {
            return null;
        }
        return (T) map.get(cls);
    }

    public final <T> void setTag(Class<T> cls, T t) {
        if (!cls.isAssignableFrom(t.getClass())) {
            throw new IllegalArgumentException("Tag value must be of type " + cls.getName());
        }
        if (this.tags == null) {
            this.tags = new LinkedHashMap();
        }
        this.tags.put(cls, t);
    }
}
