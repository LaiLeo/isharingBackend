package com.fih.ishareing.validators;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator extends RegexValidator {
	public static final String PASSWORD_PATTERN =
			"^(?![0-9]+$)(?![a-zA-Z]+$)(?![\\~\\`\\@\\#\\$\\%\\^\\&\\*\\-\\_\\=\\+\\|\\?\\(\\)\\<\\>\\[\\]\\{\\}\\,\\.\\;\\'\\!]+$)[0-9a-zA-Z\\~\\`\\@\\#\\$\\%\\^\\&\\*\\-\\_\\=\\+\\|\\?\\(\\)\\<\\>\\[\\]\\{\\}\\,\\.\\;\\'\\!]{8,15}$";

	public PasswordValidator() {
		super(PASSWORD_PATTERN);
	}
}
