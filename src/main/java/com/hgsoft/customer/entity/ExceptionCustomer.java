package com.hgsoft.customer.entity;

public class ExceptionCustomer implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1435023106726299045L;
	
	
	private Long id;
	private Long customerId;
	private String exceptionType;
	private String userType;
	private String organ;
	private String idType;
	private String idCode;
	private String linkMan;
	private String tel;
	private String warnFlag;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getWarnFlag() {
		return warnFlag;
	}
	public void setWarnFlag(String warnFlag) {
		this.warnFlag = warnFlag;
	}
	
	
}
