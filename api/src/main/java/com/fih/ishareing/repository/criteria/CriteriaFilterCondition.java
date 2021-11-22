package com.fih.ishareing.repository.criteria;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.From;

public class CriteriaFilterCondition {
	private String rootStr = null;
	private Map<String, From> fromMap = new HashMap<String, From>();
	public String getRootStr() {
		return rootStr;
	}
	public void setRootStr(String rootStr) {
		this.rootStr = rootStr;
	}
	public Map<String, From> getFromMap() {
		return fromMap;
	}
	public void setFromMap(Map<String, From> fromMap) {
		this.fromMap = fromMap;
	}


}
