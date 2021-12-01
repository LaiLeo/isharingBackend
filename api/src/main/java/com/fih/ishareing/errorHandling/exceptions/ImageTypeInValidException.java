package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class ImageTypeInValidException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "imageTypeInValid";
	private static final String description = "Image type invalid. Availavle type is jpg, jpeg, png, gif.";

	public ImageTypeInValidException() {
		super(error, description, description);
	}

	public ImageTypeInValidException(String msg) {
		super(error, description, msg);
	}

	public ImageTypeInValidException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
