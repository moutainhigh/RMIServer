package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class CardStateInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3840807290135495237L;

	private Long id;

	private Date genTime;
	
	private Integer stateFlag;
	
	private String serType;
	
	private String cardCode;
	
	private String cardType;
	
	private String userType;
	
	private Long boardListNo;
	
	private Date updateTime;

	public CardStateInfo(){
		
	}
	
	public CardStateInfo(Date genTime,Integer stateFlag,String serType,String cardCode,String cardType,String userType){
		this.genTime = genTime;
		this.stateFlag = stateFlag;
		this.serType = serType;
		this.cardCode = cardCode;
		this.cardType = cardType;
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Integer getStateFlag() {
		return stateFlag;
	}

	public void setStateFlag(Integer stateFlag) {
		this.stateFlag = stateFlag;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getBoardListNo() {
		return boardListNo;
	}

	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}




}
