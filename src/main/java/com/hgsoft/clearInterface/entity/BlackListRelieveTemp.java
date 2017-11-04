package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class BlackListRelieveTemp implements java.io.Serializable {

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
	
	private String genMode;
	
	private Date genTime;
	
	private Date sysGenTime;
	
	private Integer status;
	
	private Integer dealStatus;
	
	private Date dealTime;
	
	private Date sysDealTime;
	
	private Integer flag;
	
	private Long tempId;
	
	public BlackListRelieveTemp(){
		
	}
	
	public BlackListRelieveTemp(String netNo,String obuId,String license,String cardType
			,String cardNo,Date genTime,Integer status,Integer dealStatus,Date dealTime,String genMode,Date sysGenTime,
			Date sysDealTime,Integer flag,Long tempId){
		this.netNo = netNo;
		this.obuId = obuId;
		this.license = license;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.genTime = genTime;
		this.status = status;
		this.dealStatus = dealStatus;
		this.dealTime = dealTime;
		this.genMode = genMode;
		this.sysGenTime = sysGenTime;
		this.sysDealTime = sysDealTime;
		this.flag = flag;
		this.tempId = tempId;
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

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getGenMode() {
		return genMode;
	}

	public void setGenMode(String genMode) {
		this.genMode = genMode;
	}

	public Date getSysGenTime() {
		return sysGenTime;
	}

	public void setSysGenTime(Date sysGenTime) {
		this.sysGenTime = sysGenTime;
	}

	public Date getSysDealTime() {
		return sysDealTime;
	}

	public void setSysDealTime(Date sysDealTime) {
		this.sysDealTime = sysDealTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getTempId() {
		return tempId;
	}

	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}
}
