package com.hgsoft.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AccountFundChange  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1585743184174631981L;
	private Long id;
	private String flowNo;
	private String changeType;
	private BigDecimal beforeBalance;
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
	private BigDecimal afterBalance;
	private BigDecimal afterAvailableBalance;
	private BigDecimal afterFrozenBalance;
	private BigDecimal afterpreferentialBalance;
	private BigDecimal afterAvailableRefundBalance;
	private BigDecimal afterRefundApproveBalance;
	private Long chgOperID;
	private Long chgPlaceID;
	private Date chgDate;
	private String memo;
	private Long mainId;
	private Date createTime;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public BigDecimal getBeforeBalance() {
		return beforeBalance;
	}
	public void setBeforeBalance(BigDecimal beforeBalance) {
		this.beforeBalance = beforeBalance;
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
	public void setBeforeAvailableRefundBalance(
			BigDecimal beforeAvailableRefundBalance) {
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
	public BigDecimal getAfterBalance() {
		return afterBalance;
	}
	public void setAfterBalance(BigDecimal afterBalance) {
		this.afterBalance = afterBalance;
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
	public Long getChgOperID() {
		return chgOperID;
	}
	public void setChgOperID(Long chgOperID) {
		this.chgOperID = chgOperID;
	}
	public Long getChgPlaceID() {
		return chgPlaceID;
	}
	public void setChgPlaceID(Long chgPlaceID) {
		this.chgPlaceID = chgPlaceID;
	}
	public Date getChgDate() {
		return chgDate;
	}
	public void setChgDate(Date chgDate) {
		this.chgDate = chgDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	
}
