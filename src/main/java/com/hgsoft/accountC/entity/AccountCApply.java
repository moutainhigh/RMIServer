package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountCApply implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -392998794169145007L;
	private Long id;
	private String accountType;
	private String linkman;
	private String tel;
	private Date validity;
	private String bank;
	private String bankSpan;
	private String bankAccount;
	private String bankName;
	private String accName;
	private String invoiceprn;
	private Long reqcount;
	private Long residueCount;
	private BigDecimal bail;
	private BigDecimal truckBail;


	private String virType;
	private BigDecimal maxacr;
	private Long bankClearNo;
	private Long bankAcceptNo;
	private String clearingBankName;
	private String focushandlearea;
	private String appState;
	private Long apprOver;
	private Long operId;
	private Long placeId;
	private Long hisseqId;
	private Long customerId;
	private String newCardFlag;
	private Date appTime;
	private Date operTime;
	private Date applyTime;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;

	private String approverNo;
	private String approverName;

	private String payAgreementNo;
	private String appFailMemo;

	private String subAccountNo;

	private String shutDownStatus;

	private String obaNo;

	private Integer virenum;
	private Integer debitCardType;

	public Integer getVirenum() {
		return virenum;
	}
	public void setVirenum(Integer virenum) {
		this.virenum = virenum;
	}
	public String getObaNo() {
		return obaNo;
	}
	public void setObaNo(String obaNo) {
		this.obaNo = obaNo;
	}
	public String getShutDownStatus() {
		return shutDownStatus;
	}
	public void setShutDownStatus(String shutDownStatus) {
		this.shutDownStatus = shutDownStatus;
	}
	public String getSubAccountNo() {
		return subAccountNo;
	}
	public void setSubAccountNo(String subAccountNo) {
		this.subAccountNo = subAccountNo;
	}
	public String getPayAgreementNo() {
		return payAgreementNo;
	}
	public void setPayAgreementNo(String payAgreementNo) {
		this.payAgreementNo = payAgreementNo;
	}
	public String getAppFailMemo() {
		return appFailMemo;
	}
	public void setAppFailMemo(String appFailMemo) {
		this.appFailMemo = appFailMemo;
	}
	public String getApproverNo() {
		return approverNo;
	}
	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
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
	public String getInvoiceprn() {
		return invoiceprn;
	}
	public void setInvoiceprn(String invoiceprn) {
		this.invoiceprn = invoiceprn;
	}


	public Long getReqcount() {
		return reqcount;
	}
	public void setReqcount(Long reqcount) {
		this.reqcount = reqcount;
	}
	public Long getResidueCount() {
		return residueCount;
	}
	public void setResidueCount(Long residueCount) {
		this.residueCount = residueCount;
	}
	public BigDecimal getBail() {
		return bail;
	}
	public void setBail(BigDecimal bail) {
		this.bail = bail;
	}




	public String getVirType() {
		return virType;
	}
	public BigDecimal getTruckBail() {
		return truckBail;
	}
	public void setTruckBail(BigDecimal truckBail) {
		this.truckBail = truckBail;
	}
	public void setVirType(String virType) {
		this.virType = virType;
	}
	public BigDecimal getMaxacr() {
		return maxacr;
	}
	public void setMaxacr(BigDecimal maxacr) {
		this.maxacr = maxacr;
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

	public String getClearingBankName() {
		return clearingBankName;
	}

	public void setClearingBankName(String clearingBankName) {
		this.clearingBankName = clearingBankName;
	}

	public String getFocushandlearea() {
		return focushandlearea;
	}

	public void setFocushandlearea(String focushandlearea) {
		this.focushandlearea = focushandlearea;
	}

	public String getAppState() {
		return appState;
	}
	public void setAppState(String appState) {
		this.appState = appState;
	}
	public Long getApprOver() {
		return apprOver;
	}
	public void setApprOver(Long apprOver) {
		this.apprOver = apprOver;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Long getHisseqId() {
		return hisseqId;
	}
	public void setHisseqId(Long hisseqId) {
		this.hisseqId = hisseqId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getNewCardFlag() {
		return newCardFlag;
	}
	public void setNewCardFlag(String newCardFlag) {
		this.newCardFlag = newCardFlag;
	}
	public Date getAppTime() {
		return appTime;
	}
	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}
	public Date getOperTime() {
		return operTime;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Integer getDebitCardType() {
		return debitCardType;
	}

	public void setDebitCardType(Integer debitCardType) {
		this.debitCardType = debitCardType;
	}
}
