package com.fih.ishareing.validators;

import org.springframework.stereotype.Component;

@Component
public class EmailValidator extends RegexValidator {
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		super(EMAIL_PATTERN);
	}
}
