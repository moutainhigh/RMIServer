package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class SCinfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3310477803754858308L;
	private Long id;
	private String userNo;
	private String cardNo;
	private String state;
	private Date businessTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	
	
}
