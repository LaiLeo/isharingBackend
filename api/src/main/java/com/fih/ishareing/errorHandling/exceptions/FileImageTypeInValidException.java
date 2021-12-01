package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class FileImageTypeInValidException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "fileImageTypeInValid";
	private static final String description = "File image type invalid. Availavle type is image.";

	public FileImageTypeInValidException() {
		super(error, description, description);
	}

	public FileImageTypeInValidException(String msg) {
		super(error, description, msg);
	}

	public FileImageTypeInValidException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
