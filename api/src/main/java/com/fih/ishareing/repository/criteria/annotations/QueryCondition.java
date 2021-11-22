package com.fih.ishareing.repository.criteria.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCondition {

	public boolean sortable() default false;

	public boolean searchable() default false;

	public String databaseField() default "";

	public String field() default "";

}