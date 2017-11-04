package com.hgsoft.agentCard.entity;

import java.io.Serializable;
import java.util.Date;

public class CardBlacklistInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String UTCardNo;
	private String creditCardNo;
	private String userNo;
	private Date produceTime;
	private String produceReason;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUTCardNo() {
		return UTCardNo;
	}
	public void setUTCardNo(String uTCardNo) {
		UTCardNo = uTCardNo;
	}
	public String getCreditCardNo() {
		return creditCardNo;
	}
	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	public Date getProduceTime() {
		return produceTime;
	}
	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}
	public String getProduceReason() {
		return produceReason;
	}
	public void setProduceReason(String produceReason) {
		this.produceReason = produceReason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
