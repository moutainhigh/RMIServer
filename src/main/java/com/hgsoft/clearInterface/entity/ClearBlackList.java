package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class ClearBlackList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4841694197698548892L;

	private Long id;
	
	private String netNo;
	
	private String cardCode;
	
	private String cardType;
	
	private String obuId;
	
	private String license;
	
	private Integer genCau;
	
	private Integer genMode;
	
	private Date genTime;
	
	private Long version;
	
	private String remark;
	
	private Date updateTime;
	
	private Long boardListNo;
	
	public ClearBlackList(){
		
	}
	
	public ClearBlackList(String netNo,String cardCode,String cardType,String obuId,
			String license,Integer genCau,Integer genMode,Date genTime,Long version,String remark,Date updateTime,Long boardListNo){
		this.netNo = netNo;
		this.cardCode = cardCode;
		this.cardType = cardType;
		this.obuId = obuId;
		this.license = license;
		this.genCau = genCau;
		this.genMode = genMode;
		this.genTime = genTime;
		this.version = version;
		this.remark = remark;
		this.updateTime = updateTime;
		this.boardListNo = boardListNo;
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

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public Integer getGenCau() {
		return genCau;
	}

	public void setGenCau(Integer genCau) {
		this.genCau = genCau;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getGenMode() {
		return genMode;
	}

	public void setGenMode(Integer genMode) {
		this.genMode = genMode;
	}

	public Long getBoardListNo() {
		return boardListNo;
	}

	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}
	
	
}
