package com.fih.ishareing.repository.criteria;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil {

	public static Class<?> getGenricTypeOfField(Field field) {
		Type genericType = field.getGenericType();
		if (genericType instanceof ParameterizedType) {
			Class<?> clazz = (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
			return clazz;
		} else
			return (Class<?>) genericType;
	}

	public static Annotation getAnnotationByType(Field field, Class<Annotation> clazz) {

		Annotation[] annotations = field.getAnnotationsByType(clazz);
		if (annotations.length > 0) {
			return annotations[0];
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isClassHasAnnotations(Class clazz, Class... annotationClasses) {
		boolean result = false;
		for (Class annotation : annotationClasses) {
			if (clazz.getAnnotationsByType(annotation).length > 0) {
				result = true;
				break;
			}
		}
		return result;
	}

}
