package com.fih.ishareing.validators.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumAllMatchValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@ReportAsSingleViolation
public @interface ValidateEnumAllMatch {
	Class<? extends Enum<?>> enumClazz();

	String message() default "{accept.value.error}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
