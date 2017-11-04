package com.hgsoft.macao.entity;

import java.util.Date;

public class MacaoAddCarReq implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4389364240906124312L;
	
	private Long id;
	private Date reqTime;
	private String reqSN;
	private String bankAccountNumber;
	private String carNumber;
	private String carColor;
	private String resSN;
	private Date restime;
	private String isNotify;
	
	public String getReqSN() {
		return reqSN;
	}
	public void setReqSN(String reqSN) {
		this.reqSN = reqSN;
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
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
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
	public String getIsNotify() {
		return isNotify;
	}
	public void setIsNotify(String isNotify) {
		this.isNotify = isNotify;
	}
}
