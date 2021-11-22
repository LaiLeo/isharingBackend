package com.fih.ishareing.utils.json;

import java.util.Map;

public interface JsonConverter {

	<T> String serialize(T value);

	String serialize(Map<String, String> values);

	<T> T deserialize(String jsonString, Class<T> valueType);

	<T> T deserialize(Object fromValue, Class<T> valueType);

	Object toJsonObject(String json);
}
