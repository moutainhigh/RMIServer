package com.hgsoft.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RefundInfoHis  implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3585070246988785418L;
	private Long id ;
	private Long mainId ;
	private Long mainAccountId ;
	private String refundType ;
	private BigDecimal balance ;
	private BigDecimal availableBalance ;
	private BigDecimal preferentialBalance;
	private BigDecimal frozenBalance;
	private BigDecimal availableRefundBalance;
	private BigDecimal refundApproveBalance;
	private BigDecimal currentRefundBalance;
	private String bankNo;
	private String bankMember;
	private String bankOpenBranches ;
	private Long operId;
	private Date refundApplyTime;
	private Long refundApplyOper;
	private Long auditId ;
	private Date auditTime ;
	private String auditStatus;
	private Long refundId ;
	private Date refundTime ;
	private String memo ;
	private Long hisSeqId ;
	private Date createDate;
	private String createReason;
	
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long placeID;
	
	private String AuditNo;
	private String AuditName;
	
	private String refundNo;
	private String refundName;
	private String cardNo;
	
	private BigDecimal transferAmt;
	private BigDecimal returnAmt;
	private BigDecimal cardAmt;
	private BigDecimal cardSystemAmt;
	private BigDecimal checkAmt;
	private String differentInfo;
	private String settleAutdiInfo;
	private Long settleId;
	private String settleNo;
	private String settleName;
	private Date settleTime;
	private BigDecimal finalRefundAmt;
	private BigDecimal personalCorrectAmt;
	private String expireFlag;
	private Long directorAuditId;
	private String directorAuditNo;
	private String directorAuditName;
	private Date directorAuditTime;
	private Date waitSettleAuditTime;
	private Long bussinessPlaceId;
	private String bankAccount;
	private String bailBackReason;
	
	
	public String getBailBackReason() {
		return bailBackReason;
	}
	public void setBailBackReason(String bailBackReason) {
		this.bailBackReason = bailBackReason;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Long getBussinessPlaceId() {
		return bussinessPlaceId;
	}
	public void setBussinessPlaceId(Long bussinessPlaceId) {
		this.bussinessPlaceId = bussinessPlaceId;
	}
	public Date getWaitSettleAuditTime() {
		return waitSettleAuditTime;
	}
	public void setWaitSettleAuditTime(Date waitSettleAuditTime) {
		this.waitSettleAuditTime = waitSettleAuditTime;
	}
	public BigDecimal getTransferAmt() {
		return transferAmt;
	}
	public void setTransferAmt(BigDecimal transferAmt) {
		this.transferAmt = transferAmt;
	}
	public BigDecimal getReturnAmt() {
		return returnAmt;
	}
	public void setReturnAmt(BigDecimal returnAmt) {
		this.returnAmt = returnAmt;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public BigDecimal getCardAmt() {
		return cardAmt;
	}
	public void setCardAmt(BigDecimal cardAmt) {
		this.cardAmt = cardAmt;
	}
	public BigDecimal getCardSystemAmt() {
		return cardSystemAmt;
	}
	public void setCardSystemAmt(BigDecimal cardSystemAmt) {
		this.cardSystemAmt = cardSystemAmt;
	}
	public BigDecimal getCheckAmt() {
		return checkAmt;
	}
	public void setCheckAmt(BigDecimal checkAmt) {
		this.checkAmt = checkAmt;
	}
	public String getDifferentInfo() {
		return differentInfo;
	}
	public void setDifferentInfo(String differentInfo) {
		this.differentInfo = differentInfo;
	}
	public String getSettleAutdiInfo() {
		return settleAutdiInfo;
	}
	public void setSettleAutdiInfo(String settleAutdiInfo) {
		this.settleAutdiInfo = settleAutdiInfo;
	}
	public Long getSettleId() {
		return settleId;
	}
	public void setSettleId(Long settleId) {
		this.settleId = settleId;
	}
	public String getSettleNo() {
		return settleNo;
	}
	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}
	public String getSettleName() {
		return settleName;
	}
	public void setSettleName(String settleName) {
		this.settleName = settleName;
	}
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	public BigDecimal getFinalRefundAmt() {
		return finalRefundAmt;
	}
	public void setFinalRefundAmt(BigDecimal finalRefundAmt) {
		this.finalRefundAmt = finalRefundAmt;
	}
	public BigDecimal getPersonalCorrectAmt() {
		return personalCorrectAmt;
	}
	public void setPersonalCorrectAmt(BigDecimal personalCorrectAmt) {
		this.personalCorrectAmt = personalCorrectAmt;
	}
	public String getExpireFlag() {
		return expireFlag;
	}
	public void setExpireFlag(String expireFlag) {
		this.expireFlag = expireFlag;
	}
	public Long getDirectorAuditId() {
		return directorAuditId;
	}
	public void setDirectorAuditId(Long directorAuditId) {
		this.directorAuditId = directorAuditId;
	}
	public String getDirectorAuditNo() {
		return directorAuditNo;
	}
	public void setDirectorAuditNo(String directorAuditNo) {
		this.directorAuditNo = directorAuditNo;
	}
	public String getDirectorAuditName() {
		return directorAuditName;
	}
	public void setDirectorAuditName(String directorAuditName) {
		this.directorAuditName = directorAuditName;
	}
	public Date getDirectorAuditTime() {
		return directorAuditTime;
	}
	public void setDirectorAuditTime(Date directorAuditTime) {
		this.directorAuditTime = directorAuditTime;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getRefundName() {
		return refundName;
	}
	public void setRefundName(String refundName) {
		this.refundName = refundName;
	}
	private String refundFailReason;//审批状态为财务退款失败，其原因
	
	public String getRefundFailReason() {
		return refundFailReason;
	}
	public void setRefundFailReason(String refundFailReason) {
		this.refundFailReason = refundFailReason;
	}
	public Long getPlaceID() {
		return placeID;
	}
	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainAccountId() {
		return mainAccountId;
	}
	public void setMainAccountId(Long mainAccountId) {
		this.mainAccountId = mainAccountId;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}
	public BigDecimal getPreferentialBalance() {
		return preferentialBalance;
	}
	public void setPreferentialBalance(BigDecimal preferentialBalance) {
		this.preferentialBalance = preferentialBalance;
	}
	public BigDecimal getFrozenBalance() {
		return frozenBalance;
	}
	public void setFrozenBalance(BigDecimal frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	public BigDecimal getAvailableRefundBalance() {
		return availableRefundBalance;
	}
	public void setAvailableRefundBalance(BigDecimal availableRefundBalance) {
		this.availableRefundBalance = availableRefundBalance;
	}
	public BigDecimal getRefundApproveBalance() {
		return refundApproveBalance;
	}
	public void setRefundApproveBalance(BigDecimal refundApproveBalance) {
		this.refundApproveBalance = refundApproveBalance;
	}
	public BigDecimal getCurrentRefundBalance() {
		return currentRefundBalance;
	}
	public void setCurrentRefundBalance(BigDecimal currentRefundBalance) {
		this.currentRefundBalance = currentRefundBalance;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankMember() {
		return bankMember;
	}
	public void setBankMember(String bankMember) {
		this.bankMember = bankMember;
	}
	public String getBankOpenBranches() {
		return bankOpenBranches;
	}
	public void setBankOpenBranches(String bankOpenBranches) {
		this.bankOpenBranches = bankOpenBranches;
	}
	
	public Date getRefundApplyTime() {
		return refundApplyTime;
	}
	public void setRefundApplyTime(Date refundApplyTime) {
		this.refundApplyTime = refundApplyTime;
	}
	
	public Long getRefundApplyOper() {
		return refundApplyOper;
	}
	public void setRefundApplyOper(Long refundApplyOper) {
		this.refundApplyOper = refundApplyOper;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	public Long getRefundId() {
		return refundId;
	}
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateReason() {
		return createReason;
	}
	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}
	public String getAuditNo() {
		return AuditNo;
	}
	public void setAuditNo(String auditNo) {
		AuditNo = auditNo;
	}
	public String getAuditName() {
		return AuditName;
	}
	public void setAuditName(String auditName) {
		AuditName = auditName;
	}
	
}
