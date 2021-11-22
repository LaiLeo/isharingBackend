package com.fih.ishareing.repository.criteria;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;
import com.fih.ishareing.repository.criteria.annotations.QueryConditions;
import com.google.common.base.CaseFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryConditionUtils {

	private static final Logger logger = LoggerFactory.getLogger(QueryConditionUtils.class);

	public static <T> Map<String, String> getSortColumn(Class<T> clazz) {
		Map<String, String> values = new HashMap<>();
		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			QueryCondition value = f.getAnnotation(QueryCondition.class);
			if (value != null) {
				if (value.sortable() && StringUtils.isNotBlank(value.databaseField())) {
					if (StringUtils.isNotBlank(value.field())) {
						values.put(value.field(), value.databaseField());
					} else {
						values.put(f.getName(), value.databaseField());
					}
				}
			}

			{
				QueryConditions v = f.getAnnotation(QueryConditions.class);
				if (v != null) {
					for (QueryCondition condition : v.value()) {
						if (condition != null) {
							if (condition.sortable() && StringUtils.isNotBlank(condition.databaseField())) {
								if (StringUtils.isNotBlank(condition.field())) {
									values.put(condition.field(), condition.databaseField());
								} else {
									values.put(f.getName(), condition.databaseField());
								}
							}
						}
					}
				}
			}
		}

		Method[] ms = clazz.getMethods();
		for (Method m : ms) {
			{
				QueryCondition condition = m.getAnnotation(QueryCondition.class);
				if (condition != null) {

					if (condition.sortable() && StringUtils.isNotBlank(condition.databaseField())) {
						if (StringUtils.isNotBlank(condition.field())) {
							values.put(condition.field(), condition.databaseField());
						} else {
							String methodName = m.getName();
							if (methodName.startsWith("get")) {
								String field = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
										methodName.substring("get".length()));
								values.put(field, condition.databaseField());
							} else {
								logger.warn("Not support sortable, methodName:{}", methodName);
							}
						}
					}
				}
			}

			{
				QueryConditions v = m.getAnnotation(QueryConditions.class);
				if (v != null) {
					for (QueryCondition condition : v.value()) {
						if (condition != null) {
							if (condition.sortable() && StringUtils.isNotBlank(condition.databaseField())) {
								if (StringUtils.isNotBlank(condition.field())) {
									values.put(condition.field(), condition.databaseField());
								} else {
									String methodName = m.getName();
									if (methodName.startsWith("get")) {
										String field = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
												methodName.substring("get".length()));
										values.put(field, condition.databaseField());
									} else {
										logger.warn("Not support sortable, methodName:{}", methodName);
									}
								}
							}
						}
					}
				}
			}
		}

		return values;
	}

	public static <T> Map<String, String> getSearchColumn(Class<T> clazz) {
		Map<String, String> values = new HashMap<>();
		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			{
				QueryCondition condition = f.getAnnotation(QueryCondition.class);
				if (condition != null) {
					if (condition.searchable() && StringUtils.isNotBlank(condition.databaseField())) {
						if (StringUtils.isNotBlank(condition.field())) {
							values.put(condition.field(), condition.databaseField());
						} else {
							values.put(f.getName(), condition.databaseField());
						}
					}
				}
			}

			{
				QueryConditions v = f.getAnnotation(QueryConditions.class);
				if (v != null) {
					for (QueryCondition condition : v.value()) {
						if (condition != null) {
							if (condition.searchable() && StringUtils.isNotBlank(condition.databaseField())) {
								if (StringUtils.isNotBlank(condition.field())) {
									values.put(condition.field(), condition.databaseField());
								} else {
									values.put(f.getName(), condition.databaseField());
								}
							}
						}
					}
				}
			}
		}

		Method[] ms = clazz.getMethods();
		for (Method m : ms) {
			{
				QueryCondition condition = m.getAnnotation(QueryCondition.class);
				if (condition != null) {
					if (condition.searchable() && StringUtils.isNotBlank(condition.databaseField())) {
						if (StringUtils.isNotBlank(condition.field())) {
							values.put(condition.field(), condition.databaseField());
						} else {
							String methodName = m.getName();
							if (methodName.startsWith("get")) {
								String field = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
										methodName.substring("get".length()));
								values.put(field, condition.databaseField());
							} else {
								logger.warn("Not support searchable, methodName:{}", methodName);
							}
						}
					}
				}
			}
			{
				QueryConditions v = m.getAnnotation(QueryConditions.class);
				if (v != null) {
					for (QueryCondition condition : v.value()) {
						if (condition != null) {
							if (condition.searchable() && StringUtils.isNotBlank(condition.databaseField())) {
								if (StringUtils.isNotBlank(condition.field())) {
									values.put(condition.field(), condition.databaseField());
								} else {
									String methodName = m.getName();
									if (methodName.startsWith("get")) {
										String field = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
												methodName.substring("get".length()));
										values.put(field, condition.databaseField());
									} else {
										logger.warn("Not support searchable, methodName:{}", methodName);
									}
								}
							}
						}
					}
				}
			}
		}

		return values;
	}

}