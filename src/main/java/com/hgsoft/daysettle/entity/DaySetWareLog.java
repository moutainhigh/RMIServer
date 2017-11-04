package com.hgsoft.daysettle.entity;

import java.io.Serializable;
import java.util.Date;

public class DaySetWareLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2624604019993163120L;
	/**
	 * 
	 */
	private Long id;
	private String settleDay;
	private Date startTime;
	private Date endTime;
	private Integer state;
	private Long reportOperID;
	private String macAddress;
	private Date reportTime;
	private Long reportPlaceID;
	private String memo;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getPlaceNo() {
		return placeNo;
	}

	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getReportOperID() {
		return reportOperID;
	}

	public void setReportOperID(Long reportOperID) {
		this.reportOperID = reportOperID;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Long getReportPlaceID() {
		return reportPlaceID;
	}

	public void setReportPlaceID(Long reportPlaceID) {
		this.reportPlaceID = reportPlaceID;
	}
	

}
