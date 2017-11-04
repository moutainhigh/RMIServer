package com.hgsoft.daysettle.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class RegisterDaySetDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1018121703521477579L;
	private Long id;
	private Long mainId;
	private String feeType;
	private BigDecimal handFee;
	private String memo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public BigDecimal getHandFee() {
		return handFee;
	}
	public void setHandFee(BigDecimal handFee) {
		this.handFee = handFee;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
