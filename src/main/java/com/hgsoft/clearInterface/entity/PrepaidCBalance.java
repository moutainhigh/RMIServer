package com.hgsoft.clearInterface.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PrepaidCBalance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7404852859331575789L;

	private Long id;
	
	private String cardCode;
	
	private BigDecimal systemBalance;
	
	private BigDecimal returnMoney;
	
	private Date genTime;
	
	private String account;
	
	private String remark;
	
	private Date updateTime;
	
	private Long boardListNo;
	
	public PrepaidCBalance(){
		
	}
	
	public PrepaidCBalance(String cardCode,BigDecimal systemBalance,BigDecimal returnMoney,
			Date genTime,String account,String remark,Date updateTime){
		this.cardCode = cardCode;
		this.systemBalance = systemBalance;
		this.returnMoney = returnMoney;
		this.genTime = genTime;
		this.account = account;
		this.remark = remark;
		this.updateTime = updateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public BigDecimal getSystemBalance() {
		return systemBalance;
	}

	public void setSystemBalance(BigDecimal systemBalance) {
		this.systemBalance = systemBalance;
	}

	public BigDecimal getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getBoardListNo() {
		return boardListNo;
	}

	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}
	
	
}
