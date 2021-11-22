package com.fih.ishareing.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorUtil {
	public static String errorTrackString(Throwable ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
}
