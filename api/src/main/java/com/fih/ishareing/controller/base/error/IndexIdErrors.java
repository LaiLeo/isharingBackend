package com.fih.ishareing.controller.base.error;

import java.util.List;

public class IndexIdErrors {
	private Integer index;
	private Integer id;
	private List<ApiErrorResponse> errors;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

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
