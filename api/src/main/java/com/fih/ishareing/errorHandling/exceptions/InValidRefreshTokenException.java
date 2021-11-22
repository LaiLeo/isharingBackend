package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class InValidRefreshTokenException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "invalidRefreshToken";
	private static final String description = "Token is out-of-date or invalid";

	public InValidRefreshTokenException() {
		super(error, description, description);
	}

	public InValidRefreshTokenException(String msg) {
		super(error, description, msg);
	}

	public InValidRefreshTokenException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
