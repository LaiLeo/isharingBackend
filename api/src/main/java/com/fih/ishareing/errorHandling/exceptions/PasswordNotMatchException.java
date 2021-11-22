package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class PasswordNotMatchException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "passwordNotMatch";
	private static final String description = "Original password is incorrect";

	public PasswordNotMatchException() {
		super(error, description, description);
	}

	public PasswordNotMatchException(String msg) {
		super(error, description, msg);
	}

	public PasswordNotMatchException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
