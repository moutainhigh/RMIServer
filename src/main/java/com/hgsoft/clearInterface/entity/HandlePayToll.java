package com.hgsoft.clearInterface.entity;

import java.math.BigDecimal;
import java.util.Date;

public class HandlePayToll implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3665668524324401874L;

	private Long id;
	private String baccount;
	private String cardNo;
	private Date handPayTime;
	private BigDecimal handPayFee;
	private String payAccount;
	private String payFlag;
	private String bankFlag;
	private String remark;

	private Date updateTime;
	private Long boardListNo;

	public HandlePayToll(){
		
	}
	public HandlePayToll(String baccount,String cardNo,Date handPayTime
			,BigDecimal handPayFee,String payAccount,String payFlag,String bankFlag,String remark,Date updateTime,Long boardListNo){
		this.baccount = baccount;
		this.cardNo = cardNo;
		this.handPayTime = handPayTime;
		this.handPayFee = handPayFee;
		this.payAccount = payAccount;
		this.payFlag = payFlag;
		this.bankFlag = bankFlag;
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

	public BigDecimal getHandPayFee() {
		return handPayFee;
	}

	public void setHandPayFee(BigDecimal handPayFee) {
		this.handPayFee = handPayFee;
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

	public String getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(String bankFlag) {
		this.bankFlag = bankFlag;
	}
	public Long getBoardListNo() {
		return boardListNo;
	}
	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}
	
}
