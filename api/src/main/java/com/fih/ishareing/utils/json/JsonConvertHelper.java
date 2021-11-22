package com.fih.ishareing.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.util.Map;

public class JsonConvertHelper {
	private ObjectMapper mapper = new ObjectMapper();

	private static JsonConvertHelper _instance;
	private JsonConvertHelper() {}
	
	public static JsonConvertHelper create() {
		if (null == _instance) {
			_instance = new JsonConvertHelper();
		}
		return _instance;
	}

	public <T> T Deserialize(String jsonString, Class<T> valueType) {
		try {

			T result = mapper.readValue(jsonString, valueType);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

//	public <T> T Deserialize(String jsonString, TypeReference<?> valueTypeRef) {
//		try {
//
//			T result = mapper.readValue(jsonString, valueTypeRef);
//			return result;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public <T> String Seserialize(T value) {

		try {
			return mapper.writeValueAsString(value);
		} catch (Exception ex) {
			return null;
		}
	}

	public <T> String Seserialize(T value, PropertyNamingStrategy s) {

		try {
			mapper.setPropertyNamingStrategy(s);
			return mapper.writeValueAsString(value);
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> Map<String, Object> convertToMap(T value) {

		try {
			return mapper.convertValue(value, Map.class);
		} catch (Exception ex) {
			return null;
		}
	}
}
