package com.fih.ishareing.repository.criteria;

import java.util.HashSet;
import java.util.Set;

import com.fih.ishareing.controller.base.error.exception.ParameterException;
import com.fih.ishareing.utils.date.DateUtils;

import org.apache.commons.lang3.StringUtils;

public class StringToPrimitypeUtil {
	public static Object toObject(String value) {
		if ("null".equals(value)) {
			return null;
		}

		if (isInteger(value)) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				throw new ParameterException("long type is not accepted : " + value);
			}
		}
		if (isDouble(value)) {
			return Double.parseDouble(value);
		}

		if ("true".equals(value.toLowerCase().trim()) || "false".equals(value.toLowerCase().trim())) {
			return Boolean.valueOf(value);
		}
		if (DateUtils.isVaidDate(value)) {
			return DateUtils.toDate(value);
		}

		return value;
	}

	public static Object toObject(Class<?> clazz, String value) {
		if (Boolean.class == clazz || Boolean.TYPE == clazz)
			return Boolean.parseBoolean(value.toLowerCase().trim());
		if (Byte.class == clazz || Byte.TYPE == clazz)
			return Byte.parseByte(value.trim());
		if (Short.class == clazz || Short.TYPE == clazz)
			return Short.parseShort(value.trim());
		if (Integer.class == clazz || Integer.TYPE == clazz)
			return Integer.parseInt(value.trim());
		if (Long.class == clazz || Long.TYPE == clazz)
			return Long.parseLong(value.trim());
		if (Float.class == clazz || Float.TYPE == clazz)
			return Float.parseFloat(value.trim());
		if (Double.class == clazz || Double.TYPE == clazz)
			return Double.parseDouble(value.trim());
		if (DateUtils.isVaidDate(value))
			return DateUtils.toDate(value);
		return value;
	}

	public static boolean isSplitByCommaString(String value) {
		return value.indexOf(",") > -1 || value.startsWith("[");
	}

	public static Object[] toObjects(String value) {
		if (StringUtils.isNotEmpty(value)) {
			if (value.indexOf("[") != -1) {
				value = value.substring(1);
			}
			if (value.indexOf("]") != -1) {
				value = value.substring(0, value.length() - 1);
			}
			Set<Object> objs = new HashSet<Object>();
			String[] split = value.split(",");
			for (String str : split) {
				objs.add(toObject(str.trim()));
			}
			return objs.stream().toArray(Object[]::new);
		}
		return new Object[] {};
	}

	public static Object[] toObjects(Class<?> clazz, String value) {
		if (StringUtils.isNotEmpty(value)) {
			if (value.indexOf("[") != -1) {
				value = value.substring(1);
			}
			if (value.indexOf("]") != -1) {
				value = value.substring(0, value.length() - 1);
			}
			Set<Object> objs = new HashSet<Object>();
			String[] split = value.split(",");
			for (String str : split) {
				objs.add(toObject(clazz, str.trim()));
			}
			return objs.stream().toArray(Object[]::new);
		}
		return new Object[] {};
	}

	public static boolean isInteger(String str) {
		return str.matches("-?\\d+"); // match a number with optional '-' and
										// decimal.
	}

	public static boolean isDouble(String str) {
		return str.matches("-?[0-9]{1,13}(\\.[0-9]*)?");
	}

	public static boolean isBoolean(String str) {
		return "true".equals(str.toLowerCase().trim()) || "false".equals(str.toLowerCase().trim());
	}

}
