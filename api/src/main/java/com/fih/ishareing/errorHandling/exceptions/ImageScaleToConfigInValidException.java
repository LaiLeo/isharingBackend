package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class ImageScaleToConfigInValidException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "imageScaleToConfigInValid";
	private static final String description = "Image scale to config invalid. ";

	public ImageScaleToConfigInValidException() {
		super(error, description, description);
	}

	public ImageScaleToConfigInValidException(String msg) {
		super(error, description, msg);
	}

	public ImageScaleToConfigInValidException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
