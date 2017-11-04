package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class HandleRelieveBlackList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 280026286483113322L;

	private Long id;
	
	private String baccount;
	
	private String cardNo;
	
	private Date handPayTime;
	
	private Double handPayFee;
	
	private Double etcMoney;
	
	private Double lateFee;

	private String payAccount;

	private String payFlag;

	private String remark;
	
	private Date updateTime;

	public HandleRelieveBlackList(){
		
	}
	
	public HandleRelieveBlackList(String bankAccount,String cardNo,Date handPayTime,Double handPayFee,Double etcMoney,
			Double lateFee,String payAccount,String payFlag,String remark,Date updateTime){
		this.baccount = bankAccount;
		this.cardNo = cardNo;
		this.handPayTime = handPayTime;
		this.handPayFee = handPayFee;
		this.etcMoney = etcMoney;
		this.lateFee = lateFee;
		this.payAccount = payAccount;
		this.payFlag = payFlag;
		this.remark = remark;
		this.updateTime = updateTime;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBaccount() {
		return baccount;
	}

	public void setBaccount(String baccount) {
		this.baccount = baccount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getHandPayTime() {
		return handPayTime;
	}

	public void setHandPayTime(Date handPayTime) {
		this.handPayTime = handPayTime;
	}

	public Double getHandPayFee() {
		return handPayFee;
	}

	public void setHandPayFee(Double handPayFee) {
		this.handPayFee = handPayFee;
	}

	public Double getEtcMoney() {
		return etcMoney;
	}

	public void setEtcMoney(Double etcMoney) {
		this.etcMoney = etcMoney;
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

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}
}
