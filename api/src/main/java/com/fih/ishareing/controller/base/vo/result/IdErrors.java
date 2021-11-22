package com.fih.ishareing.controller.base.vo.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fih.ishareing.controller.base.vo.sync.AbsSync;
import com.fih.ishareing.controller.base.error.exception.BaseErrors;
import com.fih.ishareing.controller.base.error.exception.ExceptionResponseVO;


import java.util.List;

public class IdErrors<T> extends AbsSync implements BaseErrors<T> {
	private T id;
	private List<ExceptionResponseVO> errors;
	public T getId() {
		return id;
	}
	public void setId(T id) {
		this.id = id;
	}
	public List<ExceptionResponseVO> getErrors() {
//		if (null == errors)
//			errors = new ArrayList<ExceptionResponseVO>();
		return errors;
	}
	public void setErrors(List<ExceptionResponseVO> errors) {
		this.errors = errors;
	}
	@Override
	@JsonIgnore
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
