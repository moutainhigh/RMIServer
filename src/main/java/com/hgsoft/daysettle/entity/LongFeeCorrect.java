package com.hgsoft.daysettle.entity;

import java.util.Date;

public class LongFeeCorrect implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2023660699986495848L;

	private Long placeId;
	
	private String placeNo;
	
	private String placeName;
	
	private String balanceType;
	
	private String operName;
	
	private String payerMember;
	
	private String actualMember;
	
	private Double takeBalance;
	
	private Date settletTime;
	
	private String memo;

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

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getPayerMember() {
		return payerMember;
	}

	public void setPayerMember(String payerMember) {
		this.payerMember = payerMember;
	}

	public String getActualMember() {
		return actualMember;
	}

	public void setActualMember(String actualMember) {
		this.actualMember = actualMember;
	}

	public Double getTakeBalance() {
		return takeBalance;
	}

	public void setTakeBalance(Double takeBalance) {
		this.takeBalance = takeBalance;
	}

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getSettletTime() {
		return settletTime;
	}

	public void setSettletTime(Date settletTime) {
		this.settletTime = settletTime;
	}
	
	

}
