package com.fih.ishareing.repository.criteria;


import com.fih.ishareing.errorHandling.exceptions.ParameterException;

public class SearchCriteria {
	private String alias;
	private String key;
    private String operation;
    private Object value;
    private boolean orPredicate;
    
    //for costomized hql only
    private CustomizedEntry<?> customizedEntry;
    
	public SearchCriteria(String orPredicate, String key, String operation, Object value) {
		super();

		this.orPredicate = orPredicate != null && orPredicate.equalsIgnoreCase("or");
		String[] split = key.split(";");
		if (split.length == 1) {
			this.key = key;
		} else if (split.length == 2) {
			this.alias = split[0];
			this.key = split[1];
		} else {
			throw new ParameterException("SearchCriteria parse key error.");
		}
		this.operation = operation;
		this.value = value;
	}
	
	public SearchCriteria(String key, String operation, Object value) {
		this(null, key, operation, value);
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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
		return this.orPredicate;
	}

	public CustomizedEntry<?> getCustomizedEntry() {
		return customizedEntry;
	}

	public void setCustomizedEntry(CustomizedEntry<?> customizedEntry) {
		this.customizedEntry = customizedEntry;
	}
    
}
