package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class BlackListAddition implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 450936021649745825L;

	private Long id;
	
	private String netNo;
	
	private String obuId;
	
	private String license;
	
	private String cardType;
	
	private String cardNo;
	
	private Integer status;
	
	private Date genTime;
	
	private String version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNetNo() {
		return netNo;
	}

	public void setNetNo(String netNo) {
		this.netNo = netNo;
	}

	public String getObuId() {
		return obuId;
	}

	public void setObuId(String obuId) {
		this.obuId = obuId;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
