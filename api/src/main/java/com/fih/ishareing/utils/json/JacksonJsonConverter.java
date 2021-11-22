package com.fih.ishareing.utils.json;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonJsonConverter implements JsonConverter {
	private ObjectMapper mapper = new ObjectMapper();

	public static JacksonJsonConverter create() {
		return new JacksonJsonConverter();
	}

	@Override
	public <T> T deserialize(String jsonString, Class<T> valueType) {
		try {

			T result = mapper.readValue(jsonString, valueType);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public <T> T deserialize(Object fromValue, Class<T> valueType) {
		T result = mapper.convertValue(fromValue, valueType);
		return result;
	}

	@Override
	public <T> String serialize(T value) {

		try {
			return mapper.writeValueAsString(value);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public String serialize(Map<String, String> values) {
		String json;
		try {
			json = mapper.writeValueAsString(values);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public <T> String serialize(T value, PropertyNamingStrategy s) {

		try {
			mapper.setPropertyNamingStrategy(s);
			return mapper.writeValueAsString(value);
		} catch (Exception ex) {
			return null;
		}
	}

	public String findValueAsString(String json, String property) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(json);

			if (node.has(property)) {
				return node.get(property).asText();
			}

			return null;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String removeProperty(String json, String fieldName) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(json);

			((ObjectNode) node).remove(fieldName);

			return node.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String addPropertys(String json, Map<String, String> values) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			final JsonNode node = mapper.readTree(json);

			values.forEach((k, v) -> {
				((ObjectNode) node).put(k, v);
			});

			return node.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String addProperty(String json, String key, String value) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(json);

			((ObjectNode) node).put(key, value);

			return node.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> T addProperty(String json, String key, String value, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(json);

			((ObjectNode) node).put(key, value);

			return mapper.treeToValue(node, valueType);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> Map<String, Object> convertToMap(T value) {

		try {
			return mapper.convertValue(value, Map.class);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public Object toJsonObject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(json);
			return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
