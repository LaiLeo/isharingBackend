package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

import java.io.Serializable;

public class EmailAddressFailedException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "emailAddressFailed";
	private static final String description = "Sent email failed. Email address may not be exist";
	
	public EmailAddressFailedException() {
		super(error, description, description);
	}
	
	public EmailAddressFailedException(String msg) {
		super(error, description,msg);
	}

	public EmailAddressFailedException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
