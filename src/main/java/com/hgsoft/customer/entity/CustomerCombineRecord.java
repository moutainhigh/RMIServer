package com.hgsoft.customer.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerCombineRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1942260603544236678L;
	
	private Long id;
	private Long beforeCustomerId;
	private Long afterCustomerId;
	
	private String beforeOrgan;
	private String beforeIdType;
	private String beforeIdCode;
	private BigDecimal beforeBalance;
	private BigDecimal beforeAvailableBalance;
	private BigDecimal beforePreferentialBalance;
	private BigDecimal beforeAvailableRefundBalance;
	private BigDecimal beforeRefundApproveBalance;
	private BigDecimal beforeFrozenBalance;
	
	private String afterOrgan;
	private String afterIdType;
	private String afterIdCode;
	private BigDecimal afterBalance;
	private BigDecimal afterAvailableBalance;
	private BigDecimal afterPreferentialBalance;
	private BigDecimal afterAvailableRefundBalance;
	private BigDecimal afterRefundApproveBalance;
	private BigDecimal afterFrozenBalance;
	
	private Date operTime;
	private Long operId;
	private String operNo;
	private String operName;
	private Long placeId;
	private String placeNo;
	private String placeName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBeforeCustomerId() {
		return beforeCustomerId;
	}
	public void setBeforeCustomerId(Long beforeCustomerId) {
		this.beforeCustomerId = beforeCustomerId;
	}
	public Long getAfterCustomerId() {
		return afterCustomerId;
	}
	public void setAfterCustomerId(Long afterCustomerId) {
		this.afterCustomerId = afterCustomerId;
	}
	public String getBeforeOrgan() {
		return beforeOrgan;
	}
	public void setBeforeOrgan(String beforeOrgan) {
		this.beforeOrgan = beforeOrgan;
	}
	public String getBeforeIdType() {
		return beforeIdType;
	}
	public void setBeforeIdType(String beforeIdType) {
		this.beforeIdType = beforeIdType;
	}
	public String getBeforeIdCode() {
		return beforeIdCode;
	}
	public void setBeforeIdCode(String beforeIdCode) {
		this.beforeIdCode = beforeIdCode;
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
	public BigDecimal getBeforePreferentialBalance() {
		return beforePreferentialBalance;
	}
	public void setBeforePreferentialBalance(BigDecimal beforePreferentialBalance) {
		this.beforePreferentialBalance = beforePreferentialBalance;
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
	public BigDecimal getBeforeFrozenBalance() {
		return beforeFrozenBalance;
	}
	public void setBeforeFrozenBalance(BigDecimal beforeFrozenBalance) {
		this.beforeFrozenBalance = beforeFrozenBalance;
	}
	public String getAfterOrgan() {
		return afterOrgan;
	}
	public void setAfterOrgan(String afterOrgan) {
		this.afterOrgan = afterOrgan;
	}
	public String getAfterIdType() {
		return afterIdType;
	}
	public void setAfterIdType(String afterIdType) {
		this.afterIdType = afterIdType;
	}
	public String getAfterIdCode() {
		return afterIdCode;
	}
	public void setAfterIdCode(String afterIdCode) {
		this.afterIdCode = afterIdCode;
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
	public BigDecimal getAfterPreferentialBalance() {
		return afterPreferentialBalance;
	}
	public void setAfterPreferentialBalance(BigDecimal afterPreferentialBalance) {
		this.afterPreferentialBalance = afterPreferentialBalance;
	}
	public BigDecimal getAfterAvailableRefundBalance() {
		return afterAvailableRefundBalance;
	}
	public void setAfterAvailableRefundBalance(BigDecimal afterAvailableRefundBalance) {
		this.afterAvailableRefundBalance = afterAvailableRefundBalance;
	}
	public BigDecimal getAfterRefundApproveBalance() {
		return afterRefundApproveBalance;
	}
	public void setAfterRefundApproveBalance(BigDecimal afterRefundApproveBalance) {
		this.afterRefundApproveBalance = afterRefundApproveBalance;
	}
	public BigDecimal getAfterFrozenBalance() {
		return afterFrozenBalance;
	}
	public void setAfterFrozenBalance(BigDecimal afterFrozenBalance) {
		this.afterFrozenBalance = afterFrozenBalance;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
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
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
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
