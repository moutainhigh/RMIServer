package com.hgsoft.macao.entity;

import java.util.Date;

/**
 * 记帐卡注销
 * @author guokunpeng
 *
 */
public class SettlementPeriod implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1586225795791281507L;
	private Long id;
	private String month;
	private Long periods;
	private Date startTime;
	private Date endTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Long getPeriods() {
		return periods;
	}
	public void setPeriods(Long periods) {
		this.periods = periods;
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
	
}
