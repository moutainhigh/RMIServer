package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScreturnSend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024892619470380480L;
	private Long id               ;
	private String cardNo         ;
	private String feeType        ;
	private BigDecimal returnFee   ;
	private String state          ;
	private Long bussinessID      ;
	private Date returnTime       ;
	private Long placeID          ;
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
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public BigDecimal getReturnFee() {
		return returnFee;
	}
	public void setReturnFee(BigDecimal returnFee) {
		this.returnFee = returnFee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getBussinessID() {
		return bussinessID;
	}
	public void setBussinessID(Long bussinessID) {
		this.bussinessID = bussinessID;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public Long getPlaceID() {
		return placeID;
	}
	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
	}
	
}
