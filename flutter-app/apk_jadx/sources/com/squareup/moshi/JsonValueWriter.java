package com.squareup.moshi;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/* JADX INFO: loaded from: classes.dex */
final class JsonValueWriter extends JsonWriter {

    @Nullable
    private String deferredName;
    Object[] stack = new Object[32];

    JsonValueWriter() {
        pushScope(6);
    }

    public Object root() {
        int i = this.stackSize;
        if (i > 1 || (i == 1 && this.scopes[i - 1] != 7)) {
            throw new IllegalStateException("Incomplete document");
        }
        return this.stack[0];
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter beginArray() throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("Array cannot be used as a map key in JSON at path " + getPath());
        }
        if (this.stackSize == this.flattenStackSize && this.scopes[this.stackSize - 1] == 1) {
            this.flattenStackSize = ~this.flattenStackSize;
            return this;
        }
        checkStack();
        ArrayList arrayList = new ArrayList();
        add(arrayList);
        this.stack[this.stackSize] = arrayList;
        this.pathIndices[this.stackSize] = 0;
        pushScope(1);
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter endArray() throws IOException {
        if (peekScope() != 1) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.stackSize == (~this.flattenStackSize)) {
            this.flattenStackSize = ~this.flattenStackSize;
            return this;
        }
        this.stackSize--;
        this.stack[this.stackSize] = null;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter beginObject() throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("Object cannot be used as a map key in JSON at path " + getPath());
        }
        if (this.stackSize == this.flattenStackSize && this.scopes[this.stackSize - 1] == 3) {
            this.flattenStackSize = ~this.flattenStackSize;
            return this;
        }
        checkStack();
        LinkedHashTreeMap linkedHashTreeMap = new LinkedHashTreeMap();
        add(linkedHashTreeMap);
        this.stack[this.stackSize] = linkedHashTreeMap;
        pushScope(3);
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter endObject() throws IOException {
        if (peekScope() != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        if (this.stackSize == (~this.flattenStackSize)) {
            this.flattenStackSize = ~this.flattenStackSize;
            return this;
        }
        this.promoteValueToName = false;
        this.stackSize--;
        this.stack[this.stackSize] = null;
        this.pathNames[this.stackSize] = null;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter name(String str) throws IOException {
        Objects.requireNonNull(str, "name == null");
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        if (peekScope() != 3 || this.deferredName != null || this.promoteValueToName) {
            throw new IllegalStateException("Nesting problem.");
        }
        this.deferredName = str;
        this.pathNames[this.stackSize - 1] = str;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(@Nullable String str) throws IOException {
        if (this.promoteValueToName) {
            this.promoteValueToName = false;
            return name(str);
        }
        add(str);
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter nullValue() throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("null cannot be used as a map key in JSON at path " + getPath());
        }
        add(null);
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(boolean z) throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("Boolean cannot be used as a map key in JSON at path " + getPath());
        }
        add(Boolean.valueOf(z));
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(@Nullable Boolean bool) throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("Boolean cannot be used as a map key in JSON at path " + getPath());
        }
        add(bool);
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(double d) throws IOException {
        if (!this.lenient && (Double.isNaN(d) || d == Double.NEGATIVE_INFINITY || d == Double.POSITIVE_INFINITY)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + d);
        }
        if (this.promoteValueToName) {
            this.promoteValueToName = false;
            return name(Double.toString(d));
        }
        add(Double.valueOf(d));
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(long j) throws IOException {
        if (this.promoteValueToName) {
            this.promoteValueToName = false;
            return name(Long.toString(j));
        }
        add(Long.valueOf(j));
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(@Nullable Number number) throws IOException {
        if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer) || (number instanceof Long)) {
            return value(number.longValue());
        }
        if ((number instanceof Float) || (number instanceof Double)) {
            return value(number.doubleValue());
        }
        if (number == null) {
            return nullValue();
        }
        BigDecimal bigDecimal = number instanceof BigDecimal ? (BigDecimal) number : new BigDecimal(number.toString());
        if (this.promoteValueToName) {
            this.promoteValueToName = false;
            return name(bigDecimal.toString());
        }
        add(bigDecimal);
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public BufferedSink valueSink() {
        if (this.promoteValueToName) {
            throw new IllegalStateException("BufferedSink cannot be used as a map key in JSON at path " + getPath());
        }
        if (peekScope() == 9) {
            throw new IllegalStateException("Sink from valueSink() was not closed");
        }
        pushScope(9);
        final Buffer buffer = new Buffer();
        return Okio.buffer(new ForwardingSink(buffer) { // from class: com.squareup.moshi.JsonValueWriter.1
            @Override // okio.ForwardingSink, okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                if (JsonValueWriter.this.peekScope() != 9 || JsonValueWriter.this.stack[JsonValueWriter.this.stackSize] != null) {
                    throw new AssertionError();
                }
                JsonValueWriter.this.stackSize--;
                Object jsonValue = JsonReader.of(buffer).readJsonValue();
                boolean z = JsonValueWriter.this.serializeNulls;
                JsonValueWriter.this.serializeNulls = true;
                try {
                    JsonValueWriter.this.add(jsonValue);
                    JsonValueWriter.this.serializeNulls = z;
                    int[] iArr = JsonValueWriter.this.pathIndices;
                    int i = JsonValueWriter.this.stackSize - 1;
                    iArr[i] = iArr[i] + 1;
                } catch (Throwable th) {
                    JsonValueWriter.this.serializeNulls = z;
                    throw th;
                }
            }
        });
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        int i = this.stackSize;
        if (i > 1 || (i == 1 && this.scopes[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.stackSize = 0;
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JsonValueWriter add(@Nullable Object obj) {
        Object objPut;
        int iPeekScope = peekScope();
        if (this.stackSize == 1) {
            if (iPeekScope != 6) {
                throw new IllegalStateException("JSON must have only one top-level value.");
            }
            this.scopes[this.stackSize - 1] = 7;
            this.stack[this.stackSize - 1] = obj;
        } else if (iPeekScope != 3 || this.deferredName == null) {
            if (iPeekScope != 1) {
                if (iPeekScope == 9) {
                    throw new IllegalStateException("Sink from valueSink() was not closed");
                }
                throw new IllegalStateException("Nesting problem.");
            }
            ((List) this.stack[this.stackSize - 1]).add(obj);
        } else {
            if ((obj != null || this.serializeNulls) && (objPut = ((Map) this.stack[this.stackSize - 1]).put(this.deferredName, obj)) != null) {
                throw new IllegalArgumentException("Map key '" + this.deferredName + "' has multiple values at path " + getPath() + ": " + objPut + " and " + obj);
            }
            this.deferredName = null;
        }
        return this;
    }
}
