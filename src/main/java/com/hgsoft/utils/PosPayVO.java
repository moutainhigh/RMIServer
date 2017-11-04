package com.hgsoft.utils;

import java.math.BigDecimal;

public class PosPayVO {
	private Long  posId ;
	private String  payMentNo ;
	private BigDecimal  takeBalance;
	public Long getPosId() {
		return posId;
	}
	public void setPosId(Long posId) {
		this.posId = posId;
	}
	public String getPayMentNo() {
		return payMentNo;
	}
	public void setPayMentNo(String payMentNo) {
		this.payMentNo = payMentNo;
	}
	public BigDecimal getTakeBalance() {
		return takeBalance;
	}
	public void setTakeBalance(BigDecimal takeBalance) {
		this.takeBalance = takeBalance;
	}
	
}
