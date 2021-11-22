package com.fih.ishareing.repository.vo;

public class IdEnableCodeNameVO {

	private int id;
	private Boolean enable;
	private String code;
	private String name;

	public IdEnableCodeNameVO() {
		super();
	}
		public IdEnableCodeNameVO(int id, Boolean enable, String code, String name) {
		super();
		this.id = id;
		this.enable = enable;
		this.code = code;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
