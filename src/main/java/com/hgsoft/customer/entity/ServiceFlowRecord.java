package com.hgsoft.customer.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ServiceFlowRecord implements Serializable {

	private static final long serialVersionUID = -897202689050175872L;
	private Long id;
	private Long clientID;
	private String cardTagNO;
	private String serviceFlowNO;
	private Integer servicePTypeCode;//大类
	private Integer serviceTypeCode;//小类
	private String beforeState;
	private String currState;
	private String afterState;
	private BigDecimal beforeAvailableBalance;
	private BigDecimal beforeFrozenBalance;
	private BigDecimal beforepreferentialBalance;
	private BigDecimal beforeAvailableRefundBalance;
	private BigDecimal beforeRefundApproveBalance;
	private BigDecimal currAvailableBalance;
	private BigDecimal currFrozenBalance;
	private BigDecimal currpreferentialBalance;
	private BigDecimal currAvailableRefundBalance;
	private BigDecimal currRefundApproveBalance;
	private BigDecimal afterAvailableBalance;
	private BigDecimal afterFrozenBalance;
	private BigDecimal afterpreferentialBalance;
	private BigDecimal afterAvailableRefundBalance;
	private BigDecimal afterRefundApproveBalance;
	private BigDecimal beforeBailFee;
	private BigDecimal currBailFee;
	private BigDecimal afterBailFee;
	private BigDecimal transferSum;
	private BigDecimal returnMoney;
	private String offlineTradeNO;
	private String onLineTradeNO;
	private String termCode;//终端号
	private String termTradeNo;//终端交易序号
	private String checkCode;//校验码
	private String mac;//mac码
	private String tac;//tac码
	private BigDecimal beforeAddBalance;
	private BigDecimal currAddFee;
	private BigDecimal afterAddBalance;
	private String addState;
	private String oldCardTagNO;
	private Long relationID;//关联流水序列ID
	private Long operID;
	private Long placeID;
	private Date createTime;
	private String isNeedBlacklist;
	private String isDoFlag;
	private Date doTime;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;

	public ServiceFlowRecord() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClientID() {
		return clientID;
	}
	public void setClientID(Long clientID) {
		this.clientID = clientID;
	}
	public String getCardTagNO() {
		return cardTagNO;
	}
	public void setCardTagNO(String cardTagNO) {
		this.cardTagNO = cardTagNO;
	}
	public String getServiceFlowNO() {
		return serviceFlowNO;
	}
	public void setServiceFlowNO(String serviceFlowNO) {
		this.serviceFlowNO = serviceFlowNO;
	}

	public String getBeforeState() {
		return beforeState;
	}
	public void setBeforeState(String beforeState) {
		this.beforeState = beforeState;
	}
	public String getCurrState() {
		return currState;
	}
	public void setCurrState(String currState) {
		this.currState = currState;
	}
	public String getAfterState() {
		return afterState;
	}
	public void setAfterState(String afterState) {
		this.afterState = afterState;
	}
	public BigDecimal getBeforeAvailableBalance() {
		return beforeAvailableBalance;
	}
	public void setBeforeAvailableBalance(BigDecimal beforeAvailableBalance) {
		this.beforeAvailableBalance = beforeAvailableBalance;
	}
	public BigDecimal getBeforeFrozenBalance() {
		return beforeFrozenBalance;
	}
	public void setBeforeFrozenBalance(BigDecimal beforeFrozenBalance) {
		this.beforeFrozenBalance = beforeFrozenBalance;
	}
	public BigDecimal getBeforepreferentialBalance() {
		return beforepreferentialBalance;
	}
	public void setBeforepreferentialBalance(BigDecimal beforepreferentialBalance) {
		this.beforepreferentialBalance = beforepreferentialBalance;
	}
	public BigDecimal getBeforeAvailableRefundBalance() {
		return beforeAvailableRefundBalance;
	}
	public void setBeforeAvailableRefundBalance(BigDecimal beforeAvailableRefundBalance) {
		this.beforeAvailableRefundBalance = beforeAvailableRefundBalance;
	}
	public BigDecimal getBeforeRefundApproveBalance() {
		return beforeRefundApproveBalance;
	}
	public void setBeforeRefundApproveBalance(BigDecimal beforeRefundApproveBalance) {
		this.beforeRefundApproveBalance = beforeRefundApproveBalance;
	}
	public BigDecimal getCurrAvailableBalance() {
		return currAvailableBalance;
	}
	public void setCurrAvailableBalance(BigDecimal currAvailableBalance) {
		this.currAvailableBalance = currAvailableBalance;
	}
	public BigDecimal getCurrFrozenBalance() {
		return currFrozenBalance;
	}
	public void setCurrFrozenBalance(BigDecimal currFrozenBalance) {
		this.currFrozenBalance = currFrozenBalance;
	}
	public BigDecimal getCurrpreferentialBalance() {
		return currpreferentialBalance;
	}
	public void setCurrpreferentialBalance(BigDecimal currpreferentialBalance) {
		this.currpreferentialBalance = currpreferentialBalance;
	}
	public BigDecimal getCurrAvailableRefundBalance() {
		return currAvailableRefundBalance;
	}
	public void setCurrAvailableRefundBalance(BigDecimal currAvailableRefundBalance) {
		this.currAvailableRefundBalance = currAvailableRefundBalance;
	}
	public BigDecimal getCurrRefundApproveBalance() {
		return currRefundApproveBalance;
	}
	public void setCurrRefundApproveBalance(BigDecimal currRefundApproveBalance) {
		this.currRefundApproveBalance = currRefundApproveBalance;
	}
	public BigDecimal getAfterAvailableBalance() {
		return afterAvailableBalance;
	}
	public void setAfterAvailableBalance(BigDecimal afterAvailableBalance) {
		this.afterAvailableBalance = afterAvailableBalance;
	}
	public BigDecimal getAfterFrozenBalance() {
		return afterFrozenBalance;
	}
	public void setAfterFrozenBalance(BigDecimal afterFrozenBalance) {
		this.afterFrozenBalance = afterFrozenBalance;
	}
	public BigDecimal getAfterpreferentialBalance() {
		return afterpreferentialBalance;
	}
	public void setAfterpreferentialBalance(BigDecimal afterpreferentialBalance) {
		this.afterpreferentialBalance = afterpreferentialBalance;
	}
	public BigDecimal getAfterAvailableRefundBalance() {
		return afterAvailableRefundBalance;
	}
	public void setAfterAvailableRefundBalance(
			BigDecimal afterAvailableRefundBalance) {
		this.afterAvailableRefundBalance = afterAvailableRefundBalance;
	}
	public BigDecimal getAfterRefundApproveBalance() {
		return afterRefundApproveBalance;
	}
	public void setAfterRefundApproveBalance(BigDecimal afterRefundApproveBalance) {
		this.afterRefundApproveBalance = afterRefundApproveBalance;
	}
	public BigDecimal getBeforeBailFee() {
		return beforeBailFee;
	}
	public void setBeforeBailFee(BigDecimal beforeBailFee) {
		this.beforeBailFee = beforeBailFee;
	}
	public BigDecimal getCurrBailFee() {
		return currBailFee;
	}
	public void setCurrBailFee(BigDecimal currBailFee) {
		this.currBailFee = currBailFee;
	}
	public BigDecimal getAfterBailFee() {
		return afterBailFee;
	}
	public void setAfterBailFee(BigDecimal afterBailFee) {
		this.afterBailFee = afterBailFee;
	}
	public String getOfflineTradeNO() {
		return offlineTradeNO;
	}
	public void setOfflineTradeNO(String offlineTradeNO) {
		this.offlineTradeNO = offlineTradeNO;
	}
	public String getOnLineTradeNO() {
		return onLineTradeNO;
	}
	public void setOnLineTradeNO(String onLineTradeNO) {
		this.onLineTradeNO = onLineTradeNO;
	}
	public BigDecimal getBeforeAddBalance() {
		return beforeAddBalance;
	}
	public void setBeforeAddBalance(BigDecimal beforeAddBalance) {
		this.beforeAddBalance = beforeAddBalance;
	}
	public BigDecimal getCurrAddFee() {
		return currAddFee;
	}
	public void setCurrAddFee(BigDecimal currAddFee) {
		this.currAddFee = currAddFee;
	}
	public BigDecimal getAfterAddBalance() {
		return afterAddBalance;
	}
	public void setAfterAddBalance(BigDecimal afterAddBalance) {
		this.afterAddBalance = afterAddBalance;
	}
	public String getAddState() {
		return addState;
	}
	public void setAddState(String addState) {
		this.addState = addState;
	}
	
	public String getOldCardTagNO() {
		return oldCardTagNO;
	}

	public void setOldCardTagNO(String oldCardTagNO) {
		this.oldCardTagNO = oldCardTagNO;
	}

	public Long getOperID() {
		return operID;
	}
	public void setOperID(Long operID) {
		this.operID = operID;
	}
	public Long getPlaceID() {
		return placeID;
	}
	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIsNeedBlacklist() {
		return isNeedBlacklist;
	}
	public void setIsNeedBlacklist(String isNeedBlacklist) {
		this.isNeedBlacklist = isNeedBlacklist;
	}
	public String getIsDoFlag() {
		return isDoFlag;
	}
	public void setIsDoFlag(String isDoFlag) {
		this.isDoFlag = isDoFlag;
	}
	public Date getDoTime() {
		return doTime;
	}
	public void setDoTime(Date doTime) {
		this.doTime = doTime;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getTermTradeNo() {
		return termTradeNo;
	}

	public void setTermTradeNo(String termTradeNo) {
		this.termTradeNo = termTradeNo;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public Long getRelationID() {
		return relationID;
	}

	public void setRelationID(Long relationID) {
		this.relationID = relationID;
	}

	public Integer getServicePTypeCode() {
		return servicePTypeCode;
	}

	public void setServicePTypeCode(Integer servicePTypeCode) {
		this.servicePTypeCode = servicePTypeCode;
	}

	public Integer getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(Integer serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
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

}
