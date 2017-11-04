package com.hgsoft.other.entity;

import java.io.Serializable;
import java.util.Date;

public class CardDelay implements Serializable{
	
	private static final long serialVersionUID = -1091135254191750686L;
	private Long id;
	private String cardNo;
	private String cardType;
	private Date beforeDelayTime;
	private String delayTime;
	private String flag;
	private String operator;
	private Date operTime;
	private String operPlace;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Date getBeforeDelayTime() {
		return beforeDelayTime;
	}
	public void setBeforeDelayTime(Date beforeDelayTime) {
		this.beforeDelayTime = beforeDelayTime;
	}
	public String getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getOperPlace() {
		return operPlace;
	}
	public void setOperPlace(String operPlace) {
		this.operPlace = operPlace;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
