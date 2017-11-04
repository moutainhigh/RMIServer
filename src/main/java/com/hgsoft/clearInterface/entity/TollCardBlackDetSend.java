package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class TollCardBlackDetSend implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9082460896499131923L;
	/**
	 * 
	 */
	private Integer netNo;
	private String issuerId;
	private String cardCode;
	private Long cpuCardId;
	private String obuId;
	private String license;
	private Integer genCau;
	private Date genTime;
	private Integer flag;
	private Date businessTime;
	public TollCardBlackDetSend(){
		
	}
	public TollCardBlackDetSend(Integer netNo,String issuerId,String cardCode,Long cpuCardId,String obuId,String license,Integer genCau,Date genTime,Integer flag,Date businessTime){
		this.netNo=netNo;
		this.issuerId=issuerId;
		this.cardCode=cardCode;
		this.cpuCardId=cpuCardId;
		this.obuId=obuId;
		this.license=license;
		this.genCau=genCau;
		this.genTime=genTime;
		this.flag=flag;
		this.businessTime=businessTime;
	}
	public Integer getNetNo() {
		return netNo;
	}
	public void setNetNo(Integer netNo) {
		this.netNo = netNo;
	}
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Long getCpuCardId() {
		return cpuCardId;
	}
	public void setCpuCardId(Long cpuCardId) {
		this.cpuCardId = cpuCardId;
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
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	
}
