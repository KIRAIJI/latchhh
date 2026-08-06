package retrofit2.converter.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import java.io.IOException;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.ByteString;
import retrofit2.Converter;

/* JADX INFO: loaded from: classes2.dex */
final class MoshiResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final ByteString UTF8_BOM = ByteString.decodeHex("EFBBBF");
    private final JsonAdapter<T> adapter;

    MoshiResponseBodyConverter(JsonAdapter<T> jsonAdapter) {
        this.adapter = jsonAdapter;
    }

    @Override // retrofit2.Converter
    public T convert(ResponseBody responseBody) throws IOException {
        BufferedSource source = responseBody.getSource();
        try {
            if (source.rangeEquals(0L, UTF8_BOM)) {
                source.skip(r3.size());
            }
            JsonReader jsonReaderOf = JsonReader.of(source);
            T tFromJson = this.adapter.fromJson(jsonReaderOf);
            if (jsonReaderOf.peek() == JsonReader.Token.END_DOCUMENT) {
                return tFromJson;
            }
            throw new JsonDataException("JSON document was not fully consumed.");
        } finally {
            responseBody.close();
        }
    }
}
