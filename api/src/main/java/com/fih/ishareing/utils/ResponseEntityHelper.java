package com.fih.ishareing.utils;


import com.fih.ishareing.controller.base.error.exception.ExceptionResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseEntityHelper {
	private static final String error = "invalidRequest";
	public static <T> ResponseEntity<?> result(List<ObjectError> errors) {

		ExceptionResponseVO vo = new ExceptionResponseVO();
		vo.setError(error);
		List<String> descriptions = errors.stream().map(e -> {
			String fieldName = "";
			if(e instanceof FieldError) {
		        FieldError fieldError = (FieldError) e;
		        fieldName = fieldError.getField() + " ";
		    }
			return fieldName + e.getDefaultMessage();
		}).collect(Collectors.toList());
		if (descriptions.size() > 1) {
			vo.setDescriptions(descriptions);
		} else {
			vo.setDescription(descriptions.get(0));
		}

		return new ResponseEntity<ExceptionResponseVO>( vo , HttpStatus.BAD_REQUEST);
	}
}
