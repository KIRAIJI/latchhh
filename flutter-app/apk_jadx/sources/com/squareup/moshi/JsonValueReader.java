package com.squareup.moshi;

import com.squareup.moshi.JsonReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import okio.Buffer;
import okio.BufferedSource;

/* JADX INFO: loaded from: classes.dex */
final class JsonValueReader extends JsonReader {
    private static final Object JSON_READER_CLOSED = new Object();
    private Object[] stack;

    JsonValueReader(Object obj) {
        this.scopes[this.stackSize] = 7;
        Object[] objArr = new Object[32];
        this.stack = objArr;
        int i = this.stackSize;
        this.stackSize = i + 1;
        objArr[i] = obj;
    }

    JsonValueReader(JsonValueReader jsonValueReader) {
        super(jsonValueReader);
        this.stack = (Object[]) jsonValueReader.stack.clone();
        for (int i = 0; i < this.stackSize; i++) {
            Object[] objArr = this.stack;
            if (objArr[i] instanceof JsonIterator) {
                objArr[i] = ((JsonIterator) objArr[i]).m378clone();
            }
        }
    }

    @Override // com.squareup.moshi.JsonReader
    public void beginArray() throws IOException {
        List list = (List) require(List.class, JsonReader.Token.BEGIN_ARRAY);
        JsonIterator jsonIterator = new JsonIterator(JsonReader.Token.END_ARRAY, list.toArray(new Object[list.size()]), 0);
        this.stack[this.stackSize - 1] = jsonIterator;
        this.scopes[this.stackSize - 1] = 1;
        this.pathIndices[this.stackSize - 1] = 0;
        if (jsonIterator.hasNext()) {
            push(jsonIterator.next());
        }
    }

    @Override // com.squareup.moshi.JsonReader
    public void endArray() throws IOException {
        JsonIterator jsonIterator = (JsonIterator) require(JsonIterator.class, JsonReader.Token.END_ARRAY);
        if (jsonIterator.endToken != JsonReader.Token.END_ARRAY || jsonIterator.hasNext()) {
            throw typeMismatch(jsonIterator, JsonReader.Token.END_ARRAY);
        }
        remove();
    }

    @Override // com.squareup.moshi.JsonReader
    public void beginObject() throws IOException {
        Map map = (Map) require(Map.class, JsonReader.Token.BEGIN_OBJECT);
        JsonIterator jsonIterator = new JsonIterator(JsonReader.Token.END_OBJECT, map.entrySet().toArray(new Object[map.size()]), 0);
        this.stack[this.stackSize - 1] = jsonIterator;
        this.scopes[this.stackSize - 1] = 3;
        if (jsonIterator.hasNext()) {
            push(jsonIterator.next());
        }
    }

    @Override // com.squareup.moshi.JsonReader
    public void endObject() throws IOException {
        JsonIterator jsonIterator = (JsonIterator) require(JsonIterator.class, JsonReader.Token.END_OBJECT);
        if (jsonIterator.endToken != JsonReader.Token.END_OBJECT || jsonIterator.hasNext()) {
            throw typeMismatch(jsonIterator, JsonReader.Token.END_OBJECT);
        }
        this.pathNames[this.stackSize - 1] = null;
        remove();
    }

    @Override // com.squareup.moshi.JsonReader
    public boolean hasNext() throws IOException {
        if (this.stackSize == 0) {
            return false;
        }
        Object obj = this.stack[this.stackSize - 1];
        return !(obj instanceof Iterator) || ((Iterator) obj).hasNext();
    }

    @Override // com.squareup.moshi.JsonReader
    public JsonReader.Token peek() throws IOException {
        if (this.stackSize == 0) {
            return JsonReader.Token.END_DOCUMENT;
        }
        Object obj = this.stack[this.stackSize - 1];
        if (obj instanceof JsonIterator) {
            return ((JsonIterator) obj).endToken;
        }
        if (obj instanceof List) {
            return JsonReader.Token.BEGIN_ARRAY;
        }
        if (obj instanceof Map) {
            return JsonReader.Token.BEGIN_OBJECT;
        }
        if (obj instanceof Map.Entry) {
            return JsonReader.Token.NAME;
        }
        if (obj instanceof String) {
            return JsonReader.Token.STRING;
        }
        if (obj instanceof Boolean) {
            return JsonReader.Token.BOOLEAN;
        }
        if (obj instanceof Number) {
            return JsonReader.Token.NUMBER;
        }
        if (obj == null) {
            return JsonReader.Token.NULL;
        }
        if (obj == JSON_READER_CLOSED) {
            throw new IllegalStateException("JsonReader is closed");
        }
        throw typeMismatch(obj, "a JSON value");
    }

    @Override // com.squareup.moshi.JsonReader
    public String nextName() throws IOException {
        Map.Entry<?, ?> entry = (Map.Entry) require(Map.Entry.class, JsonReader.Token.NAME);
        String strStringKey = stringKey(entry);
        this.stack[this.stackSize - 1] = entry.getValue();
        this.pathNames[this.stackSize - 2] = strStringKey;
        return strStringKey;
    }

    @Override // com.squareup.moshi.JsonReader
    public int selectName(JsonReader.Options options) throws IOException {
        Map.Entry<?, ?> entry = (Map.Entry) require(Map.Entry.class, JsonReader.Token.NAME);
        String strStringKey = stringKey(entry);
        int length = options.strings.length;
        for (int i = 0; i < length; i++) {
            if (options.strings[i].equals(strStringKey)) {
                this.stack[this.stackSize - 1] = entry.getValue();
                this.pathNames[this.stackSize - 2] = strStringKey;
                return i;
            }
        }
        return -1;
    }

    @Override // com.squareup.moshi.JsonReader
    public void skipName() throws IOException {
        if (this.failOnUnknown) {
            JsonReader.Token tokenPeek = peek();
            nextName();
            throw new JsonDataException("Cannot skip unexpected " + tokenPeek + " at " + getPath());
        }
        this.stack[this.stackSize - 1] = ((Map.Entry) require(Map.Entry.class, JsonReader.Token.NAME)).getValue();
        this.pathNames[this.stackSize - 2] = "null";
    }

    @Override // com.squareup.moshi.JsonReader
    public String nextString() throws IOException {
        Object obj = this.stackSize != 0 ? this.stack[this.stackSize - 1] : null;
        if (obj instanceof String) {
            remove();
            return (String) obj;
        }
        if (obj instanceof Number) {
            remove();
            return obj.toString();
        }
        if (obj == JSON_READER_CLOSED) {
            throw new IllegalStateException("JsonReader is closed");
        }
        throw typeMismatch(obj, JsonReader.Token.STRING);
    }

    @Override // com.squareup.moshi.JsonReader
    public int selectString(JsonReader.Options options) throws IOException {
        Object obj = this.stackSize != 0 ? this.stack[this.stackSize - 1] : null;
        if (!(obj instanceof String)) {
            if (obj != JSON_READER_CLOSED) {
                return -1;
            }
            throw new IllegalStateException("JsonReader is closed");
        }
        String str = (String) obj;
        int length = options.strings.length;
        for (int i = 0; i < length; i++) {
            if (options.strings[i].equals(str)) {
                remove();
                return i;
            }
        }
        return -1;
    }

    @Override // com.squareup.moshi.JsonReader
    public boolean nextBoolean() throws IOException {
        Boolean bool = (Boolean) require(Boolean.class, JsonReader.Token.BOOLEAN);
        remove();
        return bool.booleanValue();
    }

    @Override // com.squareup.moshi.JsonReader
    @Nullable
    public <T> T nextNull() throws IOException {
        require(Void.class, JsonReader.Token.NULL);
        remove();
        return null;
    }

    @Override // com.squareup.moshi.JsonReader
    public double nextDouble() throws IOException {
        double dDoubleValue;
        Object objRequire = require(Object.class, JsonReader.Token.NUMBER);
        if (objRequire instanceof Number) {
            dDoubleValue = ((Number) objRequire).doubleValue();
        } else if (objRequire instanceof String) {
            try {
                dDoubleValue = Double.parseDouble((String) objRequire);
            } catch (NumberFormatException unused) {
                throw typeMismatch(objRequire, JsonReader.Token.NUMBER);
            }
        } else {
            throw typeMismatch(objRequire, JsonReader.Token.NUMBER);
        }
        if (!this.lenient && (Double.isNaN(dDoubleValue) || Double.isInfinite(dDoubleValue))) {
            throw new JsonEncodingException("JSON forbids NaN and infinities: " + dDoubleValue + " at path " + getPath());
        }
        remove();
        return dDoubleValue;
    }

    @Override // com.squareup.moshi.JsonReader
    public long nextLong() throws IOException {
        long jLongValueExact;
        Object objRequire = require(Object.class, JsonReader.Token.NUMBER);
        if (objRequire instanceof Number) {
            jLongValueExact = ((Number) objRequire).longValue();
        } else if (objRequire instanceof String) {
            try {
                try {
                    jLongValueExact = Long.parseLong((String) objRequire);
                } catch (NumberFormatException unused) {
                    throw typeMismatch(objRequire, JsonReader.Token.NUMBER);
                }
            } catch (NumberFormatException unused2) {
                jLongValueExact = new BigDecimal((String) objRequire).longValueExact();
            }
        } else {
            throw typeMismatch(objRequire, JsonReader.Token.NUMBER);
        }
        remove();
        return jLongValueExact;
    }

    @Override // com.squareup.moshi.JsonReader
    public int nextInt() throws IOException {
        int iIntValueExact;
        Object objRequire = require(Object.class, JsonReader.Token.NUMBER);
        if (objRequire instanceof Number) {
            iIntValueExact = ((Number) objRequire).intValue();
        } else if (objRequire instanceof String) {
            try {
                try {
                    iIntValueExact = Integer.parseInt((String) objRequire);
                } catch (NumberFormatException unused) {
                    throw typeMismatch(objRequire, JsonReader.Token.NUMBER);
                }
            } catch (NumberFormatException unused2) {
                iIntValueExact = new BigDecimal((String) objRequire).intValueExact();
            }
        } else {
            throw typeMismatch(objRequire, JsonReader.Token.NUMBER);
        }
        remove();
        return iIntValueExact;
    }

    @Override // com.squareup.moshi.JsonReader
    public void skipValue() throws IOException {
        if (this.failOnUnknown) {
            throw new JsonDataException("Cannot skip unexpected " + peek() + " at " + getPath());
        }
        if (this.stackSize > 1) {
            this.pathNames[this.stackSize - 2] = "null";
        }
        Object obj = this.stackSize != 0 ? this.stack[this.stackSize - 1] : null;
        if (obj instanceof JsonIterator) {
            throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
        }
        if (obj instanceof Map.Entry) {
            this.stack[this.stackSize - 1] = ((Map.Entry) this.stack[this.stackSize - 1]).getValue();
            return;
        }
        if (this.stackSize > 0) {
            remove();
            return;
        }
        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
    }

    @Override // com.squareup.moshi.JsonReader
    public BufferedSource nextSource() throws IOException {
        Object jsonValue = readJsonValue();
        Buffer buffer = new Buffer();
        JsonWriter jsonWriterOf = JsonWriter.of(buffer);
        try {
            jsonWriterOf.jsonValue(jsonValue);
            if (jsonWriterOf != null) {
                jsonWriterOf.close();
            }
            return buffer;
        } catch (Throwable th) {
            if (jsonWriterOf != null) {
                try {
                    jsonWriterOf.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // com.squareup.moshi.JsonReader
    public JsonReader peekJson() {
        return new JsonValueReader(this);
    }

    @Override // com.squareup.moshi.JsonReader
    public void promoteNameToValue() throws IOException {
        if (hasNext()) {
            push(nextName());
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Arrays.fill(this.stack, 0, this.stackSize, (Object) null);
        this.stack[0] = JSON_READER_CLOSED;
        this.scopes[0] = 8;
        this.stackSize = 1;
    }

    private void push(Object obj) {
        if (this.stackSize == this.stack.length) {
            if (this.stackSize == 256) {
                throw new JsonDataException("Nesting too deep at " + getPath());
            }
            this.scopes = Arrays.copyOf(this.scopes, this.scopes.length * 2);
            this.pathNames = (String[]) Arrays.copyOf(this.pathNames, this.pathNames.length * 2);
            this.pathIndices = Arrays.copyOf(this.pathIndices, this.pathIndices.length * 2);
            Object[] objArr = this.stack;
            this.stack = Arrays.copyOf(objArr, objArr.length * 2);
        }
        Object[] objArr2 = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        objArr2[i] = obj;
    }

    @Nullable
    private <T> T require(Class<T> cls, JsonReader.Token token) throws IOException {
        Object obj = this.stackSize != 0 ? this.stack[this.stackSize - 1] : null;
        if (cls.isInstance(obj)) {
            return cls.cast(obj);
        }
        if (obj == null && token == JsonReader.Token.NULL) {
            return null;
        }
        if (obj == JSON_READER_CLOSED) {
            throw new IllegalStateException("JsonReader is closed");
        }
        throw typeMismatch(obj, token);
    }

    private String stringKey(Map.Entry<?, ?> entry) {
        Object key = entry.getKey();
        if (key instanceof String) {
            return (String) key;
        }
        throw typeMismatch(key, JsonReader.Token.NAME);
    }

    private void remove() {
        this.stackSize--;
        this.stack[this.stackSize] = null;
        this.scopes[this.stackSize] = 0;
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            Object obj = this.stack[this.stackSize - 1];
            if (obj instanceof Iterator) {
                Iterator it = (Iterator) obj;
                if (it.hasNext()) {
                    push(it.next());
                }
            }
        }
    }

    static final class JsonIterator implements Iterator<Object>, Cloneable {
        final Object[] array;
        final JsonReader.Token endToken;
        int next;

        JsonIterator(JsonReader.Token token, Object[] objArr, int i) {
            this.endToken = token;
            this.array = objArr;
            this.next = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next < this.array.length;
        }

        @Override // java.util.Iterator
        public Object next() {
            Object[] objArr = this.array;
            int i = this.next;
            this.next = i + 1;
            return objArr[i];
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public JsonIterator m378clone() {
            return new JsonIterator(this.endToken, this.array, this.next);
        }
    }
}
