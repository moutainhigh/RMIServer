package com.hgsoft.associateAcount.entity;
import java.util.*;
import java.math.BigDecimal;


import java.io.Serializable;


public class AccardBussiness  implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1977222530586138713L;
	private Long placeId;
	private Long operId;
	private Date cancelDate;
	private Date endDate;
	private Date startDate;
	private Date tradeTime;
	private String lastState;
	private Integer receiptPrintTimes;
	private BigDecimal realPrice;
	private String state;
	private String cardNo;
	private Long customerId;
	private Long id;
	private String memo;
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getLastState() {
		return lastState;
	}
	public void setLastState(String lastState) {
		this.lastState = lastState;
	}
	public Integer getReceiptPrintTimes() {
		return receiptPrintTimes;
	}
	public void setReceiptPrintTimes(Integer receiptPrintTimes) {
		this.receiptPrintTimes = receiptPrintTimes;
	}
	public BigDecimal getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}

