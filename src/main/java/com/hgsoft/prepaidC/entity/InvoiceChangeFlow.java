package com.hgsoft.prepaidC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvoiceChangeFlow  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7957069569211594701L;
	private Long id;
	private String cardNo;
	private Long customerId;
	private String state;
	private BigDecimal syscost;
	private BigDecimal realcost;
	private BigDecimal tranfercost;
	private BigDecimal tranferrealcost;
	private String month;
	private Date settleDate;
	private Date realDate;
	private Long operId;
	private Long placeId;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getSyscost() {
		return syscost;
	}
	public void setSyscost(BigDecimal syscost) {
		this.syscost = syscost;
	}
	public BigDecimal getRealcost() {
		return realcost;
	}
	public void setRealcost(BigDecimal realcost) {
		this.realcost = realcost;
	}
	public BigDecimal getTranfercost() {
		return tranfercost;
	}
	public void setTranfercost(BigDecimal tranfercost) {
		this.tranfercost = tranfercost;
	}
	public BigDecimal getTranferrealcost() {
		return tranferrealcost;
	}
	public void setTranferrealcost(BigDecimal tranferrealcost) {
		this.tranferrealcost = tranferrealcost;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public Date getRealDate() {
		return realDate;
	}
	public void setRealDate(Date realDate) {
		this.realDate = realDate;
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
