package com.hgsoft.invoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PassRecord implements Serializable{
	private static final long serialVersionUID = 4466980834492238354L;
	private Long id;
	private String cardNo;
	private String flag;
	private Date settlementDate;
	private String writeDown;
	private Long customerId;
	private BigDecimal otherAmount;
	private BigDecimal tollsAmount;
	private Long passInvoice;
	private Date tollsTime;
	
	public PassRecord() {
		super();
	}
	public PassRecord(String cardNo, String flag, Date settlementDate,
			String writeDown, Long customerId, BigDecimal otherAmount,
			BigDecimal tollsAmount, Long passInvoice, Date tollsTime) {
		super();
		this.cardNo = cardNo;
		this.flag = flag;
		this.settlementDate = settlementDate;
		this.writeDown = writeDown;
		this.customerId = customerId;
		this.otherAmount = otherAmount;
		this.tollsAmount = tollsAmount;
		this.passInvoice = passInvoice;
		this.tollsTime = tollsTime;
	}
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getWriteDown() {
		return writeDown;
	}
	public void setWriteDown(String writeDown) {
		this.writeDown = writeDown;
	}
	public BigDecimal getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}
	public BigDecimal getTollsAmount() {
		return tollsAmount;
	}
	public void setTollsAmount(BigDecimal tollsAmount) {
		this.tollsAmount = tollsAmount;
	}
	public Long getPassInvoice() {
		return passInvoice;
	}
	public void setPassInvoice(Long passInvoice) {
		this.passInvoice = passInvoice;
	}
	public Date getTollsTime() {
		return tollsTime;
	}
	public void setTollsTime(Date tollsTime) {
		this.tollsTime = tollsTime;
	}
	
}
