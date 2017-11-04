package com.hgsoft.customer.entity;

import java.io.Serializable;

public class ServiceTypeCodeDef implements Serializable {

	private static final long serialVersionUID = 3545658569768049397L;
	//fields
	private Integer id;
	private Short parentTypeCode;//大类编码
	private Short typeCode;//小类编码
	private String typeName;
	private String memo;
	
	public ServiceTypeCodeDef() {
		super();
	}
	//getter and setter
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Short getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(Short typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Short getParentTypeCode() {
		return parentTypeCode;
	}
	public void setParentTypeCode(Short parentTypeCode) {
		this.parentTypeCode = parentTypeCode;
	}
	
}
