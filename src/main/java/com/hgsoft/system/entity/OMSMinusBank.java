package com.hgsoft.system.entity;

import java.util.Date;

public class OMSMinusBank implements java.io.Serializable {

	private static final long serialVersionUID = -7893436259235026875L;
	private Long id;
	private String clearingBankCode;
	private String focusHandleCode;
	private String focusHandleArea;
	private String bankName;
	private String memo;
	private String state;
	private Long operId;
	private String operName;
	private Date operTime;
	private Long placeId;
	private String placeName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClearingBankCode() {
		return clearingBankCode;
	}
	public void setClearingBankCode(String clearingBankCode) {
		this.clearingBankCode = clearingBankCode;
	}
	public String getFocusHandleCode() {
		return focusHandleCode;
	}
	public void setFocusHandleCode(String focusHandleCode) {
		this.focusHandleCode = focusHandleCode;
	}
	public String getFocusHandleArea() {
		return focusHandleArea;
	}
	public void setFocusHandleArea(String focusHandleArea) {
		this.focusHandleArea = focusHandleArea;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	
}
