package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PrepaidcBussiness entity. @author MyEclipse Persistence Tools
 */

public class PrepaidCBussiness implements java.io.Serializable {

	private static final long serialVersionUID = -5858688022515766455L;
	private Long id;
	private Long userid;
	private String cardno;
	private String oldCardno;
	private String state;
	private BigDecimal realprice;
	private BigDecimal faceValue;
	private BigDecimal transferSum;
	private BigDecimal returnMoney;
	private String termcode;
	private String termtradeno;
	private String offlinetradeno;
	private String onlinetradeno;
	private String checkcode;
	private String mac;
	private String tac;
	private String invoicestate;
	private Integer printtimes;
	private Integer receiptprinttimes;
	private String tradestate;
	private Date tradetime;
	private Long operid;
	private Long placeid;
	private BigDecimal beforebalance;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	private Long flowRecordId;
	private BigDecimal balance;

	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long businessId;
	private String memo;
	private String faultType;
	private String reason;
	private Long faultTypeId;//2017-8-17 15:09:47 hzw添加
	private Long faultReasonId;
	private String lockType;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	private String suit;

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	private String notCardState;

	public String getNotCardState() {
		return notCardState;
	}

	public void setNotCardState(String notCardState) {
		this.notCardState = notCardState;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
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

	public Long getFlowRecordId() {
		return flowRecordId;
	}

	public void setFlowRecordId(Long flowRecordId) {
		this.flowRecordId = flowRecordId;
	}

	public BigDecimal getBeforebalance() {
		return beforebalance;
	}

	public void setBeforebalance(BigDecimal beforebalance) {
		this.beforebalance = beforebalance;
	}

	// Constructors

	/** default constructor */
	public PrepaidCBussiness() {
	}

	/** full constructor */
	public PrepaidCBussiness(Long userid, String cardno, String state,
			BigDecimal realprice, BigDecimal faceValue, String termcode,
			String termtradeno, String offlinetradeno, String onlinetradeno,
			String checkcode, String mac, String tac, String invoicestate,
			Integer printtimes, Integer receiptprinttimes, String tradestate,
			Date tradetime, Long operid, Long placeid) {
		this.userid = userid;
		this.cardno = cardno;
		this.state = state;
		this.realprice = realprice;
		this.faceValue = faceValue;
		this.termcode = termcode;
		this.termtradeno = termtradeno;
		this.offlinetradeno = offlinetradeno;
		this.onlinetradeno = onlinetradeno;
		this.checkcode = checkcode;
		this.mac = mac;
		this.tac = tac;
		this.invoicestate = invoicestate;
		this.printtimes = printtimes;
		this.receiptprinttimes = receiptprinttimes;
		this.tradestate = tradestate;
		this.tradetime = tradetime;
		this.operid = operid;
		this.placeid = placeid;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getRealprice() {
		return this.realprice;
	}

	public void setRealprice(BigDecimal realprice) {
		this.realprice = realprice;
	}

	public String getTermcode() {
		return this.termcode;
	}

	public void setTermcode(String termcode) {
		this.termcode = termcode;
	}

	public String getTermtradeno() {
		return this.termtradeno;
	}

	public void setTermtradeno(String termtradeno) {
		this.termtradeno = termtradeno;
	}

	public String getOfflinetradeno() {
		return this.offlinetradeno;
	}

	public void setOfflinetradeno(String offlinetradeno) {
		this.offlinetradeno = offlinetradeno;
	}

	public String getOnlinetradeno() {
		return this.onlinetradeno;
	}

	public void setOnlinetradeno(String onlinetradeno) {
		this.onlinetradeno = onlinetradeno;
	}

	public String getCheckcode() {
		return this.checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getTac() {
		return this.tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getInvoicestate() {
		return this.invoicestate;
	}

	public void setInvoicestate(String invoicestate) {
		this.invoicestate = invoicestate;
	}

	public Integer getPrinttimes() {
		return this.printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public Integer getReceiptprinttimes() {
		return this.receiptprinttimes;
	}

	public void setReceiptprinttimes(Integer receiptprinttimes) {
		this.receiptprinttimes = receiptprinttimes;
	}

	public String getTradestate() {
		return this.tradestate;
	}

	public void setTradestate(String tradestate) {
		this.tradestate = tradestate;
	}

	public Date getTradetime() {
		return this.tradetime;
	}

	public void setTradetime(Date tradetime) {
		this.tradetime = tradetime;
	}

	public Long getOperid() {
		return this.operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public Long getPlaceid() {
		return this.placeid;
	}

	public void setPlaceid(Long placeid) {
		this.placeid = placeid;
	}

	public String getOldCardno() {
		return oldCardno;
	}

	public void setOldCardno(String oldCardno) {
		this.oldCardno = oldCardno;
	}

	public BigDecimal getTransferSum() {
		return transferSum;
	}

	public void setTransferSum(BigDecimal transferSum) {
		this.transferSum = transferSum;
	}

	public BigDecimal getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}

	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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

	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}
}