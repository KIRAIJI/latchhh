package com.squareup.moshi;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Timeout;

/* JADX INFO: loaded from: classes.dex */
final class JsonUtf8Writer extends JsonWriter {
    private static final String[] REPLACEMENT_CHARS = new String[128];
    private String deferredName;
    private String separator = ":";
    private final BufferedSink sink;

    static {
        for (int i = 0; i <= 31; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = REPLACEMENT_CHARS;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
    }

    JsonUtf8Writer(BufferedSink bufferedSink) {
        Objects.requireNonNull(bufferedSink, "sink == null");
        this.sink = bufferedSink;
        pushScope(6);
    }

    @Override // com.squareup.moshi.JsonWriter
    public void setIndent(String str) {
        super.setIndent(str);
        this.separator = !str.isEmpty() ? ": " : ":";
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter beginArray() throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("Array cannot be used as a map key in JSON at path " + getPath());
        }
        writeDeferredName();
        return open(1, 2, '[');
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter endArray() throws IOException {
        return close(1, 2, ']');
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter beginObject() throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("Object cannot be used as a map key in JSON at path " + getPath());
        }
        writeDeferredName();
        return open(3, 5, '{');
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter endObject() throws IOException {
        this.promoteValueToName = false;
        return close(3, 5, '}');
    }

    private JsonWriter open(int i, int i2, char c) throws IOException {
        if (this.stackSize == this.flattenStackSize && (this.scopes[this.stackSize - 1] == i || this.scopes[this.stackSize - 1] == i2)) {
            this.flattenStackSize = ~this.flattenStackSize;
            return this;
        }
        beforeValue();
        checkStack();
        pushScope(i);
        this.pathIndices[this.stackSize - 1] = 0;
        this.sink.writeByte(c);
        return this;
    }

    private JsonWriter close(int i, int i2, char c) throws IOException {
        int iPeekScope = peekScope();
        if (iPeekScope != i2 && iPeekScope != i) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        if (this.stackSize == (~this.flattenStackSize)) {
            this.flattenStackSize = ~this.flattenStackSize;
            return this;
        }
        this.stackSize--;
        this.pathNames[this.stackSize] = null;
        int[] iArr = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr[i3] = iArr[i3] + 1;
        if (iPeekScope == i2) {
            newline();
        }
        this.sink.writeByte(c);
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter name(String str) throws IOException {
        Objects.requireNonNull(str, "name == null");
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        int iPeekScope = peekScope();
        if ((iPeekScope != 3 && iPeekScope != 5) || this.deferredName != null || this.promoteValueToName) {
            throw new IllegalStateException("Nesting problem.");
        }
        this.deferredName = str;
        this.pathNames[this.stackSize - 1] = str;
        return this;
    }

    private void writeDeferredName() throws IOException {
        if (this.deferredName != null) {
            beforeName();
            string(this.sink, this.deferredName);
            this.deferredName = null;
        }
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(String str) throws IOException {
        if (str == null) {
            return nullValue();
        }
        if (this.promoteValueToName) {
            this.promoteValueToName = false;
            return name(str);
        }
        writeDeferredName();
        beforeValue();
        string(this.sink, str);
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
        if (this.deferredName != null) {
            if (this.serializeNulls) {
                writeDeferredName();
            } else {
                this.deferredName = null;
                return this;
            }
        }
        beforeValue();
        this.sink.writeUtf8("null");
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
        writeDeferredName();
        beforeValue();
        this.sink.writeUtf8(z ? "true" : "false");
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(Boolean bool) throws IOException {
        if (bool == null) {
            return nullValue();
        }
        return value(bool.booleanValue());
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(double d) throws IOException {
        if (!this.lenient && (Double.isNaN(d) || Double.isInfinite(d))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + d);
        }
        if (this.promoteValueToName) {
            this.promoteValueToName = false;
            return name(Double.toString(d));
        }
        writeDeferredName();
        beforeValue();
        this.sink.writeUtf8(Double.toString(d));
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
        writeDeferredName();
        beforeValue();
        this.sink.writeUtf8(Long.toString(j));
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public JsonWriter value(@Nullable Number number) throws IOException {
        if (number == null) {
            return nullValue();
        }
        String string = number.toString();
        if (!this.lenient && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
        }
        if (this.promoteValueToName) {
            this.promoteValueToName = false;
            return name(string);
        }
        writeDeferredName();
        beforeValue();
        this.sink.writeUtf8(string);
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    @Override // com.squareup.moshi.JsonWriter
    public BufferedSink valueSink() throws IOException {
        if (this.promoteValueToName) {
            throw new IllegalStateException("BufferedSink cannot be used as a map key in JSON at path " + getPath());
        }
        writeDeferredName();
        beforeValue();
        pushScope(9);
        return Okio.buffer(new Sink() { // from class: com.squareup.moshi.JsonUtf8Writer.1
            @Override // okio.Sink
            public void write(Buffer buffer, long j) throws IOException {
                JsonUtf8Writer.this.sink.write(buffer, j);
            }

            @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                if (JsonUtf8Writer.this.peekScope() != 9) {
                    throw new AssertionError();
                }
                JsonUtf8Writer jsonUtf8Writer = JsonUtf8Writer.this;
                jsonUtf8Writer.stackSize--;
                int[] iArr = JsonUtf8Writer.this.pathIndices;
                int i = JsonUtf8Writer.this.stackSize - 1;
                iArr[i] = iArr[i] + 1;
            }

            @Override // okio.Sink, java.io.Flushable
            public void flush() throws IOException {
                JsonUtf8Writer.this.sink.flush();
            }

            @Override // okio.Sink
            /* JADX INFO: renamed from: timeout */
            public Timeout getTimeout() {
                return Timeout.NONE;
            }
        });
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.sink.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.sink.close();
        int i = this.stackSize;
        if (i > 1 || (i == 1 && this.scopes[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.stackSize = 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void string(okio.BufferedSink r7, java.lang.String r8) throws java.io.IOException {
        /*
            java.lang.String[] r0 = com.squareup.moshi.JsonUtf8Writer.REPLACEMENT_CHARS
            r1 = 34
            r7.writeByte(r1)
            int r2 = r8.length()
            r3 = 0
            r4 = r3
        Ld:
            if (r3 >= r2) goto L36
            char r5 = r8.charAt(r3)
            r6 = 128(0x80, float:1.794E-43)
            if (r5 >= r6) goto L1c
            r5 = r0[r5]
            if (r5 != 0) goto L29
            goto L33
        L1c:
            r6 = 8232(0x2028, float:1.1535E-41)
            if (r5 != r6) goto L23
            java.lang.String r5 = "\\u2028"
            goto L29
        L23:
            r6 = 8233(0x2029, float:1.1537E-41)
            if (r5 != r6) goto L33
            java.lang.String r5 = "\\u2029"
        L29:
            if (r4 >= r3) goto L2e
            r7.writeUtf8(r8, r4, r3)
        L2e:
            r7.writeUtf8(r5)
            int r4 = r3 + 1
        L33:
            int r3 = r3 + 1
            goto Ld
        L36:
            if (r4 >= r2) goto L3b
            r7.writeUtf8(r8, r4, r2)
        L3b:
            r7.writeByte(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.moshi.JsonUtf8Writer.string(okio.BufferedSink, java.lang.String):void");
    }

    private void newline() throws IOException {
        if (this.indent == null) {
            return;
        }
        this.sink.writeByte(10);
        int i = this.stackSize;
        for (int i2 = 1; i2 < i; i2++) {
            this.sink.writeUtf8(this.indent);
        }
    }

    private void beforeName() throws IOException {
        int iPeekScope = peekScope();
        if (iPeekScope == 5) {
            this.sink.writeByte(44);
        } else if (iPeekScope != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        newline();
        replaceTop(4);
    }

    private void beforeValue() throws IOException {
        int iPeekScope = peekScope();
        int i = 7;
        if (iPeekScope == 1) {
            newline();
            i = 2;
        } else if (iPeekScope == 2) {
            this.sink.writeByte(44);
            newline();
            i = 2;
        } else if (iPeekScope == 4) {
            i = 5;
            this.sink.writeUtf8(this.separator);
        } else {
            if (iPeekScope == 9) {
                throw new IllegalStateException("Sink from valueSink() was not closed");
            }
            if (iPeekScope != 6) {
                if (iPeekScope == 7) {
                    if (!this.lenient) {
                        throw new IllegalStateException("JSON must have only one top-level value.");
                    }
                } else {
                    throw new IllegalStateException("Nesting problem.");
                }
            }
        }
        replaceTop(i);
    }
}
