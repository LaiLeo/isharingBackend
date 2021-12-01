package com.fih.ishareing.validators;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator extends RegexValidator {
	public static final String PASSWORD_PATTERN = "^[A-Za-z0-9]{8,12}$";

	public PasswordValidator() {
		super(PASSWORD_PATTERN);
	}
}
