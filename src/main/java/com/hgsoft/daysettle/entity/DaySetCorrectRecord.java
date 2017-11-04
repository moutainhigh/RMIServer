package com.hgsoft.daysettle.entity;

import java.util.Date;

public class DaySetCorrectRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2262818967079319741L;

	private Long id;
	
	private Long daySetID;
	
	private String settleDay;
	
	private String payee;
	
	private String actualPayee;
	
	private Double correctFee;
	
	private String correctType;
	
	private Date correctTime;
	
	private Long operid;
	
	private String operCode;
	
	private String operName;
	
	private Long placeid;
	
	private String placeCode;
	
	private String placeName;
	
	private String payType;
	
	private String waterNo;
	
	private String memo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDaySetID() {
		return daySetID;
	}

	public void setDaySetID(Long daySetID) {
		this.daySetID = daySetID;
	}

	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getActualPayee() {
		return actualPayee;
	}

	public void setActualPayee(String actualPayee) {
		this.actualPayee = actualPayee;
	}

	public Double getCorrectFee() {
		return correctFee;
	}

	public void setCorrectFee(Double correctFee) {
		this.correctFee = correctFee;
	}

	public String getCorrectType() {
		return correctType;
	}

	public void setCorrectType(String correctType) {
		this.correctType = correctType;
	}

	public Date getCorrectTime() {
		return correctTime;
	}

	public void setCorrectTime(Date correctTime) {
		this.correctTime = correctTime;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Long getPlaceid() {
		return placeid;
	}

	public void setPlaceid(Long placeid) {
		this.placeid = placeid;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getWaterNo() {
		return waterNo;
	}

	public void setWaterNo(String waterNo) {
		this.waterNo = waterNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
