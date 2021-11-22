package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

import java.io.Serializable;

public class PasswordUnavailableException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "passwordUnavailable";
	private static final String description = "Cannot send notification for password";
	
	public PasswordUnavailableException() {
		super(error, description, description);
	}
	
	public PasswordUnavailableException(String msg) {
		super(error, description,msg);
	}

	public PasswordUnavailableException(String msg, Throwable t) {
		super(error, description, msg, t);
	}


}
