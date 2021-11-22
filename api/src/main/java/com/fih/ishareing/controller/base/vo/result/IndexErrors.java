package com.fih.ishareing.controller.base.vo.result;


import com.fih.ishareing.controller.base.vo.sync.AbsSync;
import com.fih.ishareing.controller.base.error.exception.BaseErrors;
import com.fih.ishareing.controller.base.error.exception.ExceptionResponseVO;

import java.io.Serializable;
import java.util.List;

public class IndexErrors<T extends Serializable> extends AbsSync implements BaseErrors<T> {

	private Integer index;
	private T id;
	private List<ExceptionResponseVO> errors;

	public void setId(T id) {
		this.id = id;
	}

	@Override
	public T getId() {
		return id;
	}

	@Override
	public String getCode() {
		return null;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public List<ExceptionResponseVO> getErrors() {
		return errors;
	}

	@Override
	public void setErrors(List<ExceptionResponseVO> errors) {
		this.errors = errors;
	}

}
