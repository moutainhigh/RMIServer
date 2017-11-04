package com.hgsoft.daysettle.entity;

import java.io.Serializable;
import java.util.Date;

public class RegisterDaySetRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8744762056495008951L;
	private Long id;
	private String settleDay;
	private Long operId;
	private String operNo;
	private String operName;
	private Long operPlaceId;
	private String placeNo;
	private String placeName;
	private Date operTime;
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
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
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
	public Long getOperPlaceId() {
		return operPlaceId;
	}
	public void setOperPlaceId(Long operPlaceId) {
		this.operPlaceId = operPlaceId;
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
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	
	
	
}
