package com.hgsoft.daysettle.entity;

import java.util.Date;

public class PreDaySetRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5774888564255581655L;
	private Long id;
	private String preSettleDay;
	private String preSettleResult;
	private String daySettleFlag;
	private Date daySettleTime;
	private Integer operID;
	private String macAddress;
	private Date operTime;
	private Integer operPlaceID;
	private String memo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPreSettleDay() {
		return preSettleDay;
	}
	public void setPreSettleDay(String preSettleDay) {
		this.preSettleDay = preSettleDay;
	}
	public String getPreSettleResult() {
		return preSettleResult;
	}
	public void setPreSettleResult(String preSettleResult) {
		this.preSettleResult = preSettleResult;
	}
	public String getDaySettleFlag() {
		return daySettleFlag;
	}
	public void setDaySettleFlag(String daySettleFlag) {
		this.daySettleFlag = daySettleFlag;
	}
	public Date getDaySettleTime() {
		return daySettleTime;
	}
	public void setDaySettleTime(Date daySettleTime) {
		this.daySettleTime = daySettleTime;
	}
	public Integer getOperID() {
		return operID;
	}
	public void setOperID(Integer operID) {
		this.operID = operID;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Integer getOperPlaceID() {
		return operPlaceID;
	}
	public void setOperPlaceID(Integer operPlaceID) {
		this.operPlaceID = operPlaceID;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
