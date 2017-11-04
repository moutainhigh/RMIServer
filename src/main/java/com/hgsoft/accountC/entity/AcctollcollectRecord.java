package com.hgsoft.accountC.entity;

import java.math.BigDecimal;
import java.util.Date;


public class AcctollcollectRecord implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4453750509105644713L;
	private Long id;
	private Long userId;
	private Long accountId;
	private BigDecimal dealFee;
	private BigDecimal lateFee;
	private BigDecimal tollFee;
	private Date genTime;
	private String state;
	private Date payTime;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public BigDecimal getDealFee() {
		return dealFee;
	}
	public void setDealFee(BigDecimal dealFee) {
		this.dealFee = dealFee;
	}
	public BigDecimal getLateFee() {
		return lateFee;
	}
	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}
	public BigDecimal getTollFee() {
		return tollFee;
	}
	public void setTollFee(BigDecimal tollFee) {
		this.tollFee = tollFee;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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