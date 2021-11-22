package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

import java.io.Serializable;

public class PasswordTheSameException extends AbstractException implements BaseException, Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String error = "samePassword";
	private static final String description = "New password is the same with old password";
	
	public PasswordTheSameException() {
		super(error, description, description);
	}

	public PasswordTheSameException(String msg) {
		super(error, description,msg);
	}

	public PasswordTheSameException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
