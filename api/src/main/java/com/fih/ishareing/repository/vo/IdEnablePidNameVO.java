package com.fih.ishareing.repository.vo;

public class IdEnablePidNameVO {
	private int id;
	private Boolean enable;
	private String pid;
	private String name;

	public IdEnablePidNameVO() {
		super();
	}
	public IdEnablePidNameVO(int id, Boolean enable, String pid, String name) {
		super();
		this.id = id;
		this.enable = enable;
		this.pid = pid;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
