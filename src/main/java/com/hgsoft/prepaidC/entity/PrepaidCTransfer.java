package com.hgsoft.prepaidC.entity;

import java.io.Serializable;
import java.util.Date;

public class PrepaidCTransfer implements Serializable {

	private static final long serialVersionUID = -4563096465782708724L;
	private Long id;
	private Long oldAccountId;
	private Long oldCustomerId;
	private Long newAccountId;
	private Long newCustomerId;
	private Date operTime;
	private Long operId;
	private Long placeId;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long newCusHisId;
	private String newCusInvoiceTitle;
	
	public Long getNewCusHisId() {
		return newCusHisId;
	}
	public void setNewCusHisId(Long newCusHisId) {
		this.newCusHisId = newCusHisId;
	}
	public String getNewCusInvoiceTitle() {
		return newCusInvoiceTitle;
	}
	public void setNewCusInvoiceTitle(String newCusInvoiceTitle) {
		this.newCusInvoiceTitle = newCusInvoiceTitle;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOldAccountId() {
		return oldAccountId;
	}
	public void setOldAccountId(Long oldAccountId) {
		this.oldAccountId = oldAccountId;
	}
	public Long getOldCustomerId() {
		return oldCustomerId;
	}
	public void setOldCustomerId(Long oldCustomerId) {
		this.oldCustomerId = oldCustomerId;
	}
	public Long getNewAccountId() {
		return newAccountId;
	}
	public void setNewAccountId(Long newAccountId) {
		this.newAccountId = newAccountId;
	}
	public Long getNewCustomerId() {
		return newCustomerId;
	}
	public void setNewCustomerId(Long newCustomerId) {
		this.newCustomerId = newCustomerId;
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
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	
}
