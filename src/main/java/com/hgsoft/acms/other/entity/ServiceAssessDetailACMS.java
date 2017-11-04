package com.hgsoft.acms.other.entity;

public class ServiceAssessDetailACMS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long mainId;
	private Long parentTypeCode;
	private Long typeCode;
	private Long businessId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Long getParentTypeCode() {
		return parentTypeCode;
	}
	public void setParentTypeCode(Long parentTypeCode) {
		this.parentTypeCode = parentTypeCode;
	}
	public Long getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(Long typeCode) {
		this.typeCode = typeCode;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	
	

}
