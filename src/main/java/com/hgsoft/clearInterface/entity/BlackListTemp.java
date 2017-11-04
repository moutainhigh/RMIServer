package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class BlackListTemp implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5870429785887268468L;

	private Long id;
	
	private String netNo;
	
	private String obuId;
	
	private String license;
	
	private String cardType;
	
	private String cardNo;
	
	private Date genTime;
	
	private Integer status;
	
	private String genMode;
	
	public BlackListTemp(){
		
	}
	
	public BlackListTemp(String netNo,String obuId,String license,String cardType
			,String cardNo,Date genTime,Integer status,String genMode){
		this.netNo = netNo;
		this.obuId = obuId;
		this.license = license;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.genTime = genTime;
		this.status = status;
		this.genMode = genMode;
	}

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

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getGenMode() {
		return genMode;
	}

	public void setGenMode(String genMode) {
		this.genMode = genMode;
	}
}
