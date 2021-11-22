package com.fih.ishareing.controller.base.factory;

import java.util.HashMap;
import java.util.Map;

public class ColumnFactory {
	Map<String, String> sortColumnMap = new HashMap<String, String>();
	Map<String, String> searchColumnMap = new HashMap<String, String>();
	
	public ColumnFactory build(String queryColumn, String realColumn, boolean sortable, boolean searchable) {
		if (sortable) {
			sortColumnMap.put(queryColumn, realColumn);
		}
		if (searchable) {
			searchColumnMap.put(queryColumn, realColumn);
		}
		return this;
	}

	public Map<String, String> getSortColumnMap() {
		return sortColumnMap;
	}

	public Map<String, String> getSearchColumnMap() {
		return searchColumnMap;
	}
	
	
	
}
