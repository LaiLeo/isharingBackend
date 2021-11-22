package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class TokenInValidException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "invalidToken";
	private static final String description = "Token is out-of-date or invalid";

	public TokenInValidException() {
		super(error, description, description);
	}

	public TokenInValidException(String msg) {
		super(error, description, msg);
	}

	public TokenInValidException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
