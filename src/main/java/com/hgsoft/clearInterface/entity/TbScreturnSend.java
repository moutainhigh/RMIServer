package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TbScreturnSend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2028098040643382938L;
	private Long id             ;
	private String cardNo       ;
	private String feeType      ;
	private BigDecimal returnFee ;
	private Date returnTime     ;
	private String voidCardCode ;
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
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public BigDecimal getReturnFee() {
		return returnFee;
	}
	public void setReturnFee(BigDecimal returnFee) {
		this.returnFee = returnFee;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public String getVoidCardCode() {
		return voidCardCode;
	}
	public void setVoidCardCode(String voidCardCode) {
		this.voidCardCode = voidCardCode;
	}
	
}
