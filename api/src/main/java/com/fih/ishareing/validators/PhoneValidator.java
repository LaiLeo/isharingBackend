package com.fih.ishareing.validators;

import org.springframework.stereotype.Component;

@Component
public class PhoneValidator extends RegexValidator {
	public static final String PHONE_PATTERN =
			"^((\\+86)|(86))?(\\s)*[1][3-8]\\d{9}$|^((\\+886)|(886))(\\s)*[9]\\d{8}$|^[0][9]\\d{8}$";

	public PhoneValidator() {
		super(PHONE_PATTERN);
	}
}
