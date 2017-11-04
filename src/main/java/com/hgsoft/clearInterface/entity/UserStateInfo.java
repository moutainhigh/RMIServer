package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class UserStateInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5990476184714572585L;

	private Long id;
	
	private Date genTime;
	
	private Integer dealFlag;
	
	private String cardCode;
	
	private String cardType;
	
	private Integer vehColor;
	
	private String license;
	
	private String vehType;
	
	private String obuCode;
	
	private String obuSeq;
	
	private Date obuIssueTime;
	
	private Date obuExpireTime;
	
	private String remark;
	
	private Date updateTime;
	
	private Long boardListNo;
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getBoardListNo() {
		return boardListNo;
	}

	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}

	public UserStateInfo(){
		
	}
	
	public UserStateInfo(Date genTime,Integer dealFlag,	String cardCode,	
		String cardType,Integer vehColor,String license,String vehType,String obuCode,	
		String obuSeq,Date obuIssueTime,Date obuExpireTime,String remark){
		this.genTime = genTime;
		this.dealFlag = dealFlag;
		this.cardCode = cardCode;
		this.cardType = cardType;
		this.vehColor = vehColor;
		this.license = license;
		this.vehType = vehType;
		this.obuCode = obuCode;
		this.obuSeq = obuSeq;
		this.obuIssueTime = obuIssueTime;
		this.obuExpireTime = obuExpireTime;
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Integer getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(Integer dealFlag) {
		this.dealFlag = dealFlag;
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

	public Integer getVehColor() {
		return vehColor;
	}

	public void setVehColor(Integer vehColor) {
		this.vehColor = vehColor;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getVehType() {
		return vehType;
	}

	public void setVehType(String vehType) {
		this.vehType = vehType;
	}

	public String getObuSeq() {
		return obuSeq;
	}

	public void setObuSeq(String obuSeq) {
		this.obuSeq = obuSeq;
	}

	public Date getObuIssueTime() {
		return obuIssueTime;
	}

	public void setObuIssueTime(Date obuIssueTime) {
		this.obuIssueTime = obuIssueTime;
	}

	public Date getObuExpireTime() {
		return obuExpireTime;
	}

	public void setObuExpireTime(Date obuExpireTime) {
		this.obuExpireTime = obuExpireTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getObuCode() {
		return obuCode;
	}

	public void setObuCode(String obuCode) {
		this.obuCode = obuCode;
	}
	
	

}
