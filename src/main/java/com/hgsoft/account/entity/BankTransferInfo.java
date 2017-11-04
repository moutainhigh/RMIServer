package com.hgsoft.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class BankTransferInfo  implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8068772432534002550L;
	private Long id ;
	private Long mainId ;
	private String payName ;
	private BigDecimal transferBlanace ;
	private BigDecimal blanace;
	private String bankNo ;
	private String memo ;
	private Date arrivalTime;
	private String auditState;
	private Long hisSeqId;
	
	private Date accountTime;
	private String certificateType;
	private String certificateNo;
	private String abstracts;
	private String fileName;
	private Date dealTime;
	private Long operId;
	private String operNo;
	private String operName;
	private Long auditId;
	private String auditNo;
	private String auditName;
	private Date auditTime;
	
	private BigDecimal residulBalance;
	private BigDecimal sequenceNo;
	private String payAccount;
	
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	
	public BigDecimal getTransferBlanace() {
		return transferBlanace;
	}
	public void setTransferBlanace(BigDecimal transferBlanace) {
		this.transferBlanace = transferBlanace;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public BigDecimal getBlanace() {
		return blanace;
	}
	public void setBlanace(BigDecimal blanace) {
		this.blanace = blanace;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}
	public String getAbstracts() {
		return abstracts;
	}
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public Date getAccountTime() {
		return accountTime;
	}
	public void setAccountTime(Date accountTime) {
		this.accountTime = accountTime;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getAuditNo() {
		return auditNo;
	}
	public void setAuditNo(String auditNo) {
		this.auditNo = auditNo;
	}
	
	public BigDecimal getResidulBalance() {
		return residulBalance;
	}
	public void setResidulBalance(BigDecimal residulBalance) {
		this.residulBalance = residulBalance;
	}
	public BigDecimal getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(BigDecimal sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getPayAccount() {
		return payAccount;
	}
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	
	
	
}
