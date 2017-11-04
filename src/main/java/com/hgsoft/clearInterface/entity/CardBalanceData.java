package com.hgsoft.clearInterface.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CardBalanceData implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8273754596551048972L;

	private Long id;
	
	private Long boardListNo;
	
	private String cardCode;
	
	private BigDecimal chargeTotalFee;
	
	private BigDecimal dealTotalFee;
	
	private BigDecimal cardBalance;
	
	private Date balanceTime;
	
	private Date receviceTime;
	
	private String fileName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoardListNo() {
		return boardListNo;
	}

	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public BigDecimal getChargeTotalFee() {
		return chargeTotalFee;
	}

	public void setChargeTotalFee(BigDecimal chargeTotalFee) {
		this.chargeTotalFee = chargeTotalFee;
	}

	public BigDecimal getDealTotalFee() {
		return dealTotalFee;
	}

	public void setDealTotalFee(BigDecimal dealTotalFee) {
		this.dealTotalFee = dealTotalFee;
	}

	public BigDecimal getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(BigDecimal cardBalance) {
		this.cardBalance = cardBalance;
	}

	public Date getBalanceTime() {
		return balanceTime;
	}

	public void setBalanceTime(Date balanceTime) {
		this.balanceTime = balanceTime;
	}

	public Date getReceviceTime() {
		return receviceTime;
	}

	public void setReceviceTime(Date receviceTime) {
		this.receviceTime = receviceTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
