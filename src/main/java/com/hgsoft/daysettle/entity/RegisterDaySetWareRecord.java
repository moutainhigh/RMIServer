package com.hgsoft.daysettle.entity;

import java.io.Serializable;
import java.util.Date;

public class RegisterDaySetWareRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8727054762608776594L;
	private Long id;
	private String settleDay;
	private Date settleTime;
	private Long reportOperID;
	private Date reportTime;
	private Long reportPlaceID;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
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
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
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
	
	
}
