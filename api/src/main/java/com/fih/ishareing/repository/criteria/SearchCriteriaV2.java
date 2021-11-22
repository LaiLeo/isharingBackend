package com.fih.ishareing.repository.criteria;

import java.util.List;

public class SearchCriteriaV2 {
	List<SearchCriteriaV2> next;
	List<SearchCriteriaV2> nested;
	private String key;
    private String operation;
    private Object value;
    private boolean orPredicate;
    
	public List<SearchCriteriaV2> getNext() {
		return next;
	}
	public void setNext(List<SearchCriteriaV2> next) {
		this.next = next;
	}
	public List<SearchCriteriaV2> getNested() {
		return nested;
	}
	public void setNested(List<SearchCriteriaV2> nested) {
		this.nested = nested;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public boolean isOrPredicate() {
		return orPredicate;
	}
	public void setOrPredicate(boolean orPredicate) {
		this.orPredicate = orPredicate;
	}
    
    
}
