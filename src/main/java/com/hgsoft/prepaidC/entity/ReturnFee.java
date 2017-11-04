package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * AddReg entity. @author MyEclipse Persistence Tools
 */

public class ReturnFee implements java.io.Serializable {

	private static final long serialVersionUID = 947470517343110140L;
	private Long id;
	private String cardNo;
	private String feeType;
	private BigDecimal returnFee;
	private String state;
	private Long bussinessID;
	private Date returnTime;
	private Date balanceTime;
	private Date insertTime;
	private Integer settleMonth;
	

	// Constructors

	/** default constructor */
	public ReturnFee() {
	}

	/** full constructor */
	public ReturnFee(String cardNo,String feeType,BigDecimal returnFee,String state,Long bussinessID,Date returnTime) {
		this.cardNo = cardNo;
		this.feeType = feeType;
		this.returnFee = returnFee;
		this.state = state;
		this.bussinessID = bussinessID;
		this.returnTime = returnTime;
	}
	
	// Property accessors
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public BigDecimal getReturnFee() {
		return returnFee;
	}

	public void setReturnFee(BigDecimal returnFee) {
		this.returnFee = returnFee;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getBussinessID() {
		return bussinessID;
	}

	public void setBussinessID(Long bussinessID) {
		this.bussinessID = bussinessID;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Date getBalanceTime() {
		return balanceTime;
	}

	public void setBalanceTime(Date balanceTime) {
		this.balanceTime = balanceTime;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getSettleMonth() {
		return settleMonth;
	}

	public void setSettleMonth(Integer settleMonth) {
		this.settleMonth = settleMonth;
	}
}