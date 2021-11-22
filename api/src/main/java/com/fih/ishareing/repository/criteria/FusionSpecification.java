package com.fih.ishareing.repository.criteria;


import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.utils.date.DateUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FusionSpecification<T> implements Specification<T> {

	public enum OPERARATOR {
		EQ("="), NE("!="), GT(">"), GTE(">="), LT("<"), LTE("<="), LIKE("like"), BETWEEN("between"), IN("in"), NIN("not in");
		private OPERARATOR(String value) {
			this.value = value;
		}
		private String value;
		public String getValue() {
			return this.value;
		}
	}

	public enum LOGICAL_OPERATOR {
		AND, OR
	}

	private SearchCriteria criteria;
	private Class<T> entityClass;

	private CriteriaFilterCondition filter;

	public FusionSpecification(SearchCriteria searchCriteria, Class<T> entityClass, CriteriaFilterCondition filter) {
		this.criteria = searchCriteria;
		this.entityClass = entityClass;
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		String key = criteria.getKey();
		String value = criteria.getValue().toString();

		if (key.indexOf(".") == -1) {
			return genPredicate(root, builder, key, value);
		} else {
			String[] levels = key.split("\\.");
			try {
				if (null == filter.getRootStr() || !root.toString().equals(filter.getRootStr())) {
					filter.setRootStr(root.toString());
					filter.getFromMap().clear();
				}
				From join = recursiveAppendJoin(levels, 0, entityClass, root, filter.getFromMap());
				//query.distinct(true);
				return genPredicate(join, builder, levels[levels.length - 1], value);
			} catch (NoSuchFieldException e) {
				throw new ParameterException(levels[0] + "is invalid column name", e);
			} catch (SecurityException e) {
				throw new ParameterException(levels[0] + "is invalid column name", e);
			}
		}

	}
	@SuppressWarnings("rawtypes")
	private From recursiveAppendJoin(String[] levels, int index, Class parentClass, From from, Map<String, From> fromMap) throws NoSuchFieldException, SecurityException {
		Field field = parentClass.getDeclaredField(levels[index]);
		From join = null;
		if (field.getType().equals(List.class)) {
			Class<?> clazz = ReflectionUtil.getGenricTypeOfField(field);
			String mappedBy;
			if (ReflectionUtil.isClassHasAnnotations(clazz, Entity.class, Embeddable.class)) {
				mappedBy = field.getName();
			} else {
				throw new ParameterException(levels[0] + "is invalid column name");
			}
			From existedFrom = fromMap.get(clazz.getName());
			if (null == existedFrom) {
				fromMap.put(clazz.getName(), from.join(mappedBy));
				existedFrom = fromMap.get(clazz.getName());
			}
			//join = from.join(mappedBy);
			join = existedFrom;

			if (levels.length - 2 > index) {
				String name = levels[++index];
				join = recursiveAppendJoin(levels, index, clazz, join, fromMap);
			}
		} else {
			if (ReflectionUtil.isClassHasAnnotations(field.getType(), Entity.class, Embeddable.class)) {
				String mappedBy = field.getName();
				From existedFrom = fromMap.get(field.getType().getName());
				if (null == existedFrom) {
					fromMap.put(field.getType().getName(), from.join(mappedBy));
					existedFrom = fromMap.get(field.getType().getName());
				}
				//join = from.join(mappedBy);
				join = existedFrom;
				if (levels.length - 2 > index) {
					String name = levels[++index];
					join = recursiveAppendJoin(levels, index, field.getType(), join, fromMap);
				}
			}
		}
		return join;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Predicate genPredicate(From root, CriteriaBuilder builder, String key, String value) {
		switch(OPERARATOR.valueOf(criteria.getOperation().toUpperCase())) {
			case IN:
				if (StringToPrimitypeUtil.isSplitByCommaString(value)) {
					Class clazz = root.get(key).getJavaType();

					return root.get(key).in(StringToPrimitypeUtil.toObjects(clazz, value));
				}
			case NIN:
				if (StringToPrimitypeUtil.isSplitByCommaString(value)) {
					Class clazz = root.get(key).getJavaType();
					return root.get(key).in(StringToPrimitypeUtil.toObjects(clazz, value)).not();
				}
			case EQ:
				if (root.get(key).getJavaType() == String.class) {
					return builder.equal(root.<String>get(key), value);
				} else if (Date.class.isAssignableFrom(root.get(key).getJavaType())) {
					throw new ParameterException("DateTime not supper EQ(eq) operator.");
					//return builder.equal(root.<Date>get(key), DateUtils.toDate(value));
				} else {
					return builder.equal(root.get(key), StringToPrimitypeUtil.toObject(value));
				}
			case NE:
				if (root.get(key).getJavaType() == String.class) {
					return builder.notEqual(root.<String>get(key), criteria.getValue());
				} else if (Date.class.isAssignableFrom(root.get(key).getJavaType())) {
					return builder.notEqual(root.<Date>get(key), DateUtils.toDate(value));
				} else {
					return builder.notEqual(root.get(key), StringToPrimitypeUtil.toObject(value));
				}
			case GT:
				if (StringToPrimitypeUtil.isBoolean(value)) {
					return builder.greaterThan(root.<Boolean> get(key), Boolean.valueOf(value));
				} else if (Date.class.isAssignableFrom(root.get(key).getJavaType())) {
					return builder.greaterThan(root.<Date>get(key), DateUtils.toDate(value));
				}
				return builder.greaterThan(root.<String> get(key), value);
			case GTE:
				if (StringToPrimitypeUtil.isBoolean(value)) {
					return builder.greaterThanOrEqualTo(root.<Boolean> get(key), Boolean.valueOf(value));
				} else if (Date.class.isAssignableFrom(root.get(key).getJavaType())) {
					return builder.greaterThanOrEqualTo(root.<Date>get(key), DateUtils.toDate(value));
				}
				return builder.greaterThanOrEqualTo(root.<String> get(key), value);
			case LT:
				if (StringToPrimitypeUtil.isBoolean(value)) {
					return builder.lessThan(root.<Boolean> get(key), Boolean.valueOf(value));
				} else if (Date.class.isAssignableFrom(root.get(key).getJavaType())) {
					return builder.lessThan(root.<Date>get(key), DateUtils.toDate(value));
				}
				return builder.lessThan(root.<String> get(key), value);
			case LTE:
				if (StringToPrimitypeUtil.isBoolean(value)) {
					return builder.lessThanOrEqualTo(root.<Boolean> get(key), Boolean.valueOf(value));
				} else if (Date.class.isAssignableFrom(root.get(key).getJavaType())) {
					return builder.lessThanOrEqualTo(root.<Date>get(key), DateUtils.toDate(value));
				}
				return builder.lessThanOrEqualTo(root.<String> get(key), value);
			case LIKE:
				if (root.get(key).getJavaType() == String.class) {
					String d = value.replaceAll("%","\\\\%");
					return builder.like(root.<String>get(key), "%" + d + "%");
				} else {
					throw new ParameterException("Operator is not supported. [" + criteria.getOperation() + " only can be used on string type]");
				}
			case BETWEEN:
				String[] split = value.split(",");
				if (split.length != 2) {
					throw new ParameterException(key + " : the value of BETWEEN operator only can be split by ',' into two values.");
				}
				if (Date.class.isAssignableFrom(root.get(key).getJavaType())) {
					if (split.length != 2) {
						throw new ParameterException(key + " is Date type, the value of BETWEEN operator only can be split by ',' into two Dates with format 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm:ss'");
					}
					return builder.between(root.<Date>get(key), DateUtils.toDate(split[0]), DateUtils.toDate(split[1]));
				} else {
					Object v1 = StringToPrimitypeUtil.toObject(split[0]);
					Object v2 = StringToPrimitypeUtil.toObject(split[1]);
					if (!v1.getClass().equals(v2.getClass())) {
						throw new ParameterException(key + " : the data type of value after BETWEEN operator must be the same.");
					}
					if (root.get(key).getJavaType() == Integer.class && v1 instanceof Integer) {
						return builder.between(root.<Integer>get(key), (Integer)v1, (Integer)v2);
					} else if (root.get(key).getJavaType() == Double.class && v1 instanceof Double) {
						return builder.between(root.<Double>get(key), (Double)v1, (Double)v2);
					} else if (root.get(key).getJavaType() == String.class && v1 instanceof String) {
						return builder.between(root.<String>get(key), (String)v1, (String)v2);
					} else {
						throw new ParameterException(key + " between " + value + " is invalid. BETWEEN operator only accpet Integer, Double, String, Date type.");
					}
				}
			default:
		}
		throw new ParameterException("Operator is not supported. [" + criteria.getOperation() + "]");
	}

}

