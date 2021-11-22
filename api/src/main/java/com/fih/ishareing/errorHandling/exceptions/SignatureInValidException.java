package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class SignatureInValidException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "invalidSignature";
	private static final String description = "Signature is invalid";

	public SignatureInValidException() {
		super(error, description, description);
	}

	public SignatureInValidException(String msg) {
		super(error, description, msg);
	}

	public SignatureInValidException(String msg, Throwable t) {
		super(error, description, msg, t);
	}
}
