package com.squareup.moshi;

import com.squareup.moshi.JsonAdapter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
final class MapJsonAdapter<K, V> extends JsonAdapter<Map<K, V>> {
    public static final JsonAdapter.Factory FACTORY = new JsonAdapter.Factory() { // from class: com.squareup.moshi.MapJsonAdapter.1
        @Override // com.squareup.moshi.JsonAdapter.Factory
        @Nullable
        public JsonAdapter<?> create(Type type, Set<? extends Annotation> set, Moshi moshi) {
            Class<?> rawType;
            if (!set.isEmpty() || (rawType = Types.getRawType(type)) != Map.class) {
                return null;
            }
            Type[] typeArrMapKeyAndValueTypes = Types.mapKeyAndValueTypes(type, rawType);
            return new MapJsonAdapter(moshi, typeArrMapKeyAndValueTypes[0], typeArrMapKeyAndValueTypes[1]).nullSafe();
        }
    };
    private final JsonAdapter<K> keyAdapter;
    private final JsonAdapter<V> valueAdapter;

    MapJsonAdapter(Moshi moshi, Type type, Type type2) {
        this.keyAdapter = moshi.adapter(type);
        this.valueAdapter = moshi.adapter(type2);
    }

    @Override // com.squareup.moshi.JsonAdapter
    public void toJson(JsonWriter jsonWriter, Map<K, V> map) throws IOException {
        jsonWriter.beginObject();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getKey() == null) {
                throw new JsonDataException("Map key is null at " + jsonWriter.getPath());
            }
            jsonWriter.promoteValueToName();
            this.keyAdapter.toJson(jsonWriter, entry.getKey());
            this.valueAdapter.toJson(jsonWriter, entry.getValue());
        }
        jsonWriter.endObject();
    }

    @Override // com.squareup.moshi.JsonAdapter
    public Map<K, V> fromJson(JsonReader jsonReader) throws IOException {
        LinkedHashTreeMap linkedHashTreeMap = new LinkedHashTreeMap();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            jsonReader.promoteNameToValue();
            K kFromJson = this.keyAdapter.fromJson(jsonReader);
            V vFromJson = this.valueAdapter.fromJson(jsonReader);
            V vPut = linkedHashTreeMap.put(kFromJson, vFromJson);
            if (vPut != null) {
                throw new JsonDataException("Map key '" + kFromJson + "' has multiple values at path " + jsonReader.getPath() + ": " + vPut + " and " + vFromJson);
            }
        }
        jsonReader.endObject();
        return linkedHashTreeMap;
    }

    public String toString() {
        return "JsonAdapter(" + this.keyAdapter + "=" + this.valueAdapter + ")";
    }
}
