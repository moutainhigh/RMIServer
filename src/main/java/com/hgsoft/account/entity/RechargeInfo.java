package com.hgsoft.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeInfo   implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8204136696204745169L;
	private Long  id;
	private Long  mainId ;
	private Long  mainAccountId;
	private Long  correctId;
	private Long  bankTransferId ;
	private Long refundID;
	private String  payMember ;
	private String  payMentType ;
	private String  payMentNo ;
	private String  transactionType;
	private BigDecimal  balance ;
	private BigDecimal  availableBalance ;
	private BigDecimal  preferentialBalance;
	private BigDecimal  frozenBalance;
	private BigDecimal  availableRefundBalance ;
	private BigDecimal  refundApproveBalance ;
	private String  state ;
	private BigDecimal  takeBalance;
	private Long  posId ;
	private String  memo;
	private Long operId ;
	private Long placeId ;
	private Date  operTime ;
	private String isCorrect;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long  prepaidCBussinessId;
	private String voucherNo;//缴款单凭证编号
	
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public Long getPrepaidCBussinessId() {
		return prepaidCBussinessId;
	}
	public void setPrepaidCBussinessId(Long prepaidCBussinessId) {
		this.prepaidCBussinessId = prepaidCBussinessId;
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
	
	public String getIsDaySet() {
		return isDaySet;
	}
	public void setIsDaySet(String isDaySet) {
		this.isDaySet = isDaySet;
	}
	public String getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}
	public Date getSettletTime() {
		return settletTime;
	}
	public void setSettletTime(Date settletTime) {
		this.settletTime = settletTime;
	}
	public String getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(String isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public Long getRefundID() {
		return refundID;
	}
	public void setRefundID(Long refundID) {
		this.refundID = refundID;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Long getMainAccountId() {
		return mainAccountId;
	}
	public void setMainAccountId(Long mainAccountId) {
		this.mainAccountId = mainAccountId;
	}
	public Long getCorrectId() {
		return correctId;
	}
	public void setCorrectId(Long correctId) {
		this.correctId = correctId;
	}
	public Long getBankTransferId() {
		return bankTransferId;
	}
	public void setBankTransferId(Long bankTransferId) {
		this.bankTransferId = bankTransferId;
	}
	public Long getPosId() {
		return posId;
	}
	public void setPosId(Long posId) {
		this.posId = posId;
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
	public String getPayMember() {
		return payMember;
	}
	public void setPayMember(String payMember) {
		this.payMember = payMember;
	}
	public String getPayMentType() {
		return payMentType;
	}
	public void setPayMentType(String payMentType) {
		this.payMentType = payMentType;
	}
	public String getPayMentNo() {
		return payMentNo;
	}
	public void setPayMentNo(String payMentNo) {
		this.payMentNo = payMentNo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getTakeBalance() {
		return takeBalance;
	}
	public void setTakeBalance(BigDecimal takeBalance) {
		this.takeBalance = takeBalance;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	
	
}
