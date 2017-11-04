package com.hgsoft.daysettle.entity;

import java.math.BigDecimal;

public class PreDaySetDetail implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513020688986962717L;
	private Long id;
	private Long mainID;
	private String preSettleDay;
	private String feeType;
	private BigDecimal systemFee;
	private BigDecimal handFee;
	private BigDecimal lsadjustFee;
	private BigDecimal differenceFee;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainID() {
		return mainID;
	}
	public void setMainID(Long mainID) {
		this.mainID = mainID;
	}
	public String getPreSettleDay() {
		return preSettleDay;
	}
	public void setPreSettleDay(String preSettleDay) {
		this.preSettleDay = preSettleDay;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public BigDecimal getSystemFee() {
		return systemFee;
	}
	public void setSystemFee(BigDecimal systemFee) {
		this.systemFee = systemFee;
	}
	public BigDecimal getHandFee() {
		return handFee;
	}
	public void setHandFee(BigDecimal handFee) {
		this.handFee = handFee;
	}
	
	public BigDecimal getLsadjustFee() {
		return lsadjustFee;
	}
	public void setLsadjustFee(BigDecimal lsadjustFee) {
		this.lsadjustFee = lsadjustFee;
	}
	public BigDecimal getDifferenceFee() {
		return differenceFee;
	}
	public void setDifferenceFee(BigDecimal differenceFee) {
		this.differenceFee = differenceFee;
	}
	
	
}
