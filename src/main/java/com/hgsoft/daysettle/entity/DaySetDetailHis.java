package com.hgsoft.daysettle.entity;

import java.math.BigDecimal;

public class DaySetDetailHis implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7733601905453094034L;
	private Long id;
	private Long mainID;
	private String settleDay;
	private String feeType;
	private BigDecimal systemFee;
	private BigDecimal handFee;
	private BigDecimal lsadjustFee;
	private BigDecimal differenceFee;
	private String differenceFlag;
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
	public String getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
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
	public String getDifferenceFlag() {
		return differenceFlag;
	}
	public void setDifferenceFlag(String differenceFlag) {
		this.differenceFlag = differenceFlag;
	}
	
}
