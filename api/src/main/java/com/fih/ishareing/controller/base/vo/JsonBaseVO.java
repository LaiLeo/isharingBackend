package com.fih.ishareing.controller.base.vo;

import java.util.List;

public class JsonBaseVO<T> {
	private Long count;
	private List<T> results;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
