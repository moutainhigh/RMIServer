package com.hgsoft.system.entity;

import java.io.Serializable;

public class SysRole  implements Serializable{

	Long id;
	String name;
	String remark;
	String useState;
	String subuumstem;
	String propertyType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUseState() {
		return useState;
	}
	public void setUseState(String useState) {
		this.useState = useState;
	}
	public String getSubuumstem() {
		return subuumstem;
	}
	public void setSubuumstem(String subuumstem) {
		this.subuumstem = subuumstem;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	

}
