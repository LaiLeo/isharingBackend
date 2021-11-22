package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

import java.io.Serializable;

public class BadFileNameException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "badFilename";
	private static final String description = "File name format is invalid";
	
	public BadFileNameException() {
		super(error, description, description);
	}
	
	public BadFileNameException(String msg) {
		super(error, description,msg);
	}

	public BadFileNameException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
