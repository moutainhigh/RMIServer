package com.hgsoft.daysettle.entity;

import java.util.Date;

public class DaySetApprove implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2696407566139195258L;

	private Long id;
	
	private String settleDay;
	
	private Long salesDep;
	
	private String salesDepName;
	
	private String state;
	
	private Long approver;
	
	private String approverNo;
	
	private String approverName;
	
	private Date appTime;
	
	private String appOpinion;
	
	private Date sysStartTime;
	
	private Date sysEndTime;
	
	private Long stockPlace;

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

	public Long getSalesDep() {
		return salesDep;
	}

	public void setSalesDep(Long salesDep) {
		this.salesDep = salesDep;
	}

	public String getSalesDepName() {
		return salesDepName;
	}

	public void setSalesDepName(String salesDepName) {
		this.salesDepName = salesDepName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getApprover() {
		return approver;
	}

	public void setApprover(Long approver) {
		this.approver = approver;
	}

	public String getApproverNo() {
		return approverNo;
	}

	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Date getAppTime() {
		return appTime;
	}

	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}

	public String getAppOpinion() {
		return appOpinion;
	}

	public void setAppOpinion(String appOpinion) {
		this.appOpinion = appOpinion;
	}

	public Date getSysStartTime() {
		return sysStartTime;
	}

	public void setSysStartTime(Date sysStartTime) {
		this.sysStartTime = sysStartTime;
	}

	public Date getSysEndTime() {
		return sysEndTime;
	}

	public void setSysEndTime(Date sysEndTime) {
		this.sysEndTime = sysEndTime;
	}

	public Long getStockPlace() {
		return stockPlace;
	}

	public void setStockPlace(Long stockPlace) {
		this.stockPlace = stockPlace;
	}
	
	

}
