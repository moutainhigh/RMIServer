package com.hgsoft.acms.other.entity;

import java.util.Date;

public class ServiceAssessACMS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long customerId;
	private Date beginTime;
	private Date endTime;
	private String assessLevel;
	private Date assessTime;
	private String memo;//备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getAssessLevel() {
		return assessLevel;
	}
	public void setAssessLevel(String assessLevel) {
		this.assessLevel = assessLevel;
	}
	public Date getAssessTime() {
		return assessTime;
	}
	public void setAssessTime(Date assessTime) {
		this.assessTime = assessTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
