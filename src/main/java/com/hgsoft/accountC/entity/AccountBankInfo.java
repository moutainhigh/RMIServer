package com.hgsoft.accountC.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AccountBankInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2398126801241016683L;

	
	private Long id;
	private String organ;
	private String subAccountNo;
	private String accountType;
	private String linkMan;
	private String tel;
	private Date validity;
	private String bank;
	private String bankSpan;
	private String obaNo;
	private String bankAccount;
	private String bankName;
	private String accName;
	private String virType;
	private BigDecimal MaxAcr;
	private Integer virenum;
	private Long bankClearNo;
	private Long bankAcceptNo;
	private String payAgreementNo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getSubAccountNo() {
		return subAccountNo;
	}
	public void setSubAccountNo(String subAccountNo) {
		this.subAccountNo = subAccountNo;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getValidity() {
		return validity;
	}
	public void setValidity(Date validity) {
		this.validity = validity;
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
	public String getObaNo() {
		return obaNo;
	}
	public void setObaNo(String obaNo) {
		this.obaNo = obaNo;
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
	public String getVirType() {
		return virType;
	}
	public void setVirType(String virType) {
		this.virType = virType;
	}
	public BigDecimal getMaxAcr() {
		return MaxAcr;
	}
	public void setMaxAcr(BigDecimal maxAcr) {
		MaxAcr = maxAcr;
	}
	public Integer getVirenum() {
		return virenum;
	}
	public void setVirenum(Integer virenum) {
		this.virenum = virenum;
	}
	public Long getBankClearNo() {
		return bankClearNo;
	}
	public void setBankClearNo(Long bankClearNo) {
		this.bankClearNo = bankClearNo;
	}
	public Long getBankAcceptNo() {
		return bankAcceptNo;
	}
	public void setBankAcceptNo(Long bankAcceptNo) {
		this.bankAcceptNo = bankAcceptNo;
	}
	public String getPayAgreementNo() {
		return payAgreementNo;
	}
	public void setPayAgreementNo(String payAgreementNo) {
		this.payAgreementNo = payAgreementNo;
	}
	
	
}
