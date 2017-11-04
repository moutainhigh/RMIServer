package com.hgsoft.clearInterface.entity;
/**
 * 记帐卡用户状态数据
 */
import java.io.Serializable;
import java.util.Date;

public class UserStateInfoSend implements Serializable{
	private static final long serialVersionUID = -3924325532113427536L;
	private Long id;
	private String cardCode;
	private Long cardType;
	private Long state;
	private Long userType;
	private Long vehColor;
	private String license;
	private Long vehType;
	/*private String obuId;*/
	private Date obuIssueTime;
	private Date obuExpireTime;
	/*private Date transferTime;*/
	private Date businessTime;
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Long getCardType() {
		return cardType;
	}
	public void setCardType(Long cardType) {
		this.cardType = cardType;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public Long getUserType() {
		return userType;
	}
	public void setUserType(Long userType) {
		this.userType = userType;
	}
	public Long getVehColor() {
		return vehColor;
	}
	public void setVehColor(Long vehColor) {
		this.vehColor = vehColor;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Long getVehType() {
		return vehType;
	}
	public void setVehType(Long vehType) {
		this.vehType = vehType;
	}
	/*public String getObuId() {
		return obuId;
	}
	public void setObuId(String obuId) {
		this.obuId = obuId;
	}*/
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
	/*public Date getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}*/
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}