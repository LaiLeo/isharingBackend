package com.fih.ishareing.controller.base.error;

import java.util.List;

public class IdErrors {

	private Integer id;

	private List<ApiErrorResponse> errors;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ApiErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<ApiErrorResponse> errors) {
		this.errors = errors;
	}

}
