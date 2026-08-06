package com.squareup.moshi;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

/* JADX INFO: loaded from: classes.dex */
public abstract class JsonReader implements Closeable {
    boolean failOnUnknown;
    boolean lenient;
    int[] pathIndices;
    String[] pathNames;
    int[] scopes;
    int stackSize;
    private Map<Class<?>, Object> tags;

    public enum Token {
        BEGIN_ARRAY,
        END_ARRAY,
        BEGIN_OBJECT,
        END_OBJECT,
        NAME,
        STRING,
        NUMBER,
        BOOLEAN,
        NULL,
        END_DOCUMENT
    }

    public abstract void beginArray() throws IOException;

    public abstract void beginObject() throws IOException;

    public abstract void endArray() throws IOException;

    public abstract void endObject() throws IOException;

    @CheckReturnValue
    public abstract boolean hasNext() throws IOException;

    public abstract boolean nextBoolean() throws IOException;

    public abstract double nextDouble() throws IOException;

    public abstract int nextInt() throws IOException;

    public abstract long nextLong() throws IOException;

    @CheckReturnValue
    public abstract String nextName() throws IOException;

    @Nullable
    public abstract <T> T nextNull() throws IOException;

    public abstract BufferedSource nextSource() throws IOException;

    public abstract String nextString() throws IOException;

    @CheckReturnValue
    public abstract Token peek() throws IOException;

    @CheckReturnValue
    public abstract JsonReader peekJson();

    public abstract void promoteNameToValue() throws IOException;

    @CheckReturnValue
    public abstract int selectName(Options options) throws IOException;

    @CheckReturnValue
    public abstract int selectString(Options options) throws IOException;

    public abstract void skipName() throws IOException;

    public abstract void skipValue() throws IOException;

    @CheckReturnValue
    public static JsonReader of(BufferedSource bufferedSource) {
        return new JsonUtf8Reader(bufferedSource);
    }

    JsonReader() {
        this.scopes = new int[32];
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
    }

    JsonReader(JsonReader jsonReader) {
        this.stackSize = jsonReader.stackSize;
        this.scopes = (int[]) jsonReader.scopes.clone();
        this.pathNames = (String[]) jsonReader.pathNames.clone();
        this.pathIndices = (int[]) jsonReader.pathIndices.clone();
        this.lenient = jsonReader.lenient;
        this.failOnUnknown = jsonReader.failOnUnknown;
    }

    final void pushScope(int i) {
        int i2 = this.stackSize;
        int[] iArr = this.scopes;
        if (i2 == iArr.length) {
            if (i2 == 256) {
                throw new JsonDataException("Nesting too deep at " + getPath());
            }
            this.scopes = Arrays.copyOf(iArr, iArr.length * 2);
            String[] strArr = this.pathNames;
            this.pathNames = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
            int[] iArr2 = this.pathIndices;
            this.pathIndices = Arrays.copyOf(iArr2, iArr2.length * 2);
        }
        int[] iArr3 = this.scopes;
        int i3 = this.stackSize;
        this.stackSize = i3 + 1;
        iArr3[i3] = i;
    }

    final JsonEncodingException syntaxError(String str) throws JsonEncodingException {
        throw new JsonEncodingException(str + " at path " + getPath());
    }

    final JsonDataException typeMismatch(@Nullable Object obj, Object obj2) {
        if (obj == null) {
            return new JsonDataException("Expected " + obj2 + " but was null at path " + getPath());
        }
        return new JsonDataException("Expected " + obj2 + " but was " + obj + ", a " + obj.getClass().getName() + ", at path " + getPath());
    }

    public final void setLenient(boolean z) {
        this.lenient = z;
    }

    @CheckReturnValue
    public final boolean isLenient() {
        return this.lenient;
    }

    public final void setFailOnUnknown(boolean z) {
        this.failOnUnknown = z;
    }

    @CheckReturnValue
    public final boolean failOnUnknown() {
        return this.failOnUnknown;
    }

    /* JADX INFO: renamed from: com.squareup.moshi.JsonReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$squareup$moshi$JsonReader$Token;

        static {
            int[] iArr = new int[Token.values().length];
            $SwitchMap$com$squareup$moshi$JsonReader$Token = iArr;
            try {
                iArr[Token.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$squareup$moshi$JsonReader$Token[Token.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$squareup$moshi$JsonReader$Token[Token.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$squareup$moshi$JsonReader$Token[Token.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$squareup$moshi$JsonReader$Token[Token.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$squareup$moshi$JsonReader$Token[Token.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Nullable
    public final Object readJsonValue() throws IOException {
        switch (AnonymousClass1.$SwitchMap$com$squareup$moshi$JsonReader$Token[peek().ordinal()]) {
            case 1:
                ArrayList arrayList = new ArrayList();
                beginArray();
                while (hasNext()) {
                    arrayList.add(readJsonValue());
                }
                endArray();
                return arrayList;
            case 2:
                LinkedHashTreeMap linkedHashTreeMap = new LinkedHashTreeMap();
                beginObject();
                while (hasNext()) {
                    String strNextName = nextName();
                    Object jsonValue = readJsonValue();
                    Object objPut = linkedHashTreeMap.put(strNextName, jsonValue);
                    if (objPut != null) {
                        throw new JsonDataException("Map key '" + strNextName + "' has multiple values at path " + getPath() + ": " + objPut + " and " + jsonValue);
                    }
                }
                endObject();
                return linkedHashTreeMap;
            case 3:
                return nextString();
            case 4:
                return Double.valueOf(nextDouble());
            case 5:
                return Boolean.valueOf(nextBoolean());
            case 6:
                return nextNull();
            default:
                throw new IllegalStateException("Expected a value but was " + peek() + " at path " + getPath());
        }
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

    public static final class Options {
        final okio.Options doubleQuoteSuffix;
        final String[] strings;

        private Options(String[] strArr, okio.Options options) {
            this.strings = strArr;
            this.doubleQuoteSuffix = options;
        }

        public List<String> strings() {
            return Collections.unmodifiableList(Arrays.asList(this.strings));
        }

        @CheckReturnValue
        public static Options of(String... strArr) {
            try {
                ByteString[] byteStringArr = new ByteString[strArr.length];
                Buffer buffer = new Buffer();
                for (int i = 0; i < strArr.length; i++) {
                    JsonUtf8Writer.string(buffer, strArr[i]);
                    buffer.readByte();
                    byteStringArr[i] = buffer.readByteString();
                }
                return new Options((String[]) strArr.clone(), okio.Options.of(byteStringArr));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }
}
