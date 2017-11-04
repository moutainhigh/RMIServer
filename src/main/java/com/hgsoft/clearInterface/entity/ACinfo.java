package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ACinfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5968399749013815904L;
	private Long id;
	private String cardNo;
	private String userNo;
	private String bank;
	private String bankSpan;
	private String bankAccount;
	private String bankName;
	private String accName;
	private String AccountType;
	private Integer OBANo;
	private Integer State;
	private Date businessTime;
	private String virType;
	private BigDecimal maxAcr;
	private String systemType;
	private Date sdate;
	private String subaccountNo;
	
	public String getSubaccountNo() {
		return subaccountNo;
	}
	public void setSubaccountNo(String subaccountNo) {
		this.subaccountNo = subaccountNo;
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
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankSpan() {
		return bankSpan;
	}
	public void setBankSpan(String bankSpan) {
		this.bankSpan = bankSpan;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccountType() {
		return AccountType;
	}
	public void setAccountType(String accountType) {
		AccountType = accountType;
	}
	public Integer getOBANo() {
		return OBANo;
	}
	public void setOBANo(Integer oBANo) {
		OBANo = oBANo;
	}

	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	public String getVirType() {
		return virType;
	}
	public void setVirType(String virType) {
		this.virType = virType;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public BigDecimal getMaxAcr() {
		return maxAcr;
	}
	public void setMaxAcr(BigDecimal maxAcr) {
		this.maxAcr = maxAcr;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	
	
}
