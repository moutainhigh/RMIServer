package com.hgsoft.macao.entity;

import java.util.Date;

public class MacaoCancleReqInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 844452912665750542L;

	private Long id;
	private Date reqTime;
	private String reqSN;
	private String bankAccountNumber;
	private String etcCardNumber;
	private String idCardType;
	private String idCardNumber;
	private String carNumber;
	private String state;
	
	private Date reqOperTime;
	private Long reqOperId;
	private String reqOperNo;
	private String reqOperName;
	private Long reqPlaceId;
	private String reqPlaceNo;
	private String reqPlaceName;
	
	private Date sureTime;
	private Long sureOperId;
	private String sureOperNo;
	private String sureOperName;
	private Long surePlaceId;
	private String surePlaceNo;
	private String surePlaceName;
	
	private String resSN;
	private Date restime;
	
	
	public String getResSN() {
		return resSN;
	}
	public void setResSN(String resSN) {
		this.resSN = resSN;
	}
	public Date getRestime() {
		return restime;
	}
	public void setRestime(Date restime) {
		this.restime = restime;
	}
	public String getReqSN() {
		return reqSN;
	}
	public void setReqSN(String reqSN) {
		this.reqSN = reqSN;
	}
	public Date getReqOperTime() {
		return reqOperTime;
	}
	public void setReqOperTime(Date reqOperTime) {
		this.reqOperTime = reqOperTime;
	}
	public Long getReqOperId() {
		return reqOperId;
	}
	public void setReqOperId(Long reqOperId) {
		this.reqOperId = reqOperId;
	}
	public String getReqOperNo() {
		return reqOperNo;
	}
	public void setReqOperNo(String reqOperNo) {
		this.reqOperNo = reqOperNo;
	}
	public String getReqOperName() {
		return reqOperName;
	}
	public void setReqOperName(String reqOperName) {
		this.reqOperName = reqOperName;
	}
	public Long getReqPlaceId() {
		return reqPlaceId;
	}
	public void setReqPlaceId(Long reqPlaceId) {
		this.reqPlaceId = reqPlaceId;
	}
	public String getReqPlaceNo() {
		return reqPlaceNo;
	}
	public void setReqPlaceNo(String reqPlaceNo) {
		this.reqPlaceNo = reqPlaceNo;
	}
	public String getReqPlaceName() {
		return reqPlaceName;
	}
	public void setReqPlaceName(String reqPlaceName) {
		this.reqPlaceName = reqPlaceName;
	}
	public Date getSureTime() {
		return sureTime;
	}
	public void setSureTime(Date sureTime) {
		this.sureTime = sureTime;
	}
	public Long getSureOperId() {
		return sureOperId;
	}
	public void setSureOperId(Long sureOperId) {
		this.sureOperId = sureOperId;
	}
	public String getSureOperNo() {
		return sureOperNo;
	}
	public void setSureOperNo(String sureOperNo) {
		this.sureOperNo = sureOperNo;
	}
	public String getSureOperName() {
		return sureOperName;
	}
	public void setSureOperName(String sureOperName) {
		this.sureOperName = sureOperName;
	}
	public Long getSurePlaceId() {
		return surePlaceId;
	}
	public void setSurePlaceId(Long surePlaceId) {
		this.surePlaceId = surePlaceId;
	}
	public String getSurePlaceNo() {
		return surePlaceNo;
	}
	public void setSurePlaceNo(String surePlaceNo) {
		this.surePlaceNo = surePlaceNo;
	}
	public String getSurePlaceName() {
		return surePlaceName;
	}
	public void setSurePlaceName(String surePlaceName) {
		this.surePlaceName = surePlaceName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getEtcCardNumber() {
		return etcCardNumber;
	}
	public void setEtcCardNumber(String etcCardNumber) {
		this.etcCardNumber = etcCardNumber;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
}
