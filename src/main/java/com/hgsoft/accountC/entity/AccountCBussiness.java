package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountCBussiness implements Serializable {

	private static final long serialVersionUID = -5778463943390569535L;
	private Long id;
	private Long userId;
	private String cardNo;
	private String oldCardNo;
	private String state;
	private BigDecimal realPrice;
	private Integer receiptPrintTimes;
	private String lastState;
	private Date tradeTime;
	private Long operId;
	private Long placeId;
	private Long oldUserId;
	private Long accountId;
	private Long oldAccountId;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	private String lockType;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long businessId;
	private Long accountCApplyHisID;
	private BigDecimal bailFee;
	private BigDecimal bailFrozenBalance;
	private String faultType;
	private String reason;
	private Long faultTypeId;//2017-8-17 15:09:47 hzw添加
	private Long faultReasonId;

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	private String suit;

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public BigDecimal getBailFee() {
		return bailFee;
	}

	public void setBailFee(BigDecimal bailFee) {
		this.bailFee = bailFee;
	}

	public BigDecimal getBailFrozenBalance() {
		return bailFrozenBalance;
	}

	public void setBailFrozenBalance(BigDecimal bailFrozenBalance) {
		this.bailFrozenBalance = bailFrozenBalance;
	}

	public Long getAccountCApplyHisID() {
		return accountCApplyHisID;
	}

	public void setAccountCApplyHisID(Long accountCApplyHisID) {
		this.accountCApplyHisID = accountCApplyHisID;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOldCardNo() {
		return oldCardNo;
	}

	public void setOldCardNo(String oldCardNo) {
		this.oldCardNo = oldCardNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public Integer getReceiptPrintTimes() {
		return receiptPrintTimes;
	}

	public void setReceiptPrintTimes(Integer receiptPrintTimes) {
		this.receiptPrintTimes = receiptPrintTimes;
	}

	public String getLastState() {
		return lastState;
	}

	public void setLastState(String lastState) {
		this.lastState = lastState;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
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

	public Long getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(Long oldUserId) {
		this.oldUserId = oldUserId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getOldAccountId() {
		return oldAccountId;
	}

	public void setOldAccountId(Long oldAccountId) {
		this.oldAccountId = oldAccountId;
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

	public Long getFaultTypeId() {
		return faultTypeId;
	}

	public void setFaultTypeId(Long faultTypeId) {
		this.faultTypeId = faultTypeId;
	}

	public Long getFaultReasonId() {
		return faultReasonId;
	}

	public void setFaultReasonId(Long faultReasonId) {
		this.faultReasonId = faultReasonId;
	}


}
