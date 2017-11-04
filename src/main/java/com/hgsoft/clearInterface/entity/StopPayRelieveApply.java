package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class StopPayRelieveApply implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 575261676940673417L;

	private Long id;
	
	private String BAccount;
	
	private String cardNo;
	
	private Date genTime;
	
	private Double lateFee;
	
	private String remark;
	
	private Date updateTime;

	public StopPayRelieveApply(){
		
	}
	
	public StopPayRelieveApply(String BAccount,String cardNo,
			Date genTime,Double lateFee,String remark,Date updateTime){
		this.BAccount = BAccount;
		this.cardNo = cardNo;
		this.genTime = genTime;
		this.lateFee = lateFee;
		this.remark = remark;
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBAccount() {
		return BAccount;
	}

	public void setBAccount(String bAccount) {
		BAccount = bAccount;
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

	public Double getLateFee() {
		return lateFee;
	}

	public void setLateFee(Double lateFee) {
		this.lateFee = lateFee;
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
}
