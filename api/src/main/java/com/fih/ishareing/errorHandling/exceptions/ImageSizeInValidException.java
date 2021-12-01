package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.BaseException;
import com.fih.ishareing.errorHandling.abs.AbstractException;

public class ImageSizeInValidException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "imageSizeInValid";
	private static final String description = "Image size invalid.";

	public ImageSizeInValidException() {
		super(error, description, description);
	}

	public ImageSizeInValidException(String msg) {
		super(error, description, msg);
	}

	public ImageSizeInValidException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
