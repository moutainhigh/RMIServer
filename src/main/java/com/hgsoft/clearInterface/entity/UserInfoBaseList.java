package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class UserInfoBaseList implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8549172165898711970L;
	private String netNo;
	private String issuerId;
	private Date creationTime;
	private Integer agent;
	private Integer userType;
	private String userName;
	private String userTel;
	private String cardCode;
	private Date cardIssueTime;
	private Date cardExpireTime;
	private Integer cardType;
	private String obuId;
	private Date obuIssueTime;
	private Date obuExpireTime;
	private String model;
	private Integer vehType;
	private String license;
	private String licenseColor;
	private Date genTime;
	private Integer alterFlag;
	private Date businessTime;
	public String getNetNo() {
		return netNo;
	}
	public void setNetNo(String netNo) {
		this.netNo = netNo;
	}
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Integer getAgent() {
		return agent;
	}
	public void setAgent(Integer agent) {
		this.agent = agent;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Date getCardIssueTime() {
		return cardIssueTime;
	}
	public void setCardIssueTime(Date cardIssueTime) {
		this.cardIssueTime = cardIssueTime;
	}
	public Date getCardExpireTime() {
		return cardExpireTime;
	}
	public void setCardExpireTime(Date cardExpireTime) {
		this.cardExpireTime = cardExpireTime;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getObuId() {
		return obuId;
	}
	public void setObuId(String obuId) {
		this.obuId = obuId;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getVehType() {
		return vehType;
	}
	public void setVehType(Integer vehType) {
		this.vehType = vehType;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getLicenseColor() {
		return licenseColor;
	}
	public void setLicenseColor(String licenseColor) {
		this.licenseColor = licenseColor;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public Integer getAlterFlag() {
		return alterFlag;
	}
	public void setAlterFlag(Integer alterFlag) {
		this.alterFlag = alterFlag;
	}
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	
	
}
