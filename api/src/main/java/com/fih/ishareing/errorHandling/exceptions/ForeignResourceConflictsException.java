package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

import java.io.Serializable;

public class ForeignResourceConflictsException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "foreignResourceConflicts";
	private static final String description = "Resource conflicts was found";
	
	public ForeignResourceConflictsException() {
		super(error, description, description);
	}
	
	public ForeignResourceConflictsException(String msg) {
		super(error, description,msg);
	}

	public ForeignResourceConflictsException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
